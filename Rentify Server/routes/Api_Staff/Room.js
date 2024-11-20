const express = require('express');
const router = express.Router();
const mongoose = require('mongoose');
const Building = require('../../models/Building');
const Room = require('../../models/Room');
const upload = require('../../config/common/uploadImageRoom')

router.get('/buildings-by-manager/:manager_id', async (req, res) => {
    const { manager_id } = req.params;

    // Validate ID là ObjectId hợp lệ
    if (!mongoose.Types.ObjectId.isValid(manager_id)) {
        return res.status(400).json({ error: 'Invalid manager ID.' });
    }

    try {
        // Lấy danh sách tòa nhà của manager
        const buildings = await Building.find({ manager_id })
            .populate('landlord_id', 'username phoneNumber')
            .populate('manager_id', 'username phoneNumber')
            .populate('service', 'name');

        // Kiểm tra nếu không có tòa nhà nào
        if (!buildings || buildings.length === 0) {
            return res.status(404).json({ message: 'No buildings found for this manager.' });
        }

        // Lặp qua tất cả các tòa nhà và lấy danh sách phòng của từng tòa
        const buildingsWithRooms = [];

        for (let building of buildings) {
            // Lấy danh sách phòng cho mỗi tòa nhà
            const rooms = await Room.find({ building_id: building._id }).select('room_name room_type price status');

            // Thêm danh sách phòng vào đối tượng building
            building = building.toObject(); // Chuyển đổi building thành đối tượng JavaScript để có thể chỉnh sửa
            building.rooms = rooms; // Thêm trường rooms vào building

            buildingsWithRooms.push(building);
        }

        // Trả về danh sách các tòa nhà và phòng của chúng
        res.status(200).json(buildingsWithRooms);
    } catch (error) {
        console.error('Error fetching buildings and rooms:', error.message);
        res.status(500).json({ error: 'Failed to fetch buildings and rooms. Please try again later.' });
    }
});


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



// _vanphuc :thêm phòng 
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
