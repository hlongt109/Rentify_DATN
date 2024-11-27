var express = require('express');
var router = express.Router();

const Staff = require("../../models/User")
const User = require("../../models/User")
const uploadFile = require("../../config/common/upload");
const handleServerError = require("../../utils/errorHandle")
const { getFormattedDate } = require('../../utils/dateUtils');

// add 
router.post("/api/staffs", uploadFile.single('image'), async (req, res) => {
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
            created_at: getFormattedDate(),
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
        handleServerError(res, error);
    }
})
// update
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
        staffUpdate.updated_at = getFormattedDate();

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
        handleServerError(res, error);
    }
})
// delete
router.delete("/api/staffs/:id", async (req, res) => {
    try {
        const { id } = req.params
        const result = await Staff.findByIdAndDelete(id);
        if (result) {
            res.json({
                "status": 200,
                "messenger": "staff deleted successfully",
                "data": result
            })
        } else {
            res.json({
                "status": 400,
                "messenger": "Error, staff deletion failed",
                "data": []
            })
        }
    } catch (error) {
        handleServerError(res, error);
    }
})
// staff list
router.get("/api/staffs", async (req, res) => {
    try {
        const data = await Staff.find();
        if (data) {
            res.status(200).send(data)
        } else {
            res.json({
                "status": 400,
                "messenger": "Get staff list failed",
                "data": []
            })
        }
    } catch (error) {
        handleServerError(res, error);
    }
})
// get details
router.get("/api/staffs/:id", async (req, res) => {
    try {
        const { id } = req.params;
        const result = await Staff.findById(id);
        if (!result) {
            return res.status(404).json({
                status: 404,
                messenger: 'Staff not found'
            });
        }
        res.status(200).send(result)
    } catch (error) {
        handleServerError(res, error);
    }
})
// search 
router.get("/api/staffs", async (req, res) => {
    try {
        const { name } = req.query;

        const result = await Staff.find({
            name: { $regex: new RegExp(name, "i") } // Tìm kiếm theo tên không phân biệt hoa thường
        }) // không giới hạn kết quả 
        // .limit(10); // Giới hạn 10 kết quả
        if (result.length > 0) {
            res.status(200).json({
                status: 200,
                message: "Staff search successful",
                data: result
            });
        } else {
            res.status(404).json({
                status: 404,
                message: "No staff found",
                data: []
            });
        }
    } catch (error) {
        handleServerError(res, error);
    }
})

router.get("/get-staffs/:landlord_id", async (req, res) => {
    try {
        // Lấy landlord_id từ URL params
        const landlordId = req.params.landlord_id;
        
        if (!landlordId) {
            return res.status(400).json({
                status: 400,
                message: "Landlord ID is required",
            });
        }

        // Tìm tất cả staff có landlord_id trùng với landlordId
        const staffs = await User.find(
            { landlord_id: landlordId, role: "staffs" }, // Điều kiện tìm kiếm
            { username: 1, _id: 1 } // Chỉ lấy trường `username`, bỏ `_id`
        );

        if (staffs.length === 0) {
            return res.status(404).json({
                status: 404,
                message: "No staffs found",
            });
        }

        res.status(200).json({
            status: 200,
            data: staffs,
        });
    } catch (error) {
        console.error(error);
        res.status(500).json({
            status: 500,
            message: "Internal Server Error",
            error: error.message,
        });
    }
});


module.exports = router;