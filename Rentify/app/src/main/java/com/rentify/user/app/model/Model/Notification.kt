package com.rentify.user.app.model.Model

data class NotificationRequest(
    val user_id: String,
    val title: String,
    val content: String,
)