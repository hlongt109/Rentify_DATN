const mongoose = require("mongoose");
const Schema = mongoose.Schema;
const Booking = new Schema({
  user_id: {
    type: mongoose.Schema.Types.ObjectId,
    ref: "User",
    required: true,
  },
  room_id: {
    type: mongoose.Schema.Types.ObjectId,
    ref: "Room",
    required: true,
  },
  check_in_date: { type: String, require: true },
  check_out_date: { type: String, require: false },
  status: { type: Number, require: true, default: 0 }, //0 chua xem phong, 1 da xem phong
  created_at: { type: String, default: Date.now() },
});
module.exports = mongoose.model("Booking", Booking);
