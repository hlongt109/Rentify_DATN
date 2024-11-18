const { type } = require('express/lib/response');
const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const Room = new Schema({
    building_id: {
        type: mongoose.Schema.Types.ObjectId,
        ref: "Building",
        required: true
    },
    room_type: { type: String, required: true },
    description: { type: String, required: true },
    price: { type: Number, required: true }, // tien phong
    size: { type: String, required: true },// vd: 40m2 nên sẽ để là String
    video_room: { type: Array },// tải lên từ máy 
    photos_room: { type: Array },//tải lên từ máy 
    service: { type: Array },
    amenities: { type: Array }, // tiện nghi
    limit_person: { type: Number, required: true }, // giới hạn người ở
    status: { type: Number, required: true }, // 0: chua cho thue, 1: cho thue
    created_at: { type: String },
    updated_at: { type: String }
})
module.exports = mongoose.model("Room", Room);
// gộp service