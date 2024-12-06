// quản lí bài đăng (chủ tòa)
var express = require('express');
var router = express.Router();
const Post = require('../../models/Post');
const uploadFile = require("../../config/common/upload");
const handleServerError = require("../../utils/errorHandle");
const mongoose = require('mongoose');


//
router.post('/posts_mgr', uploadFile.fields([
    { name: 'video', maxCount: 1 },
    { name: 'images', maxCount: 5 }
]), async (req, res) => {
    try {
        const data = req.body; 
        const files = req.files; 

        const video = files.video ? `${req.protocol}://${req.get("host")}/public/uploads/${files.video[0].filename}` : null;

        const images = files.images ? files.images.map(file => `${req.protocol}://${req.get("host")}/public/uploads/${file.filename}`) : [];

        const userObjectId = new mongoose.Types.ObjectId(data.user_id);
        const buildingObjectId = new mongoose.Types.ObjectId(data.building_id);
        const roomObjectId = new mongoose.Types.ObjectId(data.room_id);

        const newPost = new Post({
            user_id: userObjectId,
            building_id: buildingObjectId,
            room_id: roomObjectId,
            title: data.title,
            content: data.content,
            status: data.status || 0, // Nếu không có status thì mặc định là 0
            video: video,  // Video nếu có
            photo: images, // Danh sách ảnh
            post_type: data.post_type,
            created_at: data.created_at || new Date().toISOString()  // Nếu không có created_at thì dùng thời gian hiện tại
        });

        // Lưu bài viết vào cơ sở dữ liệu
        const result = await newPost.save();

        if (result) {
            res.status(200).json({ message: "Add post success", data: result });
        } else {
            res.status(401).json({ message: "Add post failed" });
        }
    } catch (error) {
        console.error(error);
        handleServerError(res, error);  // Hàm này sẽ xử lý lỗi server
    }
});

router.put("/posts_mgr/:id", uploadFile.fields([
    { name: 'video', maxCount: 1 },
    { name: 'images', maxCount: 5 }
]), async (req, res) => {
    try {
        const { id } = req.params;
        const data = req.body;
        const files = req.files;

        const post = await Post.findById(id);
        if (!post) {
            return res.status(404).json({ messenger: 'No post found to update' });
        }

        let images
        let video
        if (files && files.images) {
            images = files.images ? files.images.map(file => `${req.protocol}://${req.get("host")}/public/uploads/${file.filename}`) : [];
        }else{
            images = post.photo
        }

        if(files && files.video){
            video = files.video ? `${req.protocol}://${req.get("host")}/public/uploads/${files.video[0].filename}` : null;
        }else{
            video = post.video;
        }

        post.user_id = data.user_id ?? post.user_id;
        post.building_id = data.building_id ?? post.building_id;
        post.room_id = data.room_id ?? post.room_id;
        post.title = data.title ?? post.title;
        post.content = data.content ?? post.content;
        post.status = data.status ?? post.status;
        post.post_type = data.post_type ?? post.post_type;
        post.photo = images;
        post.video = video;
        post.created_at = post.created_at;
        post.updated_at = new Date().toISOString();
        

        const result = await post.save();
        if (result) {
            res.status(200).json({ message: "Update post success", data: result })
        } else {
            res.status(401).json({ message: "Update post failed" })
        }

    } catch (error) {
        console.error(error);
        handleServerError(res, error);
    }
})

router.put("/posts_mgr_status/:id", async(req, res) => {
    try {
        const { id } = req.params;
        const data = req.body;

        const post = await Post.findById(id);
        if (!post) {
            return res.status(404).json({ messenger: 'No post found to update' });
        }

        post.user_id = post.user_id;
        post.building_id = post.building_id;
        post.room_id = post.room_id;
        post.title = post.title;
        post.content = post.content;
        post.status = data.status ?? post.status;
        post.post_type = post.post_type;
        post.photo = post.photo;
        post.video = post.video;
        post.created_at = post.created_at;
        post.updated_at = new Date().toISOString();

        const result = await post.save();
        if (result) {
            res.status(200).json({ message: "Update post success", data: result })
        } else {
            res.status(401).json({ message: "Update post failed" })
        }
    } catch (error) {
        console.log("Error: ", error);
        handleServerError(res, error);
    }
})

