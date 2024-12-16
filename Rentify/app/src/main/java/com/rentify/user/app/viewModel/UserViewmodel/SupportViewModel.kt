package com.rentify.user.app.viewModel.UserViewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.rentify.user.app.repository.LoginRepository.LoginRepository
import com.rentify.user.app.repository.SupportRepository.APISupportResponse
import com.rentify.user.app.repository.SupportRepository.Contract
import com.rentify.user.app.repository.SupportRepository.ContractRoomData
import com.rentify.user.app.repository.SupportRepository.ContractRoomResponse
import com.rentify.user.app.repository.SupportRepository.Support
import com.rentify.user.app.repository.SupportRepository.SupportRepository
import com.rentify.user.app.viewModel.LoginViewModel
import com.rentify.user.app.viewModel.StaffViewModel.BuildingStaffUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class SupportUiState {
    object Loading : SupportUiState()
    data class Success(val data: List<Support>) : SupportUiState()
    data class Error(val message: String) : SupportUiState()
}

sealed class RoomSupportUiState {
    object Loading : RoomSupportUiState()
    data class Success(val data: List<ContractRoomData>) : RoomSupportUiState()
    data class Error(val message: String) : RoomSupportUiState()
}

sealed class CheckContractUiState {
    object Loading : CheckContractUiState()
    data class Success(val data: List<Contract>) : CheckContractUiState()
    data class Error(val message: String) : CheckContractUiState()
}

