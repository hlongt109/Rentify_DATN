package com.rentify.user.app.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rentify.user.app.model.Model.RoomSaleResponse
import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.SaleRoomRepository
import kotlinx.coroutines.launch

class SaleRoomViewModel : ViewModel() {
    private val apiService = RetrofitService().ApiService
    private val saleRoomRepository = SaleRoomRepository(apiService)

    private val _roomList = MutableLiveData<List<RoomSaleResponse>>()
    val roomList: LiveData<List<RoomSaleResponse>> get() = _roomList

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _totalRooms = MutableLiveData<Int>()
    val totalRooms: LiveData<Int> get() = _totalRooms

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun fetchListRoom(
        address: String? = null,
        minPrice: Int? = null,
        maxPrice: Int? = null,
        roomType: String? = null,
        sortBy: String? = null,
        page: Int = 1,
        pageSize: Int = 6,
        random: String? = null,
        clearCurrentList: Boolean = false // Thêm tham số này
    ) {
        if (_isLoading.value == true) return
        _isLoading.value = true

        viewModelScope.launch {
            try {
                if (clearCurrentList) {
                    // Xóa danh sách hiện tại trước khi tải dữ liệu mới
                    _roomList.value = emptyList()
                }

                val response = saleRoomRepository.getRoomsWithSale(
                    address = address,
                    minPrice = minPrice,
                    maxPrice = maxPrice,
                    roomType = roomType,
                    sortBy = sortBy,
                    page = page,
                    pageSize = pageSize,
                    random = random
                )
                if (response.isSuccessful) {
                    response.body()?.let { roomPage ->
                        val currentList = if (clearCurrentList) emptyList() else _roomList.value.orEmpty()
                        _roomList.value = currentList + roomPage.rooms // Thêm dữ liệu mới
                        _totalRooms.value = roomPage.totalRooms

                        // Log số lượng item đã tải
                        Log.d("RentalPostRoomViewModel", "Đã tải ${roomPage.rooms.size} phòng, tổng số phòng: ${roomPage.totalRooms}")
                    } ?: run {
                        _errorMessage.value = "Không có dữ liệu phòng"
                    }
                } else {
                    _errorMessage.value = "Lỗi: ${response.code()} - ${response.message()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Đã có lỗi xảy ra: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}