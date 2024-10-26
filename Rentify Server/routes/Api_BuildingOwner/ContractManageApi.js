var express = require('express');
var router = express.Router();
const Contract = require('../../models/Contract');

// #1. Tạo hợp đồng POST: http://localhost:3000/api/contracts
router.post('/contracts', async (req, res) => {
    const { user_id, room_id, content, start_date, end_date, status } = req.body;

    try {
        const newContract = new Contract({
            user_id,
            room_id,
            content,
            start_date,
            end_date,
            status,
            created_at: new Date().toISOString()
        });

        await newContract.save();
        res.status(201).json({ message: 'Contract created successfully!', contract: newContract });
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'Failed to create contract.' });
    }
});

// #2. Lấy danh sách tất cả hợp đồng GET: http://localhost:3000/api/contracts
router.get('/contracts', async (req, res) => {
    try {
        const contracts = await Contract.find({});
        res.status(200).json(contracts);
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'Failed to retrieve contracts.' });
    }
});

// #3. Xem chi tiết hợp đồng GET: http://localhost:3000/api/contracts/{id}
router.get('/contracts/:id', async (req, res) => {
    const { id } = req.params;

    try {
        const contract = await Contract.findById(id);
        
        if (!contract) {
            return res.status(404).json({ error: 'Contract not found.' });
        }

        res.status(200).json(contract);
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'Failed to retrieve contract.' });
    }
});

// #4. Chỉnh sửa hợp đồng PUT: http://localhost:3000/api/contracts/{id}
router.put('/contracts/:id', async (req, res) => {
    const { id } = req.params;
    const { room_id, content, start_date, end_date, status } = req.body;

    try {
        const updatedContract = await Contract.findByIdAndUpdate(
            id,
            { room_id, content, start_date, end_date, status, created_at: new Date().toISOString() },
            { new: true } // trả về hợp đồng đã được cập nhật
        );

        if (!updatedContract) {
            return res.status(404).json({ error: 'Contract not found.' });
        }

        res.status(200).json({ message: 'Contract updated successfully!', contract: updatedContract });
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'Failed to update contract.' });
    }
});

// #5. Gia hạn hợp đồng PUT: http://localhost:3000/api/contracts/extend/{id}
router.put('/contracts/extend/:id', async (req, res) => {
    const { id } = req.params;
    const { new_end_date } = req.body; // Lấy ngày kết thúc mới từ body

    try {
        const updatedContract = await Contract.findByIdAndUpdate(
            id,
            { end_date: new_end_date, status: 'extended', created_at: new Date().toISOString() },
            { new: true }
        );

        if (!updatedContract) {
            return res.status(404).json({ error: 'Contract not found.' });
        }

        res.status(200).json({ message: 'Contract extended successfully!', contract: updatedContract });
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'Failed to extend contract.' });
    }
});

// #6. Chấm dứt hợp đồng PUT: http://localhost:3000/api/contracts/terminate/{id}
router.put('/contracts/terminate/:id', async (req, res) => {
    const { id } = req.params;

    try {
        const terminatedContract = await Contract.findByIdAndUpdate(
            id,
            { status: 'terminated', created_at: new Date().toISOString() },
            { new: true }
        );

        if (!terminatedContract) {
            return res.status(404).json({ error: 'Contract not found.' });
        }

        res.status(200).json({ message: 'Contract terminated successfully!', contract: terminatedContract });
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'Failed to terminate contract.' });
    }
});



module.exports = router;


// dât kiểm thử 

// {
//     "user_id": "671b0cb4f9b7a4cfce237282",
//     "room_id": "ROOM001",
//     "content": "Hợp đồng cho thuê phòng.",
//     "start_date": "2024-10-25",
//     "end_date": "2025-10-25",
//     "status": "active"
// }


// data kiểm thử gia haonj hợp đồng thay "end_date": "2025-10-25"  =   "new_end_date": "2026-10-25"