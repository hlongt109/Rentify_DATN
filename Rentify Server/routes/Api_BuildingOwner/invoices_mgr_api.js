var express = require('express');
var router = express.Router();
const mongoose = require('mongoose');
const Building = require('../../models/Building');
const Room = require("../../models/Room");
const Invoice = require("../../models/Invoice");
const handleServerError = require("../../utils/errorHandle");

//
router.get('/buildings_mgr/:landlord_id', async (req, res) => {
    try {
        const { landlord_id } = req.params;
        if (!landlord_id) {
            return res.status(400).json({ error: 'landlord_id is required.' });
        }

        const buildings = await Building.find({ landlord_id: landlord_id }).populate('manager_id');
        if (buildings) {
            res.status(200).json({ data: buildings })
        } else {
            return res.status(404).json({ message: "Buildings not found" })
        }
    } catch (error) {
        console.log("Error: ", error);
        handleServerError(res, error);
    }
});

router.get('/buildings_mgr_rooms/:building_id', async (req, res) => {
    try {
        const { building_id } = req.params;
        if (!building_id) {
            return res.status(400).json({ error: 'building_id is invalid.' });
        }

        const rooms = await Room.find({ building_id: building_id })

        if (rooms.length === 0) {
            return res.status(404).json({ message: "Buildings not found" });
        }

        res.status(200).json({ data: rooms });
        
    } catch (error) {
        console.log("Error: ", error);
        handleServerError(res, error);
    }
});

// Lấy danh sách hóa đơn cho phòng của một tòa nhà, lọc theo trạng thái thanh toán và tháng/năm
router.get("/buildings_mgr/:buildingId/invoices", async (req, res) => {
    try {
        const { buildingId } = req.params;

        const { month, year, paymentStatus } = req.query;

        const currentDate = new Date();
        const selectedMonth = month
            ? String(month).padStart(2, '0')
            : String(currentDate.getMonth() + 1).padStart(2, '0');
        const selectedYear = year || currentDate.getFullYear();

        if (!buildingId) {
            return res.status(400).json({ message: 'Building ID trống' });
        }

        const rooms = await Room.find({ building_id: buildingId }).exec();
        if (!rooms || rooms.length === 0) {
            return res.status(404).json({ message: 'Không tìm thấy phòng trong tòa nhà này.' });
        }
        // Lọc hóa đơn theo tháng/năm và trạng thái thanh toán
        const invoices = await Invoice.find({
            room_id: { $in: rooms.map(room => room._id) },
            payment_status: paymentStatus || { $exists: true },
            created_at: {
                $gte: `${selectedYear}-${selectedMonth}-01T00:00:00`,
                $lt: `${selectedYear}-${selectedMonth + 1}-01T00:00:00`
            }
        }).populate('room_id').exec();

        // Tách hóa đơn đã thanh toán và chưa thanh toán
        const paidInvoices = invoices.filter(invoice => invoice.payment_status === 'paid');
        const unpaidInvoices = invoices.filter(invoice => invoice.payment_status === 'unpaid');

        return res.status(200).json({
            paidInvoices,
            unpaidInvoices
        });

    } catch (error) {
        console.error(error);
        handleServerError(res, error);
    }
})

// lấy danh sách hóa đơn phòng lọc theo năm
router.get("/buildings_mgr/:buildingId/invoices/year", async (req, res) => {
    try {
        const { buildingId } = req.params;
        const { year, paymentStatus } = req.query;

        const currentDate = new Date();
        const selectedYear = year || currentDate.getFullYear(); // Nếu không có năm truyền vào, mặc định lấy năm hiện tại

        if (!buildingId) {
            return res.status(400).json({ message: 'Building ID trống' });
        }

        const rooms = await Room.find({ building_id: buildingId }).exec();
        if (!rooms || rooms.length === 0) {
            return res.status(404).json({ message: 'Không tìm thấy phòng trong tòa nhà này.' });
        }

        // Lọc hóa đơn theo năm và trạng thái thanh toán
        const invoices = await Invoice.find({
            room_id: { $in: rooms.map(room => room._id) },
            payment_status: paymentStatus || { $exists: true },
            created_at: {
                $gte: `${selectedYear}-01-01T00:00:00`,  // Từ ngày 1 tháng 1 của năm
                $lt: `${selectedYear + 1}-01-01T00:00:00` // Đến ngày 1 tháng 1 của năm tiếp theo
            }
        }).populate('room_id').exec();

        // Tách hóa đơn đã thanh toán và chưa thanh toán
        const paidInvoices = invoices.filter(invoice => invoice.payment_status === 'paid');
        const unpaidInvoices = invoices.filter(invoice => invoice.payment_status === 'unpaid');

        return res.status(200).json({
            paidInvoices,
            unpaidInvoices
        });

    } catch (error) {
        console.error(error);
        handleServerError(res, error);
    }
});

