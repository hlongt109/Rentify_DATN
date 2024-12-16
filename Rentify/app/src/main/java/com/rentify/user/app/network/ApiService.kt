package com.rentify.user.app.network

import Contract
import ContractResponse
import com.rentify.user.app.model.Model.District
import com.rentify.user.app.model.Model.Province
import com.rentify.user.app.model.Model.Ward
import com.rentify.user.app.model.AddRoomResponse
import com.rentify.user.app.model.Building
import com.rentify.user.app.model.BuildingWithRooms
import com.rentify.user.app.model.BuildingsResponse
import com.rentify.user.app.model.Model.InvoiceResponse
import com.rentify.user.app.model.Model.RoomPage
import com.rentify.user.app.model.ContractsResponse
import com.rentify.user.app.model.DataResponse
import com.rentify.user.app.model.LandlordOrStaffs
import com.rentify.user.app.model.ListServiceResponse
import com.rentify.user.app.model.Model.Bank
import com.rentify.user.app.model.Model.BookingRequest
import com.rentify.user.app.model.Model.BookingResponse
import com.rentify.user.app.model.Model.EmptyRoomResponse
import com.rentify.user.app.model.Model.InvoiceOfUpdate
import com.rentify.user.app.model.Model.NotificationRequest
import com.rentify.user.app.model.Model.Notification
import com.rentify.user.app.model.Model.NotificationResponse
import com.rentify.user.app.model.Model.ReportRequest
import com.rentify.user.app.model.Model.RoomDetailResponse
import com.rentify.user.app.model.Model.RoomPageSale
import com.rentify.user.app.model.Model.RoomResponse
import com.rentify.user.app.model.Model.RoomSaleResponse
import com.rentify.user.app.model.Model.StatusBookingRequest
import com.rentify.user.app.model.Model.UpdateAccUserResponse
import com.rentify.user.app.model.Model.UpdateTaiKhoanResponse
import com.rentify.user.app.model.Model.UserOfBooking
import com.rentify.user.app.model.PostListResponse
import com.rentify.user.app.model.PostingDetail
import com.rentify.user.app.model.ProfilePictureResponse
import com.rentify.user.app.model.ResponseUser
import com.rentify.user.app.model.RoomsResponse
import com.rentify.user.app.model.UpdatePostRequest
import com.rentify.user.app.model.Room
import com.rentify.user.app.model.ServiceAdmin.AdminService
import com.rentify.user.app.model.ServiceOfBuilding
import com.rentify.user.app.model.SupportModel.SupportResponse

