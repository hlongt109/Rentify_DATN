const express = require('express');
const Support = require('../../models/Support');
const uploadFile = require('../../config/common/uploadImagReport');
const Contract = require('../../models/Contract')
const router = express.Router();

// thực hiện đăng ký báo cáo hỗ trợ
router.post('/create-report', uploadFile.array('image', 5), async (req, res) => {
    try {
        // if (!req.files || req.files.length === 0) {
        //     return res.status(400).json({ message: 'No files uploaded' });
        // }
        console.log('Body:', req.body);
        console.log('Files:', req.files);

        const { user_id, room_id, building_id, title_support, content_support, status } = req.body;
        let images = [];
        if (req.files && req.files.length > 0) {
            images = req.files.map(file => file.path);  // Lấy đường dẫn ảnh
        }

        const newSupport = new Support({
            user_id,
            room_id,
            building_id,
            title_support,
            content_support,
            image: images,
            status,
            created_at: new Date().toISOString(),
            updated_at: new Date().toISOString()
        });

        await newSupport.save();
        res.status(200).json({ message: 'Support report created successfully', support: newSupport });
    } catch (error) {
        res.status(500).json({ message: 'Error creating support report', error: error.message });
    }
});

//lay danh sach bao cao
router.get("/getSupportsByUserId/:userId", async (req, res) => {
    try {
        const userId = req.params.userId
        const supports = await Support.find({ user_id: userId })
            .populate('user_id', 'name email') // Lấy thêm thông tin user
            .populate('room_id', 'room_number') // Lấy thêm thông tin phòng
            .populate('building_id', 'building_name') // Lấy thêm thông tin tòa nhà
            .sort({ created_at: -1 }); // Sắp xếp theo thời gian tạo mới nhất
        return res.status(200).json({
            status: 200,
            message: "Lấy dữ liệu thành công",
            data: supports
        });
    } catch (error) {
        return res.status(500).json({
            success: false,
            message: 'Lỗi server: ' + error.message
        });
    }
})

router.get('/get-room-by-contract/:userId', async (req, res) => {
    try {
        const userId = req.params.userId;

        // Tìm hợp đồng của user và populate thông tin phòng
        const contract = await Contract.findOne({
            user_id: userId,
            status: 0 // Chỉ lấy hợp đồng đang có hiệu lực
        })
            .populate({
                path: 'room_id',
                select: 'room_name floor_area price status building_id',
                populate: {
                    path: 'building_id',
                    select: 'building_name address'
                }
            })
            .select('room_id start_date end_date status');

        // Kiểm tra nếu không tìm thấy hợp đồng
        if (!contract) {
            return res.status(404).json({
                success: false,
                message: 'Không tìm thấy hợp đồng đang có hiệu lực của người dùng này'
            });
        }

        return res.status(200).json({
            status: 200,
            message: 'Lấy thông tin phòng thành công',
            data: [{
                contract_id: contract._id,
                start_date: contract.start_date,
                end_date: contract.end_date,
                room: {
                    room_id: contract.room_id._id,
                    room_number: contract.room_id.room_name,
                    floor_area: contract.room_id.floor_area,
                    price: contract.room_id.price,
                    status: contract.room_id.status,
                    building: {
                        building_id: contract.room_id.building_id._id,
                        building_name: contract.room_id.building_id.building_name,
                        address: contract.room_id.building_id.address
                    }
                }
            }]
        });

    } catch (error) {
        return res.status(500).json({
            success: false,
            message: 'Lỗi server: ' + error.message
        });
    }
});



module.exports = router