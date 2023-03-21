let mongoose= require('mongoose');


let username= 'shared_admin';
let dbKey= process.env.DB_PASS;

mongoose.connect(`mongodb+srv://${username}:${dbKey}@bico-reuse.rcpizlz.mongodb.net/test`);

let Schema= mongoose.Schema;

let userSchema= new Schema({
  _id: Schema.Types.ObjectId,
  name: { type: String, required: true },
  email: { type: String, required: true},
  phone: String,
  college: String,
  pfp: String,
  history: [ { type: Schema.Types.ObjectId, ref: 'Post' } ],
  bookmarked: [ { type: Schema.Types.ObjectId, ref: 'Post' } ]
});

module.exports= mongoose.model('User', userSchema);
