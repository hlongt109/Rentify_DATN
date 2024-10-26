const express = require('express');
const router = express.Router();
const Post = require("../../models/Post");
const upload = require('../../config/common/upload'); // Đường dẫn tới file upload.js

// Lấy danh sách bài viết
router.get("/list", async (req, res) => {
    try {
        const posts = await Post.find(); // Lấy tất cả bài viết từ DB
        res.json(posts);
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
});

// Route để thêm bài viết
router.post('/add', upload.fields([{ name: 'video' }, { name: 'photo' }]), async (req, res) => {
    const post = new Post({
        user_id: req.body.user_id,
        title: req.body.title,
        content: req.body.content,
        status: req.body.status,
        video: req.files['video'] ? req.files['video'].map(file => file.path.replace('public/', '')) : [], // Chỉ lưu lại đường dẫn tương đối
        photo: req.files['photo'] ? req.files['photo'].map(file => file.path.replace('public/', '')) : [], // Chỉ lưu lại đường dẫn tương đối
        created_at: new Date().toISOString(),
        updated_at: new Date().toISOString()
    });

    try {
        const savedPost = await post.save();
        res.status(201).json(savedPost);
    } catch (error) {
        res.status(400).json({ message: error.message });
    }
});

// Sửa bài viết theo ID
router.put("/update/:id", upload.fields([{ name: 'video' }, { name: 'photo' }]), async (req, res) => {
    try {
        const existingPost = await Post.findById(req.params.id);
        if (!existingPost) {
            return res.status(404).json({ message: "Post not found" });
        }

        // Cập nhật các trường
        existingPost.user_id = req.body.user_id || existingPost.user_id;
        existingPost.title = req.body.title || existingPost.title;
        existingPost.content = req.body.content || existingPost.content;
        existingPost.status = req.body.status || existingPost.status;

        // Cập nhật video và photo
        if (req.files['video']) {
            existingPost.video = req.files['video'].map(file => file.path);
        }
        if (req.files['photo']) {
            existingPost.photo = req.files['photo'].map(file => file.path);
        }

        existingPost.updated_at = new Date().toISOString();

        const updatedPost = await existingPost.save();
        res.json(updatedPost); // Trả về bài viết đã được cập nhật
    } catch (error) {
        res.status(400).json({ message: error.message });
    }
});


// Xóa bài viết theo ID
router.delete("/delete/:id", async (req, res) => {
    try {
        const deletedPost = await Post.findByIdAndDelete(req.params.id);

        if (!deletedPost) {
            return res.status(404).json({ message: "Post not found" });
        }

        res.status(204).send(); // Trả về status 204 No Content khi xóa thành công
    } catch (error) {
        res.status(500).json({ message: error.message }); // Xử lý lỗi
    }
});

module.exports = router;
