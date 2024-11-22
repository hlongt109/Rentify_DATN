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
import androidx.lifecycle.liveData
import com.rentify.user.app.model.PostingDetail
import com.rentify.user.app.network.ApiClient.apiService
import okhttp3.MultipartBody
import okhttp3.RequestBody

class PostViewModel : ViewModel() {
    private val _posts = mutableStateOf<List<PostingList>>(emptyList())
    val posts: State<List<PostingList>> = _posts

    fun getPostingList(userId: String) {
        viewModelScope.launch {
            try {
                val posts = RetrofitClient.apiService.getPosts(userId)
                _posts.value = posts
            } catch (e: Exception) {
                // Xử lý lỗi, có thể hiển thị thông báo lỗi ở đây
            }
        }
    }
    fun deletePost(postId: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.deletePost(postId)
                if (response.isSuccessful) {
                    // Cập nhật danh sách sau khi xóa
                    _posts.value = _posts.value.filter { it._id != postId }
                } else {
                    Log.e("deletePost", "Error: ${response.code()} - ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("deletePost", "Exception: ${e.message}")
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
    }}fun resetDeleteStatus() {
        _deleteStatus.value = null
    }
    private val _updateStatus = MutableLiveData<Boolean?>()
    val updateStatus: LiveData<Boolean?> get() = _updateStatus

    fun updatePost(
        postId: String,
        userId: RequestBody,
        title: RequestBody,
        content: RequestBody,
        status: RequestBody,
        postType: RequestBody,
        price: RequestBody?,
        address: RequestBody?,
        phoneNumber: RequestBody?,
        roomType: RequestBody?,
        amenities: RequestBody?,
        services: RequestBody?,
        videos: List<MultipartBody.Part>?,
        photos: List<MultipartBody.Part>?
    ) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.updatePost(
                    postId, userId, title, content, status, postType,
                    price, address, phoneNumber, roomType,
                    amenities, services, videos, photos
                )
                if (response.isSuccessful) {
                    _updateStatus.value = true // Cập nhật thành công
                } else {
                    Log.e("updatePost", "Error: ${response.code()} - ${response.errorBody()?.string()}")
                    _updateStatus.value = false // Cập nhật thất bại
                }
            } catch (e: Exception) {
                Log.e("updatePost", "Exception: ${e.message}")
                _updateStatus.value = false // Xử lý lỗi
            }
        }
    }

    /**
     * Reset trạng thái cập nhật để tránh thông báo lặp lại
     */
    fun resetUpdateStatus() {
        _updateStatus.value = null
    }
}