import com.rentify.user.app.model.User
import com.rentify.user.app.model.UserOfRoomDetail
import com.rentify.user.app.model.ServiceFees.ServiceFeesItem
import com.rentify.user.app.model.SupportModel.CreateReportResponse
import com.rentify.user.app.model.UserResponse
import com.rentify.user.app.repository.ForgotRepository.ForgotRequest
import com.rentify.user.app.repository.ForgotRepository.ForgotResponse
import com.rentify.user.app.repository.ForgotRepository.MailConfirmForgot
import com.rentify.user.app.repository.ForgotRepository.ResetPassword
import com.rentify.user.app.repository.LoginRepository.ApiResponse
import com.rentify.user.app.repository.LoginRepository.LoginRequest
import retrofit2.Response
import com.rentify.user.app.repository.LoginRepository.RegisterRequest
import com.rentify.user.app.repository.SupportRepository.APISupportResponse
import com.rentify.user.app.repository.SupportRepository.AddSupport
import com.rentify.user.app.repository.SupportRepository.ContractRoomResponse
import com.rentify.user.app.view.staffScreens.homeScreen.RoomSummary
import com.rentify.user.app.view.staffScreens.postingList.PostingListComponents.PostingList
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
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
    @POST("register-user")
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
    suspend fun getListOfRandomRooms(): Response<List<RoomSaleResponse>>

    @GET("room/get-random-rooms-page")
    suspend fun getRandomRooms(
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 10
    ): RoomPage

    @GET("room/get-landlord-buildings/{landlord_id}")
    suspend fun getDetailLandlord(@Path("landlord_id") landlordId: String): Response<UserOfRoomDetail>

    @GET("room/get-empty-rooms/{building_id}")
    suspend fun getEmptyRooms(@Path("building_id") buildingId: String): Response<List<EmptyRoomResponse>>

    @GET("room/search-rooms")
    suspend fun searchRooms(
        @Query("address") address: String?,
        @Query("minPrice") minPrice: Int?,
        @Query("maxPrice") maxPrice: Int?,
        @Query("roomType") roomType: String?,
        @Query("sortBy") sortBy: String?,
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 10,
        @Query("random") random: String? = null
    ): Response<RoomPage>

    // Xử lý các api liên quan tới xem phòng
    @POST("add-booking")
    suspend fun addBooking(@Body bookingRequest: BookingRequest): Response<BookingRequest>

    @GET("get-user-details/{user_id}")
    suspend fun getUserDetails(@Path("user_id") userId: String): Response<UserOfBooking>

    @GET("get-bookings/{user_id}/{status}")
    suspend fun getBookings(
        @Path("user_id") userId: String,
        @Path("status") status: Int
    ): Response<List<BookingResponse>>

    @PUT("update-booking-status/{booking_id}")
    suspend fun updateBookingStatus(
        @Path("booking_id") bookingId: String,
        @Body status: StatusBookingRequest
    ): Response<StatusBookingRequest>


    // Thêm phòng mới _vanphuc:_vanphuc
    @Multipart
    @POST("staff/rooms/addRoom")
    suspend fun addRoom(
        @Part("building_id") buildingId: RequestBody,
        @Part("room_name") roomName: RequestBody,
        @Part("room_type") room_type: RequestBody,
        @Part("description") description: RequestBody,
        @Part("price") price: RequestBody,
        @Part("sale") sale: RequestBody,
        @Part("size") size: RequestBody,
        @Part("service") service: RequestBody,  // Gửi dưới dạng JSON string
        @Part("amenities") amenities: RequestBody, // Gửi dưới dạng JSON string
        @Part("limit_person") limit_person: RequestBody,
        @Part("status") status: RequestBody,
        @Part photos_room: List<MultipartBody.Part>,
        @Part video_room: List<MultipartBody.Part>
    ): Response<AddRoomResponse>
    // Lấy tổng số phòng theo manager_id
    @GET("staff/rooms/RoomsSummaryByManager/{manager_id}")
    suspend fun getRoomsSummaryByManager(
        @Path("manager_id") managerId: String
    ): RoomSummary
    @Multipart
    @POST("upload-file")
    suspend fun uploadFile(
        @Part file: MultipartBody.Part // Tệp file cần upload
    ): Response<ApiResponse>

    //    get danh sách _vanphuc:_vanphuc
    @GET("staff/rooms/buildings-by-manager/{manager_id}")
    suspend fun getBuildingsWithRooms(
        @Path("manager_id") manager_id: String
    ): Response<List<BuildingWithRooms>>

    // Lấy tên danh sách phòng theo tòa nhà_vanphuc
    @GET("staff/rooms/RoomsForBuilding/{building_id}")
    suspend fun getRoomsForBuilding(@Path("building_id") building_id: String): List<Room>

    // hiển thị chi tiết phòng theo id tự động sinh ra trong mongodb_vanphuc
    @GET("staff/rooms/RoomDetail/{id}")
    suspend fun getRoomDetailById(
        @Path("id") id: String
    ): Response<Room>

    // xóa phòng 👽:_vanphuc
    @DELETE("staff/rooms/DeleteRooms/{id}")
    suspend fun deleteRoom(
        @Path("id") id: String
    ): Response<ApiResponse>

    // UPDATE PHÒNG🍕_vanphuc
    @Multipart
    @PUT("staff/rooms/updateRoom/{id}")
    suspend fun updateRoom(
        @Path("id") id: String,
        @PartMap data: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part photos: List<MultipartBody.Part>?,
        @Part videos: List<MultipartBody.Part>?
    ): Response<ApiResponse>

    // LẤY THÔNG TIN NGƯỜI DÙNG😬 _vanphuc
    @GET("staff/users/getUser/{id}")
    suspend fun getUserDetail(
        @Path("id") id: String
    ): Response<UserResponse>

    // lay dich vu cua toa _vanphuc
    @GET("staff/rooms/building/{id}/services")
    suspend fun getServiceOfBuilding(@Path("id") id: String): Response<List<ServiceOfBuilding>>

    //hien thi lit su co
    @GET("staff/SOS/support/by-building/{building_id}/{status}")
    suspend fun getListSupport(
        @Path("building_id") building_id: String,
        @Path("status") status: Int
    ): Response<List<SupportResponse>>

    // hiển thị chi tiết sự cố theo phòng
    @GET("staff/SOS/supports/{support_id}")
    suspend fun getSupportDetail(
        @Path("support_id") roomId: String
    ): Response<SupportResponse>

    // update
    @PUT("staff/SOS/supports/{id}")
    suspend fun updateSupport(
        @Path("id") id: String,
        @Body supportRequest: SupportResponse
    ): Response<SupportResponse>


    // API lấy danh sách các tòa nhà theo user_id
    @GET("staff/posts/buildings")
    suspend fun getBuildings(@Query("manager_id") userId: String): Response<BuildingsResponse>

    // API lấy danh sách phòng trong một tòa nhà theo building_id
    @GET("staff/posts/rooms")
    suspend fun getRooms(@Query("building_id") buildingId: String): Response<RoomsResponse>

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
    ): Response<PostingList>

    @GET("staff/posts/list/{user_id}")
    suspend fun getPosts(@Path("user_id") userId: String): ApiResponsee<List<PostingList>>
    data class ApiResponsee<T>(
        val status: Int,
        val data: T
    )

    ///tim keim post
    @GET("staff/posts/search")
    suspend fun searchPosts(
        @Query("query") query: String, // Từ khóa tìm kiếm
        @Query("user_id") userId: String? = null // ID người dùng (nếu có)
    ): Response<List<PostingList>>

    @DELETE("staff/posts/delete/{id}")
    suspend fun deletePost(@Path("id") postId: String): Response<Unit> // Giả sử API trả về `Unit` khi xóa thành công

    @GET("staff/posts/detail/{id}")
    suspend fun getPostDetail(@Path("id") postId: String): PostingList


    @Multipart
    @PUT("staff/posts/update/{id}")
    suspend fun updatePost(
        @Path("id") postId: String,
        @Part("user_id") userId: RequestBody?,
        @Part("building_id") buildingId: RequestBody?,
        @Part("room_id") roomId: RequestBody?,
        @Part("title") title: RequestBody?,
        @Part("address") address: RequestBody?,
        @Part("content") content: RequestBody?,
        @Part("status") status: RequestBody?,
        @Part("post_type") postType: RequestBody?,
        @Part video: List<MultipartBody.Part>?, // Optional video
        @Part photo: List<MultipartBody.Part>?  // Optional photo
    ): Response<PostingList>

    // HỢP ĐỒNG
    @GET("staff/contracts/contracts-by-building")
    suspend fun getContractsByBuilding(
        @Query("manage_id") manageId: String
    ): Response<List<Contract>>

    // CHI TIẾT HỢP ĐỒNG
    @GET("staff/contracts/contract-detail/{id}")
    suspend fun getContractDetail1(@Path("id") contractId: String): Response<Contract>

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
//    @GET("staff/contracts/search")
//    suspend fun searchContracts(
//        @Query("userName") userName: String? = null,
//        @Query("buildingRoom") buildingRoom: String? = null,
//        @Query("manageId") manageId: String? = null // Thêm manageId
//    ): List<Contract>// Kết quả trả về là danh sách hợp đồng  var
    @GET("staff/contracts/search")
    suspend fun searchContracts(
        @Query("keyword") keyword: String? = null,
        @Query("manageId") manageId: String? = null // Thêm manageId
    ): List<Contract>// Kết quả trả về là danh sách hợp đồng  var
    @GET("staff/contracts/user/{id}")
    suspend fun getUserById(
        @Path("id") userId: String
    ): Response<User>
    ///ADD POST USRT
    @Multipart
    @POST("add")
    suspend fun addPost_user(
        @Part("user_id") userId: RequestBody,
        @Part("building_id") buildingId: RequestBody,
        @Part("room_id") roomId: RequestBody?,
        @Part("title") title: RequestBody,
        @Part("address") address: RequestBody,
        @Part("content") content: RequestBody,
        @Part("post_type") postType: RequestBody,
        @Part("status") status: RequestBody,
        @Part videos: List<MultipartBody.Part>,
        @Part photos: List<MultipartBody.Part>
    ): Response<PostingList>
    @GET("staff/posts/postType/list/{user_id}")
    suspend fun getPosts_user(
        @Path("user_id") userId: String,
        @Query("post_type") postType: String? = null // Thêm post_type vào query parameter
    ): ApiResponsee<List<PostingList>>
    // bài đăng theo post typedgafgggi đăng theo post typedgafgggi đăng theo post typedgafgggi đăng theo post typedgafgggi đăng theo post typedgafggg
    @GET("list/type/{post_type}")
    suspend fun getPostsByType(
        @Path("post_type") postType: String
    ): Response<PostListResponse>  // Phản hồi kiểu PostListResponse
    // thiên thực hiện sử lý phần hợp đồng
    @GET("contract-detail/{user_id}")
    suspend fun getContractDetail(@Path("user_id") userId: String): Response<List<ContractResponse>>

    // thiên thực hiển sử lí phần hoá đơn với user
    @GET("get-invoices-by-room-and-status/{room_id}/{status}")
    suspend fun getInvoicesByStatus(
        @Path("room_id") userId: String,
        @Path("status") status: String
    ): Response<List<InvoiceResponse>>


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
    ): Response<PostingList>

    @GET("room/{roomId}/building")
    suspend fun getBuildingFromRoom(
        @Path("roomId") roomId: String
    ): Response<Building>

    @GET("landlord/{building_id}")
    suspend fun getQRLandlordOrStaffs(
        @Path("building_id") buildingId: String
    ): Response<LandlordOrStaffs>

    // thực hien dua anh chuyen khoan cua nguoi dung vao hoa don
    @Multipart
    @PUT("update-payment-image/{id}")
    suspend fun updatePaymentImage(
        @Path("id") invoiceId: String,
        @Part image: MultipartBody.Part
    ): Response<InvoiceOfUpdate>

    @PUT("update-status-to-wait/{id}")
    suspend fun updateStatusToWait(
        @Path("id") invoiceId: String
    ): Response<InvoiceOfUpdate>

    // thuc hien khi chon thanh toan bang tien mat
    @Multipart
    @POST("create-report")
    suspend fun createReport(
        @Part("user_id") userId: RequestBody,
        @Part("room_id") roomId: RequestBody,
        @Part("building_id") buildingId: RequestBody,
        @Part("title_support") titleSupport: RequestBody,
        @Part("content_support") contentSupport: RequestBody,
        @Part("status") status: RequestBody,
        @Part images: List<MultipartBody.Part>
    ): Response<CreateReportResponse>

    @GET("staff/rooms/Listservices")
    suspend fun getServiceList(): Response<List<ListServiceResponse>>

    @PUT("staff/users/addUserInfo/{id}")
    suspend fun updateUserInfo(
        @Path("id") userId: String,
        @Body userDetails: Map<String, String>
    ): Response<UserResponse>

    //bao cao su co
    @GET("getSupportsByUserId/{userId}")
    suspend fun getSupportsByUserId(@Path("userId") userId: String): Response<APISupportResponse>

    //them
    @Multipart
    @POST("create-report")
    suspend fun createSupportReport(
        @Part("user_id") userId: RequestBody,
        @Part("room_id") roomId: RequestBody,
        @Part("building_id") buildingId: RequestBody,
        @Part("title_support") titleSupport: RequestBody,
        @Part("content_support") contentSupport: RequestBody,
        @Part("status") status: RequestBody,
        @Part images: List<MultipartBody.Part>
    ): Response<AddSupport>

    //lay thong tin phong trong hop dong theo userId
    @GET("get-room-by-contract/{userId}")
    suspend fun getRoomByContract(@Path("userId") userId: String): Response<ContractRoomResponse>

    //check co hợp đồng hay không
    @GET("check-contract/{userId}")
    suspend fun checkContract(@Path("userId") userId: String): Response<com.rentify.user.app.repository.SupportRepository.ContractResponse>

    //quen mat khau
    //gui mail xac nhan
    @POST("forgot-password")
    suspend fun postMailForgot(@Body forgotRequest: ForgotRequest): Response<ForgotResponse>

    //xac nhan ma
    @POST("confirm-code")
    suspend fun confirmCode(@Body confirmCode: MailConfirmForgot): Response<ForgotResponse>

    @PUT("reset-password")
    suspend fun resetPassword(@Body resetPassword: ResetPassword): Response<ForgotResponse>

    //lay thong tin nguoi dung
    @GET("get-user-infor/{userId}")
    suspend fun getInfoUser(@Path("userId") userId: String): Response<ApiResponse>

    @GET("get-user-infor/{userId}")
    suspend fun getInfoAcc(@Path("userId") userId: String): Response<DataResponse>

    @GET("staff/users/serviceFeesUser/{userId}")
    suspend fun getServiceFeesByUser(
        @Path("userId") userId: String
    ): Response<List<ServiceFeesItem>>

    @GET("staff/users/getBankAccount/{userId}")
    suspend fun getBankAccount(
        @Path("userId") userId: String
    ): Response<Bank>

    @PUT("staff/users/updateBankAccount/{userId}")
    suspend fun updateBankAccount(
        @Path("userId") userId: String,
        @Body bank: Bank
    ): Response<Bank>

    @Multipart
    @PUT("staff/users/updateBankAccount/{userId}")
    suspend fun updateBankAccountWithImage(
        @Path("userId") userId: String,
        @Part qr_bank: MultipartBody.Part,
    ): Response<Bank>

    @PUT("staff/users/updateTaiKhoan/{id}")
    suspend fun updateTaiKhoan(
        @Path("id") id: String,
        @Body updatedUser: UserResponse?
    ): Response<UpdateTaiKhoanResponse>

    @Multipart
    @POST("staff/users/addImageUser/{id}")
    suspend fun addImageUser(
        @Path("id") userId: String,
        @Part profilePicture: MultipartBody.Part
    ): Response<ProfilePictureResponse>

    @GET("staff/users/getImageUser/{id}")
    suspend fun getImageUser(
        @Path("id") userId: String
    ): Response<ProfilePictureResponse>

    // thien lay cac phong dang sale
    @GET("room/get-rooms-with-sale")
    suspend fun getRoomsWithSale(
        @Query("address") address: String?,
        @Query("minPrice") minPrice: Int?,
        @Query("maxPrice") maxPrice: Int?,
        @Query("roomType") roomType: String?,
        @Query("sortBy") sortBy: String?,
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 10,
        @Query("random") random: String? = null
    ): Response<RoomPageSale>

    @GET("service_adm/{admin_id}")
    suspend fun service_adm(
        @Path("admin_id") adminId: String
    ): Response<AdminService>

    //update account user
    @PUT("updateAccountUser/{id}")
    suspend fun updateAccountUser(
        @Path("id") id: String,
        @Body updateUser: ResponseUser?
    ):Response<ResponseUser>

    //list phong trong map
    @GET("room/get-map-list-room")
    suspend fun getListRoomMap():Response<com.rentify.user.app.repository.ListRoomMap.RoomResponse>

    @POST("notification/create-notification")
    suspend fun createNotification(@Body request: NotificationRequest): Response<NotificationRequest>

    // VUVANPHUC : thông báo
    @GET("notification/get-by-user/{userId}")
    suspend fun GetNotification(
        @Path("userId") userId: String
    ): Response<com.rentify.user.app.model.Notification.Notification>

    // API lấy danh sách thông báo theo userId
    @GET("staff/notifications/get-by-user/{userId}")
    suspend fun getNotificationsByUser(
        @Path("userId") userId: String
    ): Response<NotificationResponse> // Trả về một object, không phải danh sách

    @PUT("staff/notifications/mark-all-read/{userId}")
    suspend fun markAllNotificationsAsRead(
        @Path("userId") userId: String
    ): Response<NotificationResponse> // Đổi từ Unit sang NotificationResponse

    @POST("add-report")
    suspend fun createReport(@Body request: ReportRequest): Response<ReportRequest>
}