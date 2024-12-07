var express = require('express');
var router = express.Router();

const mongoose = require('mongoose');

const Booking = require("../../models/Booking");
const Room = require("../../models/Room");
const User = require("../../models/User");
const Building = require("../../models/Building")


//lay danh sach
router.get("/booking/list/:id", async (req, res) => {
    try {
        const landlordId = req.params.id;
        const { status } = req.query;  // Lấy trạng thái từ query

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

        // Điều kiện lọc với status nếu có
        const filterConditions = { building_id: { $in: buildingIds } };
        if (status !== undefined) {
            filterConditions.status = parseInt(status);  // Lọc theo trạng thái nếu có
        }

        const data = await Booking.find(filterConditions);

        // Tìm tất cả các yêu cầu hỗ trợ có building_id trong danh sách buildingIds
        //const data = await Booking.find({ building_id: { $in: buildingIds } });



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
router.delete("/booking/delete/:id", async (req, res) => {
    try {
        const { id } = req.params;
        const deleteBooking = await Booking.findByIdAndDelete(id);
        if (!deleteBooking) {
            console.log("Không tìm thấy đối tượng");
            return res.status(404).json({ message: "Không tìm thấy đối tượng" });
        }
        res.status(200).json({ message: "Xóa thành công", data: deleteBooking });
    } catch (error) {
        console.error("Lỗi khi xóa:", error);
        res.status(500).json({ message: "Đã xảy ra lỗi khi xóa" });
    }
});
router.get("/booking/user/phone/:id", async (req, res) => {
    try {
        const { id } = req.params;

        // Kiểm tra xem ID có hợp lệ hay không
        if (!mongoose.Types.ObjectId.isValid(id)) {
            return res.status(400).json({ message: "ID không hợp lệ" });
        }

        // Tìm người dùng theo ID
        const user = await User.findById(id).select("phoneNumber");

        if (!user) {
            return res.status(404).json({ message: "Không tìm thấy người dùng" });
        }

        // Gửi phản hồi thành công
        res.status(200).json({ success: true, data: user.phoneNumber });
    } catch (error) {
        console.error("Lỗi khi tìm phoneNumber:", error);
        res.status(500).json({ message: "Đã xảy ra lỗi khi tìm phoneNumber", error: error.message });
    }
});


router.get("/booking/room_name/:bookingId", async (req, res) => {
    const { bookingId } = req.params;

    try {
        // Kiểm tra xem bookingId có phải là ObjectId hợp lệ không
        if (!mongoose.Types.ObjectId.isValid(bookingId)) {
            return res.status(400).json({ message: "ID không hợp lệ" });
        }

        // Tìm Booking và populate room_id để lấy dữ liệu từ Room
        const booking = await Booking.findById(bookingId).populate({
            path: 'room_id',
            select: 'room_name', // Chỉ lấy trường room_name từ Room
        });

        // Kiểm tra nếu không tìm thấy hoặc room_id rỗng
        if (!booking || !booking.room_id) {
            return res.status(404).json({ message: "Không tìm thấy Booking hoặc Room" });
        }

        // Trả về tên phòng
        return res.json({
            success: true,
            data: {
                roomName: booking.room_id.room_name, // Tên phòng từ Room
            },
        });
    } catch (error) {
        console.error("Lỗi server:", error);
        return res.status(500).json({ message: "Lỗi server" });
    }
});
router.get("/booking/building_name/:bookingId", async (req, res) => {
    const { bookingId } = req.params;

    try {
        // Tìm booking theo bookingId và populate room_id
        const booking = await Booking.findById(bookingId).populate('room_id');
        if (!booking || !booking.room_id || !booking.room_id.building_id) {
            return res.status(404).json({ message: 'Không tìm thấy thông tin phòng hoặc tòa nhà' });
        }

        // Tìm tòa nhà dựa trên building_id của phòng
        const building = await Building.findById(booking.room_id.building_id);
        if (!building) {
            return res.status(404).json({ message: 'Không tìm thấy tòa nhà' });
        }

        // Trả về tên tòa nhà
        return res.json({
            success: true,
            data: {
                buildingName: building.nameBuilding, // Trả về tên tòa nhà
            }
        });
    } catch (error) {
        console.error(error);
        return res.status(500).json({ message: 'Lỗi server' });
    }
});

module.exports = router;