var express = require('express');
var router = express.Router();

const mongoose = require('mongoose');

const Booking = require("../../models/Booking");
const Room = require("../../models/Room");
const Building = require("../../models/Building")


//lay danh sach
router.get("/booking/list/:id", async (req, res) => {
    try {
        const landlordId = req.params.id;
        console.log('Landlord ID nhận được từ client:', landlordId);
        if (!mongoose.Types.ObjectId.isValid(landlordId)) {
            return res.status(400).json({ message: "Invalid landlord_id format" });
        }

        // Tìm tất cả các tòa nhà liên kết với landlord_id
        const buildings = await Building.find({ landlord_id: landlordId });
        if (buildings.length === 0) {
            return res.status(404).json({ message: "Không tìm thấy tòa nhà cho landlord này." });
        }
        // Lấy danh sách building_id từ các tòa nhà
        const buildingIds = buildings.map(building => building._id);

        // Tìm tất cả các yêu cầu hỗ trợ có building_id trong danh sách buildingIds
        const data = await Booking.find({ building_id: { $in: buildingIds } });


        res.json({ data });

    } catch (error) {
        console.error("Error fetching room list:", error.message);
        return res.status(500).json({ message: "Internal Server Error" });
    }

})


// api sua
router.put("/booking/update/:id", async (req, res) => {
    const { status } = req.body;
    const userId = req.params.id;
    if (!mongoose.Types.ObjectId.isValid(userId)) {
        return res.status(400).json({ message: 'ID không hợp lệ.' });
    }

    const support = await Booking.findById(userId);
    if (!support) {
        return res.status(404).json({
            status: 404,
            message: 'Yêu cầu không tìm thấy',
            data: []
        });
    }
    support.status = status;
    await support.save();
    res.json({
        status: 200,
        message: 'Cập nhật thành công',
        data: support
    })
})

module.exports = router;