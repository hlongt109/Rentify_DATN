var express = require("express");
var router = express.Router();
// mongoose library
const mongoose = require("mongoose");
// model
const Account = require("../../models/User");
//Post
const Post = require("../../models/Post");
//room
const Room = require("../../models/Room");
const Building = require("../../models/Building");
// send email service
// const transporter = require("../cSDonfig/common/mailer");
// upload file (image, video)
const uploadFile = require("../../config/common/multer");
//thu vien token
const jwt = require("jsonwebtoken");
const key = "Rentify_token_key";
//mail
const transporter = require("../../config/common/mailer");
//bcrypt bam mat khau
const bcrypt = require("bcrypt");
const { verifiedEmail } = require("../../config/common/mailer");
const { forgotEmail } = require("../../config/common/mailer");
const Booking = require("../../models/Booking");
const User = require("../../models/User");
//url
const url = "http://localhost:3000/api/confirm-email/";

// routers

// Middleware để xác thực token
const verifyToken = (req, res, next) => {
  const token = req.headers["authorization"];
  console.log(token);

  if (!token) {
    return res.status(403).json({ message: "Yêu cầu token để truy cập" });
  }

  try {
    // Kiểm tra token hợp lệ
    const tokenValue = token.split(" ")[1];
    const decoded = jwt.verify(tokenValue, key);
    req.Account = decoded; // Gán thông tin người dùng đã giải mã vào request
    next();
  } catch (error) {
    return res.status(401).json({ message: "Token không hợp lệ hoặc hết hạn" });
  }
};

router.post("/login-user", async (req, res) => {
  try {
    const { email, password } = req.body;
    const user = await Account.findOne({ email: email });

    if (user) {
      const match = await bcrypt.compare(password, user.password);
      console.log(user);
      if (user.verified) {
        if (match) {
          return res.json({
            status: 200,
            message: "login success",
            data: user,
          });
        } else {
          // Trả về khi mật khẩu không khớp
          return res.status(400).send("Mật khẩu không khớp");
        }
      } else {
        return res.status(401).send("Tài khoản của bạn chưa được xác minh");
      }
    } else {
      return res.status(404).send("Tài khoản không tồn tại");
    }
  } catch (error) {
    console.error(error);
    return res.status(500).send("Lỗi hệ thống");
  }
});

//active account
router.get("/confirm-email/:userId", async (req, res) => {
  try {
    console.log("userId:", req.params.userId); // Log ID
    const user = await Account.findOne({
      _id: req.params.userId,
    });

    if (!user) {
      return res.status(400).send("Invalid token");
    }

    await Account.updateOne({ _id: user._id }, { $set: { verified: true } });
    console.log("log thu user", user);

    res.send("Email verified");
  } catch (error) {
    console.error("An error occurred:", error);
    res.status(500).send("Internal Server Error");
  }
});
//dang ky
router.post("/register-user", async (req, res) => {
  try {
    const data = req.body;
    let user = await Account.findOne({ email: data.email });
    if (user) {
      return res.status(400).send("Email đã tồn tại");
    }
    const hashPassword = await bcrypt.hash(data.password, 10);
    user = Account({
      email: data.email,
      password: hashPassword,
      name: data.name,
      username: data.name,
      phoneNumber: data.phoneNumber,
    });

    const result = await user.save();
    console.log(result);
    await verifiedEmail(user.email, url + user._id);
    console.log(verifiedEmail(user.email, url + user._id));

    res.status(200).send({
      message: "Register success",
      user: result,
    });
  } catch (error) {
    console.error("Registration failed:", error);
    res.status(500).send("Internal Server Error");
  }
});
// đoạn này thiên code
router.get("/landlord/:building_id", async (req, res) => {
  try {
    const { building_id } = req.params;
    if (!mongoose.Types.ObjectId.isValid(building_id)) {
      return res.status(400).json({ message: "ID tòa nhà không hợp lệ" });
    }
    const building = await Building.findById(building_id).populate("landlord_id").populate("manager_id");
    if (!building) {
      return res.status(404).json({ message: "Không tìm thấy tòa nhà" });
    }
    const landlord = building.landlord_id;
    const manager = building.manager_id;
    if (!landlord) {
      return res.status(404).json({ message: "Không tìm thấy thông tin landlord" });
    }
    res.status(200).json({
      landlord,
      manager
    });
  } catch (error) {
    console.error("Lỗi khi lấy thông tin landlord và danh sách người dùng:", error.message);
    res.status(500).json({ message: "Lỗi server", error: error.message });
  }
});

router.put("/updateAccountUser/:id", async (req, res) => {
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
      bankAccount,
    } = req.body; // Lấy các thông tin cần chỉnh sửa từ body

    // Kiểm tra vai trò hợp lệ
    const validRoles = ["admin", "landlord", "staffs", "user", "ban"];
    if (role && !validRoles.includes(role)) {
      return res.status(400).json({
        success: false,
        message: "Vai trò không hợp lệ",
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
        message: "Không tìm thấy người dùng để cập nhật",
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

router.get("/landlord/:building_id", async (req, res) => {
  try {
    const { building_id } = req.params;
    if (!mongoose.Types.ObjectId.isValid(building_id)) {
      return res.status(400).json({ message: "ID tòa nhà không hợp lệ" });
    }
    const building = await Building.findById(building_id)
      .populate("landlord_id")
      .populate("manager_id");
    if (!building) {
      return res.status(404).json({ message: "Không tìm thấy tòa nhà" });
    }
    const landlord = building.landlord_id;
    const manager = building.manager_id;
    if (!landlord) {
      return res
        .status(404)
        .json({ message: "Không tìm thấy thông tin landlord" });
    }
    res.status(200).json({
      landlord,
      manager,
    });
  } catch (error) {
    console.error(
      "Lỗi khi lấy thông tin landlord và danh sách người dùng:",
      error.message
    );
    res.status(500).json({ message: "Lỗi server", error: error.message });
  }
});

router.get("/get-user-infor/:userId", async (req, res) => {
  try {
    const { userId } = req.params;
    const user = await User.findOne({ _id: userId });
    res.status(200).json({
      status: 200,
      message: "Lấy thông tin thành công",
      data: user,
    });
  } catch (error) {
    res
      .status(500)
      .json({ status: 500, message: "Lỗi hệ thống", error: error.message });
  }
});

module.exports = router;
