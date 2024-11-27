package com.rentify.user.app.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rentify.user.app.model.Model.Room
import com.rentify.user.app.model.Model.RoomResponse
import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.HomeScreenRepository
import kotlinx.coroutines.launch

class HomeScreenViewModel : ViewModel() {
    private val apiService = RetrofitService().ApiService
    private val homeScreenRepository = HomeScreenRepository(apiService)

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
                val response = homeScreenRepository.getListOfRandomRooms()
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

}