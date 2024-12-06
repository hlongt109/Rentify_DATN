var express = require('express');
var router = express.Router();
const Contract = require('../../models/Contract');
const Room = require('../../models/Room')
const upload = require('../../config/common/uploadImageContract');
const fs = require('fs');
const path = require('path');


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

// #7. Lấy danh sách hợp đồng liên quan đến một toà nhà cụ thể GET: http://localhost:3000/api/contracts/building/:buildingId
router.get('/contracts/building/:buildingId', async (req, res) => {
    const { buildingId } = req.params;

    try {
        const contracts = await Contract.find({ status: 0 }).populate({
            path: 'room_id',
            match: { building_id: buildingId }
        });

        const filteredContracts = contracts.filter(contract => contract.room_id !== null);

        res.status(200).json(filteredContracts);
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'Failed to retrieve contracts for the building.' });
    }
});


// #8. Lấy danh sách phòng còn trống chưa được thuê của một toà nhà cụ thể GET: http://localhost:3000/api/contracts/available-rooms/:buildingId
router.get('/contracts/available-rooms/:buildingId', async (req, res) => {
    const { buildingId } = req.params;

    try {
        const availableRooms = await Room.find({ building_id: buildingId, status: 0 }); // status: 0 là phòng chưa cho thuê
        res.status(200).json(availableRooms);
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'Failed to retrieve available rooms for the building.' });
    }
});

// #9. Tạo hợp đồng với ảnh POST: http://localhost:3000/api/contracts/with-images
router.post('/contracts/with-images', upload.array('photos_contract', 10), async (req, res) => {
    const { manage_id, building_id, user_id, room_id, content, start_date, end_date, status } = req.body;
    const photos_contract = req.files.map(file => file.path);

    try {
        const newContract = new Contract({
            manage_id,
            building_id,
            room_id,
            user_id: JSON.parse(user_id),
            photos_contract,
            content,
            start_date,
            end_date,
            status,
            created_at: new Date().toISOString()
        });

        await newContract.save();

        // Cập nhật trạng thái của phòng
        await Room.findByIdAndUpdate(room_id, { status: 1 });

        res.status(201).json({ message: 'Contract with images created successfully!', contract: newContract });
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'Failed to create contract with images.' });
    }
});

// #10. Cập nhật ảnh và nội dung hợp đồng PUT: http://localhost:3000/api/contracts/update-images/:id
router.put('/contracts/update-images/:id', upload.array('photos_contract', 10), async (req, res) => {
    const { id } = req.params;
    const { user_id, content } = req.body;
    const photos_contract = req.files.map(file => file.path);

    try {
        const contract = await Contract.findById(id);

        if (!contract) {
            return res.status(404).json({ error: 'Contract not found.' });
        }

        // Kiểm tra nếu có ảnh mới
        if (req.files.length > 0) {
            // Delete old photos
            contract.photos_contract.forEach(photo => {
                fs.unlink(path.join(photo), (err) => {
                    if (err) {
                        console.error('Failed to delete old photo:', err);
                    }
                });
            });
            contract.photos_contract = photos_contract; // Gán ảnh mới
        }

        contract.user_id = JSON.parse(user_id),
        // contract.photos_contract = photos_contract
        contract.content = content;
        contract.created_at = new Date().toISOString();

        await contract.save();

        res.status(200).json({ message: 'Contract updated successfully!', contract });
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'Failed to update contract.' });
    }
});

// #11. Lấy chi tiết hợp đồng bao gồm thông tin phòng GET: http://localhost:3000/api/contracts/details/:id
router.get('/contracts/details/:id', async (req, res) => {
    const { id } = req.params;

    try {
        const contract = await Contract.findById(id).populate('room_id');

        if (!contract) {
            return res.status(404).json({ error: 'Contract not found.' });
        }

        res.status(200).json(contract);
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'Failed to retrieve contract details.' });
    }
});

// #12. Chấm dứt hợp đồng DELETE: http://localhost:3000/api/contracts/:id
router.delete('/endcontracts/:id', async (req, res) => {
    const { id } = req.params;

    try {
        const contract = await Contract.findById(id);

        if (!contract) {
            return res.status(404).json({ error: 'Contract not found.' });
        }

        // Update contract status to 1 (terminated)
        contract.status = 1;
        await contract.save();

        res.status(200).json({ message: 'Contract status updated to terminated successfully!' });
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'Failed to update contract status.' });
    }
});

// #13. Lấy số lượng hợp đồng cần xử lý của từng toà GET: http://localhost:3000/api/contracts/pending/:buildingId
router.get('/contracts/pending/:buildingId', async (req, res) => {
    const { buildingId } = req.params;
    try {
        const today = new Date().toISOString().split('T')[0]; // Lấy ngày hiện tại

        // Tìm các hợp đồng có ngày kết thúc bằng hoặc trước ngày hiện tại và chưa được chấm dứt
        const pendingContracts = await Contract.find({
            end_date: { $lte: today },
            status: 0 // 0 là hợp đồng còn hiệu lực
        }).populate({
            path: 'room_id',
            match: { building_id: buildingId }
        });

        const filteredContracts = pendingContracts.filter(contract => contract.room_id !== null);

        res.status(200).json({ count: filteredContracts.length });
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'Failed to retrieve pending contracts for the building.' });
    }
});

module.exports = router;