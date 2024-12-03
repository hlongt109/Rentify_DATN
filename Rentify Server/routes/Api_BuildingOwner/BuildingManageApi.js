// api/BuildingOwner/BuildingManageApi.js
var express = require("express");
var router = express.Router();
const mongoose = require("mongoose");
const Building = require("../../models/Building");
const Room = require("../../models/Room");

//const upload = require('../../config/common/uploadImageRoom')
const fs = require("fs");
const path = require("path");

// #1. Thêm tòa nhà (POST): http://localhost:3000/api/buildings
router.post("/buildings", async (req, res) => {
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
    res
      .status(201)
      .json({ message: "Building added successfully!", building: newBuilding });
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: "Failed to add building." });
  }
});

const upload = require("../../config/common/uploadImageRoom");

// #2. Lấy danh sách tất cả tòa nhà
router.get("/buildings/:id", async (req, res) => {
  const userId = req.params.id;
  try {
    const buildings = await Building.find({ landlord_id: userId })
      .populate("manager_id", "username phoneNumber")
      .populate("service", "name");

    res.status(200).json(buildings);
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: "Failed to retrieve buildings." });
  }
});

// #5. Xóa tòa nhà (DELETE): http://localhost:3000/api/buildings/{id}
router.delete("/buildings/:id", async (req, res) => {
  const { id } = req.params;
  try {
    const deletedBuilding = await Building.findByIdAndDelete(id);
    if (!deletedBuilding) {
      return res.status(404).json({ error: "Building not found." });
    }
    res
      .status(200)
      .json({
        message: "Building deleted successfully!",
        building: deletedBuilding,
      });
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: "Failed to delete building." });
  }
});
// API lấy danh sách dịch vụ của một tòa nhà cụ thể
router.get("/building/:id/services", async (req, res) => {
  const { id } = req.params;

  // các api thiên viết
  router.post("/add-building", async (req, res) => {
    try {
      const {
        landlord_id,
        manager_id,
        service,
        nameBuilding,
        address,
        description,
        number_of_floors,
      } = req.body;
      // Kiểm tra các trường bắt buộc
      if (
        !landlord_id ||
        !manager_id ||
        !nameBuilding ||
        !address ||
        !number_of_floors
      ) {
        return res
          .status(400)
          .json({ message: "Vui lòng điền đầy đủ thông tin!" });
      }

      // Tạo đối tượng building mới
      const newBuilding = new Building({
        landlord_id,
        manager_id,
        service, // Đây là mảng ID các dịch vụ
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
        message: "Thêm tòa nhà thành công!",
        building: savedBuilding,
      });
    } catch (error) {
      console.error("Lỗi khi thêm tòa nhà:", error);
      res.status(500).json({ message: "Đã xảy ra lỗi khi thêm tòa nhà!" });
    }
  });

  router.put("/buildings/:id", async (req, res) => {
    const { id } = req.params;
    const {
      address,
      description,
      number_of_floors,
      nameBuilding,
      service,
      manager_id,
    } = req.body;

    // Validate ID là ObjectId hợp lệ

    if (!mongoose.Types.ObjectId.isValid(id)) {
      return res.status(400).json({ error: "Invalid building ID." });
    }

    try {
      // Tìm tòa nhà theo ID và populate dịch vụ
      const building = await Building.findById(id).populate("service", "name");

      // Nếu không tìm thấy tòa nhà
      if (!building) {
        return res.status(404).json({ error: "Building not found." });
      }

      // Trả về danh sách dịch vụ của tòa nhà
      res.status(200).json(building.service);
    } catch (error) {
      console.error("Error fetching building services:", error.message);
      res
        .status(500)
        .json({
          error: "Failed to fetch building services. Please try again later.",
        });
    }
  });

  if (!mongoose.Types.ObjectId.isValid(manager_id)) {
    return res.status(400).json({ error: "Invalid building ID." });
  }

  // Kiểm tra dữ liệu đầu vào (đảm bảo các trường không rỗng)
  if (!address || !description || !number_of_floors || !nameBuilding) {
    return res
      .status(400)
      .json({
        error:
          "All fields (address, description, number_of_floors, nameBuilding) are required.",
      });
  }

  // Kiểm tra service có phải là một mảng và mỗi phần tử là một ObjectId hợp lệ
  if (service && !Array.isArray(service)) {
    return res
      .status(400)
      .json({ error: "Service should be an array of ObjectIds." });
  }

  if (service && !service.every((s) => mongoose.Types.ObjectId.isValid(s))) {
    return res
      .status(400)
      .json({ error: "Each service ID must be a valid ObjectId." });
  }

  try {
    // Tìm tòa nhà bằng ID và cập nhật
    const updatedBuilding = await Building.findByIdAndUpdate(
      id,
      {
        nameBuilding,
        address,
        description,
        number_of_floors,
        service, // Cập nhật dịch vụ
        manager_id,
        updated_at: new Date().toISOString(),
      },
      { new: true, runValidators: true } // Đảm bảo validation của Mongoose chạy khi cập nhật
    );

    // Nếu không tìm thấy building
    if (!updatedBuilding) {
      return res.status(404).json({ error: "Building not found." });
    }

    // Thành công, trả về building đã được cập nhật
    res
      .status(200)
      .json({
        message: "Building updated successfully!",
        building: updatedBuilding,
      });
  } catch (error) {
    console.error("Error updating building:", error.message);
    res
      .status(500)
      .json({ error: "Failed to update building. Please try again later." });
  }
});