//
router.get('/posts_mgr/:buildingId/posts/day', async (req, res) => {
    try {
        const { buildingId } = req.params;
        const { status, selectTime } = req.query;

        if (!buildingId) {
            return res.status(400).json({ message: 'Building ID trống' });
        }

        const buildingObjectId = new mongoose.Types.ObjectId(buildingId);

        const posts = await Post.find({
            building_id: buildingObjectId,
            status: status,
            post_type: 'rent',
            created_at: {
                $gte: `${selectTime}T00:00:00`,
                $lt: `${selectTime}T23:59:00`
            }
        }).populate("room_id")
            .populate({
                path: "building_id",
                select: "address" // Chỉ lấy trường 'address'
            })
            .exec();

        const isPostActive = posts.filter(post => post.status === 0)
        const isPostBan = posts.filter(post => post.status === 2)

        return res.status(200).json({
            isPostActive,
            isPostBan
        });

    } catch (error) {
        console.error(error);
        handleServerError(res, error);
    }
});

router.get('/posts_mgr/:buildingId/posts', async (req, res) => {
    try {
        const { buildingId } = req.params;
        const { month, year, status } = req.query;

        const currentDate = new Date();
        const selectedMonth = month
            ? String(month).padStart(2, '0')
            : String(currentDate.getMonth() + 1).padStart(2, '0');
        const selectedYear = year || currentDate.getFullYear();

        if (!buildingId) {
            return res.status(400).json({ message: 'Building ID trống' });
        }

        const buildingObjectId = new mongoose.Types.ObjectId(buildingId);

        const posts = await Post.find({
            building_id: buildingObjectId,
            status: status,
            post_type: 'rent',
            created_at: {
                $gte: `${selectedYear}-${selectedMonth}-01T00:00:00`,
                $lt: `${selectedYear}-${selectedMonth + 1}-01T00:00:00`
            }
        }).populate("room_id")
            .populate({
                path: "building_id",
                select: "address" // Chỉ lấy trường 'address'
            })
            .exec();

        const isPostActive = posts.filter(post => post.status === 0)
        const isPostBan = posts.filter(post => post.status === 2)

        return res.status(200).json({
            isPostActive,
            isPostBan
        });

    } catch (error) {
        console.error(error);
        handleServerError(res, error);
    }
});

router.get('/posts_mgr/:buildingId/posts/year', async (req, res) => {
    try {
        const { buildingId } = req.params;
        const { year, status } = req.query;

        const currentDate = new Date();

        const selectedYear = year || currentDate.getFullYear();

        if (!buildingId) {
            return res.status(400).json({ message: 'Building ID trống' });
        }
        const buildingObjectId = new mongoose.Types.ObjectId(buildingId);

        const posts = await Post.find({
            building_id: buildingObjectId,
            status: status,
            created_at: {
                $gte: `${selectedYear}-01-01T00:00:00`,
                $lt: `${selectedYear + 1}-01-01T00:00:00`
            }
        }).populate("room_id")
            .populate({
                path: "building_id",
                select: "address"
            })
            .exec();

        const isPostActive = posts.filter(post => post.status === 0)
        const isPostBan = posts.filter(post => post.status === 2)

        return res.status(200).json({
            isPostActive,
            isPostBan
        });

    } catch (error) {
        console.error(error);
        handleServerError(res, error);
    }
});

router.delete("/posts_mgr/:id", async (req, res) => {
    try {
        const { id } = req.params
        const result = await Post.findByIdAndDelete(id);
        if (result) {
            return res.status(200).json({ message: "Delete post success" })
        } else {
            return res.status(404).json({ message: "Invoice not found" })
        }
    } catch (error) {
        console.error(error);
        handleServerError(res, error);
    }
})


router.get("/posts_mgr/:id", async (req, res) => {
    try {
        const { id } = req.params
        const result = await Post.findById(id);
        if (!result) {
            return res.status(404).json({ message: "Post not found" })
        }
        res.status(200).json({ data: result })
    } catch (error) {
        console.error(error);
        handleServerError(res, error);
    }
})

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