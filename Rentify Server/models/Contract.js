const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const Contract = new Schema({
    user_id: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'User',
        required: true
    },
    room_id: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'Room',
        required: true
    },
    content: {type: String, require: true},
    start_date: { type: String, require: true },
    end_date: { type: String, require: true },
    status: { type: String, require: true },
    created_at: { type: String }
})
module.exports = mongoose.model("Contract", Contract);