let express= require('express');
let app= express();
let cors= require('cors');

app.use(cors());

let User= require('./dbfiles/User.js');
let Post= require('./dbfiles/Post.js');

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

app.use("/create_post", (req, res) => {
	// create a new post for sales 
	user_id = req.body.user_id
	title = req.body.title; 
	description = req.body.description; 
	price = Number(req.body.price); 
	let newPost= new Post({
		_id: new mongoose.Types.ObjectId(),
		seller: user_id,
		title: title,
		date: Date.now(),
		description: description, 
		price: price,
		photos: [],
		status: 'available'
	  });
	const post_result= newPost.save();
	post_result.then((response) =>  { res.json("Successfully added a new post") }, 
	(error) => {
		res.status(500).send("Something went wrong"); 
	})
});

app.use("/delete_post", (req, res) => {
	// delete a post for sales 
	// to use the API, send a request to /delete_post?id=[your_post_id]
	post_id = req.query.id; 
	console.log("Deleting post " + post_id); 
	Post.deleteOne({ '_id': post_id }).then((response) =>  { res.json("Successfully deleted a post") }, 
	(error) => {
		res.status(500).send("Something went wrong"); 
	}); 
});


app.listen(3000, () => {
	console.log('Listening on port 3000');
});