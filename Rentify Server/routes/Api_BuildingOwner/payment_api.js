var express = require('express');
var router = express.Router();

const Payment = require("../../models/Payment");
const handleServerError = require("../../utils/errorHandle");
const getFormattedDate = require('../../utils/dateUtils');

// create payment
router.post("/api/payments", async (req, res) => {
    try {
        const data = req.body;

        const date = await getFormattedDate()
        console.log(date)

        const newPayment = new Payment({
            user_id: mongoose.Types.ObjectId(data.user_id),
            invoice_id: mongoose.Types.ObjectId(data.invoice_id),
            amount: data.amount,
            payment_date: date,
            payment_method: data.payment_method,
        })
        const result = await newPayment.save();
        if (result) {
            res.json({
                "status": 200,
                "message": "Create payment successfully",
                "data": result
            });
        } else {
            res.json({
                "status": 400,
                "message": "Error, Create payment failed",
                "data": []
            });
        }

    } catch (error) {
        handleServerError(req, res)
    }
})
// list
router.get("/api/payments/:user_id", async (req, res) => {
    try {
        const { user_id } = req.params;

        const data = await Payment.find({ user_id });

        if (data.length > 0) {
            res.status(200).json(data);
        } else {
            res.status(404).json({
                message: "Payments not found for the specified user ID",
            });
        }
    } catch (error) {
        handleServerError(req, res)
    }
})
// details
router.get("/api/payments/:id", async (req, res) => {
    try {
        const { id } = req.params;
        const result = await Payment.findById(id);
        if (!result) {
            return res.status(404).json({
                status: 404,
                messenger: 'Payment not found'
            });
        }
        res.status(200).send(result)
    } catch (error) {
        handleServerError(req, res)
    }
})
module.exports = router;
