package com.rentify.user.app.model.SupportModel

import com.google.gson.annotations.SerializedName

class Support : ArrayList<SupportItem>()

data class BuildingId(
    val _id: String,
    val address: String
)
data class SupportItem(
    val building_id: BuildingId,
    val content_support: String,
    val created_at: String,
    val image: List<String>,
    val room_id: String,
    val status: Int,
    val title_support: String,
    val updated_at: String,
    val user_id: UserId
)
data class UserId(
    val _id: String,
    val email: String,
    val name: String
)
data class SupportResponse(
    @SerializedName("_id") val _id: String,
    @SerializedName("building_id")val building_id: BuildingId,
    @SerializedName("content_support")val content_support: String,
    @SerializedName("created_at")val created_at: String,
    @SerializedName("image")val image: List<String>,
    @SerializedName("room_id")val room_id: String,
    @SerializedName("status")val status: Int,
    @SerializedName("title_support")val title_support: String,
    @SerializedName("updated_at")val updated_at: String,
    @SerializedName("user_id")val user_id: UserId
)