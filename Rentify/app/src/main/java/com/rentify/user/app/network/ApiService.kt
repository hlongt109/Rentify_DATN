package com.rentify.user.app.network

import com.rentify.user.app.model.AddRoomResponse
import com.rentify.user.app.model.BuildingWithRooms
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
    @POST("staff/rooms/addRoom")
    suspend fun addRoom(
        @Part("building_id") building_id: RequestBody,
        @Part("room_name") room_name: RequestBody,
        @Part("room_type") room_type: RequestBody,
        @Part("description") description: RequestBody,
        @Part("price") price: RequestBody,
        @Part("size") size: RequestBody,
        @Part video_room: List<MultipartBody.Part>?,
        @Part photos_room: List<MultipartBody.Part>?,
        @Part("service") service: RequestBody?,
        @Part("amenities") amenities: RequestBody?,
        @Part("limit_person") limit_person: RequestBody,
        @Part("status") status: RequestBody,
    ): Response<AddRoomResponse>

    //    get danh sách _vanphuc:
    @GET("staff/rooms/buildings-by-manager/{manager_id}")
    suspend fun getBuildingsWithRooms(
        @Path("manager_id") manager_id: String
    ): Response<List<BuildingWithRooms>>

    // Lấy tên danh sách phòng theo tòa nhà
    @GET("staff/rooms/RoomsForBuilding/{building_id}")
    suspend fun getRoomsForBuilding(@Path("building_id") building_id: String): List<Room>

    // hiển thị chi tiết phòng theo id tự động sinh ra trong mongodb
    @GET("staff/rooms/RoomDetail/{id}")
    suspend fun getRoomDetailById(
        @Path("id") id: String
    ): Response<Room>
}