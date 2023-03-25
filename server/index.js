let express= require('express');
let app= express();
let cors= require('cors');

app.use(cors());

let User= require('./dbfiles/User.js');
let Post= require('./dbfiles/Post.js');

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

app.listen(3000, () => {
	console.log('Listening on port 3000');
});