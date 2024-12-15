package com.rentify.user.app.network.ApiStaff

import com.rentify.user.app.model.Model.BookingResponse
import com.rentify.user.app.model.Model.BookingResponseStaff
import com.rentify.user.app.model.Model.InvoiceUpdate
import com.rentify.user.app.model.Model.InvoiceUpdateRequest
import com.rentify.user.app.model.Model.NotificationRequest
import com.rentify.user.app.model.Model.StatusResponse
import com.rentify.user.app.model.Model.UpdateInvoice
import com.rentify.user.app.model.Model.UpdateStatus

import com.rentify.user.app.repository.StaffRepository.BuildingRepository.BuildingStaffResponse
import com.rentify.user.app.repository.StaffRepository.InvoiceRepository.Invoice
import com.rentify.user.app.repository.StaffRepository.InvoiceRepository.InvoiceAdd
import com.rentify.user.app.repository.StaffRepository.InvoiceRepository.InvoiceConfirmPaid
import com.rentify.user.app.repository.StaffRepository.InvoiceRepository.InvoiceResponse
import com.rentify.user.app.repository.StaffRepository.RoomRepository.RoomResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiServiceStaff{
    //get list building bang id nhan vien
    @GET("buildings/get-building-staffId/{staffId}")
    suspend fun getListBuildingStaffId(@Path("staffId") staffId: String): Response<BuildingStaffResponse>

    //get list room bang id toa nha
    @GET("rooms/get-room-buildingId/{buildingId}")
    suspend fun getListRoomBuildingId(@Path("buildingId") buildingId: String): Response<RoomResponse>

    //get invoice cho nhan vien
    @GET("invoices/listInvoiceStaff/{staffId}")
    suspend fun listInvoiceStaff(@Path("staffId") staffId: String): Response<InvoiceResponse>
    //add bill
    @POST("invoices/addBillStaff")
    suspend fun addInvoice(@Body invoice: InvoiceAdd): Response<InvoiceResponse>

    //update trang thai thanh toan
    @PUT("invoices/confirmPaidInvoice/{invoiceId}")
    suspend fun confirmPaidInvoice(
        @Path("invoiceId") invoiceId: String,
        @Body status: InvoiceConfirmPaid): Response<InvoiceResponse>

    // lay chi tiet hoa don
    @GET("invoices/invoiceDetail/{invoiceId}")
    suspend fun getDetailInvoice(@Path("invoiceId") invoiceId: String): UpdateStatus

    @PUT("invoices/partialUpdateInvoice/{invoiceId}")
    suspend fun partialUpdateInvoice(
        @Path("invoiceId") invoiceId: String,
        @Body invoice: InvoiceUpdateRequest
    ): Response<InvoiceUpdate>

    // booking
    @GET("bookings-list/{manager_id}")
    suspend fun getBookingList(@Path("manager_id") managerId: String): Response<List<BookingResponseStaff>>

    @PUT("bookings-status/{id}")
    suspend fun updateBookingStatus(
        @Path("id") id: String,
        @Body status: Map<String, Int>
    ): Response<StatusResponse>

    @GET("bookings-details/{id}")
    suspend fun getBookingById(@Path("id") id: String) : Response<BookingResponseStaff>

    @POST("notification/create-notification")
    suspend fun createNotification(@Body request: NotificationRequest): Response<NotificationRequest>
}