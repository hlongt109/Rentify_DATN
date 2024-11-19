var express = require('express');
var router = express.Router();
const mongoose = require('mongoose');

const Service = require("../../models/Service");
const uploadFile = require("../../config/common/upload");
const handleServerError = require("../../utils/errorHandle");
const { getFormattedDate } = require('../../utils/dateUtils');

const jwt = require('jsonwebtoken');

router.get("/services_mgr/list/:id", async (req, res) => {
    try {
        const userId = req.params.id;

        if (!mongoose.Types.ObjectId.isValid(userId)) {
            return res.status(400).json({ message: "Invalid landlord_id format" });
        }

        const landlordObjectId = new mongoose.Types.ObjectId(userId);
        const data = await Service.find({ landlord_id: landlordObjectId });

        if (data.length === 0) {
            console.log("Không có dữ liệu");
            return res.render("Landlord_website/screens/QuanLydichVu", { data: [] });
        }

        res.render("Landlord_website/screens/QuanLydichVu", { data }); // Truyền data tới EJS
    } catch (error) {
        console.error("Error fetching services:", error.message);
        res.status(500).render("Landlord_website/screens/QuanLydichVu", { data: [] });
    }
});


// details
router.get("/api/services/:id", async (req, res) => {
    try {
        const { id } = req.params;
        const result = await Service.findById(id);
        if (!result) {
            return res.status(404).json({
                status: 404,
                messenger: 'Service not found'
            });
        }
        res.status(200).send(result)
    } catch (error) {
        handleServerError(res, error);
    }
})
// search
router.get("/api/services", async (req, res) => {
    try {
        const { name } = req.query;

        const result = await Service.find({
            name: { $regex: new RegExp(name, "i") }
        });
        if (result.length > 0) {
            res.status(200).json({
                status: 200,
                message: "Service search successful",
                data: result
            });
        } else {
            res.status(404).json({
                status: 404,
                message: "No service found",
                data: []
            });
        }
    } catch (error) {
        handleServerError(res, error);
    }
})
// add 
router.get("/api/services1", async (req, res) => {
    res.render("Service/addService");
})

// Endpoint thêm dịch vụ
router.post("/services/add/:id", uploadFile.array('photos'), async (req, res) => {
    try {
        const userId = req.params.id;
        const { name, description, price } = req.body;

        const photos = req.files ? req.files.map(file => file.filename) : [];

        console.log('Uploaded files:', req.files);

        const objService = new Service({
            photos,
            name,
            description,
            price,
            landlord_id: userId,
            created_at: new Date().toISOString(),
            updated_at: new Date().toISOString()
        });

        await objService.save();
        return res.status(201).json({ message: 'Thêm thành công!' });

    } catch (error) {
        console.error('Lỗi server:', error.message);
        return res.status(500).json({ message: 'Đã xảy ra lỗi server!' });
    }
});



// update 
router.get("/api/services1/:id", async (req, res) => {
    try {
        const findID = req.params.id;

        if (!findID) {
            return res.status(400).json({ message: "ID không hợp lệ hoặc không được cung cấp" });
        }

        const showID = await Service.findById(findID);
        if (!showID) {
            return res.status(404).json({ message: 'Dịch vụ không tồn tại' });
        }
        res.render("Service/updateService", { showID });
    } catch (error) {
        return res.status(500).json({ message: error.message });
    }
})
router.put("/services_mgr/update/:id", uploadFile.array('photos', 10), async (req, res) => {
    try {
        const { name, description, price } = req.body;
        const newPhotos = req.files ? req.files.map(file => file.filename) : [];

        if (!name || !description || !price) {
            return res.status(400).json({
                status: 400,
                message: 'Tên, mô tả và giá không được để trống',
                data: []
            });
        }
        // Kiểm tra sự tồn tại của dịch vụ
        const service = await Service.findById(req.params.id);
        if (!service) {
            return res.status(404).json({
                status: 404,
                message: 'Dịch vụ không tìm thấy',
                data: []
            });
        }
        // Nếu có ảnh mới, thay thế ảnh cũ; nếu không, giữ nguyên ảnh cũ
        if (newPhotos.length > 0) {
            service.photos = newPhotos;  // Ghi đè ảnh mới
        }
        // Cập nhật dịch vụ
        service.name = name;
        service.description = description;
        service.price = price;
        service.updated_at = new Date().toISOString();

        await service.save(); // Lưu thay đổi vào MongoDB
        // Trả lại kết quả cập nhật thành công
        res.json({
            status: 200,
            message: 'Cập nhật dịch vụ thành công',
            data: service
        });

    } catch (error) {
        // Xử lý lỗi server
        console.error(error);
        res.status(500).json({
            status: 500,
            message: 'Lỗi server nội bộ',
            data: []
        });
    }
});


// delete 
router.delete("/services1/:id", async (req, res) => {
    try {
        const { id } = req.params
        const result = await Service.findByIdAndDelete(id);
        if (result) {
            res.json({
                "status": 200,
                "messenger": "service deleted successfully",
                "data": result
            })

        } else {
            res.json({
                "status": 400,
                "messenger": "Error, service deletion failed",
                "data": []
            })
        }
    } catch (error) {
        handleServerError(res, error);
    }
})
module.exports = router;