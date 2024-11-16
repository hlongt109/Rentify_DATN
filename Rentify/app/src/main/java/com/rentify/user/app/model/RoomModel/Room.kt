package com.rentify.user.app.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.serialization.Serializable

@Serializable
data class Room(
    val landlordId: String?,        // ID của chủ nhà
    val buildingId: String,         // ID của tòa nhà
    val roomName: String?,          // Tên phòng
    val roomType: String,           // Loại phòng
    val description: String,        // Mô tả phòng
    val price: Double,              // Giá phòng
    val size: String,               // Kích thước phòng (ví dụ: "40m2")
    val availabilityStatus: String, // Trạng thái sẵn có
    val videoRoom: String? = null,  // URL video của phòng (nếu có)
    val photosRoom: List<String>? = null, // Danh sách URL ảnh của phòng
    val serviceIds: List<String>? = null, // Danh sách ID dịch vụ liên quan đến phòng
    val amenities: List<String>? = null,  // Tiện nghi của phòng
    val serviceFees: List<String>? = null, // Danh sách phí dịch vụ
    val limitPerson: Int,           // Giới hạn số người
    val status: Int,                // 0: Chưa cho thuê, 1: Đã cho thuê
    val createdAt: String? = null,  // Ngày tạo
    val updatedAt: String? = null   // Ngày cập nhật
)

@Serializable
data class AddRoomResponse(
    val id: String,                  // ID của phòng
    val roomName: String,            // Tên phòng
    val price: Double,               // Giá phòng
    val description: String,         // Mô tả phòng
    val image: String,               // URL ảnh đại diện
    val type: String? = null         // Loại phòng
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(roomName)
        parcel.writeDouble(price)
        parcel.writeString(description)
        parcel.writeString(image)
        parcel.writeString(type)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<AddRoomResponse> {
        override fun createFromParcel(parcel: Parcel): AddRoomResponse = AddRoomResponse(parcel)
        override fun newArray(size: Int): Array<AddRoomResponse?> = arrayOfNulls(size)
    }
}

@Serializable
data class RoomListResponseWrapper(
    val data: List<Room>             // Danh sách các phòng
)
