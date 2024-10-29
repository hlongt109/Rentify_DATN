const express = require('express');
const router = express.Router();
const Invoice = require("../../models/Invoice");
// Liệt kê tất cả hóa đơn
router.get('/list', async (req, res) => {
    try {
        const invoices = await Invoice.find(); // Lấy tất cả hóa đơn
        res.json(invoices);
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
});

// router.post('/add', async (req, res) => {
//     const invoice = new Invoice({
//         user_id: req.body.user_id,
//         room_id: req.body.room_id,
//         description: req.body.description || [],
//         amount: req.body.amount,
//         due_date: req.body.due_date,
//         payment_status: req.body.payment_status || "pending",
//         created_at: new Date().toISOString()
//     });

//     try {
//         const savedInvoice = await invoice.save();
//         res.status(201).json(savedInvoice);
//     } catch (error) {
//         res.status(400).json({ message: error.message });
//     }
// });
// Xem chi tiết hóa đơn theo ID
router.get('/detail/:id', async (req, res) => {
    try {
        const invoice = await Invoice.findById(req.params.id);
        if (!invoice) {
            return res.status(404).json({ message: "Invoice not found" });
        }
        res.json(invoice);
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
});
// Tìm kiếm hóa đơn
router.get('/search', async (req, res) => {
    const { user_id, room_id, payment_status } = req.query;
    try {
        const query = {};
        if (user_id) {
            query.user_id = user_id;
        }
        if (room_id) {
            query.room_id = room_id;
        }
        if (payment_status) {
            query.payment_status = payment_status;
        }

        const invoices = await Invoice.find(query);
        res.json(invoices);
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
});

module.exports = router;
