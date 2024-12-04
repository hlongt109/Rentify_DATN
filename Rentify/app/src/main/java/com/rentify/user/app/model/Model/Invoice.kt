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
    @SerializedName("room_type")val room_type: String
)

data class UpdateInvoiceStatus(
    val message: String,
    val invoice: InvoiceResponse
)