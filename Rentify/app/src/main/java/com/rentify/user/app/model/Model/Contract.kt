package com.rentify.user.app.model

import com.google.gson.annotations.SerializedName

data class Contract(
    val _id: String,
    val manage_id: String,
    val user_id: List<User>?, // Thông tin người dùng
  val room_id: Room_post?, // Thông tin phòng
    val building_id: Building?,
  val photos_contract: List<String>?,
  val content: String,
  val start_date: String,
    val end_date: String,
    val status: Int,
  val createdAt: String,
    val duration: String,
    val startDay: String,
    val paymentCycle: String

)

