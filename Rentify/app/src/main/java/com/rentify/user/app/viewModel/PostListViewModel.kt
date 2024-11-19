package com.rentify.user.app.viewModel
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
import com.rentify.user.app.model.PostingDetail

class PostViewModel : ViewModel() {
    private val _rooms = mutableStateOf<List<PostingList>>(emptyList())
    val rooms: State<List<PostingList>> = _rooms

    fun getPostingList(userId: String) {
        viewModelScope.launch {
            try {
                val posts = RetrofitClient.apiService.getPosts(userId)
                _rooms.value = posts
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
                    _rooms.value = _rooms.value.filter { it._id != postId }
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
                _rooms.value = _rooms.value.filter { it._id != postId }
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
}
