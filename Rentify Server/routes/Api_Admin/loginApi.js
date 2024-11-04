var express = require('express');
var router = express.Router();

const jwt = require('jsonwebtoken');
const bcrypt = require('bcrypt');

// model
const User = require("../../models/User");

// send email service
const transporter = require("../../config/common/mailer")
// upload file (image,images, video)
const uploadFile = require("../../config/common/upload")


// apis
//login
router.get("/admin/login", (req, res) => {
    res.render('Login/Login');
});

router.post("/admin/login", async (req, res) => {
    try {
        const { username, password } = req.body;
        console.log(req.body);

        if (!username || !password) {
            return res.status(400).json({ message: 'Thiếu username hoặc password' });
        }
        // Tìm người dùng trong cơ sở dữ liệu
        const user = await User.findOne({ username: username, password: password });
        if (!user) {
            return res.status(401).json({ message: 'Tài khoản không tồn tại' });
        }
        if (user.role !== 'admin') {
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
        return res.status(400).send({ error: 'Lỗi trong quá trình đăng nhập', details: error.message });
    }
});

//đăng ký thường
router.get("/register1", async (req, res) => {
    res.render("Login/Register");
})
router.post("/register", async (req, res) => {
    try {
        if (req.method === "POST") {
            const { username, password, email, phoneNumber, role, name, dob, gender, address, profile_picture_url } = req.body;
            const existingUser = await User.findOne({ username });
            if (existingUser) {
                return res.status(400).json({ error: 'Username đã tồn tại. Vui lòng chọn username khác.' });
            }
            let objAccount = new User({
                username: username,
                password: password,
                email: email,
                phoneNumber: phoneNumber,
                role: role,
                name: name,
                dob: dob,
                gender: gender,
                address: address,
                profile_picture_url: profile_picture_url,
                created_at: new Date().toISOString(),
                updated_at: new Date().toISOString()
            });
            await objAccount.save();
            let msg = 'Thêm thành công id mới: ' + objAccount._id;
            console.log(msg);
            return res.json(msg);
        } else {
            return res.status(400).send('Không hợp lệ');
        }
    } catch (error) {
        console.error(error);
        return res.status(500).send('Lỗi server rồi');
    }
});
//Login thường
router.post("/login", async (req, res) => {
    try {
        const { username, password } = req.body;
        if (!username || !password) {
            return res.status(400).json({ message: 'Thiếu username hoặc password' });
        }
        // Tìm người dùng trong cơ sở dữ liệu
        const user = await User.findOne({ username });
        if (!user) {
            return res.status(401).json({ message: 'Tài khoản không tồn tại' });
        }
        if (user.role == 'ban') {
            return res.status(401).json({ message: 'Tài khoản của bạn đã bị khóa bởi ADMIN, vui lòng liên hệ với ADMIN để giải quyết.' });
        }
        // // Tạo JWT

        const token = jwt.sign({ id: user._id, role: user.role }, 'hoan', {
            expiresIn: '1y',
        });
        console.log("token khi dang nhap: ", token)
        res.json({ message: "Đăng nhập thành công", token });
    } catch (error) {
        console.error(error, " Password: " + req.body.password);
        return res.status(400).send({ error: 'Lỗi trong quá trình đăng nhập', details: error.message });
    }
});
module.exports = router