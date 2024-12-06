const express = require("express");
const router = express.Router();
const mongoose = require("mongoose");

const Room = require("../../models/Room"); // Model phòng
const Building = require("../../models/Building"); // Model tòa nhà
const Support = require("../../models/Support")

// API lấy danh sách tòa nhà theo landlord_id
// router.get("/rooms/:userId", async (req, res) => {
//     try {
//         const userId = req.params.userId;  // Lấy userId từ params
//         console.log("User ID: ", userId);

//         // Tìm các tòa nhà theo landlord_id
//         const buildings = await Building.find({ landlord_id: new mongoose.Types.ObjectId(userId) })
//             .populate("manager_id") // Lấy thêm thông tin về manager nếu cần
//             .populate("service")    // Lấy thêm thông tin về dịch vụ nếu cần
//             .populate("serviceFees"); // Lấy thêm thông tin về phí dịch vụ nếu cần

//         // Nếu không tìm thấy tòa nhà nào
//         if (buildings.length === 0) {
//             return res.status(404).json({ message: "No buildings found for this user." });
//         }

//         // Nếu có dữ liệu, trả về danh sách tòa nhà
//         return res.status(200).json(buildings);
//     } catch (error) {
//         console.error(error);
//         return res.status(500).json({ message: "Server error" });
//     }
// });
// API lấy danh sách phòng theo landlord_id
router.get("/rooms/:landlordId", async (req, res) => {
    try {
        const landlordId = req.params.landlordId;  // Lấy landlordId từ params
        console.log("Landlord ID: ", landlordId);

        // Tìm các tòa nhà có landlord_id khớp với giá trị truyền vào
        const buildings = await Building.find({ landlord_id: new mongoose.Types.ObjectId(landlordId) });

        if (buildings.length === 0) {
            return res.status(404).json({ message: "No buildings found for this landlord." });
        }

        // Lấy danh sách tất cả building_id từ các tòa nhà đã tìm thấy
        const buildingIds = buildings.map(building => building._id);

        // Tìm các phòng thuộc các building này
        const rooms = await Room.find({ building_id: { $in: buildingIds } });
        //.populate("building_id");  // Lấy thêm thông tin tòa nhà nếu cần

        if (rooms.length === 0) {
            return res.status(404).json({ message: "No rooms found for this landlord." });
        }

        // Trả về danh sách phòng
        return res.status(200).json(rooms);
    } catch (error) {
        console.error(error);
        return res.status(500).json({ message: "Server error" });
    }
});

// API lấy phòng theo tên phòng
router.get("/roomByName/:roomName", async (req, res) => {
    const roomName = req.params.roomName;  // Lấy tên phòng từ params

    try {
        const room = await Room.findOne({ name: roomName }); // Tìm phòng theo tên
        if (!room) {
            return res.status(404).json({ message: "Room not found." });
        }

        // Trả về thông tin phòng
        return res.status(200).json(room);
    } catch (err) {
        console.error('Lỗi khi tìm phòng:', err);
        return res.status(500).json({ message: "Server error" });
    }
});
router.get("/support/:roomId", async (req, res) => {
    const roomId = req.params.roomId;  // Lấy roomId từ params

    try {
        // Tìm các yêu cầu hỗ trợ có room_id khớp
        const supports = await Support.find({ room_id: new mongoose.Types.ObjectId(roomId) })
            .populate("user_id") // Lấy thông tin người dùng
            .populate("room_id"); // Lấy thông tin phòng nếu cần

        if (supports.length === 0) {
            return res.status(404).json({ message: "No support requests found for this room." });
        }

        // Trả về danh sách yêu cầu hỗ trợ
        return res.status(200).json(supports);
    } catch (err) {
        console.error("Lỗi khi lấy dữ liệu yêu cầu hỗ trợ:", err);
        return res.status(500).json({ message: "Server error" });
    }
});
// API lấy yêu cầu hỗ trợ theo tên phòng
router.get("/supportByRoomName/:roomName", async (req, res) => {
    const roomName = req.params.roomName;  // Lấy tên phòng từ params

    try {
        // Tìm room_id từ tên phòng
        const room = await Room.findOne({ name: roomName });
        if (!room) {
            return res.status(404).json({ message: "Room not found." });
        }

        // Tìm các yêu cầu hỗ trợ theo room_id
        const supports = await Support.find({ room_id: room._id })
            .populate("user_id") // Lấy thông tin người dùng
            .populate("room_id"); // Lấy thông tin phòng nếu cần

        if (supports.length === 0) {
            return res.status(404).json({ message: "No support requests found for this room." });
        }

        // Trả về danh sách yêu cầu hỗ trợ
        return res.status(200).json(supports);
    } catch (err) {
        console.error("Error fetching support requests:", err);
        return res.status(500).json({ message: "Server error" });
    }
});


module.exports = router;