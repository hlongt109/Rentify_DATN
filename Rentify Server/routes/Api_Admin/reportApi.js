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

router.get("/reports/post", async (req, res) => {
    try {
        // Tìm tất cả các báo cáo có type là "post"
        const reports = await Report.find({ type: "post" });

        // Kiểm tra nếu không có báo cáo nào
        if (reports.length === 0) {
            return res.status(404).json({ message: "No posts found" });
        }

        // Trả về danh sách các báo cáo
        return res.status(200).json({ message: "Reports fetched successfully", data: reports });
    } catch (error) {
        console.log("Error:", error);
        return res.status(500).json({ message: "Server error", error: error.message });
    }
});

router.get("/reports/post/:id", async (req, res) => {
    try {
        const { id } = req.params;  // Lấy id từ params

        // Tìm báo cáo với id tương ứng
        const report = await Report.findById(id);

        // Kiểm tra nếu báo cáo không tồn tại
        if (!report) {
            return res.status(404).json({ message: "Report not found" });
        }

        // Trả về dữ liệu chi tiết báo cáo
        return res.status(200).json({ message: "Report fetched successfully", data: report });
    } catch (error) {
        console.log("Error:", error);
        return res.status(500).json({ message: "Server error", error: error.message });
    }
});

// Route để cập nhật status của báo cáo thành 0
router.put("/reports/post/:id/status", async (req, res) => {
    try {
        const { id } = req.params; // Lấy id từ params

        // Tìm và cập nhật báo cáo với id tương ứng
        const updatedReport = await Report.findByIdAndUpdate(
            id,
            { status: 0, updated_at: new Date().toISOString() }, // Cập nhật status thành 0 và updated_at
            { new: true } // Tùy chọn để trả về tài liệu đã cập nhật
        );

        // Kiểm tra nếu báo cáo không tồn tại
        if (!updatedReport) {
            return res.status(404).json({ message: "Report not found" });
        }

        // Trả về dữ liệu báo cáo đã cập nhật
        return res.status(200).json({ message: "Status updated successfully", data: updatedReport });
    } catch (error) {
        console.log("Error:", error);
        return res.status(500).json({ message: "Server error", error: error.message });
    }
});


module.exports = router;

