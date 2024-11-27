package com.rentify.user.app.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rentify.user.app.model.Model.EmptyRoomResponse
import com.rentify.user.app.model.Model.RoomDetailResponse
import com.rentify.user.app.model.UserOfRoomDetail
import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.RoomDetailRepository
import kotlinx.coroutines.launch

class RoomDetailViewModel : ViewModel() {
    private val apiService = RetrofitService().ApiService
    private val roomDetailRepository = RoomDetailRepository(apiService)

    private val _roomDetail = MutableLiveData<RoomDetailResponse>()
    val roomDetail: LiveData<RoomDetailResponse> get() = _roomDetail

    private val _landlordDetail = MutableLiveData<UserOfRoomDetail>()
    val landlordDetail: LiveData<UserOfRoomDetail> get() = _landlordDetail

    private val _emptyRoom = MutableLiveData<List<EmptyRoomResponse>>()
    val emptyRoom: LiveData<List<EmptyRoomResponse>> get() = _emptyRoom

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun fetchRoomDetail(roomId : String){
        viewModelScope.launch {
            try {
                val response = roomDetailRepository.getRoomDetails(roomId)
                if(response.isSuccessful && response.body()!= null){
                    _roomDetail.value = response.body()  // Cập nhật chi tiết phòng nếu có dữ liệu
                } else {
                    _errorMessage.postValue("Failed to fetch room detail: ${response.message()}")
                }
            }catch (e: Exception){
                _errorMessage.postValue("Failed to fetch room detail: ${e.message}")
            }
        }
    }

    fun fetchLandlordDetail(landlordId : String){
        viewModelScope.launch {
            try {
                val response = roomDetailRepository.getUserOfRoomDetail(landlordId)
                if(response.isSuccessful && response.body()!= null){
                    _landlordDetail.value = response.body()  // Cập nhật chi tiết người bán phòng nếu có dữ liệu
                } else {
                    _errorMessage.postValue("Failed to fetch landlord detail: ${response.message()}")
                }
            }catch (e: Exception){
                _errorMessage.postValue("Failed to fetch landlord detail: ${e.message}")
            }
        }
    }

    fun fetchEmptyRoomDetail(emptyRoomId : String){
        viewModelScope.launch {
            try {
                val response = roomDetailRepository.getEmptyRoom(emptyRoomId)
                if(response.isSuccessful && response.body()!= null){
                    _emptyRoom.value = response.body()
                    Log.d("Debug", "Room detail fetched: $response")
                } else {
                    _errorMessage.postValue("Failed to fetch empty room detail: ${response.message()}")
                }
            }catch (e: Exception){
                _errorMessage.postValue("Failed to fetch empty room detail: ${e.message}")
            }
        }
    }
}