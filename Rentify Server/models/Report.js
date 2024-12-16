const { type } = require('express/lib/response');
const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const Report = new Schema({  // bảng hỏng hóc 
    user_id: { 
        type: mongoose.Schema.Types.ObjectId,
        ref: 'User',
    },
    type: {type: String, enum: ["room", "post", "service"]},
    id_problem: { type: String},
    title_support: { type: String},
    content_support: { type: String},
    image: { type: Array },
    status: { type: Number, enum: [0, 1], default: 0 },
    created_at: { type: String },
    updated_at: { type: String }
})
module.exports = mongoose.model("Report", Report);