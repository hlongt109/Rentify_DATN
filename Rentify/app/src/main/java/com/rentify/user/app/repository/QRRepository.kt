package com.rentify.user.app.repository

import com.rentify.user.app.model.LandlordOrStaffs
import com.rentify.user.app.model.Model.InvoiceOfUpdate
import com.rentify.user.app.model.SupportModel.CreateReportResponse
import com.rentify.user.app.network.APIService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class QRRepository (private val apiService: APIService) {
    suspend fun getQRCode(buildingId: String) : Response<LandlordOrStaffs> {
        return apiService.getQRLandlordOrStaffs(buildingId)
    }

    suspend fun updateInvoice(invoiceId: String, image: MultipartBody.Part): Response<InvoiceOfUpdate> {
        return apiService.updatePaymentImage(invoiceId, image)
    }

    suspend fun updateStatusInvoice(invoiceId: String): Response<InvoiceOfUpdate> {
        return apiService.updateStatusToWait(invoiceId)
    }

    suspend fun addSupport(
        userId: RequestBody,
        roomId: RequestBody,
        buildingId: RequestBody,
        titleSupport: RequestBody,
        contentSupport: RequestBody,
        status: RequestBody,
        images: List<MultipartBody.Part>
    ): Response<CreateReportResponse> {
        return apiService.createReport(
            userId = userId,
            roomId = roomId,
            buildingId = buildingId,
            titleSupport = titleSupport,
            contentSupport = contentSupport,
            status = status,
            images = images
        )
    }
}