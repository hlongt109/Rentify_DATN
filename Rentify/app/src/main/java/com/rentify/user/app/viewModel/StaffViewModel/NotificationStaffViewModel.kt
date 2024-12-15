package com.rentify.user.app.viewModel.StaffViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.rentify.user.app.model.Model.Notification
import com.rentify.user.app.model.Model.NotificationRequest
import com.rentify.user.app.model.Model.NotificationResponse
import com.rentify.user.app.network.RetrofitClient
import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.StaffRepository.BuildingRepository.Building
import com.rentify.user.app.repository.StaffRepository.BuildingRepository.BuildingStaffRepository
import com.rentify.user.app.repository.StaffRepository.BuildingRepository.BuildingStaffResponse
import com.rentify.user.app.repository.StaffRepository.BuildingRepository.ServiceFee
import com.rentify.user.app.repository.StaffRepository.InvoiceRepository.InvoiceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NotificationViewModel() : ViewModel() {

    private val _notifications = MutableLiveData<List<Notification>>()
    val notifications: LiveData<List<Notification>> get() = _notifications

    private val _createNotificationResult = MutableLiveData<NotificationResponse>()
    val createNotificationResult: LiveData<NotificationResponse> get() = _createNotificationResult

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage
    private val _unreadCount = MutableLiveData<Int>()
    val unreadCount: LiveData<Int> get() = _unreadCount
    // Gọi API để lấy danh sách thông báo theo userId
    fun getNotificationsByUser(userId: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getNotificationsByUser(userId)
                if (response.isSuccessful) {
                    val notificationResponse = response.body()

                    // Cập nhật danh sách thông báo
                    _notifications.value = notificationResponse?.data ?: emptyList()
                    _unreadCount.value = notificationResponse?.unreadCount ?: 0
                    // Đăng nhập log để kiểm tra số lượng unread
                    Log.d("UnreadCount", "Unread notifications: ${notificationResponse?.unreadCount ?: 0}")

                } else {
                    _errorMessage.value = "Lỗi: ${response.message()}"
                    Log.e("Lỗi", response.message())
                }
            } catch (e: Exception) {
                _errorMessage.value = "Lỗi kết nối: ${e.message}"
                Log.e("Lỗi kết nối:", "Lỗi kết nối: ${e.message}")
            }
        }
    }

    fun markAllAsRead(userId: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.markAllNotificationsAsRead(userId)
                Log.d("API Response", "Response: ${response.body()}") // Log phản hồi API
                if (response.isSuccessful) {
                    val result = response.body()
                    Log.d("API Response", "Modified Count: ${result?.unreadCount}")
                    _notifications.value = _notifications.value?.map { it.copy(read_status = "read") }
                } else {
                    _errorMessage.value = "Lỗi: ${response.message()}"
                    Log.e("API Error", "Lỗi: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                _errorMessage.value = "Lỗi kết nối: ${e.message}"
                Log.e("Exception", "Lỗi: ${e.message}")
            }
        }
    }


}