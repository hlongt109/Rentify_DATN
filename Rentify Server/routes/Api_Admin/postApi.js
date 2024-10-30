var express = require('express');
var router = express.Router();
const jwt = require("jsonwebtoken");

const Post = require("../../models/Post");
const User = require("../../models/User");

const upload = require('../../config/common/upload');
const { token } = require('morgan');

const cookieParser = require('cookie-parser');
const { assign } = require('nodemailer/lib/shared');

const verifyRole = (allowedRoles) => {
    return async (req, res, next) => {
        try {
            const authHeader = req.headers['authorization'];
            console.log("Authorization header:", authHeader); // Log header
            const token = authHeader?.split(' ')[1];
            if (!token) {
                return res.status(401).json({ message: "Bạn chưa đăng nhập" });
            }
            jwt.verify(token, 'hoan', (err, decoded) => {
                if (err) {
                    console.log("Token verification failed:", err);
                    return res.status(403).json({ message: "Invalid token" });
                }
                console.log("Token decoded payload:", decoded); // Kiểm tra payload
                req.user = decoded;
                next();
            });
        } catch (error) {
            return res.status(401).json({ message: "Lỗi xác thực", error: error.message });
        }
    }
};

//Lấy danh sách Post
router.get("/post/list", async (req, res) => {
    try {
        const showList = await Post.find();
        res.render('Posts/listPost', { list: showList });
        //return res.json({ message: "Danh sách bài đăng", showList });
    } catch (error) {
        console.error(error);
        res.status(500).send('Lỗi server');
    }
});
//seach
router.get('/api/search', (req, res) => {
    const query = req.query.query.toLowerCase();

    const postResults = posts.filter(post =>
        post.id.toString().includes(query) ||
        post.user_id.toLowerCase().includes(query) ||
        post.creatorId.toLowerCase().includes(query)
    );

    const userResults = users.filter(user =>
        user.id.toLowerCase().includes(query) ||
        user.name.toLowerCase().includes(query)
    );

    res.json({ posts: postResults, users: userResults }); // Trả về cả bài đăng và người dùng
});
//Add post
router.get('/post/add1', async (req, res) => {
    res.render('Posts/AddPost');
})
router.post('/post/add', verifyRole(['admin']), upload.fields([{ name: 'video' }, { name: 'photo' }]), async (req, res) => {
    let { title, content, status } = req.body;
    // let image = req.file ? req.file.filename : null;
    // let video = req.file ? req.file.filename : null;
    console.log(req.file);

    // if (!req.file) {
    //     return res.status(400).json({ message: 'Image hoac video không được trống' });
    // }
    const post = new Post({
        user_id: req.user._id,
        title,
        content,
        status,
        //image,
        //video,
        created_at: new Date().toISOString(),
        updated_at: new Date().toISOString()
    });
    try {
        const savedPost = await post.save();
        res.status(201).json(savedPost);
    } catch (error) {
        res.status(400).json({ message: error.message });
    }
})
//Update post
router.get("/post/update1/:id", async (req, res) => {
    try {
        const postId = req.params.id;
        const upDB = await Post.findById(postId); // Tìm bài viết theo ID

        if (!upDB) {
            return res.status(404).json({ message: 'Bài đăng không tồn tại' });
        }

        // Render trang updatePost.ejs và truyền biến upDB vào
        return res.render('Posts/updatePost', { upDB });
    } catch (error) {
        return res.status(500).json({ message: error.message });
    }
})
router.post("/post/update/:id", async (req, res) => {
    try {
        const findID = req.params.id;
        let { status } = req.body;
        // Convert status to a number and check if it is 0 or 1
        status = Number(status);
        if (status !== 0 && status !== 1) {
            return res.status(400).json({ message: 'Status chỉ có thể là số 0 hoặc là số 1' });
        }

        const upDB = await Post.findByIdAndUpdate(
            findID,
            {
                status,
                updated_at: new Date().toISOString()
            }
        )
        if (!upDB) {
            return res.status(404).json({ message: 'Không tìm thấy bài đăng' })
        }
        let msg = 'Sửa trạng thái thành công của ID: ' + findID;
        console.log(msg);
        return res.redirect('/api/post/list');

    } catch (error) {
        smg = "Loi " + error.message;
        return res.status(500).json({ message: smg });
    }
});

//delete post

router.delete("/post/delete/:id", async (req, res) => {
    try {
        const id = req.params.id;
        const deletedPost = await Post.findByIdAndDelete(id);
        if (!deletedPost) {
            return res.status(404).json({ message: 'Không tìm thấy bài đăng để xóa' });
        }
        return res.status(200).json({ message: 'Xóa bài đăng thành công' });
    } catch (error) {
        return res.status(500).json({ message: 'Lỗi: ' + error.message });
    }
});

module.exports = router