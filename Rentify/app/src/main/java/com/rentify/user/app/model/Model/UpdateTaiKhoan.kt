package com.rentify.user.app.model.Model

import com.google.gson.annotations.SerializedName

data class UpdateTaiKhoan(
    val __v: Int,
    val _id: String,
    val address: String,
    val bankAccount: BankAccount,
    val created_at: String,
    val dob: String,
    val email: String,
    val gender: String,
    val landlord_id: String,
    val name: String,
    val password: String,
    val phoneNumber: String,
    val profile_picture_url: String,
    val role: String,
    val updated_at: String,
    val username: String,
    val verified: Boolean
)

data class UpdateTaiKhoanResponse(
    @SerializedName("_id") val _id: String,
    @SerializedName("address") val address: String,
    @SerializedName("bankAccount") val bankAccount: BankAccount,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("dob") val dob: String,
    @SerializedName("email") val email: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("landlord_id") val landlord_id: String,
    @SerializedName("name") val name: String,
    @SerializedName("password") val password: String,
    @SerializedName("phoneNumber") val phoneNumber: String,
    @SerializedName("profile_picture_url") val profile_picture_url: String,
    @SerializedName("role") val role: String,
    @SerializedName("updated_at") val updated_at: String,
    @SerializedName("username") val username: String,
    @SerializedName("verified") val verified: Boolean
)

data class UpdateAccUserResponse(
    @SerializedName("_id") val _id: String,
    @SerializedName("address") val address: String,
    @SerializedName("bankAccount") val bankAccount: BankAccount,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("dob") val dob: String,
    @SerializedName("email") val email: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("name") val name: String,
    @SerializedName("password") val password: String,
    @SerializedName("phoneNumber") val phoneNumber: String,
    @SerializedName("profile_picture_url") val profile_picture_url: String,
    @SerializedName("role") val role: String,
    @SerializedName("updated_at") val updated_at: String,
    @SerializedName("username") val username: String,
    @SerializedName("verified") val verified: Boolean
)

data class BankAccount(
    val bank_name: String,
    val bank_number: Long,
    val qr_bank: List<String>,
    val username: String
)