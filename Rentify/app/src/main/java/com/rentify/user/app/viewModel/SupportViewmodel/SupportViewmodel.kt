package com.rentify.user.app.viewModel.SupportViewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rentify.user.app.model.SupportModel.RoomListResponse
import com.rentify.user.app.model.SupportModel.SupportResponse
import com.rentify.user.app.network.RetrofitService
import kotlinx.coroutines.launch
import retrofit2.Response

class SupportViewModel : ViewModel() {
    // Retrofit API service instance
    private val apiService = RetrofitService().ApiService

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    // LiveData for supports list
    private val _supports = MutableLiveData<List<SupportResponse>?>()
    val supports: MutableLiveData<List<SupportResponse>?> get() = _supports

    // LiveData for individual support detail
    private val _supportDetail = MutableLiveData<SupportResponse>()
    val supportDetail: LiveData<SupportResponse> get() = _supportDetail

    // LiveData for error messages
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _roomListResponse = MutableLiveData<RoomListResponse>()
    val roomListResponse: LiveData<RoomListResponse> get() = _roomListResponse


    // Fetch all supports
    fun fetchSupports() {
        viewModelScope.launch {
            try {
                val response: Response<List<SupportResponse>> = apiService.getSupports()
                if (response.isSuccessful) {
                    _supports.postValue(response.body())
                } else {
                    _errorMessage.postValue("Failed to load supports: ${response.message()}")
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Error fetching supports: ${e.message}")
            }
        }
    }

    fun fetchSupportDetail(roomId: String) {
        viewModelScope.launch {
            try {
                // Make the API call to fetch support details
                val response: Response<List<SupportResponse>> = apiService.getSupportDetail(roomId)

                // Check if the response is successful
                if (response.isSuccessful) {
                    val supportsList = response.body()

                    // Assuming we are only interested in the first support detail (or any specific logic you have)
                    supportsList?.firstOrNull()?.let {
                        _supportDetail.postValue(it)  // Post the first item (or handle your case accordingly)
                    }

                    // If there's a need to post the full list as well
                    _supports.postValue(supportsList)
                } else {
                    // Handle failure case
                    _errorMessage.postValue("Failed to load support details: ${response.message()}")
                }
            } catch (e: Exception) {
                // Handle network errors or exceptions
                _errorMessage.postValue("Error fetching support details: ${e.message}")
            }
        }
    }


    // Hàm gọi API để lấy danh sách phòng
    fun fetchRoomsByLandlordId(landlordId: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getRoomsByLandlordId(landlordId)
                if (response.isSuccessful) {
                    val roomListResponse = response.body()
                    roomListResponse?.let {
                        // Cập nhật LiveData với dữ liệu danh sách phòng
                        _roomListResponse.postValue(it)
                    }
                } else {
                    _errorMessage.postValue("Error: ${response.message()}")
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Exception: ${e.message}")
            }
        }
    }
}
