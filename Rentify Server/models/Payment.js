const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const Payment = new Schema({
    user: { type: String, require: true },
    invoice_id: { type: String, require: true },
    amount: { type: Number, require: true }, // số lượng
    payment_date: { type: String, require: true },
    payment_method: { type: String, require: true },
    created_at: { type: String, require: false }
})
module.exports = mongoose.model("Payment", Payment);