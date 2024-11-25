package com.rentify.user.app.repository.LoginRepository

import com.google.gson.annotations.SerializedName
import java.lang.Error

data class RegisterRequest(
    val username: String,
    var email: String,
    var password: String
)
data class LoginResponse(
    @SerializedName("_id") val userId: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("role") val role: String, // Vai trò (admin, user, nhan vien, ...)
    @SerializedName("name") val name: String,
    @SerializedName("verified") val verified: Boolean,
)

data class ApiResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: LoginResponse,
    @SerializedName("fileUrl") val fileUrl: String?
)
