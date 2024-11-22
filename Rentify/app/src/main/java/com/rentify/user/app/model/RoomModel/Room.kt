package com.rentify.user.app.model

import java.util.Date

data class Room(
    val landlordId: String,  // ID của chủ nhà
    val buildingId: String,  // ID của tòa nhà

    val roomType: String,  // Loại phòng
    val description: String,  // Mô tả phòng
    val price: Double,  // Giá phòng
    val size: String,  // Kích thước phòng (ví dụ: "40m2")

    val availabilityStatus: String,  // Trạng thái sẵn có

    val videoRoom: String? = null,  // Video về phòng (nếu có)
    val photosRoom: List<String>? = null,  // Danh sách ảnh về phòng
    val serviceIds: List<String>? = null,  // Danh sách ID các dịch vụ (máy giặt, tủ lạnh, ...)
    val amenities: List<String>? = null,  // Tiện nghi phòng
    val serviceFees: List<String>? = null,  // Phí dịch vụ

    val limitPerson: Int,  // Giới hạn số người
    val status: Int,  // Trạng thái (0: chưa cho thuê, 1: đã cho thuê)

    val createdAt: String? = null,  // Ngày tạo
    val updatedAt: String? = null,  // Ngày cập
)
