const mongoose = require("mongoose");
const Schema = mongoose.Schema;
const Payment = new Schema({
    user_id: {
        type: mongoose.Schema.Types.ObjectId,
        ref: "User",
        require: true
    },
    invoice_id: {
        type: mongoose.Schema.Types.ObjectId,
        ref: "Invoice",
        require: true
    },
    amount: { type: Number, require: true }, // số tiền 
    payment_date: { type: String, require: true },
    payment_method: { type: String, enum: ['transfer', 'cash'], require: true },
    status: { type: Number }, // 0 chưa thánh toán, 1 đã thanh toán
    created_at: { type: String },
    updated_at: { type: String }
})
module.exports = mongoose.model("Payment", Payment);
