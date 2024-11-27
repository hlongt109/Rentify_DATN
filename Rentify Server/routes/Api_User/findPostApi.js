var express = require("express");
var router = express.Router();

// Room và Post models
const Room = require("../../models/Room");
const Post = require("../../models/Post");
const upload = require('../../config/common/upload'); // Đường dẫn tới file upload.js
// Danh sách phòng cho thuê
router.get("/list-post-rooms", async (req, res) => {
  try {
    const result = await Post.find({ post_type: "rent" })
      .populate("user_id")  // Populating user data, nếu cần
      .populate("services")  // Populating các dịch vụ liên quan đến bài đăng (nếu có)
      .populate("amenities");  // Populating tiện nghi

    if (result.length > 0) {
      res.json({
        status: 200,
        message: "Success",
        data: result,
      });
    } else {
      res.json({
        status: 400,
        message: "Không có bài đăng nào",
        data: null,
      });
    }
  } catch (error) {
    console.log(error);
    res.status(500).json({ message: "Lỗi hệ thống" });
  }
});

// Tìm kiếm phòng cho thuê
router.get("/find-post-room", async (req, res) => {
  try {
    // Lấy từ khóa tìm kiếm từ query parameters
    const keyword = req.query.title || "";

    // Tìm các bài đăng có title hoặc content chứa từ khóa
    const result = await Post.find({
      post_type: "rent",
      $or: [
        { title: { $regex: keyword, $options: "i" } },
        { content: { $regex: keyword, $options: "i" } },
      ],
    }).populate("user_id")
      .populate("services")
      .populate("amenities");

    if (result.length > 0) {
      res.json({
        status: 200,
        message: "Success",
        data: result,
      });
    } else {
      res.json({
        status: 400,
        message: "Không tìm thấy bài đăng nào",
        data: null,
      });
    }
  } catch (error) {
    res.status(500).json({ message: "Có lỗi xảy ra, vui lòng thử lại sau.", error });
  }
});
router.post("add-post-rooms", upload.fields([{ name: 'video' }, { name: 'photo' }]), async (req, res) => {
  try {
      const post = new Post({
          user_id: new mongoose.Types.ObjectId(req.body.user_id), // Đảm bảo user_id là ObjectId hợp lệ
          title: req.body.title,
          content: req.body.content,
          status: req.body.status || 0,
          post_type: req.body.post_type,
          video: req.files['video'] ? req.files['video'].map(file => file.path.replace('public/', '')) : [],
          photo: req.files['photo'] ? req.files['photo'].map(file => file.path.replace('public/', '')) : [],
          price: req.body.price || 0,
          address: req.body.address || "",
          phoneNumber: req.body.phoneNumber || "",
          room_type: req.body.room_type || "",
          amenities: req.body.amenities || [],
          services: req.body.services || [],
          created_at: new Date().toISOString(),
          updated_at: new Date().toISOString()
      });

      const savedPost = await post.save();
      res.status(201).json(savedPost);
  } catch (error) {
      res.status(400).json({ message: error.message });
  }
});
// Thêm bài đăng tìm phòng
// router.post("/add-post-rooms", async (req, res) => {
//   try {
//     const data = req.body;
//     const newPostFindRoom = new Post({
//       user_id: data.user_id,
//       title: data.title,
//       content: data.content,
//       status: data.status,
//       video: data.video,
//       photo: data.photo,
//       price: data.price,  // Có thể cần thêm trường giá
//       address: data.address,  // Có thể cần thêm địa chỉ
//       room_type: data.room_type,  // Loại phòng
//       amenities: data.amenities,  // Tiện nghi
//       services: data.services,  // Dịch vụ
//       post_type: data.post_type,  // Loại bài đăng
//       created_at: data.created_at,
//       updated_at: data.updated_at,
//     });
    
//     const result = await newPostFindRoom.save();

//     if (result) {
//       res.json({
//         status: 200,
//         message: "Success",
//         data: result,
//       });
//     } else {
//       res.json({
//         status: 400,
//         message: "Thêm bài đăng thất bại",
//         data: [],
//       });
//     }
//   } catch (error) {
//     console.log(error);
//     res.status(500).json({ message: "Lỗi hệ thống", error });
//   }
// });

// Danh sách phòng ở ghép
router.get("/list-post-roommates", async (req, res) => {
  try {
    const result = await Post.find({ post_type: "roomate" }).populate("user_id")
      .populate("services")
      .populate("amenities");

    if (result.length > 0) {
      res.json({
        status: 200,
        message: "Success",
        data: result,
      });
    } else {
      res.json({
        status: 400,
        message: "Không có bài đăng nào",
        data: null,
      });
    }
  } catch (error) {
    console.log(error);
    res.status(500).json({ message: "Lỗi hệ thống" });
  }
});

// Tìm kiếm bài đăng tìm bạn ở ghép
router.get("/find-post-roommate", async (req, res) => {
  try {
    const keyword = req.query.title || "";

    const result = await Post.find({
      post_type: "roomate",
      $or: [
        { title: { $regex: keyword, $options: "i" } },
        { content: { $regex: keyword, $options: "i" } },
      ],
    }).populate("user_id")
      .populate("services")
      .populate("amenities");

    if (result.length > 0) {
      res.json({
        status: 200,
        message: "Success",
        data: result,
      });
    } else {
      res.json({
        status: 400,
        message: "Không tìm thấy bài đăng nào",
        data: null,
      });
    }
  } catch (error) {
    res.status(500).json({ message: "Có lỗi xảy ra, vui lòng thử lại sau.", error });
  }
});

module.exports = router;
