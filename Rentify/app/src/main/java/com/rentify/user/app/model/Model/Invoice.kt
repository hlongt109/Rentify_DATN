package com.rentify.user.app.model.Model

import com.google.gson.annotations.SerializedName

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


// thien code doan nay xu li hoa don usser
data class InvoiceResponse(
    @SerializedName("_id")val _id: String,
    @SerializedName("amount")val amount: Int,
    @SerializedName("building_id")val building_id: String,
    @SerializedName("created_at")val created_at: String,
    @SerializedName("describe")val describe: String,
    @SerializedName("description")val description: List<DescriptionOfInvoice>,
    @SerializedName("due_date")val due_date: String,
    @SerializedName("payment_status")val payment_status: String,
    @SerializedName("room_id")val room_id: RoomOfInvoice,
    @SerializedName("transaction_type")val transaction_type: String,
    @SerializedName("type_invoice")val type_invoice: String,
    @SerializedName("user_id")val user_id: String,
    @SerializedName("image_paymentofuser")val image_paymentofuser: String
)

data class InvoiceOfUpdate(
    @SerializedName("_id")val _id: String,
    @SerializedName("amount")val amount: Int,
    @SerializedName("building_id")val building_id: String,
    @SerializedName("created_at")val created_at: String,
    @SerializedName("describe")val describe: String,
    @SerializedName("description")val description: List<DescriptionOfInvoice>,
    @SerializedName("due_date")val due_date: String,
    @SerializedName("payment_status")val payment_status: String,
    @SerializedName("room_id")val room_id: String,
    @SerializedName("transaction_type")val transaction_type: String,
    @SerializedName("type_invoice")val type_invoice: String,
    @SerializedName("user_id")val user_id: String,
    @SerializedName("image_paymentofuser")val image_paymentofuser: String
)

data class DescriptionOfInvoice(
    @SerializedName("_id")val _id: String,
    @SerializedName("price_per_unit")val price_per_unit: Int,
    @SerializedName("quantity")val quantity: Int,
    @SerializedName("service_name")val service_name: String,
    @SerializedName("total")val total: Int
)

data class RoomOfInvoice(
    @SerializedName("_id")val _id: String,
    @SerializedName("price")val price: Int,
    @SerializedName("room_name")val room_name: String,
    @SerializedName("room_type")val room_type: String,
    @SerializedName("sale")val sale: Int,
)

// thuc hien chuc nang update hoa don
data class UpdateStatus(
    val status: Int,
    val message: String,
    val data: UpdateInvoice
)

data class UpdateInvoice(
    @SerializedName("_id") val id: String?,
    val amount: Int?,
    @SerializedName("building_id") val buildingId: String?,
    @SerializedName("created_at") val createdAt: String?,
    val describe: String?,
    @SerializedName("description") val description: List<DescriptionOfUpdateInvoice>?,
    @SerializedName("due_date") val dueDate: String?,
    @SerializedName("payment_status") val paymentStatus: String?,
    @SerializedName("room_id") val roomId: RoomOfUpdateInvoice?,
    @SerializedName("transaction_type") val transactionType: String?,
    @SerializedName("type_invoice") val typeInvoice: String?,
    @SerializedName("user_id") val userId: UserOfUpdateInvoice?
)

data class RoomOfUpdateInvoice(
    val _id: String?,
    val limit_person: Int?,
    val price: Int?,
    val room_name: String?,
    val service: List<ServiceX>?
)

data class ServiceX(
    val _id: String?,
    val description: String?,
    val name: String?
)

data class UserOfUpdateInvoice(
    val _id: String?,
    val name: String?,
    val phoneNumber: String?
)

data class DescriptionOfUpdateInvoice(
    val service_name: String?,
    val quantity: Int?,
    @SerializedName("price_per_unit") val pricePerUnit: Double?,
    val total: Double?,
    val _id: String?
)

// xử lý phan update bill

data class InvoiceUpdate(
    val user_id: String,
    val building_id: String,
    val room_id: String,
    val description: List<DescriptionItem>,
    val describe: String,
    val type_invoice: String,
    val amount: Double,
    val transaction_type: String,
    val due_date: String,
    val payment_status: String,
    val image_paymentofuser: String?,
    val created_at: String,
    val updated_at: String
)


data class InvoiceUpdateRequest(
    val description: List<DescriptionItem>? = null,
    val describe: String? = null,
    val amount: Double? = null,
    val due_date: String? = null
)

data class DescriptionItem(
    val service_name: String,
    val quantity: Int,
    val price_per_unit: Double,
    val total: Double
)


