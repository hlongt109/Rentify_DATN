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

// {
//     "_id": "675a42873b4922d46354e9f6",
//     "user_id": {
//         "_id": "67594fab4f65cce59df450aa",
//         "name": "Hoang Long",
//         "phoneNumber": "0869070888"
//     },
//     "room_id": {
//         "_id": "6752eb6c7f9f19f2287915d8",
//         "building_id": "6752eb207f9f19f2287915d1",
//         "room_name": "P101",
//         "room_type": "single",
//         "description": "Phòng đẹp, đầy đủ",
//         "price": 3800000,
//         "decrease": 0,
//         "size": "35",
//         "video_room": [
//             ""
//         ],
//         "photos_room": [
//             "public\\imageRooms\\photos_room-1734020984182z6096254917691_300720cb49bfc0f951d5c27a9021d658.jpg",
//             "public\\imageRooms\\photos_room-1734020984185z6096254929240_f14cd77fb83143aee5aa841c58a90f75.jpg",
//             "public\\imageRooms\\photos_room-1734020984188z6096254938860_8c5589515e5bc6c8a7aeb7ff1721050b.jpg",
//             "public\\imageRooms\\photos_room-1734020984189z6096254944065_420e98a24c344f4767f808ed4f919cd9.jpg"
//         ],
//         "service": [
//             "6752eabf7f9f19f2287915c4",
//             "6752eacb7f9f19f2287915c7",
//             "6752ead67f9f19f2287915ca"
//         ],
//         "amenities": [
//             "Vệ sinh khép kín",
//             "Ra vào vân tay",
//             "Không chung chủ"
//         ],
//         "limit_person": 4,
//         "status": 0,
//         "created_at": "2024-12-06T12:17:48.376Z",
//         "updated_at": "2024-12-12T16:29:44.219Z",
//         "__v": 0
//     }