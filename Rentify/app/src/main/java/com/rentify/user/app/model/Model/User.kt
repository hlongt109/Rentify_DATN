package com.rentify.user.app.model

import com.google.gson.annotations.SerializedName


data class User(
    val username: String,
    val password: String,
    val email: String,
    val phoneNumber: String,
    val role: String, // vai trò (admin, user, nhan vien, . ..)
    val name: String,
    val dob: String, // ngày sinh
    val gender: String, // giới tính
    val address: String,
    val profile_picture_url: String,
    val verified: Boolean,
    val landlord_id : String,
    val createdAt: String,
    val updatedAt: String
)

// Xử lý user với màn roomDetail
data class UserOfRoomDetail(
    @SerializedName("landlord")val landlord: Landlord,
    @SerializedName("totalRooms")val totalRooms: Int
)

data class Landlord(
    @SerializedName("_id")val _id: String,
    @SerializedName("address")val address: String,
    @SerializedName("email")val email: String,
    @SerializedName("name")val name: String,
    @SerializedName("phoneNumber")val phoneNumber: String,
    @SerializedName("profile_picture_url")val profile_picture_url: String
)