package com.rentify.user.app.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.State
import com.rentify.user.app.network.RetrofitService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DistrictViewModel : ViewModel() {
    private val apiService = RetrofitService().ApiService
    private val _districts = mutableStateOf<List<String>>(emptyList())
    val districts: State<List<String>> get() = _districts

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> get() = _isLoading

    private val _hasMoreData = MutableStateFlow(true) // Chuyển thành StateFlow
    val hasMoreData: StateFlow<Boolean> get() = _hasMoreData

    private var currentPage = 0 // Trang hiện tại
    private val pageSize = 5 // Số lượng mục mỗi lần tải

    // Hàm chia dữ liệu thành các trang
    private fun paginateData(data: List<String>): List<List<String>> {
        return data.chunked(pageSize) // Chia danh sách thành từng nhóm nhỏ theo `pageSize`
    }

    fun fetchDistricts(city: String, loadMore: Boolean = false) {
        if (_isLoading.value || !_hasMoreData.value) {
            println("Already loading or no more data to load, skip fetch")
            return
        }

        viewModelScope.launch {
            try {
                _isLoading.value = true
                if (loadMore) delay(300) // Đảm bảo CircularProgressIndicator hiển thị đủ lâu

                val response = apiService.getDistricts(city)
                val paginatedData = paginateData(response) // Sử dụng hàm `paginateData`

                if (loadMore) {
                    if (currentPage < paginatedData.size) {
                        _districts.value = _districts.value + paginatedData[currentPage]
                        currentPage++
                    }
                    if (currentPage >= paginatedData.size) {
                        _hasMoreData.value = false
                    }
                } else {
                    _districts.value = paginatedData.getOrNull(0) ?: emptyList()
                    currentPage = 1
                    _hasMoreData.value = paginatedData.size > 1
                }
            } catch (e: Exception) {
                println("Error fetching districts: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}
