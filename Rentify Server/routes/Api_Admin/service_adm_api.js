var express = require('express');
var router = express.Router();
const mongoose = require('mongoose');
const Service = require("../../models/Service")
const handleServerError = require("../../utils/errorHandle");
const uploadFile = require("../../config/common/upload");

//
router.get('/service_adm/:admin_id', async(req, res) => {
    try {
        const { admin_id } = req.params;
        if (!admin_id) {
            return res.status(400).json({ error: 'admin_id is required.' });
        }

        const services = await Service.find({ admin_id: admin_id })
        if(!services){
            return res.status(404).json({ message: "Services not found" })
        }

        res.status(200).json({ data: services })
    } catch (error) {
        handleServerError(res, error);
    }
})

router.post("/service_adm", uploadFile.fields([{name: 'images', maxCount: 5}]) , async(req, res) => {
    try {
        const data = req.body;
        const files = req.files;

        const images = files.images ? files.images.map(file => `${req.protocol}://${req.get("host")}/public/uploads/${file.filename}`) : [];

        const newService = new Service({
            admin_id: data.admin_id,
            name: data.name,
            description: data.description,
            price: data.price,
            photos: images,
            created_at: new Date().toISOString(),
            updated_at: new Date().toISOString(),
        })

        const result = await newService.save();

        if(result){
            res.status(200).json({ message: "Add service success", data: result });
        }else{
            res.status(401).json({ message: "Add service failed" });
        }
    } catch (error) {
        handleServerError(res, error);
    }
})

router.put("/service_adm/:id", uploadFile.fields([{name: 'images', maxCount: 5}]) ,async(req, res) => {
    try {
        const { id } = req.params;
        const data = req.body;
        const files = req.files;

        const service = await Service.findById(id);
        if (!service) {
            return res.status(404).json({ messenger: 'No service found to update' });
        }

        let images;

        if (files && files.images) {
            images = files.images ? files.images.map(file => `${req.protocol}://${req.get("host")}/public/uploads/${file.filename}`) : [];
        }else{
            images = service.photos
        }

        service.admin_id = service.admin_id;
        service.name = data.name ?? service.name;
        service.description = data.description ?? service.description;
        service.price = data.price ?? service.price;
        service.photos = images;
        service.created_at = service.created_at;
        service.updated_at = new Date().toISOString();

        const result = await service.save();
        if (result) {
            res.status(200).json({ message: "Update service success", data: result })
        } else {
            res.status(401).json({ message: "Update service failed" })
        }
    } catch (error) {
        handleServerError(res, error);
    }
})

router.delete("/service_adm/:id", async(req, res) => {
    try {
        const { id } = req.params
        const result = await Service.findByIdAndDelete(id);
        if (result) {
            return res.status(200).json({ message: "Delete service success" })
        } else {
            return res.status(404).json({ message: "Service not found" })
        }
    } catch (error) {
        handleServerError(res, error);
    }
})

router.get("/service_details/:id", async(req, res) => {
    try {
        const { id } = req.params
        const result = await Service.findById(id);
        if (!result) {
            return res.status(404).json({ message: "Service not found" })
        }
        res.status(200).json({ data: result })
    } catch (error) {
        handleServerError(res, error);
    }
})

module.exports = router;