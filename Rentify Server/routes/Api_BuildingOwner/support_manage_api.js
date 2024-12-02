var express = require("express")
var router = express.Router();
const mongoose = require('mongoose');

const Support = require("../../models/Support")
const Building = require("../../models/Building")

// api
// Đảm bảo route này đã được định nghĩa trong server của bạn
router.get("/support_mgr/list/:id", async (req, res) => {
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
        const data = await Support.find({ building_id: { $in: buildingIds } });

        console.log('Dữ liệu trả về từ cơ sở dữ liệu:', data);

        if (data.length === 0) {
            return res.status(404).json({ message: "No support requests found for this landlord." });
        }

        res.json({ data });
    } catch (error) {
        console.error("Error fetching support requests:", error.message);
        return res.status(500).json({
            "status": 500,
            "message": "Internal Server Error"
        });
    }
});
// API để lấy tên phòng theo room_id của một support
router.get('/support_mgr/room_name/:supportId', async (req, res) => {
    const { supportId } = req.params;  // Lấy supportId từ URL params

    try {
        // Tìm hỗ trợ (support) theo supportId
        const support = await Support.findById(supportId).populate('room_id');  // Tìm support và populate room_id
        if (!support || !support.room_id) {
            return res.status(404).json({ message: 'Không tìm thấy hỗ trợ hoặc phòng' });
        }

        // Trả về tên phòng
        return res.json({
            success: true,
            data: {
                roomName: support.room_id.room_name,  // Trả về tên phòng
            }
        });
    } catch (error) {
        console.error(error);
        return res.status(500).json({ message: 'Lỗi server' });
    }
});



//UPDATE
// router.put("/support-customer/update/:id", async (req, res) => {
//     try {
//         const { id } = req.params;
//         const data = req.body;
//         const { file } = req

//         const supportUpdate = await Support.findById(id)
//         if (!supportUpdate) {
//             return res.status(404).json({
//                 status: 404,
//                 messenger: 'No support found for update',
//                 data: []
//             })
//         }

//         let imageNew;
//         if (file && file.length > 0) {
//             imageNew = `${req.protocol}://${req.get("host")}/uploads/${file.filename}`
//         } else {
//             imageNew = supportUpdate.image
//         }

//         supportUpdate.user_id = data.user_id ?? supportUpdate.user_id;
//         supportUpdate.room_id = data.room_id ?? supportUpdate.room_id;
//         supportUpdate.title_support = data.title_support ?? supportUpdate.title_support;
//         supportUpdate.content_support = data.content_support ?? supportUpdate.content_support;
//         supportUpdate.image = imageNew;
//         supportUpdate.status = data.status ?? supportUpdate.status;
//         supportUpdate.created_at = supportUpdate.created_at;
//         supportUpdate.updated_at = getFormattedDate();

//         const result = await supportUpdate.save()

//         if (result) {
//             res.json({
//                 status: 200,
//                 messenger: 'support update successfully',
//                 data: result
//             });
//         } else {
//             res.json({
//                 status: 400,
//                 messenger: 'support update failed',
//                 data: []
//             });
//         }
//     } catch (error) {
//         handleServerError(res, error);
//     }
// })
router.put("/support_mgr/update/:id", async (req, res) => {
    const { status } = req.body;
    const userId = req.params.id;
    if (!mongoose.Types.ObjectId.isValid(userId)) {
        return res.status(400).json({ message: 'ID không hợp lệ.' });
    }

    const support = await Support.findById(userId);
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


router.get("/api/support-customer/:id", async (req, res) => {
    try {
        const { id } = req.params;
        const support = await Support.findById(id);

        if (!support) {
            return res.status(404).json({
                status: 404,
                messenger: 'food drink not found'
            })
        }
        res.status(200).send(support)
    } catch (error) {
        handleServerError(res, error);
    }
})
module.exports = router;