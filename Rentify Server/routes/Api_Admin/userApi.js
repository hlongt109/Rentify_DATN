var express = require('express');
var router = express.Router();

const User = require("../../models/User");
//Lấy danh sách người dùng

router.get("/user/show-list", async (req, res) => {
    try {
        const data = await User.find();
        res.render('UserManagement/UserManagement', { data });
    } catch (error) {
        console.error(error);
        res.status(500).send('Lỗi server');
    }
});

router.get("/user/list", async (req, res) => {
    try {
        const find = await User.find();
        return res.status(200).json({
            status: 200,
            message: "Lấy dữ liệu thành công",
            find
        });
    } catch (error) {
        console.error(error);
        res.status(500).send('Lỗi server');
    }
});

// Sửa role người dùng
router.put("/user/update/:id", async (req, res) => {
    try {
        const findID = req.params.id;
        const { role } = req.body; // Chỉ lấy role từ request body

        // Tìm kiếm người dùng theo ID và cập nhật role
        const upDB = await User.findByIdAndUpdate(
            findID,
            { role }, // Chỉ cập nhật trường role
            { new: true, runValidators: true } // new: true để trả về đối tượng cập nhật, runValidators: true để chạy các validators
        );

        if (!upDB) {
            return res.status(404).json({ message: 'Không tìm thấy tài khoản' });
        }
        let msg = 'Sửa thành công id: ' + findID;
        console.log(msg);
        return res.status(200).json({ message: 'Cập nhật thành công', user: upDB });
    } catch (error) {
        let msg = "Lỗi: " + error.message;
        return res.status(500).json({ message: msg });
    }
});

// doi mat khau
router.put("/account/pass/update/:id", async (req, res) => {
    const userId = req.params.id; // Lấy userId từ URL
    const { currentPassword, newPassword } = req.body;

    if (!userId || !currentPassword || !newPassword) {
        return res.status(400).json({ message: 'Thiếu thông tin yêu cầu' });
    }

    try {
        // Tìm người dùng theo userId
        const user = await User.findById(userId);
        if (!user) {
            return res.status(404).json({ message: 'Người dùng không tồn tại' });
        }

        let isPasswordValid = false;

        // Kiểm tra mật khẩu hiện tại
        if (user.password.startsWith('$2b$')) {
            // Nếu mật khẩu là mã hóa, so sánh bằng bcrypt
            isPasswordValid = await bcrypt.compare(currentPassword, user.password);
        } else {
            // Nếu mật khẩu chưa mã hóa, so sánh trực tiếp
            isPasswordValid = currentPassword === user.password;
        }

        if (!isPasswordValid) {
            return res.status(401).json({ message: 'Mật khẩu hiện tại không chính xác' });
        }

        // Mã hóa mật khẩu mới
        const hashedNewPassword = await bcrypt.hash(newPassword, 10);

        // Cập nhật mật khẩu mới vào cơ sở dữ liệu
        const updateResult = await User.updateOne(
            { _id: userId }, // Sử dụng filter là đối tượng { _id: userId }
            { $set: { password: hashedNewPassword } } // Cập nhật mật khẩu mới đã mã hóa
        );

        if (updateResult.nModified === 0) {
            return res.status(500).json({ message: 'Cập nhật mật khẩu thất bại' });
        }
        res.status(200).json({ message: 'Đổi mật khẩu thành công' });
    } catch (error) {
        console.error(error);
        res.status(500).json({ message: 'Có lỗi xảy ra khi đổi mật khẩu' });
    }
});
//nhảy màn đổi mật khẩukhẩu
router.get("/login/get_pass", async (req, res) => {
    res.render("Auth/Pass");
})

const nodemailer = require('nodemailer');

const transporter = nodemailer.createTransport({
    host: 'smtp.gmail.com',
    port: 587,
    secure: false,
    auth: {
        user: 'rentify66668888@gmail.com',
        pass: 'nreg uwuz rwvl xbux',
    },
});

const crypto = require('crypto');
const bcrypt = require('bcrypt');
// Route: Gửi mật khẩu qua email
router.post("/send_password", async (req, res) => {
    const { email } = req.body;

    if (!email) {
        return res.status(400).json({ message: 'Email is required!' });
    }

    try {
        const user = await User.findOne({ email });
        if (!user) {
            return res.status(404).json({ message: 'User not found!' });
        }
        // Tạo mật khẩu mới 
        const newPassword = crypto.randomBytes(8).toString('hex');
        const hashedPassword = await bcrypt.hash(newPassword, 10);
        user.password = hashedPassword;
        await user.save();

        // Gửi email với mật khẩu của người dùng    
        await transporter.sendMail({
            from: '"Hỗ trợ mật khẩu" <rentify66668888@gmail.com>', // Địa chỉ email gửi
            to: user.email, // Địa chỉ người nhận
            subject: 'Rentify đã đến nơi đây',
            html: `<p>Xin chào người dùng ${user.name},</p>
                   <p>Đây sẽ là mật khẩu mới của bạn: <strong><h4>${newPassword}</h4></strong></p>
                   <p>Hãy nhớ đổi mật khẩu mới nhé</p>
                `,
        });

        res.status(200).json({ message: 'Password sent to your email!' });
    } catch (error) {
        console.error('Error:', error);
        res.status(500).json({ message: 'Internal server error.' });
    }
});



module.exports = router