const mongoose = require("mongoose");
const Schema = mongoose.Schema;
const Request = new Schema({
   user_id: {type: String, require: true},
   room_id: {type: String, require: true},
   request_type: {type: String},
   description: {type: String},
   status: {type: Number},
   created_at: {type: String},
   updated_at: {type: String},
})
module.exports = mongoose.model("Request",Request);