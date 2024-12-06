package com.rentify.user.app.repository.ForgotRepository

data class ForgotRequest(
    val email: String,
)

data class MailConfirmForgot(
    val email: String,
    val confirmationCode: String,
)

data class ResetPassword(
    val email: String,
    val newPassword: String
)

data class ForgotResponse(
    val status: Int,
    val message: String
)