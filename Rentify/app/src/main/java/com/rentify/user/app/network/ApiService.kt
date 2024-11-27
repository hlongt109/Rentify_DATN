package com.rentify.user.app.network

import com.rentify.user.app.model.AddRoomResponse
import com.rentify.user.app.model.BuildingWithRooms
import com.rentify.user.app.model.BuildingsResponse
import com.rentify.user.app.model.Model.District
import com.rentify.user.app.model.Model.Province
import com.rentify.user.app.model.Model.Ward
import com.rentify.user.app.model.PostResponse
import com.rentify.user.app.model.PostingDetail
import com.rentify.user.app.model.RoomsResponse
import com.rentify.user.app.model.UpdatePostResponse
import com.rentify.user.app.model.User
import com.rentify.user.app.repository.LoginRepository.ApiResponse
import com.rentify.user.app.repository.LoginRepository.LoginRequest
import retrofit2.Response
import com.rentify.user.app.repository.LoginRepository.RegisterRequest
import com.rentify.user.app.view.staffScreens.postingList.PostingListComponents.PostingList
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part
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

    // Thêm phòng mới _vanphuc:
    @Multipart
    @POST("staff/rooms/AddRoom")
    suspend fun addRoom(
        @Part("building_id") buildingId: RequestBody,
        @Part("room_name") roomName: RequestBody,
        @Part("room_type") roomType: RequestBody,
        @Part("description") description: RequestBody,
        @Part("price") price: RequestBody,
        @Part("size") size: RequestBody,
        @Part videoRoom: MultipartBody.Part?,
        @Part photosRoom: List<MultipartBody.Part>?,
        @Part("service") service: RequestBody?,
        @Part("amenities") amenities: RequestBody?,
        @Part("limit_person") limitPerson: RequestBody,
        @Part("status") status: RequestBody,
    ): Response<AddRoomResponse>
//    get danh sách
    @GET("staff/rooms/buildings-by-manager/{manager_id}")
    suspend fun getBuildingsWithRooms(
        @Path("manager_id") managerId: String
    ): Response<List<BuildingWithRooms>>


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


    // API lấy danh sách các tòa nhà theo user_id
    @GET("staff/posts/buildings")
    suspend fun getBuildings(@Query("user_id") userId: String): Response<BuildingsResponse>

    // API lấy danh sách phòng trong một tòa nhà theo building_id
    @GET("staff/posts/rooms")
    suspend fun getRooms(@Query("building_id") buildingId: String):  Response<RoomsResponse>

    // API đăng bài mới
    @Multipart
    @POST("staff/posts/add")
    suspend fun addPost(
        @Part("user_id") userId: RequestBody,
        @Part("building_id") buildingId: RequestBody,
        @Part("room_id") roomId: RequestBody?,
        @Part("title") title: RequestBody,
        @Part("content") content: RequestBody,
        @Part("post_type") postType: RequestBody,
        @Part("status") status: RequestBody,
        @Part videos: List<MultipartBody.Part>,
        @Part photos: List<MultipartBody.Part>
    ): Response<PostResponse>

    @GET("staff/posts/list/{user_id}")
    suspend fun getPosts(@Path("user_id") userId: String): ApiResponsee<List<PostingList>>

    @DELETE("staff/posts/delete/{id}")
    suspend fun deletePost(@Path("id") postId: String): Response<Unit> // Giả sử API trả về `Unit` khi xóa thành công
    @GET("staff/posts/detail/{id}")
    suspend fun getPostDetail(@Path("id") postId: String): PostingDetail
    @Multipart
    @PUT("staff/posts/update/{id}")
    suspend fun updatePost(
        @Path("id") id: String, // ID của bài viết cần sửa
        @Part("building_id") building_id: RequestBody?,
        @Part("room_id") room_id: RequestBody?,
        @Part("user_id") userId: RequestBody?,
        @Part("title") title: RequestBody?,
        @Part("content") content: RequestBody?,
        @Part("status") status: RequestBody?,
        @Part("post_type") postType: RequestBody?,
        @Part videos: List<MultipartBody.Part>?,
        @Part photos: List<MultipartBody.Part>?
    ): Response<UpdatePostResponse>
    data class ApiResponsee<T>(
        val status: Int,
        val data: T
    )

    @Multipart
    @POST("add")
    suspend fun addPost_user(
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
/// user
