const express = require('express');
const router = express.Router();

const User = require('../../models/User');
///sconst authenticate = require('../../middleware/authenticate');
const { decode } = require('jsonwebtoken');


function authenticate(req, res, next) {
    try {
        const authHeader = req.headers['authorization'];
        if (!authHeader) {
            return res.status(403).json({ message: "Không có phản hồi từ Token" });
        }

        // Lấy token từ chuỗi "Bearer <token>"
        const token = authHeader.split(' ')[1];
        if (!token) {
            return res.status(403).json({ message: "Không có phản hồi từ Token" });
        }

        // Xác thực token
        jwt.verify(token, 'hoan', (err, decoded) => {
            if (err) {
                return res.status(403).json({ message: "Lỗi xác thực Token" });
            }
            req.user_id = decoded.id; // Gán thông tin người dùng từ token vào request
            next(); // Cho phép đi tiếp
        });
    } catch (error) {
        return res.status(401).json({ message: "Lỗi xác thực", error: error.message });
    }
}

router.get('/staffs_mgr', async (req, res) => {
    res.render('chutoa_web/ejs/QuanLyNhanVien', (err, html) => {
        if (err) {
            return res.status(500).send(err);
        }
        res.render('chutoa_web/ejs/index', {
            title: 'Quản lý người dùng',
            body: html
        });
    });
})
// Tạo tài khoản mới
router.post("/create", async (req, res) => {
    try {
        const newUser = new User({
            ...req.body,
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
router.get("/staffs/list", authenticate, async (req, res) => {
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
router.put("/:id", async (req, res) => {
    try {
        const updatedUser = await User.findByIdAndUpdate(
            req.params.id,
            { ...req.body, updated_at: new Date().toISOString() },
            { new: true }
        );

        if (!updatedUser) {
            return res.status(404).json({ message: "Tài khoản không tồn tại" });
        }
        res.status(200).json({ message: "Cập nhật thành công", user: updatedUser });
    } catch (error) {
        res.status(500).json({ message: "Đã có lỗi xảy ra", error });
    }
});

// Xóa tài khoản theo ID
router.delete("/:id", async (req, res) => {
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

module.exports = router;
