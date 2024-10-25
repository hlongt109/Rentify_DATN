var express = require('express');
var router = express.Router();
const Payment = require('../../models/Payment');

// #1. Tạo hóa đơn POST: http://localhost:3000/api/payments
router.post('/payments', async (req, res) => {
    const { user_id, invoice_id, amount, payment_date, payment_method } = req.body;

    try {
        const newPayment = new Payment({
            user_id,
            invoice_id,
            amount,
            payment_date,
            payment_method,
            created_at: new Date().toISOString()
        });

        await newPayment.save();
        res.status(201).json({ message: 'Invoice created successfully!', payment: newPayment });
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'Failed to create invoice.' });
    }
});

// #2. Lấy danh sách tất cả hóa đơn GET: http://localhost:3000/api/payments
router.get('/payments', async (req, res) => {
    try {
        const payments = await Payment.find({});
        res.status(200).json(payments);
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'Failed to retrieve invoices.' });
    }
});

// #3. Tìm kiếm hóa đơn GET: http://localhost:3000/api/payments/search?query={keyword}
router.get('/payments/search', async (req, res) => {
    const { query } = req.query; // Lấy từ query string

    try {
        const payments = await Payment.find({
            $or: [
                { invoice_id: { $regex: query, $options: 'i' } }, // Tìm kiếm theo ID hóa đơn
                { user_id: { $regex: query, $options: 'i' } } // Tìm kiếm theo ID người dùng
            ]
        });
        res.status(200).json(payments);
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'Failed to search invoices.' });
    }
});

// #4. Xem chi tiết hóa đơn GET: http://localhost:3000/api/payments/{id}
router.get('/payments/:id', async (req, res) => {
    const { id } = req.params;

    try {
        const payment = await Payment.findById(id);
        
        if (!payment) {
            return res.status(404).json({ error: 'Invoice not found.' });
        }

        res.status(200).json(payment);
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'Failed to retrieve invoice.' });
    }
});

module.exports = router;


// data kiểm thử postman 
// {
//     "user_id": "671b0cb4f9b7a4cfce237282",
//     "invoice_id": "INV001",
//     "amount": 500000,
//     "payment_date": "2024-10-25",
//     "payment_method": "Credit Card"
// }