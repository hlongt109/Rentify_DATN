package com.rentify.user.app.viewModel.StaffViewModel


import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rentify.user.app.model.Model.InvoiceUpdate
import com.rentify.user.app.model.Model.InvoiceUpdateRequest
import com.rentify.user.app.model.Model.UpdateInvoice
import com.rentify.user.app.model.Model.UpdateStatus
import com.rentify.user.app.network.ApiStaff.ApiServiceStaff
import com.rentify.user.app.network.ApiStaff.RetrofitStaffService
import com.rentify.user.app.repository.LoginRepository.LoginRepository
import com.rentify.user.app.repository.StaffRepository.BuildingRepository.ServiceFee
import com.rentify.user.app.repository.StaffRepository.InvoiceRepository.Description
import com.rentify.user.app.repository.StaffRepository.InvoiceRepository.DetailInvoice
import com.rentify.user.app.repository.StaffRepository.InvoiceRepository.Invoice
import com.rentify.user.app.repository.StaffRepository.InvoiceRepository.InvoiceAdd
import com.rentify.user.app.repository.StaffRepository.InvoiceRepository.InvoiceData
import com.rentify.user.app.repository.StaffRepository.InvoiceRepository.InvoiceRepository
import com.rentify.user.app.repository.StaffRepository.InvoiceRepository.InvoiceResponse
import com.rentify.user.app.repository.StaffRepository.InvoiceRepository.InvoiceStatus
import com.rentify.user.app.repository.StaffRepository.InvoiceRepository.RoomDetail
import com.rentify.user.app.repository.StaffRepository.InvoiceRepository.ServiceDetail
import com.rentify.user.app.repository.StaffRepository.RoomRepository.Room
import com.rentify.user.app.repository.StaffRepository.RoomRepository.RoomStaffRepository
import com.rentify.user.app.viewModel.LoginViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import kotlin.math.log


sealed class InvoiceUiState {
    object Loading : InvoiceUiState()
    data class Success(val data: InvoiceData) : InvoiceUiState()
    data class Error(val message: String) : InvoiceUiState()
}


enum class InvoiceStatus {
    PAID, UNPAID
}


