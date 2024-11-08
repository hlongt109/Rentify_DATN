const { type } = require('express/lib/response');
const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const Service = new Schema({
    landlord_id: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'Landlord',
        required: true
    },
    name: { type: String, require: true },
    description: { type: String, require: true },// mô tả
    price: { type: Number, require: true },
    photos: {type: Array},
    created_at: { type: String },
    updated_at: { type: String }
})
module.exports = mongoose.model("Service", Service);