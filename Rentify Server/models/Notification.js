const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const Notification = new Schema({
    user_id: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'User',
    },
    title: { type: String, required: true },
    content: { type: String, required: true },
    read_status: {
        type: String,
        enum: ['unread', 'read'],
        default: 'unread'
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
