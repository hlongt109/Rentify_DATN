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
    private val _supports = MutableLiveData<SupportResponse>()
    val supports: LiveData<SupportResponse> get() = _supports

    // LiveData for individual support detail
    private val _supportDetail = MutableLiveData<SupportResponse?>()
    val supportDetail: MutableLiveData<SupportResponse?> get() = _supportDetail

    private val _listSupport = MutableLiveData<List<SupportResponse?>>()
    val listSupport: LiveData<List<SupportResponse?>> get() = _listSupport
    private val _listSupportMap = MutableLiveData<Map<String, List<SupportResponse?>>>()//
    val listSupportMap: LiveData<Map<String, List<SupportResponse?>>> = _listSupportMap//

    // LiveData for error messages
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _selectedSupport = MutableLiveData<SupportResponse?>()
    val selectedSupport: LiveData<SupportResponse?> = _selectedSupport

    fun fetchSupport(building_id: String, status: Int) {
        viewModelScope.launch {
            try {
                val response = apiService.getListSupport(building_id, status)
                if (response.isSuccessful && response.body() != null) {
                    val filteredList = response.body()?.filter { it?.status == status } ?: emptyList()
                    val currentMap = _listSupportMap.value.orEmpty().toMutableMap()
                    currentMap[building_id] = filteredList
                    _listSupportMap.postValue(currentMap)
                }
                else {
                    _errorMessage.postValue("Failed to load support: ${response.message()}")
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Error fetching support: ${e.message}")
            }
        }
    }

    fun fetchSupportDetail(roomId: String) {
        viewModelScope.launch {
            try {
                val response: Response<SupportResponse> = apiService.getSupportDetail(roomId)
                if (response.isSuccessful) {
                    supportDetail.value = response.body()
                } else {
                    _errorMessage.postValue("Failed to load support details: ${response.message()}")
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Error fetching support details: ${e.message}")
            }
        }
    }

    // update thay đổi status từ 0 thành 1
    fun updateSupportDetail(supportId: String, updatedSupport: SupportResponse, buildingId: String, status: Int) {
        viewModelScope.launch {
            try {
                val response: Response<SupportResponse> = apiService.updateSupport(supportId, updatedSupport)
                if (response.isSuccessful) {
                    val updatedResponse = response.body()
                    if (updatedResponse != null) {
                        _supportDetail.postValue(updatedResponse)
                        // Cập nhật danh sách hiện tại
                        _listSupport.value = _listSupport.value?.map { support ->
                            if (support?._id == supportId) {
                                updatedSupport.copy(status = status)
                            } else {
                                support
                            }
                        }
                    } else {
                        _errorMessage.postValue("Update successful but no data returned from the server.")
                    }
                } else {
                    _errorMessage.postValue("Failed to update support: ${response.message()}")
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Error updating support: ${e.message}")
            }
        }
    }



    fun setSelectedSupport(support: SupportResponse) {
        _selectedSupport.postValue(support)
    }

    fun clearSelectedSupport() {
        _selectedSupport.postValue(null)
    }

}