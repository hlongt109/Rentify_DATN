package com.rentify.user.app.network

import com.rentify.user.app.model.Model.District
import com.rentify.user.app.model.Model.Province
import com.rentify.user.app.model.Model.Ward
import com.rentify.user.app.model.AddRoomResponse
import com.rentify.user.app.model.Building
import com.rentify.user.app.model.BuildingWithRooms
import com.rentify.user.app.model.BuildingsResponse
import com.rentify.user.app.model.Contract
import com.rentify.user.app.model.ContractsResponse
import com.rentify.user.app.model.PostResponse
import com.rentify.user.app.model.PostingDetail
import com.rentify.user.app.model.RoomsResponse
import com.rentify.user.app.model.UpdatePostRequest
import com.rentify.user.app.model.User
import com.rentify.user.app.model.Room
import com.rentify.user.app.model.Room_post
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
import retrofit2.http.Path
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Query


////
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


    // Thêm phòng mới _vanphuc:
    @Multipart
    @POST("staff/rooms/addRoom")
    suspend fun addRoom(
        @Part("building_id") buildingId: RequestBody,
        @Part("room_name") roomName: RequestBody,
        @Part("room_type") room_type: RequestBody,
        @Part("description") description: RequestBody,
        @Part("price") price: RequestBody,
        @Part("size") size: RequestBody,
        @Part("service") service: RequestBody,  // Gửi dưới dạng JSON string
        @Part("amenities") amenities: RequestBody, // Gửi dưới dạng JSON string
        @Part("limit_person") limit_person: RequestBody,
        @Part("status") status: RequestBody,
        @Part photos_room: List<MultipartBody.Part>,
        @Part video_room: List<MultipartBody.Part>
    ): Response<AddRoomResponse>
    @Multipart
    @POST("upload-file")
    suspend fun uploadFile(
        @Part file: MultipartBody.Part // Tệp file cần upload
    ): Response<ApiResponse>

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

    // xóa phòng 👽:
    @DELETE("staff/rooms/DeleteRooms/{id}")
    suspend fun deleteRoom(
        @Path("id") id: String
    ): Response<ApiResponse>

    // UPDATE PHÒNG
    @Multipart
    @PUT("staff/rooms/updateRoom/{id}")
    suspend fun updateRoom(
        @Path("id") id: String,
        @PartMap data: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part photos: List<MultipartBody.Part>?,
        @Part videos: List<MultipartBody.Part>?
    ): Response<ApiResponse>
    // LẤY THÔNG TIN NGƯỜI DÙNG
    @GET("staff/users/usermail/{email}")
    suspend fun getUserDetail(
        @Path("email") email: String
    ): Response<User>



    // API lấy danh sách các tòa nhà theo user_id
    @GET("staff/posts/buildings")
    suspend fun getBuildings(@Query("manager_id") userId: String): Response<BuildingsResponse>

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

    data class ApiResponsee<T>(
        val status: Int,
        val data: T
    )
    @Multipart
    @PUT("staff/posts/update/{id}")
    suspend fun updatePostUser(
        @Path("id") postId: String,
        @Part("user_id") userId: RequestBody?,
        @Part("building_id") buildingId: RequestBody?,
        @Part("room_id") roomId: RequestBody?,
        @Part("title") title: RequestBody?,
        @Part("content") content: RequestBody?,
        @Part("status") status: RequestBody?,
        @Part("post_type") postType: RequestBody?,
        @Part video: List<MultipartBody.Part>?, // Optional video
        @Part photo: List<MultipartBody.Part>?  // Optional photo
    ): Response<UpdatePostRequest>
    // HỢP ĐỒNG
    @GET("staff/contracts/contracts-by-building")
    suspend fun getContractsByBuilding(
        @Query("manage_id") manageId: String
    ): Response<List<Contract>>
    // CHI TIẾT HỢP ĐỒNG
    @GET("staff/contracts/contract-detail/{id}")
    suspend fun getContractDetail(
        @Path("id") contractId: String
    ): Response<Contract>
//GET ROOM BY BUILDING CHO HỢP ĐỒNG
@GET("staff/contracts/buildings/{manage_id}")
suspend fun getBuildings_contrac(@Path("manage_id") manageId: String): Response<BuildingsResponse>
 // ADD HỢP ĐỒNG
    @Multipart
    @POST("staff/contracts/add")
    suspend fun addContract(
        @Part("manage_id") manageId: RequestBody,
        @Part("building_id") buildingId: RequestBody,
        @Part("room_id") roomId: RequestBody?,
        @Part("user_id") userId: RequestBody,
        @Part photosContract: List<MultipartBody.Part>,
        @Part("content") content: RequestBody,
        @Part("start_date") startDate: RequestBody,
        @Part("end_date") endDate: RequestBody,
        @Part("status") status: RequestBody,
    ): Response<ContractsResponse>
    //UPDATE HỢP ĐỒNG
    @PUT("staff/contracts/update/{id}")
    @Multipart
    suspend fun updateContract(
        @Path("id") contractId: String,
        @Part("user_id") userId: RequestBody?,
        @Part("content") content: RequestBody?,
        @Part photos: List<MultipartBody.Part>?
    ): Response<ContractsResponse>
    // Lấy danh sách ROOM theo building_id và status = 0
    @GET("staff/contracts/rooms/{building_id}")
    suspend fun getRooms_contrac(@Path("building_id") buildingId: String): Response<RoomsResponse>
   //TÌM KIẾM HỌPƯ ĐỒNG
   @GET("staff/contracts/search")
   suspend fun searchContracts(
       @Query("userName") userName: String? = null,  // Tham số tìm kiếm theo tên khách hàng
       @Query("buildingRoom") buildingRoom: String? = null  // Tham số tìm kiếm theo tòa nhà/phòng
   ): List<Contract> // Kết quả trả về là danh sách hợp đồng
   ///ADD POST USRT
    @Multipart
    @POST("add")
    suspend fun addPost_user(
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

    @GET("contracts/{user_id}")
    suspend fun getContracts(
        @Path("user_id") userId: String
    ): Response<ContractsResponse>

    @Multipart
    @PUT("update/{id}")
    suspend fun updatePostuser(
        @Path("id") postId: String,
        @Part("user_id") userId: RequestBody?,
        @Part("building_id") buildingId: RequestBody?,
        @Part("room_id") roomId: RequestBody?,
        @Part("title") title: RequestBody?,
        @Part("content") content: RequestBody?,
        @Part("status") status: RequestBody?,
        @Part("post_type") postType: RequestBody?,
        @Part video: List<MultipartBody.Part>?, // Optional video
        @Part photo: List<MultipartBody.Part>?  // Optional photo
    ): Response<UpdatePostRequest>

    @GET("room/{roomId}/building")
    suspend fun getBuildingFromRoom(
        @Path("roomId") roomId: String
    ): Response<Building>



}