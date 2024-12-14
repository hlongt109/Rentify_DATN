package com.rentify.user.app.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rentify.user.app.repository.NotificationRepository.NotificationRepository
import kotlinx.coroutines.launch

class NotificationViewModel:ViewModel(){
    private val repository: NotificationRepository = NotificationRepository()

    fun createNotification(userId: String, title: String, content: String) {
        viewModelScope.launch {
            val result = repository.createNotification(userId, title, content)
            result.onSuccess { response ->
                println("Thông báo tạo thành công: ${response.message}")
            }.onFailure { error ->
                println("Lỗi khi tạo thông báo: ${error.message}")
            }
        }
    }
}