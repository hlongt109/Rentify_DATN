package com.rentify.user.app.view.userScreens.BillScreen

import com.rentify.user.app.model.FakeModel.PaymentDetails
import com.rentify.user.app.model.FakeModel.RoomInfo
import com.rentify.user.app.model.FakeModel.RoomPaymentInfo
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import kotlin.random.Random

class FakeData {
    // Fake data cho RoomInfo
    val roomInfo1 = RoomInfo(
        roomNumber = "101A",
        numberOfPeople = 2,
        monthlyRent = 500.0
    )

    val roomInfo2 = RoomInfo(
        roomNumber = "102B",
        numberOfPeople = 3,
        monthlyRent = 750.0
    )

    val roomInfo3 = RoomInfo(
        roomNumber = "103C",
        numberOfPeople = 1,
        monthlyRent = 400.0
    )

    // Fake data cho PaymentDetails
    val paymentDetails1 = PaymentDetails(
        roomCharge = 500.0,
        electricityCharge = 50.0,
        waterCharge = 20.0,
        serviceCharge = 30.0
    )

    val paymentDetails2 = PaymentDetails(
        roomCharge = 750.0,
        electricityCharge = 60.0,
        waterCharge = 25.0,
        serviceCharge = 35.0
    )

    val paymentDetails3 = PaymentDetails(
        roomCharge = 400.0,
        electricityCharge = 40.0,
        waterCharge = 15.0,
        serviceCharge = 25.0
    )

    // Fake data cho RoomPaymentInfo
    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    val roomPaymentInfo1 = RoomPaymentInfo(
        roomInfo = roomInfo1,
        paymentDetails = paymentDetails1,
        status = 0, // chưa thanh toán,
        invoiceDate = dateFormat.parse("10/11/2024")
    )

    val roomPaymentInfo2 = RoomPaymentInfo(
        roomInfo = roomInfo2,
        paymentDetails = paymentDetails2,
        status = 1, // đã thanh toán
        invoiceDate = dateFormat.parse("10/12/2023")
    )

    val roomPaymentInfo3 = RoomPaymentInfo(
        roomInfo = roomInfo3,
        paymentDetails = paymentDetails3,
        status = 0, // chưa thanh toán
        invoiceDate = dateFormat.parse("09/11/2024")
    )
    val roomPaymentInfo4 = RoomPaymentInfo(
        roomInfo = roomInfo3,
        paymentDetails = paymentDetails3,
        status = 1, // đã thanh toán
        invoiceDate = dateFormat.parse("09/11/2024")
    )
    val roomPaymentInfo5 = RoomPaymentInfo(
        roomInfo = roomInfo2,
        paymentDetails = paymentDetails2,
        status = 1, // đã thanh toán
        invoiceDate = dateFormat.parse("09/11/2023")
    )

    // Danh sách các dữ liệu giả để sử dụng
    val fakeRoomPayments = listOf(roomPaymentInfo1, roomPaymentInfo2, roomPaymentInfo3,roomPaymentInfo4, roomPaymentInfo5)



    // Tạo ngày ngẫu nhiên trong năm 2023 hoặc 2024

}