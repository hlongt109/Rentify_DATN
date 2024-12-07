const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const Post = new Schema({

    user_id: { 
        type: mongoose.Schema.Types.ObjectId,
        ref: 'User',
    },
    building_id: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'Building'
    },
    room_id: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'Room'
    },
    title: { type: String },
    content: { type: String },
    status: { type: Number, enum: [0, 1, 2, 3], default: 0 }, // 0 chờ xác nhận, 1 hoạt động, 2 ban, 3 ẩn
    video: { type: Array },
    photo: { type: Array },
    post_type: { type: String, enum: ['roomate', 'rent', 'seek']},
    created_at: { type: String },
    updated_at: { type: String }

})
module.exports = mongoose.model("Post", Post);