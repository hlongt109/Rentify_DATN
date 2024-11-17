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
    description: [{
        service_name: { type: String, required: true }, // tên dịch vụ (ví dụ: điện, nước)
        quantity: { type: Number, required: true }, // số lượng sử dụng (ví dụ: số kWh, m3 nước)
        price_per_unit: { type: Number, required: true }, // giá mỗi đơn vị
        total: { type: Number, required: true } // tổng chi phí cho dịch vụ đó
    }],
    amount: { type: Number, require: true }, // số tien
    transaction_type: { type: String, enum: ['income', 'expense'], require: true },
    due_date: { type: String }, // hạn chót
    payment_status: { type: String },
    created_at: { type: String }
})
module.exports = mongoose.model("Invoice", Invoice);