package com.rentify.user.app.viewModel.RoomViewModel

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.rentify.user.app.model.AddRoomResponse
import com.rentify.user.app.model.BuildingWithRooms
import com.rentify.user.app.model.Room
import com.rentify.user.app.model.ServiceOfBuilding
import com.rentify.user.app.network.APIService
import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.LoginRepository.ApiResponse
import com.rentify.user.app.view.staffScreens.homeScreen.RoomSummary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

class RoomViewModel(private val context: Context) : ViewModel() {
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
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _updateRoomResponse = MutableLiveData<ApiResponse>()
    val updateRoomResponse: LiveData<ApiResponse> get() = _updateRoomResponse
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading
    private val _successMessage = MutableLiveData<String?>()
    val successMessage: LiveData<String?> get() = _successMessage

    private val _services = MutableLiveData<List<ServiceOfBuilding>>()
    val services: LiveData<List<ServiceOfBuilding>> get() = _services
    private val _managerId = MutableLiveData<String>()
    val managerId: LiveData<String> get() = _managerId

    fun setManagerId(id: String) {
        _managerId.value = id
    }

    fun fetchServiceOfBuilding(id: String){
        viewModelScope.launch {
            try {
                val response = apiService.getServiceOfBuilding(id)
                if(response.isSuccessful){
                    _services.value = response.body()
                }else{
                    Log.e("API_ERROR", "Error fetching data: ${response.message()}")
                    _error.value = "Failed to fetch buildings: ${response.message()}"
                }
            }catch (e: Exception){
                Log.e("API_EXCEPTION", "Exception: ${e.message}", e)
                _error.value = "An error occurred: ${e.message}"
            }
        }
    }

