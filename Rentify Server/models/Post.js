const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const Post = new Schema({
    user_id: { type: String, require: true },
    title: { type: String, require: true },
    content: { type: String, require: true },
    status: { type: String, require: true },
    video: { type: String, require: true },
    photo: { type: String, require: true },
    created_at: { type: String, require: false },
    updated_at: { type: String, require: false }

})
module.exports = mongoose.model("Post", Post);
// test nhánh 