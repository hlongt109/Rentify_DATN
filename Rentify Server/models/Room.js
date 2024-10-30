const { type } = require('express/lib/response');
const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const Room = new Schema({
    landlord_id: { 
        type: mongoose.Schema.Types.ObjectId,
        ref: 'Landlord',
        required: true
    },
    building_id: { 
        type: mongoose.Schema.Types.ObjectId,
        ref: "Building",
        required: true 
    },
    room_type: { type: String, required: true },
    description: { type: String, required: true },
    price: { type: Number, required: true }, // tien phong
    size: { type: String, required: true },// vd: 40m2 nên sẽ để là String
    availability_status: { type: String, required: true },
    video_room: { type: String},
    photos_room: { type: Array},
    service_ids: { type: Array}, // may giat , tu lanh
    amenities: {type: Array},
    service_fees: {type: Array},
    limit_person: { type: Number, required: true },
    status: { type: Number, required: true }, // 0: chua cho thue, 1: cho thue
    created_at: { type: String },
    updated_at: { type: String }
})
module.exports = mongoose.model("Room", Room);