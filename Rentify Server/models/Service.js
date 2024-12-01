const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const Service = new Schema({
    landlord_id: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'User',
        require: false
    },
    name: { type: String, require: true },
    description: { type: String, require: true },
    price: { type: Number, require: true },
    photos: { type: [String], required: true },
    created_at: { type: Date, default: Date.now },
    updated_at: { type: Date, default: Date.now }
});

module.exports = mongoose.model("Service", Service);