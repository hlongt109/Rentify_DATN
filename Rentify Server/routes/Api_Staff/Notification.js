const express = require('express');
const router = express.Router();
const Notification = require("../../models/Notification");

// Lấy danh sách tất cả thông báo
router.get("/list/:user_id", async(req, res) => {
    try {
        const { user_id } = req.params; 
        const data = await Notification.find({ user_id });
        if(data){
            res.status(200).send(data)
        }else{
            res.json({
                "status": 400,
                "messenger": "Get notification list failed",
                "data": []
            })
        }
    } catch (error) {
        handleServerError(req, res)
    }
})
router.post('/add', async (req, res) => {
    console.log(req.body); // Kiểm tra dữ liệu nhận được

    const notification = new Notification({
        user_id: req.body.user_id,
        title: req.body.title,
        content: req.body.content,
        status: req.body.status,
        read_status: req.body.read_status || "unread",
        created_at: new Date().toISOString()
    });

    try {
        const savedNotification = await notification.save();
        res.status(201).json({ message: 'Notification created', data: savedNotification });
    } catch (error) {
        res.status(400).json({ message: error.message });
    }
});



// Xem chi tiết thông báo theo ID
router.get('/detail/:id', async (req, res) => {
    try {
        const notification = await Notification.findById(req.params.id);
        if (!notification) {
            return res.status(404).json({ message: "Notification not found" });
        }
        res.json(notification);
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
});

module.exports = router;
