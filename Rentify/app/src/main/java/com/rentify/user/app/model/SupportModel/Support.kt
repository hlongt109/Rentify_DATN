package com.rentify.user.app.model.SupportModel

import com.google.gson.annotations.SerializedName

data class Support(
    val content_support: String,
    val created_at: String,
    val image: List<String>,
    val landlord_id: LandlordId,
    val room_id: RoomId,
    val status: Int,
    val title_support: String,
    val updated_at: String,
    val user_id: UserId
)
data class SupportResponse(
    @SerializedName("_id")val _id: Id,
    @SerializedName("content_support")val content_support: String,
    @SerializedName("created_at")val created_at: String,
    @SerializedName("image")val image: List<String>,
    @SerializedName("landlord_id")val landlord_id: LandlordId,
    @SerializedName("room_id")val room_id: RoomId,
    @SerializedName("status")val status: Int,
    @SerializedName("title_support")val title_support: String,
    @SerializedName("updated_at")val updated_at: String,
    @SerializedName("user_id")val user_id: UserId
)
data class RoomListResponse(
    @SerializedName("landlord_id") val landlordId: String,
    @SerializedName("room_ids") val roomIds: List<String>
)
data class RoomId(
    val `$oid`: String,
    val room_name: String,
)
data class Id(
    val `$oid`: String,
    val room_name: String
)
data class LandlordId(
    val _id: Id,
    val email: String,
    val name: String
)
data class UserId(
    val _id: Id,
    val email: String,
    val name: String
)