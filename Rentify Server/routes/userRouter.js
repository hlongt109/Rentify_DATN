var express = require('express');
var router = express.Router();
// model
const User = require("../models/User")
// send email service
const transporter = require("../config/common/mailer")
// upload file (image, video)
const uploadFile = require("../config/common/multer")

// routers
router.post("/register-account", async (req, res) => {
    try {
        
    } catch (error) {
        
    }
})

module.exports = router