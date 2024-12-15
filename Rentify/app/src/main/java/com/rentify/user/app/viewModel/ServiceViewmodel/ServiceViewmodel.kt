package com.rentify.user.app.viewModel.ServiceViewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rentify.user.app.model.ServiceAdmin.AdminService
import com.rentify.user.app.network.RetrofitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class ServiceViewModel : ViewModel() {
    private val apiService = RetrofitService().ApiService

    // LiveData for handling error messages
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    // LiveData to hold the fetched service
    private val _service = MutableLiveData<AdminService>()
    val service: LiveData<AdminService> get() = _service

    // LiveData to show loading status
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    // Function to fetch services by admin ID
    fun fetchServiceByAdminId(adminId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Indicate loading has started
                _isLoading.postValue(true)

                // Call the API to fetch the services
                val response: Response<AdminService> = apiService.service_adm(adminId)

                // Handle the response from the API
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        // If successful, set the data to LiveData
                        _service.value = response.body()
                    } else {
                        // If API call fails, set the error message
                        _error.value = "Failed to fetch services: ${response.message()}"
                    }
                    // Indicate loading has finished
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                // Handle any exceptions during the API call
                withContext(Dispatchers.Main) {
                    _error.value = "Error occurred: ${e.message}"
                    _isLoading.value = false
                }
            }
        }
    }
}


