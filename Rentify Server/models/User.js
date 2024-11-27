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
  verified: { type: Boolean, default: false }, //false: chua xac thuc, true: da xac thuc
  landlord_id: { type: mongoose.Schema.Types.ObjectId, ref: "User", required: false }, // 
  created_at: { type: String },
  updated_at: { type: String },
});
module.exports = mongoose.model("User", User);
