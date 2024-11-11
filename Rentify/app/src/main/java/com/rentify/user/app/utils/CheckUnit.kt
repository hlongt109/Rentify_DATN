package com.rentify.user.app.utils

import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Currency
import java.util.Date
import java.util.Locale

object CheckUnit {
     fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
        return email.matches(emailRegex.toRegex())
    }

    fun formattedPrice(price: Float): String {
        // Định dạng số tiền theo chuẩn của Việt Nam (VND)
        return NumberFormat.getCurrencyInstance(Locale("vi", "VN")).apply {
            currency = Currency.getInstance("VND")
        }.format(price)
    }

    fun formatYear(date: Date): String {
        val format = SimpleDateFormat("yyyy", Locale.getDefault()) // Chỉ định định dạng năm
        return format.format(date)
    }
    fun formatMonth(date: Date): String {
        val format = SimpleDateFormat("MM/yyyy", Locale.getDefault()) // Chỉ định định dạng năm
        return format.format(date)
    }
}