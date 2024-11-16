// api/BuildingOwner/BuildingManageApi.js
var express = require('express');
var router = express.Router();
const Building = require('../../models/Building');


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




module.exports = router;








// data kiểm thử
// {
//     "landlord_id": "1",
//     "address": "123 Đường ABC, Quận 1, TP.HCM",
//     "description": "Tòa nhà mới xây dựng.",
//     "number_of_floors": 5
// }



