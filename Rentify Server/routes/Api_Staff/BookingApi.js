var express = require('express');
var router = express.Router();
const mongoose = require('mongoose');
const Building = require('../../models/Building');
const Room = require("../../models/Room");
const Booking = require("../../models/Booking");
const handleServerError = require("../../utils/errorHandle");

router.get('/bookings-list/:manager_id', async (req, res) => {
    try {
        const { manager_id } = req.params;

        const bookings = await Booking.find({ manager_id })
            .populate('building_id')
            .populate('room_id')
            .populate({
                path: 'user_id',
                select: 'name phoneNumber',
            });

        if (!bookings || bookings.length === 0) {
            return res.status(401).json({ message: "No bookings found for the provided manager ID." });
        }

        res.status(200).json(bookings)
    } catch (error) {
        handleServerError(res, error)
    }
})

router.get('/bookings-details/:id', async (req, res) => {
    try {
        const { id } = req.params;

        const booking = await Booking.findById(id)
            .populate('building_id')
            .populate('room_id')
            .populate({
                path: 'user_id',
                select: 'name phoneNumber',
            });

        if (!booking) {
            return res.status(401).json({ message: "No booking found" });
        }

        res.status(200).json(booking)
    } catch (error) {
        handleServerError(res, error)
    }
})

router.put('/bookings-status/:id', async (req, res) => {
    try {
        const { id } = req.params;
        const { status } = req.body;

        const updatedBooking = await Booking.findByIdAndUpdate(
            id,
            { status },
            { new: true }
        );

        if (!updatedBooking) {
            return res.status(404).json({
                message: "Không tìm thấy booking"
            });
        }

        res.status(200).json({
            status: 200,
            message: "Cập nhật trạng thái booking thành công",
            booking: updatedBooking
        });
    } catch (error) {
        handleServerError(res, error)
    }
})

module.exports = router;