package com.rentify.user.app.repository.LoginRepository

import com.rentify.user.app.network.RetrofitService
import retrofit2.Response

class LoginRepository(private val apiService: RetrofitService){
    //goi api dang nhap
    suspend fun login(email: String, password: String): Response<ApiResponse>{
        val loginRequest = LoginRequest(email, password)
        return apiService.ApiService.LoginUser(loginRequest)
    }

    suspend fun getInfoUser(userId: String): Response<ApiResponse>{
        return apiService.ApiService.getInfoUser(userId)
    }
}