package com.rentify.user.app.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rentify.user.app.model.Model.Room
import com.rentify.user.app.model.Model.RoomResponse
import com.rentify.user.app.model.Model.RoomSaleResponse
import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.HomeScreenRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeScreenViewModel : ViewModel() {
    private val apiService = RetrofitService().ApiService
    private val homeScreenRepository = HomeScreenRepository(apiService)

    private val _roomList = MutableLiveData<List<RoomSaleResponse>>()
    val roomList: LiveData<List<RoomSaleResponse>> get() = _roomList

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    init {
        fetchListRoom()
    }

    private fun fetchListRoom(){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = homeScreenRepository.getListOfRandomRooms()
                if(response.isSuccessful && response.body() != null){
                    _roomList.value = response.body()  // Cập nhật danh sách nếu có dữ liệu
                } else {
                    _errorMessage.postValue("Failed to fetch room list: ${response.message()}")
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Failed to fetch room list: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

}