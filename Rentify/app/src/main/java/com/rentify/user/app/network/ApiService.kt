package com.rentify.user.app.network

import com.rentify.user.app.model.District
import com.rentify.user.app.model.Province
import com.rentify.user.app.repository.LoginRepository.ApiResponse
import com.rentify.user.app.repository.LoginRepository.LoginRequest
import retrofit2.Response
import com.rentify.user.app.model.User
import com.rentify.user.app.model.Ward
import com.rentify.user.app.repository.LoginRepository.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call
import retrofit2.http.GET
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
    fun registerUser(@Body request: RegisterRequest): Call<RegisterResponse>

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

}