package com.rentify.user.app.repository.StaffRepository.InvoiceRepository

import com.rentify.user.app.network.ApiStaff.ApiServiceStaff
import com.rentify.user.app.network.ApiStaff.RetrofitStaffService

class InvoiceRepository(
    private val api: ApiServiceStaff = RetrofitStaffService.ApiService
){
    suspend fun getListInvoice(staffId: String): Result<InvoiceResponse>{
        return try {
            //goi api
            val response = api.listInvoiceStaff(staffId)
            if (response.isSuccessful){
                response.body()?.let {
                    Result.success(it)//tra ve neu thanh cong
                } ?: Result.failure(Exception("Response body is null"))
            }else{
                Result.failure(Exception("Error: ${response.code()}"))
            }
        }catch(e: Exception){
            Result.failure(e)
        }
    }

    suspend fun addBillStaff(invoice: InvoiceAdd): Result<InvoiceResponse>{
        return try {
            val response = api.addInvoice(invoice)
            if(response.isSuccessful){
                response.body()?.let { invoiceResponse ->
                    Result.success(invoiceResponse)
                } ?: Result.failure(Exception("Response body is null"))
            }else{
                Result.failure(Exception("Error: ${response.code()}"))
            }
        }catch(e: Exception){
            Result.failure(e)
        }
    }
}
