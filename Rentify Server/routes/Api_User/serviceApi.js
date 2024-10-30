var express = require('express')
var router = express.Router()

var service = require('../../models/Service')
var request = require('../../models/Request')

// ====================Service api=========================
// lấy danh sách dịch vụ
router.get('/get-list-services', async (req, res) => {
    try {
        const data = await service.find()
        res.status(200).send(data)
    } catch (error) {
        res.status(400).json({
            message: error.message
        })
    }
})

// lấy thông tin chi tiết của dịch vụ
router.get('/get-detail-service/:id', async (req, res) => {
    try {
        const { id } = req.params
        const data = await service.findById(id)
        res.status(200).send(data)
    } catch (error) {
        res.status(400).json({
            message: error.message
        })
    }
})

// đặt dịch vụ
router.post('/post-request-service', async (req, res) => {
     try {
        const { user_id, room_id, request_type, description, status } = req.body

        if(!user_id?.trim() || !room_id?.trim() || !request_type?.trim() || !description?.trim()){
            return res.status(400).json({
                message: 'Missing required fields (user_id, room_id).'
            })
        }

        const newRequest = await request.create({
            user_id: user_id,
            room_id: room_id,
            request_type: request_type || "yêu cầu dịch vụ",
            description: description || "không có mô tả",
            status: status ?? 0,
            created_at: new Date().toISOString(),
            updated_at: new Date().toISOString()
        })
        res.status(200).json({
            message: newRequest
        })
     } catch (error) {
        res.status(400).json({
            message: error.message
        })
     }
})

// huỷ dịch vụ đã đặt
router.put('/put-request/:id', async (req, res) => {
    try {
        const { id } = req.params
        const data = await request.findByIdAndUpdate(id, {status: 3, updated_at: new Date().toISOString()})
        res.status(200).json({
            message: data
        })
    } catch (error) {
        res.status(400).json({
            message: error.message
        })
    }
})


module.exports = router