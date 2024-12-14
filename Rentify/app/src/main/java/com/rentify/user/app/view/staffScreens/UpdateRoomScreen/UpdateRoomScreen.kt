package com.rentify.user.app.view.staffScreens.UpdateRoomScreen

import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.request.ImageRequest
import com.rentify.user.app.R
import com.rentify.user.app.model.Model.NotificationRequest
import com.rentify.user.app.utils.Component.getLoginViewModel
import com.rentify.user.app.view.auth.components.HeaderComponent
import com.rentify.user.app.view.staffScreens.RoomDetailScreen.components.ComfortableOptionsFromApi
import com.rentify.user.app.view.staffScreens.RoomDetailScreen.components.RoomTypeOptionschitiet
import com.rentify.user.app.view.staffScreens.RoomDetailScreen.components.ServiceOptionschitiet
import com.rentify.user.app.view.staffScreens.UpdateRoomScreen.components.ComfortableLabelUpdate
import com.rentify.user.app.view.staffScreens.UpdateRoomScreen.components.ComfortableOptionsUpdate
import com.rentify.user.app.view.staffScreens.UpdateRoomScreen.components.RoomTypeLabelUpdate
import com.rentify.user.app.view.staffScreens.UpdateRoomScreen.components.RoomTypeOptionsUpdate
import com.rentify.user.app.view.staffScreens.UpdateRoomScreen.components.ServiceLabelUpdate
import com.rentify.user.app.view.staffScreens.UpdateRoomScreen.components.ServiceOptionsUpdate
import com.rentify.user.app.view.staffScreens.addRoomScreen.Components.ComfortableLabel
import com.rentify.user.app.view.staffScreens.addRoomScreen.Components.ComfortableLabelAdd
import com.rentify.user.app.view.staffScreens.addRoomScreen.Components.ComfortableOptionsAdd
import com.rentify.user.app.view.staffScreens.addRoomScreen.Components.RoomTypeLabel
import com.rentify.user.app.view.staffScreens.addRoomScreen.Components.SelectMedia
import com.rentify.user.app.view.staffScreens.addRoomScreen.Components.ServiceLabel
import com.rentify.user.app.view.staffScreens.addRoomScreen.Components.ServiceOptions
import com.rentify.user.app.view.staffScreens.addRoomScreen.CustomTextField
import com.rentify.user.app.view.staffScreens.addRoomScreen.StatusDropdown
import com.rentify.user.app.viewModel.NotificationViewModel
import com.rentify.user.app.viewModel.RoomViewModel.RoomViewModel
import java.text.DecimalFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun UpdateRoomScreenPreview(){
//    UpdateRoomScreen(
//        navController= rememberNavController(),
//        id = "",
//        buildingId = ""
//    )
//}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UpdateRoomScreen(
    navController: NavHostController,
    id: String,
    buildingId:String,
    notificationViewModel: NotificationViewModel = viewModel()
){
    val context = LocalContext.current
    val viewModel: RoomViewModel = viewModel(
        factory = RoomViewModel.RoomViewModeFactory(context = context)
    )
    val roomDetail by viewModel.roomDetail.observeAsState()
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    var selectedComfortable by remember { mutableStateOf(listOf<String>()) }
    var selectedService by remember { mutableStateOf(listOf<String>()) }
    val scrollState = rememberScrollState()

    var postTitle by remember { mutableStateOf("") }
    var mota by remember { mutableStateOf("") }
    var numberOfRoommates by remember { mutableStateOf("") }
    var currentPeopleCount by remember { mutableStateOf("") }
    var area by remember { mutableStateOf("") }
    var roomPrice by remember { mutableStateOf("") }
    var roomSale by remember { mutableStateOf("") }
    var Status by remember { mutableStateOf("") }
    var selectedImages by remember { mutableStateOf(listOf<Uri>())}
    var selectedVideos by remember {mutableStateOf(listOf<Uri>())}
    var selectedRoomTypes by remember { mutableStateOf(listOf<String>()) }

    var allComfortable by remember {
        mutableStateOf(
            listOf(
                "Vệ sinh khép kín",
                "Gác xép",
                "Ra vào vân tay",
                "Nuôi pet",
                "Không chung chủ"
            )
        )
    }

    val loginViewModel = getLoginViewModel(context)
    val userData = loginViewModel.getUserData()
    val staffId = userData.userId

    val successMessage by viewModel.successMessage.observeAsState()
    LaunchedEffect(successMessage) {
        successMessage?.let {
            Toast.makeText(context, "Update thành công ", Toast.LENGTH_SHORT).show()
            viewModel.fetchRoomDetailById(id)
            navController.popBackStack()
        }
    }

    LaunchedEffect(id) {
        if (roomDetail == null) {
            viewModel.fetchRoomDetailById(id)
        }
    }

    LaunchedEffect(roomDetail) {
        roomDetail?.let { room ->
            postTitle = room.room_name ?: ""
            mota = room.description ?: ""
            currentPeopleCount = room.limit_person.toString() ?: ""
            area = room.size.toString() ?: ""
            roomPrice = room.price.toString() ?: ""
            roomSale = room.sale.toString() ?: ""
            Status = room.status.toString() ?: ""
            selectedComfortable = roomDetail?.amenities ?: emptyList()
            selectedService = room.service.map { it._id } ?: emptyList()
            // Chuyển đổi dữ liệu từ roomDetail
            selectedImages = room.photos_room.map { photoUrl ->
                Uri.parse("http://10.0.2.2:3000/$photoUrl")
            } ?: emptyList()

            selectedVideos = room.video_room.map { videoUrl ->
                Uri.parse("http://10.0.2.2:3000/$videoUrl")
            } ?: emptyList()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xfff7f7f7))
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xfff7f7f7))
        ) {
            UpdateRoomTopBar(navController = navController)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
                    .background(color = Color(0xfff7f7f7))
                    .padding(15.dp)
            ) {
                // if ở đây
                if (roomDetail != null) {
                    val room = roomDetail!!

                    SelectMedia(
                        onMediaSelected = { images, videos ->
                            selectedImages = images
                            selectedVideos = videos
                        },
                        existingImages = selectedImages,
                        existingVideos = selectedVideos
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    CustomTextField(
                        label = "Tên phòng",
                        value = postTitle,
                        onValueChange = { postTitle = it.uppercase() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        placeholder = "Tên phòng... ( P201 )",
                        isReadOnly = false
                    )

                    CustomTextField(
                        label = "Mô tả",
                        value = mota,
                        onValueChange = { mota = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        placeholder = "Mô tả...",
                        isReadOnly = false
                    )

                    CustomTextField(
                        label = "Giới hạn người ở",
                        value = currentPeopleCount,
                        onValueChange = { currentPeopleCount = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        placeholder = "Giới hạn người ở...",
                        isReadOnly = false
                    )

                    CustomTextField(
                        label = "Diện tích(m2)",
                        value = area,
                        onValueChange = { area = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        placeholder = "Diện tích...",
                        isReadOnly = false
                    )

                    val decimalFormat = remember { DecimalFormat("#,###,###") }
                    val formattedRoomPrice = roomPrice.replace(",", "").toDoubleOrNull()?.let {
                        decimalFormat.format(it)
                    } ?: roomPrice
                    CustomTextField(
                        label = "Giá phòng",
                        value = formattedRoomPrice,
                        onValueChange = { input ->
                            // Remove commas before storing the raw value
                            val rawInput = input.replace(",", "")
                            roomPrice = rawInput
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        placeholder = "Giá phòng...",
                        isReadOnly = false
                    )

                    val formattedRoomSale = roomSale.replace(",", "").toDoubleOrNull()?.let {
                        decimalFormat.format(it)
                    } ?: roomSale
                    CustomTextField(
                        label = "Giá phòng",
                        value = formattedRoomSale,
                        onValueChange = { input ->
                            // Remove commas before storing the raw value
                            val rawInput = input.replace(",", "")
                            roomSale = rawInput
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        placeholder = "Giá phòng...",
                        isReadOnly = false
                    )

                    StatusDropdown(
                        label = "Trạng thái",
                        currentStatus = when (Status) {
                            "0" -> "Chưa cho thuê"
                            "1" -> "Đã cho thuê"
                            else -> ""
                        },
                        onStatusChange = { newStatus -> Status = newStatus },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 5.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    Column(modifier = Modifier.padding(horizontal = 5.dp)) {
                        RoomTypeLabel()
                        Spacer(modifier = Modifier.height(5.dp))
                        RoomTypeOptionschitiet(
                            apiSelectedRoomTypes = listOfNotNull(roomDetail?.room_type),
                            onRoomTypeSelected = { selectedRoomType ->
                                selectedRoomTypes = listOf(selectedRoomType)
                            }
                        )
                    }

                    // tiện nghi cũ
                    Spacer(modifier = Modifier.height(10.dp))
                    Column(modifier = Modifier.padding(horizontal = 5.dp)) {
                        ComfortableLabelAdd { newComfortable ->
                            if (newComfortable !in allComfortable) {
                                allComfortable = allComfortable + newComfortable
                            }
                        }
                        Spacer(modifier = Modifier.height(5.dp))

                        // Gộp hai danh sách và loại bỏ trùng lặp
                        val combinedComfortables = (allComfortable + selectedComfortable).distinct()

                        ComfortableOptionsAdd(
                            selectedComfortable = selectedComfortable,
                            allComfortable = combinedComfortables, // Danh sách đã loại bỏ trùng lặp
                            onComfortableSelected = { comfortable ->
                                selectedComfortable = if (selectedComfortable.contains(comfortable)) {
                                    selectedComfortable - comfortable
                                } else {
                                    selectedComfortable + comfortable
                                }
                            }
                        )
                    }

                    // dịch vụ cũ
                    Spacer(modifier = Modifier.height(10.dp))

                    Column(modifier = Modifier.padding(horizontal = 5.dp)) {
                        ServiceLabel()
                        Spacer(modifier = Modifier.height(5.dp))
                        ServiceOptions(
                            selectedService = selectedService,
                            onServiceSelected = { serviceId ->
                                selectedService = if (selectedService.contains(serviceId)) {
                                    selectedService - serviceId // Bỏ chọn dịch vụ
                                } else {
                                    selectedService + serviceId // Chọn dịch vụ
                                }
                            },
                            buildingId = buildingId
                        )
                    }

                    Spacer(modifier = Modifier.height(90.dp))
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Transparent),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(R.drawable.loading)
                                .decoderFactory(GifDecoder.Factory())
                                .build(),
                            contentDescription = "Loading GIF",
                            modifier = Modifier
                                .size(150.dp)
                                .align(Alignment.Center)
                        )
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(screenHeight.dp / 9.5f)
                .background(color = Color.White)
        ) {
            Box(modifier = Modifier.padding(20.dp)) {
                Button(
                    onClick = {
                        viewModel.updateRoom(
                            id = id,
                            roomName = postTitle,
                            roomType = if (selectedRoomTypes.isNotEmpty()) selectedRoomTypes.first() else roomDetail?.room_type ?: "Default",
                            description = mota,
                            price = roomPrice,
                            size = area,
                            service = selectedService,
                            amenities = selectedComfortable,
                            limit_person = currentPeopleCount,
                            status = Status,
                            photoUris = selectedImages,
                            videoUris = selectedVideos,
                            sale = roomSale
                        )

                        val formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy")
                        val currentTime = LocalDateTime.now().format(formatter)

                        val notificationRequest = NotificationRequest(
                            user_id = staffId,
                            title = "Chỉnh sửa phòng thành công",
                            content = "Phòng ${postTitle} đã được chỉnh sửa thành công lúc: $currentTime",
                        )

                        notificationViewModel.createNotification(notificationRequest)
                    },
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xff5dadff),
                        disabledContainerColor = Color(0xff5dadff).copy(alpha = 0.7f)
                    ),
                ) {
                    Text(
                        text = "Chỉnh sửa Phòng",
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.cairo_regular)),
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun UpdateRoomTopBar(
    navController: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        androidx.compose.material.IconButton(onClick = { navController.popBackStack() }) {
            androidx.compose.material.Icon(
                imageVector = Icons.Filled.ArrowBackIosNew, contentDescription = "Back"
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        androidx.compose.material.Text(
            text = "Chỉnh sửa phòng",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 18.sp,
            style = MaterialTheme.typography.h6
        )
    }
}