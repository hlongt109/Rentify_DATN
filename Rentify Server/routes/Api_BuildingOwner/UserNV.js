const express = require('express');
const router = express.Router();
const mongoose = require('mongoose');

const User = require('../../models/User');
const { decode } = require('jsonwebtoken');
const bcrypt = require("bcrypt");


router.get('/staffs_mgr/list/:id', async (req, res) => {
    try {
        const userId = req.params.id;

        if (!mongoose.Types.ObjectId.isValid(userId)) {
            return res.status(400).json({ message: "Invalid landlord_id format" });
        }

        const landlordObjectId = new mongoose.Types.ObjectId(userId);
        const data = await User.find({ landlord_id: landlordObjectId });

        if (data.length === 0) {
            console.log("Không có dữ liệu");
            return res.render("Landlord_website/screens/QuanLyNhanVien.ejs", { data: [] });
        }

        res.json({ data });
    } catch (error) {
        console.error("Error fetching services:", error.message);
        res.status(500).render("Landlord_website/screens/QuanLyNhanVien", { data: [] });
    }
})
// Tạo tài khoản mới
router.post("/staffs_mgr/add/:id", async (req, res) => {
    try {
        const userId = req.params.id;
        // Kiểm tra dữ liệu đầu vào (req.body)
        const { username, email, password, role, name, phoneNumber, dob, gender, address } = req.body;
        if (!username || !email || !password || !role) {
            return res.status(400).json({ message: "Thiếu dữ liệu cần thiết!" });
        }
        const hashPassword = await bcrypt.hash(password, 10);
        // Tạo mới tài khoản
        const newUser = new User({
            username,
            email,
            password: hashPassword,
            role,
            landlord_id: userId,  // Gán id người dùng chủ sở hữu
            name,
            phoneNumber,
            dob,
            gender,
            address,
            created_at: new Date().toISOString(),
            updated_at: new Date().toISOString()
        });

        await newUser.save();
        res.status(201).json({ message: "Tài khoản đã được tạo thành công", user: newUser });
    } catch (error) {
        res.status(500).json({ message: "Đã có lỗi xảy ra", error });
    }
});
// Lấy danh sách tất cả tài khoản
router.get("/staffs/list", async (req, res) => {
    try {
        const { userId, role } = req.decoded;  // Lấy thông tin người dùng từ token (JWT)
        console.log(decode);

        // Kiểm tra xem người dùng có role là landlord không
        if (role !== "landlord") {
            return res.status(403).json({ message: "Bạn không có quyền truy cập vào tài nguyên này" });
        }

        const accounts = await User.find({ landlord_id: userId });
        res.status(200).json(accounts); // Trả về danh sách tài khoản
    } catch (error) {
        res.status(500).json({ message: "Đã có lỗi xảy ra", error });
    }
});

// Lấy thông tin tài khoản theo ID
router.get("/:id", async (req, res) => {
    try {
        const user = await User.findById(req.params.id);
        if (!user) {
            return res.status(404).json({ message: "Tài khoản không tồn tại" });
        }
        res.status(200).json(user);
    } catch (error) {
        res.status(500).json({ message: "Đã có lỗi xảy ra", error });
    }
});
// Cập nhật tài khoản theo ID
// Cập nhật role của tài khoản theo ID
router.put("/staffs_mgr/edit/:id", async (req, res) => {
    try {
        const { role } = req.body;
        if (!role) {
            return res.status(400).json({ message: "Cần cung cấp role để cập nhật" });
        }

        const updatedUser = await User.findByIdAndUpdate(
            req.params.id,
            {
                role,
                updated_at: new Date().toISOString()
            },
            { new: true }
        );

        if (!updatedUser) {
            return res.status(404).json({ message: "Tài khoản không tồn tại" });
        }

        res.status(200).json({ message: "Cập nhật role thành công", user: updatedUser });
    } catch (error) {
        res.status(500).json({ message: "Đã có lỗi xảy ra", error });
    }
});


// Xóa tài khoản theo ID
router.delete("/delete_mgr/delete/:id", async (req, res) => {
    try {
        const deletedUser = await User.findByIdAndDelete(req.params.id);
        if (!deletedUser) {
            return res.status(404).json({ message: "Tài khoản không tồn tại" });
        }
        res.status(200).json({ message: "Xóa tài khoản thành công" });
    } catch (error) {
        res.status(500).json({ message: "Đã có lỗi xảy ra", error });
    }
});
//lay ten nguoi dung de booking
router.get("/booking/name", async (req, res) => {
    const userId = req.query.userId; // Lấy userId từ query string

    if (!mongoose.Types.ObjectId.isValid(userId)) {
        return res.status(400).json({ message: 'ID người dùng không hợp lệ.' });
    }

    try {
        // Tìm người dùng theo userId
        const user = await User.findById(userId); // Lấy trường 'name' từ User

        if (!user) {
            return res.status(404).json({ message: 'Người dùng không tìm thấy' });
        }

        res.json({
            status: 200,
            message: 'Lấy tên người dùng thành công',
            data: user.name // Trả về tên người dùng
        });
    } catch (error) {
        console.error('Lỗi khi lấy dữ liệu từ API:', error);
        res.status(500).json({ message: 'Lỗi server' });
    }
});

module.exports = router;
