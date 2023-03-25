let express= require('express');
let app= express();
let cors= require('cors');

app.use(cors());

let User= require('./dbfiles/User.js');
let Post= require('./dbfiles/Post.js');

app.use("/profile", (req, res) => {

	// shows another user's profile
	if (req.query.id) {
		var filter = {'_id' : req.query.id};
		let user = User.findOne(filter)
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

	// User.findOne(filter)
	// .then((user)=>{
	// 	console.log("Result :",user);
	// 	res.json({'user' : user});
	// })
	// .catch((err)=>{
	// 	console.log(err);
	// });
	// if (!req.query.id) {
	// 	console.log("wtf")
	// }
	// else {
	// 	let user = 	User.findOne(filter).then( (user, err) => {
	// 		if (err) {
	// 			res.json({'status' : err});
	// 		}
	// 		else if (!user) {
	// 			res.json({'status' : 'no user found'});
	// 		}
	// 		else {
	// 			res.json({'user' : user});
	// 		}
	// 	});
	// }


app.get("/api", (req, res) => {
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


app.listen(3000, () => {
	console.log('Listening on port 3000');
});