
package com.rentify.user.app.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.rentify.user.app.repository.LoginRepository.LoginRepository
import com.rentify.user.app.repository.LoginRepository.LoginResponse
import com.rentify.user.app.repository.RegisterRepository.RegisterRepository
import com.rentify.user.app.utils.CheckUnit
import kotlinx.coroutines.launch
class RegisterViewModel(private val userRepository: RegisterRepository) : ViewModel() {

    private val _errorName = MutableLiveData<String?>()
    val errorName: LiveData<String?> = _errorName

    private val _errorEmail = MutableLiveData<String?>()
    val errorEmail: LiveData<String?> = _errorEmail

    private val _errorPass = MutableLiveData<String?>()
    val errorPass: LiveData<String?> = _errorPass

    private val _errorPhone = MutableLiveData<String?>()
    val errorPhone: LiveData<String?> = _errorPhone
    // LiveData cho sự kiện đăng ký thành công
    private val _successMessage = MutableLiveData<String>()
    val successMessage: LiveData<String> = _successMessage

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun register(name: String, email: String, password: String, repassword: String, phoneNumber: String, onSucess:() -> Unit) {
        // Reset thông báo lỗi trước khi kiểm tra
        _errorMessage.value = null

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || repassword.isEmpty()) {
            when {
                name.isEmpty() -> _errorName.postValue("Vui lòng nhập tên của bạn")
                email.isEmpty() -> _errorEmail.postValue("Vui lòng nhập email")
                phoneNumber.isEmpty() -> _errorPhone.postValue("Vui lòng nhập số điện thoại")
                !CheckUnit.isValidEmail(email) -> _errorEmail.postValue("Email không hợp lệ")
                password.isEmpty() -> _errorPass.postValue("Vui lòng nhập mật khẩu")
                password != repassword -> _errorPass.postValue("Mật khẩu và mật khẩu nhập lại không khớp")
            }
            return
        }

        viewModelScope.launch {
            _isLoading.postValue(true)
            try {
                val response = userRepository.registerUser(name, email, password, phoneNumber)

                // Log toàn bộ response để debug
                Log.d("RegisterDebug", "Response: $response")
                Log.d("RegisterDebug", "Response body: ${response.body()}")

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val result = responseBody.data
                        if (result != null) {
                            Log.d("RegisterSuccess", "Username: ${result.name}, Role: ${result.role}")
                            _successMessage.postValue("Đăng ký thành công. Hãy xác nhận tài khoản email của bạn")
                            onSucess()
                        } else {
                            Log.e("RegisterError", "Response data is null")
                            _isLoading.postValue(false)
                            _errorMessage.postValue("Không thể lấy thông tin người dùng")
                        }
                    } else {
                        Log.e("RegisterError", "Response body is null")
                        _errorMessage.postValue("Không thể lấy dữ liệu đăng ký")
                    }
                } else {
                    Log.e("RegisterError", "Response not successful: ${response.code()}")
                    _errorMessage.postValue("Đăng ký thất bại. Mã lỗi: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("RegisterError", "Exception during registration", e)
                _errorMessage.postValue("Đăng ký thất bại: ${e.message}")
            }
        }
    }

    fun clearEmailError() {
        _errorEmail.value = null
    }

    fun clearPasswordError() {
        _errorPass.value = null
    }

    class RegisterViewModelFactory(private val userRepository: RegisterRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return RegisterViewModel(userRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
