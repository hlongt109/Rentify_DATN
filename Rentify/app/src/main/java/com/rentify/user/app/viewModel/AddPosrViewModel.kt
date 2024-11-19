
package com.rentify.user.app.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.rentify.user.app.model.PostRequest
import com.rentify.user.app.model.PostResponse
import com.rentify.user.app.network.RetrofitClient
import com.rentify.user.app.repository.LoginRepository.LoginRepository
import com.rentify.user.app.repository.LoginRepository.LoginResponse
import com.rentify.user.app.utils.CheckUnit
import kotlinx.coroutines.launch
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