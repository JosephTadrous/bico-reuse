let express= require('express');
let app= express();

let mongoose= require('mongoose');

let User= require('./User.js');
let Post= require('./Post.js');

let newUser= new User({
  _id: new mongoose.Types.ObjectId(),
  name: 'David Dinh',
  email: 'ddinh@haverford.edu',
  phone: '7174391476',
  college: 'Haverford',
  pfp: null,
  history: [],
  bookmarked: [],
});

let newPost= new Post({
  _id: new mongoose.Types.ObjectId(),
  seller: newUser._id,
  title: 'Toys',
  date: Date.now(),
  description: 'I am selling toys such as a teddybear for a low cost.',
  price: 1.11,
  photos: [],
  status: 'available'
});

newUser.history.push(newPost._id);

const user_result= newUser.save();
user_result.then((response) => console.log(response));

const post_result= newPost.save();
post_result.then((response) => console.log(response));

