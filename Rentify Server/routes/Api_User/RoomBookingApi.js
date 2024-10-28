var express = require("express");
var router = express.Router();

//booking
const Booking = require("../../models/Booking");
//room
const Room = require("../../models/Room");
// đặt phòng
router.post("/new-booking", async (req, res) => {
  try {
    const { user_id, room_id, check_in_date } = req.body;
    console.log(req.body);

    //kiem tra
    if (!user_id || !room_id || !check_in_date) {
      return res
        .status(400)
        .json({ message: "Vui lòng cung cấp đầy đủ thông tin đặt trước." });
    }

    //kiem tra xem phong da duoc thue hay chua
    const existingBooking = await Room.findOne({
      room_id,
      status: 1,
    });

    if (existingBooking) {
      return res.status(400).json({
        message: "Phòng này đã được cho thuê",
      });
    }
    const newBooking = new Booking({
      user_id: user_id,
      room_id: room_id,
      check_in_date: check_in_date,
    });

    await newBooking.save();
    res.status(201).json({
      message: "Đặt phòng thành công",
      data: newBooking,
    });
  } catch (error) {
    console.log(error);
    res.status(500).json({ message: "Lỗi hệ thống" });
  }
});

//xoa booking
router.delete("/delete-booking/:booking_id", async (req, res) => {
  try {
    const { booking_id } = req.params;

    //xoa theo id
    const deleteBooking = await Booking.findByIdAndDelete(booking_id);
    if (deleteBooking) {
      res.status(200).json({
        message: "Xóa thanh cong",
        data: deleteBooking,
      });
    } else {
      res.status(404).json({
        message: "Khong tim thay id",
      });
    }
  } catch (error) {
    console.log(error);
    res.status(500).json({
      message: "Lỗi hệ thống",
    });
  }
});

module.exports = router;
