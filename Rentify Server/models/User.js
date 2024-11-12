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
  created_at: { type: String },
  updated_at: { type: String },
});

// Phương thức để tạo token
// User.methods.generateAuthToken = function () {
//   const user = this;  // Dữ liệu người dùng hiện tại
//   const payload = { id: user._id, role: user.role }; // Payload của token (ID và role người dùng)
//   const token = jwt.sign(payload, 'hoan', { expiresIn: '1000h' }); // Tạo token với thời gian hết hạn 10 giờ
//   return token;
// };
module.exports = mongoose.model("User", User);
