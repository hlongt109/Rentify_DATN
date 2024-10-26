const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const Notification = new Schema({
    user_id: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'User',
        required: true
    },
    title: {type: String},
    content: { type: String },
    status: {type: Number},
    read_status: { type: String },
    created_at: { type: String }
})
module.exports = mongoose.model("Notification", Notification)