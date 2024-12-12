package com.rentify.user.app.repository.StaffRepository.RoomRepository

data class RoomResponse(
    val status: Int,
    val message: String,
    val data: List<Room>
)

data class Room(
    val _id: String,
    val building_id: Building,
    val room_name: String,
    val room_type: String,
    val description: String,
    val price: Int,
    val size: String,
    val video_room: List<String>,
    val photos_room: List<String>,
    val service: List<Service>,
    val amenities: List<String>,
    val limit_person: Int,
    val status: Int,
    val created_at: String,
    val updated_at: String,
    val __v: Int,
    val sale: Int
)

data class Building(
    val _id: String,
    val serviceFees: List<ServiceFee>
)

data class ServiceFee(
    val name: String,
    val price: Double
)

data class Service(
    val _id: String,
    val name: String,
    val description: String,
    val price: Int,
    val photos: List<String>,
    val created_at: String,
    val updated_at: String,
    val landlord_id: String
)
