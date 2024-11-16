package com.rentify.user.app.model.Model

data class Location(
    val city: String,      // Tỉnh/Thành phố
    val district: String,  // Quận/Huyện
    val ward: String,      // Xã/Phường
    val latitude: Double,   // Vĩ độ
    val longitude: Double    // Kinh độ
)
