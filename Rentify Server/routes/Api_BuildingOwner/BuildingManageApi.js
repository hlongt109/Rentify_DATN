// api/BuildingOwner/BuildingManageApi.js
var express = require('express');
var router = express.Router();
const mongoose = require('mongoose');
const Building = require('../../models/Building');
const Room = require('../../models/Room')
const handleServerError = require("../../utils/errorHandle");

//const upload = require('../../config/common/uploadImageRoom')
const fs = require('fs');
const path = require('path');

// #1. Thêm tòa nhà (POST): http://localhost:3000/api/buildings
router.post('/buildings', async (req, res) => {
    const { landlord_id, address, description, number_of_floors } = req.body;


    try {
        const newBuilding = new Building({
            landlord_id,
            address,
            description,
            number_of_floors,
            created_at: new Date().toISOString(),
            updated_at: new Date().toISOString(),
        });


        await newBuilding.save();
        res.status(201).json({ message: 'Building added successfully!', building: newBuilding });
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'Failed to add building.' });
    }
});


const upload = require('../../config/common/uploadImageRoom')

// #2. Lấy danh sách tất cả tòa nhà
router.get('/buildings/:id', async (req, res) => {
    const userId = req.params.id;
    try {
        const buildings = await Building.find({ landlord_id: userId })
            .populate('manager_id', 'username phoneNumber')
            .populate('service', 'name')

        res.status(200).json(buildings);
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'Failed to retrieve buildings.' });
    }
});

// #5. Xóa tòa nhà (DELETE): http://localhost:3000/api/buildings/{id}
router.delete('/buildings/:id', async (req, res) => {
    const { id } = req.params;
    try {
        const deletedBuilding = await Building.findByIdAndDelete(id);
        if (!deletedBuilding) {
            return res.status(404).json({ error: 'Building not found.' });
        }
        res.status(200).json({ message: 'Building deleted successfully!', building: deletedBuilding });
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'Failed to delete building.' });
    }
});


// các api thiên viết
router.post('/add-building', async (req, res) => {
    try {
        const {
            landlord_id,
            manager_id,
            service,
            serviceFees, // Thêm phí dịch vụ
            nameBuilding,
            address,
            description,
            number_of_floors
        } = req.body;

        // Kiểm tra các trường bắt buộc
        if (!landlord_id || !manager_id || !nameBuilding || !address || !number_of_floors) {
            return res.status(400).json({ message: 'Vui lòng điền đầy đủ thông tin!' });
        }

        // Kiểm tra tính hợp lệ của serviceFees (nếu có)
        if (serviceFees && !Array.isArray(serviceFees)) {
            return res.status(400).json({ message: 'Dữ liệu serviceFees không hợp lệ!' });
        }

        // Validate từng phí dịch vụ
        if (serviceFees) {
            for (const fee of serviceFees) {
                if (!fee.name || typeof fee.name !== 'string') {
                    return res.status(400).json({ message: 'Tên dịch vụ không hợp lệ!' });
                }
                if (!fee.price || typeof fee.price !== 'number') {
                    return res.status(400).json({ message: `Giá dịch vụ ${fee.name} không hợp lệ!` });
                }
            }
        }

        // Tạo đối tượng building mới
        const newBuilding = new Building({
            landlord_id,
            manager_id,
            service, // Đây là mảng ID các dịch vụ
            serviceFees, // Lưu phí dịch vụ vào cơ sở dữ liệu
            nameBuilding,
            address,
            description,
            number_of_floors,
            created_at: new Date().toISOString(),
            updated_at: new Date().toISOString(),
        });

        // Lưu vào database
        const savedBuilding = await newBuilding.save();

        res.status(201).json({
            message: 'Thêm tòa nhà thành công!',
            building: savedBuilding,
        });
    } catch (error) {
        console.error('Lỗi khi thêm tòa nhà:', error);
        res.status(500).json({ message: 'Đã xảy ra lỗi khi thêm tòa nhà!' });
    }
});

