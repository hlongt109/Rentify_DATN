package com.rentify.user.app.repository

import com.rentify.user.app.model.Model.ReportRequest
import com.rentify.user.app.network.APIService
import okhttp3.Response

class ReportRepository (private val apiService: APIService) {
    suspend fun createReport(reportRequest: ReportRequest) : retrofit2.Response<ReportRequest> {
        return apiService.createReport(reportRequest)
    }
}