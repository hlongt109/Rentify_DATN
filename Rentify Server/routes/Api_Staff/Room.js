const express = require('express');
const router = express.Router();
const mongoose = require('mongoose');
const Building = require('../../models/Building');
const Room = require('../../models/Room');
const upload = require('../../config/common/uploadImageRoom')
// api lấy danh sách tòa 😊
router.get('/buildings-by-manager/:manager_id', async (req, res) => {
    const { manager_id } = req.params;
    if (!mongoose.Types.ObjectId.isValid(manager_id)) {
        return res.status(400).json({ error: 'Invalid manager ID.' });
    }
    try {
        const buildings = await Building.find({ manager_id })
            .populate('landlord_id', 'username phoneNumber')
            .populate('manager_id', 'username phoneNumber')
            .populate('service', 'name');
        if (!buildings || buildings.length === 0) {
            return res.status(404).json({ message: 'No buildings found for this manager.' });
        }
        const buildingsWithRooms = [];
        for (let building of buildings) {
            const rooms = await Room.find({ building_id: building._id }).select('room_name room_type price status');
            building = building.toObject(); 
            building.rooms = rooms; 
            buildingsWithRooms.push(building);
        }
        res.status(200).json(buildingsWithRooms);
    } catch (error) {
        console.error('Error fetching buildings and rooms:', error.message);
        res.status(500).json({ error: 'Failed to fetch buildings and rooms. Please try again later.' });
    }
});
// api lấy danh sách phòng theo tòa 🤦‍♂️
router.get('/RoomsForBuilding/:building_id', async (req, res) => {
    try {
        // Lấy building_id từ tham số URL
        const { building_id } = req.params;

        // Tìm tất cả phòng trong tòa nhà đó
        const rooms = await Room.find({ building_id }).select('room_name room_type price status');

        // Kiểm tra nếu không có phòng nào
        if (!rooms || rooms.length === 0) {
            // Nếu không có phòng, trả về danh sách rỗng
            return res.json([]);
        }

        // Trả về kết quả là danh sách các phòng với các trường được chọn
        return res.status(200).json(rooms);
    } catch (error) {
        // Nếu có lỗi xảy ra
        return res.status(500).json({ message: 'Server error', error: error.message });
    }
});
// Hiển thị chi tiết phòng 🤷‍♂️
const normalizePaths = (room) => {
    const removeUnnecessaryPath = (path) => path.replace(/^\/landlord\//, '');

    room.photos_room = room.photos_room.map(photo => removeUnnecessaryPath(photo.replace(/\\/g, '/')));
    room.video_room = room.video_room.map(video => removeUnnecessaryPath(video.replace(/\\/g, '/')));
    return room;
};
router.get('/RoomDetail/:id', async (req, res) => {
    const { id } = req.params;
    if (!mongoose.Types.ObjectId.isValid(id)) {
        return res.status(400).json({ error: 'Invalid room ID.' });
    }
    try {
        const room = await Room.findById(id).lean(); 
        if (!room) {
            return res.status(404).json({ error: 'Room not found.' });
        }
        const normalizedRoom = normalizePaths(room);
        res.status(200).json(normalizedRoom);
    } catch (error) {
        console.error('Error fetching room details:', error.message);
        res.status(500).json({ error: 'Failed to fetch room details. Please try again later.' });
    }
});
// _vanphuc :thêm phòng  theo tòa nhà  😶‍🌫️
router.post('/addRoom', upload.fields([
    { name: 'photos_room', maxCount: 10 }, // Tối đa 10 ảnh
    { name: 'video_room', maxCount: 2 }   // Tối đa 2 video
]), async (req, res) => {
    try {
        const { building_id, room_name, room_type, description, price, size, service, amenities, limit_person, status, } = req.body;

        // Kiểm tra dữ liệu bắt buộc
        if (!building_id || !room_name || !room_type || !description || !price || !size || status === undefined) {
            return res.status(400).json({ message: "Thiếu thông tin bắt buộc" });
        }

        // Lưu đường dẫn ảnh và video
        const photos_room = req.files.photos_room ? req.files.photos_room.map(file => file.path) : [];
        const video_room = req.files.video_room ? req.files.video_room.map(file => file.path) : [];
        console.log(photos_room);
        
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
        console.log(newRoom);
        // Lưu phòng vào cơ sở dữ liệu
        const savedRoom = await newRoom.save();
        res.status(201).json({ message: "Thêm phòng thành công", room: savedRoom });
    } catch (error) {
        console.error(error);
        res.status(500).json({ message: "Lỗi khi thêm phòng", error: error.message });
    }
});
// api xóa phòng 👽
router.delete('/DeleteRooms/:id', async (req, res) => {
    const { id } = req.params;
    if (!mongoose.Types.ObjectId.isValid(id)) {
        return res.status(400).json({ message: "Invalid room ID." });
    }
    try {
        const room = await Room.findById(id);
        if (!room) {
            return res.status(404).json({ message: "Room not found" });
        }
        await Room.findByIdAndDelete(id);
        res.status(200).json({ message: "Room deleted successfully." });
    } catch (error) {
        console.error('Error deleting room:', error.message);
        res.status(500).json({ message: "Failed to delete room", error: error.message });
    }
});


module.exports = router;
