package com.rentify.user.app.viewModel.UserViewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.rentify.user.app.repository.ForgotRepository.ForgotRepository
import com.rentify.user.app.repository.SupportRepository.SupportRepository
import com.rentify.user.app.utils.CheckUnit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class ForgotPasswordUiState {
    object Loading : ForgotPasswordUiState()
    data class Success(val message: String) : ForgotPasswordUiState()
    data class Error(val message: String) : ForgotPasswordUiState()
}

class ForgotPasswordViewModel(
    private val repository: ForgotRepository = ForgotRepository()
) : ViewModel() {
    private val _uiState = MutableStateFlow<ForgotPasswordUiState>(ForgotPasswordUiState.Loading)
    val uiState: StateFlow<ForgotPasswordUiState> = _uiState.asStateFlow()

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _successMessage = MutableLiveData<String?>()
    val successMessage: LiveData<String?> = _successMessage

    private val _successMessageMail = MutableLiveData<String?>()
    val successMessageMail: LiveData<String?> = _successMessageMail

    private val _successMessageConfirm = MutableLiveData<String?>()
    val successMessageConfirm: LiveData<String?> = _successMessageConfirm

    private val _errorEmail = MutableLiveData<String?>()
    val errorEmail: LiveData<String?> = _errorEmail

    private val _errorCode = MutableLiveData<String?>()
    val errorCode: LiveData<String?> = _errorCode

    private val _errorPassword = MutableLiveData<String?>()
    val errorPassword: LiveData<String?> = _errorPassword

    private val _errorConfirmPassword = MutableLiveData<String?>()
    val errorConfirmPassword: LiveData<String?> = _errorConfirmPassword

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun postEmail(email: String, onSuccess: () -> Unit) {

        if (email.isBlank()) {
            _errorEmail.value = "Vui lòng nhập email"
            return
        }
        if (!CheckUnit.isValidEmail(email)) {
            _errorEmail.value = ("Email sai định dạng")
            return
        }
        viewModelScope.launch {
            _isLoading.postValue(true)
            repository.mailForgot(email).fold(
                onSuccess = { response ->
                    if (response.status == 200) {
                        _isLoading.postValue(false)
                        _uiState.value = ForgotPasswordUiState.Success(response.message)
                        _successMessageMail.value = response.message
                        Log.d(
                            "CheckMessage",
                            "postEmail: ${response.message}, $_successMessageMail"
                        )
                        onSuccess()
                        clearError()
                    }
                },
                onFailure = { exception ->
                    _isLoading.postValue(false)
                    _uiState.value =
                        ForgotPasswordUiState.Error(exception.message ?: "Lỗi không xác định")
                    _errorMessage.value = exception.message ?: "Lỗi không xác định"
                }
            )
        }
    }

    fun confirmCode(email: String, code: String, onSuccess: () -> Unit) {
        // Kiểm tra mã xác nhận có rỗng không
        if (email.isBlank()) {
            _errorEmail.value = ("Vui lòng nhập email")
            return
        }
        if (code.isBlank()) {
            _errorCode.value = ("Vui lòng nhập mã xác nhận")
            _isLoading.postValue(false)
            return
        }

        viewModelScope.launch {
            _isLoading.postValue(true)
            try {
                val result = repository.confirmCode(email, code)
                _uiState.value = ForgotPasswordUiState.Loading
                result.onSuccess { response ->
                    if (response.status == 200) {
                        _isLoading.postValue(false)
                        _successMessageConfirm.value = response.message
                        onSuccess()
                        clearError()
                    } else {
                        _isLoading.postValue(false)
                        _errorCode.value = ("Xác nhận mã không thành công.")
                    }
                }.onFailure { error ->
                    _isLoading.postValue(false)
                    _errorCode.value = (error.message ?: "Lỗi xác nhận mã.")
                }
            } catch (e: Exception) {
                _errorMessage.value = ("Đã xảy ra lỗi: ${e.message}")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun resetPassword(
        email: String,
        newPassword: String,
        confirmPassword: String,
        onSuccess: () -> Unit
    ) {
        if (newPassword.isBlank()) {
            _errorPassword.value = ("Vui lòng nhập mật khẩu mới")
            return
        }
        if (confirmPassword.isBlank()) {
            _errorConfirmPassword.value = ("Vui lòng nhập xác nhận mật khẩu")
            return
        }

        if (confirmPassword != newPassword) {
            _errorMessage.value = ("Mật khẩu và xác nhận mật khẩu không khớp")
            return
        }

        viewModelScope.launch {
            _isLoading.postValue(true)
            _uiState.value = ForgotPasswordUiState.Loading
            repository.resetPassword(email, newPassword).fold(
                onSuccess = { response ->
                    if (response.status == 200) {
                        _isLoading.postValue(false)
                        _uiState.value = ForgotPasswordUiState.Success(response.message)
                        _successMessage.value = response.message
                        onSuccess()
                        clearError()

                    }
                },
                onFailure = { exception ->
                    _isLoading.postValue(false)
                    clearError()
                    _uiState.value =
                        ForgotPasswordUiState.Error(exception.message ?: "Lỗi không xác định")
                    _errorMessage.value = (exception.message ?: "Lỗi không xác định")
                }
            )
        }
    }

    fun clearEmail() {
        _errorEmail.postValue("")
    }

    fun clearPassword() {
        _errorPassword.postValue("")
    }

    fun clearConfirmPassword() {
        _errorConfirmPassword.postValue("")
    }

    fun clearError() {
        _errorMessage.postValue("")
    }

    fun confirmCode() {
        _errorCode.postValue("")
    }

    class ForgotPasswordViewModelFactory(
        private val repository: ForgotRepository,
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ForgotPasswordViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ForgotPasswordViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}