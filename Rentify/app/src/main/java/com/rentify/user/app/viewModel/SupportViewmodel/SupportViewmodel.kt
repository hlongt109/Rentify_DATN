package com.rentify.user.app.viewModel.SupportViewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rentify.user.app.model.SupportModel.SupportResponse
import com.rentify.user.app.network.RetrofitService
import kotlinx.coroutines.launch
import retrofit2.Response

class SupportViewModel : ViewModel() {
    // Retrofit API service instance
    private val apiService = RetrofitService().ApiService

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    // LiveData for supports list
    private val _supports = MutableLiveData<List<SupportResponse>?>()
    val supports: MutableLiveData<List<SupportResponse>?> get() = _supports

    // LiveData for individual support detail
    private val _supportDetail = MutableLiveData<SupportResponse>()
    val supportDetail: LiveData<SupportResponse> get() = _supportDetail

    // LiveData for error messages
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun fetchSupportDetail(roomId: String) {
        viewModelScope.launch {
            try {
                // Make the API call to fetch support details
                val response: Response<List<SupportResponse>> = apiService.getSupportDetail(roomId)

                // Check if the response is successful
                if (response.isSuccessful) {
                    val supportsList = response.body()

                    if (supportsList.isNullOrEmpty()) {
                        _errorMessage.postValue("No support details found for this room.")
                    } else {
                        // Post the support details (if available)
                        _supports.postValue(supportsList)

                        // Post the first support detail, or adjust based on your requirement
                        _supportDetail.postValue(supportsList.first())
                    }
                } else {
                    // Handle failure case (API response is not successful)
                    _errorMessage.postValue("Failed to load support details: ${response.message()}")
                }
            } catch (e: Exception) {
                // Handle network errors or exceptions (e.g., no internet connection)
                _errorMessage.postValue("Error fetching support details: ${e.message}")
            }
        }
    }
}
