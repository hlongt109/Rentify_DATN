const express = require('express');
const router = express.Router();
const mongoose = require('mongoose');
const Building = require('../../models/Building');
const Room = require('../../models/Room');
const Service = require('../../models/Service');
const upload = require('../../config/common/uploadImageRoom')

const Invoice = require('../../models/Invoice')
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
    const room = await Room.findById(id).lean().populate("service", "name");
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
router.post(
  "/addRoom",
  upload.fields([
    { name: "photos_room", maxCount: 10 }, // Tối đa 10 ảnh
    { name: "video_room", maxCount: 2 }, // Tối đa 2 video
  ]),
  async (req, res) => {
    try {
      const {
        building_id,
        room_name,
        room_type,
        description,
        price,
        size,
        service,
        amenities,
        limit_person,
        status,
        sale
      } = req.body;

      // Kiểm tra dữ liệu bắt buộc
      if (
        !building_id ||
        !room_name ||
        !room_type ||
        !description ||
        !price ||
        !size ||
        status === undefined
      ) {
        return res.status(400).json({ message: "Thiếu thông tin bắt buộc" });
      }

      // Lưu đường dẫn ảnh và video
      const photos_room = req.files.photos_room
        ? req.files.photos_room.map((file) => file.path) // Lưu chuỗi URL thay vì đối tượng
        : [];
      const video_room = req.files.video_room
        ? req.files.video_room.map((file) => file.path)
        : [];

      const parsedAmenities = Array.isArray(amenities)
        ? amenities
        : typeof amenities === "string"
          ? JSON.parse(amenities)
          : [];
      const parsedService = Array.isArray(service)
        ? service
        : typeof service === "string"
          ? JSON.parse(service)
          : [];

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
        service: parsedService,
        amenities: parsedAmenities,
        limit_person,
        status,
        sale,
        created_at: new Date().toISOString(),
        updated_at: new Date().toISOString(),
      });

      // Lưu phòng vào cơ sở dữ liệu
      const savedRoom = await newRoom.save();
      res
        .status(201)
        .json({ message: "Thêm phòng thành công", room: savedRoom });
    } catch (error) {
      console.error(error);
      res
        .status(500)
        .json({ message: "Lỗi khi thêm phòng", error: error.message });
    }
  }
);
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
// API cập nhật phòng 😘
router.put(
  "/updateRoom/:id",
  upload.fields([
    { name: "photos_room", maxCount: 10 }, // Tối đa 10 ảnh
    { name: "video_room", maxCount: 2 }, // Tối đa 2 video
  ]),
  async (req, res) => {
    try {
      const { id } = req.params;
      const {
        building_id,
        room_name,
        room_type,
        description,
        price,
        size,
        service,
        amenities,
        limit_person,
        status,
        sale,
      } = req.body;

      // Kiểm tra nếu `id` không hợp lệ
      if (!mongoose.Types.ObjectId.isValid(id)) {
        return res.status(400).json({ message: "ID không hợp lệ" });
      }

      // Tìm phòng theo ID
      const room = await Room.findById(id);
      if (!room) {
        return res.status(404).json({ message: "Không tìm thấy phòng" });
      }

      // Lưu đường dẫn ảnh và video
      const photos_room = req.files.photos_room
        ? req.files.photos_room.map((file) => file.path)
        : room.photos_room; // Giữ nguyên nếu không có ảnh mới
      const video_room = req.files.video_room
        ? req.files.video_room.map((file) => file.path)
        : room.video_room; // Giữ nguyên nếu không có video mới

      // Xử lý mảng amenities và service
      const parsedAmenities = Array.isArray(amenities)
        ? amenities
        : typeof amenities === "string"
          ? JSON.parse(amenities)
          : room.amenities;
      const parsedService = Array.isArray(service)
        ? service
        : typeof service === "string"
          ? JSON.parse(service)
          : room.service;

      // Cập nhật thông tin phòng
      room.building_id = building_id || room.building_id;
      room.room_name = room_name || room.room_name;
      room.room_type = room_type || room.room_type;
      room.description = description || room.description;
      room.price = price || room.price;
      room.size = size || room.size;
      room.video_room = video_room;
      room.photos_room = photos_room;
      room.service = parsedService;
      room.amenities = parsedAmenities;
      room.limit_person = limit_person || room.limit_person;
      room.status = status !== undefined ? status : room.status;
      room.sale = sale || room.sale;
      room.updated_at = new Date().toISOString();

      // Lưu thông tin đã cập nhật
      const updatedRoom = await room.save();

      res.status(200).json({
        message: "Cập nhật phòng thành công",
        room: updatedRoom,
      });
    } catch (error) {
      console.error(error);
      res
        .status(500)
        .json({ message: "Lỗi khi cập nhật phòng", error: error.message });
    }
  }
);

