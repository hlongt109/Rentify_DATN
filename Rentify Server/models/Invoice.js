const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const Invoice = new Schema({
    user_id: { type: String, require: true },
    room_id: { type: String, require: true },
    description: { type: Array },
    amount: { type: Number, require: true }, // số tien
    due_date: { type: String}, // hạn chót
    payment_status: { type: String},
    created_at: { type: String }
})
module.exports = mongoose.model("Invoice", Invoice);