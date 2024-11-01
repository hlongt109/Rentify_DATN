package com.rentify.user.app.network

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



interface ApiService {
    @POST("register")
    fun registerUser(@Body request: RegisterRequest): Call<RegisterResponse>
}
