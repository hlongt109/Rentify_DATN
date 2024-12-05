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
  building_id: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'Building',
  },
  manager_id: {
    type: mongoose.Schema.Types.ObjectId,
    ref: "User",
    required: true,
  },
  check_in_date: { type: String, require: true }, // ngày giờ xem phòng
  status: { type: Number, require: true, default: 0 }, // 0 đang xử lý, 1 đã duyệt, 2 đã xem, 3 đã huỷ
  created_at: { type: Date, default: Date.now },
});
module.exports = mongoose.model("Booking", Booking);
