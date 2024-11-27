package com.rentify.user.app.viewModel

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.compositionLocalOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.rentify.user.app.MainActivity
import com.rentify.user.app.model.User
import com.rentify.user.app.repository.LoginRepository.LoginRepository
import com.rentify.user.app.repository.LoginRepository.LoginResponse
import com.rentify.user.app.utils.CheckUnit
import kotlinx.coroutines.launch
import kotlin.math.log

class LoginViewModel(
    private val userRepository: LoginRepository,
    private val context: Context
) : ViewModel() {

    private val _isLoggedIn = MutableLiveData<Boolean>(false)
    val isLoggedIn: LiveData<Boolean> get() = _isLoggedIn

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

    private val _initialRoute = MutableLiveData<String?>(null)
    val initialRoute: LiveData<String?> = _initialRoute

    init {
        // Kiểm tra trạng thái đăng nhập khi khởi tạo ViewModel
        checkLoginStatusAndRoute()
    }

    //luu thong tin nguoi dung
    // Tạo một SharedPreferences wrapper để xử lý null safety
    private val preferences: SafeSharedPreferences = SafeSharedPreferences(context)

    init {
        Log.d("LoginViewModel", "Initializing ViewModel")
        Log.d("LoginViewModel", "Context: $context")
        Log.d("LoginViewModel", "Preferences initialized: $preferences")
        checkLoginStatusAndRoute()
    }

    private class SafeSharedPreferences(context: Context) {
        private val prefs: SharedPreferences =
            context.getSharedPreferences("USER_INFOR", Context.MODE_PRIVATE)

        fun getBoolean(key: String, defaultValue: Boolean): Boolean {
            return try {
                prefs.getBoolean(key, defaultValue)
            } catch (e: Exception) {
                Log.e("SafeSharedPreferences", "Error getting boolean value", e)
                defaultValue
            }
        }

        fun getString(key: String, defaultValue: String): String {
            return try {
                prefs.getString(key, defaultValue) ?: defaultValue
            } catch (e: Exception) {
                Log.e("SafeSharedPreferences", "Error getting string value", e)
                defaultValue
            }
        }

        fun edit(): SharedPreferences.Editor {
            return prefs.edit()
        }
    }

    private fun checkLoginStatusAndRoute() {
        viewModelScope.launch {
            try {
                Log.d("LoginViewModel", "Starting checkLoginStatusAndRoute")
                val isLogged = preferences.getBoolean("isLogged", false)
                Log.d("LoginViewModel", "isLogged: $isLogged")

                if (isLogged) {
                    val userData = getUserData()
                    Log.d("LoginViewModel", "userData: $userData")
                    Log.d("LoginViewModel", "userData role: ${userData.role}")

                    // Xử lý role một cách linh hoạt hơn
                    val role = userData.role.lowercase().trim()
                    _isLoggedIn.value = true

                    _initialRoute.value = when {
                        role.startsWith("staff") -> {
                            Log.d("LoginViewModel", "Setting STAFF route")
                            MainActivity.ROUTER.HOME_STAFF.name
                        }

                        role == "user" -> {
                            Log.d("LoginViewModel", "Setting USER route")
                            MainActivity.ROUTER.HOME.name
                        }

                        else -> {
                            Log.d("LoginViewModel", "Unknown role: $role")
                            null
                        }
                    }

                    Log.d("LoginViewModel", "Final route set to: ${_initialRoute.value}")
                } else {
                    Log.d("LoginViewModel", "User not logged in")
                    _isLoggedIn.value = false
                    _initialRoute.value = null
                }
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Error in checkLoginStatusAndRoute", e)
                _isLoggedIn.value = false
                _initialRoute.value = null
            }
        }
    }


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

                                result.email != email && result.password != password -> {
                                    _errorMessage.postValue("Tài khoản hoặc mật khẩu không đúng.")
                                }

                                else -> {
                                    // Đăng nhập thành công
                                    _userData.postValue(result)
                                    _isLoggedIn.postValue(true)
                                    saveUserData(result)
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

    private fun saveUserData(userData: LoginResponse) {
        preferences.edit()?.apply {
            putString("UserId", userData._id)
            putString("Email", userData.email)
            putString("Password", userData.password)
            putString("Role", userData.role)
            putString("Name", userData.name)
            putBoolean("Verified", userData.verified)
            putBoolean("isLogged", true)
            putString("PhoneNumber", userData.phoneNumber)
            putString("DOB", userData.dob)
            putString("Gender", userData.gender)
            putString("Address", userData.address)
            putString("ProfilePictureUrl", userData.profilePictureUrl)
            putString("CreatedAt", userData.createdAt)
            putString("UpdatedAt", userData.updatedAt)
            apply()
        }
        checkLoginStatusAndRoute()
    }

    fun logout() {
        preferences.edit()?.apply {
            clear()
            apply()
        }
        _isLoggedIn.postValue(false)
    }

    fun getUserData(): UserData {
        return UserData(
            preferences.getString("UserId", "") ?: "",
            preferences.getString("Email", "") ?: "",
            preferences.getString("Password", "") ?: "",
            preferences.getString("Role", "") ?: "",
            preferences.getString("Name", "") ?: "",
            preferences.getBoolean("Verified", false),
            preferences.getString("PhoneNumber", "") ?: "",
            preferences.getString("DOB", "") ?: "",
            preferences.getString("Gender", "") ?: "",
            preferences.getString("Address", "") ?: "",
            preferences.getString("ProfilePictureUrl", "") ?: ""
        )
    }

    data class UserData(
        val userId: String,
        val email: String,
        val password: String,
        val role: String,
        val name: String,
        val verified: Boolean,
        val phoneNumber: String,
        val dob: String,
        val gender: String,
        val address: String,
        val profilePictureUrl: String,
    )


    class LoginViewModelFactory(
        private val userRepository: LoginRepository,
        private val context: Context
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LoginViewModel(userRepository, context) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
