const express = require('express');
const mongoose = require('mongoose');
const router = express.Router();
const Post = require("../../models/Post");
const upload = require('../../config/common/upload'); // Đường dẫn tới file upload.js
var building = require('../../models/Building')
const Room = require('../../models/Room')
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
    const { user_id } = req.params;

    // Kiểm tra user_id có hợp lệ hay không
    if (!mongoose.Types.ObjectId.isValid(user_id)) {
        return res.status(400).json({ message: "ID người dùng không hợp lệ" });
    }

    try {
        // Tìm bài viết liên kết với user_id, đồng thời populate thông tin từ các bảng khác
        const posts = await Post.find({ user_id })
            .populate("user_id", "name email") // Lấy thông tin người dùng
            .populate("building_id", "address") // Lấy địa chỉ tòa nhà
            .populate("room_id", "price") // Lấy giá phòng
            .sort({ created_at: -1 }); // Sắp xếp theo thời gian tạo mới nhất

        // Kiểm tra nếu không có bài viết nào
        if (!posts.length) {
            return res.status(404).json({ message: "Không có bài đăng nào của người dùng này." });
        }

        // Định dạng dữ liệu trả về
        const formattedPosts = posts.map(post => ({
            _id: post._id,
            title: post.title,
            content: post.content,
            status: post.status,
            video: post.video,
            photo: post.photo,
            post_type: post.post_type,
            created_at: post.created_at,
            updated_at: post.updated_at,
            price: post.room_id ? post.room_id.price : null, // Giá phòng từ Room
            address: post.building_id ? post.building_id.address : null, // Địa chỉ từ Building
            user: post.user_id ? {
                name: post.user_id.name,
                email: post.user_id.email
            } : null
        }));

        res.status(200).json({
            status: 200,
            message: "Danh sách bài viết được lấy thành công.",
            data: formattedPosts
        });
    } catch (error) {
        console.error("Lỗi server:", error.message);
        res.status(500).json({ message: "Đã xảy ra lỗi khi lấy danh sách bài viết.", error: error.message });
    }
});
// API lấy danh sách các tòa nhà theo user_id
router.get('/buildings', async (req, res) => {
    try {
        const { user_id } = req.query; // Lấy user_id từ query params

        if (!user_id) {
            return res.status(400).json({ message: "user_id không hợp lệ" });
        }

        // Tìm tất cả các tòa nhà mà người dùng quản lý (giả sử có quan hệ giữa User và Building)
        const buildings = await building.find({ user_id: user_id });

        if (!buildings.length) {
            return res.status(404).json({ message: "Không tìm thấy tòa nhà" });
        }

        res.status(200).json({ status: 200, data: buildings });
    } catch (error) {
        res.status(500).json({ message: "Lỗi khi lấy tòa nhà", error: error.message });
    }
});
// API lấy danh sách phòng trong một tòa nhà theo building_id
router.get('/rooms', async (req, res) => {
    try {
        const { building_id } = req.query; // Lấy building_id từ query params

        if (!building_id) {
            return res.status(400).json({ message: "building_id không hợp lệ" });
        }

        // Tìm tất cả các phòng trong tòa nhà theo building_id
        const rooms = await Room.find({ building_id: building_id });

        if (!rooms.length) {
            return res.status(404).json({ message: "Không tìm thấy phòng trong tòa nhà" });
        }

        res.status(200).json({ status: 200, data: rooms });
    } catch (error) {
        res.status(500).json({ message: "Lỗi khi lấy phòng", error: error.message });
    }
});
// API đăng bài với video, ảnh

router.post('/add',  upload.fields([{ name: 'video' }, { name: 'photo' }]), async (req, res) => {
    try {
        const { user_id, building_id, room_id, title, content, post_type, status } = req.body;

        // Kiểm tra giá trị `post_type`
        if (!["roomate", "rent", "seek"].includes(post_type)) {
            return res.status(400).json({ message: "Loại bài đăng không hợp lệ" });
        }

        // Tạo bài đăng mới
        const newPost = new Post({
            user_id: new mongoose.Types.ObjectId(req.body.user_id),
            building_id: building_id ?new mongoose.Types.ObjectId(building_id) : null, // Kiểm tra nếu có building_id
            room_id: room_id ?new mongoose.Types.ObjectId(room_id) : null, // Kiểm tra nếu có room_id
            title,
            content,
            post_type,
            status: status || 0, // Mặc định là `0`
           video: req.files['video'] ? req.files['video'].map(file => file.path.replace('public/', '')) : [],
            photo: req.files['photo'] ? req.files['photo'].map(file => file.path.replace('public/', '')) : [],
            created_at: new Date().toISOString(),
            updated_at: new Date().toISOString(),
        });

        // Lưu bài đăng vào database
        const savedPost = await newPost.save();
        res.status(201).json({ status: 201, data: savedPost });
    } catch (error) {
        res.status(400).json({ message: "Lỗi thêm bài đăng", error: error.message });
    }
});


