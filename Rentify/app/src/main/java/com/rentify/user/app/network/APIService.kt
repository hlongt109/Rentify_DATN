package com.rentify.user.app.network

import com.rentify.user.app.repository.LoginRequest
import com.rentify.user.app.repository.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST


interface APIService {

    // dang nhap
    @POST("login")
    suspend fun LoginUser(@Body loginRequest: LoginRequest): LoginResponse
}