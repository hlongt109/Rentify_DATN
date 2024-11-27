var express = require('express')
var router = express.Router()

var room = require('../../models/Room')
var building = require('../../models/Building')
const fetch = require('node-fetch');

// ====================Room api=========================
// lấy danh sách phòng
router.get('/get-list-rooms', async (req, res) => {
    try {
        const data = await room.find()
        res.status(200).send(data)
    } catch (error) {
        res.status(400).json({
            message: error.message
        })
    }
})

// lấy thông tin chi tiết phòng 
router.get('/get-detail-room/:id', async (req, res) => {
    try {
        const { id } = req.params;

        // Tìm phòng và sử dụng populate để lấy thông tin từ building_id, service và manager_id
        const data = await room.findById(id)
            .populate({
                path: 'building_id', // populate building_id
                select: 'landlord_id nameBuilding address description serviceFees manager_id', // chỉ lấy các trường cần thiết
                populate: [
                    {
                        path: 'serviceFees', // populate các serviceFees
                        select: 'name price', // chỉ lấy tên và giá
                    },
                    {
                        path: 'manager_id', // populate manager_id
                        select: 'name phoneNumber', // chỉ lấy các trường cần thiết
                    },
                    {
                        path: 'landlord_id', // populate manager_id
                        select: 'name phoneNumber', // chỉ lấy các trường cần thiết
                    }
                ]
            })
            .populate({
                path: 'service', // populate service
                select: 'name price -_id' // chỉ lấy tên và giá
            });

        // Trả về dữ liệu đã được lọc
        res.status(200).send(data);
    } catch (error) {
        res.status(400).json({
            message: error.message
        });
    }
});

// Tìm kiếm phòng theo địa chỉ, khoảng giá, loại phòng và sắp xếp theo giá
const removeVietnameseTones = (str) => {
    return str.normalize('NFD').replace(/[\u0300-\u036f]/g, '').replace(/đ/g, 'd').replace(/Đ/g, 'D');
};

router.get('/search-rooms', async (req, res) => {
    try {
        const { address, minPrice, maxPrice, roomType, sortBy } = req.query;
        let searchConditions = {};

        // Thêm điều kiện giá
        if (minPrice) {
            if (!searchConditions.price) searchConditions.price = {};
            searchConditions.price.$gte = Number(minPrice);
        }

        if (maxPrice) {
            if (!searchConditions.price) searchConditions.price = {};
            searchConditions.price.$lte = Number(maxPrice);
        }

        // Thêm điều kiện loại phòng
        if (roomType) {
            const normalizedRoomType = removeVietnameseTones(roomType.toLowerCase());
            searchConditions.room_type = { $regex: normalizedRoomType, $options: 'i' };
        }

        // Điều kiện sắp xếp
        let sortCondition = {};
        if (sortBy === 'price_asc') {
            sortCondition.price = 1;
        } else if (sortBy === 'price_desc') {
            sortCondition.price = -1;
        }

        // Truy vấn dữ liệu từ MongoDB
        const rooms = await room.find(searchConditions)
            .sort(sortCondition)
            .populate({
                path: 'building_id',
                select: 'nameBuilding address images description status',
            });

        if (rooms.length === 0) {
            return res.status(404).json({
                message: "Không tìm thấy phòng nào phù hợp với tiêu chí tìm kiếm",
            });
        }

        // Nếu có `address`, lọc lại dữ liệu sau khi lấy từ DB
        let filteredRooms = rooms;
        if (address) {
            const normalizedAddress = removeVietnameseTones(address.toLowerCase());
            filteredRooms = rooms.filter((room) => {
                const buildingAddress = removeVietnameseTones(room.building_id.address.toLowerCase());
                return buildingAddress.includes(normalizedAddress);
            });
        }

        if (filteredRooms.length === 0) {
            return res.status(404).json({
                message: "Không tìm thấy phòng nào phù hợp với tiêu chí tìm kiếm",
            });
        }

        // Trả về danh sách phòng đã lọc
        res.status(200).json(filteredRooms);
    } catch (error) {
        res.status(500).json({
            message: "Đã có lỗi xảy ra khi tìm kiếm phòng",
            error: error.message,
        });
    }
});

