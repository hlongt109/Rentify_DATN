package com.rentify.user.app.viewModel.UserViewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rentify.user.app.model.ServiceFees.ServiceFeesItem
import com.rentify.user.app.model.UserResponse
import com.rentify.user.app.network.RetrofitService
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val _user = MutableLiveData<UserResponse?>()
    val user: LiveData<UserResponse?> = _user
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error
    private val apiService = RetrofitService().ApiService
    private val _updateSuccess = MutableLiveData<Boolean>()
    val updateSuccess: LiveData<Boolean> = _updateSuccess
    private val _serviceFees = MutableLiveData<List<ServiceFeesItem>?>()
    val serviceFees: LiveData<List<ServiceFeesItem>?> = _serviceFees
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
    fun updateUserDetails(userId: String, gender: String, dob: String, address: String) {
        viewModelScope.launch {
            val userDetails = mapOf(
                "gender" to gender,
                "dob" to dob,
                "address" to address
            )

            try {
                val response = apiService.updateUserInfo(userId, userDetails)
                if (response.isSuccessful && response.body() != null) {
                    _updateSuccess.postValue(true) // Báo thành công
                    _user.postValue(response.body()) // Cập nhật dữ liệu người dùng
                    Log.d("UserViewModel", "Updated user: ${response.body()}")
                } else {
                    _updateSuccess.postValue(false) // Báo lỗi
                    _error.postValue("Cập nhật thất bại: ${response.message()}")
                    Log.e("UserViewModel", "Response error: ${response.message()}")
                }
            } catch (e: Exception) {
                _updateSuccess.postValue(false) // Báo lỗi
                _error.postValue("Lỗi khi cập nhật: ${e.message}")
                Log.e("UserViewModel", "Exception: ${e.message}")
            }
        }
    }
    // viết tiếp
    fun getServiceFeesByUser(userId: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getServiceFeesByUser(userId)  // Gọi API với userId
                if (response.isSuccessful && response.body() != null) {
                    _serviceFees.postValue(response.body())  // Cập nhật LiveData với danh sách phí dịch vụ
                    Log.d("UserViewModel", "Service fees: ${response.body()}")
                } else {
                    _error.postValue("Không thể lấy phí dịch vụ: ${response.message()}")
                    Log.e("UserViewModel", "Error response: ${response.message()}")
                }
            } catch (e: Exception) {
                _error.postValue("Lỗi: ${e.message}")
                Log.e("UserViewModel", "Exception: ${e.message}")
            }
        }
    }
}
