package com.rentify.user.app.model.SupportModel

import com.google.gson.annotations.SerializedName

data class SupportResponse(
    @SerializedName("_id")val _id: String,
    val building_id: BuildingOfSupport,
    val content_support: String,
    val created_at: String,
    val image: List<String>,
    val room_id: RoomOfSupport,
    val status: Int,
    val title_support: String,
    val updated_at: String,
    val user_id: UserOfSupport
)

data class RoomOfSupport(
    @SerializedName("_id")val _id: String,
    val room_name: String
)

data class UserOfSupport(
    @SerializedName("_id")val _id: String
)

data class BuildingOfSupport(
    @SerializedName("_id")val _id: String,
    val nameBuilding: String
)

data class CreateReportResponse(
    val message: String,
    val support: Any
)