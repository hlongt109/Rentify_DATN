const express = require('express');
const router = express.Router();
const mongoose = require('mongoose');
const Building = require('../../models/Building');
const Room = require('../../models/Room');
const upload = require('../../config/common/uploadImageRoom')

// API lấy danh sách tòa nhà theo manager_id
router.get('/buildings-by-manager/:manager_id', async (req, res) => {
    const { manager_id } = req.params;

    // Validate ID là ObjectId hợp lệ
    if (!mongoose.Types.ObjectId.isValid(manager_id)) {
        return res.status(400).json({ error: 'Invalid manager ID.' });
    }

    try {
        const buildings = await Building.find({ manager_id })
            .populate('landlord_id', 'username phoneNumber')
            .populate('manager_id', 'username phoneNumber')
            .populate('service', 'name');

        res.status(200).json(buildings);
    } catch (error) {
        console.error('Error fetching buildings by manager:', error.message);
        res.status(500).json({ error: 'Failed to fetch buildings. Please try again later.' });
    }
});
// _vanphuc :thêm phòng 
router.post('/addRoom', upload.fields([
    { name: 'photos_room', maxCount: 10 }, // Tối đa 10 ảnh
    { name: 'video_room', maxCount: 2 }   // Tối đa 2 video
]), async (req, res) => {
    try {
        const {building_id,room_name,room_type,description,price,size,service,amenities,limit_person,status,} = req.body;

        // Kiểm tra dữ liệu bắt buộc
        if (!building_id || !room_name || !room_type || !description || !price || !size || !limit_person || status === undefined) {
            return res.status(400).json({ message: "Thiếu thông tin bắt buộc" });
        }
        
        // Lưu đường dẫn ảnh và video
        const photos_room = req.files.photos_room ? req.files.photos_room.map(file => file.path) : [];
        const video_room = req.files.video_room ? req.files.video_room.map(file => file.path) : [];

        // Tạo mới một phòng
        const newRoom = new Room({
            building_id,
            room_name,
            room_type,
            description,
            price,
            size,
            video_room,
            photos_room,
            service, 
            amenities, 
            limit_person,
            status,
            created_at: new Date().toISOString(),
            updated_at: new Date().toISOString(),
        });

        // Lưu phòng vào cơ sở dữ liệu
        const savedRoom = await newRoom.save();
        res.status(201).json({ message: "Thêm phòng thành công", room: savedRoom });
    } catch (error) {
        console.error(error);
        res.status(500).json({ message: "Lỗi khi thêm phòng", error: error.message });
    }
});

// API lấy chi tiết phòng theo ID
const normalizePaths = (room) => {
    // Loại bỏ '/landlord/' nếu có trong đường dẫn
    const removeUnnecessaryPath = (path) => path.replace(/^\/landlord\//, '');

    room.photos_room = room.photos_room.map(photo => removeUnnecessaryPath(photo.replace(/\\/g, '/')));
    room.video_room = room.video_room.map(video => removeUnnecessaryPath(video.replace(/\\/g, '/')));
    return room;
};

router.get('/room/:id', async (req, res) => {
    const { id } = req.params;

    // Kiểm tra ID là ObjectId hợp lệ
    if (!mongoose.Types.ObjectId.isValid(id)) {
        return res.status(400).json({ error: 'Invalid room ID.' });
    }

    try {
        // Tìm phòng theo ID
        const room = await Room.findById(id).lean(); // Dùng `lean()` để lấy dữ liệu dạng JSON

        // Nếu không tìm thấy phòng
        if (!room) {
            return res.status(404).json({ error: 'Room not found.' });
        }

        // Chuẩn hóa dữ liệu trước khi trả về
        const normalizedRoom = normalizePaths(room);

        // Trả về chi tiết phòng
        res.status(200).json(normalizedRoom);
    } catch (error) {
        console.error('Error fetching room details:', error.message);
        res.status(500).json({ error: 'Failed to fetch room details. Please try again later.' });
    }
});

module.exports = router;
