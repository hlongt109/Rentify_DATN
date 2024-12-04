var express = require('express');
var router = express.Router();

const Report = require("../../models/Report");
const Post = require("../../models/Post");
const Room = require("../../models/Room");
const Service = require("../../models/Service");
const Contract = require("../../models/Contract"); 
const handleServerError = require("../../utils/errorHandle");

// get all 
router.get("/reports_all", async (req, res) => {
    try {
        const data = await Report.find().populate('user_id', 'name');
        if (data) {
            res.status(200).send(data)
            console.log(data)
        } else {
            res.status(400).json({
                "status": 400,
                "messenger": "Get report list failed",
                "data": []
            })
        }
    } catch (error) {
        console.log("Error: ", error);
        handleServerError(res, error);
    }
})

// get detail
router.get("/get_report_details/:id", async (req, res) => {
    try {
        const { id } = req.params;
        if (!id) {
            return res.status(400).json({ error: 'id cant not be empty' });
        }

        const result = await Report.findById(id).populate('user_id');
        // room, service thì cần thông tin chỗ ở, thông tin người quản lý
        // bài đăng thì cânf thông tin người báo cáo, thông tin bài post, thông tin người đăng 
        if (!result) {
            return res.status(404).json({ message: "Report not found" })
        }

        const infoContract = await Contract.findOne({ user_id: result.user_id, status: 0 })
        .populate({
            path: 'room_id',
            populate: {
                path: 'building_id' 
            }
        });
        console.log("infoContract :", infoContract)

        if (result.type !== "post" && result.type !== "room" && result.type !== "service") {
            return res.status(404).json({ message: "Report type error during processing" })
        }

        if (result.type === "post") {
            const post = await Post.findById(result.id_problem).populate("user_id")
            if (!post) {
                return res.status(404).json({ message: "Post not found" })
            }
            res.status(200).json({ data: result, dataDetails: post, dataRoom4Service: infoContract })
        }
        if (result.type === "room") {
            const room = await Room.findById(result.id_problem).populate({
                path: 'building_id',
                populate: { path: 'landlord_id', model: 'User'}
            });
            if (!room) {
                return res.status(404).json({ message: "Room not found" })
            }
            res.status(200).json({ data: result, dataDetails: room, dataRoom4Service: infoContract })
        }
        if (result.type === "service") {
            const service = await Service.findById(result.id_problem).populate("landlord_id");
            if (!service) {
                return res.status(404).json({ message: "Service not found" })
            }
            
            res.status(200).json({ data: result, dataDetails: service, dataRoom4Service: infoContract })
        }
    } catch (error) {
        console.log("Error: ", error);
        handleServerError(res, error);
    }
});

// update status
router.put("/report/:id", async (req, res) => {
    try {
        const { id } = req.params;
        const data = req.body;
        const report = await Report.findById(id);
        if (!report) {
            return res.status(404).json({ message: "Report not found" })
        }

        report.user_id = report.user_id;
        report.type = report.type;
        report.id_problem = report.id_problem;
        report.title_support = report.title_support;
        report.content_support = report.content_support;
        report.image = report.image;
        report.status = data.status ?? report.status;
        report.created_at = report.created_at;
        report.updated_at = new Date().toISOString()

        const result = await report.save();
        if (result) {
            res.status(200).json({ message: "Update report success", data: result })
        } else {
            res.status(401).json({ message: "Update report failed" })
        }
    } catch (error) {
        console.log("Error: ", error);
        handleServerError(res, error);
    }
})

module.exports = router;