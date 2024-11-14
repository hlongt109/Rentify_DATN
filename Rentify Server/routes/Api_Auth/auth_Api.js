var express = require('express');
var router = express.Router();

const jwt = require('jsonwebtoken');
const bcrypt = require('bcrypt');
// model
const User = require("../../models/User");
// send email service
const {transporter} = require("../../config/common/mailer")
// upload file (image,images, video)
const uploadFile = require("../../config/common/upload")

// api 
router.get("/rentify/login", (req, res) => {
    res.render('Auth/Login');
});

router.post("/rentify/login", async (req, res) => {
    try {
        const { username, password } = req.body;
        if (!username || !password) {
            return res.status(400).json({ message: 'Thiếu username hoặc password' });
        }
        // Tìm người dùng trong cơ sở dữ liệu
        const user = await User.findOne({ username: username, password: password });
        if (!user) {
            return res.status(401).json({ message: 'Tài khoản không tồn tại' });
        }
        if (user.role !== 'admin' && user.role !== "landlord") {
            return res.status(401).json({ message: 'Bạn không có quyền truy cập!' });
        }
        if (user.role == 'ban') {
            return res.status(401).json({ message: 'Tài khoản của bạn đã bị khóa bởi ADMIN' });
        }
        // Tạo JWT
        const token = jwt.sign({ id: user._id, role: user.role }, 'hoan', { expiresIn: '1w' });
        console.log("Generated token:", token); // Kiểm tra token được tạo
        const userID = user._id;
        console.log(user._id);

        res.json({ message: "Đăng nhập thành công", token, data: user, userID });


    } catch (error) {
        console.error(error, " Password: " + req.body.password);
        return res.status(500).send({ error: 'Lỗi trong quá trình đăng nhập', details: error.message });
    }
});

//đăng ký thường
router.get("/rentify/register", async (req, res) => {
    res.render("Auth/SignUp");
})
router.post("/rentify/register", async (req, res) => {
    try {
        const data = req.body;
        const { username, email } = data;
        const existingUser = await User.findOne({ username });
        const existingEmail = await User.findOne({ email })

        if (existingUser) {
            return res.status(400).json({ error: 'Username đã tồn tại' });
        }
        if (existingEmail) {
            return res.status(400).json({ error: 'Email đã tồn tại' });
        }

        let newUser = new User({
            username: data.username,
            password: data.password,
            email: data.email,
            phoneNumber: data.phoneNumber,
            role: "landlord",
            name: data.name,
            dob: data.dob,
            gender: data.gender,
            address: data.address,
            profile_picture_url: "",
            created_at: new Date().toISOString(),
            updated_at: new Date().toISOString()
        });
        const result = await newUser.save()

        if (result) {
            const mailOptions = {
                from: "hlong109.it@gmail.com",
                to: result.email,
                subject: "Account registration successful",
                text: "Thank you for registering an account to use the Rentify",
            };
            await transporter.sendMail(mailOptions);
            res.json({
                status: 200, messenger: "Account registration successful", data: result
            })
        } else {
            res.json({
                status: 400, messenger: "Account registration failed.", data: []
            })
        }
    } catch (error) {
        console.error(error);
        return res.status(500).json({ error: error.message || 'Server error' });
    }
});

module.exports = router