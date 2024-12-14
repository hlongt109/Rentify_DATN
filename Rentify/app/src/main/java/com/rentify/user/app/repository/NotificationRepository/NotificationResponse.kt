package com.rentify.user.app.repository.NotificationRepository

data class NotificationRequest(
    val user_id: String,
    val title: String,
    val content: String
)
data class NotificationResponse(
    val message: String,
    val notification: NotificationData?
)

data class NotificationData(
    val user_id: String,
    val title: String,
    val content: String,
    val created_at: String
)
