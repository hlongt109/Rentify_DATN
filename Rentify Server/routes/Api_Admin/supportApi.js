var express = require("express")
var router = express.Router();

const Support = require("../../models/Support")

// api
router.get("/api/support-customer", async (req, res) => {
    try {
        const data = await Support.find();
        if (data) {
            res.status(200).send(data)
        } else {
            res.json({
                "status": 400,
                "message": "Get support list failed",
                "data": []
            })
        }
    } catch (error) {
        handleServerError(res, error);
    }
})

router.put("/api/support-customer/{id}", async (req, res) => {
    try {
        const { id } = req.params;
        const data = req.body;
        const { file } = req

        const supportUpdate = await Support.findById(id)
        if (!supportUpdate) {
            return res.status(404).json({
                status: 404,
                messenger: 'No support found for update',
                data: []
            })
        }

        let imageNew;
        if (file && file.length > 0) {
            imageNew = `${req.protocol}://${req.get("host")}/uploads/${file.filename}`
        } else {
            imageNew = supportUpdate.image
        }

        supportUpdate.user_id = data.user_id ?? supportUpdate.user_id;
        supportUpdate.room_id = data.room_id ?? supportUpdate.room_id;
        supportUpdate.title_support = data.title_support ?? supportUpdate.title_support;
        supportUpdate.content_support = data.content_support ?? supportUpdate.content_support;
        supportUpdate.image = imageNew;
        supportUpdate.status = data.status ?? supportUpdate.status;
        supportUpdate.created_at = supportUpdate.created_at;
        supportUpdate.updated_at = getFormattedDate();

        const result = await supportUpdate.save()

        if (result) {
            res.json({
                status: 200,
                messenger: 'support update successfully',
                data: result
            });
        } else {
            res.json({
                status: 400,
                messenger: 'support update failed',
                data: []
            });
        }
    } catch (error) {
        handleServerError(res, error);
    }
})

router.get("/api/support-customer/:id", async (req, res) => {
    try {
        const { id } = req.params;
        const support = await Support.findById(id);

        if (!support) {
            return res.status(404).json({
                status: 404,
                messenger: 'food drink not found'
            })
        }
        res.status(200).send(support)
    } catch (error) {
        handleServerError(res, error);
    }
})
module.exports = router;