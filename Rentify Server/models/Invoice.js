const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const Invoice = new Schema({
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
    description: { type: Array },
    amount: { type: Number, require: true }, // số tien
    transaction_type: { type: String, enum: ['income', 'expense'], require: true },
    due_date: { type: String }, // hạn chót
    payment_status: { type: String },
    created_at: { type: String }
})
module.exports = mongoose.model("Invoice", Invoice);