router.get('/detail/:id', async (req, res) => {
    try {
        const postId = req.params.id;

        // Kiểm tra ID có hợp lệ hay không
        if (!mongoose.Types.ObjectId.isValid(postId)) {
            return res.status(400).json({ message: 'ID không hợp lệ' });
        }

        // Tìm bài đăng và populate thông tin người dùng, building và room
        const post = await Post.findById(postId)
            .populate('user_id', 'name email')
            .populate('building_id', 'nameBuilding address')  // Populate thông tin từ bảng Building
            .populate('room_id', 'room_type room_name price') // Populate thông tin từ bảng Room

        if (!post) {
            return res.status(404).json({ message: 'Bài đăng không tìm thấy' });
        }

        // Tính số bài đăng trong tòa nhà có building_id giống
        const buildingPostCount = await Post.countDocuments({ building_id: post.building_id._id });

        // Định dạng dữ liệu phản hồi
        res.status(200).json({
            id: post._id,
            title: post.title,
            content: post.content,
            post_type: post.post_type,
            status: post.status,
            photos: post.photo,
            videos: post.video,
            created_at: post.created_at,
            updated_at: post.updated_at,
            user: post.user_id ? {
                _id: post.user_id._id,
                name: post.user_id.name,
                email: post.user_id.email
            } : null,
            building: post.building_id ? {
                _id: post.building_id._id,
                nameBuilding: post.building_id.nameBuilding,
                address: post.building_id.address,
                post_count: buildingPostCount // Trả về số bài đăng trong tòa nhà
            } : null,
            room: post.room_id ? {
                _id: post.room_id._id,
                room_type: post.room_id.room_type,
                room_name: post.room_id.room_name,
                price: post.room_id.price
            } : null
        });
    } catch (error) {
        console.error('Lỗi khi lấy chi tiết bài đăng:', error);
        res.status(500).json({ message: 'Lỗi server', error: error.message });
    }
});

// Cập nhật bài viết
// router.put('/update/:id', upload.fields([{ name: 'video' }, { name: 'photo' }]), async (req, res) => {
//     try {
//         const { id } = req.params;
//         const { title, content, post_type, status, building_id, room_id } = req.body;

//         // Chuẩn bị dữ liệu cập nhật
//         const updateData = {
//             title,
//             content,
//             post_type,
//             status,
//             updated_at: new Date().toISOString(),
//         };
//         if (building_id) {
//             if (mongoose.Types.ObjectId.isValid(building_id)) {
//                 updateData.building_id = new mongoose.Types.ObjectId(building_id);
//             } else {
//                 return res.status(400).json({ message: "building_id không hợp lệ" });
//             }
//         }
        
//         if (room_id) {
//             if (mongoose.Types.ObjectId.isValid(room_id)) {
//                 updateData.room_id = new mongoose.Types.ObjectId(room_id);
//             } else {
//                 return res.status(400).json({ message: "room_id không hợp lệ" });
//             }
//         }
        

//         // Xử lý cập nhật video và ảnh nếu có
//         if (req.files['video']) {
//             updateData.video = req.files['video'].map(file => file.path.replace('public/', ''));
//         }
//         if (req.files['photo']) {
//             updateData.photo = req.files['photo'].map(file => file.path.replace('public/', ''));
//         }

//         // Tìm và cập nhật bài viết
//         const updatedPost = await Post.findByIdAndUpdate(id, updateData, { new: true });

//         // Kiểm tra nếu không tìm thấy bài viết
//         if (!updatedPost) {
//             return res.status(404).json({ message: "Bài viết không tồn tại" });
//         }

//         res.status(200).json({
//             status: 200,
//             message: "Cập nhật bài viết thành công",
//             data: updatedPost
//         });
//     } catch (error) {
//         console.error("Lỗi khi cập nhật bài viết:", error.message);
//         res.status(500).json({ message: "Lỗi server", error: error.message });
//     }
// });

router.put("/update/:id", upload.fields([{ name: 'video' }, { name: 'photo' }]), async (req, res) => {
    try {
        const { id } = req.params;
        const { user_id, building_id, room_id, title, content, status, post_type, room_type } = req.body;

         // Kiểm tra ID có hợp lệ hay không
         if (!mongoose.Types.ObjectId.isValid(id)) {
            return res.status(400).json({ message: 'ID không hợp lệ' });
        }

        // Tìm bài đăng đã tồn tại
        const existingPost = await Post.findById(id);
        if (!existingPost) {
            return res.status(404).json({ message: "Bài đăng không tồn tại" });
        }

        // Chuyển đổi các ID vào đúng dạng ObjectId nếu có
        existingPost.user_id = user_id ? new mongoose.Types.ObjectId(user_id) : existingPost.user_id;
        existingPost.building_id = building_id ? new mongoose.Types.ObjectId(building_id) : existingPost.building_id;
        existingPost.room_id = room_id ? new mongoose.Types.ObjectId(room_id) : existingPost.room_id;

        // Cập nhật các trường thông tin bài đăng
        existingPost.title = title || existingPost.title;
        existingPost.content = content || existingPost.content;
        existingPost.status = status || existingPost.status;
        existingPost.post_type = post_type || existingPost.post_type;
        existingPost.room_type = room_type || existingPost.room_type;

        // Cập nhật video và ảnh nếu có
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
