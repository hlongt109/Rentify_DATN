const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const Building = new Schema({
    landlord_id: { type: String, require: true },
    address: { type: String, require: true },
    description: { type: String, require: true }, //môt tả
    number_of_floors: { type: String, require: true },
    created_at: { type: String, require: false },
    updated_at: { type: String, require: false }
})
module.exports = mongoose.model("Building", Building);