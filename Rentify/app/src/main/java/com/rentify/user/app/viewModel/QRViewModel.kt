package com.rentify.user.app.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rentify.user.app.model.LandlordOrStaffs
import com.rentify.user.app.model.Model.InvoiceOfUpdate
import com.rentify.user.app.model.SupportModel.CreateReportResponse
import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.QRRepository
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class QRViewModel : ViewModel() {
    private val apiService = RetrofitService().ApiService
    private val qrRepository = QRRepository(apiService)

    private val _bankAccount = MutableLiveData<LandlordOrStaffs>()
    val bankAccount: LiveData<LandlordOrStaffs> get() = _bankAccount

    private val _updateStatus = MutableLiveData<InvoiceOfUpdate>()
    val updateStatus: LiveData<InvoiceOfUpdate> get() = _updateStatus

    private val _supportResponse = MutableLiveData<CreateReportResponse>()
    val supportResponse: LiveData<CreateReportResponse> get() = _supportResponse

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun fetchBankAccount(buildingId: String){
        viewModelScope.launch {
            try {
                val response = qrRepository.getQRCode(buildingId)
                if(response.isSuccessful && response.body() != null){
                    _bankAccount.value = response.body()
                } else {
                    _errorMessage.value = response.message()
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }

    fun updatePaymentImage(invoiceId: String, imageFile: File) {
        viewModelScope.launch {
            try {
                // Chuẩn bị MultipartBody.Part cho file
                val requestFile = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
                val imagePart = MultipartBody.Part.createFormData("image", imageFile.name, requestFile)

                // Gọi API
                val response = qrRepository.updateInvoice(invoiceId, imagePart)
                if (response.isSuccessful && response.body() != null) {
                    _updateStatus.value = response.body()
                } else {
                    _errorMessage.value = response.message()
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }

    fun updateStatusInvoice(invoiceId: String) {
        viewModelScope.launch {
            try {
                val response = qrRepository.updateStatusInvoice(invoiceId)
                if (response.isSuccessful && response.body() != null) {
                    _updateStatus.value = response.body()
                } else {
                    _errorMessage.value = response.message()
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }

    fun addSupport(
        userId: String,
        roomId: String,
        buildingId: String,
        titleSupport: String,
        contentSupport: String,
        status: String,
        imageFiles: List<File>
    ) {
        viewModelScope.launch {
            try {
                val userIdPart = createPartFromString(userId)
                val roomIdPart = createPartFromString(roomId)
                val buildingIdPart = createPartFromString(buildingId)
                val titleSupportPart = createPartFromString(titleSupport)
                val contentSupportPart = createPartFromString(contentSupport)
                val statusPart = createPartFromString(status.toInt().toString())

                val imageParts = imageFiles.map { file ->
                    val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                    MultipartBody.Part.createFormData("image", file.name, requestFile)
                }

                val response = qrRepository.addSupport(
                    userId = userIdPart,
                    roomId = roomIdPart,
                    buildingId = buildingIdPart,
                    titleSupport = titleSupportPart,
                    contentSupport = contentSupportPart,
                    status = statusPart,
                    images = imageParts
                )

                if (response.isSuccessful && response.body() != null) {
                    _supportResponse.value = response.body()
                } else {
                    val errorBody = response.errorBody()?.string()
                    _errorMessage.value = "Error: ${response.code()}, ${response.message()}, Body: $errorBody"
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }

}

private fun createPartFromString(value: String): RequestBody {
    return RequestBody.create("text/plain".toMediaTypeOrNull(), value)
}
