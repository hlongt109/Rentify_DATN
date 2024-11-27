package com.rentify.user.app.viewModel

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.LoginRepository.LoginRepository
//
//class AppViewModelProvider(
//    private val context: Context,
//    private val apiService: RetrofitService = RetrofitService()
//) {
//    val Factory: ViewModelProvider.Factory = viewModelFactory {
//        initializer {
//            val repository = LoginRepository(apiService)
//            LoginViewModel(repository, context)
//        }
//    }
//}