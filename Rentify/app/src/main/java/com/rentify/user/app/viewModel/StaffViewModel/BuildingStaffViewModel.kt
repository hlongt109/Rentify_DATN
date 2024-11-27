package com.rentify.user.app.viewModel.StaffViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.rentify.user.app.repository.StaffRepository.BuildingRepository.Building
import com.rentify.user.app.repository.StaffRepository.BuildingRepository.BuildingStaffRepository
import com.rentify.user.app.repository.StaffRepository.BuildingRepository.BuildingStaffResponse
import com.rentify.user.app.repository.StaffRepository.BuildingRepository.ServiceFee
import com.rentify.user.app.repository.StaffRepository.InvoiceRepository.InvoiceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class BuildingStaffUiState {
    object Loading : BuildingStaffUiState()
    data class Success(val data: BuildingStaffResponse) : BuildingStaffUiState()
    data class Error(val message: String) : BuildingStaffUiState()
}

class BuildingStaffViewModel(
    private val repository: BuildingStaffRepository = BuildingStaffRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<BuildingStaffUiState>(BuildingStaffUiState.Loading)
    val uiState: StateFlow<BuildingStaffUiState> = _uiState.asStateFlow()

    private val _listBuilding = MutableStateFlow<List<Building>>(emptyList())
    val listBuilding: StateFlow<List<Building>> = _listBuilding.asStateFlow()

    private val _serviceFees = MutableStateFlow<List<ServiceFee>>(emptyList())
    val serviceFees: StateFlow<List<ServiceFee>> = _serviceFees.asStateFlow()
    fun getBuildingList(staffId: String) {
        viewModelScope.launch {
            _uiState.value = BuildingStaffUiState.Loading
            repository.getListBuildingStaffId(staffId).fold(
                onSuccess = { response ->
                    Log.d("APIResponse", "Data: ${response.data}, Status: ${response.status}")
                    if (response.status == 200 && response.data != null) {
                        _listBuilding.value = response.data
                        _uiState.value = BuildingStaffUiState.Success(response)
                    } else {
                        _uiState.value =
                            BuildingStaffUiState.Error(response.message ?: "Không có dữ liệu")
                    }
                },
                onFailure = { exception ->
                    _uiState.value =
                        BuildingStaffUiState.Error(exception.message ?: "Lỗi không xác định")
                }
            )
        }
    }

    fun selectBuilding(buildingId: String) {
        val selectedBuilding = _listBuilding.value.find { it._id == buildingId }
        _serviceFees.value = selectedBuilding?.serviceFees ?: emptyList()
    }

    class BuildingStaffViewModelFactory(
        private val repository: BuildingStaffRepository,
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(BuildingStaffViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return BuildingStaffViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}