class InvoiceStaffViewModel(
    private val repository: InvoiceRepository = InvoiceRepository(),
) : ViewModel() {

    private val api: ApiServiceStaff = RetrofitStaffService.ApiService


    private val _uiState = MutableStateFlow<InvoiceUiState>(InvoiceUiState.Loading)
    val uiState: StateFlow<InvoiceUiState> = _uiState.asStateFlow()


    // StateFlow lưu danh sách hóa đơn đã phân loại
    private val _paidInvoices = MutableStateFlow<List<Invoice>>(emptyList())
    val paidInvoices: StateFlow<List<Invoice>> = _paidInvoices.asStateFlow()


    private val _unpaidInvoices = MutableStateFlow<List<Invoice>>(emptyList())
    val unpaidInvoices: StateFlow<List<Invoice>> = _unpaidInvoices.asStateFlow()

    private val _waitInvoices = MutableStateFlow<List<Invoice>>(emptyList())
    val waitInvoices: StateFlow<List<Invoice>> = _waitInvoices.asStateFlow()

    private val _addBillResult = MutableStateFlow<Result<InvoiceResponse>?>(null)
    val addBillResult: StateFlow<Result<InvoiceResponse>?> = _addBillResult


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage


    private val _successMessage = MutableLiveData<String?>()
    val successMessage: LiveData<String?> = _successMessage


    private val _waterErrorMessage = MutableLiveData<String?>()
    val waterErrorMessage: LiveData<String?> = _waterErrorMessage


    private val _elecErrorMessage = MutableLiveData<String?>()
    val elecErrorMessage: LiveData<String?> = _elecErrorMessage


    private val _oldWaterErrorMessage = MutableLiveData<String?>()
    val oldWaterErrorMessage: LiveData<String?> = _oldWaterErrorMessage


    private val _oldElecErrorMessage = MutableLiveData<String?>()
    val oldElecErrorMessage: LiveData<String?> = _oldElecErrorMessage


    private val _roomErrorMessage = MutableLiveData<String?>()
    val roomErorMessage: LiveData<String?> = _roomErrorMessage


    private val _buildingErrorMessage = MutableLiveData<String?>()
    val buildingErrorMessage: LiveData<String?> = _buildingErrorMessage


    private val _dateErrorMessage = MutableLiveData<String?>()
    val dateErrorMessage: LiveData<String?> = _dateErrorMessage

    private val _describeErrorMessage = MutableLiveData<String?>()
    val describeErrorMessage: LiveData<String?> = _describeErrorMessage

    private val _roomsWithoutInvoice = MutableStateFlow<List<Room>>(emptyList())
    val roomsWithoutInvoice: StateFlow<List<Room>> = _roomsWithoutInvoice.asStateFlow()

    //lay chi tiet hoa don
    private val _invoiceDetail = MutableLiveData<UpdateInvoice>()
    val invoiceDetail: LiveData<UpdateInvoice> get() = _invoiceDetail

    private val _updatedInvoice = MutableLiveData<InvoiceUpdate>()
    val updatedInvoice: LiveData<InvoiceUpdate> get() = _updatedInvoice

    fun updateInvoice(invoiceId: String, invoiceUpdateRequest: InvoiceUpdateRequest) {
        _isLoading.postValue(true)
        viewModelScope.launch {
            try {
                val response = api.partialUpdateInvoice(invoiceId, invoiceUpdateRequest)
                if (response.isSuccessful) {
                    _updatedInvoice.value = response.body()
                    _successMessage.value = "Chỉnh sửa hóa đơn thành công"
                } else {
                    _errorMessage.value = "Cập nhật hóa đơn thất bại: ${response.errorBody()?.string()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Lỗi khi cập nhật hóa đơn: ${e.message}"
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun getDetailInvoice(invoiceId: String) {
        _isLoading.postValue(true)
        viewModelScope.launch {
            try {
                val response = api.getDetailInvoice(invoiceId)
                Log.d("dcmm", "Invoice details fetched successfully: ${response}")
                _invoiceDetail.value = response.data
            } catch (e: Exception) {
                _errorMessage.postValue("Failed to fetch invoice detail: ${e.message}")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun getInvoiceList(staffId: String) {
        viewModelScope.launch {
//           _isLoading.postValue(true)
            repository.getListInvoice(staffId).fold(
                onSuccess = { response ->
                    // Kiểm tra và xử lý dữ liệu từ InvoiceData
                    if (response.status == 200 && response.data != null) {
                        _uiState.value = InvoiceUiState.Success(response.data)
                        // Phân loại hóa đơn dựa trên danh sách paid và unpaid
                        _paidInvoices.value = response.data.paid
                        _unpaidInvoices.value = response.data.unpaid
                        _waitInvoices.value = response.data.wait
                    } else {
                        Log.d("Error", "getInvoiceList: ${response.message}")
                        _uiState.value = InvoiceUiState.Error(response.message ?: "Không có dữ liệu")
                        _errorMessage.postValue(response.message)
                    }
                },
                onFailure = { exception ->
                    Log.d("Ex", "getInvoiceList: ${exception.message}")
                    _uiState.value = InvoiceUiState.Error(exception.message ?: "Lỗi không xác định")
                   _errorMessage.postValue(exception.message ?: "Lỗi không xác định")
                }
            )
        }
    }

    fun addBill(
        userId: String,
        roomId: String,
        buildingId: String,
        describe: String,
        consumeElec: Int,
        totalElec: Double,
        consumeWater: Int,
        totalWater: Double,
        roomPrice: Double,
        serviceFees: List<ServiceFee>,
        amount: Double,
        dueDate: String,
        pricePreUnitWater: Double,
        pricePreUnitElec: Double,
        buildingName: String,
        roomName: String,
        water: Int?,
        elec: Int?,
        oldWater: Int?,
        oldElec: Int?
    ) {
        viewModelScope.launch {
            _isLoading.postValue(true)
            try {
                // Kiểm tra giá trị hợp lệ
                if (dueDate.isEmpty() || buildingName.isEmpty() || roomName.isEmpty() || water == null || elec == null || oldWater == null || oldElec == null || describe.isEmpty()) {
                    when {
                        buildingName.isEmpty() -> {
                            _buildingErrorMessage.postValue("Tòa nhà không được để trống")
                            _isLoading.postValue(false)
                            return@launch
                        }

                        describe.isEmpty() -> {
                            _describeErrorMessage.postValue("Mô tả không được để trống")
                            _isLoading.postValue(false)
                            return@launch
                        }

                        roomName.isEmpty() -> {
                            _roomErrorMessage.postValue("Phòng không được để trống")
                            _isLoading.postValue(false)
                            return@launch
                        }


                        water == null || water == 0 -> {
                            _waterErrorMessage.postValue("Số nước không được để trống")
                            _isLoading.postValue(false)
                            return@launch
                        }


                        water < 0 -> {
                            _waterErrorMessage.postValue("Số nước không được âm")
                            _isLoading.postValue(false)
                            return@launch
                        }


                        elec == null || elec == 0 -> {
                            _elecErrorMessage.postValue("Số điện không được để trống")
                            _isLoading.postValue(false)
                            return@launch
                        }


                        elec < 0 -> {
                            _elecErrorMessage.postValue("Số điện không được âm")
                            _isLoading.postValue(false)
                            return@launch
                        }


                        oldWater == null || oldWater == 0 -> {
                            _oldWaterErrorMessage.postValue("Số nước không được để trống")
                            _isLoading.postValue(false)
                            return@launch
                        }


                        oldWater < 0 -> {
                            _oldWaterErrorMessage.postValue("Số nước không được âm")
                            _isLoading.postValue(false)
                            return@launch
                        }


                        oldElec == null || oldElec == 0 -> {
                            _oldElecErrorMessage.postValue("Số điện không được để trống")
                            _isLoading.postValue(false)
                            return@launch
                        }

                        oldElec < 0 -> {
                            _oldElecErrorMessage.postValue("Số điện không được âm")
                            _isLoading.postValue(false)
                            return@launch
                        }

                        water < oldWater -> {
                            _waterErrorMessage.postValue("Số nước không được giảm")
                            _isLoading.postValue(false)
                            return@launch
                        }


                        elec < oldElec -> {
                            _elecErrorMessage.postValue("Số điện không được giảm")
                            _isLoading.postValue(false)
                            return@launch
                        }

                        !water.isNumber() -> {
                            _waterErrorMessage.postValue("Số nước phải là số hợp lệ")
                        }

                        !elec.isNumber() -> {
                            _elecErrorMessage.postValue("Số điện phải là số hợp lệ")
                        }

                        !oldWater.isNumber() -> {
                            _oldWaterErrorMessage.postValue("Số nước phải là số hợp lệ")
                        }

                        !oldElec.isNumber() -> {
                            _oldElecErrorMessage.postValue("Số điện phải là số hợp lệ")
                        }

                        dueDate.isEmpty() -> {
                            _dateErrorMessage.postValue("Vui lòng nhập hạn thanh toán")
                            _isLoading.postValue(false)
                            return@launch
                        }
                    }
                }


                val otherService = serviceFees
                    .filter { it.name.toLowerCase() !in listOf("điện", "nước") }
                    .map { service ->
                        Description(
                            service_name = service.name,
                            quantity = 1,
                            price_per_unit = service.price,
                            total = service.price
                        )
                    }


                // Tạo danh sách mô tả dịch vụ
                val descriptions = listOf(
                    Description(
                        service_name = "Điện",
                        quantity = consumeElec,
                        price_per_unit = pricePreUnitElec,
                        total = totalElec
                    ),
                    Description(
                        service_name = "Nước",
                        quantity = consumeWater,
                        price_per_unit = pricePreUnitWater,
                        total = totalWater
                    )
                ) + otherService

                // Tạo hóa đơn
                val invoice = InvoiceAdd(
                    _id = "",
                    user_id = userId,
                    room_id = roomId,
                    amount = amount,
                    due_date = dueDate,
                    description = descriptions,
                    payment_status = "unpaid",
                    transaction_type = "income",
                    created_at = "",
                    building_id = buildingId,
                    describe = describe,
                    type_invoice = "rent"
                )
                // Gọi API để thêm hóa đơn
                _uiState.value = InvoiceUiState.Loading
                repository.addBillStaff(invoice).fold(
                    onSuccess = { response ->
                        if (response.status == 200) {
                            _uiState.value = InvoiceUiState.Success(response.data)
                            _addBillResult.value = Result.success(response)
                            _successMessage.value = "Thêm hóa đơn thành công"
                            getInvoiceList(staffId = userId)
                            clearAll()
                        }
                        _isLoading.postValue(false)
                    },
                    onFailure = { exception ->
                        _isLoading.postValue(false)
                        _errorMessage.value = "Thêm hóa đơn thất bại"
                        _uiState.value =
                            InvoiceUiState.Error(exception.message ?: "Lỗi không xác định")
                    }
                )
            } catch (e: Exception) {
                _addBillResult.value = Result.failure(e)
                _isLoading.postValue(false)
            }
        }
    }

    fun confirmPaidInvoice(invoiceId: String, staffId: String) {
        viewModelScope.launch {
            _uiState.value = InvoiceUiState.Loading
            _isLoading.postValue(true)

            repository.confirmPaidStaff(invoiceId).fold(
                onSuccess = { response ->
                    if (response.status == 200 && response.data != null) {
                        _uiState.value = InvoiceUiState.Success(response.data)
                        _successMessage.postValue("Đã xác nhận thành công")
                        getInvoiceList(staffId)
                        // Log trạng thái mới
//                        Log.d("InvoiceUpdate", "Cập nhật thành công: ${response.data.paid}")
                    } else {
                        _uiState.value = InvoiceUiState.Error("Cập nhật thất bại")
                    }
                },
                onFailure = { error ->
                    _uiState.value = InvoiceUiState.Error(error.message ?: "Đã xảy ra lỗi")
                }
            )
            _isLoading.postValue(false)
        }
    }

    fun Any?.isNumber(): Boolean {
        return when (this) {
            is Int, is Float, is Double, is Long -> true
            is String -> this.toDoubleOrNull() != null
            else -> false
        }
    }

    fun clearAll() {
        clearDateError()
        clearBuildingError()
        clearWaterError()
        clearElecError()
        clearOldElecError()
        clearOldWaterError()
        clearRoomError()
        clearDescribeError()
    }

    fun clearDescribeError() {
        _describeErrorMessage.value = null
    }

    fun clearDateError() {
        _dateErrorMessage.value = null
    }


    fun clearBuildingError() {
        _buildingErrorMessage.value = null
    }


    fun clearRoomError() {
        _roomErrorMessage.value = null
    }


    fun clearWaterError() {
        _waterErrorMessage.value = null
    }


    fun clearElecError() {
        _elecErrorMessage.value = null
    }


    fun clearOldWaterError() {
        _oldWaterErrorMessage.value = null
    }


    fun clearOldElecError() {
        _oldElecErrorMessage.value = null
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



