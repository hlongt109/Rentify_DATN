const express = require('express');
const router = express.Router();
const Room = require('../../models/Room');
const upload = require('../../config/common/upload');
// Lấy danh sách phòng
router.get('/listRoom/:buildingId', async (req, res) => {
    const { buildingId } = req.params;

    try {
        // Tìm các phòng theo buildingId
        const rooms = await Room.find({ building_id: buildingId });

        // Kiểm tra xem có phòng nào không
        if (!rooms || rooms.length === 0) {
            return res.status(404).json({ message: "Không tìm thấy phòng cho tòa nhà này." });
        }

        // Nếu tìm thấy phòng, trả về dữ liệu phòng
        res.status(200).json(rooms);
    } catch (error) {
        console.error(error); // Ghi lại lỗi để dễ dàng theo dõi
        res.status(500).json({ message: "Đã xảy ra lỗi khi lấy danh sách phòng." });
    }
});
// _vanphuc :thêm phòng 
router.post('/AddRoom', upload.fields([{ name: 'video_room' }, { name: 'photos_room' }]), async (req, res) => {
    const room = new Room({
        building_id: req.body.building_id,
        room_name:req.body.room_name,
        room_type: req.body.room_type,
        description: req.body.description,
        price: req.body.price,
        size: req.body.size,
        status: req.body.status,
        video_room: req.files['video_room'] ? req.files['video_room'][0].path.replace('public/', '') : req.body.video_room, // Giữ nguyên nếu không cập nhật
        photos_room: req.files['photos_room'] ? req.files['photos_room'].map(file => file.path.replace('public/', '')) : req.body.photos_room, // Giữ nguyên nếu không cập nhật
        service: req.body.service || [],
        amenities: req.body.amenities || [],
        limit_person: req.body.limit_person,
        created_at: new Date().toISOString(),
        updated_at: new Date().toISOString()
    });

    try {
        const savedRoom = await room.save();
        res.status(201).json(savedRoom); // Trả về thông tin phòng đã được thêm
    } catch (error) {
        res.status(400).json({ message: error.message }); // Xử lý lỗi
    }
});



router.put('/update/:id', upload.fields([{ name: 'video_room' }, { name: 'photos_room' }]), async (req, res) => {
    try {
        // Tìm phòng theo ID và cập nhật
        const updatedRoom = await Room.findByIdAndUpdate(
            req.params.id,
            {
                landlord_id: req.body.landlord_id,
                building_id: req.body.building_id,
                room_type: req.body.room_type,
                description: req.body.description,
                price: req.body.price,
                size: req.body.size,
                status: req.body.status,
                availability_status: req.body.availability_status,
                video_room: req.files['video_room'] ? req.files['video_room'][0].path.replace('public/', '') : req.body.video_room, // Giữ nguyên nếu không cập nhật
                photos_room: req.files['photos_room'] ? req.files['photos_room'].map(file => file.path.replace('public/', '')) : req.body.photos_room, // Giữ nguyên nếu không cập nhật
                service_ids: req.body.service_ids,
                amenities: req.body.amenities,
                service_fees: req.body.service_fees,
                limit_person: req.body.limit_person,
                updated_at: new Date().toISOString()
            },
            { new: true, runValidators: true }
        );

        if (!updatedRoom) {
            return res.status(404).json({ message: "Room not found" });
        }
        res.json(updatedRoom);
    } catch (error) {
        res.status(400).json({ message: error.message });
    }
});


// Tìm kiếm phòng
router.get('/search', async (req, res) => {
    const { price, room_type, availability_status } = req.query;
    try {
        const query = {};
        if (price) {
            query.price = price;
        }
        if (room_type) {
            query.room_type = room_type;
        }
        if (availability_status) {
            query.availability_status = availability_status;
        }

        const rooms = await Room.find(query);
        res.json(rooms);
        console.log(rooms);

    } catch (error) {
        res.status(500).json({ message: error.message });
    }
});
module.exports = router;
