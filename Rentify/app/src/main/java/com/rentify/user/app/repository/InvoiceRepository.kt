package com.rentify.user.app.repository

import com.rentify.user.app.model.Model.InvoiceResponse
import com.rentify.user.app.network.APIService
import retrofit2.Response

class InvoiceRepository (private val apiService: APIService) {
    suspend fun getListInvoice(user_id: String, status: String) : Response<List<InvoiceResponse>> {
        return apiService.getInvoicesByStatus(user_id, status)
    }
}