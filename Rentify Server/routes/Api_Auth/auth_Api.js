var express = require('express');
var router = express.Router();

const jwt = require('jsonwebtoken');
const bcrypt = require('bcrypt');
// model
const User = require("../../models/User");
// send email service
const { transporter } = require("../../config/common/mailer")
// upload file (image,images, video)
const uploadFile = require("../../config/common/upload");
const handleServerError = require("../../utils/errorHandle");

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
            bankAccount: [],
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
// check bankAccount
router.get("/rentify/user/:id", async (req, res) => {
    try {
        const { id } = req.params;

        const user = await User.findById(id);
        if (!user) {
            return res.status(404).json({ message: "User not found" });
        }

        const hasBankAccount = user.bankAccount && user.bankAccount.bank_name && user.bankAccount.bank_number && user.bankAccount.qr_bank;

        res.status(200).json({
            data: user,
            hasBankAccount: !!hasBankAccount
        });
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: "Internal server error" });
    }
});

// cập nhật thông tin bank
router.put("/rentify/user/:id", uploadFile.single('qr_bank'), async (req, res) => {
    try {
        const { id } = req.params;
        const { bank_name, bank_number, username } = req.body; // Lấy dữ liệu từ form
        const file = req.file; // Lấy file (ảnh mã QR)

        const user = await User.findById(id);
        if (!user) {
            return res.status(404).json({ messenger: 'User not found' });
        }

        let qrImage = "";
        if (file) {
            qrImage = `${req.protocol}://${req.get("host")}/public/uploads/${file.filename}`;
        }

        user.bankAccount = {
            bank_name,
            bank_number,
            qr_bank: qrImage, 
            username,
        };

        await user.save();

        return res.status(200).json({
            messenger: 'User information updated successfully',
            data: user
        });

    } catch (error) {
        console.error(error);
        handleServerError(res, error)
    }
});

module.exports = router