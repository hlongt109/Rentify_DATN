var express = require("express");
var router = express.Router();

var room = require("../../models/Room");
var building = require("../../models/Building");

// ====================Room api=========================
// lấy danh sách phòng
router.get("/get-list-rooms", async (req, res) => {
  try {
    const data = await room.find();
    res.status(200).send(data);
  } catch (error) {
    res.status(400).json({
      message: error.message,
    });
  }
});

// lấy thông tin chi tiết phòng
router.get("/get-detail-room/:id", async (req, res) => {
  try {
    const { id } = req.params;
    const data = await room.findById(id);
    res.status(200).send(data);
  } catch (error) {
    res.status(400).json({
      message: error.message,
    });
  }
});

// chức năng tìm kiếm phòng theo địa chỉ, khoảng giá
router.get("/search-room", async (req, res) => {
  try {
    const { address, minPrice, maxPrice, name } = req.query;

    let filter = {};

    // Thêm điều kiện tìm kiếm theo giá
    if (minPrice && maxPrice) {
      filter.price = {};
      if (minPrice) filter.price.$gte = Number(minPrice);
      if (maxPrice) filter.price.$lte = Number(maxPrice);
    }

    // Tìm các phòng và liên kết với toà nhà thông qua building_id
    const rooms = await room.find(filter).populate({
      path: "building_id",
      select: "address",
    });

    // Lọc bỏ các phòng không có địa chỉ phù hợp
    const filteredRooms = rooms.filter(
      (room) =>
        room.building_id &&
        room.building_id.address.match(new RegExp(address.trim(), "i"))
    );
    console.log("Filtered rooms:", filteredRooms);
    res.status(200).json(filteredRooms);
  } catch (error) {
    res.status(400).json({ message: error.message });
  }
});

module.exports = router;
