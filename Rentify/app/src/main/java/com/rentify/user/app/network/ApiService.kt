package com.rentify.user.app.network

import com.rentify.user.app.model.Model.District
import com.rentify.user.app.model.Model.Province
import com.rentify.user.app.model.Model.Ward
import com.rentify.user.app.model.PostRequest
import com.rentify.user.app.model.PostResponse
import com.rentify.user.app.repository.LoginRepository.ApiResponse
import com.rentify.user.app.repository.LoginRepository.LoginRequest
import retrofit2.Response
import com.rentify.user.app.model.User
import com.rentify.user.app.repository.LoginRepository.RegisterRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.Part
import retrofit2.http.Path


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


    @Multipart
    @POST("add")
    suspend fun addPost(
        @Part("user_id") userId: RequestBody,
        @Part("title") title: RequestBody,
        @Part("content") content: RequestBody,
        @Part("status") status: RequestBody,
        @Part("post_type") postType: RequestBody,
        @Part("price") price: RequestBody,
        @Part("address") address: RequestBody,
        @Part("phoneNumber") phoneNumber: RequestBody,
        @Part("room_type") roomType: RequestBody,
        @Part("amenities") amenities: RequestBody,
        @Part("services") services: RequestBody,
        @Part videos: List<MultipartBody.Part>,
        @Part photos: List<MultipartBody.Part>
    ): Response<PostResponse>


}