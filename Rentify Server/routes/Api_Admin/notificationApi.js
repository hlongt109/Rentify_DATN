var express = require('express');
var router = express.Router();
const Notification = require("../../models/Notification")
const handleServerError = require("../../utils/errorHandle");

// send it who reported it
router.post("/notification_reply", async(req, res) => {
    try {
        const data = req.body;

        const newNotification = new Notification({
            user_id: data.user_id,
            title: data.title,
            content: data.content,
            read_status: 'unread',
            created_at: new Date().toISOString()
        })
        const result = await newNotification.save();
        if(result){
            res.status(200).json({ message: "Send notification success", data: result })
        }else{
            res.status(401).json({ message: "Send notification failed" })
        }

    } catch (error) {
        handleServerError(req, res)
    }
})

module.exports = router;


