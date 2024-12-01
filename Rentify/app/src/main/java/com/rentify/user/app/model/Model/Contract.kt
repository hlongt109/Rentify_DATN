package com.rentify.user.app.model.Model

import com.google.gson.annotations.SerializedName

data class Contract(
    val _id: String,
    val building_id: BuildingInfoOfContract,
    val content: String,
    val created_at: String,
    val end_date: String,
    val manage_id: String,
    val photos_contract: List<String>,
    val room_id: RoomInfoOfContract,
    val start_date: String,
    val status: Int,
    val user_id: List<UserInfoOfContract>
)

data class ContractResponse(
    @SerializedName("_id")val _id: String,
    @SerializedName("building_id")val building_id: BuildingInfoOfContract,
    @SerializedName("content")val content: String,
    @SerializedName("created_at")val created_at: String,
    @SerializedName("end_date")val end_date: String,
    @SerializedName("manage_id")val manage_id: String,
    @SerializedName("photos_contract")val photos_contract: List<String>,
    @SerializedName("room_id")val room_id: RoomInfoOfContract,
    @SerializedName("start_date")val start_date: String,
    @SerializedName("status")val status: Int,
    @SerializedName("user_id")val user_id: List<UserInfoOfContract>
)

data class RoomInfoOfContract(
    @SerializedName("_id")val _id: String,
    @SerializedName("price")val price: Int,
    @SerializedName("room_name")val room_name: String,
    @SerializedName("room_type")val room_type: String
)

data class BuildingInfoOfContract(
    @SerializedName("_id")val _id: String,
    @SerializedName("nameBuilding")val nameBuilding: String
)

data class UserInfoOfContract(
    @SerializedName("_id")val _id: String,
    @SerializedName("name")val name: String
)