// Lấy phòng ngẫu nhiên từ mỗi tòa nhà với status là 0
router.get("/get-random-rooms", async (req, res) => {
    try {
        // Lấy tất cả các phòng trống và populate thông tin tòa nhà
        const allEmptyRooms = await room.find({
            status: 0 // Phòng trống
        }).populate({
            path: 'building_id',
            select: 'nameBuilding address images description status',
            match: { status: true } // Chỉ lấy các phòng từ tòa nhà đang hoạt động
        });

        // Lọc bỏ các phòng có building_id là null (do match condition)
        const validRooms = allEmptyRooms.filter(room => room.building_id !== null);

        if (validRooms.length === 0) {
            return res.status(404).json({
                status: 404,
                message: "Không tìm thấy phòng trống nào"
            });
        }

        // Trộn ngẫu nhiên mảng phòng (Fisher-Yates shuffle algorithm)
        for (let i = validRooms.length - 1; i > 0; i--) {
            const j = Math.floor(Math.random() * (i + 1));
            [validRooms[i], validRooms[j]] = [validRooms[j], validRooms[i]];
        }

        // Format dữ liệu trả về
        const randomRooms = validRooms.map(room => ({
            ...room._doc
        }));

        return res.status(200).json(randomRooms);

    } catch (error) {
        console.error("Error:", error);
        return res.status(500).json({
            status: 500,
            message: "Đã có lỗi xảy ra khi lấy danh sách phòng",
            error: error.message
        });
    }
});

// Lấy tất cả các tòa nhà mà landlord đang sở hữu và tổng số phòng đang sở hữu
router.get('/get-landlord-buildings/:landlord_id', async (req, res) => {
    try {
        const { landlord_id } = req.params;

        // Tìm tất cả các tòa nhà mà landlord đang sở hữu
        const buildings = await building.find({ landlord_id }).populate({
            path: 'landlord_id',
            select: 'name email phoneNumber address profile_picture_url'
        });

        if (buildings.length === 0) {
            return res.status(404).json({
                message: "Không tìm thấy tòa nhà nào của chủ tòa nhà này"
            });
        }

        // Đếm tổng số phòng trong tất cả các tòa nhà
        let totalRooms = 0;
        for (const bld of buildings) {
            const roomCount = await room.countDocuments({ building_id: bld._id });
            totalRooms += roomCount;
        }

        // Trả về thông tin các tòa nhà và tổng số phòng
        res.status(200).json({
            landlord: buildings[0].landlord_id,
            totalRooms
        });
    } catch (error) {
        res.status(500).json({
            message: "Đã có lỗi xảy ra khi lấy thông tin tòa nhà",
            error: error.message
        });
    }
});

// Lấy các phòng trống của một tòa nhà theo building_id
router.get('/get-empty-rooms/:building_id', async (req, res) => {
    try {
        const { building_id } = req.params;

        // Tìm các phòng trống của tòa nhà
        const emptyRooms = await room.find({
            building_id,
            status: 0 // Phòng trống
        }).select('room_name _id'); // Chỉ lấy tên phòng và id

        if (emptyRooms.length === 0) {
            return res.status(404).json({
                message: "Không tìm thấy phòng trống nào trong tòa nhà này"
            });
        }

        // Trả về danh sách các phòng trống
        res.status(200).json(emptyRooms);
    } catch (error) {
        res.status(500).json({
            message: "Đã có lỗi xảy ra khi lấy danh sách phòng trống",
            error: error.message
        });
    }
});
router.get('/get-empty-rooms/:building_id', async (req, res) => {
    try {
        const { building_id } = req.params;

        // Tìm các phòng trống của tòa nhà
        const emptyRooms = await room.find({
            building_id,
            status: 0 // Phòng trống
        });

        if (emptyRooms.length === 0) {
            return res.status(404).json({
                message: "Không tìm thấy phòng trống nào trong tòa nhà này"
            });
        }

        // Trả về danh sách các phòng trống
        res.status(200).json(emptyRooms);
    } catch (error) {
        res.status(500).json({
            message: "Đã có lỗi xảy ra khi lấy danh sách phòng trống",
            error: error.message
        });
    }
});

// Route để lấy tất cả các quận của Hà Nội
const districtsData = {
    "Hà Nội": [
        "Ba Đình",
        "Hoàn Kiếm",
        "Tây Hồ",
        "Long Biên",
        "Cầu Giấy",
        "Đống Đa",
        "Hai Bà Trưng",
        "Hoàng Mai",
        "Nam Từ Liêm",
        "Bắc Từ Liêm",
        "Thanh Xuân"
    ],
    "Hồ Chí Minh": [
        "Quận 1",
        "Quận 2",
        "Quận 3",
        "Quận 4",
        "Quận 5",
        "Quận 6",
        "Quận 7",
        "Quận 8",
        "Quận 9",
        "Quận Bình Thạnh",
        "Quận Tân Bình"
    ],
    "Đà Nẵng": [
        "Quận Hải Châu",
        "Quận Thanh Khê",
        "Quận Sơn Trà",
        "Quận Ngũ Hành Sơn",
        "Quận Liên Chiểu"
    ]
};

router.get('/get-districts/:city', (req, res) => {
    const city = req.params.city;

    // Kiểm tra nếu thành phố không hợp lệ
    if (!districtsData[city]) {
        return res.status(404).json({
            message: `Không tìm thấy thông tin về quận của thành phố: ${city}`
        });
    }

    // Trả về danh sách các quận của thành phố
    res.status(200).json(districtsData[city]);
});


module.exports = router;