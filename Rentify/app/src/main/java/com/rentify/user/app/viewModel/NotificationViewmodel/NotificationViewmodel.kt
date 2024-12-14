package com.rentify.user.app.viewModel.NotificationViewmodel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rentify.user.app.model.Notification.Notification
import com.rentify.user.app.network.RetrofitService
import kotlinx.coroutines.launch

class NotificationViewmodel : ViewModel() {

    private val _notifications = MutableLiveData<Notification>()
    val notifications: LiveData<Notification> = _notifications

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val apiService = RetrofitService().ApiService

    fun getByUserNotification(userId: String) {
        viewModelScope.launch {
            try {
                val response = apiService.GetNotification(userId)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _notifications.postValue(it)
                    } ?: run {
                        _error.postValue("Không có dữ liệu từ server.")
                    }
                } else {
                    _error.postValue("Lỗi từ server: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                _error.postValue("Lỗi kết nối: ${e.message}")
            }
        }
    }
}