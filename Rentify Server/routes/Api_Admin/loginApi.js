var express = require('express');
var router = express.Router();
// model
const User = require("../../models/User");

const jwt = require('jsonwebtoken');
const bcrypt = require('bcrypt');




// send email service
const transporter = require("../../config/common/mailer")
// upload file (image,images, video)
const uploadFile = require("../../config/common/upload")
const authenticate = require('../../middleware/authenticate');
const checkRole = require('../../middleware/checkRole');

// apis
router.get("/home", authenticate, checkRole, (req, res) => {
    res.render('Home/Home');
})
//login
router.get("/admin/login", (req, res) => {
    res.render('Login/Login');
});

router.post("/admin/login", async (req, res) => {
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
        if (user.role !== 'admin') {
            return res.status(401).json({ message: 'Bạn không có quyền truy cập!' });
        }
        if (user.role == 'ban') {
            return res.status(401).json({ message: 'Tài khoản của bạn đã bị khóa bởi ADMIN' });
        }
        // Tạo JWT
        const token = jwt.sign({ id: user._id, role: user.role }, 'hoan', { expiresIn: '1000h' });
        //token
        // const token = await user.generateAuthToken()
        // user.token = token;
        const userID = user._id;

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
        console.log("Token khi đăng nhập: ", token)
        res.json({ message: "Đăng nhập thành công", token });
    } catch (error) {
        console.error(error, " Password: " + req.body.password);
        return res.status(400).send({ error: 'Lỗi trong quá trình đăng nhập', details: error.message });
    }
});
router.get("/rentify/login", async (req, res) => {
    res.render('Auth/Login');
})

router.post("/rentify/login", async (req, res) => {
    try {
        const { username, password } = req.body;
        console.log("Received login request with username:", username); // Kiểm tra username nhận được từ client

        if (!username || !password) {
            return res.status(400).json({ message: 'Thiếu username hoặc password' });
        }

        // Tìm người dùng trong cơ sở dữ liệu
        const user = await User.findOne({ username: username.toLowerCase() });
        console.log("Tìm kiếm người dùng với tên đăng nhập:", username);

        console.log("User found in DB:", user);  // Kiểm tra xem người dùng có tồn tại trong cơ sở dữ liệu không

        if (!user) {
            return res.status(401).json({ message: 'Tài khoản không tồn tại' });
        }

        // So sánh mật khẩu
        const isPasswordValid = await bcrypt.compare(password, user.password);
        console.log("Password valid:", isPasswordValid);  // Kiểm tra xem mật khẩu có đúng không

        if (!isPasswordValid) {
            return res.status(401).json({ message: 'Tên đăng nhập hoặc mật khẩu không đúng' });
        }

        // Tạo JWT token nếu mật khẩu đúng
        const token = jwt.sign({ landlord_id: user.landlord_id }, 'hoan', { expiresIn: '1h' });

        return res.json({
            token,
            data: {
                _id: user._id,
                username: user.username,
                role: user.role,
                landlord_id: user.landlord_id,
            }
        });
    } catch (error) {
        console.error("Error in login:", error);
        return res.status(400).send({ error: 'Lỗi trong quá trình đăng nhập', details: error.message });
    }
});

module.exports = router