// lọc invoice theo type 
router.get("/buildings_mgr/:buildingId/invoices_type", async (req, res) => {
    try {
        const { buildingId } = req.params;

        const { month, year, paymentStatus, typeInvoice } = req.query;

        const currentDate = new Date();
        const selectedMonth = month
            ? String(month).padStart(2, '0') // Đảm bảo luôn là 2 chữ số
            : String(currentDate.getMonth() + 1).padStart(2, '0');
        const selectedYear = year || currentDate.getFullYear();

        if (!buildingId) {
            return res.status(400).json({ message: 'Building ID trống' });
        }
        const buildingObjectId = new mongoose.Types.ObjectId(buildingId);

        if (!typeInvoice) {
            return res.status(400).json({ message: 'Type invoice trống' });
        }

        const validTypes = ['water', 'electric', 'salary','internet', 'maintain'];
        if (!validTypes.includes(typeInvoice)) {
            return res.status(400).json({ message: `Loại hóa đơn không hợp lệ. Chỉ chấp nhận: ${validTypes.join(', ')}` });
        }

        // Lọc hóa đơn theo tháng/năm và trạng thái thanh toán
        const invoices = typeInvoice === 'salary'
            ? await Invoice.find({
                building_id: buildingObjectId,
                type_invoice: typeInvoice,
                payment_status: paymentStatus || { $exists: true },
                created_at: {
                    $gte: `${selectedYear}-${selectedMonth}-01T00:00:00`,
                    $lt: `${selectedYear}-${selectedMonth + 1}-01T00:00:00`
                }
            }).populate('user_id').exec()
            : await Invoice.find({
                building_id: buildingObjectId,
                type_invoice: typeInvoice,
                payment_status: paymentStatus || { $exists: true },
                created_at: {
                    $gte: `${selectedYear}-${selectedMonth}-01T00:00:00`,
                    $lt: `${selectedYear}-${selectedMonth + 1}-01T00:00:00`
                }
            }).exec()

        if (typeInvoice === 'water') {
            invoices.filter(invoice => invoice.type_invoice === 'water');
        }
        if (typeInvoice === 'electric') {
            invoices.filter(invoice => invoice.type_invoice === 'electric');
        }
        if(typeInvoice === 'internet'){
            invoices.filter(invoice => invoice.type_invoice === 'internet');
        }
        if (typeInvoice === 'salary') {
            invoices.filter(invoice => invoice.type_invoice === 'salary');
        }
        if (typeInvoice === 'maintain') {
            invoices.filter(invoice => invoice.type_invoice === 'maintain');
            invoices.forEach(invoice => {
                if (Array.isArray(invoice.description)) {
                    invoice.description = invoice.description
                        .map(desc => Object.values(desc).join(""))
                        .join("");
                }
            });
        }

        // Tách hóa đơn đã thanh toán và chưa thanh toán
        const paidInvoices = invoices.filter(invoice => invoice.payment_status === 'paid');
        const unpaidInvoices = invoices.filter(invoice => invoice.payment_status === 'unpaid');

        return res.status(200).json({
            paidInvoices,
            unpaidInvoices
        });

    } catch (error) {
        console.error(error);
        handleServerError(res, error);
    }
})

// lấy danh sách hóa đơn lọc theo năm
router.get("/buildings_mgr/:buildingId/invoices_type/year", async (req, res) => {
    try {
        const { buildingId } = req.params;
        const { year, paymentStatus, typeInvoice } = req.query;

        const currentDate = new Date();
        const selectedYear = year || currentDate.getFullYear();

        if (!buildingId) {
            return res.status(400).json({ message: 'Building ID trống' });
        }
        if (!typeInvoice) {
            return res.status(400).json({ message: 'Type invoice trống' });
        }

        const validTypes = ['water', 'electric', 'salary','internet', 'maintain'];
        if (!validTypes.includes(typeInvoice)) {
            return res.status(400).json({ message: `Loại hóa đơn không hợp lệ. Chỉ chấp nhận: ${validTypes.join(', ')}` });
        }

        // Lọc hóa đơn theo tháng/năm và trạng thái thanh toán
        const invoices = typeInvoice === 'salary'
            ? await Invoice.find({
                building_id: buildingId,
                type_invoice: typeInvoice,
                payment_status: paymentStatus || { $exists: true },
                created_at: {
                    $gte: `${selectedYear}-01-01T00:00:00`,  // Từ ngày 1 tháng 1 của năm
                    $lt: `${selectedYear + 1}-01-01T00:00:00` // Đến ngày 1 tháng 1 của năm tiếp theo
                }
            }).populate('user_id').exec()
            : await Invoice.find({
                building_id: buildingId,
                type_invoice: typeInvoice,
                payment_status: paymentStatus || { $exists: true },
                created_at: {
                    $gte: `${selectedYear}-01-01T00:00:00`,  // Từ ngày 1 tháng 1 của năm
                    $lt: `${selectedYear + 1}-01-01T00:00:00` // Đến ngày 1 tháng 1 của năm tiếp theo
                }
            }).exec()

        if (typeInvoice === 'water') {
            invoices.filter(invoice => invoice.type_invoice === 'water');
        }
        if (typeInvoice === 'electric') {
            invoices.filter(invoice => invoice.type_invoice === 'electric');
        }
        if(typeInvoice === 'internet'){
            invoices.filter(invoice => invoice.type_invoice === 'internet');
        }
        if (typeInvoice === 'salary') {
            invoices.filter(invoice => invoice.type_invoice === 'salary');
        }
        if (typeInvoice === 'maintain') {
            invoices.filter(invoice => invoice.type_invoice === 'maintain');
            invoices.forEach(invoice => {
                if (Array.isArray(invoice.description)) {
                    invoice.description = invoice.description
                        .map(desc => Object.values(desc).join(""))
                        .join("");
                }
            });
        }

        // Tách hóa đơn đã thanh toán và chưa thanh toán
        const paidInvoices = invoices.filter(invoice => invoice.payment_status === 'paid');
        const unpaidInvoices = invoices.filter(invoice => invoice.payment_status === 'unpaid');

        return res.status(200).json({
            paidInvoices,
            unpaidInvoices
        });

    } catch (error) {
        console.error(error);
        handleServerError(res, error);
    }
});

