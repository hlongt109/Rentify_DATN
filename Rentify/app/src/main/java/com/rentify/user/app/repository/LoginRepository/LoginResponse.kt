package com.rentify.user.app.repository.LoginRepository

import com.google.gson.annotations.SerializedName
import java.lang.Error

data class RegisterRequest(
    val name: String,
    var email: String,
    var password: String,
    val phoneNumber: String,
    val username: String,
)
data class LoginResponse(
    val _id: String,
    val email: String,
    val password: String,
    val role: String, // Vai trò (admin, user, nhan vien, ...)
    val name: String,
    val verified: Boolean,
    val phoneNumber: String,
    val dob: String, // Ngày sinh
    val gender: String, // Giới tính
    val address: String,
    val profile_picture_url: String,
    val username: String,
    val createdAt: String,
    val updatedAt: String
)

data class ApiResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: LoginResponse,
    @SerializedName("fileUrl") val fileUrl: String?
)