package com.rentify.user.app.viewModel.UserViewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rentify.user.app.repository.ListRoomMap.ListMapRepository
import com.rentify.user.app.repository.ListRoomMap.RoomData
import com.rentify.user.app.repository.ListRoomMap.RoomResponse
import kotlinx.coroutines.launch

class MapViewModel:ViewModel(){
    private val repository: ListMapRepository = ListMapRepository()
    private val _listRoom = MutableLiveData<List<RoomData>>()
    val listRoom:LiveData<List<RoomData>> = _listRoom
    fun getListRoomMap(){
        viewModelScope.launch {
            val response = repository.getListRoomMap()
            if(response.isSuccessful){
                val responseBody = response.body()
                if(responseBody != null){
                    _listRoom.value = responseBody.data
                }else{
                    Log.e("LoginError", "Response data is null")
                }
            }
        }
    }

}