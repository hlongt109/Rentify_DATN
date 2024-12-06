const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const Service = new Schema({
    admin_id: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'User',
    },
    landlord_id: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'User',
    },
    name: { type: String},
    description: { type: String}, // mô tả
    price: { type: Number},
    photos: { type: [String] }, // lưu ảnh icon 
    created_at: { type: Date, default: Date.now },
    updated_at: { type: Date, default: Date.now }
});

module.exports = mongoose.model("Service", Service);