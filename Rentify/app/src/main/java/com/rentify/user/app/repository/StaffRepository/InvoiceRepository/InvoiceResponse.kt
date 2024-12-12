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
    val unpaid: List<Invoice>,
    val wait: List<Invoice>
)

data class InvoiceConfirmPaid(
    val payment_status: String
)

data class InvoiceAdd(
    val _id: String,
    val user_id: String,
    val building_id: String,
    val room_id: String,
    val description: List<Description>,
    val describe: String,
    val type_invoice: String = "rent",
    val amount: Double,
    val transaction_type: String = "expense",
    val due_date: String,
    val payment_status: String = "unpaid",
    val created_at: String,
)

data class Invoice(
    val _id: String,
    val user_id: User,
    val room_id: RoomDetail,
    val description: List<Description>,
    val amount: Double,
    val transaction_type: String,
    val due_date: String,
    val payment_status: String,
    val created_at: String,
    val detail_invoice: List<Any> = emptyList(),
    val image_paymentofuser : String
)
data class Description(
    val service_name: String,
    val quantity: Int,
    val price_per_unit: Double,
    val total: Double
)


data class DetailInvoice(
    val name: String,
    val fee: Double,
    val quantity: Int
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
    val limit_person: Int,
    val sale: Int
)


data class ServiceDetail(
    val _id: String,
    val name: String,
    val description: String,
    val price: Double
)



