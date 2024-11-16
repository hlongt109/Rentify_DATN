const { type } = require('express/lib/response');
const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const Report = new Schema({  // bảng hỏng hóc 
    user_id: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'User',
        required: true
    },
    type: {type: String, enum: ["room", "post", "service"], require: true},
    id_problem: { type: String, require: true },
    title_support: { type: String, require: true },
    content_support: { type: String, require: true },
    image: { type: Array },
    status: { type: Number, enum: [0, 1], default: 0 },
    created_at: { type: String },
    updated_at: { type: String }
})
module.exports = mongoose.model("Report", Report);