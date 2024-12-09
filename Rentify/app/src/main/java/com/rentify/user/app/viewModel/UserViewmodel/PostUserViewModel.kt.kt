
package com.rentify.user.app.viewModel.UserViewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rentify.user.app.model.Post
import com.rentify.user.app.model.PostResponse
import com.rentify.user.app.network.APIService
import com.rentify.user.app.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.compose.runtime.State
import kotlinx.coroutines.launch

class PostUserViewModel : ViewModel() {
    private val _posts = mutableStateOf<List<PostResponse>>(emptyList())
    val posts: State<List<PostResponse>> get() = _posts

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    /**
     * Lấy danh sách bài viết theo loại
     */
    fun fetchPostsByType(postType: String) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = RetrofitClient.apiService.getPostsByType(postType)
                Log.d("API Response Code", response.code().toString()) // Log mã trạng thái HTTP
                if (response.isSuccessful) {
                    val postListResponse = response.body()
                    Log.d("API Response", postListResponse.toString())

                    // Kiểm tra nếu posts là null hoặc danh sách rỗng
                    if (postListResponse != null && postListResponse.formattedPosts.isNotEmpty()) {
                        _posts.value = postListResponse.formattedPosts
                    } else {
                        Log.d("API Response", "No posts found or posts is empty")
                        _posts.value = listOf() // Trả về danh sách trống nếu không có dữ liệu
                    }
                } else {
                    _errorMessage.value = "API error: ${response.message()}"
                    Log.d("API Error", "Error: ${response.message()}")
                }
            } catch (e: Exception) {
                _errorMessage.value = "Lỗi khi gọi API: ${e.message}"
                Log.e("API Exception", "Exception occurred: ${e.message}")
            } finally {
                _loading.value = false
            }
        }
    }

}
