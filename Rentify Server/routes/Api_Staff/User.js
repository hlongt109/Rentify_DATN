const express = require('express');
const router = express.Router();
const mongoose = require('mongoose');

const Contract = require('../../models/Contract');
const Building = require('../../models/Building');
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
// thêm gender,dob,address theo id 
router.put("/addUserInfo/:id", async (req, res) => {
  try {
    const { id } = req.params; // Lấy ID từ URL
    const { gender, dob, address } = req.body; // Thông tin cần bổ sung từ body

    // Tìm và cập nhật thông tin người dùng
    const updatedUser = await User.findByIdAndUpdate(
      id,
      {
        $set: {
          gender,
          dob,
          address,
          updated_at: new Date().toISOString(), // Thời gian cập nhật
        },
      },
      { new: true } // Trả về thông tin sau khi cập nhật
    );

    // Nếu không tìm thấy người dùng
    if (!updatedUser) {
      return res.status(404).json({
        success: false,
        message: "Không tìm thấy người dùng để cập nhật",
      });
    }

    // Trả về thông tin người dùng sau khi cập nhật
    res.status(200).json({
      success: true,
      message: "Bổ sung thông tin thành công",
      data: updatedUser,
    });
  } catch (error) {
    console.error("Lỗi khi cập nhật thông tin người dùng:", error);
    res.status(500).json({
      success: false,
      message: "Đã xảy ra lỗi khi cập nhật thông tin người dùng",
      error: error.message,
    });
  }
});
// api lấy servicerFees 
router.get('/serviceFeesUser/:userId', async (req, res) => {
  try {
      const { userId } = req.params;

      // Tìm hợp đồng theo user_id (có thể có nhiều hợp đồng)
      const contracts = await Contract.find({ 'user_id': userId })
          .populate({
              path: 'building_id',
              select: 'nameBuilding address description service serviceFees number_of_floors',
              populate: {
                  path: 'service',
                  select: 'name', // Dữ liệu dịch vụ liên quan đến tòa nhà
              },
          })
          .exec();

      // Nếu không tìm thấy hợp đồng
      if (contracts.length === 0) {
          return res.status(404).json('Không tìm thấy hợp đồng cho người dùng này.');
      }

      // Trả về trực tiếp danh sách hợp đồng
      res.status(200).json(contracts);
  } catch (error) {
      console.error(error);
      res.status(500).json('Đã xảy ra lỗi khi lấy thông tin hợp đồng');
  }
});

module.exports = router;