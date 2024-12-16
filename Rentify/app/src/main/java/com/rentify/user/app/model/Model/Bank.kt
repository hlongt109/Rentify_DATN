package com.rentify.user.app.model.Model

data class Bank(
    val bank_name: String = "Chưa xác định",
    val bank_number: Long = 0L,
    val qr_bank: List<String> = emptyList(),
    val username: String = "Chưa xác định"
)