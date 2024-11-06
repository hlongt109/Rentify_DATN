const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const Post = new Schema({
    user_id: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'User',
        required: false
    },
    title: { type: String, require: true },
    content: { type: String, require: true },
    status: { type: Number, enum: [0, 1], default: 1 },
    video: { type: Array },
    photo: { type: Array },
    post_type: { type: String, enum: ['roomate', 'rent'], require: true },
    created_at: { type: String },
    updated_at: { type: String }

})
module.exports = mongoose.model("Post", Post);