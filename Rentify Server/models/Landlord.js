const { type } = require('express/lib/response')
const mongoose = require('mongoose')
const Schema = mongoose.Schema
const Landlord = new Schema({
    user_id: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'users',
        required: true
    },
    building_id: { 
        type: mongoose.Schema.Types.ObjectId,
        ref: "Building",
        required: true 
    },
    company_name: { type: String},
    contact_number: { type: String},
    email: { type: String},
    created_at: { type: String },
    updated_at: { type: String }
})

module.exports = mongoose.model("Landlord", Landlord);