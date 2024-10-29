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
        post_type: req.body.post_type,
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
router.get('/detail/:id', async (req, res) => {
    try {
        const post = await Post.findById( req.params.id);
        if (!post) {
            return res.status(404).json({ message: 'Bài đăng không tìm thấy' });
        }
        res.status(200).json(post);
    } catch (error) {
        console.error(error);
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

        // Cập nhật các trường
        existingPost.user_id = req.body.user_id || existingPost.user_id;
        existingPost.title = req.body.title || existingPost.title;
        existingPost.content = req.body.content || existingPost.content;
        existingPost.status = req.body.status || existingPost.status;
  existingPost.post_type= req.body.post_type||existingPost.post_type;
       
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
router.get('/search', async (req, res) => {
    try {
        const { query } = req.query; // Lấy từ khóa tìm kiếm từ query string

        // Kiểm tra xem từ khóa tìm kiếm có được cung cấp không
        if (!query) {
            return res.status(400).json({ message: 'Từ khóa tìm kiếm không được cung cấp' });
        }

        // Tìm kiếm bài đăng dựa trên tiêu đề hoặc nội dung
        const posts = await Post.find({
            $or: [
                { title: { $regex: query, $options: 'i' } }, // Tìm kiếm không phân biệt chữ hoa chữ thường
                { content: { $regex: query, $options: 'i' } }
            ]
        });

        // Kiểm tra xem có bài đăng nào được tìm thấy không
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
