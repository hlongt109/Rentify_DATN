const express = require('express');
const router = express.Router();
const mongoose = require('mongoose');

const Contract = require('../../models/Contract');
const Building = require('../../models/Building');
const upload = require('../../config/common/uploadImageBank')
const uploadUser = require('../../config/common/UploadImageUser')
const User = require('../../models/User')
// #1.hiển thị danh sách người dùng _vanphuc
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
// #2.hiển thị chi tiết theo id _vanphuc
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
// #3.Bổ sung thông tin cho User _vanphuc :
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
// #4.api lấy servicerFees 
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

// #5.API to fetch bank account details by userId _vanphuc
router.get('/getBankAccount/:userId', async (req, res) => {
  try {
    const { userId } = req.params;

    // Find the user by ID and select only the bankAccount field
    const user = await User.findById(userId).select('bankAccount');

    // If the user is not found
    if (!user) {
      return res.status(404).json({
        success: false,
        message: 'Không tìm thấy người dùng',
      });
    }

    // Check if the bankAccount field exists
    if (!user.bankAccount) {
      return res.status(404).json({
        success: false,
        message: 'Người dùng này không có thông tin tài khoản ngân hàng',
      });
    }

    // Directly return the bankAccount details
    res.status(200).json(user.bankAccount);
  } catch (error) {
    console.error(error);
    res.status(500).json({
      success: false,
      message: 'Đã xảy ra lỗi khi lấy thông tin tài khoản ngân hàng',
      error: error.message,
    });
  }
});
// #6.update tài khoản ngân hàng 
router.put("/updateBankAccount/:userId", upload.array("qr_bank", 5), async (req, res) => {
  try {
    const { userId } = req.params;
    const { bank_name, bank_number, username } = req.body;

    // Tìm người dùng
    const user = await User.findById(userId);
    if (!user) {
      return res.status(404).json({
        message: "Không tìm thấy người dùng",
      });
    }

    // Cập nhật thông tin tài khoản ngân hàng
    if (bank_name !== undefined) user.bankAccount.bank_name = bank_name;
    if (bank_number !== undefined) user.bankAccount.bank_number = bank_number;
    if (username !== undefined) user.bankAccount.username = username;

    // Xử lý các file upload
    if (req.files && req.files.length > 0) {
      // Lưu đường dẫn các file
      // Loại bỏ phần 'public' để lưu đường dẫn tương đối
      user.bankAccount.qr_bank = req.files.map(file => file.path)
    }

    // Lưu thay đổi
    await user.save();

    // Trả về response mà không có trường 'success'
    res.status(200).json({
      bank_name: user.bankAccount.bank_name,
      bank_number: user.bankAccount.bank_number,
      qr_banks: user.bankAccount.qr_bank,
      username: user.bankAccount.username,
    });

  } catch (error) {
    console.error(error);
    res.status(500).json({
      message: "Đã xảy ra lỗi khi cập nhật thông tin tài khoản ngân hàng",
      error: error.message,
    });
  }
});

// #7.API chỉnh sửa toàn bộ thông tin người dùng _vanphuc
router.put('/updateTaiKhoan/:id', async (req, res) => {
  try {
    const { id } = req.params;
    const {
      username,
      email,
      phoneNumber,
      role,
      name,
      dob,
      gender,
      address,
      profile_picture_url,
      verified,
      landlord_id,
      bankAccount,
    } = req.body; // Lấy các thông tin cần chỉnh sửa từ body

    // Kiểm tra vai trò hợp lệ
    const validRoles = ["admin", "landlord", "staffs", "user", "ban"];
    if (role && !validRoles.includes(role)) {
      return res.status(400).json({
        success: false,
        message: "Vai trò không hợp lệ"
      });
    }

    // Tạo đối tượng cập nhật
    const updateData = {
      username,
      email,
      phoneNumber,
      role,
      name,
      dob,
      gender,
      address,
      profile_picture_url,
      verified,
      landlord_id,
      updated_at: new Date().toISOString(), // Cập nhật thời gian
    };

    // Kiểm tra xem có cần cập nhật tài khoản ngân hàng không
    if (bankAccount) {
      updateData.bankAccount = bankAccount; // Cập nhật thông tin tài khoản ngân hàng nếu có
    }

    // Tìm và cập nhật thông tin người dùng
    const updatedUser = await User.findByIdAndUpdate(
      id,
      { $set: updateData },
      { new: true } // Trả về thông tin người dùng đã cập nhật
    );

    if (!updatedUser) {
      return res.status(404).json({
        success: false,
        message: "Không tìm thấy người dùng để cập nhật"
      });
    }

    // Trả về đối tượng người dùng đã cập nhật mà không có "success", "message", "data"
    res.status(200).json(updatedUser);
  } catch (error) {
    console.error("Lỗi khi chỉnh sửa thông tin người dùng:", error);
    res.status(500).json({
      success: false,
      message: "Đã xảy ra lỗi khi chỉnh sửa thông tin người dùng",
      error: error.message,
    });
  }
});
//#8 add avatar
router.post('/addImageUser/:id', uploadUser.single('profile_picture_url'), async (req, res) => {
  try {
    const { id } = req.params;

    // Kiểm tra xem file ảnh có được upload không
    if (!req.file) {
      return res.status(400).json({
        message: 'Không có file nào được tải lên',
      });
    }

    // Cập nhật đường dẫn ảnh profile_picture_url
    const profile_picture_url = req.file.path.replace('public/', ''); // Lưu đường dẫn tương đối
    const updatedUser = await User.findByIdAndUpdate(
      id,
      { $set: { profile_picture_url, updated_at: new Date().toISOString() } },
      { new: true, select: 'profile_picture_url' } // Chỉ trả về field profile_picture_url
    );

    // Nếu không tìm thấy người dùng
    if (!updatedUser) {
      return res.status(404).json({
        message: 'Không tìm thấy người dùng để cập nhật hình ảnh',
      });
    }

    // Trả về thông tin ảnh đã cập nhật
    res.status(200).json({
      profile_picture_url: updatedUser.profile_picture_url,
    });
  } catch (error) {
    console.error('Lỗi khi thêm hình ảnh người dùng:', error);
    res.status(500).json({
      message: 'Đã xảy ra lỗi khi thêm hình ảnh người dùng',
      error: error.message,
    });
  }
});
// #9. Hiển thị ảnh profile theo ID người dùng
router.get('/getImageUser/:id', async (req, res) => {
  try {
    const { id } = req.params;

    // Tìm người dùng theo ID
    const user = await User.findById(id).select('profile_picture_url');

    // Nếu không tìm thấy người dùng
    if (!user) {
      return res.status(404).json({
        message: 'Không tìm thấy người dùng',
      });
    }

    // Trả về URL của ảnh profile
    res.status(200).json({
      profile_picture_url: user.profile_picture_url,
    });
  } catch (error) {
    console.error('Lỗi khi lấy thông tin ảnh profile:', error);
    res.status(500).json({
      message: 'Đã xảy ra lỗi khi lấy thông tin ảnh profile',
      error: error.message,
    });
  }
});

module.exports = router;