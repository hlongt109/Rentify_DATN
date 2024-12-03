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
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

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
    fun fetchContractsByBuilding(manageId: String) {
        viewModelScope.launch {
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
                val result = when {
                    manageId != null -> {
                        // Nếu có manageId, kết hợp query và manageId
                        RetrofitClient.apiService.searchContracts(
                            userName = if (!query.contains("/")) query else null,
                            buildingRoom = if (query.contains("/")) query else null,
                            manageId = manageId
                        )
                    }
                    query.contains("/") -> {
                        // Nếu query theo định dạng buildingRoom
                        RetrofitClient.apiService.searchContracts(buildingRoom = query)
                    }
                    else -> {
                        // Nếu tìm kiếm theo userName
                        RetrofitClient.apiService.searchContracts(userName = query)
                    }
                }
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


