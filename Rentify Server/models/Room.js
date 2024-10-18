const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const Room = new Schema({
    landlord_id: { type: String, require: true },
    building_id: { type: String, require: true },
    room_type: { type: String, require: true },
    description: { type: String, require: true },
    price: { type: Number, require: true },
    size: { type: String, require: true },// vd: 40m2 nên sẽ để là String
    availability_status: { type: String, require: true },
    video_room: { type: String, require: false },
    photos_room: { type: String, require: false },
    service_id: { type: String, require: true },
    limit_person: { type: String, require: true },
    created_at: { type: String, require: false },
    updated_at: { type: String, require: false }
})
module.exports = mongoose.model("Room", Room);