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
router.post("/login", async (req, res) => {
    try {
        const { username, password } = req.body;
        const user = await Account.findOne({ username, password });
        if (!user || user.role !== 'admin') {
            return res.status(400).json({ message: "Username hoac Password khong dung hoac khong co quyen truy cap" })
        }
    } catch (error) {

    }
})

module.exports = router