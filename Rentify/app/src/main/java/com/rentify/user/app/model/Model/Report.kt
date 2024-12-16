package com.rentify.user.app.model.Model

data class ReportRequest(
    val user_id: String,
    val type: String,
    val id_problem: String,
    val title_support: String,
    val content_support: String
)
