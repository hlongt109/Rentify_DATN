package com.rentify.user.app.viewModel.RoomViewModel

import android.net.Uri
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
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Response
import java.io.File

class RoomViewModel : ViewModel() {
    private val _roomNames = MutableLiveData<List<String>>()
    val roomNames: LiveData<List<String>> get() = _roomNames
    private val apiService = RetrofitService().ApiService
    private val _addRoomResponse = MutableLiveData<Response<AddRoomResponse>>()
    val addRoomResponse: LiveData<Response<AddRoomResponse>> get() = _addRoomResponse
    private val _rooms = MutableLiveData<List<Room>>()
    val rooms: LiveData<List<Room>> get() = _rooms
    private val _buildingWithRooms = MutableLiveData<List<BuildingWithRooms>>()
    val buildingWithRooms: LiveData<List<BuildingWithRooms>> get() = _buildingWithRooms
    private val _roomDetail = MutableLiveData<Room?>()
    val roomDetail: LiveData<Room?> get() = _roomDetail
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error
    //    API ADD PHÒNG
    fun addRoom(
        buildingId: String,
        roomName: String,
        roomType: String,
        description: String,
        price: Double,
        size: String,
        status: Int,
        videoFile: List<Uri>?,
        photoFiles: List<Uri>?,
        service: List<String>?,
        amenities: List<String>?,
        limitPerson: Int
    ) {
        viewModelScope.launch {
            try {
                // Convert video URIs to URLs (Assuming you upload them somewhere and get the URLs)
                val videoUrls = videoFile?.map { uri ->
                    uploadFileToServer(uri, "video/mp4")
                }
                val photoUrls = photoFiles?.map { uri ->
                    uploadFileToServer(uri, "image/jpeg")
                }
                val response = apiService.addRoom(
                    building_id = createPartFromString(buildingId),
                    room_name = createPartFromString(roomName),
                    room_type = createPartFromString(roomType),
                    description = createPartFromString(description),
                    price = createPartFromString(price.toString()),
                    size = createPartFromString(size),
                    status = createPartFromString(status.toString()),
                    video_room = videoUrls,
                    photos_room = photoUrls,
                    service = service?.let { createPartFromString(it.joinToString(",")) },
                    amenities = amenities?.let { createPartFromString(it.joinToString(",")) },
                    limit_person = createPartFromString(limitPerson.toString())
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
    private suspend fun uploadFileToServer(uri: Uri, mimeType: String): String {
        try {
            val file = File(uri.path!!)
            val requestBody = file.asRequestBody(mimeType.toMediaTypeOrNull())
            val multipart = MultipartBody.Part.createFormData("file", file.name, requestBody)
            val response = apiService.uploadFile(multipart)
            return response.body()?.fileUrl ?: ""
        } catch (e: Exception) {
            Log.e("UploadError", "File upload failed: ${e.message}", e)
            return ""
        }
    }
    private fun createPartFromString(value: String): RequestBody {
        return RequestBody.create("text/plain".toMediaTypeOrNull(), value)
    }

    // API LẤY DANH SÁCH TÒA THEO MANAGERID
    fun fetchBuildingsWithRooms(manager_id: String) {
        viewModelScope.launch {
            try {
                val response: Response<List<BuildingWithRooms>> =
                    apiService.getBuildingsWithRooms(manager_id)

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
    // API LẤY DANH SÁCH PHÒNG THEO TÒA 🤦‍♂️
    fun fetchRoomsForBuilding(building_id: String) {
        viewModelScope.launch {
            try {
                // Giả sử bạn có hàm API này để lấy phòng cho tòa nhà
                val rooms = apiService.getRoomsForBuilding(building_id) // Thực hiện gọi API
                _rooms.postValue(rooms)
            } catch (e: Exception) {
                _rooms.postValue(emptyList()) // Xử lý lỗi nếu cần
                e.printStackTrace()
            }
        }
    }
    // hiển thị chi tiết phòng theo id phòng của mongodb tự động sinh ra 🏠
    fun fetchRoomDetailById(id: String) {
        // Kiểm tra xem dữ liệu phòng đã có chưa
        if (_roomDetail.value != null) return // Nếu đã có dữ liệu thì không gọi API nữa

        viewModelScope.launch {
            try {
                val response = apiService.getRoomDetailById(id)
                if (response.isSuccessful) {
                    _roomDetail.value = response.body()  // Lưu dữ liệu vào LiveData
                } else {
                    Log.e("API_ERROR", "Failed to fetch room details: ${response.message()}")
                    _error.value = "Failed to fetch room details: ${response.message()}"
                }
            } catch (e: Exception) {
                Log.e("API_EXCEPTION", "Exception: ${e.message}", e)
                _error.value = "Exception: ${e.message}"
            }
        }
    }
    fun deleteRoomById(id: String) {
        viewModelScope.launch {
            try {
                // Gọi API để xóa phòng
                val response = apiService.deleteRoom(id)

                if (response.isSuccessful) {
                    // Xử lý thành công
                    _error.value = null // Reset lỗi (nếu có)
                    Log.d("RoomViewModel", "Room deleted successfully.")
                } else {
                    // Xử lý thất bại
                    _error.value = "Failed to delete room: ${response.message()}"
                    Log.e("RoomViewModel", "Failed to delete room: ${response.message()}")
                }
            } catch (e: Exception) {
                // Xử lý lỗi khi gọi API
                _error.value = "Exception: ${e.message}"
                Log.e("RoomViewModel", "Exception: ${e.message}", e)
            }
        }
    }

}
