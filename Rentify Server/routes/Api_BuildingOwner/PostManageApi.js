// quản lí bài đăng (chủ tòa)
var express = require('express');
var router = express.Router();
const Post = require('../../models/Post');

// #1. Tạo bài đăng POST: http://localhost:3000/api/posts
router.post('/posts', async (req, res) => {
    const { user_id, title, content, status, video, photo } = req.body;

    try {
        const newPost = new Post({
            user_id,
            title,
            content,
            status,
            video,
            photo,
            created_at: new Date().toISOString(),
            updated_at: new Date().toISOString()
        });

        await newPost.save();
        res.status(201).json({ message: 'Post created successfully!', post: newPost });
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'Failed to create post.' });
    }
});

// #2. Lấy danh sách tất cả bài đăng GET: http://localhost:3000/api/posts
router.get('/posts', async (req, res) => {
    try {
        const posts = await Post.find({});
        res.status(200).json(posts);
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'Failed to retrieve posts.' });
    }
});

// #3. Cập nhật bài đăng PUT : http://localhost:3000/api/posts/{id}
router.put('/posts/:id', async (req, res) => {
    const { id } = req.params;
    const { title, content, status, video, photo } = req.body;

    try {
        const updatedPost = await Post.findByIdAndUpdate(
            id,
            { title, content, status, video, photo, updated_at: new Date().toISOString() },
            { new: true } // trả về bài đăng đã được cập nhật
        );

        if (!updatedPost) {
            return res.status(404).json({ error: 'Post not found.' });
        }

        res.status(200).json({ message: 'Post updated successfully!', post: updatedPost });
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'Failed to update post.' });
    }
});

// #4. Xóa bài đăng DELETE : http://localhost:3000/api/posts/{id}
router.delete('/posts/:id', async (req, res) => {
    const { id } = req.params;

    try {
        const deletedPost = await Post.findByIdAndDelete(id);
        
        if (!deletedPost) {
            return res.status(404).json({ error: 'Post not found.' });
        }

        res.status(200).json({ message: 'Post deleted successfully!' });
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'Failed to delete post.' });
    }
});
// #5. Tìm kiếm bài đăng GET: http://localhost:3000/api/posts/search?query={keyword}
router.get('/posts/search', async (req, res) => {
    const { query } = req.query; // Lấy từ query string

    try {
        const posts = await Post.find({
            $or: [
                { title: { $regex: query, $options: 'i' } }, // Tìm kiếm theo tiêu đề
                { content: { $regex: query, $options: 'i' } } // Tìm kiếm theo nội dung
            ]
        });
        res.status(200).json(posts);
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'Failed to search posts.' });
    }
});

// #6. Xem chi tiết bài đăng GET: http://localhost:3000/api/posts/{id}
router.get('/posts/:id', async (req, res) => {
    const { id } = req.params;

    try {
        const post = await Post.findById(id);
        
        if (!post) {
            return res.status(404).json({ error: 'Post not found.' });
        }

        res.status(200).json(post);
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'Failed to retrieve post.' });
    }
});


module.exports = router;


// kiểm thử postman 

// {
//     "user_id": "671a29b84e350b2df4aee4ed",
//     "title": "Bài đăng mới của chủ toa phúc ",
//     "content": "Nội dung bài đăng. phúc đẹp trai bình thường ",
//     "status": "active",
//     "video": [],
//     "photo": []
// }
