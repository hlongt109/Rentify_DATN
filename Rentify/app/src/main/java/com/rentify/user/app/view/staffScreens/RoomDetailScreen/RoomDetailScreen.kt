package com.rentify.user.app.view.staffScreens.RoomDetailScreen

import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.annotation.OptIn
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.view.auth.components.HeaderComponent
import com.rentify.user.app.view.staffScreens.addRoomScreen.Components.ComfortableLabel
import com.rentify.user.app.view.staffScreens.addRoomScreen.Components.RoomTypeLabel
import com.rentify.user.app.view.staffScreens.addRoomScreen.Components.ServiceLabel
import com.rentify.user.app.viewModel.RoomViewModel.RoomViewModel
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.request.ImageRequest
import com.rentify.user.app.R
import com.rentify.user.app.model.Model.NotificationRequest
import com.rentify.user.app.utils.Component.getLoginViewModel
import com.rentify.user.app.view.staffScreens.RoomDetailScreen.components.ComfortableOptionsFromApi
import com.rentify.user.app.view.staffScreens.RoomDetailScreen.components.RoomTypeOptionschitiet
import com.rentify.user.app.view.staffScreens.RoomDetailScreen.components.ServiceOptionschitiet
import com.rentify.user.app.viewModel.NotificationViewModel
import java.text.DecimalFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun RoomDetailScreenPreview() {
//    RoomDetailScreen(
//        navController = rememberNavController(),
//        id = "",
//    )
//}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RoomDetailScreen(
    navController: NavHostController,
    id: String,
    notificationViewModel: NotificationViewModel = viewModel()
) {
    val context = LocalContext.current
    val viewModel: RoomViewModel = viewModel(
        factory = RoomViewModel.RoomViewModeFactory(context)
    )
    // Lấy thông tin chi tiết phòng từ ViewModel
    val roomDetail by viewModel.roomDetail.observeAsState()
    val configuration = LocalConfiguration.current
    val scrollState = rememberScrollState()

    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = id) {
        viewModel.fetchRoomDetailById(id)
    }

    LaunchedEffect(roomDetail) {
        if (roomDetail == null) {
            viewModel.fetchRoomDetailById(id)
        }
    }

    var isFullScreen by remember { mutableStateOf(false) }
    val isLoading by viewModel.isLoading.observeAsState(false)

    val loginViewModel = getLoginViewModel(context)
    val userData = loginViewModel.getUserData()
    val staffId = userData.userId

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
                .padding(bottom = 15.dp)

        ) {
            RoomDetailManagerTopBar(navController = navController)
            if (isLoading){
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
            }else{
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(scrollState)
                        .background(color = Color(0xfff7f7f7))
                        .padding(10.dp)
                ) {
                    // if ở đây
                    if (roomDetail != null) {
                        val room = roomDetail!!

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

                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp)
                        ) {
                            items(room.video_room ?: emptyList()) { videoUrl ->
                                val urivideo: String = "http://10.0.2.2:3000/${videoUrl}"
                                Box(
                                    modifier = Modifier
                                        .fillParentMaxWidth() // Đảm bảo item chiếm đủ chiều rộng trong LazyRow
                                        .clickable { isFullScreen = !isFullScreen } // Thêm sự kiện nhấn để phóng to
                                ) {
                                    ExoPlayerView(
                                        context = context,
                                        videoUri = Uri.parse(urivideo),
                                        isFullScreen = isFullScreen
                                    )
                                }
                            }
                        }


                        CustomTextField(
                            label = "Tên nhà",
                            value = roomDetail?.room_name ?: "",
                            onValueChange = { newValue ->
                                println("New Value: $newValue")
                            },
                            modifier = Modifier.padding(5.dp),
                            isReadOnly = true
                        )

                        CustomTextField(
                            label = "Mô tả",
                            value = roomDetail?.description ?: "",
                            onValueChange = { newValue ->
                                println("New Value: $newValue")
                            },
                            modifier = Modifier.padding(5.dp),
                            isReadOnly = true
                        )

                        CustomTextField(
                            label = "Giới hạn người",
                            value = if (roomDetail?.limit_person?.let { it > 0 } == true) {
                                "${roomDetail!!.limit_person} người"
                            } else {
                                "Không giới hạn người ở"
                            },
                            onValueChange = { newValue ->
                                println("New Value: $newValue")
                            },
                            modifier = Modifier.padding(5.dp),
                            isReadOnly = true
                        )

                        CustomTextField(
                            label = "Diện tích",
                            value = "${roomDetail?.size}m2" ?: "",
                            onValueChange = { newValue ->
                                println("New Value: $newValue")
                            },
                            modifier = Modifier.padding(5.dp),
                            isReadOnly = true
                        )

                        val formattedPrice = DecimalFormat("#,###,###").format(roomDetail?.price)
                        CustomTextField(
                            label = "Giá phòng",
                            value = "$formattedPrice VND" ?: "",
                            onValueChange = { newValue ->
                                println("New Value: $newValue")
                            },
                            modifier = Modifier.padding(5.dp),
                            isReadOnly = true
                        )

                        val formattedSale = DecimalFormat("#,###,###").format(roomDetail?.sale)
                        CustomTextField(
                            label = "Giảm giá",
                            value = "$formattedSale VND" ?: "",
                            onValueChange = { newValue ->
                                println("New Value: $newValue")
                            },
                            modifier = Modifier.padding(5.dp),
                            isReadOnly = true
                        )

                        val roomStatus = if(roomDetail?.status == 0) "Chưa cho thuê" else "Đã cho thuê"
                        CustomTextField(
                            label = "Trạng thái",
                            value = roomStatus ?: "",
                            onValueChange = { newValue ->
                                println("New Value: $newValue")
                            },
                            modifier = Modifier.padding(5.dp),
                            isReadOnly = true
                        )

                        Spacer(modifier = Modifier.height(10.dp))
                        Column(modifier = Modifier.padding(horizontal = 5.dp)) {
                            RoomTypeLabel()
                            Spacer(modifier = Modifier.padding(5.dp))
                            RoomTypeOptionschitiet(apiSelectedRoomTypes = listOfNotNull(roomDetail?.room_type),
                                onRoomTypeSelected = { selectedRoomType ->
                                    println("Selected Room Type: $selectedRoomType")
                                })
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Column(modifier = Modifier.padding(horizontal = 5.dp)) {
                            ComfortableLabel()
                            Spacer(modifier = Modifier.padding(5.dp))
                            roomDetail?.amenities?.let { amenities ->
                                ComfortableOptionsFromApi(
                                    selectedComfortables = amenities,
                                    onComfortableChange = { updatedAmenities ->
                                        println("Selected amenities: $updatedAmenities")
                                    },
                                    databaseComfortables = amenities
                                )
                            } ?: run {
                                Text(
                                    text = "Không có tiện nghi nào được cung cấp.",
                                    color = Color(0xFF7c7b7b),
                                    fontSize = 13.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Column(modifier = Modifier.padding(horizontal = 5.dp)) {
                            ServiceLabel()
                            Spacer(modifier = Modifier.padding(5.dp))
                            roomDetail?.service?.let { service ->
                                ServiceOptionschitiet(
                                    selectedServices = service
                                )
                            } ?: run {
                                Text(
                                    text = "Không có dịch vụ nào được cung cấp.",
                                    color = Color(0xFF7c7b7b),
                                    fontSize = 13.sp
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(50.dp))
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
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(Color.White)
                    .height(70.dp)
                    .padding(start = 15.dp, end = 15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        val id = roomDetail?.id
                        val buildingId = roomDetail?.buildingId
                        Log.d("RoomDetailScreen", "$buildingId")
                        navController.navigate("UpdateRoomScreen/${id}/${buildingId}")
                    },
                    modifier = Modifier
                        .weight(1f) // Chia đều không gian ngang
                        .height(50.dp)
                        .background(color = Color(0xFF84d8ff), shape = RoundedCornerShape(10.dp))
                        .border(
                            width = 1.dp,
                            color = Color(0xFF84d8ff),
                            shape = RoundedCornerShape(10.dp)
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Update Room",
                        modifier = Modifier
                            .size(45.dp)
                            .padding(10.dp),
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.padding(10.dp))
                IconButton(
                    onClick = {
                        showDialog = true
                    },
                    modifier = Modifier
                        .weight(1f) // Chia đều không gian ngang
                        .height(50.dp)
                        .background(color = Color.Red, shape = RoundedCornerShape(10.dp))
                        .border(
                            width = 1.dp, color = Color.Red, shape = RoundedCornerShape(10.dp)
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Room",
                        modifier = Modifier
                            .size(45.dp)
                            .padding(10.dp),
                        tint = Color.White
                    )
                }

                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false }, // Đóng dialog nếu nhấn ra ngoài
                        title = { Text(text = "Xác nhận xóa phòng") },
                        text = { Text(text = "Bạn có chắc chắn muốn xóa phòng này không?") },
                        confirmButton = {
                            TextButton(onClick = {
                                viewModel.deleteRoomById(roomDetail?.id!!)
                                showDialog = false
                                val formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy")
                                val currentTime = LocalDateTime.now().format(formatter)

                                val notificationRequest = NotificationRequest(
                                    user_id = staffId,
                                    title = "Xoá phòng thành công",
                                    content = "Phòng ${roomDetail?.room_name} đã được xoá thành công lúc: $currentTime",
                                )

                                notificationViewModel.createNotification(notificationRequest)
                                navController.popBackStack()
                            }) {
                                Text("Xóa", color = Color.Red)
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showDialog = false }) {
                                Text("Hủy")
                            }
                        }
                    )
                }
            }
        }
    }
}


