package com.rentify.user.app.model.Model

data class Room(
    val buildingId: String, // Mã ID của tòa nhà (tương tự ObjectId trong Mongoose)
    val roomType: String, // Loại phòng, ví dụ "Single", "Double", v.v.
    val roomName: String?, // Tên phòng, ví dụ: "P202"
    val description: String, // Mô tả phòng
    val price: Double, // Giá phòng (ví dụ: 5000)
    val size: String, // Kích thước phòng (ví dụ: "40m2")
    val videoRoom: List<String>?, // Danh sách các liên kết hoặc mã video phòng
    val photosRoom: List<String>?, // Danh sách các liên kết ảnh phòng
    val service: List<String>?, // Danh sách ID các dịch vụ (tương tự ObjectId trong Mongoose)
    val amenities: List<String>?, // Danh sách các tiện nghi trong phòng
    val limitPerson: Int, // Giới hạn số người ở phòng
    val status: Int, // Trạng thái phòng: 0 = chưa cho thuê, 1 = đang cho thuê
    val createdAt: String?, // Thời gian tạo phòng (timestamp)
    val updatedAt: String? // Thời gian cập nhật phòng (timestamp)
)
