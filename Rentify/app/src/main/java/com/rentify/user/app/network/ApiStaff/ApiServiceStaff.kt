package com.rentify.user.app.network.ApiStaff

import com.rentify.user.app.repository.StaffRepository.BuildingRepository.BuildingStaffResponse
import com.rentify.user.app.repository.StaffRepository.InvoiceRepository.Invoice
import com.rentify.user.app.repository.StaffRepository.InvoiceRepository.InvoiceAdd
import com.rentify.user.app.repository.StaffRepository.InvoiceRepository.InvoiceResponse
import com.rentify.user.app.repository.StaffRepository.RoomRepository.RoomResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
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

}