package com.rentify.user.app.network

import com.rentify.user.app.repository.LoginRepository.ApiResponse
import com.rentify.user.app.repository.LoginRepository.LoginRequest
import retrofit2.Response
import com.rentify.user.app.model.User
import com.rentify.user.app.repository.LoginRepository.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call




////
interface APIService {
    // dang nhap
    @POST("login-user")
    suspend fun LoginUser(@Body loginRequest: LoginRequest): Response<ApiResponse>
    //phong bao to 123456789
    // Đăng ký
    @POST("register-user")
    suspend fun registerUser(@Body request: RegisterRequest): Response<ApiResponse>
}