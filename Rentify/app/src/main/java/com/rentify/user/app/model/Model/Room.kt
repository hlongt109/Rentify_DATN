package com.rentify.user.app.model.Model

import com.google.gson.annotations.SerializedName

data class Room(
    val _id: String,
    val amenities: List<String>,
    val building_id: BuildingId,
    val created_at: String,
    val description: String,
    val limit_person: Int,
    val photos_room: List<Any>,
    val price: Int,
    val room_name: String,
    val room_type: String,
    val service: List<String>,
    val size: String,
    val status: Int,
    val updated_at: String,
    val video_room: List<Any>
)

// Thử nghiệm loadmore
data class RoomPage(
    val totalRooms: Int,
    val totalPages: Int,
    val currentPage: Int,
    val rooms: List<RoomSaleResponse>
)

data class RoomPageSale(
    val totalRooms: Int,
    val totalPages: Int,
    val currentPage: Int,
    val rooms: List<RoomSaleResponse>
)
// Thử nghiệm loadmore

data class RoomResponse(
    @SerializedName("_id")val _id: String,
    @SerializedName("amenities")val amenities: List<String>,
    @SerializedName("building_id")val building_id: BuildingId,
    @SerializedName("created_at")val created_at: String,
    @SerializedName("description")val description: String,
    @SerializedName("limit_person")val limit_person: Int,
    @SerializedName("photos_room")val photos_room: List<String>,
    @SerializedName("price")val price: Int,
    @SerializedName("room_name")val room_name: String,
    @SerializedName("room_type")val room_type: String,
    @SerializedName("service")val service: List<String>,
    @SerializedName("size")val size: String,
    @SerializedName("status")val status: Int,
    @SerializedName("updated_at")val updated_at: String,
    @SerializedName("video_room")val video_room: List<String>
)

data class BuildingId(
    @SerializedName("_id")val _id: String,
    @SerializedName("address")val address: String,
    @SerializedName("description")val description: String,
    @SerializedName("nameBuilding")val nameBuilding: String
)

// thực hiện chi tiết phong
data class RoomDetailResponse(
    @SerializedName("_id")val _id: String,
    @SerializedName("amenities")val amenities: List<String>,
    @SerializedName("building_id")val building_id: BuildingOfRoomDetail,
    @SerializedName("created_at")val created_at: String,
    @SerializedName("description")val description: String,
    @SerializedName("limit_person")val limit_person: Int,
    @SerializedName("photos_room")val photos_room: List<String>,
    @SerializedName("price")val price: Int,
    @SerializedName("room_name")val room_name: String,
    @SerializedName("room_type")val room_type: String,
    @SerializedName("service")val service: List<Service>,
    @SerializedName("size")val size: String,
    @SerializedName("status")val status: Int,
    @SerializedName("updated_at")val updated_at: String,
    @SerializedName("video_room")val video_room: List<String>,
    @SerializedName("sale")val sale: Int
)

data class BuildingOfRoomDetail(
    @SerializedName("_id")val _id: String,
    @SerializedName("address")val address: String,
    @SerializedName("description")val description: String,
    @SerializedName("landlord_id")val landlord_id: LandlordOfRoomDetail,
    @SerializedName("manager_id")val manager_id: ManagerOfRoomDetail,
    @SerializedName("nameBuilding")val nameBuilding: String,
    @SerializedName("serviceFees")val serviceFees: List<ServiceFee>
)

data class ManagerOfRoomDetail(
    @SerializedName("_id")val _id: String,
    @SerializedName("name")val name: String,
    @SerializedName("phoneNumber")val phoneNumber: String,
)

data class LandlordOfRoomDetail(
    @SerializedName("_id")val _id: String,
    @SerializedName("name")val name: String,
    @SerializedName("phoneNumber")val phoneNumber: String,
)

data class ServiceFee(
    @SerializedName("name")val name: String,
    @SerializedName("price")val price: Int
)

data class Service(
    @SerializedName("name")val name: String,
    @SerializedName("price")val price: Int
)

data class EmptyRoomResponse(
    @SerializedName("_id")val _id: String,
    @SerializedName("room_name")val room_name: String
)
// thực hiện chi tiết phong

// lay cac phong sale
data class RoomSaleResponse(
    @SerializedName("_id")val _id: String,
    @SerializedName("amenities")val amenities: List<String>,
    @SerializedName("building_id")val building_id: BuildingId,
    @SerializedName("created_at")val created_at: String,
    @SerializedName("description")val description: String,
    @SerializedName("limit_person")val limit_person: Int,
    @SerializedName("photos_room")val photos_room: List<String>,
    @SerializedName("price")val price: Int,
    @SerializedName("room_name")val room_name: String,
    @SerializedName("room_type")val room_type: String,
    @SerializedName("service")val service: List<String>,
    @SerializedName("size")val size: String,
    @SerializedName("status")val status: Int,
    @SerializedName("updated_at")val updated_at: String,
    @SerializedName("video_room")val video_room: List<String>,
    @SerializedName("sale")val sale: Int
)
