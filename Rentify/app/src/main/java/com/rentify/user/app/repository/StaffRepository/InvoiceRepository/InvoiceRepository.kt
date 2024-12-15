package com.rentify.user.app.repository.StaffRepository.InvoiceRepository


import com.rentify.user.app.model.Model.UpdateInvoice
import com.rentify.user.app.network.ApiStaff.ApiServiceStaff
import com.rentify.user.app.network.ApiStaff.RetrofitStaffService
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response


class InvoiceRepository(
    private val api: ApiServiceStaff = RetrofitStaffService.ApiService
){

//    suspend fun getDetailInvoice(invoiceId: String): Response<UpdateInvoice> {
//        return api.getDetailInvoice(invoiceId)
//    }

    suspend fun getListInvoice(staffId: String): Result<InvoiceResponse> {
        return try {
            val response = api.listInvoiceStaff(staffId)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it) // trả về dữ liệu thành công
                } ?: Result.failure(Exception("Dữ liệu trả về rỗng"))
            } else {
                // Parse error body để lấy thông tin message
                val errorBody = response.errorBody()?.string()
                val message = errorBody?.let {
                    try {
                        JSONObject(it).getString("message") // Lấy giá trị trường "message"
                    } catch (e: JSONException) {
                        "Lỗi không xác định" // Trường hợp không parse được JSON
                    }
                } ?: "Lỗi không xác định"
                Result.failure(Exception(message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun addBillStaff(invoice: InvoiceAdd): Result<InvoiceResponse> {
        return try {
            val response = api.addInvoice(invoice)
            if (response.isSuccessful) {
                response.body()?.let { invoiceResponse ->
                    Result.success(invoiceResponse)
                } ?: Result.failure(Exception("Dữ liệu trả về rỗng"))
            } else {
                // Parse error body để lấy thông tin trong trường "message"
                val errorBody = response.errorBody()?.string()
                val message = errorBody?.let {
                    try {
                        JSONObject(it).getString("message")
                    } catch (e: JSONException) {
                        "Lỗi không xác định"
                    }
                } ?: "Lỗi không xác định"
                Result.failure(Exception(message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    suspend fun confirmPaidStaff(invoiceId: String): Result<InvoiceResponse> {
        return try {
            val statusUpdate = InvoiceConfirmPaid(payment_status = "paid")
            val response = api.confirmPaidInvoice(invoiceId, statusUpdate)
            if (response.isSuccessful) {
                response.body()?.let { invoiceResponse ->
                    Result.success(invoiceResponse)
                } ?: Result.failure(Exception("Dữ liệu trả về rỗng"))
            } else {
                // Parse error body để lấy thông tin trong trường "message"
                val errorBody = response.errorBody()?.string()
                val message = errorBody?.let {
                    try {
                        JSONObject(it).getString("message")
                    } catch (e: JSONException) {
                        "Lỗi không xác định"
                    }
                } ?: "Lỗi không xác định"
                Result.failure(Exception(message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}