class SupportViewModel(
    private val repository: SupportRepository = SupportRepository()
) : ViewModel() {
    private val _uiState = MutableStateFlow<SupportUiState>(SupportUiState.Loading)
    val uiState: StateFlow<SupportUiState> = _uiState.asStateFlow()

    private val _roomUiState = MutableStateFlow<RoomSupportUiState>(RoomSupportUiState.Loading)
    val roomUiState: StateFlow<RoomSupportUiState> = _roomUiState.asStateFlow()

    private val _contractUiState =
        MutableStateFlow<CheckContractUiState>(CheckContractUiState.Loading)
    val contractUiState: StateFlow<CheckContractUiState> = _contractUiState.asStateFlow()

    private val _listSupport = MutableStateFlow<List<APISupportResponse>>(emptyList())
    val listSupport: StateFlow<List<APISupportResponse>> = _listSupport.asStateFlow()

    private val _listRoom = MutableStateFlow<List<ContractRoomData>>(emptyList())
    val listRoom: StateFlow<List<ContractRoomData>> = _listRoom.asStateFlow()
//    private val _roomContract = MutableStateFlow<ContractRoomData>()
//    val listRoomContract: StateFlow<List<ContractRoomData>> = _listRoomContract.asStateFlow()

    //phan loai danh sach sự cố
    private val _processed = MutableStateFlow<List<Support>>(emptyList())
    val processed: StateFlow<List<Support>> = _processed.asStateFlow()
    private val _unprocessed = MutableStateFlow<List<Support>>(emptyList())
    val unprocessed: StateFlow<List<Support>> = _unprocessed.asStateFlow()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _successMessage = MutableLiveData<String?>()
    val successMessage: LiveData<String?> = _successMessage

    private val _successMessageAdd = MutableLiveData<String?>()
    val successMessageAdd: LiveData<String?> = _successMessageAdd
    //loi
    private val _errorRoom = MutableLiveData<String?>()
    val errorRoom: LiveData<String?> = _errorRoom
    private val _errorTitle = MutableLiveData<String?>()
    val errorTitle: LiveData<String?> = _errorTitle
    private val _errorContent = MutableLiveData<String?>()
    val errorContent: LiveData<String?> = _errorContent

    fun getListSupport(userId: String) {
        viewModelScope.launch {
            _uiState.value = SupportUiState.Loading
            _isLoading.postValue(true)
            repository.getSupportsByUserId(userId).fold(
                onSuccess = { response ->
                    if (response.status == 200) {
                        _isLoading.postValue(false)
                        _uiState.value = SupportUiState.Success(response.data)
                        //chua xu ly
                        _processed.value = response.data.filter { it.status == 1 }
                        //da xu ly
                        _unprocessed.value = response.data.filter { it.status == 0 }
                    }else{
                        _isLoading.postValue(false)
                        _uiState.value = SupportUiState.Error(response.message)
                        _errorMessage.postValue(response.message)
                    }
                },
                onFailure = { exception ->
                    _isLoading.postValue(false)
                    _uiState.value = SupportUiState.Error(exception.message ?: "Lỗi không xác định")
                    _errorMessage.postValue(exception.message ?: "Lỗi không xác định")
                }
            )
        }
    }

    //lay thong tin phong
    fun getInfoRoom(userId: String) {
        viewModelScope.launch {
            _uiState.value = SupportUiState.Loading // Hiển thị trạng thái loading
            repository.getInfoRoom(userId).fold(
                onSuccess = { response ->
                    if (response.status == 200) {
                        // Cập nhật trạng thái thành công với dữ liệu từ response
                        _roomUiState.value = RoomSupportUiState.Success(response.data)
                        _listRoom.value = response.data
                        Log.d("ListRoom", "getInfoRoom: ${response.data}")
                        _successMessage.postValue(response.message)
                    } else {
                        // Xử lý lỗi trả về từ server (nếu có)
                        _roomUiState.value = RoomSupportUiState.Error(response.message)
                        _errorMessage.postValue(response.message)
                    }
                },
                onFailure = { exception ->
                    // Cập nhật trạng thái lỗi khi có ngoại lệ xảy ra
                    _roomUiState.value =
                        RoomSupportUiState.Error(exception.message ?: "Lỗi không xác định")
                    _errorMessage.postValue(exception.message ?: "Lỗi không xác định")
                }
            )
        }
    }

    fun checkUserContract(userId: String) {
        viewModelScope.launch {
            _contractUiState.value = CheckContractUiState.Loading
            repository.checkStatusContract(userId).fold(
                onSuccess = { response ->
                    if (response.status == 200) {
                        // Lọc các hợp đồng còn hiệu lực (status = 0)
                        val activeContracts = response.data.filter { contract ->
                            contract.status == 0
                        }
                        // Nếu có hợp đồng còn hiệu lực
                        if (activeContracts.isNotEmpty()) {
                            _contractUiState.value = CheckContractUiState.Success(activeContracts)
                        } else {
                            // Không có hợp đồng nào còn hiệu lực
                            _contractUiState.value =
                                CheckContractUiState.Error("Không có hợp đồng còn hiệu lực")
                        }
                    } else {
                        _contractUiState.value =
                            CheckContractUiState.Error("Không thể kiểm tra hợp đồng")
                    }
                },
                onFailure = { exception ->
                    // Xử lý lỗi kết nối hoặc lỗi khác
                    _contractUiState.value = CheckContractUiState.Error(
                        exception.message ?: "Đã xảy ra lỗi khi kiểm tra hợp đồng"
                    )
                }

            )
        }
    }

    //them bao cao
    fun createSupportReport(
        userId: String,
        roomId: String,
        buildingId: String,
        titleSupport: String,
        contentSupport: String,
        status: Int,
        imagePaths: List<String?>,
        onSuccess: () -> Unit
    ) {

        if (roomId.isBlank()) {
            _errorRoom.postValue("Vui lòng chọn phòng.")
            return
        }
        if (titleSupport.isBlank()) {
            _errorTitle.postValue("Vui lòng nhập tiêu đề sự cố.")
            return
        }
        if (contentSupport.isBlank()) {
            _errorContent.postValue("Vui lòng nhập mô tả sự cố.")
            return
        }

        viewModelScope.launch {
            _isLoading.postValue(true) // Hiển thị trạng thái loading
            val result = repository.createReport(
                userId, roomId, buildingId, titleSupport, contentSupport, status, imagePaths
            )
            Log.d("AddSupport", "createSupportReport: $result")
            // Ẩn trạng thái loading
            result.onSuccess { response ->
                // Cập nhật thông báo thành công
                _isLoading.postValue(false)
                _successMessageAdd.postValue("Tạo báo cáo thành công:")
                onSuccess()
            }.onFailure { error ->
                // Cập nhật thông báo lỗi
                _isLoading.postValue(false)
                _errorMessage.postValue(error.message ?: "Lỗi không xác định khi tạo báo cáo")
            }
        }
    }

    fun clearErrorRoom(){
        _errorRoom.value = null
    }
    fun clearErrorTitle(){
        _errorTitle.value = null
    }
    fun clearErrorContent(){
        _errorContent.value = null
    }

    class SupportViewModelFactory(
        private val repository: SupportRepository,
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SupportViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SupportViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}