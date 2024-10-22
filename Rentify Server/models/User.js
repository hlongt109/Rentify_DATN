const mongoose = require('mongoose')
const Schema = mongoose.Schema
const User = new Schema({
    username: { type: String, require: true },
    password: { type: String, require: true },
    email: { type: String, require: true },
    phoneNumber: { type: String, require: true },
    role: { type: String, enum: ['admin', 'landlord', 'staffs', 'user'], default: 'user' },
    name: { type: String, require: true },
    dob: { type: String, require: true },// ngày sinh
    gender: { type: String, require: true },//giới tính
    address: { type: String, require: true },
    profile_picture_url: { type: String, require: true },
    created_at: { type: String, require: false },
    updated_at: { type: String, require: false }
})
module.exports = mongoose.model("User", User);