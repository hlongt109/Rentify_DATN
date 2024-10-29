var express = require("express");
var router = express.Router();
// model
const Account = require("../../models/User");
//Post
const Post = require("../../models/Post");
//room
const Room = require("../../models/Room");
// send email service
// const transporter = require("../config/common/mailer");
// upload file (image, video)
const uploadFile = require("../../config/common/multer");
//thu vien token
const jwt = require("jsonwebtoken");
const key = "Rentify_token_key";
//mail
const transporter = require("../../config/common/mailer");
//bcrypt bam mat khau
const bcrypt = require("bcrypt");
const verifiedEmail = require("../../config/common/mailer");
const Booking = require("../../models/Booking");
//url
const url = "http://localhost:3000/api/confirm/";

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

router.post("/login", async (req, res) => {
  try {
    const { username, password } = req.body;
    const user = await Account.findOne({ username: username });
    if (user) {
      const match = await bcrypt.compare(password, user.password);
      if (match) {
        //token
        const token = jwt.sign({ id: user._id }, key, {
          expiresIn: "1d",
        });
        //khi token hết hạn, người dùng sẽ call 1 api khác để lấy token mới
        //Lúc này người dùng sẽ truyền refreshToken lên để nhận về 1 cặp token, refershToken mới
        //Nếu cả 2 token đều hết hạn người dùng sẽ phải thoát app và đăng nhập lại
        const refreshToken = jwt.sign({ id: user._id }, key, {
          expiresIn: "1d",
        });
        return res.json({
          status: 200,
          message: "login success",
          data: user,
          token: token,
          refreshToken: refreshToken,
        });
      } else {
        // Trả về khi mật khẩu không khớp
        return res.status(400).send("Mật khẩu không khớp");
      }
    } else {
      return res.status(400).send("Tài khoản không tồn tại");
    }
  } catch (error) {
    console.error(error);
    return res.status(500).send("Lỗi hệ thống");
  }
});

//active account
router.get("/confirm/:userId", async (req, res) => {
  try {
    const user = await Account.findOne({
      id: req.params.userId,
    });

    if (!user) {
      return res.status(400).send("Invalid token");
    }

    await Account.updateOne({ _id: user._id }, { $set: { verified: true } });
    res.send("Email verified");
  } catch (error) {
    console.error("An error occurred:", error);
    res.status(500).send("Internal Server Error");
  }
});
//dang ky
router.post("/register", async (req, res) => {
  try {
    const data = req.body;
    let user = await Account.findOne({ email: data.email });
    if (user) {
      return res.status(400).send("Email đã tồn tại");
    }
    const hashPassword = await bcrypt.hash(data.password, 10);
    user = Account({
      email: data.email,
      username: data.username,
      password: hashPassword,
      name: data.name,
    });

    const result = await user.save();
    console.log(result);
    await verifiedEmail(user.email, url + user._id);
    console.log(user.email);

    res.status(200).send({
      message: "Register success",
      user: result,
    });
  } catch (error) {
    console.error("Registration failed:", error);
    res.status(500).send("Internal Server Error");
  }
});

//

module.exports = router;
