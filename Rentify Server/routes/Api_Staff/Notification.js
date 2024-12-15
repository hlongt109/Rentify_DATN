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
    // Đếm số lượng thông báo unread
    const unreadCount = await Notification.countDocuments({
        user_id: userId,
        read_status: 'unread',
    });
        return res.status(200).json({
            status: 200,
            message: "Lấy danh sách thông báo thành công",
            data: notifications,
            unreadCount: unreadCount
        });

    } catch (error) {
        return res.status(500).json({
            success: false,
            message: "Lỗi server: " + error.message
        });
    }
});
router.get('/count-unread/:userId', async (req, res) => {
    try {
        const userId = req.params.userId;

        // Đếm số lượng thông báo với trạng thái 'unread' cho user_id cụ thể
        const unreadCount = await Notification.countDocuments({
            user_id: userId,
            read_status: 'unread',
        });

        res.status(200).json({
            status: 200,
            message: 'Số lượng thông báo chưa đọc',
            count: unreadCount,
        });
    } catch (error) {
        console.error('Lỗi khi đếm thông báo unread:', error);
        res.status(500).json({
            status: 500,
            message: 'Lỗi hệ thống',
            error: error.message,
        });
    }
});

// API cập nhật tất cả thông báo thành read
router.put('/mark-all-read/:userId', async (req, res) => {
    try {
        const userId = req.params.userId;

        const result = await Notification.updateMany(
            { user_id: userId, read_status: 'unread' },
            { $set: { read_status: 'read' } }
        );

        res.status(200).json({
            status: 200,
            message: 'Đã cập nhật tất cả thông báo thành trạng thái read',
            modifiedCount: result.modifiedCount,
        });
    } catch (error) {
        console.error('Lỗi khi cập nhật thông báo thành read:', error);
        res.status(500).json({
            status: 500,
            message: 'Lỗi hệ thống',
            error: error.message,
        });
    }
});
module.exports = router;