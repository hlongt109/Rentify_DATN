const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const Building = new Schema({
    landlord_id: { 
        type: mongoose.Schema.Types.ObjectId,
        ref: 'Landlord',
        required: true
    },
    manager_id: { 
        type: mongoose.Schema.Types.ObjectId,
        ref: 'Manager',
        required: true
    },
    address: { type: String },
    description: { type: String }, 
    number_of_floors: { type: Number},
    created_at: { type: String},
    updated_at: { type: String}
})
module.exports = mongoose.model("Building", Building);