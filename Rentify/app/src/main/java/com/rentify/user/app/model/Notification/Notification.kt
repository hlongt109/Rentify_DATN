package com.rentify.user.app.model.Notification

data class Notification(
    val `data`: List<Data>,
    val message: String,
    val status: Int
)
data class Data(
    val __v: Int,
    val _id: String,
    val content: String,
    val created_at: String,
    val read_status: String,
    val title: String,
    val user_id: UserId
)
data class UserId(
    val _id: String,
    val email: String,
    val name: String
)