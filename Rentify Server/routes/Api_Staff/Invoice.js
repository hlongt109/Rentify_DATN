const express = require("express");
const router = express.Router();
const Invoice = require("../../models/Invoice");
const Building = require("../../models/Building");
const Room = require("../../models/Room");
const User = require("../../models/User");
const { route } = require("./Room");
// Liệt kê tất cả hóa đơn
router.get("/listInvoiceStaff/:staffId", async (req, res) => {
  try {
    // Lấy staffId từ req.params
    const staffId = req.params.staffId;

    if (!staffId) {
      return res.status(400).json({ message: "Không tìm thấy nhân viên" });
    }

    // Lấy danh sách các tòa nhà mà nhân viên quản lý
    const buildings = await Building.find({ staff_id: staffId }).select("_id");

    if (buildings.length === 0) {
      return res
        .status(404)
        .json({ message: "Không tìm thấy tòa nhà nào được quản lý" });
    }

    const buildingIds = buildings.map((building) => building._id);

    // Lấy danh sách các phòng trong các tòa nhà đó
    const rooms = await Room.find({ building_id: { $in: buildingIds } }).select(
      "_id"
    );

    if (rooms.length === 0) {
      return res
        .status(404)
        .json({ message: "Không tìm thấy phòng nào trong tòa nhà" });
    }

    const roomIds = rooms.map((room) => room._id);

    // Lấy hóa đơn dựa trên room_id
    // Lấy danh sách các hóa đơn trong các tòa nhà đó
    const invoices = await Invoice.find({
      room_id: { $in: roomIds },
    })
      .populate({
        path: "room_id",
        select: "room_name price service limit_person",
        populate: {
          path: "service",
          select: "name description price",
        },
      })
      .populate("user_id", "name phoneNumber");

    // Kiểm tra nếu không có hóa đơn
    if (invoices.length === 0) {
      return res.status(404).json({ message: "Không tìm thấy hóa đơn nào" });
    }
    // Chia hóa đơn thành 2 loại: đã thanh toán và chưa thanh toán
    const paidInvoices = invoices.filter(
      (invoice) => invoice.payment_status === "paid"
    );
    const unpaidInvoices = invoices.filter(
      (invoice) => invoice.payment_status === "unpaid"
    );

    // Trả kết quả
    return res.status(200).json({
      status: 200,
      message: "Lấy danh sách hóa đơn thành công",
      data: {
        paid: paidInvoices,
        unpaid: unpaidInvoices,
      },
    });
  } catch (error) {
    console.error(error);
    return res.status(500).json({ message: "Internal server error" });
  }
});

//them hoa don
router.post("/addBillStaff", async (req, res) => {
  try {
    const { user_id, room_id, description, amount, due_date, payment_status } =
      req.body;

    // Kiểm tra dữ liệu đầu vào
    if (!user_id || !room_id || !amount || !due_date) {
      return res.status(400).json({
        status: 400,
        message: "Vui lòng điền đầy đủ thông tin hóa đơn",
      });
    }

    // Kiểm tra phòng có tồn tại không
    const room = await Room.findById(room_id);
    if (!room) {
      return res.status(404).json({
        status: 404,
        message: "Không tìm thấy phòng",
      });
    }

    // Kiểm tra người dùng có tồn tại không
    const user = await User.findById(user_id);
    if (!user) {
      return res.status(404).json({
        status: 404,
        message: "Không tìm thấy người dùng",
      });
    }

    // Lấy tháng và năm từ due_date
    const billDate = new Date(due_date);
    const billMonth = billDate.getMonth() + 1; // getMonth() trả về 0-11
    const billYear = billDate.getFullYear();

    // Kiểm tra xem phòng đã có hóa đơn trong tháng này chưa
    const existingInvoice = await Invoice.findOne({
      room_id: room_id,
      due_date: {
        $regex: `${billYear}-${String(billMonth).padStart(2, "0")}`,
      },
    });

    if (existingInvoice) {
      return res.status(400).json({
        status: 400,
        message: `Phòng này đã có hóa đơn trong tháng ${billMonth}/${billYear}`,
        existingInvoice: {
          invoice_id: existingInvoice._id,
          created_at: existingInvoice.created_at,
          amount: existingInvoice.amount,
        },
      });
    }

    // Tạo hóa đơn mới
    const newInvoice = new Invoice({
      user_id,
      room_id,
      description: description || [],
      amount,
      due_date,
      payment_status: payment_status || "unpaid",
      created_at: new Date().toISOString(),
    });

    // Lưu hóa đơn vào database
    const savedInvoice = await newInvoice.save();

    // Populate thông tin chi tiết
    const populatedInvoice = await Invoice.findById(savedInvoice._id)
      .populate({
        path: "room_id",
        select: "room_name building_id",
        populate: {
          path: "building_id",
          select: "nameBuilding address",
        },
      })
      .populate("user_id", "name phoneNumber");

    return res.status(200).json({
      status: 200,
      message: "Thêm hóa đơn thành công",
      data: populatedInvoice,
    });
  } catch (error) {
    console.error("Error:", error);
    return res.status(500).json({
      status: 500,
      message: "Lỗi khi thêm hóa đơn",
      error: error.message,
    });
  }
});

