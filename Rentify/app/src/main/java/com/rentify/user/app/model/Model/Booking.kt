package com.rentify.user.app.model.Model

import com.google.gson.annotations.SerializedName

data class Booking (
    val _id: String,
    val user_id: String,
    val room_id: String,
    val manager_id: String,
    val check_in_date: String,
    val status: Int,
    val created_at: String
)

data class BookingRequest(
    val user_id: String,
    val room_id: String,
    val manager_id: String,
    val check_in_date: String
)

data class UserOfBooking(
    @SerializedName("_id")val _id: String,
    @SerializedName("name")val name: String,
    @SerializedName("phoneNumber")val phoneNumber: String,
)

//Thực hiện get dữ liệu đặt phòng
data class BookingResponse(
    @SerializedName("_id")val _id: String,
    @SerializedName("check_in_date")val check_in_date: String,
    @SerializedName("created_at")val created_at: String,
    @SerializedName("manager_id")val manager_id: UserOfBooking,
    @SerializedName("room_id")val room_id: RoomId,
    @SerializedName("status")val status: Int,
    @SerializedName("user_id")val user_id: String
)

data class RoomId(
    @SerializedName("_id")val _id: String,
    @SerializedName("room_name")val room_name: String,
    @SerializedName("room_type")val room_type: String
)

// Liên quan đến update phòng
data class StatusBookingRequest(
    val status: Int
)
