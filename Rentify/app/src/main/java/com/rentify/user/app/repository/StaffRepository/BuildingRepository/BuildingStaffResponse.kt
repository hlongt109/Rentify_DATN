package com.rentify.user.app.repository.StaffRepository.BuildingRepository

data class BuildingStaffResponse(
    val status: Int,
    val message: String,
    val data: List<Building>
)

data class Building(
    val _id: String,
    val landlord_id: String,
    val manager_id: Manager,
    val service: List<String>,
    val nameBuilding: String,
    val address: String,
    val description: String,
    val number_of_floors: Int,
    val created_at: String,
    val updated_at: String,
    val serviceFees: List<ServiceFee>
)

data class Manager(
    val _id: String,
    val email: String,
    val name: String
)

data class ServiceFee(
    val name: String,
    val price: Double
)