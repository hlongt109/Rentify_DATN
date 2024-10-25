var express = require('express');
var router = express.Router();

const Post = require("../../models/Post");

//Lấy danh sách Post
router.get("/post/list", async (req, res) => {
    try {
        const showList = await Post.find();
        return res.json({ message: "Danh sách bài đăng", showList });
    } catch (error) {
        console.error(error);
        res.status(500).send('Lỗi server');
    }
});

module.exports = router