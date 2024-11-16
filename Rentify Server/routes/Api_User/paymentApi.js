const express = require('express');
const Payment = require('../../models/Payment');

const router = express.Router();

// Hiển thị danh sách thanh toán theo user_id người dùng
router.get('/get-list-payments/:user_id', async (req, res) => {
    try {
        const payments = await Payment.find({ user_id: req.params.user_id });
        res.status(200).json(payments);
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
});

// Hiển thị chi tiết thanh toán theo id
router.get('/payment-details/:id', async (req, res) => {
    try {
        const payment = await Payment.findById(req.params.id)
            .populate('user_id')
            .populate('invoice_id')
        if (!payment) {
            return res.status(404).json({ message: 'Payment not found' });
        }
        res.status(200).json(payment);
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
});

// Cập nhật trạng thái thanh toán
router.put('/update-payment-status/:id', async (req, res) => {
    try {
        const payment = await Payment.findById(req.params.id);
        if (!payment) {
            return res.status(404).json({ message: 'Payment not found' });
        }

        payment.status = 1; // Change status to 1
        await payment.save();

        res.status(200).json({ message: 'Payment status updated successfully', payment });
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
});

// create
// notification_api.js
router.post("/api/post/notifications", async (req, res) => {
    console.log("Request Body:", req.body);
    try {
        const data = req.body;
        const { user_id } = data; // Đọc user_id từ request body, không phải query

        if (!user_id) {
            return res.status(400).json({
                "status": 400,
                "message": "user_id bị bỏ trống",
            });
        }

        const newNotification = new Notification({
            user_id: user_id,
            title: data.title,
            content: data.content,
            status: data.status,
            read_status: 'unread',
            created_at: getFormattedDate()
        });

        const result = await newNotification.save();
        if (result) {
            res.json({
                "status": 200,
                "message": "Create notification successfully",
                "data": result
            });
        } else {
            res.json({
                "status": 400,
                "message": "Error, Create notification failed",
                "data": []
            });
        }
    } catch (error) {
        handleServerError(req, res);
    }
});

module.exports = router;