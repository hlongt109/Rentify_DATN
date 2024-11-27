const mongoose = require("mongoose");
const Schema = mongoose.Schema;

const serviceFeeSchema = new mongoose.Schema(
  {
    name: { type: String, required: true },
    price: { type: Number, required: true },
  },
  { _id: false } // Tắt tự động tạo _id cho từng phần tử trong mảng
);

const Building = new Schema({
  landlord_id: {
    type: mongoose.Schema.Types.ObjectId,
    ref: "User",
    required: true,
  },
  manager_id: { // id nhân viên quản lý tòa nhà 
    type: mongoose.Schema.Types.ObjectId,
    ref: 'User',
    required: true
  },
  service: [{ // toà nhà có nhưng vụ gì add vào đây
    type: mongoose.Schema.Types.ObjectId,
    ref: "Service",
  }],
  serviceFees: [serviceFeeSchema],
  nameBuilding: { type: String },
  address: { type: String },
  description: { type: String },
  number_of_floors: { type: Number },
  created_at: { type: String },
  updated_at: { type: String },
});

module.exports = mongoose.model("Building", Building);