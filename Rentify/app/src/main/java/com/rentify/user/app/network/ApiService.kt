package com.rentify.user.app.network

import com.rentify.user.app.repository.LoginRepository.ApiResponse
import com.rentify.user.app.repository.LoginRepository.LoginRequest
import retrofit2.Response
import com.rentify.user.app.model.User
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call


data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String,
)

data class RegisterResponse(
    val message: String,
    val user: User
)

////
interface APIService {
    // dang nhap
    @POST("login-user")
    suspend fun LoginUser(@Body loginRequest: LoginRequest): Response<ApiResponse>
    //phong bao to 123456789
    @POST("register")
    fun registerUser(@Body request: RegisterRequest): Call<RegisterResponse>
}