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
    },
    building_id: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'Building',
    },
    title_support: { type: String, require: true },//kien nghi / khieu nai
    content_support: { type: String, require: true },
    image: { type: Array },
    status: { type: Number, enum: [0, 1], default: 1 },
    created_at: { type: String },
    updated_at: { type: String }
})
module.exports = mongoose.model("Support", Support);