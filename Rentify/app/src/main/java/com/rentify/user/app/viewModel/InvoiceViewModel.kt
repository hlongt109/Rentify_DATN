package com.rentify.user.app.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rentify.user.app.model.Model.InvoiceResponse
import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.InvoiceRepository
import kotlinx.coroutines.launch

class InvoiceViewModel : ViewModel() {
    private val apiService = RetrofitService().ApiService
    private val invoiceRepository = InvoiceRepository(apiService)

    private val _listInvoice = MutableLiveData<List<InvoiceResponse>>()
    val listInvoice: LiveData<List<InvoiceResponse>> get() = _listInvoice

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun fetchListInvoice(user_id: String, status: String){
        viewModelScope.launch {
            try {
                val response = invoiceRepository.getListInvoice(user_id, status)
                if(response.isSuccessful && response.body() != null){
                    _listInvoice.value = response.body()
                }else{
                    _errorMessage.value = "Failed to fetch invoice: ${response.message()}"
                }
            }catch (e: Exception){
                _errorMessage.value = "Failed to fetch invoice: ${e.message}"
            }
        }
    }

}