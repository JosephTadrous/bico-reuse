let express= require('express');
let app= express();
let cors= require('cors');
var mongoose = require('mongoose');

var bodyParser = require('body-parser');
app.use(bodyParser.urlencoded({ extended: true }));


// set up BodyParser
var bodyParser = require('body-parser');
app.use(bodyParser.urlencoded({ extended: true }));

app.use(cors());

let User= require('./dbfiles/User.js');
let Post= require('./dbfiles/Post.js');


app.use("/profile", (req, res) => {

	// shows another user's profile
	if (req.query.id) {
		var filter = {'_id' : req.query.id};
		let user = User.findOne(filter)
		.populate('history')
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
					post.forEach( (p) => {
						// res.write('<li>');
						res.write('Name: ' + p.seller + '\n');  // double check 
						res.write('Title: ' + p.title + '\n');
						res.write('Date: ' + p.date + '\n'); 
						res.write('Description: ' + p.description + '\n');
						res.write('Price: ' + p.price + '\n');
						res.write('Photos: ' + p.photos + '\n'); 
						res.write('Status: ' + p.status + '\n\n\n');
						res.write('</li>');
					});
				} else { // only one post exists
					var p = post[0];
					res.write('Name: ' + p.seller + '\n'); 
					res.write('Title: ' + p.title + '\n');
					res.write('Date: ' + p.date + '\n'); 
					res.write('Description: ' + p.description + '\n');
					res.write('Price: ' + p.price + '\n');
					res.write('Photos: ' + p.photos + '\n'); 
					res.write('Status: ' + p.status + '\n\n\n');
				}
			} else { // undefined
				res.json([]);
			}
		}, // failure
		(error) => {
			res.status(500).send('Something went wrong');
	});
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
	var newPhotos = req.body.image;
	var newStatus = req.body.status;

	var filter = {'_id' : postId};
	// 
	var action = { '$set' : { 'title' :  newTitle, 'description': newDesc, 'price': newPrice, 'image': [newPhotos], 'status': newStatus} };

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
	console.log(pid);
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
	photo = req.body.photo
	let newPost= new Post({
		_id: new mongoose.Types.ObjectId(),
		seller: user_id,
		title: title,
		date: Date.now(),
		description: description, 
		price: price,
		photos: (photo) ? [photo] : [],
		status: 'available'
	  });
	const post_result= newPost.save();
	post_result.then((response) =>  { res.redirect("http://localhost:5173/")}, 
	(error) => {
		res.status(500).send("Something went wrong"); 
	})
});

app.use("/delete_post", (req, res) => {
	// delete a post for sales 
	// to use the API, send a request to /delete_post?id=[your_post_id]
	post_id = req.body.id; 
	console.log(post_id); 
	console.log("Deleting post " + post_id); 
	Post.deleteOne({ '_id': post_id }).then((response) =>  {res.redirect("http://localhost:5173/")}, 
	(error) => {
		res.status(500).send("Something went wrong"); 
	}); 
});


app.post("/login_user", (req, res) => {
		let reqEmail= req.body.email;
		let reqPass= req.body.password;
		
	  let filter = {'email' : reqEmail};

		User.findOne(filter)
		.populate('history')
		.populate('bookmarked').then(
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

	user_result.then((response) =>  { console.log("Successfully created new user") }, 
	(error) => {
		console.log(error);
		res.status(500).send("Something went wrong"); 
	});
});



app.listen(3000, () => {
	console.log('Listening on port 3000');
});