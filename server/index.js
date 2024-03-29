
let express= require('express');
let app= express();
let cors= require('cors');
var mongoose = require('mongoose');

var bodyParser = require('body-parser');
app.use(bodyParser.urlencoded({ extended: true }));

// set up date imports
let date_fns= require('date-fns');

// set up BodyParser
var bodyParser = require('body-parser');
app.use(bodyParser.urlencoded({ extended: true }));

app.use(cors());

let User= require('./dbfiles/User.js');
let Post= require('./dbfiles/Post.js');
let Info= require('./dbfiles/Info.js');


app.use("/profile", (req, res) => {

	// shows another user's profile
	if (req.query.id) {
		var filter = {'_id' : req.query.id};
		let user = User.findOne(filter)
		.populate('history')
		.populate('bookmarked')
		.then(
			// success
			(user) => {
				// user found
				if (user) {
					res.json(user);
				} else {
					// user not found
					res.send('user not found');
				}
			},
			// failure
			(error) => {
				res.status(500).send('Something went wrong');
		});
	}
});

app.use("/get_bookmarks", (req, res) => {

// shows another user's profile
	var filter = {'_id' : req.query.id};
	let user = User.findOne(filter)
	.populate('history')
	.populate('bookmarked')
	.then(
		// success
		(user) => {
			// user found
			if (user) {
				res.json(user.bookmarked);
			} else {
				// user not found
				res.send('user not found');
			}
		},
		// failure
		(error) => {
			res.status(500).send('Something went wrong');
	});

});

app.use("/get_history", (req, res) => {

	// shows another user's profile
		var filter = {'_id' : req.query.id};
		let user = User.findOne(filter)
		.populate('history')
		.populate('bookmarked')
		.then(
			// success
			(user) => {
				// user found
				if (user) {
					res.json(user.history);
				} else {
					// user not found
					res.send('user not found');
				}
			},
			// failure
			(error) => {
				res.status(500).send('Something went wrong');
		});
	
	});


app.get("/api", (req, res) => {
	// get all documents
	Post.find()
	.populate('seller')
	.then(
		// success
		(post) => {
			if (post) {
				if (Array.isArray(post)) {
					res.json(post);
				} else { // only one post exists
					res.json([post]);
				}
			} else { // undefined
				res.json([]);
			}
		}, // failure
		(error) => {
			res.status(500).send('Something went wrong');
	});
});

app.get("/users", (req, res) => {
	User.find()
	.populate('history')
	.populate('bookmarked')
	.then(
		// success
		(user) => {
			if (user) {
				if (Array.isArray(user)) {
					res.json(user);
				} else {
					res.json([user]);
				}
			} else {
				res.json([]);
			}
		},
		(error) => {
			res.status(500).send('Something went wrong');
		});
});

app.use("/update", (req, res) => {
	var filter = { '_id' : req.query.id };
	var action = { '$set': {'name' : req.body.name, 'email' : req.body.email, 'phone' : req.body.phone, 'college' : req.body.college} };
	let updatedUser = User.findOneAndUpdate(filter, action)
	.then(
		(orig) => {
			if (!orig) {
				res.json({'status' : 'no user found'});
			} else {
				res.redirect("http://localhost:5173/");
			}
		},
		(error) => {
			res.status(500).send('Something went wrong');
		}
	);
});

// endpoint for viewing a list of items for sale from other sellers on the homepage
app.use("/all", (req, res) => {
	// get all documents
	let posts= Post.find()
	.populate('seller')
	.then(
		// success
		(post) => {
			if (post) {
				if (Array.isArray(post)) {
					res.json(post); 
				} else { // only one post exists
					
				}
			} else { // undefined
				res.json([]);
			}
		}, // failure
		(error) => {
			res.status(500).send('Something went wrong');
	});
});

// endpoint for viewing a list of items already approved for sale
app.use("/all_approved", (req, res) => {
	// get all documents
	var filter = {'approved': true};
	Post.find(filter)
	.populate('seller')
	.then(
		// success
		(post) => {
			if (post) {
				if (Array.isArray(post)) {
					res.json(post);
				} else { // only one post exists
					res.json([post]);
				}
			} else { // undefined
				res.json([]);
			}
		}, // failure
		(error) => {
			res.status(500).send('Something went wrong');
	});
});

app.use("/add_bookmark", (req, res) => {
	var filter = { '_id' : req.body.userId };
	var action = { '$push': {'bookmarked' : req.body.postId} };
	let updatedUser = User.findOneAndUpdate(filter, action)
	.then(
		(orig) => {
			if (!orig) {
				res.json({'status' : 'no user found'});
			} else {
				res.redirect("http://localhost:5173/");
			}
		},
		(error) => {
			res.status(500).send('Something went wrong');
		}
	);
});

