const mongoose = require("mongoose");
const Schema = mongoose.Schema;
const User = new Schema({
  username: { type: String },
  password: { type: String },
  email: { type: String },
  phoneNumber: { type: String },
  role: {
    type: String,
    enum: ["admin", "landlord", "staffs", "user"],
    default: "user",
  },
  name: { type: String },
  dob: { type: String }, // ngày sinh
  gender: { type: String }, //giới tính
  address: { type: String },
  profile_picture_url: { type: String },
  created_at: { type: String },
  updated_at: { type: String },
  verified: { type: Boolean, require: false }, //da xac nhan hay chua
});
module.exports = mongoose.model("User", User);
