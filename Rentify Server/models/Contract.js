const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const Contract = new Schema({
    user_id: { type: String, require: true },
    room_id: { type: String, require: true },
    start_date: { type: String, require: true },
    end_date: { type: String, require: true },
    status: { type: String, require: true },
    created_at: { type: String, require: false }
})
module.exports = mongoose.model("Contract", Contract);