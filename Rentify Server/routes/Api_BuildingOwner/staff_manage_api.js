var express = require('express');
var router = express.Router();

const Staff = require("../../models/User")
const uploadFile = require("../../config/common/upload");

const now = new Date();
// Định dạng giờ và ngày tháng
const options = {
    hour: '2-digit',
    minute: '2-digit',
    day: '2-digit',
    month: '2-digit',
    year: 'numeric'
};
const formattedDate = new Intl.DateTimeFormat('vi-VN', options).format(now);

// api
router.post("/api/staffs", async (req, res) => {
    try {
        const data = req.body;
        const { file } = req

        let avatar;
        if (!file) {
            avatar = ""
        } else {
            avatar = `${req.protocol}://${req.get("host")}/uploads/${file.filename}`;
        }

        const newStaff = new Staff({
            username: data.username,
            password: data.password,
            email: data.email,
            phoneNumber: data.phoneNumber,
            role: 'staffs',
            name: data.name,
            dob: data.dob,
            gender: data.gender,
            address: data.address,
            profile_picture_url: avatar,
            created_at: formattedDate,
            updated_at: ""
        })

        const result = await newStaff.save();
        if (result) {
            res.json({
                "status": 200,
                "message": "Create staff successfully",
                "data": result
            });
        } else {
            res.json({
                "status": 400,
                "message": "Error, Create staff failed",
                "data": []
            });
        }
    } catch (error) {
        console.error("Error: " + error);
        res.status(500).json({
            "status": 500,
            "message": "Server error",
            "error": error.message
        });
    }
})

router.put("/api/staffs/:id", uploadFile.single("image"), async (req, res) => {
    try {
        const { id } = req.params;
        const data = req.body;
        const { file } = req

        const staffUpdate = await Staff.findById(id)

        if (!staffUpdate) {
            return res.status(404).json({
                status: 404,
                messenger: 'No staff found to update',
                data: []
            });
        }

        let avatarStaff;
        if (file && file.length > 0) {
            avatarStaff = `${req.protocol}://${req.get("host")}/uploads/${file.filename}`;
        } else {
            avatarStaff = staffUpdate.profile_picture_url
        }

        staffUpdate.username = data.username ?? staffUpdate.username;
        staffUpdate.password = data.password ?? staffUpdate.password;
        staffUpdate.email = data.email ?? staffUpdate.email;
        staffUpdate.phoneNumber = data.phoneNumber ?? staffUpdate.phoneNumber;
        staffUpdate.role = 'staffs';
        staffUpdate.name = data.name ?? staffUpdate.name;
        staffUpdate.dob = data.dob ?? staffUpdate.dob;
        staffUpdate.gender = data.gender ?? staffUpdate.gender;
        staffUpdate.address = data.address ?? staffUpdate.address;
        staffUpdate.profile_picture_url = avatar;
        staffUpdate.created_at = staffUpdate.created_at
        staffUpdate.updated_at = formattedDate;

        const result = await staffUpdate.save()

        if (result) {
            res.json({
                status: 200,
                messenger: 'staff update successfully',
                data: result
            });
        } else {
            res.json({
                status: 400,
                messenger: 'staff update failed',
                data: []
            });
        }

    } catch (error) {
        console.error("Error: " + error);
        res.status(500).json({
            "status": 500,
            "message": "Server error",
            "error": error.message
        });
    }
})
