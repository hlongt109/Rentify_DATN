const express = require('express');
const router = express.Router();
const Contract = require("../../models/Contract");
var Building = require('../../models/Building')
const moment = require('moment');
const mongoose = require("mongoose");
const upload = require('../../config/common/upload'); // Đường dẫn tới file upload.js
const Room = require('../../models/Room')
const User = require('../../models/User')
router.get("/contracts-by-building", async (req, res) => {
    const { manage_id } = req.query;

    if (!manage_id) {
        return res.status(400).json({ message: "manage_id is required" });
    }

    try {
        // Tìm các tòa nhà mà manage_id quản lý
        const managedBuildings = await Building.find({ manager_id: manage_id }).select("_id");

        if (!managedBuildings || managedBuildings.length === 0) {
            return res.status(404).json({ message: "No buildings found for this manager" });
        }

        // Lấy danh sách building_id từ các tòa nhà được quản lý
        const buildingIds = managedBuildings.map((building) => building._id);

        // Lấy danh sách hợp đồng theo building_id
        const contracts = await Contract.find({ building_id: { $in: buildingIds } })
            .populate("building_id", "nameBuilding") // Thêm thông tin tòa nhà
            .populate("room_id", "room_name") // Thêm thông tin phòng
            .populate("user_id", "name email") // Thêm thông tin người thuê
            .sort({ created_at: -1 }); // Sắp xếp theo ngày tạo giảm dần

        // Thêm thông tin khoảng thời gian (số tháng)
        const contractsWithDuration = contracts.map(contract => {
            const startDate = moment(contract.start_date, "YYYY-MM-DD");
            const endDate = moment(contract.end_date, "YYYY-MM-DD");
            const durationMonths = endDate.diff(startDate, "months"); // Tính số tháng làm tròn

            return {
                ...contract.toObject(),
                duration: durationMonths + " tháng" // Không có phần thập phân
            };
        });

        res.json(contractsWithDuration);
    } catch (error) {
        console.error("Error fetching contracts:", error);
        res.status(500).json({ message: "Error fetching contracts", error: error.message });
    }
});

// API lấy chi tiết hợp đồng theo contract_id
router.get("/contract-detail/:id", async (req, res) => {
    const { id } = req.params;

    try {
        // Tìm hợp đồng theo ID
        const contract = await Contract.findById(id)
            .populate("building_id", "nameBuilding")  // Thêm thông tin tòa nhà
            .populate("room_id", "room_name price")        // Thêm thông tin phòng
            .populate("user_id", "name email");      // Thêm thông tin người thuê

        if (!contract) {
            return res.status(404).json({ message: "Contract not found" });
        }
        // Tính duration (khoảng thời gian hợp đồng)
        const startDate = moment(contract.start_date); // Dùng moment để xử lý ngày
        const endDate = moment(contract.end_date);
        const duration = endDate.diff(startDate, 'months'); // Tính số tháng giữa start_date và end_date

        // Tính kỳ thanh toán (ví dụ: "01 - 05 hàng tháng")
        const startDay = moment(contract.start_date).format('DD'); // Ngày bắt đầu (dd)
        const paymentCycle = `${startDay}`;

        // Trả về thông tin hợp đồng với duration và payment cycle
        const contractDetails = {
            ...contract.toObject(), // Chuyển contract sang object để dễ dàng thao tác
            duration,              // Thêm thông tin duration vào hợp đồng
            paymentCycle          // Thêm thông tin kỳ thanh toán vào hợp đồng
        };

        res.json(contractDetails);
    } catch (error) {
        console.error("Error fetching contract details:", error);
        res.status(500).json({ message: "Error fetching contract details", error: error.message });
    }
});



// API: Lấy danh sách tòa theo manage_id
router.get("/buildings/:manager_id", async (req, res) => {
    try {
        const { manager_id } = req.params;

        // Kiểm tra nếu manage_id không hợp lệ
        if (!mongoose.Types.ObjectId.isValid(manager_id)) {
            return res.status(400).json({
                status: 400,
                message: "manage_id không hợp lệ.",
            });
        }

        // Tìm các tòa nhà theo manage_id
        const buildings = await Building.find({ manager_id })
        // .select("_id name location") // Chỉ chọn các trường cần thiết
        // .populate("manage_id", "name email") // Populate thông tin quản lý
        // .lean(); // Chuyển đổi sang Object JS thuần

        // Nếu không có tòa nhà nào, trả về thông báo lỗi
        if (!buildings || buildings.length === 0) {
            return res.status(404).json({
                status: 404,
                message: "Không tìm thấy tòa nhà nào cho manage_id này.",
            });
        }

        // Trả về danh sách tòa nhà
        res.status(200).json({
            status: 200,
            data: buildings,
        });
    } catch (error) {
        console.error("Lỗi khi lấy danh sách tòa nhà:", error.message);
        res.status(500).json({
            status: 500,
            message: "Lỗi khi lấy danh sách tòa nhà.",
            error: error.message,
        });
    }
});