router.get("/get-room/:building_id", async (req, res) => {
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
    return res
      .status(500)
      .json({ message: "Server error", error: error.message });
  }
});

// API thêm phòng
router.post(
  "/add-room",
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
      } = req.body;

      // Kiểm tra dữ liệu bắt buộc
      if (
        !building_id ||
        !room_name ||
        !room_type ||
        !description ||
        !price ||
        !size ||
        !limit_person ||
        status === undefined
      ) {
        return res.status(400).json({ message: "Thiếu thông tin bắt buộc" });
      }

      // Lưu đường dẫn ảnh và video
      const photos_room = req.files.photos_room
        ? req.files.photos_room.map((file) => file.path)
        : [];
      const video_room = req.files.video_room
        ? req.files.video_room.map((file) => file.path)
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
        service,
        amenities,
        limit_person,
        status,
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

// API lấy chi tiết phòng theo ID
const normalizePaths = (room) => {
  // Loại bỏ '/landlord/' nếu có trong đường dẫn
  const removeUnnecessaryPath = (path) => path.replace(/^\/landlord\//, "");

  room.photos_room = room.photos_room.map((photo) =>
    removeUnnecessaryPath(photo.replace(/\\/g, "/"))
  );
  room.video_room = room.video_room.map((video) =>
    removeUnnecessaryPath(video.replace(/\\/g, "/"))
  );
  return room;
};

router.get("/room/:id", async (req, res) => {
  const { id } = req.params;

  // Kiểm tra ID là ObjectId hợp lệ
  if (!mongoose.Types.ObjectId.isValid(id)) {
    return res.status(400).json({ error: "Invalid room ID." });
  }

  try {
    // Tìm phòng theo ID
    const room = await Room.findById(id).lean(); // Dùng `lean()` để lấy dữ liệu dạng JSON

    // Nếu không tìm thấy phòng
    if (!room) {
      return res.status(404).json({ error: "Room not found." });
    }

    // Chuẩn hóa dữ liệu trước khi trả về
    const normalizedRoom = normalizePaths(room);

    // Trả về chi tiết phòng
    res.status(200).json(normalizedRoom);
  } catch (error) {
    console.error("Error fetching room details:", error.message);
    res
      .status(500)
      .json({ error: "Failed to fetch room details. Please try again later." });
  }
});

module.exports = router;
