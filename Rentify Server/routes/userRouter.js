var express = require('express');
var router = express.Router();
// model
const Account = require("../models/Account")
// send email service
const transporter = require("../config/common/mailer")
// upload file (image, video)
const uploadFile = require("../config/common/multer")


// routers

router.get("/login", (req, res) => {
    res.render('login');
});
module.exports = router