router.get("/rooms/:building_id", async (req, res) => {
    try {
        const { building_id } = req.params;

        // Tìm các phòng theo building_id và status = 0
        const rooms = await Room.find({ building_id, status: 0 });

        res.status(200).json({
            status: 200,
            data: rooms,
        });
    } catch (error) {
        console.error("Lỗi khi lấy danh sách phòng:", error.message);
        res.status(500).json({
            status: 500,
            message: "Lỗi khi lấy danh sách phòng.",
            error: error.message,
        });
    }
});

// API thêm hợp đồng
router.post('/add', upload.fields([{ name: 'photos_contract' }]), async (req, res) => {
    try {
        // Lấy dữ liệu hợp đồng từ body request
        const {
            manage_id,
            building_id,
            room_id,
            user_id,
            photos_contract,
            content,
            start_date,
            end_date,
            status,
            created_at
        } = req.body;

        // Kiểm tra nếu tất cả các trường dữ liệu bắt buộc đã được cung cấp
        if (!manage_id || !building_id || !room_id || !user_id || !start_date || !end_date) {
            return res.status(400).json({ message: 'Thiếu thông tin bắt buộc để tạo hợp đồng' });
        }
        const userIds = user_id.split(',').map(id => new mongoose.Types.ObjectId(id.trim()));

        // Tạo hợp đồng mới
        const newContract = new Contract({
            manage_id: new mongoose.Types.ObjectId(req.body.manage_id),
            building_id: building_id ? new mongoose.Types.ObjectId(building_id) : null, // Kiểm tra nếu có building_id
            room_id: room_id ? new mongoose.Types.ObjectId(room_id) : null, // Kiểm tra nếu có room_id
            user_id: userIds,
            photos_contract: req.files['photos_contract'] ? req.files['photos_contract'].map(file => file.path.replace('public/', '')) : [],
            content,
            start_date,
            end_date,
            status,
            created_at: new Date().toISOString(),
        });

        // Lưu hợp đồng vào cơ sở dữ liệu
        await newContract.save();
        const updatedRoom = await Room.findByIdAndUpdate(
            room_id, // ID của phòng
            { status: 1 }, // Cập nhật trạng thái phòng thành 1
            { new: true } // Trả về phòng sau khi được cập nhật
        );

        if (!updatedRoom) {
            return res.status(400).json({ message: 'Không tìm thấy phòng để cập nhật trạng thái.' });
        }
        // Trả về phản hồi thành công
        res.status(201).json({
            message: 'Hợp đồng đã được tạo thành công',
            contract: newContract,
        });

    } catch (error) {
        console.error("Lỗi khi tạo hợp đồng:", error.message);
        res.status(500).json({
            message: 'Lỗi khi tạo hợp đồng',
            error: error.message,
        });
    }
});


