const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const Support = new Schema({  // bảng hỏng hóc 
    user_id: { type: String, require: true },
    room_id: { type: String, require: true },
    title_support: { type: String, require: true },
    content_support: { type: String, require: true },
    image: { type: String },
    status: { type: String, require: true },//nên đổi trạng thái on hoặc off
    created_at: { type: String },
    updated_at: { type: String }
})
module.exports = mongoose.model("Support", Support);