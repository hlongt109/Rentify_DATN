const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const Notification = new Schema({
    user_id: { type: String, require: true },
    content: { type: String, require: true },
    read_status: { type: String, require: true },
    created_at: { type: String, require: false }
})
module.exports = mongoose.model("Notification", Notification)