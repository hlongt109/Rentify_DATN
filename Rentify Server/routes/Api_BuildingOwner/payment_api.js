var express = require('express');
var router = express.Router();

const Payment = require("../../models/Payment");
const handleServerError = require("../../utils/errorHandle");
const { getFormattedDate } = require('../../utils/dateUtils');

// create payment
router.post("/api/payments", async(req, res) => {
    try {
        const data = req.body;
        
        const newPayment = new Payment({
            user_id: data.user_id,
            invoice_id: data.invoice_id,
            amount: data.amount, 
            payment_date: getFormattedDate(),
            payment_method: data.payment_method,
        })

        const result = await newPayment.save();
        if(result){
            res.json({
                "status": 200,
                "message": "Create payment successfully",
                "data": result
            });
        }else{
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
router.get("/api/payments/:user_id", async(req, res) => {
    try {
        const { user_id } = req.params; 
        const data = await Payment.find({ user_id });
        if(data){
            res.status(200).send(data)
        }else{
            res.json({
                "status": 400,
                "messenger": "Get payment list failed",
                "data": []
            })
        }
    } catch (error) {
        handleServerError(req, res)
    }
})
// details
router.get("/api/payments/:id", async(req, res) => {
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