// API lấy danh sách dịch vụ của một tòa nhà cụ thể
router.get('/building/:id/services', async (req, res) => {
  const { id } = req.params;

  // Kiểm tra ID là ObjectId hợp lệ
  if (!mongoose.Types.ObjectId.isValid(id)) {
    return res.status(400).json({ error: 'Invalid building ID.' });
  }

  try {
    // Tìm tòa nhà theo ID và chỉ lấy _id và name của dịch vụ
    const building = await Building.findById(id)
      .populate('service', 'name'); // Chỉ lấy name của service

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

router.get('/Listservices', async (req, res) => {
  try {
    // Find all services
    const services = await Service.find().select('_id landlord_id name description price photos created_at updated_at');

    // Modify the response structure to match the desired format
    const formattedServices = services.map(service => ({
      _id: service._id,
      landlord_id: service.landlord_id, // Only return the ObjectId without populated data
      name: service.name,
      description: service.description,
      price: service.price,
      photos: service.photos,
      created_at: service.created_at,
      updated_at: service.updated_at,
      __v: service.__v
    }));

    res.status(200).json(formattedServices); // Return the formatted list
  } catch (error) {
    console.error(error);
    res.status(500).json({
      message: 'Failed to fetch the list of services',
      error: error.message
    });
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

router.get("/get-room-buildingId/:buildingId", async (req, res) => {
  try {
    const buildingId = req.params.buildingId;
    const rooms = await Room.find({ building_id: buildingId })
      .populate('service')  // thêm populate để lấy thông tin chi tiết của service
      .populate('building_id', "serviceFees"); // có thể thêm populate building nếu cần
    res.json({
      status: 200,
      message: "Lấy danh sách phòng thành công",
      data: rooms
    });
  } catch (error) {
    res.status(500).json({ message: "Có lỗi xảy ra", error: error.message });
  }
});
////
// API: Tổng số phòng theo manager_id
router.get('/RoomsSummaryByManager/:manager_id', async (req, res) => {
  try {
    const { manager_id } = req.params;

    // Bước 1: Lấy danh sách tòa nhà do manager quản lý
    const buildings = await Building.find({ manager_id }).select('_id');
    const buildingIds = buildings.map(building => building._id);

    // Kiểm tra nếu không có tòa nhà nào
    if (buildingIds.length === 0) {
      return res.json({
        totalRooms: 0,
        available: 0,
        rented: 0
      });
    }

    // Bước 2: Lọc và tính toán tổng số phòng cho các tòa nhà này
    const summary = await Room.aggregate([
      {
        $match: { building_id: { $in: buildingIds } } // Chỉ các phòng thuộc các building_id của manager
      },
      {
        $group: {
          _id: null, // Không nhóm theo building_id
          totalRooms: { $sum: 1 }, // Tổng số phòng
          available: { $sum: { $cond: [{ $eq: ["$status", 0] }, 1, 0] } }, // Số phòng status = 0
          rented: { $sum: { $cond: [{ $eq: ["$status", 1] }, 1, 0] } } // Số phòng status = 1
        }
      },
      {
        $project: {
          _id: 0, // Loại bỏ _id khỏi kết quả
          totalRooms: 1,
          available: 1,
          rented: 1
        }
      }
    ]);

    // Trả về kết quả
    return res.status(200).json(summary[0] || {
      totalRooms: 0,
      available: 0,
      rented: 0
    });
  } catch (error) {
    // Nếu có lỗi xảy ra
    return res.status(500).json({ message: 'Server error', error: error.message });
  }
});
///
module.exports = router
