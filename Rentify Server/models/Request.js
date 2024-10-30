const mongoose = require("mongoose");
const Schema = mongoose.Schema;
const Request = new Schema({
   user_id: {type: String, required: true},
   room_id: {type: String, required: true},
   request_type: {type: String}, // Loại yêu cầu (như yêu cầu sửa chữa, yêu cầu thêm dịch vụ)
   description: {type: String}, // Trạng thái của yêu cầu (đang xử lý, đã hoàn thành, đã hủy)
   status: {type: Number}, // 0. Đang xử lý, 1. Duyệt, 2. Đã hoàn thành, 3. Đã hủy
   created_at: {type: String},
   updated_at: {type: String},
})
module.exports = mongoose.model("Request",Request);