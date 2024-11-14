var express = require('express');
var router = express.Router();
const Notification = require("../../models/Notification")
const { getFormattedDate } = require('../../utils/dateUtils');

// create
router.post("/api/notifications", async (req, res) => {
    try {
        const data = req.body;
        const { user_id } = req.query;

        if (!user_id) {
            return res.status(400).json({
                "status": 400,
                "message": "user_id bị bỏ trống",
            });
        }

        const newNotification = new Notification({
            user_id: user_id,
            title: data.title,
            content: data.content,
            status: data.status,
            read_status: 'unread',
            created_at: getFormattedDate()
        })

        const result = await newNotification.save();
        if (result) {
            res.json({
                "status": 200,
                "message": "Create notification successfully",
                "data": result
            });
        } else {
            res.json({
                "status": 400,
                "message": "Error, Create notification failed",
                "data": []
            });
        }
    } catch (error) {
        handleServerError(req, res)
    }
})
// list
router.get("/api/notifications/:user_id", async (req, res) => {
    try {
        const { user_id } = req.params;
        const data = await Notification.find({ user_id });
        if (data) {
            res.status(200).send(data)
        } else {
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
// details
router.get("/api/notifications/:id", async (req, res) => {
    try {
        const { id } = req.params;
        const result = await Notification.findById(id);
        if (!result) {
            return res.status(404).json({
                status: 404,
                messenger: 'Notification not found'
            });
        }
        res.status(200).send(result)
    } catch (error) {
        handleServerError(req, res)
    }
})
// delete
router.delete("/api/notifications/:id", async (req, res) => {
    try {
        const { id } = req.params;
        const result = await Notification.findByIdAndDelete(id);
        if (result) {
            res.json({
                "status": 200,
                "messenger": "Notification deleted successfully",
                "data": result
            })

        } else {
            res.json({
                "status": 400,
                "messenger": "Error, Notification deletion failed",
                "data": []
            })
        }
    } catch (error) {
        handleServerError(req, res)
    }
})
module.exports = router;