router.put('/buildings/:id', async (req, res) => {
    const { id } = req.params;
    const {
        address,
        description,
        number_of_floors,
        nameBuilding,
        service,
        serviceFees, // Danh sách phí dịch vụ
        manager_id
    } = req.body;

    // Validate ID là ObjectId hợp lệ
    if (!mongoose.Types.ObjectId.isValid(id)) {
        return res.status(400).json({ error: 'Invalid building ID.' });
    }

    if (manager_id && !mongoose.Types.ObjectId.isValid(manager_id)) {
        return res.status(400).json({ error: 'Invalid manager ID.' });
    }

    // Kiểm tra dữ liệu đầu vào
    if (!address || !description || !number_of_floors || !nameBuilding) {
        return res.status(400).json({
            error: 'All fields (address, description, number_of_floors, nameBuilding) are required.'
        });
    }

    // Kiểm tra service (nếu có)
    if (service && !Array.isArray(service)) {
        return res.status(400).json({ error: 'Service should be an array of ObjectIds.' });
    }

    if (service && !service.every((s) => mongoose.Types.ObjectId.isValid(s))) {
        return res.status(400).json({ error: 'Each service ID must be a valid ObjectId.' });
    }

    // Kiểm tra serviceFees (nếu có)
    if (serviceFees && !Array.isArray(serviceFees)) {
        return res.status(400).json({ error: 'Service fees should be an array of objects.' });
    }

    if (serviceFees) {
        for (const fee of serviceFees) {
            if (!fee.name || typeof fee.name !== 'string') {
                return res.status(400).json({ error: 'Each service fee must have a valid name.' });
            }
            if (!fee.price || typeof fee.price !== 'number') {
                return res.status(400).json({ error: `Service fee price for "${fee.name}" is invalid.` });
            }
        }
    }

    try {
        // Tìm tòa nhà bằng ID
        const building = await Building.findById(id);

        // Nếu không tìm thấy tòa nhà
        if (!building) {
            return res.status(404).json({ error: 'Building not found.' });
        }

        // Cập nhật thông tin tòa nhà
        const updatedBuilding = await Building.findByIdAndUpdate(
            id,
            {
                nameBuilding,
                address,
                description,
                number_of_floors,
                service, // Cập nhật dịch vụ
                serviceFees, // Cập nhật phí dịch vụ
                manager_id,
                updated_at: new Date().toISOString(),
            },
            { new: true, runValidators: true } // Đảm bảo validation của Mongoose chạy khi cập nhật
        );

        // Nếu cập nhật thành công, trả về tòa nhà đã được cập nhật
        return res.status(200).json({
            message: 'Building updated successfully!',
            building: updatedBuilding,
        });
    } catch (error) {
        console.error('Error updating building:', error.message);
        return res.status(500).json({ error: 'Failed to update building. Please try again later.' });
    }
});

router.get('/get-room/:building_id', async (req, res) => {
    try {
        // Lấy building_id từ tham số URL
        const { building_id } = req.params;

        // Tìm tất cả phòng trong tòa nhà đó
        const rooms = await Room.find({ building_id });

        // Kiểm tra nếu không có phòng nào
        if (!rooms || rooms.length === 0) {
            // Nếu không có phòng, trả về danh sách rỗng thay vì lỗi 404
            return res.json([]);
        }

        // Trả về kết quả
        return res.status(200).json(rooms);
    } catch (error) {
        // Nếu có lỗi xảy ra
        return res.status(500).json({ message: 'Server error', error: error.message });
    }
});

