package com.rentify.user.app.repository

import com.rentify.user.app.model.Model.BookingRequest
import com.rentify.user.app.model.Model.BookingResponse
import com.rentify.user.app.model.Model.StatusBookingRequest
import com.rentify.user.app.model.Model.UserOfBooking
import com.rentify.user.app.network.APIService
import retrofit2.Response

class BookingRepository (private val apiService: APIService) {
    suspend fun bookRoom(bookingRequest: BookingRequest): Response<BookingRequest> {
        return apiService.addBooking(bookingRequest)
    }
    suspend fun getUserDetails(userId: String): Response<UserOfBooking> {
        return apiService.getUserDetails(userId)
    }
    suspend fun getBookings(userId: String, status: Int): Response<List<BookingResponse>> {
        return apiService.getBookings(userId, status)
    }
    suspend fun updateStatusBooking(bookingId: String, status: StatusBookingRequest): Response<StatusBookingRequest> {
        return apiService.updateBookingStatus(bookingId, status)
    }
}