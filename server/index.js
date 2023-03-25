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

// endpoint for viewing a list of items for sale from other sellers on the homepage
app.use("/all", (req, res) => {
	// find all the Post objects in the database
	Post.find()({}, (err, posts) => {
		if (err) {
		    res.type('html').status(200);
		    console.log(err);
		    res.write(err);
		} else {
			if (posts.length == 0) {
				res.type('html').status(200);
				res.write('There are no items for sale!');
				res.end();
				return;
			} else {
				res.type('html').status(200);
				res.write('Here are the list of items for sale on the homepage:');
				res.write('<ul>');
				// show all the items
				posts.forEach( (post) => {
					res.write('<li>');
					res.write('Name: ' + post.seller); 
					res.write('Title: ' + post.title);
					res.write('Date: ' + post.date); 
					res.write('Description: ' + post.description);
					res.write('Price: ' + post.price);
					res.write('Photos: ' + post.photos); 
					res.write('Status: ' + post.status);
					res.write('</li>');
				});
				res.write('</ul>');
				res.end();
		    }
		}
	}).sort({ 'Date': 'asc' }); // sorting by date before rendering the results
});

app.listen(3000, () => {
	console.log('Listening on port 3000');
});