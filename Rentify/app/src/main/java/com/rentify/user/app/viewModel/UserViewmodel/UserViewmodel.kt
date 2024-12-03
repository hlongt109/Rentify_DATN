package com.rentify.user.app.viewModel.UserViewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rentify.user.app.model.User
import com.rentify.user.app.model.UserResponse
import com.rentify.user.app.network.RetrofitService
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val _user = MutableLiveData<UserResponse?>()
    val user: LiveData<UserResponse?> = _user
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error
    private val apiService = RetrofitService().ApiService

    
    // Hàm lấy thông tin người dùng theo _id (MongoDB _id)
    fun getUserDetailById(userId: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getUserDetail(userId)  // Gọi API với _id
                if (response.isSuccessful && response.body() != null) {
                    _user.postValue(response.body())  // Cập nhật LiveData với thông tin người dùng
                    Log.d("UserViewModel", "User data: ${response.body()}")
                } else {
                    _error.postValue("Không thể lấy thông tin người dùng: ${response.message()}")
                    Log.e("UserViewModel", "Error response: ${response.message()}")
                }
            } catch (e: Exception) {
                _error.postValue("Lỗi: ${e.message}")
                Log.e("UserViewModel", "Exception: ${e.message}")
            }
        }
    }
}
