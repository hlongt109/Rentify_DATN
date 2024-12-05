const mongoose = require("mongoose");
const Schema = mongoose.Schema;
const Invoice = new Schema({
    user_id: { // id nhân viên 
        type: mongoose.Schema.Types.ObjectId,
        ref: 'User',
    },
    building_id: { 
        type: mongoose.Schema.Types.ObjectId,
        ref: "Building",
    },
    room_id: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'Room',
    },
    description: [{
        service_name: { type: String }, // tên dịch vụ (ví dụ: điện, nước)
        quantity: { type: Number }, // số lượng sử dụng (ví dụ: số kWh, m3 nước)
        price_per_unit: { type: Number }, // giá mỗi đơn vị
        total: { type: Number } // tổng chi phí cho dịch vụ đó
    }],
    describe: {type: String},
    type_invoice: {type: String, enum: ["rent","electric", "water", "internet", "salary", "maintain"]},
    amount: { type: Number, require: true }, // số tien
    transaction_type: { type: String, enum: ['income', 'expense'], require: true },
    due_date: { type: String }, // hạn chót
    payment_status: { type: String, enum: ["wait" ,"paid", "unpaid"] },
    created_at: { type: String }
})
module.exports = mongoose.model("Invoice", Invoice);


