const express = require('express');
const router = express.Router();
const jwt = require('jsonwebtoken');
const bcrypt = require('bcrypt');

// Model
const User = require("../../models/User");

// Đăng nhập cho nhân viên
router.post("/", async (req, res) => {
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
            return res.status(401).json({ message: 'Tài khoản của bạn đã bị khóa bởi ADMIN' });
        }else if (user.role !== 'staffs') {
            return res.status(401).json({ message: 'Bạn không có quyền truy cập!' });
        }
       
        // Tạo JWT
        const token = jwt.sign({ id: user._id, role: user.role }, 'hoan', {
            expiresIn: '1h',
        });
        console.log("Token: ", token);

        res.json({ message: "Đăng nhập thành công", token });
    } catch (error) {
        console.error(error, " Password: " + req.body.password);
        return res.status(400).send({ error: 'Lỗi trong quá trình đăng nhập', details: error.message });
    }
});

module.exports = router;