@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
fun ExoPlayerView(context: Context, videoUri: Uri, isFullScreen: Boolean) {
    AndroidView(
        factory = {
            val exoPlayer = ExoPlayer.Builder(context).build().apply {
                setMediaItem(MediaItem.fromUri(videoUri))
                prepare()
                playWhenReady = false
            }
            PlayerView(context).apply {
                layoutParams = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                player = exoPlayer
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(if (isFullScreen) LocalConfiguration.current.screenHeightDp.dp else 200.dp) // Phóng to màn hình nếu isFullScreen là true
            .clip(RoundedCornerShape(10.dp))
            .background(Color.Black)
    )
}

@Composable
fun RoomDetailManagerTopBar(
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
            text = "Chi tiết phòng",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 18.sp,
            style = MaterialTheme.typography.h6
        )
    }
}

@Composable
fun CustomTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isReadOnly: Boolean = false
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(
                    color = Color.Transparent, shape = RoundedCornerShape(8.dp)
                )
                .border(
                    width = 1.dp, color = Color(0xFF908b8b), shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 12.dp, vertical = 10.dp),
            textStyle = TextStyle(
                fontSize = 14.sp,
                color = Color(0xFF989898),
            ),
            enabled = !isReadOnly,
            decorationBox = { innerTextField ->
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp)
                ) {
                    innerTextField()
                }
            }
        )
    }
}