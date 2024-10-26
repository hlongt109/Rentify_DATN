const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const Support = new Schema({  // bảng hỏng hóc 
    user_id: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'User',
        required: true
    },
    room_id: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'Room',
        required: true
    },
    title_support: { type: String, require: true },
    content_support: { type: String, require: true },
    image: { type: Array },
    status: { type: Number, require: true },
    created_at: { type: String },
    updated_at: { type: String }
})
module.exports = mongoose.model("Support", Support);