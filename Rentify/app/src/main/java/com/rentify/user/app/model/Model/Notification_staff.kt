package com.rentify.user.app.model.Model

import com.rentify.user.app.model.User

data class Notification(
    val _id: String,           // ID của thông báo
    val user_id: User,         // Thông tin người dùng liên quan (populate)
    val title: String,         // Tiêu đề thông báo
    val content: String,       // Nội dung thông báo
    val read_status: String,   // Trạng thái (unread, read)
    val created_at: String     // Thời gian tạo thông báo
)

// Response sau khi tạo thông báo
data class NotificationResponse(
    val status: Int,                      // Trạng thái trả về (200, 400, ...)
    val message: String,                  // Thông điệp trả về từ API
    val data: List<Notification>,
    val unreadCount:Int// Dữ liệu chứa danh sách thông báo
)

//data class NotificationResponse(
//    val status: Int,
//    val message: String,       // Thông điệp trả về
//    val data: List<Notification>
//)
