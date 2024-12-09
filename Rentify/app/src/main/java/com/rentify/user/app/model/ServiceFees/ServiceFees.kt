package com.rentify.user.app.model.ServiceFees

import com.google.gson.annotations.SerializedName

class ServiceFees : ArrayList<ServiceFeesItem>()

data class ServiceFee(
    val name: String,
    val price: Int
)

data class Service(
    val _id: String,
    val name: String
)
data class ServiceFeesItem(
    val __v: Int,
    val _id: String,
    val building_id: BuildingId,
    val content: String,
    val created_at: String,
    val end_date: String,
    val manage_id: String,
    val photos_contract: List<String>,
    val room_id: String,
    val start_date: String,
    val status: Int,
    val user_id: List<String>
)
data class ServiceFeesResponse(
    @SerializedName("_id")val _id: String,
    @SerializedName("building_id")val building_id: BuildingId,
    @SerializedName("content")val content: String,
    @SerializedName("created_at")val created_at: String,
    @SerializedName("end_date")val end_date: String,
    @SerializedName("manage_id")val manage_id: String,
    @SerializedName("photos_contract")val photos_contract: List<String>,
    @SerializedName("room_id")val room_id: String,
    @SerializedName("start_date")val start_date: String,
    @SerializedName("status")val status: Int,
    @SerializedName("user_id")val user_id: List<String>
)
data class BuildingId(
    val _id: String,
    val address: String,
    val description: String,
    val nameBuilding: String,
    val number_of_floors: Int,
    val service: List<Service>,
    val serviceFees: List<ServiceFee>
)