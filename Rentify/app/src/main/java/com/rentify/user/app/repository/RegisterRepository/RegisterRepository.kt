package com.rentify.user.app.repository.RegisterRepository

import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.LoginRepository.ApiResponse
import com.rentify.user.app.repository.LoginRepository.RegisterRequest
import retrofit2.Response

class RegisterRepository(private val apiService: RetrofitService){
    suspend fun registerUser(username: String, email: String, password: String): Response<ApiResponse> {
        val registerRequest = RegisterRequest(username, email, password)
        return apiService.ApiService.registerUser(registerRequest)
    }

}