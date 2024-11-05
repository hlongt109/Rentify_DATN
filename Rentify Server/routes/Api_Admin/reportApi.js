var express = require('express');
var router = express.Router();

const Report = require("../../models/Report");
const Post = require("../../models/Post");
const Room = require("../../models/Room");
const Service = require("../../models/Service");
const handleServerError = require("../../utils/errorHandle");

router.get("/api/get_report_details/:id", async (req, res) => {
    try {
        const { id } = req.params;
        if (!id) {
            return res.status(400).json({ error: 'id cant not be empty' });
        }

        const result = await Report.findById(id);
        if (!result) {
            return res.status(404).json({ message: "Report not found" })
        }

        if(result.type !== "post" && result.type !== "room" || result.type !== "service"){
            return res.status(404).json({message: "Report type error during processing"})
        }

        if (result.type === "post") {
            const post = await Post.findById(result.id_problem);
            if (!post) {
                return res.status(404).json({ message: "Post not found" })
            }
            res.status(200).json({data: result, dataDetails: post})
        }
        if (result.type === "room") {
            const room = await Room.findById(result.id_problem);
            if (!room) {
                return res.status(404).json({ message: "Room not found" })
            }
            res.status(200).json({data: result, dataDetails: room})
        }
        if (result.type === "service") {
            const service = await Service.findById(result.id_problem);
            if (!service) {
                return res.status(404).json({ message: "Service not found" })
            }
            res.status(200).json({data: result, dataDetails: service})
        }
    } catch (error) {
        console.log("Error: ", error);
        handleServerError(res, error);
    }
});

router.put("/api/report:id", async(req, res) => {
    try {
        const { id } = req.params;
        const data = req.body;
        const report = await Report.findById(id);
        if(!report){
            return res.status(404).json({message: "Report not found"})
        }

        report.user_id = report.user_id;
        report.type =  report.type;
        report.id_problem = report.id_problem;
        report.title_support = report.title_support;
        report.content_support = report.content_support;
        report.image = report.image;
        report.status = data.status ?? report.status;
        report.created_at = report.created_at;
        report.updated_at = new Date().toISOString()

        const result = await report.save();
        if(result){
            res.status(200).json({message: "Update report success", data: result})
        }else{
            res.status(401).json({message: "Update report failed"})
        }
    } catch (error) {
        console.log("Error: ", error);
        handleServerError(res, error);
    }
})

module.exports = router;

