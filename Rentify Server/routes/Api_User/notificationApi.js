const express = require('express');
const router = express.Router();
const Notification = require('../../models/Notification'); // Giả sử bạn có model Notification

// API tạo thông báo
router.post('/create-notification', async (req, res) => {
    try {
        const { user_id, title, content } = req.body;

        // Kiểm tra xem các trường bắt buộc có được cung cấp không
        if (!user_id || !title || !content) {
            return res.status(400).json({ message: 'Thiếu thông tin cần thiết' });
        }

        // Tạo thông báo mới
        const newNotification = new Notification({
            user_id,
            title,
            content,
            created_at: new Date().toISOString() // Thời gian tạo
        });

        // Lưu thông báo vào cơ sở dữ liệu
        await newNotification.save();

        res.status(201).json({ message: 'Thông báo đã được tạo thành công', notification: newNotification });
    } catch (error) {
        console.error(error);
        res.status(500).json({ message: 'Lỗi hệ thống', error: error.message });
    }
});

// API lấy danh sách thông báo theo userId
router.get("/get-by-user/:userId", async (req, res) => {
    try {
        const userId = req.params.userId;

        const notifications = await Notification.find({ user_id: userId })
            .sort({ created_at: -1 }) // Sắp xếp theo thời gian mới nhất
            .populate("user_id", "_id name email") // Lấy thêm thông tin người dùng nếu cần

        return res.status(200).json({
            status: 200,
            message: "Lấy danh sách thông báo thành công",
            data: notifications
        });

    } catch (error) {
        return res.status(500).json({
            success: false,
            message: "Lỗi server: " + error.message
        });
    }
});

module.exports = router;