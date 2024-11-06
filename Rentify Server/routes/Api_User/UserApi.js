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
        return res.status(400).send("Ta khoan chua duoc xac minh");
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
router.get("/confirm-email/:userId", async (req, res) => {
  try {
    console.log("Requesting userId:", req.params.userId); // Log ID
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
      username: data.username,
      password: hashPassword,
      name: data.name,
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

//danh sach phong tro
router.get("/list-post-rooms", verifyToken, async (req, res) => {
  try {
    const result = await Room.find();
    // const result = await Post.find();

    if (result) {
      res.json({
        status: 200,
        message: "success",
        data: result,
      });
    } else {
      res.json({
        status: 400,
        message: "fail",
        data: null,
      });
    }
  } catch (error) {
    console.log(error);
    res.status(500).json({ message: "Lỗi hệ thống" });
  }
});

//tim phong tro
router.get("/find-post-room", verifyToken, async (req, res) => {
  try {
    const { city, district } = req.query;
    const query = {};
    if (city) {
      query.city = city;
    }
    if (district) {
      query.district = district;
    }
    //tim dua tren cac dieu kien
    // const findPostRoom = await Post.find(query);
    const findPostRoom = await Room.find(query);

    //kiem tra phan hoi
    if (findPostRoom.length === 0) {
      return res.status(404).json({
        message: "Không có phòng trọ nào trong khu vực",
      });
    }
    res.status(200).json({
      findPostRoom,
    });
  } catch (error) {
    res
      .status(500)
      .json({ message: "Có lỗi xảy ra, vui lòng thử lại sau.", error });
  }
});

//them bai dang tim kiem phong tro
router.post("/add-post-rooms", verifyToken, async (req, res) => {
  try {
    const data = req.body;
    const newPostFindRoom = new Post({
      user_id: data.user_id,
      title: data.title,
      content: data.content,
      status: data.status,
      video: data.video,
      photo: data.photo,
      created_at: data.created_at,
      updated_at: data.updated_at,
    });
    const result = await newPostFindRoom.save();
    if (result) {
      res.json({
        status: 200,
        message: "success",
        data: result,
      });
    } else {
      res.json({
        status: 400,
        message: "fail",
        data: [],
      });
    }
  } catch (error) {
    console.log(error);
  }
});

//đây là phần của tìm bạn ở ghép

//

// đặt phòng
router.post("/new-booking", verifyToken, async (req, res) => {
  try {
    const { roomId, userId, bookingDate } = req.body;
    //kiem tra
    if (!roomId || !userId || !bookingDate) {
      return res
        .status(400)
        .json({ message: "Vui lòng cung cấp đầy đủ thông tin đặt trước." });
    }

    //kiem tra xem phong da duoc thue hay chua
    const existingBooking = await Booking.findOne({
      roomId,
    });
  } catch (error) { }
});
module.exports = router;
