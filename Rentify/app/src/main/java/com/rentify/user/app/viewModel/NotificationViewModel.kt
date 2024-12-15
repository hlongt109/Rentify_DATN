package com.rentify.user.app.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rentify.user.app.model.Model.BookingRequest
import com.rentify.user.app.model.Model.NotificationRequest
import com.rentify.user.app.network.ApiStaff.RetrofitStaffService
import com.rentify.user.app.network.RetrofitClient
import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.NotificationRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class NotificationViewModel : ViewModel() {
    private val apiService = RetrofitService().ApiService
    private val notificationRepository = NotificationRepository(apiService)

    private val _createNotificationResult = MutableSharedFlow<Result<NotificationRequest>>()
    val createNotificationResult = _createNotificationResult.asSharedFlow()

    fun createNotification(notificationRequest: NotificationRequest) {
        viewModelScope.launch {
            try {
                val response = notificationRepository.createNotification(notificationRequest)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _createNotificationResult.emit(Result.success(it))
                    } ?: run {
                        _createNotificationResult.emit(Result.failure(Exception("Response is empty")))
                    }
                } else {
                    _createNotificationResult.emit(Result.failure(Exception(response.message())))
                }
            } catch (e: Exception) {
                _createNotificationResult.emit(Result.failure(e))
            }
        }
    }
}