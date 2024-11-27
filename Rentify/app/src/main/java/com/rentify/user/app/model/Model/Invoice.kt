package com.rentify.user.app.model.Model

data class Invoice(
    val userId: String, // user_id (ObjectId ref tới User)
    val roomId: String, // room_id (ObjectId ref tới Room)
    val description: List<ServiceDescription>, // danh sách mô tả dịch vụ
    val amount: Double, // số tiền (tổng tiền)
    val transactionType: TransactionType, // loại giao dịch (income/expense)
    val dueDate: String?, // hạn chót (tùy chọn)
    val paymentStatus: String?, // trạng thái thanh toán (tùy chọn)
    val createdAt: String?, // ngày tạo (tùy chọn)
    val detailInvoice: List<InvoiceDetail> // thông tin chi tiết hóa đơn
)

data class ServiceDescription(
    val serviceName: String, // tên dịch vụ (ví dụ: điện, nước)
    val quantity: Double, // số lượng sử dụng (ví dụ: kWh, m3 nước)
    val pricePerUnit: Double, // giá mỗi đơn vị
    val total: Double // tổng chi phí cho dịch vụ đó
)

data class InvoiceDetail(
    val name: String?, // tên dịch vụ
    val fee: Double?, // phí dịch vụ
    val quantity: Double? // số lượng (ví dụ: số điện, số nước)
)

enum class TransactionType {
    INCOME,  // thu nhập
    EXPENSE  // chi phí
}