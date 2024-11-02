package com.rentify.user.app.repository

import com.rentify.user.app.network.APIService
import com.rentify.user.app.network.RetrofitService

data class LoginRequest(
    val username: String,
    val password: String
)

data class LoginResponse(
//    val token: String, // JWT hoặc token nào đó được trả về khi đăng nhập thành công
    val userId: String,
    val username: String,
    val password: String,
    val email: String,
    val phoneNumber: String,
    val role: String, // vai trò (admin, user, nhan vien, . ..)
    val name: String,
    val dob: String, // ngày sinh
    val gender: String, // giới tính
    val address: String,
    val profilePictureUrl: String,
    val verified: Boolean,
    val createdAt: String,
    val updatedAt: String
)

class LoginRepository(){
    //goi api dang nhap
    private val api: APIService = RetrofitService().ApiService
    suspend fun Login(username: String, password: String): LoginResponse{
        val loginRequest = LoginRequest(username, password)
        return api.LoginUser(loginRequest)
    }
}