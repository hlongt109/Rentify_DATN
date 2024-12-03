const express = require('express');
const router = express.Router();
const mongoose = require('mongoose');

const User = require('../../models/User')
// hiển thị danh sách người dùng 
router.get('/listusers', async (req, res) => {
    try {
      const users = await User.find(); // Fetch all users from the database
      res.status(200).json({
        success: true,
        message: "Fetched all users successfully",
        data: users,
      });
    } catch (error) {
      res.status(500).json({
        success: false,
        message: "An error occurred while fetching users",
        error: error.message,
      });
    }
  });
// hiển thị chi tiết theo id 
router.get('/getUser/:id', async (req, res) => {
    try {
        // Lấy id từ params
        const { id } = req.params;

        // Tìm người dùng theo _id
        const user = await User.findById(id);

        // Kiểm tra nếu người dùng không tồn tại
        if (!user) {
            return res.status(404).json({
                success: false,
                message: 'Không tìm thấy người dùng'
            });
        }

        // Trả về thông tin chi tiết người dùng
        res.status(200).json(user);
    } catch (error) {
        // Xử lý lỗi
        console.error(error);
        res.status(500).json({
            success: false,
            message: 'Đã xảy ra lỗi khi lấy thông tin người dùng'
        });
    }
});
module.exports = router;