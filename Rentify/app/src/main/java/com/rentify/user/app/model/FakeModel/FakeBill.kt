package com.rentify.user.app.model.FakeModel

import java.util.Date

// Thông tin chi tiết về phòng
data class RoomInfo(
    val roomNumber: String,
    val numberOfPeople: Int,
    val monthlyRent: Double,
)

// Chi tiết các khoản thanh toán
data class PaymentDetails(
    val roomCharge: Double,    // Tiền phòng
    val electricityCharge: Double,  // Tiền điện
    val waterCharge: Double,   // Tiền nước
    val serviceCharge: Double  // Tiền dịch vụ
) {
    // Tính tổng tiền cần thanh toán
    fun calculateTotal(): Double = roomCharge + electricityCharge + waterCharge + serviceCharge
}

// Class tổng hợp để chứa tất cả thông tin
data class RoomPaymentInfo(
    val roomInfo: RoomInfo,
    val paymentDetails: PaymentDetails,
    val status: Int,// Trạng thái thanh toán (0: chưa thanh toán, 1: đã thanh toán)
    val invoiceDate: Date
)
