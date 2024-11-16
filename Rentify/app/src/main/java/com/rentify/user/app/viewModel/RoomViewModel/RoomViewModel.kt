package com.rentify.user.app.viewModel.RoomViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rentify.user.app.model.Room
import com.rentify.user.app.model.AddRoomResponse
import com.rentify.user.app.network.APIService
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class RoomViewModel(private val apiService: APIService) : ViewModel() {

    // LiveData cho danh sách phòng
    private val _rooms = MutableLiveData<List<Room>>()
    val rooms: LiveData<List<Room>> get() = _rooms

    // LiveData cho trạng thái thêm phòng
    private val _addRoomResponse = MutableLiveData<Response<AddRoomResponse>>()
    val addRoomResponse: LiveData<Response<AddRoomResponse>> get() = _addRoomResponse

    // LiveData để theo dõi lỗi
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    // Lấy danh sách phòng
    fun fetchRooms() {
        viewModelScope.launch {
            try {
                val response = apiService.getRooms()
                if (response.isSuccessful) {
                    _rooms.value = response.body()
                } else {
                    _error.value = "Lỗi khi lấy danh sách phòng: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Lỗi khi lấy danh sách phòng: ${e.message}"
            }
        }
    }

    // Thêm phòng mới
    fun addRoom(
        buildingId: String,
        roomName: String,
        roomType: String,
        description: String,
        price: Double,
        size: String,
        status: Int,
        availabilityStatus: String,
        videoFile: MultipartBody.Part?,
        photoFiles: List<MultipartBody.Part>?,
        serviceIds: List<String>?,
        amenities: List<String>?,
        serviceFees: List<Double>?,
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
                    availabilityStatus = createPartFromString(availabilityStatus),
                    videoRoom = videoFile,
                    photosRoom = photoFiles,
                    serviceIds = serviceIds?.let { createPartFromString(it.joinToString(",")) },
                    amenities = amenities?.let { createPartFromString(it.joinToString(",")) },
                    serviceFees = serviceFees?.let { createPartFromString(it.joinToString(",")) },
                    limitPerson = createPartFromString(limitPerson.toString())
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

    // Upload video
    fun uploadVideo(videoFile: MultipartBody.Part) {
        viewModelScope.launch {
            try {
                val response = apiService.uploadVideo(videoFile)
                if (response.isSuccessful) {
                    // Xử lý kết quả sau khi upload video thành công
                } else {
                    _error.value = "Lỗi khi tải video: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Lỗi khi tải video: ${e.message}"
            }
        }
    }

    // Upload ảnh
    fun uploadPhotos(photoFiles: List<MultipartBody.Part>) {
        viewModelScope.launch {
            try {
                val response = apiService.uploadPhotos(photoFiles)
                if (response.isSuccessful) {
                    // Xử lý kết quả sau khi upload ảnh thành công
                } else {
                    _error.value = "Lỗi khi tải ảnh: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Lỗi khi tải ảnh: ${e.message}"
            }
        }
    }

    // Tạo RequestBody từ String
    private fun createPartFromString(value: String): RequestBody {
        return RequestBody.create("text/plain".toMediaTypeOrNull(), value)
    }
}
