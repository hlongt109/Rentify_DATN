package com.rentify.user.app.repository.StaffRepository.BookingRepository

import com.rentify.user.app.model.Model.BookingResponse
import com.rentify.user.app.model.Model.StatusResponse
import com.rentify.user.app.network.ApiStaff.RetrofitStaffService
import com.rentify.user.app.network.RetrofitService
import retrofit2.Response

class BookingRespository(private val retrofitService: RetrofitStaffService) {
    suspend fun getBooking(manager_id: String) =
        retrofitService.ApiService_.getBookingList(manager_id)

    suspend fun updateBooking(id: String, sts: Int): Response<StatusResponse> {
        val status = mapOf("status" to sts)
        return retrofitService.ApiService_.updateBookingStatus(id, status)
    }

    suspend fun getBookingDetails(id: String) = retrofitService.ApiService_.getBookingById(id)
}