package com.rentify.user.app.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rentify.user.app.model.Model.ContractResponse
import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.ContractRepository
import kotlinx.coroutines.launch

class ContractViewModel : ViewModel() {
    private val apiService = RetrofitService().ApiService
    private val contractRepository = ContractRepository(apiService)

    private val _contract = MutableLiveData<List<ContractResponse>>()
    val contract: LiveData<List<ContractResponse>> get() = _contract

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun getContractDetails(user_id: String){
        viewModelScope.launch {
            try {
                val response = contractRepository.getContractDetails(user_id)
                if(response.isSuccessful && response.body() != null){
                    _contract.value = response.body()
                }else{
                    _errorMessage.postValue("Failed to fetch contract: ${response.message()}")
                }
            }catch (e: Exception){
                _errorMessage.postValue("Failed to fetch contract: ${e.message}")
            }
        }
    }
}