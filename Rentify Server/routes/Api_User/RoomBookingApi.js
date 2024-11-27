var express = require("express");
var router = express.Router();

const Booking = require("../../models/Booking");
const Room = require("../../models/Room");
const User = require("../../models/User")

// Thêm lịch xem phòng (add booking)
router.post('/add-booking', async (req, res) => {
  try {
    const { user_id, room_id, manager_id, check_in_date } = req.body;

    // Kiểm tra nếu phòng đã được đặt trước đó
    const existingBooking = await Booking.findOne({ room_id, check_in_date });
    if (existingBooking) {
      return res.status(400).json({
        message: "Phòng đã được đặt vào thời gian này"
      });
    }

    // Tạo booking mới
    const newBooking = new Booking({
      user_id,
      room_id,
      manager_id,
      check_in_date,
      status: 0 // Đang xử lý
    });

    // Lưu booking vào cơ sở dữ liệu
    await newBooking.save();

    res.status(201).json({
      message: "Đặt lịch xem phòng thành công",
      booking: newBooking
    });
  } catch (error) {
    res.status(500).json({
      message: "Đã có lỗi xảy ra khi đặt lịch xem phòng",
      error: error.message
    });
  }
});

// Lấy danh sách booking theo user_id
router.get('/get-bookings/:user_id', async (req, res) => {
  try {
    const { user_id } = req.params;

    // Tìm các booking theo user_id và populate thông tin phòng và quản lý
    const bookings = await Booking.find({ user_id })
      .populate({
      path: 'room_id',
      select: 'room_name room_type price description size video_room photos_room amenities limit_person status',
      })
      .populate({
      path: 'manager_id',
      select: 'name phoneNumber',
      })
      .populate({
      path: 'user_id',
      select: 'name phoneNumber',
      });

    if (bookings.length === 0) {
      return res.status(404).json({
        message: "Không tìm thấy booking nào của người dùng này"
      });
    }

    res.status(200).json(bookings);
  } catch (error) {
    res.status(500).json({
      message: "Đã có lỗi xảy ra khi lấy danh sách booking",
      error: error.message
    });
  }
});

// Lấy chi tiết người dùng theo user_id
router.get('/get-user-details/:user_id', async (req, res) => {
  try {
    const { user_id } = req.params;

    // Tìm người dùng theo user_id và chỉ lấy các trường cần thiết
    const user = await User.findById(user_id).select('_id name phoneNumber');

    if (!user) {
      return res.status(404).json({
        message: "Không tìm thấy người dùng"
      });
    }

    res.status(200).json(user);
  } catch (error) {
    res.status(500).json({
      message: "Đã có lỗi xảy ra khi lấy chi tiết người dùng",
      error: error.message
    });
  }
});

// Lấy danh sách booking theo user_id và status
router.get('/get-bookings/:user_id/:status', async (req, res) => {
  try {
    const { user_id, status } = req.params;

    // Tìm các booking theo user_id và status, populate thông tin phòng và quản lý
    const bookings = await Booking.find({ user_id, status })
      .populate({
        path: 'room_id',
        select: '_id room_name room_type' // Chỉ lấy các trường _id, room_name, room_type
      })
      .populate({
        path: 'manager_id',
        select: '_id name phoneNumber'
      });

    if (bookings.length === 0) {
      return res.status(404).json({
        message: "Không tìm thấy booking nào của người dùng này với trạng thái này"
      });
    }

    res.status(200).json(bookings);
  } catch (error) {
    res.status(500).json({
      message: "Đã có lỗi xảy ra khi lấy danh sách booking",
      error: error.message
    });
  }
});

// Cập nhật trạng thái booking
router.put('/update-booking-status/:booking_id', async (req, res) => {
  try {
    const { booking_id } = req.params;
    const { status } = req.body;

    // Tìm booking theo booking_id và cập nhật trạng thái
    const updatedBooking = await Booking.findByIdAndUpdate(
      booking_id,
      { status },
      { new: true }
    );

    if (!updatedBooking) {
      return res.status(404).json({
        message: "Không tìm thấy booking"
      });
    }

    res.status(200).json({
      message: "Cập nhật trạng thái booking thành công",
      booking: updatedBooking
    });
  } catch (error) {
    res.status(500).json({
      message: "Đã có lỗi xảy ra khi cập nhật trạng thái booking",
      error: error.message
    });
  }
});

module.exports = router;
