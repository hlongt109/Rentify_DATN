const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const Service = new Schema({
    user_id: { type: String, require: true },
    name: { type: String, require: true },
    description: { type: String, require: true },// mô tả
    price: { type: Number, require: true },
    created_at: { type: String, require: false },
    updated_at: { type: String, require: false }
})
module.exports = mongoose.model("Service", Service);