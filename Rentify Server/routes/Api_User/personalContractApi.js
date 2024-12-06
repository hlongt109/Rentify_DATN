var express = require("express");
var router = express.Router();

//contract
const Contract = require("../../models/Contract");
//request
const Request = require("../../models/Request");

//lay tat ca thong tin ca nhan
router.get("/contract/:user_id", async (req, res) => {
  try {
    const { user_id } = req.params;
    if (!user_id) {
      return res.status(404).json({
        message: "Khong tim thay nguoi dung",
      });
    }
    const contracts = await Contract.find({ user_id });
    if (contracts) {
      return res.status(200).json({
        message: "Lay thanh cong",
        data: contracts,
      });
    }
  } catch (error) {
    console.log(error);
    res.status(500).json({
      message: "Lỗi hệ thống",
    });
  }
});

//lay chi tiet hop dong
router.get("/contract_detail:contract_id", async (req, res) => {
  try {
    const { contract_id } = req.params;
    if (!contract) {
      return res.status(404).json({
        message: "Khong tim thay hoa don",
      });
    }
    const contract = await Contract.findById(contract_id);
    if (contract) {
      return res.status(200).json({
        message: "Lay thanh cong",
        data: contract,
      });
    }
  } catch (error) {
    console.log(error);
    res.status(500).json({
      message: "Lỗi hệ thống",
    });
  }
});

// Gửi yêu cầu hủy hợp đồng, gia hạn hợp đồng
router.post("/request-contract", async (req, res) => {
  try {
    const { user_id, room_id, request_type, description } = req.body;

    // Kiểm tra các trường thông tin bắt buộc
    if (!user_id || !room_id || !request_type) {
      return res
        .status(400)
        .json({ message: "Vui lòng cung cấp đầy đủ thông tin." });
    }

    // Tạo yêu cầu mới
    const newRequest = new Request({
      user_id,
      room_id,
      request_type,
      description,
      status: 0, // Mặc định là "Đang xử lý"
      created_at: new Date().toISOString(),
      updated_at: new Date().toISOString(),
    });

    // Lưu yêu cầu vào cơ sở dữ liệu
    await newRequest.save();

    res.status(201).json({
      message: "Yêu cầu đã được gửi thành công.",
      data: newRequest,
    });
  } catch (error) {
    console.error(error);
    res.status(500).json({ message: "Lỗi hệ thống" });
  }
});

// Lấy chi tiết hợp đồng và thông tin liên quan
router.get("/contract-detail/:user_id", async (req, res) => {
  try {
    const { user_id } = req.params;
    if (!user_id) {
      return res.status(404).json({
        message: "Không tìm thấy người dùng",
      });
    }
    const contracts = await Contract.find({ user_id })
      .populate({
        path: "room_id",
        select: "room_name room_type price",
      })
      .populate({
        path: "building_id",
        select: "nameBuilding",
      })
      .populate({
        path: "user_id",
        select: "name",
      });

    if (contracts.length === 0) {
      return res.status(404).json({
        message: "Không tìm thấy hợp đồng nào",
      });
    }

    res.status(200).json(contracts);
  } catch (error) {
    console.log(error);
    res.status(500).json({
      message: "Lỗi hệ thống",
    });
  }
});

// API kiểm tra hợp đồng của người dùng
router.get("/check-contract/:userId", async (req, res) => {
  try {
    const userId = req.params.userId;

    // Tìm hợp đồng của user với status = 1 (đang có hiệu lực)
    const contract = await Contract.findOne({
      user_id: userId,
      status: 0, // Chỉ lấy hợp đồng đang có hiệu lực
    });

    // Kiểm tra nếu không tìm thấy hợp đồng
    if (!contract) {
      return res.status(404).json({
        success: false,
        message: "Bạn không có hợp đồng hoặc hợp đồng đã hết hiệu lực",
      });
    }

    return res.status(200).json({
      status: 200,
      message: "Người dùng có hợp đồng còn hiệu lực",
      data: [
        {
          contract,
        },
      ],
    });
  } catch (error) {
    return res.status(500).json({
      success: false,
      message: "Lỗi server: " + error.message,
    });
  }
});

module.exports = router;
