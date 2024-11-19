var express = require('express');
const mongoose = require('mongoose');
var router = express.Router();

const Service = require("../../models/Service");
const uploadFile = require("../../config/common/upload");
const handleServerError = require("../../utils/errorHandle");
const { getFormattedDate } = require('../../utils/dateUtils');

// service list
router.get("/api/services", async (req, res) => {
    try {
        const data = await Service.find();
        if (data) {
            //res.status(200).send(data);
            res.render("Service/listService", { data });
        } else {
            res.json({
                "status": 400,
                "messenger": "Get service list failed",
                "data": []
            })
        }
    } catch (error) {
        handleServerError(res, error);
    }
})
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
// router.post("/api/services/add", async (req, res) => {
//     try {
//         const data = req.body;

//         const newService = new Service({
//             name: data.name,
//             description: data.description,
//             price: data.price,
//             created_at: getFormattedDate(),
//             updated_at: ""
//         })
//         const result = await newService.save()
//         if (result) {
//             res.json({
//                 "status": 200,
//                 "message": "Add service successfully",
//                 "data": result
//             });
//         } else {
//             res.json({
//                 "status": 400,
//                 "message": "Error, Add service failed",
//                 "data": []
//             });
//         }
//     } catch (error) {
//         handleServerError(res, error);
//     }
// })

router.post("/api/services/add", uploadFile.array('photos'), async (req, res) => {
    try {
        if (req.method == 'POST') {
            const { name, description, price } = req.body;
            let photos = req.files.map(file => file.filename);

            if (photos.length === 0) {
                return res.status(400).json({ message: 'Image hoặc video không được trống' });
            }

            let objService = new Service({
                photos,
                name,
                description,
                price,
                created_at: new Date().toISOString(),
                updated_at: new Date().toISOString()
            });
            await objService.save();
            let msg = 'Thêm thành công id mới: ' + objService._id;
            console.log(msg);
            res.redirect('/api/api/services');
        }
    } catch (error) {
        console.error(error);
        return res.status(500).send('Lỗi server rồi');
    }
})

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
router.post("/api/services/update/:id", async (req, res) => {
    try {
        const { id } = req.params;
        const data = req.body;

        const servicePut = await Service.findByIdAndUpdate(id);

        if (!servicePut) {
            return res.status(404).json({
                status: 404,
                messenger: 'Service not found',
                data: []
            });
        }

        servicePut.name = data.name ?? servicePut.name;
        servicePut.description = data.description ?? servicePut.description;
        servicePut.price = data.price ?? servicePut.price;
        servicePut.updated_at = new Date().toISOString()

        const result = await servicePut.save()

        if (result) {
            // res.json({
            //     status: 200,
            //     messenger: 'service update successfully',
            //     data: result
            // });
            res.redirect("/api/api/services");
        } else {
            res.json({
                status: 400,
                messenger: 'service update failed',
                data: []
            });
        }

    } catch (error) {
        handleServerError(res, error);
    }
})
// delete 
router.delete("/api/services1/:id", async (req, res) => {
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

// service list
router.get("/get-services/:landlord_id", async (req, res) => { 
    try {
        const landlordId = req.params.landlord_id;

        // Kiểm tra nếu landlordId là một ObjectId hợp lệ
        if (!mongoose.Types.ObjectId.isValid(landlordId)) {
            return res.status(400).json({
                status: 400,
                message: "Invalid landlord ID",
                data: []
            });
        }

        // Chuyển landlordId thành ObjectId nếu cần thiết
        const objectId = new mongoose.Types.ObjectId(landlordId);

        // Tìm dịch vụ có liên quan đến landlord_id
        const data = await Service.find({ landlord_id: objectId }); 

        if (data && data.length > 0) {
            // Nếu có dữ liệu dịch vụ, trả về danh sách dịch vụ
            res.status(200).json(data);
        } else {
            // Nếu không có dịch vụ nào
            res.status(404).json({
                status: 404,
                message: "No services found for this landlord",
                data: []
            });
        }
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
});
module.exports = router;