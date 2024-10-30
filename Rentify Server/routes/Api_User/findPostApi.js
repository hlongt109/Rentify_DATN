var express = require("express");
var router = express.Router();

//room
const Room = require("../../models/Room");
//post
const Post = require("../../models/Post");

//danh sach phong tro
router.get("/list-post-rooms", async (req, res) => {
  try {
    const result = await Post.find({ post_type: "rent" });
    // const result = await Post.find();

    if (result.length > 0) {
      res.json({
        status: 200,
        message: "success",
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
//tim phong tro
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
    });

    //kiem tra phan hoi
    if (result.length > 0) {
      res.json({
        status: 200,
        message: "success",
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
    res
      .status(500)
      .json({ message: "Có lỗi xảy ra, vui lòng thử lại sau.", error });
  }
});

//them bai dang tim kiem phong tro
router.post("/add-post-rooms", async (req, res) => {
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
//danh sach phong tro
router.get("/list-post-roommates", async (req, res) => {
  try {
    const result = await Post.find({ post_type: "roomate" });
    // const result = await Post.find();

    if (result.length > 0) {
      res.json({
        status: 200,
        message: "success",
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
//tim o ghep
router.get("/find-post-roommate", async (req, res) => {
  try {
    // Lấy từ khóa tìm kiếm từ query parameters
    const keyword = req.query.title || "";

    // Tìm các bài đăng có title hoặc content chứa từ khóa
    const result = await Post.find({
      post_type: "roomate",
      $or: [
        { title: { $regex: keyword, $options: "i" } },
        { content: { $regex: keyword, $options: "i" } },
      ],
    });

    //kiem tra phan hoi
    if (result.length > 0) {
      res.json({
        status: 200,
        message: "success",
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
    res
      .status(500)
      .json({ message: "Có lỗi xảy ra, vui lòng thử lại sau.", error });
  }
});

module.exports = router;
