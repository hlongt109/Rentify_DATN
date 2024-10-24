const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const Invoice = new Schema({
    user_id: { type: String, require: true },
    room_id: { type: String, require: true },
    description: { type: Array },
    amount: { type: Number, require: true }, // số lượng
    due_date: { type: String, require: true }, // hạn chót
    payment_status: { type: String, require: true },
    created_at: { type: String, require: false }
})
module.exports = mongoose.model("Invoice", Invoice);