package com.rentify.user.app.repository.ForgotRepository

import com.rentify.user.app.network.APIService
import com.rentify.user.app.network.RetrofitService
import org.json.JSONException
import org.json.JSONObject

class ForgotRepository(
    private val apiService: APIService = RetrofitService().ApiService
){
    suspend fun mailForgot(email: String): Result<ForgotResponse> {
        return try {
            val request = ForgotRequest(email)
            val response = apiService.postMailForgot(request)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("Phản hồi từ server không hợp lệ"))
                }
            } else {
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

    suspend fun confirmCode(email: String, code: String):Result<ForgotResponse>{
        return try {
            val confirmCode = MailConfirmForgot(email, code)
            val response = apiService.confirmCode(confirmCode)
            if(response.isSuccessful){
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("Phản hồi từ server không hợp lệ"))
                }
            }else{
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
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    suspend fun resetPassword(email: String, password: String):Result<ForgotResponse>{
        return try {
            val resetPassword = ResetPassword(email, password)
            val response = apiService.resetPassword(resetPassword)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("Phản hồi từ server không hợp lệ"))
                }
            } else {
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
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}