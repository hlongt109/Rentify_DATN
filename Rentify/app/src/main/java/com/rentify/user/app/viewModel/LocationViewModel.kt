package com.rentify.user.app.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rentify.user.app.model.District
import com.rentify.user.app.model.Location
import com.rentify.user.app.model.Province
import com.rentify.user.app.model.Ward
import com.rentify.user.app.repository.GetLocationRepository.LocationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LocationViewModel(
    private val repository: LocationRepository = LocationRepository()
) : ViewModel() {
    private val _locationState = MutableStateFlow<UiState<List<Location>>>(UiState.Loading)
    val locationState: StateFlow<UiState<List<Location>>> = _locationState

    private val _provinces = MutableStateFlow<UiState<List<Province>>>(UiState.Loading)
    val provinces = _provinces.asStateFlow()

    private val _districts = MutableStateFlow<UiState<List<District>>>(UiState.Loading)
    val districts = _districts.asStateFlow()

    private val _wards = MutableStateFlow<UiState<List<Ward>>>(UiState.Loading)
    val wards = _wards.asStateFlow()

    private val _selectedProvince = MutableStateFlow<UiState<Province>>(UiState.Loading)
    val selectedProvince = _selectedProvince.asStateFlow()

    private val _selectedDistrict = MutableStateFlow<UiState<District>>(UiState.Loading)
    val selectedDistrict = _selectedDistrict.asStateFlow()

    init {
        loadProvinces()
    }

    fun loadProvinces() {
        viewModelScope.launch {
            try {
                _locationState.value = UiState.Loading
                // Lấy dữ liệu tỉnh từ repository, kết quả trả về là Result<List<Province>>
                val result = repository.getProvinces()

                // Kiểm tra kết quả trả về và chuyển đổi dữ liệu
                result.onSuccess { provinces ->
                    // Chuyển đổi List<Province> thành List<Location>
                    val locations = provinces.map { province ->
                        // Tạo đối tượng Location từ Province (hoặc thêm thông tin về District, Ward nếu cần)
                        Location(province = province, district = null, ward = null)
                    }
                    _locationState.value = UiState.Success(locations)
                }.onFailure { exception ->
                    _locationState.value = UiState.Error(exception.message ?: "Có lỗi xảy ra")
                }
            } catch (e: Exception) {
                _locationState.value = UiState.Error(e.message ?: "Có lỗi xảy ra")
            }
        }
    }

    fun selectProvince(province: Province) {
        viewModelScope.launch {
            _selectedProvince.value = UiState.Success(province)
            loadDistricts(province.code)
            // Reset wards when selecting new province
            _wards.value = UiState.Success(emptyList())
            _selectedDistrict.value = UiState.Loading
        }
    }

    fun selectDistrict(district: District) {
        viewModelScope.launch {
            _selectedDistrict.value = UiState.Success(district)
            loadWards(district.code)
        }
    }

    private fun loadDistricts(provinceCode: String) {
        viewModelScope.launch {
            _districts.value = UiState.Loading
            repository.getProvinceWithDistricts(provinceCode)
                .onSuccess { province ->
                    // Xử lý districts bị null bằng cách gán danh sách trống
                    _districts.value = UiState.Success(province.districts ?: emptyList())
                }
                .onFailure {
                    _districts.value = UiState.Error(it.message ?: "Lỗi không xác định")
                }
        }
    }

    private fun loadWards(districtCode: String) {
        viewModelScope.launch {
            _wards.value = UiState.Loading
            repository.getWard(districtCode)
                .onSuccess { district ->
                    _wards.value = UiState.Success(district.wards ?: emptyList())
                }
                .onFailure {
                    _wards.value = UiState.Error(it.message ?: "Unknown error")
                }
        }
    }
}

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}