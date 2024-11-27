package com.rentify.user.app.network

import com.rentify.user.app.model.Model.BookingRequest
import com.rentify.user.app.model.Model.BookingResponse
import com.rentify.user.app.model.Model.District
import com.rentify.user.app.model.Model.EmptyRoomResponse
import com.rentify.user.app.model.Model.Province
import com.rentify.user.app.model.Model.RoomDetailResponse
import com.rentify.user.app.model.Model.RoomResponse
import com.rentify.user.app.model.Model.StatusBookingRequest
import com.rentify.user.app.model.Model.UserOfBooking
import com.rentify.user.app.model.Model.Ward
import com.rentify.user.app.repository.LoginRepository.ApiResponse
import com.rentify.user.app.repository.LoginRepository.LoginRequest
import retrofit2.Response
import com.rentify.user.app.model.User
import com.rentify.user.app.model.UserOfRoomDetail
import com.rentify.user.app.repository.LoginRepository.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query


data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String,
)

data class RegisterResponse(
    val message: String,
    val user: User
)

////
interface APIService {
    // dang nhap
    @POST("login-user")
    suspend fun LoginUser(@Body loginRequest: LoginRequest): Response<ApiResponse>
    //phong bao to 123456789
    @POST("register")
    suspend fun registerUser(@Body request: RegisterRequest): Response<ApiResponse>

    //getLocation
    @GET("p")
    suspend fun getProvinces(): List<Province>

    @GET("p/{code}")
    suspend fun getProvinceDetail(@Path("code") code: String): Province

    @GET("p/{code}?depth=2")
    suspend fun getProvinceWithDistricts(@Path("code") code: String): Province

    @GET("d")
    suspend fun getDistricts(): List<District>

    @GET("d/{code}")
    suspend fun getDistrictDetail(@Path("code") code: String): District

    @GET("w")
    suspend fun getWards(): List<Ward>

    @GET("d/{code}?depth=2")
    suspend fun getDistrictWithWards(@Path("code") code: String): District

    // Xử lý lấy dữ liệu với app user
    @GET("room/get-districts/{city}")
    suspend fun getDistricts(@Path("city") city: String): List<String>

    @GET("room/get-detail-room/{id}")
    suspend fun getRoomDetail(@Path("id") id: String): Response<RoomDetailResponse>

    @GET("room/get-random-rooms")
    suspend fun getListOfRandomRooms(): Response<List<RoomResponse>>

    @GET("room/get-landlord-buildings/{landlord_id}")
    suspend fun getDetailLandlord(@Path("landlord_id") landlordId: String): Response<UserOfRoomDetail>

    @GET("room/get-empty-rooms/{building_id}")
    suspend fun getEmptyRooms(@Path("building_id") buildingId: String): Response<List<EmptyRoomResponse>>

    @GET("room/search-rooms")
    suspend fun searchRooms(
        @Query("address") address: String? = null,
        @Query("minPrice") minPrice: Int? = null,
        @Query("maxPrice") maxPrice: Int? = null,
        @Query("roomType") roomType: String? = null,
        @Query("sortBy") sortBy: String? = null
    ): Response<List<RoomResponse>>

    // Xử lý các api liên quan tới xem phòng
    @POST("add-booking")
    suspend fun addBooking(@Body bookingRequest: BookingRequest): Response<BookingRequest>

    @GET("get-user-details/{user_id}")
    suspend fun getUserDetails(@Path("user_id") userId: String): Response<UserOfBooking>

    @GET("get-bookings/{user_id}/{status}")
    suspend fun getBookings(@Path("user_id") userId: String, @Path("status") status: Int): Response<List<BookingResponse>>

    @PUT("update-booking-status/{booking_id}")
    suspend fun updateBookingStatus(@Path("booking_id") bookingId: String, @Body status: StatusBookingRequest): Response<StatusBookingRequest>

}