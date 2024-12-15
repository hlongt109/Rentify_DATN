package com.rentify.user.app.model.Model

import com.google.gson.annotations.SerializedName
import kotlin.String

data class Booking(
    val _id: String,
    val user_id: String,
    val room_id: String,
    val manager_id: String,
    val check_in_date: String,
    val status: Int,
    val created_at: String,
)

data class BookingRequest(
    val user_id: String,
    val room_id: String,
    val building_id: String,
    val manager_id: String,
    val check_in_date: String,
)

data class UserOfBooking(
    @SerializedName("_id") val _id: String,
    @SerializedName("name") val name: String,
    @SerializedName("phoneNumber") val phoneNumber: String,
)

//Thực hiện get dữ liệu đặt phòng
data class BookingResponse(
    @SerializedName("_id") val _id: String,
    @SerializedName("check_in_date") val check_in_date: String,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("manager_id") val manager_id: UserOfBooking,
    @SerializedName("room_id") val room_id: RoomId,
    @SerializedName("status") val status: Int,
    @SerializedName("user_id") val user_id: String,
)

data class BookingStaff(
    val _id: String,
    val user: UserOfBooking,
    val building_id: BuildingId,
    val room: RoomId,
    val manager_id: String,
    val check_in_date: String,
    val status: Int,
    val created_at: String,
)

data class BookingResponseStaff(
    @SerializedName("_id") val _id: String,
    @SerializedName("building_id") val building_id: BuildingId,
    @SerializedName("check_in_date") val check_in_date: String,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("manager_id") val manager_id: String,
    @SerializedName("room_id") val room_id: RoomId,
    @SerializedName("status") val status: Int,
    @SerializedName("user_id") val user_id: UserOfBooking,
)

data class RoomId(
    @SerializedName("_id") val _id: String,
    @SerializedName("room_name") val room_name: String,
    @SerializedName("room_type") val room_type: String,
    @SerializedName("price")val price: Int,
    @SerializedName("photos_room")val photos_room: List<String>,
)

fun BookingResponseStaff.toBooking(): BookingStaff {
    return BookingStaff(
        _id = this._id,
        building_id = this.building_id,
        check_in_date = this.check_in_date,
        user = this.user_id,
        room = this.room_id,
        manager_id = this.manager_id,
        status = this.status,
        created_at = this.created_at
    )
}

// Liên quan đến update phòng
data class StatusBookingRequest(
    val status: Int,
)