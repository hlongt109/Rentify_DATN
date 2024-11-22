const express = require('express');
const router = express.Router();
const mongoose = require('mongoose');

const User = require('../../models/User')

// LẤY THÔNG TIN NGƯỜI DÙNG 
router.get('/userr/:id', async (req, res) => {
    try {
      const { id } = req.params;
  
      // Tìm người dùng theo ID
      const user = await User.findById(id);
  
      // Kiểm tra nếu người dùng không tồn tại
      if (!user) {
        return res.status(404).json({
          success: false,
          message: 'Không tìm thấy người dùng'
        });
      }
  
      // Trả về thông tin chi tiết người dùng
      res.status(200).json({
        success: true,
        data: user
      });
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