app.use("/delete_bookmark", (req, res) => {
	var filter = { '_id' : req.body.userId };
	var action = { '$pull': {'bookmarked' : req.body.postId} };
	let updatedUser = User.findOneAndUpdate(filter, action)
	.then(
		(orig) => {
			if (!orig) {
				res.json({'status' : 'no user found'});
			} else {
				res.redirect("http://localhost:5173/");
			}
		},
		(error) => {
			res.status(500).send('Something went wrong');
		}
	);
});


// endpoint for editing currently active posts such as updating the description and price 
app.use("/edit_post", (req, res) => {
	// if (!req.body.id) {
	// 	// if post id is missing
    //     res.json( { 'status' : 'missing Data' });
	// }

	var postId = req.body.id;
	var newTitle = req.body.title;
	var newDesc = req.body.description; 
	var newPrice = Number(req.body.price);
	var newPhotos = req.body.photo;
	var newStatus = req.body.status;

	var filter = {'_id' : postId};
	// 
	var action = { '$set' : { 'title' :  newTitle, 'description': newDesc, 'price': newPrice, 'photos': [newPhotos], 'status': newStatus} };

	let updatedPost = Post.findOneAndUpdate(filter, action)
	.then(
		(oldPost) => {
			if (!oldPost) {
				// console.log("FAILURE");
				res.json({'status' : 'no post found'});
			} else {
				// res.json({'status' : 'updated the post'});
				// console.log("SUCCESS!");
				res.redirect('http://localhost:5173/');
			}
		},
		(error) => {
			// if an error occurs 
			// console.log("ERROR");
			res.status(500).send(error);
		}
	);
});

app.use("/approve_post", (req, res) => {
	// if (!req.body.id) {
	// 	// if post id is missing
    //     res.json( { 'status' : 'missing Data' });
	// }

	var postId = req.body.id; 

	var filter = {'_id' : postId};
	// 
	var action = { '$set' : { 'approved' :  true} };

	let updatedPost = Post.findOneAndUpdate(filter, action)
	.then(
		(oldPost) => {
			if (!oldPost) {
				// console.log("FAILURE");
				res.json({'status' : 'no post found'});
			} else {
				// res.json({'status' : 'updated the post'});
				// console.log("SUCCESS!");
				console.log(oldPost.photos[0]);
				res.redirect('http://localhost:5173/');
			}
		},
		(error) => {
			// if an error occurs 
			console.log(error);
			res.status(500).send(error);
		}
	);
});

// app.use("/edit_post", (req, res) => {
// 	// if (!req.query.id) {
// 	// 	// if post id is missing
//     //     res.json( { 'status' : 'missing Data' });
// 	// }

// 	var postId = req.body.id;
// 	var newTitle = req.body.title;
// 	var newDesc = req.body.description; 
// 	var newPrice = Number(req.body.price);
// 	var newPhotos = req.body.photos;
// 	var newStatus = req.body.status;

// 	var filter = {'_id' : postId};

// 	var action = { '$set' : { 'title' :  newTitle, 'description': newDesc, 
// 	'price': newPrice, 'photos': newPhotos, 'status': newStatus} };

// 	let updatedPost = Post.findOneAndUpdate(filter, action)
// 	.then(
// 		(oldPost) => {
// 			if (!oldPost) {
// 				res.json({'status' : 'no post found'});
// 			} else {
// 				res.json({'status' : 'updated the post'});
// 			}
// 		},
// 		(error) => {
// 			// if an error occurs 
// 			console.log(error);
// 			res.status(500).send('Something went wrong');
// 		}
// 	);
// });

app.get("/post", (req, res) => {
	let pid= req.query.pid;
	console.log("AAAAA");
	Post.findById(pid)
	.populate('seller')
	.then(
		// success
		(post) => {
			res.json(post);
		}, // failure
		(error) => {
			res.status(500).send('Something went wrong');
		});
});


app.use("/create_post", (req, res) => {
	// create a new post for sales 
	user_id = req.body.user_id;
	title = req.body.title; 
	description = req.body.description; 
	price = Number(req.body.price); 
	photo = req.body.photo;

	let post_id= new mongoose.Types.ObjectId();

	let newPost= new Post({
		_id: post_id,
		seller: user_id,
		title: title,
		date: Date.now(),
		description: description, 
		price: price,
		photos: (photo) ? [photo] : [],
		status: 'available'
	  });
	const post_result= newPost.save();
	post_result.then((response) =>  { console.log("created post") }, 
	(error) => {
		res.status(500).send("Something went wrong"); 
	})
	
	var action = { $push: { "history" : post_id } };

	User.findByIdAndUpdate(user_id, action)
	.then((response) => {
		res.redirect("http://localhost:5173/")
	})
	.catch((error) => {
		res.status(500).send("Something went wrong");
	});
	
});

