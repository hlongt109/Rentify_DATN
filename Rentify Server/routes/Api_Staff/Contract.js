const express = require('express');
const router = express.Router();
const Contract = require("../../models/Contract");

// Lấy danh sách tất cả hợp đồng 
router.get('/list', async (req, res) => {
    try {
        const contracts = await Contract.find(); // Lấy tất cả hợp đồng từ DB
        res.json(contracts);
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
});

// Tìm kiếm hợp đồng 
router.get('/search', async (req, res) => {
    const { user_id, room_id } = req.query;

    try {
        const query = {};
        if (user_id) query.user_id = user_id;
        if (room_id) query.room_id = room_id;

        const contracts = await Contract.find(query);
        res.json(contracts);
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
});

// Xem chi tiết hợp đồng theo ID http://localhost:3000/api/staff/contracts/detail/
router.get('/detail/:id', async (req, res) => {
    try {
        const contract = await Contract.findById(req.params.id);
        if (!contract) {
            return res.status(404).json({ message: "Contract not found" });
        }
        res.json(contract);
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
});

module.exports = router;
