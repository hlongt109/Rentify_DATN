const mongoose = require("mongoose");
const Schema = mongoose.Schema;
const Account = new Schema({
  username: { type: String, require: true },
  password: { type: String, require: true },
  email: { type: String, require: true },
  phoneNumber: { type: String, require: false },
  role: {
    type: String,
    enum: ["admin", "landlord", "staffs", "user"],
    default: "user",
  },
  name: { type: String, require: true },
  dob: { type: String, require: false }, // ngày sinh
  gender: { type: String, require: false }, //giới tính
  address: { type: String, require: false },
  profile_picture_url: { type: String, require: false },
  created_at: { type: String, require: false },
  updated_at: { type: String, require: false },
});
module.exports = mongoose.model("Account", Account);
