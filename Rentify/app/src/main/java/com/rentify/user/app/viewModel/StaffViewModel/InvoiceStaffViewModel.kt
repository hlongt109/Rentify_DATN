package com.rentify.user.app.viewModel.StaffViewModel

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.rentify.user.app.repository.LoginRepository.LoginRepository
import com.rentify.user.app.repository.StaffRepository.InvoiceRepository.Invoice
import com.rentify.user.app.repository.StaffRepository.InvoiceRepository.InvoiceData
import com.rentify.user.app.repository.StaffRepository.InvoiceRepository.InvoiceRepository
import com.rentify.user.app.repository.StaffRepository.InvoiceRepository.InvoiceStatus
import com.rentify.user.app.repository.StaffRepository.InvoiceRepository.ServiceDetail
import com.rentify.user.app.viewModel.LoginViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class InvoiceUiState {
    object Loading : InvoiceUiState()
    data class Success(val data: InvoiceData) : InvoiceUiState()
    data class Error(val message: String) : InvoiceUiState()
}

enum class InvoiceStatus {
    PAID  , UNPAID
}
class InvoiceStaffViewModel(
    private val repository: InvoiceRepository = InvoiceRepository()
) : ViewModel() {


    private val _uiState = MutableStateFlow<InvoiceUiState>(InvoiceUiState.Loading)
    val uiState: StateFlow<InvoiceUiState> = _uiState.asStateFlow()

    // StateFlow lưu danh sách hóa đơn đã phân loại
    private val _paidInvoices = MutableStateFlow<List<Invoice>>(emptyList())
    val paidInvoices: StateFlow<List<Invoice>> = _paidInvoices.asStateFlow()

    private val _unpaidInvoices = MutableStateFlow<List<Invoice>>(emptyList())
    val unpaidInvoices: StateFlow<List<Invoice>> = _unpaidInvoices.asStateFlow()

    fun getInvoiceList(staffId: String) {
        viewModelScope.launch {
            _uiState.value = InvoiceUiState.Loading
            repository.getListInvoice(staffId).fold(
                onSuccess = { response ->
                    // Kiểm tra và xử lý dữ liệu từ InvoiceData
                    if (response.status == 200 && response.data != null) {
                        _uiState.value = InvoiceUiState.Success(response.data)

                        // Phân loại hóa đơn dựa trên danh sách paid và unpaid
                        _paidInvoices.value = response.data.paid
                        _unpaidInvoices.value = response.data.unpaid
                    } else {
                        _uiState.value = InvoiceUiState.Error(response.message ?: "Không có dữ liệu")
                    }
                },
                onFailure = { exception ->
                    _uiState.value = InvoiceUiState.Error(exception.message ?: "Lỗi không xác định")
                }
            )
        }
    }
    class InvoiceStaffViewModelFactory(
        private val repository: InvoiceRepository,
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(InvoiceStaffViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return InvoiceStaffViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

