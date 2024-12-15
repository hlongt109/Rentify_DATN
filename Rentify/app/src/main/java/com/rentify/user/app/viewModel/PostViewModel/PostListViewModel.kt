package com.rentify.user.app.viewModel.PostViewModel
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rentify.user.app.network.RetrofitClient
import com.rentify.user.app.view.staffScreens.postingList.PostingListComponents.PostingList
import kotlinx.coroutines.launch
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
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
    // Trạng thái bài viết
    private val _posts = mutableStateOf<List<PostingList>>(emptyList())
    val posts: State<List<PostingList>> = _posts

    // Các trạng thái bài viết theo từng loại
    private val _pendingPosts = mutableStateOf<List<PostingList>>(emptyList())
    val pendingPosts: State<List<PostingList>> = _pendingPosts

    private val _activePosts = mutableStateOf<List<PostingList>>(emptyList())
    val activePosts: State<List<PostingList>> = _activePosts

    private val _hiddenPosts = mutableStateOf<List<PostingList>>(emptyList())
    val hiddenPosts: State<List<PostingList>> = _hiddenPosts

    // Lỗi chung
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    // Trạng thái xóa bài đăng
    private val _deleteStatus = MutableLiveData<Boolean?>()
    val deleteStatus: LiveData<Boolean?> get() = _deleteStatus

    // Trạng thái tải
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    // Chi tiết bài đăng
    private val _postDetail = MutableLiveData<PostingList?>()
    val postDetail: LiveData<PostingList?> get() = _postDetail

    var address by mutableStateOf<String?>(null)
    var content by mutableStateOf<String?>(null)
    var images by mutableStateOf<List<String>?>(null)
    var videos by mutableStateOf<List<String>?>(null)

    // Lấy danh sách bài viết của người dùng
    fun getPostingList(userId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitClient.apiService.getPosts(userId)
                if (response.data.isNotEmpty()) {
                    _posts.value = response.data
                } else {
                    _errorMessage.postValue("Không có bài đăng nào được trả về.")
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Lỗi khi lấy danh sách bài viết: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Lấy danh sách bài viết theo trạng thái
    fun getPostingList_user(userId: String, postType: String? = null) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitClient.apiService.getPosts_user(userId, postType)
                if (response.status == 200 && response.data.isNotEmpty()) {
                    _posts.value = response.data
                    _pendingPosts.value = response.data.filter { it.status == 0 }
                    _activePosts.value = response.data.filter { it.status == 1 }
                    _hiddenPosts.value = response.data.filter { it.status == 2 }
                } else {
                    _errorMessage.postValue("Không có bài đăng nào được trả về.")
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Lỗi khi lấy danh sách bài viết: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Lấy chi tiết bài đăng
    fun getPostDetail(postId: String) {
        viewModelScope.launch {
            try {
                val detail = RetrofitClient.apiService.getPostDetail(postId)
                _postDetail.value = detail
            } catch (e: Exception) {
                _postDetail.value = null
                _errorMessage.postValue("Lỗi khi lấy chi tiết bài đăng: ${e.message}")
            }
        }
    }

    private val _updateStatus = MutableLiveData<Boolean?>() // Sử dụng Boolean? để kiểm tra null
    val updateStatus: LiveData<Boolean?> = _updateStatus
    private val _pendingUpdates = MutableLiveData(0) // Số lần cập nhật đang chờ xử lý
    fun updatePost(
        postId: String,
        userId: String?,
        buildingId: String?,
        roomId: String?,
        title: String?,
        address: String?,
        content: String?,
        status: String?,
        postType: String?,
        videoFile: List<MultipartBody.Part>?,
        photoFile: List<MultipartBody.Part>?
    ) {
        viewModelScope.launch {
            _pendingUpdates.value = (_pendingUpdates.value ?: 0) + 1 // Tăng số cập nhật đang chờ xử lý

            try {
                // Chuẩn bị dữ liệu gửi đi
                val userIdBody = userId?.toRequestBody("text/plain".toMediaTypeOrNull())
                val buildingIdBody = buildingId?.toRequestBody("text/plain".toMediaTypeOrNull())
                val roomIdBody = roomId?.toRequestBody("text/plain".toMediaTypeOrNull())
                val titleBody = title?.toRequestBody("text/plain".toMediaTypeOrNull())
                val addressBody = address?.toRequestBody("text/plain".toMediaTypeOrNull())
                val contentBody = content?.toRequestBody("text/plain".toMediaTypeOrNull())
                val statusBody = status?.toRequestBody("text/plain".toMediaTypeOrNull())
                val postTypeBody = postType?.toRequestBody("text/plain".toMediaTypeOrNull())

                // Gửi yêu cầu cập nhật
                val response = RetrofitClient.apiService.updatePost(
                    postId,
                    userIdBody,
                    buildingIdBody,
                    roomIdBody,
                    titleBody,
                    addressBody,
                    contentBody,
                    statusBody,
                    postTypeBody,
                    videoFile,
                    photoFile
                )

                if (response.isSuccessful && response.body() != null) {
                    _postDetail.value = response.body()
                    _updateStatus.value = true // Thành công
                    getPostDetail(postId)
                    _errorMessage.postValue("Cập nhật bài viết thành công.")
                    Log.d("CheckUpdate", "updatePost: ${response.body()}")
                    resetUpdateStatus()
                    if (userId != null) {
                        getPostingList_user(userId, postType = postType)
                    }
                } else {
                    _updateStatus.value = false // Thất bại
                    _errorMessage.postValue("Cập nhật thất bại: ${response.message()}")
                }
            } catch (e: Exception) {
                _updateStatus.value = false // Lỗi
                _errorMessage.postValue("Lỗi khi cập nhật bài viết: ${e.message}")
            } finally {
                // Giảm số cập nhật chờ xử lý
                val pending = (_pendingUpdates.value ?: 1) - 1
                _pendingUpdates.value = pending

                // Chỉ tải lại dữ liệu khi tất cả cập nhật hoàn tất
                if (pending == 0) {
                    getPostDetail(postId) // Tải lại thông tin bài đăng
                    if (userId != null) {
                        getPostingList_user(userId, postType = postType)
                    }
                }
            }
        }
    }


    fun resetUpdateStatus() {
        _updateStatus.value = null // Reset trạng thái sau khi xử lý
    }

    // Xóa bài viết với phản hồi
    fun deletePostWithFeedback(postId: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.deletePost(postId)
                if (response.isSuccessful) {
                    _posts.value = _posts.value.filter { it._id != postId }
                    _deleteStatus.value = true
                } else {
                    _deleteStatus.value = false
                }
            } catch (e: Exception) {
                _deleteStatus.value = false
            }
        }
    }


    fun deletePostWithFeedback_user(postId: String) {
        viewModelScope.launch {
            try {
                val response = apiService.deletePost(postId)
                if (response.isSuccessful) {
                    // Loại bỏ bài đăng khỏi danh sách hiện tại
                    _pendingPosts.value = _pendingPosts.value.filter { it._id != postId }
                    _activePosts.value = _activePosts.value.filter { it._id != postId }
                    _hiddenPosts.value = _hiddenPosts.value.filter { it._id != postId }
                }
            } catch (e: Exception) {
                Log.e("DeletePost", "Error: $e")
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

    private val _roomsFromContracts = MutableStateFlow<List<Room_post>>(emptyList())
    val roomsFromContracts: StateFlow<List<Room_post>> = _roomsFromContracts

    fun fetchRoomsFromContracts(userId: String) {
        viewModelScope.launch {
            try {
                // Gọi API lấy hợp đồng
                val response = apiService.getContracts(userId)
                if (response.isSuccessful) {
                    val contracts = response.body()?.data ?: emptyList()

                    // Lấy danh sách các phòng từ hợp đồng
                    val rooms = contracts.mapNotNull { it.room_id }
                    _roomsFromContracts.value = rooms
                    //       fetchBuildings(contracts)
                } else {
                    Log.e("PostViewModel", "Error fetching contracts: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("PostViewModel", "Exception: ${e.message}")
            }
        }
    }

    private val _buildingss = MutableStateFlow<List<Building>>(emptyList())
    val buildingss: StateFlow<List<Building>> = _buildingss

    fun fetchBuildingForRoom(roomId: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getBuildingFromRoom(roomId)
                if (response.isSuccessful) {
                    response.body()?.let { building ->
                        _buildingss.value = listOf(building) // Cập nhật danh sách tòa nhà
                        Log.d("PostViewModel", "Building fetched: $building")
                    } ?: Log.e("PostViewModel", "Building is null")
                } else {
                    Log.e("PostViewModel", "Error fetching building: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("PostViewModel", "Error fetching building: ${e.message}")
            }
        }
    }

    fun updateSelectedBuilding(buildingId: String) {
        _selectedBuilding.value = buildingId
    }

    //update user
    fun updatePost_user(
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
                val response = RetrofitClient.apiService.updatePostuser(
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

                if (response.isSuccessful && response.body() != null) {
                    _postDetail.value = response.body()
                    if (userId != null) {
                        getPostDetail(postId)
                    }
                    Log.d("aaaaa", "API response code: ${userId} postType${postType}")
                    _errorMessage.postValue("Cập nhật bài viết thành công.")
                } else {
                    _errorMessage.postValue("Cập nhật thất bại: ${response.message()}")
                }

            } catch (e: Exception) {
                // Nếu có lỗi trong quá trình gọi API, trả về thông báo lỗi
                _errorMessage.postValue(e.message)
                Log.e("updatePost", "Exception: ${e.message}", e)
            }
        }
    }

    val searchQuery = mutableStateOf("")

    // LiveData để chứa kết quả tìm kiếm bài đăng


    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error
    fun searchPosts(query: String, userId: String? = null) {
        viewModelScope.launch {
            try {
                // Gọi API với tham số `query` và `userId`
                val response = if (userId != null) {
                    apiService.searchPosts(query, userId)
                } else {
                    apiService.searchPosts(query)
                }

                if (response.isSuccessful) {
                    // Kiểm tra nếu body của phản hồi không null
                    val data = response.body()
                    if (!data.isNullOrEmpty()) {
                        Log.d("API Response", "Query: $query, UserId: $userId, Data: $data")
                        _posts.value = data // Gán danh sách bài đăng vào LiveData
                    } else {
                        Log.e("API Response Error", "Query: $query, UserId: $userId, No data found")
                        _posts.value = emptyList()
                        _error.postValue("Không tìm thấy bài đăng nào")
                    }
                } else {
                    Log.e(
                        "API Response Error",
                        "Query: $query, UserId: $userId, Code: ${response.code()}"
                    )
                    _posts.value = emptyList()
                    _error.postValue("Không tìm thấy bài đăng nào (Mã lỗi: ${response.code()})")
                }
            } catch (e: Exception) {
                // Xử lý lỗi khác (ví dụ: lỗi mạng, lỗi parse JSON)
                Log.e("API Error", "Lỗi khi tìm kiếm bài đăng: ${e.message}")
                _error.postValue("Lỗi khi tìm kiếm bài đăng: ${e.message}")
            }
        }
    }


    /**
     * Xử lý khi giá trị tìm kiếm thay đổi
     */
    fun onSearchQueryChange(newQuery: String) {
        searchQuery.value = newQuery
        if (newQuery.isNotEmpty()) {
            searchPosts(newQuery) // Tự động tìm kiếm khi giá trị thay đổi
        }
    }


    ////
    private fun updatePostLists() {
        val posts = _posts.value
        _pendingPosts.value = posts.filter { it.status == 0 }
        _activePosts.value = posts.filter { it.status == 1 }
        _hiddenPosts.value = posts.filter { it.status == 2 }
    }
}