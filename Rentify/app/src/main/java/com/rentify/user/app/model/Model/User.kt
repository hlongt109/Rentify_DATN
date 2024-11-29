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

// lay use de xu ly profile
data class UserResponse(
    @SerializedName("_id")val _id: String,
    @SerializedName("username")val username: String,
    @SerializedName("password")val password: String,
    @SerializedName("email")val email: String,
    @SerializedName("phoneNumber")val phoneNumber: String,
    @SerializedName("role")val role: String, // vai trò (admin, user, nhan vien, . ..)
    @SerializedName("name")val name: String,
    @SerializedName("dob")val dob: String, // ngày sinh
    @SerializedName("gender")val gender: String, // giới tính
    @SerializedName("address")val address: String,
    @SerializedName("profile_picture_url")val profile_picture_url: String,
    @SerializedName("verified")val verified: Boolean,
    @SerializedName("landlord_id")val landlord_id : String,
    @SerializedName("created_at")val created_at: String,
    @SerializedName("updated_at")val updated_at: String
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