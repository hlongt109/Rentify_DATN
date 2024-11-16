var express = require('express');
var router = express.Router();

const Notification = require("../../models/Notification")
const getFormattedDate = require('../../utils/dateUtils');

const jwt = require('jsonwebtoken');

// Middleware để xác thực và lấy user_id từ JWT token

function authenticate(req, res, next) {
    try {
        const authHeader = req.headers['authorization'];
        if (!authHeader) {
            return res.status(403).json({ message: "Không có phản hồi từ Token" });
        }

        // Lấy token từ chuỗi "Bearer <token>"
        const token = authHeader.split(' ')[1];
        if (!token) {
            return res.status(403).json({ message: "Không có phản hồi từ Token" });
        }

        // Xác thực token
        jwt.verify(token, 'hoan', (err, decoded) => {
            if (err) {
                return res.status(403).json({ message: "Lỗi xác thực Token" });
            }
            req.user_id = decoded.id; // Gán thông tin người dùng từ token vào request
            next(); // Cho phép đi tiếp
        });
    } catch (error) {
        return res.status(401).json({ message: "Lỗi xác thực", error: error.message });
    }
}

// create
router.post("/notifications", async (req, res) => {
    try {
        const data = req.body;
        console.log('Received data:', data);

        const { user_id, title, content } = data;

        // Kiểm tra nếu thiếu thông tin quan trọng
        if (!user_id || !title || !content) { //nhập ID của ng nhận
            return res.status(400).json({
                "status": 400,
                "message": "user_id, title và content là bắt buộc",
            });
        }

        // Tạo thông báo mới
        const newNotification = new Notification({
            user_id: user_id,
            title: title,
            content: content,
            // created_at: getFormattedDate()  // Tùy chỉnh hàm này nếu cần
        });

        const result = await newNotification.save();
        console.log('Notification saved:', result);

        // Trả về thông báo nếu thành công
        res.status(200).json({
            "status": 200,
            "message": "Create notification successfully",
            "data": result
        });
    } catch (error) {
        console.error('Server Error:', error);
        res.status(500).json({
            "status": 500,
            "message": "Server error",
            "error": error.message || "An error occurred"
        });
    }
});


// list
router.get("/notifications/:user_id", authenticate, async (req, res) => {
    try {
        const { user_id } = req.params;
        const data = await Notification.find({ user_id }).sort({ created_at: -1 });

        if (data.length === 0) {
            return res.status(404).json({
                "status": 404,
                "message": "Không có thông báo nào cho user_id này",
                "data": []
            });
        }

        res.status(200).json({
            "status": 200,
            "message": "Lấy thông báo thành công",
            "data": data
        });
    } catch (error) {
        console.error('Error fetching notifications:', error);
        res.status(500).json({
            "status": 500,
            "message": "Server error",
            "error": error.message || "An error occurred"
        });
    }
});

router.get('/notifications', authenticate, async (req, res) => {
    try {
        const notifications = await Notification.find({ user_id: req.user_id }).sort({ created_at: -1 });
        const unreadCount = await Notification.countDocuments({ user_id: req.user_id, read_status: false });

        res.json({ notifications, unreadCount });
    } catch (error) {
        console.error('Lỗi khi lấy thông báo:', error);
        res.status(500).json({ message: "Lỗi khi lấy thông báo từ server", error: error.message });
    }
});



// API đánh dấu thông báo là đã đọc
router.put('/notifications/:id/read', async (req, res) => {
    try {
        const notificationId = req.params.id;
        await Notification.updateOne({ _id: notificationId, user_id: req.user.id }, { read_status: 'read' });
        res.json({ message: "Notification marked as read" });
    } catch (error) {
        res.status(500).json({ message: "Error updating notification status" });
    }
});

router.patch('/notifications/:notificationId/read', authenticate, async (req, res) => {
    const { notificationId } = req.params;
    try {
        const notification = await Notification.findOne({ _id: notificationId });

        if (!notification) {
            return res.status(404).json({ message: 'Notification not found' });
        }

        if (notification.user_id.toString() !== req.user_id.toString()) {
            return res.status(403).json({ message: 'Bạn không có quyền đánh dấu thông báo này' });
        }

        notification.read_status = 'read';
        await notification.save();

        return res.json({ message: 'Notification đã được đánh dấu là đã đọc' });
    } catch (error) {
        return res.status(500).json({ message: 'Lỗi khi đánh dấu thông báo là đã đọc', error: error.message });
    }
});


// details
router.get("/notifications/:id", async (req, res) => {
    try {
        const { id } = req.params;
        const result = await Notification.findById(id);
        if (!result) {
            return res.status(404).json({
                status: 404,
                messenger: 'Notification not found'
            });
        }
        res.status(200).send(result)
    } catch (error) {
        handleServerError(req, res)
    }
})
// delete
router.delete("/notifications/:id", authenticate, async (req, res) => {
    try {
        const { id } = req.params;
        const notification = await Notification.findById(id);

        if (!notification) {
            return res.status(404).json({
                "status": 404,
                "message": "Thông báo không tìm thấy",
            });
        }

        if (notification.user_id.toString() !== req.user_id.toString()) {
            return res.status(403).json({ message: 'Bạn không có quyền xóa thông báo này' });
        }

        await notification.remove();
        res.json({
            "status": 200,
            "message": "Thông báo đã được xóa thành công",
            "data": notification
        });
    } catch (error) {
        console.error('Error deleting notification:', error);
        res.status(500).json({
            "status": 500,
            "message": "Lỗi server khi xóa thông báo",
            "error": error.message || "An error occurred"
        });
    }
});


module.exports = router;
