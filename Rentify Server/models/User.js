const mongoose = require('mongoose')
const Schema = mongoose.Schema
const User = new Schema({
    username: {type: String},
    password: {type: String},
    email: {type: String},
    phoneNumber: {type: String},
    role: {type: Number},
    name: {type: String}
})
module.exports = mongoose.model("User",User)