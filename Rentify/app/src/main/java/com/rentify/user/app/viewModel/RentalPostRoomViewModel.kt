package com.rentify.user.app.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rentify.user.app.model.Model.RoomResponse
import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.HomeScreenRepository
import com.rentify.user.app.repository.RentalPostRoomRepository
import kotlinx.coroutines.launch

class RentalPostRoomViewModel : ViewModel() {
    private val apiService = RetrofitService().ApiService
    private val rentalPostRoomRepository = RentalPostRoomRepository(apiService)

    private val _roomList = MutableLiveData<List<RoomResponse>>()
    val roomList: LiveData<List<RoomResponse>> get() = _roomList

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    init {
        fetchListRoom()
    }

    private fun fetchListRoom(){
        viewModelScope.launch {
            try {
                val response = rentalPostRoomRepository.getListOfRandomRooms()
                if(response.isSuccessful && response.body() != null){
                    _roomList.value = response.body()  // Cập nhật danh sách nếu có dữ liệu
                } else {
                    _errorMessage.postValue("Failed to fetch room list: ${response.message()}")
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Failed to fetch room list: ${e.message}")
            }
        }
    }

    fun searchRooms(
        address: String? = null,
        minPrice: Int? = null,
        maxPrice: Int? = null,
        roomType: String? = null,
        sortBy: String? = null
    ) {
        viewModelScope.launch {
            try {
                val response = rentalPostRoomRepository.searchRooms(address, minPrice, maxPrice, roomType, sortBy)
                if (response.isSuccessful && response.body() != null) {
                    _roomList.value = response.body()
                } else {
                    _errorMessage.value = "Không tìm thấy phòng nào phù hợp"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Đã có lỗi xảy ra: ${e.message}"
            }
        }
    }

}