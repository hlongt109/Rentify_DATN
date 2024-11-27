import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.rentify.user.app.model.Model.District
import com.rentify.user.app.model.Model.Province
import com.rentify.user.app.network.ApiClient.apiService
import com.rentify.user.app.repository.GetLocationRepository.LocationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// Tạo một data class để lưu trữ thông tin location state
data class LocationState(
    val provinceName: String = "",
    val districtName: String = "",
    val wardName: String = "",
    val fullAddress: String = "Toàn quốc"
)

class LocationViewModel(
    private val repository: LocationRepository
) : ViewModel() {

    private val _locationState = MutableStateFlow(LocationState())
    val locationState = _locationState.asStateFlow()

    // LiveData chứa danh sách các tỉnh
    private val _provinces = MutableLiveData<Result<List<Province>>>()
    val provinces: LiveData<Result<List<Province>>> get() = _provinces

    // LiveData chứa chi tiết tỉnh cùng danh sách quận/huyện
    private val _provinceWithDistricts = MutableLiveData<Result<Province>>()
    val provinceWithDistricts: LiveData<Result<Province>> get() = _provinceWithDistricts

    // LiveData chứa chi tiết quận cùng danh sách xã/phường
    private val _districtWithWards = MutableLiveData<Result<District>>()
    val districtWithWards: LiveData<Result<District>> get() = _districtWithWards

    private val _filteredProvinces = MutableStateFlow<List<Province>>(emptyList())
    val filteredProvinces: StateFlow<List<Province>> = _filteredProvinces

    private var allProvinces: List<Province> = emptyList()

    init {
        loadAllProvinces()
    }

    // Lấy danh sách tỉnh
    fun fetchProvinces() {
        viewModelScope.launch {
            _provinces.value = repository.getProvinces()
        }
    }

    // Lấy danh sách quận/huyện theo tỉnh
    fun fetchDistrictsByProvince(provinceCode: String) {
        viewModelScope.launch {
            _provinceWithDistricts.value = repository.getProvinceWithDistricts(provinceCode)
        }
    }

    // Lấy danh sách xã/phường theo quận/huyện
    fun fetchWardsByDistrict(districtCode: String) {
        viewModelScope.launch {
            _districtWithWards.value = repository.getWard(districtCode)
        }
    }
    //tìm kiếm
    private fun loadAllProvinces() {
        viewModelScope.launch {
            try {
                allProvinces = apiService.getProvinces()
                _filteredProvinces.value = allProvinces // Hiển thị toàn bộ khi không có tìm kiếm
            } catch (e: Exception) {
                // Xử lý lỗi nếu cần
                _filteredProvinces.value = emptyList()
            }
        }
    }

    fun searchProvinces(query: String) {
        val filtered = allProvinces.filter {
            it.name.contains(query, ignoreCase = true) // Lọc theo tên tỉnh
        }
        _filteredProvinces.value = filtered
    }

    fun updateLocation(province: String = "", district: String = "", ward: String = "") {
        _locationState.update { currentState ->
            val fullAddress = buildString {
                append(province)
                if (district.isNotEmpty()) {
                    append(", ")
                    append(district)
                }
                if (ward.isNotEmpty()) {
                    append(", ")
                    append(ward)
                }
            }

            currentState.copy(
                provinceName = province,
                districtName = district,
                wardName = ward,
                fullAddress = if (fullAddress.isEmpty()) "Toàn quốc" else fullAddress
            )
        }
    }
}

class LocationViewModelFactory(
    private val repository: LocationRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LocationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LocationViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
