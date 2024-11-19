var express = require('express');
var router = express.Router();
const Building = require('../../models/Building');
const Room = require("../../models/Room");
const Invoice = require("../../models/Invoice");
const handleServerError = require("../../utils/errorHandle");

//
router.get('/buildings_mgr/:landlord_id', async(req, res) => {
    try {
        const { landlord_id } = req.params;
        if (!landlord_id) {
            return res.status(400).json({ error: 'landlord_id is required.' });
        }

        const buildings = await Building.find({ landlord_id: landlord_id })
        if(buildings){
            res.status(200).json({data: buildings})
        }else{
            return res.status(404).json({ message: "Buildings not found" })
        }
    } catch (error) {
        console.log("Error: ", error);
        handleServerError(res, error);
    }
});

// Route để lấy danh sách hóa đơn cho phòng của một tòa nhà, lọc theo trạng thái thanh toán và tháng/năm
router.get("/buildings_mgr/:buildingId/invoices", async(req, res) => {
    try {
        const { buildingId } = req.params;

        const { month, year, paymentStatus } = req.query;
    
        const currentDate = new Date();
        const selectedMonth = month || currentDate.getMonth() + 1; 
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

module.exports = router;