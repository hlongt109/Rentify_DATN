package com.rentify.user.app.model.ServiceAdmin

data class AdminService(
    val `data`: List<Data>
)
data class Data(
    val __v: Int,
    val _id: String,
    val admin_id: String,
    val created_at: String,
    val description: String,
    val name: String,
    val photos: List<String>,
    val price: Int,
    val updated_at: String
)