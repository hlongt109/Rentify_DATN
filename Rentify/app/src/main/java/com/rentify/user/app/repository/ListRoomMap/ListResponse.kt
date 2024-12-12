package com.rentify.user.app.repository.ListRoomMap

import com.google.gson.annotations.SerializedName

// Lớp đại diện cho Building
data class Building(
    @SerializedName("_id") val id: String,
    @SerializedName("nameBuilding") val name: String,
    @SerializedName("address") val address: String,
    @SerializedName("toaDo") val coordinates: List<Double>
)

// Lớp đại diện cho RepresentativeRoom
data class RepresentativeRoom(
    @SerializedName("_id") val id: String,
    @SerializedName("building_id") val building: Building,
    @SerializedName("room_name") val roomName: String,
    @SerializedName("room_type") val roomType: String,
    @SerializedName("description") val description: String,
    @SerializedName("price") val price: Int,
    @SerializedName("decrease") val decrease: Int,
    @SerializedName("size") val size: String,
    @SerializedName("video_room") val videoRoom: List<String>,
    @SerializedName("photos_room") val photosRoom: List<String>,
    @SerializedName("service") val services: List<String>,
    @SerializedName("amenities") val amenities: List<String>,
    @SerializedName("limit_person") val limitPerson: Int,
    @SerializedName("status") val status: Int,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("__v") val version: Int
)

// Lớp đại diện cho một mục trong danh sách data
data class RoomData(
    @SerializedName("building") val building: Building,
    @SerializedName("representativeRoom") val representativeRoom: RepresentativeRoom
)

// Lớp đại diện cho response
data class RoomResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<RoomData>
)