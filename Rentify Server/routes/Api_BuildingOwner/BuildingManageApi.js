// api/BuildingOwner/BuildingManageApi.js
var express = require('express');
var router = express.Router();
const mongoose = require('mongoose');
const Building = require('../../models/Building');
const Room = require('../../models/Room')
//const upload = require('../../config/common/uploadImageRoom')
const fs = require('fs');
const path = require('path');

// #1. Thêm tòa nhà (POST): http://localhost:3000/api/buildings
router.post('/buildings', async (req, res) => {
    const { landlord_id, address, description, number_of_floors } = req.body;


    try {
        const newBuilding = new Building({
            landlord_id,
            address,
            description,
            number_of_floors,
            created_at: new Date().toISOString(),
            updated_at: new Date().toISOString(),
        });


        await newBuilding.save();
        res.status(201).json({ message: 'Building added successfully!', building: newBuilding });
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'Failed to add building.' });
    }
});


// #2. Lấy danh sách tất cả tòa nhà
router.get('/buildings/:id', async (req, res) => {
    const userId = req.params.id;
    try {
        const buildings = await Building.find({ landlord_id: userId })
            .populate('manager_id', 'username phoneNumber');

        res.status(200).json(buildings);
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'Failed to retrieve buildings.' });
    }
});



// #3. Xem chi tiết tòa nhà (GET): http://localhost:3000/api/buildings/{id}
router.get('/buildings/:id', async (req, res) => {
    const { id } = req.params;


    try {
        const building = await Building.findById(id);
       
        if (!building) {
            return res.status(404).json({ error: 'Building not found.' });
        }


        res.status(200).json(building);
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'Failed to retrieve building.' });
    }
});


// #4. Chỉnh sửa tòa nhà (PUT): http://localhost:3000/api/buildings/{id}
router.put('/buildings/:id', async (req, res) => {
    const { id } = req.params;
    const { address, description, number_of_floors } = req.body;


    try {
        const updatedBuilding = await Building.findByIdAndUpdate(
            id,
            { address, description, number_of_floors, updated_at: new Date().toISOString() },
            { new: true }
        );


        if (!updatedBuilding) {
            return res.status(404).json({ error: 'Building not found.' });
        }


        res.status(200).json({ message: 'Building updated successfully!', building: updatedBuilding });
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'Failed to update building.' });
    }
});


// #5. Xóa tòa nhà (DELETE): http://localhost:3000/api/buildings/{id}
router.delete('/buildings/:id', async (req, res) => {
    const { id } = req.params;


    try {
        const deletedBuilding = await Building.findByIdAndDelete(id);


        if (!deletedBuilding) {
            return res.status(404).json({ error: 'Building not found.' });
        }


        res.status(200).json({ message: 'Building deleted successfully!', building: deletedBuilding });
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'Failed to delete building.' });
    }
});
// API lấy danh sách dịch vụ của một tòa nhà cụ thể
router.get('/building/:id/services', async (req, res) => {
    const { id } = req.params;

    // Kiểm tra ID là ObjectId hợp lệ
    if (!mongoose.Types.ObjectId.isValid(id)) {
        return res.status(400).json({ error: 'Invalid building ID.' });
    }

    try {
        // Tìm tòa nhà theo ID và populate dịch vụ
        const building = await Building.findById(id)
            .populate('service', 'name');

        // Nếu không tìm thấy tòa nhà
        if (!building) {
            return res.status(404).json({ error: 'Building not found.' });
        }

        // Trả về danh sách dịch vụ của tòa nhà
        res.status(200).json(building.service);
    } catch (error) {
        console.error('Error fetching building services:', error.message);
        res.status(500).json({ error: 'Failed to fetch building services. Please try again later.' });
    }
});



module.exports = router;








// data kiểm thử
// {
//     "landlord_id": "1",
//     "address": "123 Đường ABC, Quận 1, TP.HCM",
//     "description": "Tòa nhà mới xây dựng.",
//     "number_of_floors": 5
// }



