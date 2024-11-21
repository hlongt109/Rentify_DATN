package com.rentify.user.app.network.ApiStaff

import com.rentify.user.app.model.Model.Invoice
import com.rentify.user.app.repository.StaffRepository.InvoiceRepository.InvoiceResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServiceStaff{



    //get invoice cho nhan vien
    @GET("listInvoiceStaff/{staffId}")
    suspend fun listInvoiceStaff(@Path("staffId") staffId: String): Response<InvoiceResponse>
}