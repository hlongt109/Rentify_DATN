const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const Post = new Schema({
  user_id: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'User',
        required: false
    },
    building_id: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'Building'
    },
    room_id: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'Room'
    },
    title: { type: String, require: true },
    content: { type: String, require: true },
    status: { type: Number, enum: [0, 1, 2], default: 0 }, // 0 hoạt động, 1 tạm ẩn, 2 ban
    video: { type: Array },
    photo: { type: Array },
    post_type: { type: String, enum: ['roomate', 'rent', 'seek'], require: true },
    created_at: { type: String },
    updated_at: { type: String }

})
module.exports = mongoose.model("Post", Post);