package com.rentify.user.app.view.userScreens.roomdetailScreen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.request.ImageRequest
import com.rentify.user.app.R
import com.rentify.user.app.model.Model.BookingRequest
import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.LoginRepository.LoginRepository
import com.rentify.user.app.view.userScreens.roomdetailScreen.components.ImageComponent
import com.rentify.user.app.view.userScreens.roomdetailScreen.components.LayoutComfort
import com.rentify.user.app.view.userScreens.roomdetailScreen.components.LayoutInterior
import com.rentify.user.app.view.userScreens.roomdetailScreen.components.LayoutNameComponent
import com.rentify.user.app.view.userScreens.roomdetailScreen.components.LayoutNoidung
import com.rentify.user.app.view.userScreens.roomdetailScreen.components.LayoutRoom
import com.rentify.user.app.view.userScreens.roomdetailScreen.components.LayoutService
import com.rentify.user.app.view.userScreens.roomdetailScreen.components.baidangPreview
import com.rentify.user.app.view.userScreens.roomdetailScreen.components.datLichXemPhong
import com.rentify.user.app.viewModel.BookingViewModel
import com.rentify.user.app.viewModel.LoginViewModel
import com.rentify.user.app.viewModel.RoomDetailViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LayoutRoomdetails2(
    navController: NavHostController,
    roomId: String?,
    roomDetailViewModel: RoomDetailViewModel = viewModel(),
    bookingViewModel: BookingViewModel = viewModel()
) {
    val scrollState = rememberScrollState()
    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val roomDetail = roomDetailViewModel.roomDetail.observeAsState()
    val landlordDetail = roomDetailViewModel.landlordDetail.observeAsState()
    val emptyRoom = roomDetailViewModel.emptyRoom.observeAsState()
    val userDetail = bookingViewModel.userDetails.observeAsState()

    var roomIds by remember { mutableStateOf(roomId) }
    var isLoading by remember { mutableStateOf(false) }
    var isDataLoaded by remember { mutableStateOf(false) }
    var showLoading by remember { mutableStateOf(true) }
    val landlordId = roomDetail.value?.building_id?.landlord_id?._id
    val buildingId = roomDetail.value?.building_id?._id

    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }

    val apiService = RetrofitService()
    val userRepository = LoginRepository(apiService)
    val factory = remember(context) {
        LoginViewModel.LoginViewModelFactory(userRepository, context.applicationContext)
    }
    val loginViewModel: LoginViewModel = viewModel(factory = factory)
    val userId = loginViewModel.getUserData().userId

    val staffId = userDetail.value?._id ?:""
    val staffName = userDetail.value?.name?:""

    var searchText by remember { mutableStateOf("") } // Lưu trữ trạng thái tìm kiếm

    val imageUrls = roomDetail.value?.photos_room?.map { photoPath ->
        "http://10.0.2.2:3000/$photoPath"
    } ?: emptyList()
    val videoUrls = roomDetail.value?.video_room?.map { videoPath ->
        "http://10.0.2.2:3000/${videoPath.replace("\\", "/")}"
    }

    LaunchedEffect(roomIds, landlordId, buildingId, userId) {
        isLoading = true

        val roomDetailLoaded = async { roomDetailViewModel.fetchRoomDetail(roomIds!!) }
        val landlordDetailLoaded = async {
            landlordId?.let { roomDetailViewModel.fetchLandlordDetail(it) }
        }
        val emptyRoomLoaded = async {
            buildingId?.let { roomDetailViewModel.fetchEmptyRoomDetail(it) }
        }
        val userDetailsLoaded = async { bookingViewModel.fetchUserDetails(userId) }

        awaitAll(roomDetailLoaded, landlordDetailLoaded, emptyRoomLoaded, userDetailsLoaded)
        isLoading = false
        isDataLoaded = true
    }

    LaunchedEffect(isLoading, isDataLoaded) {
        if (!isLoading && isDataLoaded) {
            delay(1000) // Chờ 1 giây
            showLoading = false
        }
    }

    LaunchedEffect(Unit) {
        bookingViewModel.addBookingResult.collect { result ->
            result.onSuccess {
                isLoading = false
                Toast.makeText(context, "Đặt lịch thành công!", Toast.LENGTH_SHORT).show()
                date = ""
                time = ""
                coroutineScope.launch {
                    bottomSheetState.hide()
                }
            }.onFailure {
                // Hiển thị thông báo lỗi
                isLoading = false
                Toast.makeText(context, "Đặt lịch thất bại: ${it.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            roomDetail.value?.let { detail ->
                val userName = userDetail.value?.name ?: ""
                val phoneNumber = userDetail.value?.phoneNumber ?: ""
                val staffId = userDetail.value?._id ?: ""
                datLichXemPhong(
                    staffId = staffId,
                    userName = userName,  // Thay thế bằng giá trị thích hợp
                    phoneNumber = phoneNumber,  // Thay thế bằng giá trị thích hợp
                    staffName = when {
                        true -> detail.building_id.manager_id.name
                        true -> detail.building_id.landlord_id.name
                        else -> ""  // Nếu không có tên cả manager và landlord
                    },
                    staffPhoneNumber = when {
                        true -> detail.building_id.manager_id.phoneNumber
                        true -> detail.building_id.landlord_id.phoneNumber
                        else -> ""  // Nếu không có số điện thoại của cả manager và landlord
                    },
                    date = date,
                    time = time,
                    onDateSelected = { selectedDate -> date = selectedDate },
                    onTimeSelected = { selectedTime -> time = selectedTime },
                    onClick = {
                        if (date.isEmpty() || time.isEmpty()) {
                            Toast.makeText(
                                context, "Vui lòng chọn ngày và giờ xem phòng!", Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            isLoading = true
                            // Tạo yêu cầu đặt lịch nếu hợp lệ
                            val bookingRequest = BookingRequest(
                                user_id = userId,
                                room_id = roomIds!!,
                                building_id = buildingId!!,
                                manager_id = when {
                                    true -> detail.building_id.manager_id._id
                                    true -> detail.building_id.landlord_id._id
                                    else -> ""
                                },
                                check_in_date = "$date $time"
                            )
                            bookingViewModel.addBooking(bookingRequest)
                        }
                    },
                    isLoading = isLoading
                )
            }
        }
    ) {
        if (isLoading || !isDataLoaded || showLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
                    .statusBarsPadding()
                    .navigationBarsPadding(),
            ) {
                LayoutNameComponent(navController)
            }
            Box(
                modifier = Modifier
                    .fillMaxSize() // Chiếm toàn bộ màn hình
                    .background(Color.Transparent), // Giữ nền trong suốt
                contentAlignment = Alignment.Center // Căn giữa nội dung cả chiều ngang và dọc
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(R.drawable.loading) // Đường dẫn tới GIF
                        .decoderFactory(GifDecoder.Factory()) // Bộ giải mã GIF
                        .build(),
                    contentDescription = "Loading GIF",
                    modifier = Modifier
                        .size(150.dp) // Tăng kích thước GIF (tuỳ chỉnh)
                        .align(Alignment.Center) // Đảm bảo GIF được căn giữa
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xffF7F7F7))
                    .verticalScroll(scrollState)
                    .statusBarsPadding()
                    .navigationBarsPadding()
            ) {
                LayoutNameComponent(navController)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                ) {
                    ImageComponent(
                        imageUrls = imageUrls,
                        videoUrls = videoUrls
                    )
                    roomDetail.value?.let { detail ->
                        LayoutNoidung(
                            roomName = detail.room_name,
                            roomType = detail.room_type ?: "Phòng Trọ",
                            priceRange = detail.price ?: 0,
                            buildingName = detail.building_id.nameBuilding ?: "Chưa có tên",
                            fullAddress = detail.building_id.address ?: "Chưa có địa chỉ",
                            sale = detail.sale, // Truyền trường sale
                            onClick = {
                                coroutineScope.launch {
                                    bottomSheetState.show()
                                }
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(5.dp))
                landlordDetail.value?.totalRooms?.let {
                    LayoutRoom(
                        landlordId = landlordDetail.value?.landlord?._id ?: "",
                        landlordName = landlordDetail.value?.landlord?.name ?: "khong ac dinh",
                        totalRooms = it,
                        listEmptyRoom = emptyRoom.value ?: emptyList(),
                        roomId = roomId!!,
                        onRoomSelected = { selectedRoomName ->
                            val matchingRoom =
                                emptyRoom.value?.find { it.room_name == selectedRoomName }
                            if (matchingRoom != null) {
                                roomIds = matchingRoom._id
                            }
                        }

                    )
                }
                Spacer(modifier = Modifier.padding(5.dp))
                LayoutService(
                    listAmenities = roomDetail.value?.building_id?.serviceFees ?: emptyList()
                )
                Spacer(modifier = Modifier.padding(5.dp))
                LayoutComfort(listAmenities = roomDetail.value?.amenities ?: emptyList())
                Spacer(modifier = Modifier.padding(5.dp))
                LayoutInterior(listAmenities = roomDetail.value?.service ?: emptyList())
                Spacer(modifier = Modifier.padding(5.dp))
//                baidangPreview(navController, staffId, staffName)
            }
        }
    }
}