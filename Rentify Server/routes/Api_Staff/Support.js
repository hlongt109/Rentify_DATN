const express = require('express');
const router = express.Router();
const mongoose = require('mongoose');
const Support = require('../../models/Support')

// hiển thị chi tiết theo room_id :
router.get('/supports/:room_id', async (req, res) => {
    try {
        const { room_id } = req.params;

        // Validate room_id format
        if (!mongoose.Types.ObjectId.isValid(room_id)) {
            return res.status(400).json({ message: 'Invalid room_id' });
        }

        // Find all support entries for the given room_id and populate related fields
        const supports = await Support.find({ room_id })
            .populate('user_id', 'name email') // Populate user details
            .populate('building_id', 'name address') // Populate building details
            .select('-__v') // Exclude the version key (__v)
            .lean(); // Use lean to return plain JavaScript objects instead of Mongoose documents

        // If no support tickets are found, return a 404 response
        if (supports.length === 0) {
            return res.status(404).json({ message: 'No support tickets found for this room_id' });
        }

        // Format the response to have the proper ObjectId structure
        const formattedSupports = supports.map(support => ({
            ...support,
            _id: support._id.toString(),
            user_id: support.user_id ? { ...support.user_id, _id: support.user_id._id.toString() } : null,
            building_id: support.building_id ? { ...support.building_id, _id: support.building_id._id.toString() } : null,
            room_id: support.room_id.toString(),
        }));

        // Return the formatted support tickets
        res.status(200).json(formattedSupports);
    } catch (error) {
        console.error('Error fetching supports by room_id:', error);
        res.status(500).json({ message: 'Internal server error', error });
    }
});


module.exports = router;