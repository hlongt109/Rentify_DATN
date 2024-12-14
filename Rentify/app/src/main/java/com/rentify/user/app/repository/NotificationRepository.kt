package com.rentify.user.app.repository

import com.rentify.user.app.model.Model.NotificationRequest
import com.rentify.user.app.network.APIService
import com.rentify.user.app.network.ApiStaff.ApiServiceStaff
import retrofit2.Response

class NotificationRepository ( private val apiService: APIService) {
    suspend fun createNotification(notificationRequest: NotificationRequest): Response<NotificationRequest> {
        return apiService.createNotification(notificationRequest)
    }
}