const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const Invoice = new Schema({
    user_id: { // id nhân viên 
        type: mongoose.Schema.Types.ObjectId,
        ref: 'User',
    },
    building_id: { // cho hóa đơn phí dv, lương, bảo trì
        type: mongoose.Schema.Types.ObjectId,
        ref: "Building",
    },
    room_id: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'Room',
    },
    description: [{
        service_name: { type: String, required: true }, // tên dịch vụ (ví dụ: điện, nước)
        quantity: { type: Number, required: true }, // số lượng sử dụng (ví dụ: số kWh, m3 nước)
        price_per_unit: { type: Number, required: true }, // giá mỗi đơn vị
        total: { type: Number, required: true } // tổng chi phí cho dịch vụ đó
    }],
    type_invoice: {type: String, enum: ["electric", "water", "salary", "maintain"]},
    amount: { type: Number, require: true }, // số tien
    transaction_type: { type: String, enum: ['income', 'expense'], require: true },
    due_date: { type: String }, // hạn chót
    payment_status: { type: String, enum: ["paid", "unpaid"]},
    created_at: { type: String }
})
module.exports = mongoose.model("Invoice", Invoice);