    // API LẤY DANH SÁCH TÒA NHÀ THEO MANAGERID
    fun fetchBuildingsWithRooms(manager_id: String) {
        _isLoading.value = true
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
            } finally {
                _isLoading.value = false
            }
        }
    }
    // API LẤY DANH SÁCH PHÒNG THEO TÒA 🤦‍♂️
    fun fetchRoomsForBuilding(building_id: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                // Giả sử bạn có hàm API này để lấy phòng cho tòa nhà
                val rooms = apiService.getRoomsForBuilding(building_id) // Thực hiện gọi API
                _rooms.postValue(rooms)
            } catch (e: Exception) {
                _rooms.postValue(emptyList()) // Xử lý lỗi nếu cần
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
    // hiển thị chi tiết phòng theo id phòng của mongodb tự động sinh ra 🏠
    fun fetchRoomDetailById(id: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = apiService.getRoomDetailById(id)
                if (response.isSuccessful) {
                    _roomDetail.value = response.body()
                } else {
                    Log.e("API_ERROR", "Failed to fetch room details: ${response.message()}")
                    _error.value = "Failed to fetch room details: ${response.message()}"
                }
            } catch (e: Exception) {
                Log.e("API_EXCEPTION", "Exception: ${e.message}", e)
                _error.value = "Exception: ${e.message}"
            } finally {
                _isLoading.value = false
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
    //    API ADD PHÒNG
    fun addRoom(
        buildingId: String,
        roomName: String,
        roomType: String,
        description: String,
        price: Double,
        size: String,
        service: Any,
        amenities: Any,
        limit_person: Int,
        status: String,
        sale: Double,
        photoUris: List<Uri>,
        videoUris: List<Uri>
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _isLoading.postValue(true)


                // Kiểm tra số lượng ảnh và video trước khi gửi
                if (photoUris.size > 10 || videoUris.size > 2) {
                    _error.postValue("Chỉ được phép tải lên tối đa 10 ảnh và 2 video.")
                    _isLoading.postValue(false)
                    return@launch
                }

                val parsedService = when (service) {
                    is List<*> -> Gson().toJson(service) // Nếu là danh sách, chuyển sang JSON
                    is String -> service // Nếu là chuỗi JSON, giữ nguyên
                    else -> "[]" // Mặc định rỗng
                }

                val parsedAmenities = when (amenities) {
                    is List<*> -> Gson().toJson(amenities) // Nếu là danh sách, chuyển sang JSON
                    is String -> amenities // Nếu là chuỗi JSON, giữ nguyên
                    else -> "[]" // Mặc định rỗng
                }

                // Tạo request body cho các trường văn bản
                val buildingIdBody = createPartFromString(buildingId)
                val roomNameBody = createPartFromString(roomName)
                val roomTypeBody = createPartFromString(roomType)
                val descriptionBody = createPartFromString(description)
                val priceBody = createPartFromString(price.toString())
                val sizeBody = createPartFromString(size)
                val limitPersonBody = createPartFromString(limit_person.toString())
                val statusBody = createPartFromString(status.toString())
                val saleBody = createPartFromString(sale.toString())
                val serviceBody = createPartFromString(parsedService)
                val amenitiesBody = createPartFromString(parsedAmenities)
                // Xử lý các URI hình ảnh và video thành MultipartBody.Part
                val photoParts = photoUris.mapIndexed { index, uri ->
                    processUri(context, Uri.parse(uri.toString()), "photos_room", "photo_$index.jpg")
                }

                val videoParts = videoUris.mapIndexed { index, uriString ->
                    val uri = Uri.parse(uriString.toString()) // Chuyển đổi String thành Uri
                    processUri(context, uri, "video_room", "video_$index.mp4")
                }

                // Gửi yêu cầu API
                val response = apiService.addRoom(
                    buildingId = buildingIdBody,
                    roomName = roomNameBody,
                    room_type = roomTypeBody,
                    description = descriptionBody,
                    price = priceBody,
                    size = sizeBody,
                    service = serviceBody,
                    amenities = amenitiesBody,
                    limit_person = limitPersonBody,
                    status = statusBody,
                    sale = saleBody,
                    photos_room = photoParts,
                    video_room = videoParts
                )

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        _addRoomResponse.value = response
                        managerId.value?.let { fetchRoomSummary(it)
                            Log.d("managerId", "addRoom: ${it}") }
                    } else {
                        _error.value = "Lỗi: ${response.message()}"
                    }
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.d("ErrorUpload", "addRoom: ${e.message}")
                    _error.value = "Lỗi: ${e.message}"
                    _isLoading.value = false
                }
            }
        }
    }

    private fun createPartFromString(value: String): RequestBody {
        return RequestBody.create("text/plain".toMediaTypeOrNull(), value)
    }
    fun processUri(context: Context, uri: Uri, paramName: String, fileName: String): MultipartBody.Part {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
                ?: throw IOException("Không thể mở InputStream từ URI: $uri")


            val byteArray = inputStream.readBytes()


            val requestBody = byteArray.toRequestBody("application/octet-stream".toMediaTypeOrNull())
            MultipartBody.Part.createFormData(paramName, fileName, requestBody)
        } catch (e: Exception) {
            Log.e("ProcessUriError", "Error processing URI $uri: ${e.message}", e)
            throw e
        }
    }


    fun getRealPathFromURI(context: Context, uri: Uri): String? {
        // Kiểm tra xem URI là kiểu content hay file
        return if (uri.scheme == "content") {
            val cursor = context.contentResolver.query(uri, null, null, null, null)
            cursor?.use {
                val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                if (it.moveToFirst()) it.getString(columnIndex) else null
            }
        } else if (uri.scheme == "file") {
            uri.path
        } else {
            null
        }
    }

    class RoomViewModeFactory(private val context: Context) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RoomViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return RoomViewModel(context) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    // UPDATE PHÒNG :
    fun updateRoom(
        id: String,
        roomName: String,
        roomType: String,
        description: String,
        price: String,
        size: String,
        service: Any,
        amenities: Any,
        limit_person: String,
        status: String,
        sale: String,
        photoUris: List<Uri>,
        videoUris: List<Uri>
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _isLoading.postValue(true)

                // Kiểm tra số lượng ảnh và video trước khi gửi
                if (photoUris.size > 10 || videoUris.size > 2) {
                    _error.postValue("Chỉ được phép tải lên tối đa 10 ảnh và 2 video.")
                    _isLoading.postValue(false)
                    return@launch
                }

                val parsedService = when (service) {
                    is List<*> -> Gson().toJson(service) // Nếu là danh sách, chuyển sang JSON
                    is String -> service // Nếu là chuỗi JSON, giữ nguyên
                    else -> "[]" // Mặc định rỗng
                }

                val parsedAmenities = when (amenities) {
                    is List<*> -> Gson().toJson(amenities) // Nếu là danh sách, chuyển sang JSON
                    is String -> amenities // Nếu là chuỗi JSON, giữ nguyên
                    else -> "[]" // Mặc định rỗng
                }



                // Tạo map chứa các trường văn bản
                val data = mapOf(
                    "room_name" to createPartFromString(roomName),
                    "room_type" to createPartFromString(roomType),
                    "description" to createPartFromString(description),
                    "price" to createPartFromString(price.toString()),
                    "sale" to createPartFromString(sale.toString()),
                    "size" to createPartFromString(size),
                    "limit_person" to createPartFromString(limit_person.toString()),
                    "status" to createPartFromString(status.toString()),
                    "service" to createPartFromString(parsedService),
                    "amenities" to createPartFromString(parsedAmenities)
                )

                // Xử lý URI hình ảnh và video
                val processedPhotoUris = photoUris.mapNotNull { uri ->
                    if (uri.scheme == "content" || uri.scheme == "file") {
                        uri // Hợp lệ
                    } else {
                        // Thêm logic kiểm tra và tải file từ server nếu cần
                        val filePath = uri.toString().replace("http://10.0.2.2:3000/", "")
                        val file = File(context.filesDir, filePath)
                        if (file.exists()) Uri.fromFile(file) else null
                    }
                }

                val processedVideoUris = videoUris.mapNotNull { uri ->
                    if (uri.scheme == "content" || uri.scheme == "file") {
                        uri // Hợp lệ
                    } else {
                        // Thêm logic kiểm tra và tải file từ server nếu cần
                        val filePath = uri.toString().replace("http://10.0.2.2:3000/", "")
                        val file = File(context.filesDir, filePath)
                        if (file.exists()) Uri.fromFile(file) else null
                    }
                }

                val photoParts = processedPhotoUris.mapIndexed { index, photoPath ->
                    processUriImage(context, Uri.parse(photoPath.toString()), "photos_room", "photo_$index.jpg")
                }

                val videoParts = processedVideoUris.mapIndexed { index, videoPath ->
                    processUriImage(context, Uri.parse(videoPath.toString()), "video_room", "video_$index.mp4")
                }

                // Gửi yêu cầu API
                val response = apiService.updateRoom(
                    id = id,
                    data = data,
                    photos = if (photoParts.isNotEmpty()) photoParts else null,
                    videos = if (videoParts.isNotEmpty()) videoParts else null
                )

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        _updateRoomResponse.value = response.body()
                        _successMessage.postValue("Cập nhật phòng thành công.")
                        managerId.value?.let { fetchRoomSummary(it)
                            Log.d("managerId", "addRoom: ${managerId}") }

                    } else {
                        _error.postValue("Lỗi cập nhật: ${response.message()}")
                    }
                    _isLoading.postValue(false)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("UpdateRoomError", "Error: ${e.message}")
                    _error.postValue("Lỗi: ${e.message}")
                    _isLoading.postValue(false)
                }
            }
        }
    }
    private val _roomSummary = MutableLiveData<RoomSummary?>()
    val roomSummary: LiveData<RoomSummary?> = _roomSummary

    // Hàm để lấy tổng số phòng
    fun fetchRoomSummary(managerId: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getRoomsSummaryByManager(managerId)
                _roomSummary.postValue(response)

                Log.e(" _roomSummary.postValue(response)", "Error: ${ _roomSummary.postValue(response)}")
            } catch (e: Exception) {
                _error.postValue(e.message)
            }
        }
    }

}

fun processUriImage(context: Context, uri: Uri, folderName: String, fileName: String): MultipartBody.Part {
    val contentResolver = context.contentResolver
    val stream = try {
        if (uri.scheme == "content") {
            contentResolver.openInputStream(uri)
        } else {
            val file = File(uri.path ?: throw IllegalArgumentException("Invalid Uri"))
            if (!file.exists()) throw FileNotFoundException("File not found at ${uri.path}")
            file.inputStream()
        }
    } catch (e: Exception) {
        throw FileNotFoundException("Failed to open stream for $uri: ${e.message}")
    }

    val tempFile = File(context.cacheDir, fileName)
    stream.use { input ->
        tempFile.outputStream().use { output ->
            input?.copyTo(output) ?: ""
        }
    }

    val requestBody = tempFile.asRequestBody("multipart/form-data".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData(folderName, tempFile.name, requestBody)
    /////

}


