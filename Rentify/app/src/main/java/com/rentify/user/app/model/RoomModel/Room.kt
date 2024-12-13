package com.rentify.user.app.model

import com.google.gson.annotations.SerializedName
import com.rentify.user.app.model.Model.ServiceFee
import kotlinx.serialization.Serializable

@Serializable
data class Room(
    @SerializedName("_id") val id: String = "",
    @SerializedName("building_id") val buildingId: String,         // ID của tòa nhà
    val room_name: String?,          // Tên phòng
    val room_type: String,           // Loại phòng
    val description: String,        // Mô tả phòng
    val price: Double,              // Giá phòng
    val size: String,               // Kích thước phòng (ví dụ: "40m2")
    val video_room: List<String>,  // URL video của phòng (nếu có)
    val photos_room: List<String>, // Danh sách URL ảnh của phòng
    val service: List<ServiceOfRoom>, // Danh sách ID dịch vụ liên quan đến phòng
    val amenities: List<String> = listOf(),  // Tiện nghi của phòng
    val limit_person: Int,           // Giới hạn số người
    val status: Int,                // 0: Chưa cho thuê, 1: Đã cho thuê
    val createdAt: String? = null,  // Ngày tạo
    val updatedAt: String? = null,   // Ngày cập nhật
    @SerializedName("sale") val sale: Int,
)

data class ServiceOfRoom(
    val _id: String,
    val name: String
)

data class AddRoomResponse(
    val success: Boolean,
    val message: String,
    val room: RoomDetail
)

@Serializable
data class RoomDetail(
    val _id: String,
    val building_id: String,
    val room_name: String,
    val room_type: String,
    val description: String,
    val price: Double,
    val size: String,
    val video_room: List<String> = emptyList(),
    val photos_room: List<String> = emptyList(),
    val service: List<String> = emptyList(),
    val amenities: List<String> = emptyList(),
    val limit_person: Int,
    val status: Int,
    val sale: Int,
    val created_at: String,
    val updated_at: String
)

@Serializable
data class PhotoRoom(
    val url: String
)

data class BuildingWithRooms(
    val _id: String,
    val nameBuilding: String,
    val address: String,
    val serviceFees: List<ServiceFee>,
    val rooms: List<Room>
)

// tao mot data class de hien thi du lieu tra ve
data class ServiceOfBuilding(
    @SerializedName("_id")val _id: String,
    @SerializedName("name")val name: String,
)

