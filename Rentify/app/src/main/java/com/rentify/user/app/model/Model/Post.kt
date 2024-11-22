package com.rentify.user.app.model

data class Post(
    val userId: String,         // ID của người dùng
    val title: String,          // Tiêu đề bài đăng
    val content: String,        // Nội dung bài đăng
    val status: Int,            // Trạng thái bài đăng
    val video: List<String>?,   // Danh sách video (nếu có)
    val photo: List<String>?,   // Danh sách ảnh (nếu có)
    val postType: PostType,     // Loại bài đăng (roommate hoặc rent)
    val createdAt: String? = null, // Ngày tạo
    val updatedAt: String? = null   // Ngày cập nhật
)

enum class PostType {
    ROOMATE, RENT
}