router.put("/invoice_mgr_status/:id", async(req, res) => {
    try {
        const { id } = req.params;
        const data = req.body;
        const invoice = await Invoice.findById(id)
        if(!invoice){
            return res.status(404).json({ message: "Invoice not found" })
        }

        invoice.user_id = invoice.user_id
        invoice.building_id =  invoice.building_id 
        invoice.room_id =  invoice.room_id 
        invoice.description = invoice.description 
        invoice.describe=  invoice.describe,
        invoice.type_invoice = invoice.type_invoice ?? 'rent'
        invoice.amount = invoice.amount
        invoice.transaction_type = invoice.transaction_type
        invoice.due_date = invoice.due_date
        invoice.payment_status = data.payment_status ?? invoice.payment_status
        invoice.created_at = invoice.created_at
    

        const result = await invoice.save();
        if (result) {
            res.status(200).json({ message: "Update Invoice success", data: result })
        } else {
            res.status(401).json({ message: "Update Invoice failed" })
        }
    } catch (error) {
        console.error(error);
        handleServerError(res, error);
    }
})

router.put('/invoice_mgr/:id', async (req, res) => {
    try {
        const { id } = req.params;
        const data = req.body;

        const invoice = await Invoice.findById(id)
        if(!invoice){
            return res.status(404).json({ message: "Invoice not found" })
        }

        invoice.user_id = data.user_id ?? invoice.user_id
        invoice.building_id = data.buildingId ?? invoice.building_id 
        invoice.room_id = data.room_id ?? invoice.room_id 
        invoice.description = data.description ?? invoice.description 
        invoice.describe=  data.describe ?? invoice.describe,
        invoice.type_invoice = data.type_invoice ?? invoice.type_invoice ?? 'rent'
        invoice.amount = data.amount ?? invoice.amount
        invoice.transaction_type = data.transaction_type ?? invoice.transaction_type
        invoice.due_date = data.due_date ?? invoice.due_date
        invoice.payment_status = data.payment_status ?? invoice.payment_status
        invoice.created_at = invoice.created_at ?? data.created_at

        const result = await invoice.save();
        if (result) {
            res.status(200).json({ message: "Update Invoice success", data: result })
        } else {
            res.status(401).json({ message: "Update Invoice failed" })
        }

    } catch (error) {
        console.log("Cập nhật hóa đơn lỗi",error)
        handleServerError(res, error);
    }
});

router.post("/invoice_mgr", async(req, res) => {
    try {
        const data = req.body;
        
        const newInvoice = new Invoice({
            user_id: data.user_id,
            building_id: data.building_id,
            room_id: data.room_id,
            description: data.description,
            describe: data.describe,
            type_invoice: data.type_invoice,
            amount: data.amount,
            transaction_type: data.transaction_type,
            due_date: data.due_date,
            payment_status: data.payment_status,
            created_at: data.created_at
        })

        const result = await newInvoice.save();
        if (result) {
            res.status(200).json({ message: "Add Invoice success", data: result })
        } else {
            res.status(401).json({ message: "Add Invoice failed" })
        }
    } catch (error) {
        console.error(error);
        handleServerError(res, error);
    }
})

router.get("/invoice_mgr/:id", async(req, res) => {
    try {
        const { id } = req.params;
        const result = await Invoice.findById(id);
        if (!result) {
            return res.status(404).json({ message: "Invoice not found" })
        }
        res.status(200).json({ data: result })
    } catch (error) {
        handleServerError(res, error);
    }
})

router.delete("/invoice_mgr/:id", async(req, res) => {
    try {
        const { id } = req.params
        const result = await Invoice.findByIdAndDelete(id);
        if (result) {
            return res.status(200).json({  message: "Delete invoice success" })
        } else {
            return res.status(404).json({ message: "Invoice not found" })
        }
    } catch (error) {
        handleServerError(res, error);
    }
})

module.exports = router;