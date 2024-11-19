import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
//class AddPostViewModel : ViewModel() {
//    private val apiService = RetrofitClient.instance
//
//    fun addPost(
//        postRequest: PostRequest,
//        onSuccess: (PostResponse) -> Unit,
//        onError: (Throwable) -> Unit
//    ) {
//        viewModelScope.launch {
//            try {
//                val response = apiService.addPost(postRequest)
//                onSuccess(response)
//            } catch (e: Exception) {
//                onError(e)
//            }
//        }
//    }
//}

