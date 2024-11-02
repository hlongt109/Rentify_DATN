package com.rentify.user.app.network

import com.rentify.user.app.repository.LoginRepository.ApiResponse
import com.rentify.user.app.repository.LoginRepository.LoginRequest
import com.rentify.user.app.repository.LoginRepository.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface APIService {

    // dang nhap
    @POST("login-user")
    suspend fun LoginUser(@Body loginRequest: LoginRequest): Response<ApiResponse>
}