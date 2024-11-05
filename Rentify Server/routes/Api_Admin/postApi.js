var express = require('express');
var router = express.Router();
const jwt = require("jsonwebtoken");

const Post = require("../../models/Post");
const User = require("../../models/User");

const upload = require('../../config/common/upload');

const verifyToken = (req, res, next) => {
    const token = req.headers['authorization']?.split(' ')[1];
    if (!token) {
        return res.status(403).send("Không có token, quyền truy cập bị từ chối!");
    }
    jwt.verify(token, 'hoan', (err, user) => {
        if (err) {
            return res.status(403).send("Token không hợp lệ!");
        }
        req.user = user; // Gán thông tin người dùng vào req.user
        next();
    });
};

//Lấy danh sách Post
router.get("/post/list", async (req, res) => {
    try {
        const showList = await Post.find();
        return res.status(200).json({
            status: 200,
            message: "Lấy dữ liệu thành công",
            showList
        });
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
router.post('/post/add', verifyToken, upload.fields([{ name: 'video' }, { name: 'photo' }]), async (req, res) => {
    let { title, content, status, post_type, } = req.body;
    // let photo = req.files['photo'] ? req.files['photo'].map(file => file.filename) : [];
    // let video = req.files['video'] ? req.files['video'].map(file => file.filename) : [];

    // if (!req.files['photo'] && !req.files['video']) {
    //     return res.status(400).json({ message: 'Ít nhất một ảnh hoặc video không được trống' });
    // }

    // Lấy ID người dùng từ token

    const userId = data.user._id; // Giả sử bạn đã lưu ID người dùng trong req.user khi xác thực
    console.log(data);
    const post = new Post({
        user_id: userId,
        title,
        content,
        status,
        post_type,
        //photo,
        // video,
        created_at: new Date().toISOString(),
        updated_at: new Date().toISOString()
    });
    try {
        const savedPost = await post.save();
        res.redirect("/api/post/list");
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
        return res.render('Posts/updatePost', { upDB });
    } catch (error) {
        return res.status(500).json({ message: error.message });
    }
})
router.post("/post/update/:id", async (req, res) => {
    try {
        const findID = req.params.id;
        let { status } = req.body;
        status = Number(status);

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