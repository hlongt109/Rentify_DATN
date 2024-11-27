package com.rentify.user.app.model.Model

data class Invoice(
    val userId: String,             // ID của người dùng, tham chiếu đến bảng User
    val roomId: String,             // ID của phòng, tham chiếu đến bảng Room
    val description: List<String> = listOf(), // Mô tả là một mảng (List trong Kotlin)
    val amount: Double,               // Số tiền, yêu cầu nhập
    val transactionType: TransactionType, // Loại giao dịch (income/expense), sử dụng enum
    val dueDate: String? = null,      // Hạn chót, có thể để null
    val paymentStatus: Boolean? = null, // Trạng thái thanh toán, có thể để null
    val createdAt: String? = null     // Ngày tạo, có thể để null
)

enum class TransactionType {
    INCOME, EXPENSE
}