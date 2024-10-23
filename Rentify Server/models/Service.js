const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const Service = new Schema({
    name: { type: String, require: true },
    description: { type: String, require: true },// mô tả
    price: { type: Number, require: true },
    created_at: { type: String },
    updated_at: { type: String }
})
module.exports = mongoose.model("Service", Service);