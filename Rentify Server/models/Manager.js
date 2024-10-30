const mongoose = require('mongoose');

const Schema = mongoose.Schema;

const ManagerSchema = new Schema({
    user_id: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'User',
        required: true
    },
    building_id: { 
        type: mongoose.Schema.Types.ObjectId,
        ref: "Building",
        required: true 
    },
    contact_number: { type: String, required: true },
    email: { type: String, required: true },
    created_at: { type: String },
    updated_at: { type: String }
});

module.exports = mongoose.model('Manager', ManagerSchema);