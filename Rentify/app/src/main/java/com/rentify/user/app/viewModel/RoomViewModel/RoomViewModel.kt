package com.rentify.user.app.viewModel.RoomViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rentify.user.app.model.AddRoomResponse
import com.rentify.user.app.model.BuildingWithRooms
import com.rentify.user.app.model.Room
import com.rentify.user.app.network.RetrofitService
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class RoomViewModel : ViewModel() {
    private val apiService = RetrofitService().ApiService
    private val _addRoomResponse = MutableLiveData<Response<AddRoomResponse>>()
    val addRoomResponse: LiveData<Response<AddRoomResponse>> get() = _addRoomResponse
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error
    private val _rooms = MutableLiveData<List<Room>>()
    val rooms: LiveData<List<Room>> get() = _rooms
    private val _buildingWithRooms = MutableLiveData<List<BuildingWithRooms>>()
    val buildingWithRooms: LiveData<List<BuildingWithRooms>> get() = _buildingWithRooms

    // Fetch buildings with rooms
    fun fetchBuildingsWithRooms(managerId: String) {
        viewModelScope.launch {
            try {
                val response: Response<List<BuildingWithRooms>> =
                    apiService.getBuildingsWithRooms(managerId)

                if (response.isSuccessful) {
                    _buildingWithRooms.value = response.body()
                } else {
                    Log.e("API_ERROR", "Error fetching data: ${response.message()}")
                    _error.value = "Failed to fetch buildings: ${response.message()}"
                }
            } catch (e: Exception) {
                Log.e("API_EXCEPTION", "Exception: ${e.message}", e)
                _error.value = "An error occurred: ${e.message}"
            }
        }
    }


    fun addRoom(
        buildingId: String,
        roomName: String,
        roomType: String,
        description: String,
        price: Double,
        size: String,
        status: Int,
        videoFile: MultipartBody.Part?,
        photoFiles: List<MultipartBody.Part>?,
        service: List<String>?,
        amenities: List<String>?,
        limitPerson: Int
    ) {
        viewModelScope.launch {
            try {
                val response = apiService.addRoom(
                    buildingId = createPartFromString(buildingId),
                    roomName = createPartFromString(roomName),
                    roomType = createPartFromString(roomType),
                    description = createPartFromString(description),
                    price = createPartFromString(price.toString()),
                    size = createPartFromString(size),
                    status = createPartFromString(status.toString()),
                    videoRoom = videoFile,
                    photosRoom = photoFiles,
                    service = service?.let { createPartFromString(it.joinToString(",")) },
                    amenities = amenities?.let { createPartFromString(it.joinToString(",")) },
                    limitPerson = createPartFromString(limitPerson.toString()),
                )

                if (response.isSuccessful) {
                    _addRoomResponse.value = response
                } else {
                    _error.value = "Lỗi khi thêm phòng: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Lỗi khi thêm phòng: ${e.message}"
            }
        }
    }
    private fun createPartFromString(value: String): RequestBody {
        return RequestBody.create("text/plain".toMediaTypeOrNull(), value)
    }
}
