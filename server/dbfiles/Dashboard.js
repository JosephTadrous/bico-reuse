
let mongoose= require('mongoose');


let username= 'shared_admin';
let dbKey= process.env.DB_PASS;

mongoose.connect(`mongodb+srv://${username}:${dbKey}@bico-reuse.rcpizlz.mongodb.net/test`);

let Schema= mongoose.Schema;

module.exports= mongoose.model('Post', postSchema);