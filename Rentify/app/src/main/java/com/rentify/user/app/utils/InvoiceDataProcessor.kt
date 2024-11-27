package com.rentify.user.app.utils

import com.rentify.user.app.repository.StaffRepository.InvoiceRepository.Invoice
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object InvoiceDataProcessor {
    fun groupInvoicesByYearAndMonth(invoices: List<Invoice>): Map<String, Map<String, List<Invoice>>> {
        return invoices.groupBy { invoice ->
            // Lấy năm từ createdAt
            val date = parseDate(invoice.created_at ?: "")
            CheckUnit.formatYear(date)
        }.mapValues { (_, yearInvoices) ->
            // Trong mỗi năm, nhóm theo tháng
            yearInvoices.groupBy { invoice ->
                val date = parseDate(invoice.created_at ?: "")
                CheckUnit.formatMonth(date)
            }
        }
    }

    // Thêm các hàm helper khác nếu cần
    fun calculateTotalAmount(invoices: List<Invoice>): Double {
        return invoices.sumOf { it.amount }
    }

    fun getInvoicesByMonth(invoices: List<Invoice>, month: String): List<Invoice> {
        return invoices.filter { invoice ->
            val date = parseDate(invoice.created_at ?: "")
            CheckUnit.formatMonth(date) == month
        }
    }

    fun parseDate(dateString: String): Date {
        return try {
            val dateFormat = SimpleDateFormat(
                "yyyy-MM-dd",
                Locale.getDefault()
            ) // Hoặc định dạng ngày tháng tùy thuộc vào định dạng của bạn
            dateFormat.parse(dateString)
                ?: Date() // Trả về Date mặc định nếu parse không thành công
        } catch (e: Exception) {
            Date() // Trả về Date mặc định nếu gặp lỗi khi parse
        }
    }
}