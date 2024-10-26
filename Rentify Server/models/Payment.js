const mongoose = require('mongoose');
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
    payment_date: { type: String, enum: ['transfer', 'cash'], require: true },
    payment_method: { type: String, require: true },
    created_at: { type: String }
})
module.exports = mongoose.model("Payment", Payment);