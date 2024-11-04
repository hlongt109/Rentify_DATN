const express = require('express');
const router = express.Router();
const Request = require('../../models/Request');

// 1. Lấy danh sách tất cả các yêu cầu
router.get('/list', async (req, res) => {
    try {
        const requests = await Request.find();
        res.json(requests);
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
});
// 2
router.post('/add', async (req, res) => {
    const request = new Request({
        user_id: req.body.user_id, // Đảm bảo rằng user_id là hợp lệ
        room_id: req.body.room_id, // Đảm bảo rằng room_id là hợp lệ
        request_type: req.body.request_type, // Thêm loại yêu cầu nếu cần
        description: req.body.description,
        status: req.body.status || 0, // Mặc định là 0
        created_at: new Date().toISOString(),
        updated_at: new Date().toISOString()
    });

    try {
        const savedRequest = await request.save();
        res.status(201).json(savedRequest);
    } catch (error) {
        res.status(400).json({ message: error.message });
    }
});

// 3. Xem chi tiết yêu cầu theo ID
router.get('/detail/:id', async (req, res) => {
    try {
        const request = await Request.findById(req.params.id);
        if (!request) {
            return res.status(404).json({ message: "Request not found" });
        }
        res.json(request);
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
});

// 4. Cập nhật yêu cầu theo ID
router.put('/update/:id', async (req, res) => {
    try {
        const updatedRequest = await Request.findByIdAndUpdate(
            req.params.id,
            {
                user_id: req.body.user_id,
                room_id: req.body.room_id,
                request_type: req.body.request_type,
                description: req.body.description,
                status: req.body.status,
                updated_at: new Date().toISOString()
            },
            { new: true, runValidators: true }
        );

        if (!updatedRequest) {
            return res.status(404).json({ message: "Request not found" });
        }
        res.json(updatedRequest);
    } catch (error) {
        res.status(400).json({ message: error.message });
    }
});

// 5. Xóa yêu cầu theo ID
router.delete('/delete/:id', async (req, res) => {
    try {
        const deletedRequest = await Request.findByIdAndDelete(req.params.id);
        if (!deletedRequest) {
            return res.status(404).json({ message: "Request not found" });
        }
        res.json({ message: "Request deleted successfully" });
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
});

module.exports = router;
