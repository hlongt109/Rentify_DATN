package com.rentify.user.app.repository.StaffRepository.InvoiceRepository

import com.rentify.user.app.model.Model.InvoiceDetail
import com.rentify.user.app.model.Model.ServiceDescription
import com.rentify.user.app.model.Model.TransactionType

// Enum cho trạng thái thanh toán
enum class InvoiceStatus {
    PAID, UNPAID
}

// Data Classes
data class InvoiceResponse(
    val status: Int,
    val message: String,
    val data: InvoiceData
)

data class InvoiceData(
    val paid: List<Invoice>,
    val unpaid: List<Invoice>
)

data class Invoice(
    val _id: String,
    val user_id: User,
    val room_id: RoomDetail,
    val description: List<Map<String, String>>,
    val amount: Double,
    val transaction_type: String,
    val due_date: String,
    val payment_status: String,
    val created_at: String,
    val detail_invoice: List<Any> = emptyList()
)

data class User(
    val _id: String,
    val phoneNumber: String,
    val name: String
)

data class RoomDetail(
    val _id: String,
    val room_name: String,
    val price: Double,
    val service: List<ServiceDetail>,
    val limit_person: Int
)

data class ServiceDetail(
    val _id: String,
    val name: String,
    val description: String,
    val price: Double
)

