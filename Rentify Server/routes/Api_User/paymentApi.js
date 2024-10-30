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

module.exports = router;