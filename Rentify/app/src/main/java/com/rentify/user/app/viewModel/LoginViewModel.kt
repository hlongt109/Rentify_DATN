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
                if(response.isSuccessful){
                    if(response.body() != null){
                        val result = response.body()?.data!!
                        Log.d("UserData", "login: ${response.body()}")
                        // Kiểm tra xem tài khoản đã được xác minh chưa
                        if (result.verified == true) {
                            // Nếu tài khoản đã được xác minh, kiểm tra xem thông tin đăng nhập có đúng không
                            if (result.email == email && result.password == password) {
                                // Đăng nhập thành công
                                _userData.postValue(result)
                                Log.d("UserData", "login: $_userData")
                                _successMessage.postValue(result.role)// Gửi thông báo thành công

                            } else {
                                // Tài khoản hoặc mật khẩu không đúng
                                _errorMessage.postValue("Tài khoản hoặc mật khẩu không đúng.")
                            }
                        } else {
                            // Tài khoản chưa được xác minh
                            _errorMessage.postValue("Tài khoản chưa được xác minh. Vui lòng kiểm tra email để xác minh tài khoản của bạn.")
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("ErrorApi", "Login: ${e}")
                _errorMessage.postValue("Đăng nhập thất bại. Vui lòng thử lại.")
            }
        }
    }

    fun clearEmailError() {
        _errorEmail.value = null
    }

    fun clearPasswordError() {
        _errorPass.value = null
    }
    class LoginViewModelFactory(private val userRepository: LoginRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>) : T{
            if(modelClass.isAssignableFrom(LoginViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return LoginViewModel(userRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
