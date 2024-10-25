var express = require('express');
var router = express.Router();

const Post = require("../../models/Post");

const upload = require('../../config/common/upload');

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
//Add post
router.post('/post/add', upload.fields([{ name: 'video' }, { name: 'photo' }]), async (req, res) => {
    const post = new Post({
        user_id: req.body.user_id,
        title: req.body.title,
        content: req.body.content,
        status: req.body.status,
        video: req.files['video'] ? req.files['video'].map(file => file.path.replace('public/', '')) : [],
        photo: req.files['photo'] ? req.files['photo'].map(file => file.path.replace('public/', '')) : [],
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
router.put("/post/update/:id", async (req, res) => {
    try {

    } catch (error) {

    }
});

//delete post
router.delete("/post/delete/:id", async (req, res) => {
    try {
        const findID = req.params.id;
        const deleteID = await Post.findByIdAndDelete(findID);
        return res.status(200).json({
            status: 200,
            message: "Bạn đã xóa thành công.",
            deleteID
        })
    } catch (error) {
        console.error(error);
        res.status(500).send('Lỗi server');
    }
});

module.exports = router