const express = require('express');
const mongoose = require('mongoose');
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

// Lấy danh sách bài viết theo user_id
router.get("/list/:user_id", async (req, res) => {
    const { user_id } = req.params; // Lấy user_id từ URL
    try {
        const posts = await Post.find({ user_id }); // Lọc bài viết theo user_id
        if (posts.length === 0) {
            return res.status(404).json({ message: "No posts found for this user." });
        }
        res.json(posts);
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
});
router.post('/add', upload.fields([{ name: 'video' }, { name: 'photo' }]), async (req, res) => {
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

// Route để lấy chi tiết bài viết theo ID
router.get('/detail/:id', async (req, res) => {
    try {
        const postId = req.params.id;

        // Kiểm tra ID có hợp lệ hay không
        if (!mongoose.Types.ObjectId.isValid(postId)) {
            return res.status(400).json({ message: 'ID không hợp lệ' });
        }

        // Tìm bài đăng và populate thông tin người dùng
        const post = await Post.findById(postId).populate('user_id', 'name email');

        if (!post) {
            return res.status(404).json({ message: 'Bài đăng không tìm thấy' });
        }

        // Định dạng dữ liệu phản hồi
        res.status(200).json({
            id: post._id,
            title: post.title,
            content: post.content,
            price: post.price,
            address: post.address,
            phoneNumber: post.phoneNumber,
            room_type: post.room_type,
            amenities: post.amenities,
            services: post.services,
            post_type: post.post_type,
            status: post.status,
            photos: post.photo,
            videos: post.video,
            created_at: post.created_at,
            updated_at: post.updated_at,
            user: post.user_id ? {
                id: post.user_id._id,
                name: post.user_id.name,
                email: post.user_id.email
            } : null
        });
    } catch (error) {
        console.error('Lỗi khi lấy chi tiết bài đăng:', error);
        res.status(500).json({ message: 'Lỗi server', error: error.message });
    }
});
// Sửa bài viết theo ID
router.put("/update/:id", upload.fields([{ name: 'video' }, { name: 'photo' }]), async (req, res) => {
    try {
        const existingPost = await Post.findById(req.params.id);
        if (!existingPost) {
            return res.status(404).json({ message: "Post not found" });
        }

        // Cập nhật các trường thông tin bài đăng
        const userId = new mongoose.Types.ObjectId(req.body.user_id); // Đúng
        existingPost.title = req.body.title || existingPost.title;
        existingPost.content = req.body.content || existingPost.content;
        existingPost.status = req.body.status || existingPost.status;
        existingPost.post_type = req.body.post_type || existingPost.post_type;
        existingPost.price = req.body.price || existingPost.price;
        existingPost.address = req.body.address || existingPost.address;
        existingPost.phoneNumber = req.body.phoneNumber || existingPost.phoneNumber;
        existingPost.room_type = req.body.room_type || existingPost.room_type;
        existingPost.amenities = req.body.amenities ? req.body.amenities : existingPost.amenities;
        existingPost.services = req.body.services ? req.body.services : existingPost.services;

        // Cập nhật video và photo nếu có
        if (req.files['video']) {
            existingPost.video = req.files['video'].map(file => file.path.replace('public/', ''));
        }
        if (req.files['photo']) {
            existingPost.photo = req.files['photo'].map(file => file.path.replace('public/', ''));
        }

        existingPost.updated_at = new Date().toISOString();

        // Lưu bài viết đã cập nhật
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

// Tìm kiếm bài viết theo từ khóa
router.get('/search', async (req, res) => {
    try {
        const { query } = req.query; // Lấy từ khóa tìm kiếm từ query string

        if (!query) {
            return res.status(400).json({ message: 'Từ khóa tìm kiếm không được cung cấp' });
        }

        // Tìm kiếm bài đăng theo tiêu đề hoặc nội dung
        const posts = await Post.find({
            $or: [
                { title: { $regex: query, $options: 'i' } },
                { content: { $regex: query, $options: 'i' } }
            ]
        });

        if (posts.length === 0) {
            return res.status(404).json({ message: 'Không tìm thấy bài đăng nào' });
        }

        res.status(200).json(posts);
    } catch (error) {
        console.error(error);
        res.status(500).json({ message: 'Lỗi server', error: error.message });
    }
});

module.exports = router;
