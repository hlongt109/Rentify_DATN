package com.rentify.user.app.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rentify.user.app.model.Model.NotificationRequest
import com.rentify.user.app.model.Model.ReportRequest
import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.NotificationRepository
import com.rentify.user.app.repository.ReportRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class ReportViewModel : ViewModel() {
    private val apiService = RetrofitService().ApiService
    private val reportRepository = ReportRepository(apiService)

    private val _createReportResult = MutableSharedFlow<Result<ReportRequest>>()
    val createReportResult = _createReportResult.asSharedFlow()

    fun createNotification(request: ReportRequest) {
        viewModelScope.launch {
            try {
                val response = reportRepository.createReport(request)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _createReportResult.emit(Result.success(it))
                    } ?: run {
                        _createReportResult.emit(Result.failure(Exception("Response is empty")))
                    }
                } else {
                    _createReportResult.emit(Result.failure(Exception(response.message())))
                }
            } catch (e: Exception) {
                _createReportResult.emit(Result.failure(e))
            }
        }
    }
}