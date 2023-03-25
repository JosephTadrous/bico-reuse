let express= require('express');
let app= express();
let cors= require('cors');

// set up BodyParser
var bodyParser = require('body-parser');
app.use(bodyParser.urlencoded({ extended: true }));

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
					// res.json(post);
					// res.write('<ul>');
					post.forEach( (p) => {
						// res.write('<li>');
						res.write('Name: ' + p.seller + '\n'); 
						res.write('Title: ' + p.title + '\n');
						res.write('Date: ' + p.date + '\n'); 
						res.write('Description: ' + p.description + '\n');
						res.write('Price: ' + p.price + '\n');
						res.write('Photos: ' + p.photos + '\n'); 
						res.write('Status: ' + p.status + '\n\n\n');
						res.write('</li>');
					});
					// res.write('<ul>');
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

app.listen(3000, () => {
	console.log('Listening on port 3000');
});