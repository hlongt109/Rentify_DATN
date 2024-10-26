const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const Invoice = new Schema({
    user_id: { type: String, require: true },
    room_id: { type: String, require: true },
    description: { type: Array },
    amount: { type: Number, require: true }, // số tien
    transaction_type: { type: String, enum: ['income', 'expense'], require: true },
    due_date: { type: String}, // hạn chót
    payment_status: { type: String, enum: ['paid', 'unpaid', 'pending'] }, // đã thanh toán, chưa thanh toán, đang chờ 
    created_at: { type: String }
})
module.exports = mongoose.model("Invoice", Invoice);