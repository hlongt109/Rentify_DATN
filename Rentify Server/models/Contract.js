const mongoose = require("mongoose");
const Schema = mongoose.Schema;
const Contract = new Schema({
  manage_id: { // nếu nhân viên tạo contract thì lưu id nhân viên, chủ tòa tạo thì lưu id chủ tòa
    type: mongoose.Schema.Types.ObjectId,
    ref: "User",
  },
  building_id: {
    type: mongoose.Schema.Types.ObjectId,
    ref: "Building",
  },
  room_id: {
    type: mongoose.Schema.Types.ObjectId,
    ref: "Room",
  },
  user_id: [{
    type: mongoose.Schema.Types.ObjectId,
    ref: "User",
  }],
  photos_contract: { type: Array },
  content: { type: String },
  start_date: { type: String },
  end_date: { type: String },
  status: { type: Number, default: 0 }, //0 con hop dong, 1 het hop dong
  created_at: { type: String },
});
module.exports = mongoose.model("Contract", Contract);