//xan nhan hoa don da thanh toan
router.put('/confirmPaidInvoice/:invoiceId', async (req, res) => {
  try {
    const { invoiceId } = req.params;
    const { payment_status } = req.body;

    // Kiểm tra invoiceId có hợp lệ không
    if (!invoiceId) {
      return res.status(400).json({
        status: 400,
        message: "ID hóa đơn không được để trống"
      });
    }

    // Kiểm tra payment_status có hợp lệ không
    if (!payment_status || !['paid', 'unpaid'].includes(payment_status)) {
      return res.status(400).json({
        status: 400,
        message: "Trạng thái thanh toán không hợp lệ"
      });
    }

    // Tìm và cập nhật hóa đơn
    const updatedInvoice = await Invoice.findByIdAndUpdate(
      invoiceId,
      {
        payment_status,
        updated_at: new Date().toISOString()
      },
      { new: true } // Trả về document sau khi cập nhật
    ).populate({
      path: 'room_id',
      select: 'room_name price service limit_person',
      populate: {
        path: 'service',
        select: 'name description price'
      }
    }).populate('user_id', 'name phoneNumber');

    // Kiểm tra nếu không tìm thấy hóa đơn
    if (!updatedInvoice) {
      return res.status(404).json({
        status: 404,
        message: "Không tìm thấy hóa đơn"
      });
    }

    // Trả về kết quả
    return res.status(200).json({
      status: 200,
      message: "Cập nhật trạng thái hóa đơn thành công",
      data: updatedInvoice
    });

  } catch (error) {
    console.error('Error updating invoice:', error);
    return res.status(500).json({
      status: 500,
      message: "Đã có lỗi xảy ra khi cập nhật hóa đơn",
      error: error.message
    });
  }
});

// Cập nhật thông tin hóa đơn
router.put('/updateInvoice/:invoiceId', async (req, res) => {
  try {
    const { invoiceId } = req.params;
    const { description, amount, due_date } = req.body;

    // Kiểm tra invoiceId có hợp lệ không
    if (!invoiceId) {
      return res.status(400).json({
        status: 400,
        message: "ID hóa đơn không được để trống"
      });
    }

    // Tìm hóa đơn cần cập nhật
    const invoice = await Invoice.findById(invoiceId);
    if (!invoice) {
      return res.status(404).json({
        status: 404,
        message: "Không tìm thấy hóa đơn"
      });
    }

    // Kiểm tra nếu hóa đơn đã thanh toán
    if (invoice.payment_status === 'paid') {
      return res.status(400).json({
        status: 400,
        message: "Không thể cập nhật hóa đơn đã thanh toán"
      });
    }

    // Cập nhật thông tin hóa đơn
    const updateData = {
      updated_at: new Date().toISOString()
    };

    if (description) updateData.description = description;
    if (amount) updateData.amount = amount;
    if (due_date) updateData.due_date = due_date;

    // Thực hiện cập nhật
    const updatedInvoice = await Invoice.findByIdAndUpdate(
      invoiceId,
      updateData,
      { new: true }
    ).populate({
      path: 'room_id',
      select: 'room_name price service limit_person',
      populate: {
        path: 'service',
        select: 'name description price'
      }
    }).populate('user_id', 'name phoneNumber');

    return res.status(200).json({
      status: 200,
      message: "Cập nhật hóa đơn thành công",
      data: updatedInvoice
    });

  } catch (error) {
    console.error('Error updating invoice:', error);
    return res.status(500).json({
      status: 500,
      message: "Đã có lỗi xảy ra khi cập nhật hóa đơn",
      error: error.message
    });
  }
});


// Xem chi tiết hóa đơn theo ID
router.get("/detail/:id", async (req, res) => {
  try {
    const invoice = await Invoice.findById(req.params.id);
    if (!invoice) {
      return res.status(404).json({ message: "Invoice not found" });
    }
    res.json(invoice);
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
});
// Tìm kiếm hóa đơn
router.get("/search", async (req, res) => {
  const { user_id, room_id, payment_status } = req.query;
  try {
    const query = {};
    if (user_id) {
      query.user_id = user_id;
    }
    if (room_id) {
      query.room_id = room_id;
    }
    if (payment_status) {
      query.payment_status = payment_status;
    }

    const invoices = await Invoice.find(query);
    res.json(invoices);
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
});



module.exports = router;