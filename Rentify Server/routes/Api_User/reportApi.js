const express = require('express');
const Support = require('../../models/Support'); 
const uploadFile = require('../../config/common/uploadImagReport'); 

const router = express.Router();

// thực hiện đăng ký báo cáo hỗ trợ
router.post('/create-report', uploadFile.array('image', 5), async (req, res) => {
    try {
        if (!req.files || req.files.length === 0) {
            return res.status(400).json({ message: 'No files uploaded' });
        }

        const { user_id, room_id, title_support, content_support, status } = req.body;
        const images = req.files.map(file => file.path);

        const newSupport = new Support({
            user_id,
            room_id,
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


module.exports = router