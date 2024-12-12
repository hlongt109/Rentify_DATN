package com.rentify.user.app.viewModel.UserViewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rentify.user.app.model.Model.Bank
import com.rentify.user.app.model.Model.UpdateAccUserResponse
import com.rentify.user.app.model.Model.UpdateTaiKhoanResponse
import com.rentify.user.app.model.ResponseUser

import com.rentify.user.app.model.ServiceFees.ServiceFeesItem
import com.rentify.user.app.model.UserResponse
import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.LoginRepository.ApiResponse
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class UserViewModel : ViewModel() {
    private val _user = MutableLiveData<UserResponse?>()
    val user: LiveData<UserResponse?> = _user
    private val _userAcc = MutableLiveData<ResponseUser?>()
    val userAcc: LiveData<ResponseUser?> = _userAcc

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val apiService = RetrofitService().ApiService
    private val _updateSuccess = MutableLiveData<Boolean>()

    val updateSuccess: LiveData<Boolean> = _updateSuccess
    private val _serviceFees = MutableLiveData<List<ServiceFeesItem>?>()

    val serviceFees: LiveData<List<ServiceFeesItem>?> = _serviceFees
    private val _bankAccount = MutableLiveData<Bank?>()

    val bankAccount: LiveData<Bank?> = _bankAccount
    private val _updateTaiKhoanResponse = MutableLiveData<UpdateTaiKhoanResponse?>()

    val updateTaiKhoanResponse: LiveData<UpdateTaiKhoanResponse?> = _updateTaiKhoanResponse
    private val _updateProfilePictureSuccess = MutableLiveData<Boolean>()

    private val _updateAccUserResponse = MutableLiveData<ResponseUser?>()
    val updateAccUserResponse: LiveData<ResponseUser?> = _updateAccUserResponse

    val updateProfilePictureSuccess: LiveData<Boolean> = _updateProfilePictureSuccess
    private val _profilePictureUrl = MutableLiveData<String>()

    val profilePictureUrl: LiveData<String> = _profilePictureUrl
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

    fun getUserAccById(userId: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getInfoAcc(userId)  // Gọi API với _id
                if (response.isSuccessful && response.body() != null) {
                    // Cập nhật LiveData với thông tin người dùng
                    _userAcc.postValue(response.body()!!.data)
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

    private var _pendingUpdates = 0

    fun updateUserDetails(userId: String, gender: String, dob: String, address: String) {
        viewModelScope.launch {
            _pendingUpdates++
            val userDetails = mapOf(
                "gender" to gender,
                "dob" to dob,
                "address" to address,
            )

            try {
                val response = apiService.updateUserInfo(userId, userDetails)
                if (response.isSuccessful && response.body() != null) {
                    _user.postValue(response.body())
                    Log.d("UserViewModel", "Updated user: ${response.body()}")
                } else {
                    _error.postValue("Cập nhật thất bại: ${response.message()}")
                    Log.e("UserViewModel", "Response error: ${response.message()}")
                }
            } catch (e: Exception) {
                _error.postValue("Lỗi khi cập nhật: ${e.message}")
                Log.e("UserViewModel", "Exception: ${e.message}")
            } finally {
                _pendingUpdates--
                if (_pendingUpdates == 0) {
                    getUserDetailById(userId) // Chỉ tải lại dữ liệu khi tất cả các cập nhật hoàn tất
                }
            }
        }
    }

    // phí dịch vụ người dùng
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
    // hiển thị thông tin tài khoản
    fun getBankAccountByUser(userId: String) {
        viewModelScope.launch {
            try {
                // Gọi API để lấy thông tin tài khoản ngân hàng
                val response = apiService.getBankAccount(userId)

                if (response.isSuccessful && response.body() != null) {
                    // Nếu thành công, cập nhật LiveData với đối tượng Bank
                    _bankAccount.postValue(response.body())
                    Log.d("UserViewModel", "Bank account: ${response.body()}")
                } else {
                    // Nếu API trả về lỗi, cập nhật LiveData lỗi
                    _error.postValue("Không thể lấy thông tin tài khoản ngân hàng: ${response.message()}")
                    Log.e("UserViewModel", "Error response: ${response.message()}")
                }
            } catch (e: Exception) {
                // Nếu có lỗi xảy ra, cập nhật LiveData với thông báo lỗi
                _error.postValue("Lỗi: ${e.message}")
                Log.e("UserViewModel", "Exception: ${e.message}")
            }
        }
    }
    private val pendingUpdates = MutableLiveData(0)

    fun updateBankAccount(userId: String, bank: Bank) {
        pendingUpdates.value = pendingUpdates.value?.plus(1) // Tăng số lượng yêu cầu chờ
        viewModelScope.launch {
            try {
                val response = apiService.updateBankAccount(userId, bank)
                if (response.isSuccessful && response.body() != null) {
                    _bankAccount.postValue(response.body())
                    _updateSuccess.postValue(true)
                } else {
                    _updateSuccess.postValue(false)
                    _error.postValue("Cập nhật tài khoản ngân hàng thất bại: ${response.message()}")
                }
            } catch (e: Exception) {
                _updateSuccess.postValue(false)
                _error.postValue("Lỗi khi cập nhật tài khoản ngân hàng: ${e.message}")
            } finally {
                decrementPendingUpdatesAndRefresh(userId)
            }
        }
    }

    fun updateBankAccountWithImage(
        userId: String,
        bank: Bank,
        imageUri: Uri?,
        context: Context
    ) {
        pendingUpdates.value = pendingUpdates.value?.plus(1) // Tăng số lượng yêu cầu chờ
        viewModelScope.launch {
            try {
                if (imageUri == null) {
                    updateBankAccount(userId, bank)
                } else {
                    val contentResolver = context.contentResolver
                    val inputStream = contentResolver.openInputStream(imageUri)
                    val tempFile = File(context.cacheDir, "qr_bank_${System.currentTimeMillis()}.jpg")
                    inputStream?.use { tempFile.outputStream().use { output -> it.copyTo(output) } }

                    val imagePart = MultipartBody.Part.createFormData(
                        "qr_bank",
                        tempFile.name,
                        tempFile.readBytes().toRequestBody("image/*".toMediaTypeOrNull())
                    )

                    val response = apiService.updateBankAccountWithImage(userId, imagePart)
                    if (response.isSuccessful && response.body() != null) {
                        _bankAccount.postValue(response.body())
                        _updateSuccess.postValue(true)
                    } else {
                        _updateSuccess.postValue(false)
                        _error.postValue("Cập nhật tài khoản ngân hàng thất bại: ${response.message()}")
                    }
                }
            } catch (e: Exception) {
                _updateSuccess.postValue(false)
                _error.postValue("Lỗi khi cập nhật tài khoản ngân hàng: ${e.message}")
            } finally {
                decrementPendingUpdatesAndRefresh(userId)
            }
        }
    }

    private fun decrementPendingUpdatesAndRefresh(userId: String) {
        pendingUpdates.value = pendingUpdates.value?.minus(1)
        if (pendingUpdates.value == 0) {
            getBankAccountByUser(userId) // Load lại dữ liệu sau khi hoàn tất tất cả cập nhật
        }
    }

    fun updateTaiKhoan(id: String, updatedUser: UserResponse?) {
        viewModelScope.launch {
            try {
                val response = apiService.updateTaiKhoan(id, updatedUser)
                if (response.isSuccessful && response.body() != null) {
                    // Thành công, gọi lại API để tải dữ liệu mới
                    _updateTaiKhoanResponse.postValue(response.body())
                    getUserDetailById(id) // Lấy lại thông tin người dùng
                    Log.d("UserViewModel", "Update successful: ${response.body()}")
                } else {
                    _error.postValue("Cập nhật thất bại: ${response.message()}")
                    Log.e("UserViewModel", "Error response: ${response.message()}")
                }
            } catch (e: Exception) {
                _error.postValue("Lỗi khi cập nhật tài khoản: ${e.message}")
                Log.e("UserViewModel", "Exception: ${e.message}")
            }
        }
    }

    fun updateProfilePicture(userId: String, imageUri: Uri?, context: Context) {
        viewModelScope.launch {
            try {
                // Kiểm tra xem ảnh có hợp lệ không
                if (imageUri == null) {
                    _error.postValue("Ảnh không hợp lệ")
                    return@launch
                }

                // Chuyển đổi Uri thành File tạm thời
                val contentResolver = context.contentResolver
                val inputStream = contentResolver.openInputStream(imageUri)
                val tempFile = File(context.cacheDir, "profile_picture_${System.currentTimeMillis()}.jpg")
                inputStream?.use { tempFile.outputStream().use { output -> it.copyTo(output) } }

                // Tạo MultipartBody.Part để gửi ảnh
                val imagePart = MultipartBody.Part.createFormData(
                    "profile_picture_url",
                    tempFile.name,
                    tempFile.readBytes().toRequestBody("image/*".toMediaTypeOrNull())
                )

                // Gọi API để cập nhật ảnh hồ sơ
                val response = apiService.addImageUser(userId, imagePart)

                if (response.isSuccessful && response.body() != null) {
                    // Thành công, cập nhật thông tin ảnh
                    _updateProfilePictureSuccess.postValue(true)
                    Log.d("UserViewModel", "Cập nhật ảnh hồ sơ thành công: ${response.body()?.profile_picture_url}")
                } else {
                    _updateProfilePictureSuccess.postValue(false)
                    _error.postValue("Cập nhật ảnh thất bại: ${response.message()}")
                    Log.e("UserViewModel", "Response error: ${response.message()}")
                }
            } catch (e: Exception) {
                _updateProfilePictureSuccess.postValue(false)
                _error.postValue("Lỗi khi cập nhật ảnh hồ sơ: ${e.message}")
                Log.e("UserViewModel", "Exception: ${e.message}")
            }
        }
    }

    // hiển thị ảnh đại điện
    fun getProfilePictureById(userId: String) {
        viewModelScope.launch {
            try {
                // Gọi API để lấy ảnh hồ sơ của người dùng
                val response = apiService.getImageUser(userId)
                if (response.isSuccessful && response.body() != null) {
                    // Nếu thành công, cập nhật LiveData với URL ảnh hồ sơ
                    _profilePictureUrl.postValue(response.body()?.profile_picture_url)
                    Log.d("UserViewModel", "Profile picture URL: ${response.body()?.profile_picture_url}")
                } else {
                    // Nếu có lỗi từ API, cập nhật LiveData với thông báo lỗi
                    _error.postValue("Không thể lấy ảnh hồ sơ: ${response.message()}")
                    Log.e("UserViewModel", "Error response: ${response.message()}")
                }
            } catch (e: Exception) {
                // Nếu có lỗi xảy ra, cập nhật LiveData với thông báo lỗi
                _error.postValue("Lỗi khi lấy ảnh hồ sơ: ${e.message}")
                Log.e("UserViewModel", "Exception: ${e.message}")
            }
        }
    }
    fun updateUserAccount(id: String, updateUser: ResponseUser?){
        viewModelScope.launch {
            try {
                val response = apiService.updateAccountUser(id, updateUser)
                if (response.isSuccessful && response.body() != null) {
                    // Thành công, gọi lại API để tải dữ liệu mới
                    _updateAccUserResponse.postValue(response.body())
                    getUserAccById(id) // Lấy lại thông tin người dùng
                    Log.d("UserViewModel", "Update successful: ${response.body()}")
                } else {
                    _error.postValue("Cập nhật thất bại: ${response.message()}")
                    Log.e("UserViewModel", "Error response: ${response.message()}")
                }
            } catch (e: Exception) {
                _error.postValue("Lỗi khi cập nhật tài khoản: ${e.message}")
                Log.e("UserViewModel", "Exception: ${e.message}")
            }
        }
    }

}