router.put("/update/:id", upload.fields([{ name: "photos_contract" }]), async (req, res) => {
    try {
        const { id } = req.params; // Lấy contract_id từ params
        const { user_id, content } = req.body; // Lấy dữ liệu từ body request

        // Kiểm tra xem hợp đồng tồn tại không
        const contract = await Contract.findById(id);
        if (!contract) {
            return res.status(404).json({ message: "Contract not found" });
        }

        // Cập nhật user_id nếu có
        if (user_id) {
            const userIds = user_id.split(",").map((id) => new mongoose.Types.ObjectId(id.trim()));
            contract.user_id = userIds;
        }

        // Cập nhật photos_contract nếu có file upload
        if (req.files["photos_contract"]) {
            const photoPaths = req.files["photos_contract"].map((file) => file.path.replace("public/", ""));
            contract.photos_contract = photoPaths;
        }

        // Cập nhật content nếu có
        if (content) {
            contract.content = content;
        }

        // Lưu thay đổi vào database
        await contract.save();

        // Phản hồi thành công
        res.status(200).json({
            message: "Contract updated successfully",
            contract,
        });
    } catch (error) {
        console.error("Error updating contract:", error.message);
        res.status(500).json({
            message: "Error updating contract",
            error: error.message,
        });
    }
});
router.get('/search', async (req, res) => {
    try {
        const { keyword, manageId } = req.query; // Lấy 'keyword' và 'manageId' từ query string

        // Kiểm tra nếu không có manageId, trả về lỗi
        if (!manageId) {
            return res.status(400).json({ message: 'manageId là bắt buộc' });
        }

        let filters = { manage_id: manageId }; // Bắt buộc có manageId trong filters

        // Nếu không có từ khóa, trả về tất cả các hợp đồng theo manageId
        if (!keyword) {
            const contracts = await Contract.find(filters)
                .populate("user_id", "name")
                .populate("building_id", "nameBuilding")
                .populate("room_id", "room_name")
                .lean();

            return res.status(200).json(contracts);
        }

        // Tìm kiếm theo tên người dùng
        const userIds = [];
        const users = await User.find({ name: new RegExp(keyword, "i") }); // Tìm kiếm userName không phân biệt hoa thường
        if (users.length > 0) {
            userIds.push(...users.map(user => user._id)); // Lưu user_id của các user tìm được
        }

        // Tìm kiếm theo phòng (room_name)
        const roomIds = [];
        const rooms = await Room.find({ room_name: new RegExp(keyword, "i") }); // Tìm kiếm tên phòng
        if (rooms.length > 0) {
            roomIds.push(...rooms.map(room => room._id)); // Lưu room_id
        }

        // Xử lý tìm kiếm theo tòa và phòng (tòa_phòng)
        const buildingIds = [];
        const match = keyword.match(/^([a-zA-Z0-9]+)_([a-zA-Z0-9]+)$/); // Tách theo format tòa_phòng
  
        if (match) {
            const buildingKeyword = match[1];  // Phần trước dấu gạch dưới (tòa nhà)
            const roomKeyword = match[2];      // Phần sau dấu gạch dưới (phòng)
        
            // Tìm kiếm tòa nhà khớp chính xác với buildingKeyword
            const buildings = await Building.find({ nameBuilding: new RegExp(`^${buildingKeyword}$`, "i") });
            
            if (buildings.length === 0) {
                return res.status(404).json({ message: `Tòa nhà ${buildingKeyword} không tồn tại` });
            }
            
            buildingIds.push(...buildings.map(building => building._id)); // Lưu building_id
        
            // Nếu có phòng (roomKeyword), tìm theo phòng
            if (roomKeyword) {
                const rooms = await Room.find({ room_name: new RegExp(roomKeyword, "i") });
                if (rooms.length > 0) {
                    roomIds.push(...rooms.map(room => room._id)); // Lưu roo
                }
            }
        } else {
            // Nếu không có dấu gạch dưới, tìm theo cả tòa và phòng
            const buildings = await Building.find({ nameBuilding: new RegExp(keyword, "i") });
            if (buildings.length > 0) {
                buildingIds.push(...buildings.map(building => building._id)); // Lưu building_id
            }
            
            const rooms = await Room.find({ room_name: new RegExp(keyword, "i") });
            if (rooms.length > 0) {
                roomIds.push(...rooms.map(room => room._id)); // Lưu room_id
            }
        }

     
        if (buildingIds.length > 0) {
            filters['building_id'] = { $in: buildingIds }; // Tìm kiếm theo building_id
        }
        if (roomIds.length > 0) {
            filters['room_id'] = { $in: roomIds }; // Tìm kiếm theo room_id
        }
        if (userIds.length > 0) {
            filters['user_id'] = { $in: userIds }; // Tìm kiếm theo user_id
        }

        // Truy vấn danh sách hợp đồng
        const contracts = await Contract.find(filters)
            .populate("user_id", "name")
            .populate("building_id", "nameBuilding")
            .populate("room_id", "room_name")
            .lean();

        // Nếu không có hợp đồng, trả về thông báo
        if (contracts.length === 0) {
            return res.status(200).json({ message: 'Không tìm thấy hợp đồng nào' });
        }

        // Tính duration cho các hợp đồng
        const contractsWithDuration = contracts.map(contract => {
            const startDate = moment(contract.start_date);
            const endDate = moment(contract.end_date);
            const duration = endDate.diff(startDate, 'months');

            return {
                ...contract,
                duration: `${duration} tháng` || "0 tháng",
            };
        });

        // Trả về kết quả hợp đồng đã tính uration
        res.status(200).json(contractsWithDuration);
    } catch (error) {
        console.error("Error fetching contracts:", error);
        res.status(500).json({ message: "Internal server error" });
    }
});


router.get("/user/:id", async (req, res) => {
    try {
        const userId = req.params.id;

        // Tìm người dùng theo ID và role là "user"
        const user = await User.findOne({ _id: userId, role: "user" }).select("_id name role profile_picture_url");

        if (!user) {
            return res.status(404).json({ message: "User not found or not a user" });
        }

        // Trả về thông tin người dùng
        res.status(200).json({
            _id: user._id,
            name: user.name,
role: user.role,
            profile_picture_url: user.profile_picture_url,
        });
    } catch (error) {
        console.error("Error fetching user:", error);
        res.status(500).json({ message: "Internal Server Error" });
    }
});

module.exports = router;
