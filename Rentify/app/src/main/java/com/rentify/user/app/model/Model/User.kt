package com.rentify.user.app.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id") val id: String = "",
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