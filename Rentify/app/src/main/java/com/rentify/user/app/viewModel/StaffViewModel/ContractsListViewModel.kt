package com.rentify.user.app.viewModel.StaffViewModel
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
import com.rentify.user.app.model.Building
import com.rentify.user.app.model.Contract
import com.rentify.user.app.model.PostingDetail
import com.rentify.user.app.model.Room_post
import com.rentify.user.app.model.UpdatePostRequest
import com.rentify.user.app.network.APIService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

import retrofit2.HttpException
import java.io.IOException
class ContractViewModel : ViewModel() {

    private val _contracts = MutableLiveData<List<Contract>>()
    val contracts: LiveData<List<Contract>> = _contracts

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error
    private val _contractDetail = MutableLiveData<Contract>()
    val contractDetail: LiveData<Contract> = _contractDetail
    fun fetchContractsByBuilding(manageId: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getContractsByBuilding(manageId)
                if (response.isSuccessful) {
                    _contracts.value = response.body() ?: emptyList()
                    Log.d("ContractViewModel", "Contracts fetched: ${_contracts.value}")
                } else {
                    _error.value = "Error: ${response.message()}"
                    Log.e("ContractViewModel", "Error fetching contracts: ${response.message()}")
                }
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
                Log.e("ContractViewModel", "Error fetching contracts: ${e.message}")
            }
        }
    }
    fun fetchContractDetail(contractId: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getContractDetail(contractId)
                if (response.isSuccessful) {
                    _contractDetail.value = response.body()
                } else {
                    Log.e("ContractViewModel", "Error fetching contract details: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("ContractViewModel", "Error fetching contract details: ${e.message}")
            }
        }
    }
}
