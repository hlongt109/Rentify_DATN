package com.rentify.user.app.viewModel.PostViewModel
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rentify.user.app.network.RetrofitClient
import com.rentify.user.app.view.staffScreens.postingList.PostingListComponents.PostingList
import kotlinx.coroutines.launch
import androidx.compose.runtime.State
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rentify.user.app.model.Building
import com.rentify.user.app.model.PostingDetail
import com.rentify.user.app.model.Room_post
import com.rentify.user.app.model.UpdatePostRequest
import com.rentify.user.app.network.APIService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

class PostViewModel : ViewModel() {
    private val _posts = mutableStateOf<List<PostingList>>(emptyList())
    val posts: State<List<PostingList>> = _posts

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _updateBookingStatusResult = MutableLiveData<Result<UpdatePostRequest>?>()
    val updateBookingStatusResult: LiveData<Result<UpdatePostRequest>?> get() = _updateBookingStatusResult
    // thien code phan nay

    fun updatePost(
        postId: String,
        userId: String?,
        buildingId: String?,
        roomId: String?,
        title: String?,
        content: String?,
        status: String?,
        postType: String?,
        videoFile: List<MultipartBody.Part>?,
        photoFile: List<MultipartBody.Part>?
    ) {
        viewModelScope.launch {
            try {
                // Chuẩn bị RequestBody cho từng tham số dạng chuỗi
                val userIdBody = userId?.toRequestBody("text/plain".toMediaTypeOrNull())
                val buildingIdBody = buildingId?.toRequestBody("text/plain".toMediaTypeOrNull())
                val roomIdBody = roomId?.toRequestBody("text/plain".toMediaTypeOrNull())
                val titleBody = title?.toRequestBody("text/plain".toMediaTypeOrNull())
                val contentBody = content?.toRequestBody("text/plain".toMediaTypeOrNull())
                val statusBody = status?.toRequestBody("text/plain".toMediaTypeOrNull())
                val postTypeBody = postType?.toRequestBody("text/plain".toMediaTypeOrNull())

                // Gọi API để cập nhật bài viết
                val response = RetrofitClient.apiService.updatePostUser(
                    postId,
                    userIdBody,
                    buildingIdBody,
                    roomIdBody,
                    titleBody,
                    contentBody,
                    statusBody,
                    postTypeBody,
                    videoFile,
                    photoFile
                )

                // Logging thông tin phản hồi để kiểm tra
                Log.d("updatePost", "API response code: ${response.code()}")
                Log.d("updatePost", "API response message: ${response.message()}")
                Log.d("updatePost", "API response body: ${response.body()}")

                // Kiểm tra kết quả trả về từ API
                if (response.isSuccessful) {
                    val updatedPost = response.body()
                    if (updatedPost != null) {
                        // Trả về kết quả thành công
                        _updateBookingStatusResult.postValue(Result.success(updatedPost))
                        Log.d("updatePost", "Update successful: $updatedPost")
                    } else {
                        _updateBookingStatusResult.postValue(Result.failure(Exception("Update failed")))
                        Log.e("updatePost", "Update failed: Response body is null")
                    }
                } else {
                    _updateBookingStatusResult.postValue(Result.failure(Exception("API Error: ${response.message()}")))
                    Log.e("updatePost", "API Error: ${response.message()}")
                }

            } catch (e: Exception) {
                // Nếu có lỗi trong quá trình gọi API, trả về thông báo lỗi
                _errorMessage.postValue(e.message)
                Log.e("updatePost", "Exception: ${e.message}", e)
            }
        }
    }
// thien code phan nay

fun getPostingList(userId: String) {
    viewModelScope.launch {
        try {
            val response = RetrofitClient.apiService.getPosts(userId)
            Log.d("API Response", response.toString())

            // Lấy danh sách bài viết từ response.data
            if (response.data.isNotEmpty()) {
                _posts.value = response.data
                Log.d("Posts Updated", _posts.value.toString())
            } else {
                Log.d("Posts Empty", "Không có bài đăng nào được trả về")
            }
        } catch (e: Exception) {
            Log.e("API Error", "Lỗi khi lấy danh sách bài viết: ${e.message}")
        }
    }
}

private val _postDetail = MutableLiveData<PostingDetail?>()
val postDetail: LiveData<PostingDetail?> get() = _postDetail

// Lấy chi tiết bài đăng
fun getPostDetail(postId: String) {
    viewModelScope.launch {
        try {
            val detail = RetrofitClient.apiService.getPostDetail(postId)
            Log.d("API_Response", "Detail: $detail") // In toàn bộ dữ liệu trả về
            _postDetail.value = detail
        } catch (e: Exception) {
            Log.e("getPostDetail", "Error: ${e.message}")
            _postDetail.value = null
        }
    }
}


private val _deleteStatus = MutableLiveData<Boolean?>()
val deleteStatus: MutableLiveData<Boolean?> get() = _deleteStatus

fun deletePostWithFeedback(postId: String) {
    viewModelScope.launch {
        try {
            val response = RetrofitClient.apiService.deletePost(postId)
            if (response.isSuccessful) {
                _posts.value = _posts.value.filter { it._id != postId }
                _deleteStatus.value = true // Thông báo thành công
            } else {
                _deleteStatus.value = false // Thông báo thất bại
            }
        } catch (e: Exception) {
            _deleteStatus.value = false // Xử lý lỗi
        }
    }
}
    private val _buildings = mutableStateOf<List<Building>>(emptyList())
    val buildings: State<List<Building>> = _buildings

    // Biến lưu tòa nhà đã chọn (selected building)
    private val _selectedBuilding = mutableStateOf<String?>(null)
    val selectedBuilding: State<String?> = _selectedBuilding

    private val apiService: APIService = RetrofitClient.apiService // Kết nối Retrofit instance

    // Lấy danh sách các tòa nhà
    fun getBuildings(userId: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getBuildings(userId)
                if (response.isSuccessful) {
                    val buildingsResponse = response.body()
                    _buildings.value = buildingsResponse?.data ?: emptyList()
                    Log.d("PostViewModel", "Buildings fetched: ${_buildings.value}")
                } else {
                    Log.e("PostViewModel", "Error fetching buildings: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("PostViewModel", "Exception: ${e.message}")
            }
        }
    }

    // Lưu danh sách phòng
    private val _rooms = MutableStateFlow<List<Room_post>>(emptyList())
    val rooms: StateFlow<List<Room_post>> = _rooms

    // Lấy danh sách phòng theo buildingId
    fun getRooms(buildingId: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getRooms(buildingId) // Gọi API để lấy phòng
                if (response.isSuccessful) {
                    val roomsResponse = response.body()
                    _rooms.value = roomsResponse?.data ?: emptyList()
                    Log.d("PostViewModel", "Rooms fetched: ${_rooms.value}")
                } else {
                    Log.e("PostViewModel", "Error fetching rooms: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("PostViewModel", "Exception: ${e.message}")
            }
        }
    }

    // Cập nhật tòa nhà đã chọn và lấy danh sách phòng cho tòa nhà đó
    fun setSelectedBuilding(buildingId: String) {
        _selectedBuilding.value = buildingId // Cập nhật tòa nhà đã chọn
        getRooms(buildingId) // Gọi API để lấy danh sách phòng cho tòa nhà đã chọn
    }
}