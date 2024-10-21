const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const Support = new Schema({
    user_id: { type: String, require: false },
    room_id: { type: String, require: false },
    title_support: { type: String, require: true },
    content_support: { type: String, require: true },
    image: { type: String, require: true },
    status: { type: String, require: true },
    created_at: { type: String, require: false },
    updated_at: { type: String, require: false }
})
module.exports = mongoose.model("Support", Support);