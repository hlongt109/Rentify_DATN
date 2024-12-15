package com.rentify.user.app.viewModel.StaffViewModel
import Contract
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rentify.user.app.network.RetrofitClient
import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rentify.user.app.model.Building
import com.rentify.user.app.model.Room_post
import com.rentify.user.app.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class ContractViewModel : ViewModel() {

    private val _contracts = MutableLiveData<List<Contract>>()
    val contracts: LiveData<List<Contract>> = _contracts

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error
    private val _contractDetail = MutableLiveData<Contract>()
    val contractDetail: LiveData<Contract> = _contractDetail
    // LiveData danh sách tòa nhà
    private val _buildings = MutableLiveData<List<Building>>()
    val buildings: LiveData<List<Building>> get() = _buildings

    // LiveData danh sách phòng
    private val _rooms = MutableLiveData<List<Room_post>>()
    val rooms: LiveData<List<Room_post>> get() = _rooms


    private val _userDetail = MutableStateFlow<User?>(null)
    val userDetail: StateFlow<User?> get() = _userDetail

    fun fetchUserById(userId: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getUserById(userId)

                if (response.isSuccessful) {
                    val user = response.body()

                    if (user != null) {
                        // Kiểm tra vai trò của người dùng
                        if (user.role == "user") {
                            _userDetail.value = user
                            _error.value = "" // Reset lỗi nếu thành công
                            Log.d("fetchUserById", "Người dùng hợp lệ: $user")
                        } else {
                            val errorMessage = "Người dùng không có vai trò hợp lệ (phải là user)."
                            _error.value = errorMessage
                            Log.e("fetchUserById", errorMessage)
                        }
                    } else {
                        val errorMessage = "Không tìm thấy người dùng với ID này."
                        _error.value = errorMessage
                        Log.e("fetchUserById", errorMessage)
                    }
                } else {
                    val errorMessage = "Lỗi: ${response.code()} - ${response.message()}"
                    _error.value = errorMessage
                    Log.e("fetchUserById", errorMessage)
                }
            } catch (e: HttpException) {
                val errorMessage = "Lỗi HTTP: ${e.message()}"
                _error.value = errorMessage
                Log.e("fetchUserById", errorMessage, e)
            } catch (e: Exception) {
                val errorMessage = "Lỗi không xác định: ${e.message}"
                _error.value = errorMessage
                    Log.e("fetchUserById", errorMessage, e)
            }
        }
    }

    suspend fun fetchUserByIdSuspend(userId: String): Result<User?> {
        return try {
            val response = RetrofitClient.apiService.getUserById(userId)
            if (response.isSuccessful) {
                val user = response.body()
                if (user != null && user.role == "user") {
                    Result.success(user)
                } else {
                    Result.failure(Exception("Người dùng không có vai trò hợp lệ (phải là user)."))
                }
            } else {
                Result.failure(Exception("Lỗi: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Lỗi không xác định: ${e.message}"))
        }
    }


    // Lấy danh sách tòa nhà
    fun fetchBuildings(manageId: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getBuildings_contrac(manageId)
                if (response.isSuccessful) {
                    _buildings.value = response.body()?.data ?: emptyList()

                } else {
                    _error.value = "Lỗi khi lấy danh sách tòa nhà: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Đã xảy ra lỗi"
            }
        }
    }

    // Lấy danh sách phòng
    fun fetchRooms(buildingId: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getRooms_contrac(buildingId)
                if (response.isSuccessful) {
                    _rooms.value = response.body()?.data ?: emptyList()
                } else {
                    _error.value = "Lỗi khi lấy danh sách phòng: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Đã xảy ra lỗi"
            }
        }
    }
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading
    fun fetchContractsByBuilding(manageId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitClient.apiService.getContractsByBuilding(manageId)
                if (response.isSuccessful) {
                    _contracts.value = response.body() ?: emptyList()
                    Log.d("ContractViewModel", "Contracts fetched: ${_contracts.value}")
                } else {
                    _error.value = "Error: ${response.message()}"
                    Log.e("ContractViewModel", "Error fetching contracts: ${response.message()}")
                }
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
                Log.e("ContractViewModel", "Error fetching contracts: ${e.message}")
            }finally {
                _isLoading.value = false
            }
        }
    }
    fun fetchContractDetail(contractId: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getContractDetail1(contractId)
                if (response.isSuccessful) {
                    _contractDetail.value = response.body()
                } else {
                    Log.e("ContractViewModel", "Error fetching contract details: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("ContractViewModel", "Error fetching contract details: ${e.message}")
            }
        }
    }
    private val _updateStatus = MutableLiveData<Result<Boolean>>()
    val updateStatus: LiveData<Result<Boolean>> = _updateStatus

    fun updateContract_STAFF(
        contractId: String,
        userId: String?,
        content: String?,
        photos: List<MultipartBody.Part>?
    ) {
        viewModelScope.launch {
            try {
                // Chuẩn bị RequestBody
                val userIdBody = userId?.toRequestBody("text/plain".toMediaTypeOrNull())
                val contentBody = content?.toRequestBody("text/plain".toMediaTypeOrNull())

                // Gọi API để cập nhật hợp đồng
                val response = RetrofitClient.apiService.updateContract(
                    contractId,
                    userIdBody,
                    contentBody,
                    photos
                )

                // Logging để kiểm tra phản hồi
                Log.d("updateContract", "API response code: ${response.code()}")
                Log.d("updateContract", "API response message: ${response.message()}")

                if (response.isSuccessful) {
                    val updatedContract = response.body()
                    if (updatedContract != null) {
                        Log.d("updateContract", "Update successful: $updatedContract")
              //          _contractDetail.value = updatedContract // Cập nhật dữ liệu hợp đồng
                    } else {
                        Log.e("updateContract", "Update failed: Response body is null")
                        _error.value = "Cập nhật thất bại: Không có dữ liệu trả về từ server."
                    }
                } else {
                    Log.e("updateContract", "API Error: ${response.message()}")
                    _error.value = "Lỗi API: ${response.message()}"
                }
            } catch (e: Exception) {
                Log.e("updateContract", "Exception: ${e.message}", e)
                _error.value = "Đã xảy ra lỗi: ${e.message}"
            }
        }
    }
    val searchQuery = mutableStateOf("")

    fun searchContracts(query: String, manageId: String? = null) {
        viewModelScope.launch {
            try {
                // Xử lý tìm kiếm khi có manageId
                val result = when {
                    manageId != null -> {
                        // Nếu có manageId, gửi yêu cầu tìm kiếm hợp đồng với keyword và manageId
                        RetrofitClient.apiService.searchContracts(
                            keyword = query,  // Gửi luôn query tìm kiếm (tòa nhà, phòng, người dùng)
                            manageId = manageId
                        )
                    }
                    else -> {
                        // Nếu không có manageId, tìm kiếm theo từ khóa query mà không cần phân tách
                        RetrofitClient.apiService.searchContracts(
                            keyword = query,  // Gửi luôn query tìm kiếm
                            manageId = null    // Không có manageId
                        )
                    }
                }

                // Đặt kết quả vào LiveData hoặc MutableLiveData
                _contracts.postValue(result)
            } catch (e: Exception) {
                _error.postValue("Lỗi khi tìm kiếm hợp đồng: ${e.message}")
            }
        }
    }


    // Hàm xử lý khi giá trị tìm kiếm thay đổi
    fun onSearchQueryChange(newQuery: String) {
        searchQuery.value = newQuery
        if (newQuery.isNotEmpty()) {

            searchContracts(newQuery) // Gọi tìm kiếm ngay khi có query
        }
    }
}


