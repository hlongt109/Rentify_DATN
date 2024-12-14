package com.rentify.user.app.repository.NotificationRepository

import com.rentify.user.app.network.ApiStaff.RetrofitStaffService
import com.rentify.user.app.network.RetrofitService

class NotificationRepository{
    private val apiService = RetrofitService().ApiService
    suspend fun createNotification(userId: String, title: String, content: String): Result<NotificationResponse> {
        return try {
            val request = NotificationRequest(userId, title, content)
            val response = apiService.createNotification(request)

            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.errorBody()?.string() ?: "Unknown error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}