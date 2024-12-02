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
// update 
router.put('/supports/:id', async (req, res) => {
    try {
        const { id } = req.params;
        const updateData = req.body;

        // Validate ID format
        if (!mongoose.Types.ObjectId.isValid(id)) {
            return res.status(400).json({ message: 'Invalid support ticket ID' });
        }

        // Validate required fields
        if (!updateData.title_support || !updateData.content_support) {
            return res.status(400).json({ message: 'Title and content are required' });
        }

        // Update the support entry
        const updatedSupport = await Support.findByIdAndUpdate(
            id,
            {
                ...updateData,
                updated_at: new Date().toISOString(), // Automatically set updated_at
            },
            { new: true } // Return the updated document
        ).populate('user_id', 'name email')
         .populate('building_id', 'name address')
         .lean();

        // If no support entry is found, return 404
        if (!updatedSupport) {
            return res.status(404).json({ message: 'Support ticket not found' });
        }

        // Format the response
        const formattedSupport = {
            ...updatedSupport,
            _id: updatedSupport._id.toString(),
            user_id: updatedSupport.user_id ? { ...updatedSupport.user_id, _id: updatedSupport.user_id._id.toString() } : null,
            building_id: updatedSupport.building_id ? { ...updatedSupport.building_id, _id: updatedSupport.building_id._id.toString() } : null,
            room_id: updatedSupport.room_id ? updatedSupport.room_id.toString() : null,
        };

        // Return the updated support ticket
        res.status(200).json(formattedSupport);
    } catch (error) {
        console.error('Error updating support ticket:', error);
        res.status(500).json({ message: 'Internal server error', error });
    }
});
module.exports = router;