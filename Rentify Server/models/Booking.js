const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const Booking = new Schema({
    user_id: { type: String, require: true },
    room_id: { type: String, require: true },
    check_in_date: { type: String, require: true },
    check_out_date: { type: String, require: true },
    status: { type: String, require: true },
    created_at: { type: String }
})
module.exports = mongoose.model("Booking", Booking);