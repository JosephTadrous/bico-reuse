let mongoose= require('mongoose');
let crypto= require('crypto');


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
  bookmarked: [ { type: Schema.Types.ObjectId, ref: 'Post' } ],
  hash: String,
  salt: String
});

userSchema.methods.setPassword = function(password) {
  this.salt= crypto.randomBytes(16).toString('hex');

  this.hash= crypto.pbkdf2Sync(password, this.salt,
  1000, 64, 'sha512').toString('hex');
};

userSchema.methods.validatePassword = function(password) {
  let hash= crypto.pbkdf2Sync(password, 
    this.salt, 1000, 64, 'sha512').toString('hex');

  return this.hash === hash;
}


module.exports= mongoose.model('User', userSchema);