// API thêm phòng
router.post('/add-room', upload.fields([
    { name: 'photos_room', maxCount: 10 }, // Tối đa 10 ảnh
    { name: 'video_room', maxCount: 2 }   // Tối đa 2 video
]), async (req, res) => {
    try {
        const { building_id, room_name, room_type, description, price, decrease, size, service, amenities, limit_person, status, } = req.body;

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
            decrease,
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

// API cập nhật phòng
router.put('/update-room/:id', upload.fields([
    { name: 'photos_room', maxCount: 10 }, // Tối đa 10 ảnh
    { name: 'video_room', maxCount: 2 }   // Tối đa 2 video
]), async (req, res) => {
    const { id } = req.params;
    const { room_name, room_type, description, price, decrease, size, service, amenities, limit_person, status } = req.body;

    if (!mongoose.Types.ObjectId.isValid(id)) {
        return res.status(400).json({ error: 'Invalid room ID.' });
    }

    if (!room_name || !room_type || !description || !price || !size || !limit_person || status === undefined) {
        return res.status(400).json({ message: "Thiếu thông tin bắt buộc" });
    }

    try {
        // Lấy thông tin phòng cũ từ database
        const room = await Room.findById(id);
        if (!room) {
            return res.status(404).json({ error: 'Room not found.' });
        }

        // Lưu đường dẫn ảnh và video mới
        const photos_room = req.files.photos_room ? req.files.photos_room.map(file => file.path) : null;
        const video_room = req.files.video_room ? req.files.video_room.map(file => file.path) : null;

        // Xóa file ảnh cũ nếu có ảnh mới
        if (photos_room) {
            room.photos_room.forEach(photo => {
                const photoPath = path.join(photo); // Đường dẫn ảnh cũ
                if (fs.existsSync(photoPath)) {
                    try {
                        fs.unlinkSync(photoPath); // Xóa file
                        console.log(`Đã xóa ảnh: ${photoPath}`);
                    } catch (err) {
                        console.error(`Không thể xóa ảnh: ${photoPath}, lỗi: ${err.message}`);
                    }
                } else {
                    console.log(`Ảnh không tồn tại để xóa: ${photoPath}`);
                }
            });
        }

        // Xóa file video cũ nếu có video mới
        if (video_room) {
            room.video_room.forEach(video => {
                const videoPath = path.join(video); // Đường dẫn video cũ
                if (fs.existsSync(videoPath)) {
                    try {
                        fs.unlinkSync(videoPath); // Xóa file
                        console.log(`Đã xóa video: ${videoPath}`);
                    } catch (err) {
                        console.error(`Không thể xóa video: ${videoPath}, lỗi: ${err.message}`);
                    }
                } else {
                    console.log(`Video không tồn tại để xóa: ${videoPath}`);
                }
            });
        }

        // Tạo đối tượng cập nhật
        const updateData = {
            room_name,
            room_type,
            description,
            price,
            decrease,
            size,
            service,
            amenities,
            limit_person,
            status,
            updated_at: new Date().toISOString(),
        };

        // Ghi đè hoàn toàn nếu có ảnh/video mới
        if (photos_room) {
            updateData.photos_room = photos_room;
        }
        if (video_room) {
            updateData.video_room = video_room;
        }

        // Cập nhật phòng
        const updatedRoom = await Room.findByIdAndUpdate(id, updateData, { new: true, runValidators: true });

        res.status(200).json({ message: 'Room updated successfully!', room: updatedRoom });
    } catch (error) {
        console.error('Error updating room:', error.message);
        res.status(500).json({ error: 'Failed to update room. Please try again later.' });
    }
});

// API xóa phòng
router.delete('/delete-room/:id', async (req, res) => {
    const { id } = req.params;

    // Kiểm tra ID là ObjectId hợp lệ
    if (!mongoose.Types.ObjectId.isValid(id)) {
        return res.status(400).json({ error: 'Invalid room ID.' });
    }

    try {
        // Tìm phòng theo ID
        const room = await Room.findById(id);
        if (!room) {
            return res.status(404).json({ error: 'Room not found.' });
        }

        // Xóa file ảnh
        room.photos_room.forEach(photo => {
            const photoPath = path.join(photo);
            if (fs.existsSync(photoPath)) {
                try {
                    fs.unlinkSync(photoPath);
                    console.log(`Đã xóa ảnh: ${photoPath}`);
                } catch (err) {
                    console.error(`Không thể xóa ảnh: ${photoPath}, lỗi: ${err.message}`);
                }
            } else {
                console.log(`Ảnh không tồn tại để xóa: ${photoPath}`);
            }
        });

        // Xóa file video
        room.video_room.forEach(video => {
            const videoPath = path.join(video);
            if (fs.existsSync(videoPath)) {
                try {
                    fs.unlinkSync(videoPath);
                    console.log(`Đã xóa video: ${videoPath}`);
                } catch (err) {
                    console.error(`Không thể xóa video: ${videoPath}, lỗi: ${err.message}`);
                }
            } else {
                console.log(`Video không tồn tại để xóa: ${videoPath}`);
            }
        });

        // Xóa phòng khỏi database
        await Room.findByIdAndDelete(id);

        res.status(200).json({ message: 'Room deleted successfully!' });
    } catch (error) {
        console.error('Error deleting room:', error.message);
        res.status(500).json({ error: 'Failed to delete room. Please try again later.' });
    }
});

// API xóa tòa nhà với kiểm tra trạng thái phòng
router.delete('/delete-buildings/:id', async (req, res) => {
    const { id } = req.params;

    // Validate ID là ObjectId hợp lệ
    if (!mongoose.Types.ObjectId.isValid(id)) {
        return res.status(400).json({ error: 'Invalid building ID.' });
    }

    try {
        // Tìm tất cả phòng trong tòa nhà
        const rooms = await Room.find({ building_id: id });

        // Kiểm tra nếu có phòng nào đang cho thuê
        const rentedRooms = rooms.filter(room => room.status === 1);
        if (rentedRooms.length > 0) {
            const rentedRoomNames = rentedRooms.map(room => room.room_name).join(', ');
            return res.status(400).json({
                error: `Không thể xóa tòa nhà phòng vẫn đang cho thuê: ${rentedRoomNames}.`
            });
        }

        // Xóa tất cả ảnh và video của các phòng
        rooms.forEach(room => {
            room.photos_room.forEach(photo => {
                const photoPath = path.join(photo);
                if (fs.existsSync(photoPath)) {
                    try {
                        fs.unlinkSync(photoPath);
                        console.log(`Đã xóa ảnh: ${photoPath}`);
                    } catch (err) {
                        console.error(`Failed to delete photo: ${photoPath}, error: ${err.message}`);
                    }
                }
            });

            room.video_room.forEach(video => {
                const videoPath = path.join(video);
                if (fs.existsSync(videoPath)) {
                    try {
                        fs.unlinkSync(videoPath);
                        console.log(`Đã xóa video: ${videoPath}`);
                    } catch (err) {
                        console.error(`Failed to delete video: ${videoPath}, error: ${err.message}`);
                    }
                }
            });
        });

        // Xóa tất cả phòng liên quan đến tòa nhà
        await Room.deleteMany({ building_id: id });

        // Xóa tòa nhà
        const deletedBuilding = await Building.findByIdAndDelete(id);
        if (!deletedBuilding) {
            return res.status(404).json({ error: 'Building not found.' });
        }

        res.status(200).json({ message: 'Building and related rooms deleted successfully!', building: deletedBuilding });
    } catch (error) {
        console.error('Error deleting building:', error.message);
        res.status(500).json({ error: 'Failed to delete building. Please try again later.' });
    }
});

// API lấy danh sách dịch vụ của một tòa nhà cụ thể
router.get('/building/:id/services', async (req, res) => {
    const { id } = req.params;

    // Kiểm tra ID là ObjectId hợp lệ
    if (!mongoose.Types.ObjectId.isValid(id)) {
        return res.status(400).json({ error: 'Invalid building ID.' });
    }

    try {
        // Tìm tòa nhà theo ID và populate dịch vụ
        const building = await Building.findById(id)
            .populate('service', 'name');

        // Nếu không tìm thấy tòa nhà
        if (!building) {
            return res.status(404).json({ error: 'Building not found.' });
        }

        // Trả về danh sách dịch vụ của tòa nhà
        res.status(200).json(building.service);
    } catch (error) {
        console.error('Error fetching building services:', error.message);
        res.status(500).json({ error: 'Failed to fetch building services. Please try again later.' });
    }
});

// API lấy danh sách tiện nghi của một phòng cụ thể
router.get('/room/:id/amenities', async (req, res) => {
    const { id } = req.params;

    // Kiểm tra ID là ObjectId hợp lệ
    if (!mongoose.Types.ObjectId.isValid(id)) {
        return res.status(400).json({ error: 'Invalid room ID.' });
    }

    try {
        // Tìm phòng theo ID
        const room = await Room.findById(id).select('amenities');

        // Nếu không tìm thấy phòng
        if (!room) {
            return res.status(404).json({ error: 'Room not found.' });
        }

        // Trả về danh sách tiện nghi của phòng
        res.status(200).json(room.amenities);
    } catch (error) {
        console.error('Error fetching room amenities:', error.message);
        res.status(500).json({ error: 'Failed to fetch room amenities. Please try again later.' });
    }
});

// API đếm số phòng còn trống trong tòa nhà
router.get('/building/:id/available-rooms', async (req, res) => {
    const { id } = req.params;

    // Kiểm tra ID là ObjectId hợp lệ
    if (!mongoose.Types.ObjectId.isValid(id)) {
        return res.status(400).json({ error: 'Invalid building ID.' });
    }

    try {
        // Đếm số phòng còn trống (status = 0) trong tòa nhà
        const availableRoomsCount = await Room.countDocuments({ building_id: id, status: 0 });

        // Trả về kết quả
        res.status(200).json({ availableRooms: availableRoomsCount });
    } catch (error) {
        console.error('Error counting available rooms:', error.message);
        res.status(500).json({ error: 'Failed to count available rooms. Please try again later.' });
    }
});

// API lấy danh sách phí dịch vụ của một tòa nhà cụ thể
router.get('/building/:id/service-fees', async (req, res) => {
    const { id } = req.params;

    // Kiểm tra ID là ObjectId hợp lệ
    if (!mongoose.Types.ObjectId.isValid(id)) {
        return res.status(400).json({ error: 'Invalid building ID.' });
    }

    try {
        // Tìm tòa nhà theo ID và lấy danh sách phí dịch vụ
        const building = await Building.findById(id).select('serviceFees');

        // Nếu không tìm thấy tòa nhà
        if (!building) {
            return res.status(404).json({ error: 'Building not found.' });
        }

        // Trả về danh sách phí dịch vụ của tòa nhà
        res.status(200).json(building.serviceFees);
    } catch (error) {
        console.error('Error fetching building service fees:', error.message);
        res.status(500).json({ error: 'Failed to fetch building service fees. Please try again later.' });
    }
});
//
//hoan
router.get('/buildings/address/:buildingId', async (req, res) => {
    const { buildingId } = req.params;  // Lấy buildingId từ URL params

    try {
        // Tìm Building trong cơ sở dữ liệu
        const building = await Building.findById(buildingId);

        if (!building) {
            return res.status(404).json({ message: 'Building không tồn tại' });
        }

        // Trả về dữ liệu địa chỉ
        return res.json({
            success: true,
            data: {
                address: building.address  // Chỉ trả về địa chỉ
            }
        });
    } catch (error) {
        console.error(error);
        return res.status(500).json({ message: 'Lỗi server' });
    }
});
module.exports = router;