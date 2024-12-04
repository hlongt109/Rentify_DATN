package com.rentify.user.app.viewModel.ServiceViewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rentify.user.app.model.ListServiceResponse

import com.rentify.user.app.network.RetrofitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response


class ServiceViewmodel : ViewModel() {
    private val apiService = RetrofitService().ApiService

    // LiveData for handling error messages
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    // LiveData to hold the fetched list of services
    private val _servicesList = MutableLiveData<List<ListServiceResponse>>()
    val servicesList: LiveData<List<ListServiceResponse>> = _servicesList

    // LiveData to show loading status
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    // Function to fetch services from the API
    fun fetchServices() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Indicate that loading has started
                _isLoading.postValue(true)

                // Call the API to fetch the list of services
                val response: Response<List<ListServiceResponse>> = apiService.getServiceList()

                // Handling the response from API
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        // If successful, set the data to LiveData
                        _servicesList.value = response.body()
                    } else {
                        // If API call fails, set the error message
                        _error.value = "Failed to fetch services: ${response.message()}"
                    }
                    // Indicate that loading has finished
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                // Handle exception if any error occurs during the API call
                withContext(Dispatchers.Main) {
                    _error.value = "Error occurred: ${e.message}"
                    _isLoading.value = false
                }
            }
        }
    }
}