package com.rentify.user.app.model.Model

data class Bank(
    val bank_name: String,
    val bank_number: Long,
    val qr_bank: List<String>,
    val username: String
)