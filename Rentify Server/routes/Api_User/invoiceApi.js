const express = require('express');
const router = express.Router();
const Invoice = require('../../models/Invoice')

// Lấy hoá đơn theo trạng thái payment_status và user_id
router.get('/get-invoices-by-status/:user_id/:status', async (req, res) => {
    try {
        const invoices = await Invoice.find({ 
            user_id: req.params.user_id,
            payment_status: req.params.status 
        }).populate({
            path: 'room_id',
            select: 'room_name room_type price'
        });
        res.status(200).json(invoices);
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
});

// Hiển thị chi tiết hoá đơn
router.get('/get-detail-invoice/:id', async (req, res) => {
    try {
        const { id } = req.params;
        const data = await Invoice.findById(id)
        if (data == null) {
            return res.status(404).json({ message: 'Cannot find invoice' })
        }
        res.status(200).json(data);
    } catch (err) {
        res.status(400).json({
            message: error.message
        })
    }
});

module.exports = router;