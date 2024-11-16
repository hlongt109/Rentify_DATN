package com.rentify.user.app.network

import androidx.room.Query
import com.rentify.user.app.model.AddRoomResponse
import com.rentify.user.app.model.Room
import com.rentify.user.app.repository.LoginRepository.ApiResponse
import com.rentify.user.app.repository.LoginRepository.LoginRequest
import retrofit2.Response
import com.rentify.user.app.repository.LoginRepository.RegisterRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface APIService {
    // dang nhap
    @POST("login-user")
    suspend fun LoginUser(@Body loginRequest: LoginRequest): Response<ApiResponse>

    //phong bao to 123456789
    // Đăng ký
    @POST("register-user")
    suspend fun registerUser(@Body request: RegisterRequest): Response<ApiResponse>

    // Thêm phòng mới _vanphuc:
    @Multipart
    @POST("api/staff/rooms/AddRoom")
    suspend fun addRoom(
        @Part("building_id") buildingId: RequestBody,
        @Part("room_name") roomName: RequestBody,
        @Part("room_type") roomType: RequestBody,
        @Part("description") description: RequestBody,
        @Part("price") price: RequestBody,
        @Part("size") size: RequestBody,
        @Part("status") status: RequestBody,
        @Part("availability_status") availabilityStatus: RequestBody,
        @Part videoRoom: MultipartBody.Part?,
        @Part photosRoom: List<MultipartBody.Part>?,
        @Part("service_ids") serviceIds: RequestBody?,
        @Part("amenities") amenities: RequestBody?,
        @Part("service_fees") serviceFees: RequestBody?,
        @Part("limit_person") limitPerson: RequestBody
    ): Response<AddRoomResponse>

    // Lấy danh sách phòng _vanphuc :
    @GET("api/staff/rooms/list")
    suspend fun getRooms(): Response<List<Room>>
    // Upload video (mới)
    @Multipart
    @POST("api/staff/rooms/uploadVideo")
    suspend fun uploadVideo(
        @Part video: MultipartBody.Part
    ): Response<ApiResponse>

    // Upload ảnh (mới)
    @Multipart
    @POST("api/staff/rooms/uploadPhotos")
    suspend fun uploadPhotos(
        @Part photos: List<MultipartBody.Part>
    ): Response<ApiResponse>
}