const express = require('express');
const router = express.Router();
const mongoose = require('mongoose');
const Support = require('../../models/Support')

router.get('/support/by-building/:building_id/:status', async (req, res) => {
    try {
        const { building_id, status } = req.params;
        const supports = await Support.find({ building_id, status }).populate('building_id', 'nameBuilding').populate('room_id', 'room_name');

        if (supports.length === 0) {
            return res.status(404).json({ message: 'No support tickets found for this building_id and status' });
        }

        const formattedSupports = supports.map(support => ({
            ...support._doc,
            _id: support._id.toString(),
            user_id: support.user_id ? { ...support.user_id._doc, _id: support.user_id._id.toString() } : null,
            building_id: support.building_id ? { ...support.building_id._doc, _id: support.building_id._id.toString() } : null,
            room_id: support.room_id ? { ...support.room_id._doc, _id: support.room_id._id.toString() } : null,
        }));

        res.status(200).json(formattedSupports);
    } catch (error) {
        console.error('Error fetching supports by building_id and status:', error);
        res.status(500).json({ message: 'Internal server error', error });
    }
});
// hiển thị chi tiết theo room_id :
router.get('/supports/:support_id', async (req, res) => {
    try {
        const { support_id } = req.params;
        if (!mongoose.Types.ObjectId.isValid(support_id)) {
            return res.status(400).json({ message: 'Invalid support_id' });
        }
        const support = await Support.findById(support_id)
            .populate('user_id', 'name email')
            .populate('building_id', 'name address')
            .populate('room_id', 'room_name')
            .select('-__v')
            .lean();

        if (!support) {
            return res.status(404).json({ message: 'Support ticket not found' });
        }

        const formattedSupport = {
            ...support,
            _id: support._id.toString(),
            user_id: support.user_id ? { ...support.user_id, _id: support.user_id._id.toString() } : null,
            building_id: support.building_id ? { ...support.building_id, _id: support.building_id._id.toString() } : null,
            room_id: typeof support.room_id === 'object'
                ? { ...support.room_id, _id: support.room_id._id.toString() }
                : { _id: support.room_id.toString(), room_name: 'Unknown' },
        };

        res.status(200).json(formattedSupport);
    } catch (error) {
        console.error('Error fetching support by support_id:', error);
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