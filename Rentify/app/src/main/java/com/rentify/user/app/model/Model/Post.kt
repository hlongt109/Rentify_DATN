package com.rentify.user.app.model

data class Post1(
    val userId: String,         // ID của người dùng
    val title: String,          // Tiêu đề bài đăng
    val content: String,        // Nội dung bài đăng
    val status: Int,            // Trạng thái bài đăng
//    val video: List<String>?,   // Danh sách video (nếu có)
//    val photo: List<String>?,   // Danh sách ảnh (nếu có)
    val video: List<String> = listOf(),   // Danh sách video (nếu có)
    val photo: List<String> = listOf(),   // Danh sách ảnh (nếu có)
    val price: Int? = null,      // Giá (nullable vì không bắt buộc)
    val address: String? = null, // Địa chỉ (nullable vì không bắt buộc)
    val phoneNumber: String? = null, // Số điện thoại (nullable vì không bắt buộc)
    val roomType: String? = null,    // Loại phòng (nullable vì không bắt buộc)
    val postType: PostType,     // Loại bài đăng (roommate hoặc rent)
    val amenities: List<String> = listOf(), // Danh sách tiện nghi
    val services: List<String> = listOf(),  // Danh sách dịch vụ
    val createdAt: String? = null, // Ngày tạo
    val updatedAt: String? = null   // Ngày cập nhật
)
data class Post(
    val _id: String,
    val user_id: String,
    val building_id: String,
    val room_id: String?,
    val title: String,
    val content: String,
    val post_type: String,
    val status: Int,
    val photos: List<String> = listOf(),
    val videos: List<String> = listOf(),
    val created_at: String,
    val updated_at: String
)

enum class PostType {
    ROOMATE, RENT
}
data class PostResponse(
    val _id: String,
    val user_id: String,
    val building_id: String,
    val room_id: String?,
    val title: String,
    val content: String,
    val post_type: String,
    val status: Int,
    val photos: List<String> = listOf(),
    val  videos: List<String> = listOf(),
    // Danh sách dịch vụ
    val createdAt: String? = null,     // Ngày tạo
    val updatedAt: String? = null      // Ngày cập nhật
)
data class UpdatePostRequest(
    val userId: String,         // ID của người dùng
    val title: String?,         // Tiêu đề bài đăng (nullable vì không bắt buộc cập nhật)
    val content: String?,       // Nội dung bài đăng
    val status: Int?,           // Trạng thái bài đăng
    val videos: List<String>?,   // Danh sách video
    val photos: List<String>?,   // Danh sách ảnh
    val price: Int?,            // Giá
    val address: String?,       // Địa chỉ
    val phoneNumber: String?,   // Số điện thoại
    val roomType: String?,      // Loại phòng
    val amenities: List<String>?, // Danh sách tiện nghi
    val services: List<String>?  // Danh sách dịch vụ
)
data class PostingDetail(
    val id: String,
    val title: String,
    val content: String?,
    val post_type: String,
    val status: String,
    val photos: List<String>,
    val videos: List<String>,
    val created_at: String,
    val updated_at: String,
    val user: User?,
    val building: Building?,
    val room: Room_post?
)

data class Building(
    val _id: String,
    val nameBuilding: String,
    val address: String,
    val post_count: Int
)
data class Room_post(
    val _id: String,
    val room_name: String,
    val building_id: String,
    val room_type: String,
    val price: Int
)
data class BuildingsResponse(
    val status: Int,
    val data: List<Building>
)

data class RoomsResponse(
    val status: Int,
    val data: List<Room_post> // Danh sách phòng
)
data class UpdatePostResponse(
    val success: Boolean,         // Trạng thái thành công
    val message: String,          // Thông báo từ server
    val updatedPost: PostingDetail? // Dữ liệu bài đăng đã cập nhật (nếu có)
)