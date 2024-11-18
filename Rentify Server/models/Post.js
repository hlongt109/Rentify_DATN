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
    status: { type: Number, enum: [0, 1, 2], default: 0},
    video: { type: Array },
    photo: { type: Array },
    price: {type: Number},
    address: { type: String },
    phoneNumber: { type: String },
    room_type: { type: String },
    amenities: { type: Array }, // tiện nghi 
    services: { type: Array }, // dịch vụ lưu dạng string
    post_type: { type: String, enum: ['roomate', 'rent', 'seek'], require: true },
    created_at: { type: String },
    updated_at: { type: String }

})
module.exports = mongoose.model("Post", Post);