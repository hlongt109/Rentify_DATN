const express = require('express');
const router = express.Router();
const Payment = require("../../models/Payment");

// Lấy danh sách tất cả thanh toán
router.get('/list', async (req, res) => {
    try {
        const payments = await Payment.find(); // Lấy tất cả thanh toán từ DB
        res.json(payments);
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
});

// Tìm kiếm thanh toán
router.get('/search', async (req, res) => {
    const { user_id, invoice_id } = req.query;

    try {
        const query = {};
        if (user_id) query.user_id = user_id;
        if (invoice_id) query.invoice_id = invoice_id;

        const payments = await Payment.find(query);
        res.json(payments);
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
});

// Xem chi tiết thanh toán theo ID
router.get('/detail/:id', async (req, res) => {
    try {
        const payment = await Payment.findById(req.params.id);
        if (!payment) {
            return res.status(404).json({ message: "Payment not found" });
        }
        res.json(payment);
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
});

module.exports = router;