app.use("/delete_post", (req, res) => {
	// delete a post for sales 
	// to use the API, send a request to /delete_post?id=[your_post_id]
	post_id = req.body.id; 
	user_id = req.body.user_id;

	let action= { $pull : {"history" : post_id} };

	// delete post from history
	User.findByIdAndUpdate(user_id, action)
	.then((response) => 
		res.redirect("http://localhost:5173/")
	)
	.catch( (error) => 
		res.status(500).send("Could not delete from history")
	);
	
	console.log(post_id); 
	console.log("Deleting post " + post_id); 
	Post.deleteOne({ '_id': post_id }).then((response) =>  {}, 
	(error) => {
		res.status(500).send("Could not delete post"); 
	}); 

	action= { $inc: {'items_sold': 1} };
	Info.findByIdAndUpdate(0, action)
	.then((response) => 
		{ console.log("incremented items sold by 1"); }	
	)
	.catch( (error) => 
		res.status(500).send("Could not increment items_sold")
	);


});

app.use('/delete_user', (req, res) => {
	user_id= req.body.id;
	user_posts= req.body.user_posts;

	// check that if a single element is sent, we still put it in an array form
	if (!Array.isArray(user_posts)) {
		user_posts= [user_posts]
	}
	
	User.deleteOne({'_id': user_id})
	.then((response) => { console.log("Successfully deleted " + user_id)},
	(error) => {
		res.status(500).send("Something went wrong with deleting user");
	});

	if (user_posts) {
		Post.deleteMany({'_id': {$in: user_posts}})
		.then(
			(response) => { console.log("Deleted " + user_posts); 
				res.redirect("http://localhost:5173/"); },
			(error) => {
				res.redirect("http://localhost:5173/");
		});
	} else {
		res.redirect("http://localhost:5173/");
	}

});


app.post("/login_user", (req, res) => {
		let reqEmail= req.body.email;
		let reqPass= req.body.password;
		
	  let filter = {'email' : reqEmail};

		User.findOne(filter)
		.populate('history')
		.populate('bookmarked')
		.then(
			(user) => {
				if (user.validatePassword(reqPass)) {
					res.json(user);
				} else {
					res.status(400).send("Wrong password");
				}
			}, (error) => {
				res.status(400).send("User not found");
			}
		)
	}
);


app.use("/create_user", (req, res) => {
	// create a new post for sales 
	reqName= req.body.name;
	reqEmail= req.body.email;
	reqPhone= req.body.phone;
	reqCollege= req.body.college;
	reqPass= req.body.password;

	User.countDocuments({email: reqEmail})
	.then((count) => {
			if (count > 0) {
				res.status(400).send("email already exists");
			} else {
				let newUser= new User({
					_id: new mongoose.Types.ObjectId(),
					name: reqName,
					email: reqEmail,
					phone: reqPhone,
					college: reqCollege,
					pfp: null,
					history: [],
					bookmarked: [],
				});
			
				newUser.setPassword(reqPass);
			
				const user_result= newUser.save();
			
				user_result.then((response) =>  { res.status(200).send("Success"); }, 
				(error) => {
					console.log(error);
					res.status(500).send("Something went wrong"); 
				});
			}
		}
	);

	
});

// gets the number of posts 
app.use('/last_week', (req, res) => {

	let today= new Date();

	promises= [];

	let daysInMS= 24 * 60 * 60 * 1000;

	for (let i= 7; i >= 0; i--) {
		let startDay= date_fns.startOfDay(today - i * daysInMS);
		let endDay= date_fns.endOfDay(today - i * daysInMS);
		
		filter= { 'date': {$gte: startDay,
										 $lte: endDay} };
		promises.push(Post.countDocuments(filter));
	}

	Promise.all(promises)
	.then( (counts) => {
		res.json(counts);
	})
	.catch((error) => {
		console.log(error);
	});
});

app.use('/items_sold', (req, res) => {
	Info.findById(0)
	.then( (info) =>
		res.json(info.items_sold)
	)
	.catch( (error) =>
		console.log(error)
	)
});

app.get("/dashboard", (req, res) => {
	// get dashboard data
	var dashboard_data = {}
	Promise.all([Post.countDocuments({"approved": true}), User.countDocuments()]).then((values) => {
		dashboard_data["post_count"] = values[0]; 
		dashboard_data["user_count"] = values[1]; 
		res.json(dashboard_data); 
	})
});

app.listen(3000, () => {
	console.log('Listening on port 3000');
});