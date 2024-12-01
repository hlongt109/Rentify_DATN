const express = require('express');
const router = express.Router();
const mongoose = require('mongoose');
const Support = require('../../models/Support')
router.get('/supports', async (req, res) => {
    try {
        const supports = await Support.find({}, { __v: 0 })
            .lean();
        const formattedSupports = supports.map(support => ({
            ...support,
            _id: { $oid: support._id.toString() },
            user_id: support.user_id ? { $oid: support.user_id.toString() } : null,
            room_id: support.room_id ? { $oid: support.room_id.toString() } : null,
            landlord_id: support.landlord_id ? { $oid: support.landlord_id.toString() } : null,
        }));
        res.status(200).json(formattedSupports);
    } catch (error) {
        console.error('Error fetching supports:', error);
        res.status(500).json({ message: 'Internal server error', error });
    }
});
router.get('/ListRoom/:landlord_id', async (req, res) => {
    try {
        const { landlord_id } = req.params;
        if (!mongoose.Types.ObjectId.isValid(landlord_id)) {
            return res.status(400).json({ message: 'Invalid landlord_id' });
        }
        const rooms = await Support.find({ landlord_id }, { room_id: 1, _id: 0 })
            .lean();
        const uniqueRooms = [...new Set(rooms.map(room => room.room_id?.toString()).filter(Boolean))];
        res.status(200).json({ landlord_id, room_ids: uniqueRooms });
    } catch (error) {
        console.error('Error fetching rooms by landlord_id:', error);
        res.status(500).json({ message: 'Internal server error', error });
    }
});
// hiển thị chi tiết theo room_id :
router.get('/supports/:room_id', async (req, res) => {
    try {
        const { room_id } = req.params;
        if (!mongoose.Types.ObjectId.isValid(room_id)) {
            return res.status(400).json({ message: 'Invalid room_id' });
        }

        // Find all support entries for the given room_id
        const supports = await Support.find({ room_id }, { __v: 0 })
            .populate('user_id', 'name email') // Populate user details
            .populate('landlord_id', 'name email') // Populate landlord details
            .lean();

        if (!supports.length) {
            return res.status(404).json({ message: 'No supports found for the given room_id' });
        }

        const formattedSupports = supports.map(support => ({
            ...support,
            _id: { $oid: support._id.toString() },
            user_id: support.user_id ? { ...support.user_id, _id: { $oid: support.user_id._id.toString() } } : null,
            landlord_id: support.landlord_id
                ? { ...support.landlord_id, _id: { $oid: support.landlord_id._id.toString() } }
                : null,
            room_id: support.room_id ? { $oid: support.room_id.toString() } : null,
        }));

        res.status(200).json(formattedSupports);
    } catch (error) {
        console.error('Error fetching supports by room_id:', error);
        res.status(500).json({ message: 'Internal server error', error });
    }
});

module.exports = router;