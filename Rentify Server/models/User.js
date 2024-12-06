const mongoose = require("mongoose");
const Schema = mongoose.Schema;
const jwt = require('jsonwebtoken');
const User = new Schema({
  username: { type: String },
  password: { type: String },
  email: { type: String },
  phoneNumber: { type: String },
  role: {
    type: String,
    enum: ["admin", "landlord", "staffs", "user", "ban"],
    default: "user",
  },
  name: { type: String },
  dob: { type: String }, // ngày sinh
  gender: { type: String }, //giới tính
  address: { type: String },
  profile_picture_url: { type: String },
  verified: { type: Boolean, default: true }, //false: chua xac thuc, true: da xac thuc
  landlord_id: { type: mongoose.Schema.Types.ObjectId, ref: "User", required: false }, // 
  created_at: { type: String },
  updated_at: { type: String },
  // cái này chỉ dùng cho nhân viên và chủ toà
  bankAccount: {
    bank_name: { type: String },
    bank_number: { type: Number },
    qr_bank: { type: Array },
    username: { type: String }
  }
});
module.exports = mongoose.model("User", User);