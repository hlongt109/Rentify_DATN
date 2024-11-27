package com.rentify.user.app.view.staffScreens.UpdateRoomScreen

import android.content.Context
import android.net.Uri
import android.util.Log
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.rentify.user.app.R
import com.rentify.user.app.view.auth.components.HeaderComponent
import com.rentify.user.app.view.staffScreens.RoomDetailScreen.components.ComfortableOptionsFromApi
import com.rentify.user.app.view.staffScreens.RoomDetailScreen.components.RoomTypeOptionschitiet
import com.rentify.user.app.view.staffScreens.RoomDetailScreen.components.ServiceOptionschitiet
import com.rentify.user.app.view.staffScreens.addRoomScreen.Components.ComfortableLabel
import com.rentify.user.app.view.staffScreens.addRoomScreen.Components.ComfortableOptions
import com.rentify.user.app.view.staffScreens.addRoomScreen.Components.RoomTypeLabel
import com.rentify.user.app.view.staffScreens.addRoomScreen.Components.RoomTypeOptions
import com.rentify.user.app.view.staffScreens.addRoomScreen.Components.SelectMedia
import com.rentify.user.app.view.staffScreens.addRoomScreen.Components.ServiceLabel
import com.rentify.user.app.view.staffScreens.addRoomScreen.Components.ServiceOptions
import com.rentify.user.app.viewModel.RoomViewModel.RoomViewModel

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun UpdateRoomScreenPreview(){
    UpdateRoomScreen(
        navController= rememberNavController(),
        id = "",
    )
}
@Composable
fun UpdateRoomScreen(
    navController: NavHostController,
    id: String,
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
    Log.d("TAG", " tiện nghi select : $selectedComfortable")
    Log.d("TAG", "dịch vụ select: $selectedService")
    var postTitle by remember { mutableStateOf("") }
    var mota by remember { mutableStateOf("") }
    var numberOfRoommates by remember { mutableStateOf("") }
    var currentPeopleCount by remember { mutableStateOf("") }
    var area by remember { mutableStateOf("") }
    var roomPrice by remember { mutableStateOf("") }
    var selectedImages by remember { mutableStateOf(listOf<Uri>())}
    var selectedVideos by remember {mutableStateOf(listOf<Uri>())}
    var selectedRoomTypes by remember { mutableStateOf(listOf<String>()) }
    Log.d("TAG", " tiện nghi select : $selectedComfortable")
    Log.d("TAG", "dịch vụ select: $selectedService")
    Log.d("TAG", " tiện nghi select : $selectedComfortable")
    Log.d("TAG", "Người select: $currentPeopleCount")
    // Observe states
    val isLoading by viewModel.isLoading.observeAsState(false)
    val addRoomResponse by viewModel.addRoomResponse.observeAsState()
    val updateRoomResponse by viewModel.updateRoomResponse.observeAsState()
    val error by viewModel.error.observeAsState()
    // Observe states
    LaunchedEffect(id) {
        // Kiểm tra xem đã có dữ liệu phòng chưa, nếu chưa thì gọi API
        if (roomDetail == null) {
            viewModel.fetchRoomDetailById(id)
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xfff7f7f7))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .background(color = Color(0xfff7f7f7))
                .padding(bottom = screenHeight.dp / 7f)

        ) {


            HeaderComponent(
                backgroundColor = Color(0xffffffff),
                title = "Update room",
                navController = navController
            )
            Text("id : ${id}")
            Spacer(modifier = Modifier.height(10.dp))
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
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                    ) {
                        androidx.compose.material3.Text(
                            text = "Tên phòng*",
                            color = Color(0xFF7c7b7b),
                            fontSize = 13.sp
                        )
                        TextField(
                            value = postTitle,
                            onValueChange = { postTitle = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(53.dp),
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color(0xFFcecece),
                                unfocusedIndicatorColor = Color(0xFFcecece),
                                focusedPlaceholderColor = Color.Black,
                                unfocusedPlaceholderColor = Color.Gray,
                                unfocusedContainerColor = Color(0xFFf7f7f7),
                                focusedContainerColor = Color.White
                            ),
                            placeholder = {
                                androidx.compose.material3.Text(
                                    text = "${roomDetail?.room_name}",
                                    fontSize = 14.sp,
                                    color = Color(0xFF898888),
                                    fontFamily = FontFamily(Font(R.font.cairo_regular))
                                )
                            },
                            shape = RoundedCornerShape(size = 8.dp),
                            textStyle = TextStyle(
                                color = Color.Black,
                                fontFamily = FontFamily(Font(R.font.cairo_regular))
                            )
                        )
                    }
                    // mota
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                    ) {
                        androidx.compose.material3.Text(
                            text = "Mô tả phòng*",
                            color = Color(0xFF7c7b7b),
                            fontSize = 13.sp
                        )
                        TextField(
                            value = mota,
                            onValueChange = { mota = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(53.dp),
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color(0xFFcecece),
                                unfocusedIndicatorColor = Color(0xFFcecece),
                                focusedPlaceholderColor = Color.Black,
                                unfocusedPlaceholderColor = Color.Gray,
                                unfocusedContainerColor = Color(0xFFf7f7f7),
                                focusedContainerColor = Color.White
                            ),
                            placeholder = {
                                androidx.compose.material3.Text(
                                    text = "${roomDetail?.description}",
                                    fontSize = 13.sp,
                                    color = Color(0xFF898888),
                                    fontFamily = FontFamily(Font(R.font.cairo_regular))
                                )
                            },
                            shape = RoundedCornerShape(size = 8.dp),
                            textStyle = TextStyle(
                                color = Color.Black,
                                fontFamily = FontFamily(Font(R.font.cairo_regular))
                            )
                        )
                    }
                    Column {
                        RoomTypeLabel()
                        RoomTypeOptions(
                            selectedRoomTypes = selectedRoomTypes,
                            onRoomTypeSelected = { roomType ->
                                selectedRoomTypes =
                                    listOf(roomType)  // Ensure only one room type is selected at a time
                            }
                        )
                    }
                    androidx.compose.material3.Text(
                        text = "Hình ảnh:",
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(room.photos_room ?: emptyList()) { photoUrl ->
                            val urianh: String = "http://10.0.2.2:3000/${photoUrl}"
                            AsyncImage(
                                model = urianh,
                                contentDescription = "Room Photo",
                                modifier = Modifier
                                    .size(150.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color.LightGray),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }

                    // Display videos
                    androidx.compose.material3.Text(
                        text = "Videos:",
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(room.video_room ?: emptyList()) { videoUrl ->
                            val urivideo: String = "http://10.0.2.2:3000/${videoUrl}"
                            com.rentify.user.app.view.staffScreens.RoomDetailScreen.ExoPlayerView(context = context, videoUri = Uri.parse(urivideo))
                        }
                    }
                    Log.d("TAG", "RoomDetailScreen:${room.video_room} ")
                    SelectMedia { images, videos ->
                        selectedImages = images
                        selectedVideos = videos
                    }
                    // giới hạn người
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                    ) {
                        androidx.compose.material3.Text(
                            text = "Giới hạn người *",
                            color = Color(0xFF7c7b7b),
                            fontSize = 13.sp
                        )
                        TextField(
                            value = numberOfRoommates,
                            onValueChange = { numberOfRoommates = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(53.dp),
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color(0xFFcecece),
                                unfocusedIndicatorColor = Color(0xFFcecece),
                                focusedPlaceholderColor = Color.Black,
                                unfocusedPlaceholderColor = Color.Gray,
                                unfocusedContainerColor = Color(0xFFf7f7f7),
                                focusedContainerColor = Color.White
                            ),
                            placeholder = {
                                androidx.compose.material3.Text(
                                    text = "${roomDetail?.limit_person}",
                                    fontSize = 13.sp,
                                    color = Color(0xFF898888),
                                    fontFamily = FontFamily(Font(R.font.cairo_regular))
                                )
                            },
                            shape = RoundedCornerShape(size = 8.dp),
                            textStyle = TextStyle(
                                color = Color.Black,
                                fontFamily = FontFamily(Font(R.font.cairo_regular))
                            )
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                    ) {
                        androidx.compose.material3.Text(
                            text = "Diện tích(m2) *",
                            color = Color(0xFF7c7b7b),
                            fontSize = 13.sp
                        )
                        TextField(
                            value = area,
                            onValueChange = { area = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(53.dp),
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color(0xFFcecece),
                                unfocusedIndicatorColor = Color(0xFFcecece),
                                focusedPlaceholderColor = Color.Black,
                                unfocusedPlaceholderColor = Color.Gray,
                                unfocusedContainerColor = Color(0xFFf7f7f7),
                                focusedContainerColor = Color.White
                            ),
                            placeholder = {
                                androidx.compose.material3.Text(
                                    text = "${roomDetail?.size}",
                                    fontSize = 13.sp,
                                    color = Color(0xFF898888),
                                    fontFamily = FontFamily(Font(R.font.cairo_regular))
                                )
                            },
                            shape = RoundedCornerShape(size = 8.dp),
                            textStyle = TextStyle(
                                color = Color.Black,
                                fontFamily = FontFamily(Font(R.font.cairo_regular))
                            )
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                    ) {
                        androidx.compose.material3.Text(
                            text = "Giá phòng *",
                            color = Color(0xFF7c7b7b),
                            fontSize = 13.sp
                        )
                        TextField(
                            value = roomPrice,
                            onValueChange = { roomPrice = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(53.dp),
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color(0xFFcecece),
                                unfocusedIndicatorColor = Color(0xFFcecece),
                                focusedPlaceholderColor = Color.Black,
                                unfocusedPlaceholderColor = Color.Gray,
                                unfocusedContainerColor = Color(0xFFf7f7f7),
                                focusedContainerColor = Color.White
                            ),
                            placeholder = {
                                androidx.compose.material3.Text(
                                    text = "${roomDetail?.price}",
                                    fontSize = 13.sp,
                                    color = Color(0xFF898888)
                                )
                            },
                            shape = RoundedCornerShape(size = 8.dp),
                            textStyle = TextStyle(
                                color = Color.Black,
                                fontFamily = FontFamily(Font(R.font.cairo_regular))
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(3.dp))
                    Spacer(modifier = Modifier.height(3.dp))
                    Column {
                        ComfortableLabel()
                        ComfortableOptions(
                            selectedComfortable = selectedComfortable,
                            onComfortableSelected = { comfortable ->
                                selectedComfortable = if (selectedComfortable.contains(comfortable)) {
                                    selectedComfortable - comfortable
                                } else {
                                    selectedComfortable + comfortable
                                }
                            })
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Spacer(modifier = Modifier.height(10.dp))
                    Column {
                        ServiceLabel()
                        ServiceOptions(
                            selectedService = selectedService,
                            onServiceSelected = { service ->
                                selectedService = if (selectedService.contains(service)) {
                                    selectedService - service
                                } else {
                                    selectedService + service
                                }
                            })
                    }
                    Row(
                        modifier = Modifier
                            .height(50.dp)
                            .fillMaxWidth()
                            .padding(start = 15.dp, end = 15.dp, top = 10.dp)
                            .background(color = Color(0xFF84d8ff))
                            .border(
                                width = 1.dp,
                                color = Color(0xFF84d8ff),
                                shape = RoundedCornerShape(20.dp)
                            )
                            .clickable(
                                onClick = {
                                    viewModel.updateRoom(
                                        id = id,
                                        roomName = postTitle,
                                        roomType = selectedRoomTypes.firstOrNull() ?: "Default",
                                        description = mota,
                                        price = roomPrice.toDoubleOrNull() ?: 0.0,
                                        size = area,
                                        service = selectedService,
                                        amenities = selectedComfortable,
                                        limit_person = numberOfRoommates.toIntOrNull() ?: 0,
                                        photoUris = selectedImages,
                                        videoUris = selectedVideos
                                    )
                                },
                                indication = null, // Tắt hiệu ứng gợn sóng
                                interactionSource = remember { MutableInteractionSource() }
                            ),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically // Căn giữa theo chiều dọc
                    ) {
                        androidx.compose.material3.Text(
                            text = "Update Room",
                            modifier = Modifier.padding(5.dp),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold, // Đậm chữ
                            color = Color.White, // Màu chữ trắng
                            lineHeight = 24.sp // Khoảng cách giữa các dòng
                        )
                    }
                } else {
                    // Hiển thị trạng thái loading hoặc lỗi nếu không có thông tin
                    androidx.compose.material3.Text(text = "Loading room details...")
                }

                // Hiển thị thông báo lỗi nếu có
                if (error != null) {
                    Log.d("PHUC", "thất bại: $error")
                } else {
                    Log.d("PHUC", "thành công")
                }

            }
        }
    }
}