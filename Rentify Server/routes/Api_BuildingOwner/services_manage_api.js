var express = require('express');
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
            res.status(200).send(data);
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
router.post("/api/services", async (req, res) => {
    try {
        const data = req.body;

        const newService = new Service({
            name: data.name,
            description: data.description,
            price: data.price,
            created_at: getFormattedDate(),
            updated_at: ""
        })
        const result = await newService.save()
        if (result) {
            res.json({
                "status": 200,
                "message": "Add service successfully",
                "data": result
            });
        } else {
            res.json({
                "status": 400,
                "message": "Error, Add service failed",
                "data": []
            });
        }
    } catch (error) {
        handleServerError(res, error);
    }
})
// update 
router.put("/api/services/:id", async (req, res) => {
    try {
        const { id } = req.params;
        const data = req.body;

        const servicePut = await Service.findById(id);

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
        servicePut.created_at = servicePut.created_at;
        servicePut.updated_at = getFormattedDate();

        const result = await servicePut.save()

        if (result) {
            res.json({
                status: 200,
                messenger: 'service update successfully',
                data: result
            });
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
router.delete("/api/services/:id", async (req, res) => {
    try {
        const {id} = req.params
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