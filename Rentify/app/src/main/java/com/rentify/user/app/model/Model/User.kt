package com.rentify.user.app.model

import com.google.gson.annotations.SerializedName

data class User(
    val _id: String,
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
    val landlord_id: String,
    val createdAt: String,
    val updatedAt: String,
)
data class User_contrac(
    val _id: String,
    val role: String, // vai trò (admin, user, nhan vien, . ..)
    val profile_picture_url: String,
    val name: String,
)
// Xử lý user với màn roomDetail
data class UserOfRoomDetail(
    @SerializedName("landlord") val landlord: Landlord,
    @SerializedName("totalRooms") val totalRooms: Int,
)

data class Landlord(
    @SerializedName("_id") val _id: String,
    @SerializedName("address") val address: String,
    @SerializedName("email") val email: String,
    @SerializedName("name") val name: String,
    @SerializedName("phoneNumber") val phoneNumber: String,
    @SerializedName("profile_picture_url") val profile_picture_url: String,
)

// lay use de xu ly profile
data class UserResponse(
    @SerializedName("_id") val _id: String,
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String,
    @SerializedName("email") val email: String,
    @SerializedName("phoneNumber") val phoneNumber: String,
    @SerializedName("role") val role: String, // vai trò (admin, user, nhan vien, . ..)
    @SerializedName("name") val name: String,
    @SerializedName("dob") val dob: String, // ngày sinh
    @SerializedName("gender") val gender: String, // giới tính
    @SerializedName("address") val address: String,
    @SerializedName("profile_picture_url") val profile_picture_url: String,
    @SerializedName("verified") val verified: Boolean,
    @SerializedName("landlord_id") val landlord_id: String,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("updated_at") val updated_at: String,
)

data class ProfilePictureResponse(
    val profile_picture_url: String,
)

data class ResponseUser(
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
    @SerializedName("profile_picture_url")val profile_picture_url: String?,
    @SerializedName("verified")val verified: Boolean,
    @SerializedName("updated_at")val updated_at: String
)

data class DataResponse(
    val status: Int,
    val message: String,
    val data: ResponseUser
)

// thien thuc hien lay QR landlord va chu toa

data class LandlordOrStaffs(
    val landlord: LandlordResponse,
    val manager: LandlordResponse,
)

data class LandlordResponse(
    val _id: String,
    val address: String,
    val bankAccount: BankAccount,
    val created_at: String,
    val dob: String,
    val email: String,
    val gender: String,
    val name: String,
    val password: String,
    val phoneNumber: String,
    val profile_picture_url: String,
    val role: String,
    val updated_at: String,
    val username: String,
    val verified: Boolean,
)

data class BankAccount(
    val bank_name: String = "Chưa xác định",
    val bank_number: Long = 0L,
    val qr_bank: List<String> = emptyList(),
    val username: String = "Chưa xác định"
)