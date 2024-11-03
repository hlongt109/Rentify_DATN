package com.rentify.user.app.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.rentify.user.app.repository.LoginRepository.LoginRepository
import com.rentify.user.app.repository.LoginRepository.LoginResponse
import com.rentify.user.app.utils.CheckUnit
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: LoginRepository) : ViewModel() {
    private val _userData = MutableLiveData<LoginResponse>()
    val userData: LiveData<LoginResponse> get() = _userData

    // LiveData cho thông báo lỗi
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    //email
    private val _errorEmail = MutableLiveData<String?>()
    val errorEmail: LiveData<String?> = _errorEmail

    //password
    private val _errorPass = MutableLiveData<String?>()
    val errorPass: LiveData<String?> = _errorPass

    // LiveData cho sự kiện đăng nhập thành công
    private val _successMessage = MutableLiveData<String>()
    val successMessage: LiveData<String> = _successMessage

    fun login(email: String, password: String) {
        // Reset thông báo lỗi trước khi kiểm tra
        _errorMessage.value = null

        if (email.isEmpty() || password.isEmpty()) {
            when {
                email.isEmpty() -> _errorEmail.postValue("Vui lòng nhập email")
                !CheckUnit.isValidEmail(email) -> _errorEmail.postValue("Email không hợp lệ")
                password.isEmpty() -> _errorPass.postValue("Vui lòng nhập mật khẩu")
            }
            return
        }
        viewModelScope.launch {
            try {
                val response = userRepository.login(email, password)

                // Log toàn bộ response để debug
                Log.d("LoginDebug", "Response: $response")
                Log.d("LoginDebug", "Response body: ${response.body()}")

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val result = responseBody.data
                        if (result != null) {
                            // Log chi tiết thông tin user
                            Log.d(
                                "LoginDebug", """
                                User Info:
                                Email: ${result.email}
                                Verified: ${result.verified}
                                Role: ${result.role}
                            """.trimIndent()
                            )

                            when {
                                result.verified != true -> {
                                    _errorMessage.postValue("Tài khoản chưa được xác minh. Vui lòng kiểm tra email để xác minh tài khoản của bạn.")
                                }

                                result.email != email  && result.password != password-> {
                                    _errorMessage.postValue("Tài khoản hoặc mật khẩu không đúng.")
                                }

                                else -> {
                                    // Đăng nhập thành công
                                    _userData.postValue(result)
                                    result.role?.let { role ->
                                        _successMessage.postValue(role)
                                        Log.d("LoginSuccess", "Role: $role")
                                    } ?: run {
                                        Log.e("LoginError", "Role is null")
                                        _errorMessage.postValue("Không thể xác định vai trò người dùng")
                                    }
                                }
                            }
                        } else {
                            Log.e("LoginError", "Response data is null")
                            _errorMessage.postValue("Không thể lấy thông tin người dùng")
                        }
                    } else {
                        Log.e("LoginError", "Response body is null")
                        _errorMessage.postValue("Không thể lấy dữ liệu đăng nhập")
                    }
                } else {
                    Log.e("LoginError", "Response not successful: ${response.code()}")
                    _errorMessage.postValue("Đăng nhập thất bại. Mã lỗi: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("LoginError", "Exception during login", e)
                _errorMessage.postValue("Đăng nhập thất bại: ${e.message}")
            }
        }
    }

    fun clearEmailError() {
        _errorEmail.value = null
    }

    fun clearPasswordError() {
        _errorPass.value = null
    }

    class LoginViewModelFactory(private val userRepository: LoginRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LoginViewModel(userRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
