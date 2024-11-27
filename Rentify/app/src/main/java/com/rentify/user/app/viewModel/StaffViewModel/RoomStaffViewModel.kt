package com.rentify.user.app.viewModel.StaffViewModel

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.rentify.user.app.repository.StaffRepository.RoomRepository.ServiceFee
import com.rentify.user.app.repository.StaffRepository.RoomRepository.Room
import com.rentify.user.app.repository.StaffRepository.RoomRepository.RoomResponse
import com.rentify.user.app.repository.StaffRepository.RoomRepository.RoomStaffRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class RoomStaffUiState {
    object Loading : RoomStaffUiState()
    data class Success(val data: RoomResponse) : RoomStaffUiState()
    data class Error(val message: String) : RoomStaffUiState()
}

class RoomStaffViewModel(
    private val repository: RoomStaffRepository = RoomStaffRepository()
) : ViewModel() {
    private val _uiState = MutableStateFlow<RoomStaffUiState>(RoomStaffUiState.Loading)
    val uiState: StateFlow<RoomStaffUiState> = _uiState.asStateFlow()

    private val _listRoom = MutableStateFlow<List<Room>>(emptyList())
    val listRoom: StateFlow<List<Room>> = _listRoom.asStateFlow()


    fun getRoomList(buildingId: String) {
        viewModelScope.launch {
            _uiState.value = RoomStaffUiState.Loading
            repository.getListRoomBuildingId(buildingId).fold(
                onSuccess = { response ->
                    if (response.status == 200 && response.data != null) {
                        _listRoom.value = response.data
                        _uiState.value = RoomStaffUiState.Success(response)
                    } else {
                        _uiState.value =
                            RoomStaffUiState.Error(response.message ?: "Không có dữ liệu")
                    }
                },
                onFailure = {exeption ->
                    _uiState.value = RoomStaffUiState.Error(exeption.message ?: "Lỗi không xác định")
                }
            )
        }
    }


    class RoomStaffViewModelFactory(
        private val repository: RoomStaffRepository
    ): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(RoomStaffViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return RoomStaffViewModel(repository) as T
            }
            throw IllegalStateException("Unknown ViewModel class")
        }
    }
}