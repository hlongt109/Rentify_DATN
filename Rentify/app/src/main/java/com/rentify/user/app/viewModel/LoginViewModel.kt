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
import com.google.firebase.database.FirebaseDatabase
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
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _successRole = MutableLiveData<String>()
    val successRole: LiveData<String> = _successRole
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
                            MainActivity.ROUTER.BottomTest.name
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
        _errorMessage.postValue("")
        _successMessage.postValue("")
        _isLoading.postValue(true)
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

                // Log toàn bộ response để kiểm tra
                Log.d("LoginDebug", "Response: $response")
                Log.d("LoginDebug", "Response body: ${response.body()}")

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val result = responseBody.data
                        if (result != null) {
                            if (result.verified != true) {
                                _errorMessage.postValue("Tài khoản chưa được xác minh. Vui lòng kiểm tra email.")
                            } else {
                                // Đăng nhập thành công
                                _isLoading.postValue(false)
                                _userData.postValue(result)
                                _isLoggedIn.postValue(true)
                                saveUserData(result)
//                                saveUserDataToFirestore(result)
                                result.role?.let {role ->
                                    _successRole.postValue(role)
                                    _successMessage.postValue("Đăng nhập thành công")
                                } ?: run{
                                    Log.e("LoginError", "Role is null")
                                    _errorMessage.postValue("Không thể xác định vai trò người dùng")
                                }

                            }
                        } else {
                            Log.e("LoginError", "Response data is null")
                            _errorMessage.postValue("Không thể lấy thông tin đăng nhập")
                        }
                    } else {
                        Log.e("LoginError", "Response body is null")
                        _errorMessage.postValue("Không thể lấy dữ liệu đăng nhập")
                    }
                } else {
                    Log.e("LoginError", "Response not successful: ${response.code()}")
                    val errorBody = response.errorBody()?.string() // Lấy nội dung lỗi từ server
                    Log.e("LoginDebug", "Error body: $errorBody")

                    if (response.code() == 400) {
                        _errorMessage.postValue("Tài khoản hoặc mật khẩu không đúng.")
                    } else if (response.code() == 404) {
                        _errorMessage.postValue("Tài khoản không tồn tại.")
                    } else if(response.code() == 401){
                        _errorMessage.postValue("Tài khoản chưa được xác minh. Vui lòng kiểm tra email.")
                    }else {
                        _errorMessage.postValue("Đăng nhập thất bại. Mã lỗi: ${response.code()}")
                    }
                }
            } catch (e: Exception) {
                Log.e("LoginError", "Exception during login", e)
                _errorMessage.postValue("Đăng nhập thất bại: ${e.message}")
            }
        }
    }

    fun saveUserDataToFirestore(user: LoginResponse) {
        val database = FirebaseDatabase.getInstance()
        val userRef = database.getReference("users")
        //tao map tu user
        val userMap = mapOf(
            "id" to user._id,
            "email" to user.email,
            "name" to user.name,
            "avatar" to user.profile_picture_url,
            "isOnline" to true,
            "lastLogin" to System.currentTimeMillis()
        )

        //luu du lieu vao realtime database
        userRef.child(user._id).setValue(userMap)
            .addOnSuccessListener {

            }
            .addOnFailureListener { exception ->
                Log.e("SaveUserData", "Lỗi khi lưu thông tin người dùng", exception)
            }
    }

    fun clearEmailError() {
        _errorEmail.value = null
    }

    fun clearPasswordError() {
        _errorPass.value = null
    }

    fun clearError(){
        _errorMessage.value = null
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
            putString("ProfilePictureUrl", userData.profile_picture_url)
            putString("CreatedAt", userData.createdAt)
            putString("UpdatedAt", userData.updatedAt)
            putString("Username", userData.username)
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
            preferences.getString("Username", "") ?: "",
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
        val usename :String
    )

    private val _userDataMap = MutableLiveData<Map<String, LoginResponse>>(emptyMap())
    val userDataMap: LiveData<Map<String, LoginResponse>> get() = _userDataMap
    
    fun getInfoUser(userId: String){
        viewModelScope.launch {
            val response = userRepository.getInfoUser(userId)
            if(response.isSuccessful){
                val responseBody = response.body()
                if(responseBody != null){
                    val result = responseBody.data
                    Log.d("CheckResult", "getInfoUser: ${responseBody.data}")
                    if(result!= null){
                        _userDataMap.value = _userDataMap.value.orEmpty() + (userId to result)
                    }
                }
            }
        }
    }


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
