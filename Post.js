
let mongoose= require('mongoose');

let dbKey= process.env.DB_PASS

mongoose.connect(`mongodb+srv://dndinh7:${dbKey}@bico-reuse.rcpizlz.mongodb.net/test`);

let Schema= mongoose.Schema;

let postSchema= new Schema({
  _id: Schema.Types.ObjectId,
  seller: { type: Schema.Types.ObjectId, ref: 'User' },
  title: { type: String, required: true},
  date: { type: Date, required: true},
  description: String,
  price: Number,
  photos: [String],
  status: String
});

module.exports= mongoose.model('Post', postSchema);