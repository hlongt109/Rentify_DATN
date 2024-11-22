package com.rentify.user.app.model

data class Post(
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

enum class PostType {
    ROOMATE, RENT
}
data class PostResponse(
    val userId: String,                // ID của người dùng
    val title: String,                 // Tiêu đề bài đăng
    val content: String,               // Nội dung bài đăng
    val status: Int,                   // Trạng thái bài đăng
    val video: List<String> = listOf(),  // Danh sách video
    val photo: List<String> = listOf(),  // Danh sách ảnh
    val price: Int? = null,            // Giá
    val address: String? = null,       // Địa chỉ
    val phoneNumber: String? = null,   // Số điện thoại
    val roomType: String? = null,      // Loại phòng
    val postType: PostType,            // Loại bài đăng
    val amenities: List<String> = listOf(), // Danh sách tiện nghi
    val services: List<String> = listOf(),  // Danh sách dịch vụ
    val createdAt: String? = null,     // Ngày tạo
    val updatedAt: String? = null      // Ngày cập nhật
)
data class UpdatePostRequest(
    val userId: String,         // ID của người dùng
    val title: String?,         // Tiêu đề bài đăng (nullable vì không bắt buộc cập nhật)
    val content: String?,       // Nội dung bài đăng
    val status: Int?,           // Trạng thái bài đăng
    val video: List<String>?,   // Danh sách video
    val photo: List<String>?,   // Danh sách ảnh
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
    val content: String,
    val price: Double?,
    val address: String?,
    val phoneNumber: String?,
    val room_type: String?,
    val amenities: List<String>?,
    val services: List<String>?,
    val post_type: String,
    val status: Int,
    val photos: List<String>?, // Tên trường đúng với JSON
    val videos: List<String>?, // Tên trường đúng với JSON
    val created_at: String?,
    val updated_at: String?,
    val user: UserDetail?
)
data class UserDetail(
    val id: String,
    val email: String
)
data class UpdatePostResponse(
    val success: Boolean,         // Trạng thái thành công
    val message: String,          // Thông báo từ server
    val updatedPost: PostingDetail? // Dữ liệu bài đăng đã cập nhật (nếu có)
)
