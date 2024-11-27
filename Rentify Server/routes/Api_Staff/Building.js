const express = require("express");
const router = express.Router();
const Building = require("../../models/Building");
const User = require("../../models/User");
router.get("/get-building-staffId/:staffId", async (req, res) => {
    try {
        const staffId = req.params.staffId;

        if (!staffId) {
            return res.status(400).json({ message: "Không tìm thấy ID nhân viên" });
        }

        const buildings = await Building.find({ manager_id: staffId })
            .populate("manager_id", "name phoneNumber email")
            .select("-__v");

        if (!buildings || buildings.length === 0) {
            return res.status(404).json({ message: "Không tìm thấy tòa nhà nào được quản lý bởi nhân viên này" });
        }

        res.status(200).json({
            status: 200,
            message: "Lấy danh sách tòa nhà thành công",
            data: buildings
        });

    } catch (error) {
        res.status(500).json({
            status: 500,
            message: "Lỗi server: " + error.message
        });
    }
});

module.exports = router;