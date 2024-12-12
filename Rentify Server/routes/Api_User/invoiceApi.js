const express = require('express');
const router = express.Router();
const Invoice = require('../../models/Invoice')
const Upload = require('../../config/common/upload')

// Lấy hoá đơn theo room_id và trạng thái payment_status
router.get('/get-invoices-by-room-and-status/:room_id/:status', async (req, res) => {
    try {
        const invoices = await Invoice.find({ 
            room_id: req.params.room_id,
            payment_status: req.params.status 
        }).populate({
            path: 'room_id',
            select: 'room_name room_type price sale'
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
            message: err.message
        })
    }
});

// Cập nhật ảnh thanh toán của người dùng
router.put('/update-payment-image/:id', Upload.single('image'), async (req, res) => {
    try {
        const { id } = req.params;
        const image_paymentofuser = req.file.path;

        const invoice = await Invoice.findByIdAndUpdate(
            id,
            { image_paymentofuser, payment_status: 'wait' },
            { new: true }
        );

        if (!invoice) {
            return res.status(404).json({ message: 'Cannot find invoice' });
        }

        res.status(200).json({
            message: 'Image updated successfully and status updated to wait',
            invoice
        });
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
});

// Cập nhật trạng thái hoá đơn thành 'wait'
const mongoose = require('mongoose');

router.put('/update-status-to-wait/:id', async (req, res) => {
    try {
        const { id } = req.params;

        // Kiểm tra nếu id không phải ObjectId hợp lệ
        if (!mongoose.Types.ObjectId.isValid(id)) {
            return res.status(400).json({ message: 'Invalid invoice ID' });
        }

        const invoice = await Invoice.findByIdAndUpdate(
            id,
            { payment_status: 'wait' },
            { new: true }
        );

        if (!invoice) {
            return res.status(404).json({ message: 'Cannot find invoice' });
        }

        res.status(200).json({
            message: 'Status updated to wait successfully',
            invoice
        });
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
});


module.exports = router;