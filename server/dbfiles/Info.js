let mongoose= require('mongoose');

let username= 'shared_admin';
let dbKey= process.env.DB_PASS;

mongoose.connect(`mongodb+srv://${username}:${dbKey}@bico-reuse.rcpizlz.mongodb.net/test`);

let Schema= mongoose.Schema;

let infoSchema= new Schema({
	_id: 0, 
	items_sold: Number,
});

module.exports= mongoose.model('Info', infoSchema);