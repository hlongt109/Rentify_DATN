// const mongoose = require('mongoose');
// const Schema = mongoose.Schema;
// const Notification = new Schema({
//     user_id: {
//         type: mongoose.Schema.Types.ObjectId,
//         ref: 'User',
//         required: true
//     },
//     title: {type: String},
//     content: { type: String },
//     read_status: { type: String, enum: ['unread', 'read'] },
//     created_at: { type: String }
// })
// module.exports = mongoose.model("Notification", Notification)
const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const Notification = new Schema({
    user_id: {
        // type: mongoose.Schema.Types.ObjectId,
        // ref: 'User',
        // required: false
        type: String, require: true
    },
    title: { type: String, required: true },
    content: { type: String, required: true },
    read_status: {
        type: String,
        enum: ['unread', 'read'],
        default: 'unread'  // Đặt trạng thái mặc định là 'unread'
    },
    created_at: {
        type: Date,
        default: Date.now  // Tự động lưu thời gian tạo
    }
});

// Thêm phương thức để đánh dấu thông báo là đã đọc
Notification.methods.markAsRead = function () {
    this.read_status = 'read';
    return this.save(); // Lưu thay đổi vào cơ sở dữ liệu
};

module.exports = mongoose.model("Notification", Notification);
