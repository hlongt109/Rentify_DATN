package com.rentify.user.app.view.staffScreens.RoomDetailScreen


import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
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
import androidx.compose.ui.text.font.FontWeight
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import coil.compose.AsyncImage
import com.rentify.user.app.view.staffScreens.RoomDetailScreen.components.ComfortableOptionsFromApi
import com.rentify.user.app.view.staffScreens.RoomDetailScreen.components.RoomTypeOptionschitiet
import com.rentify.user.app.view.staffScreens.RoomDetailScreen.components.ServiceOptionschitiet


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RoomDetailScreenPreview() {
    RoomDetailScreen(
        navController = rememberNavController(),
        id = "",
    )
}

@Composable
fun RoomDetailScreen(
    navController: NavHostController,
    id: String,
) {
    val context = LocalContext.current
    val viewModel: RoomViewModel = viewModel(
        factory = RoomViewModel.RoomViewModeFactory(context)
    )
    // Lấy thông tin chi tiết phòng từ ViewModel
    val roomDetail by viewModel.roomDetail.observeAsState()
    val error by viewModel.error.observeAsState()
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    val scrollState = rememberScrollState()
    // Gọi API để lấy thông tin phòng chi tiết, chỉ khi id thay đổi
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
                title = "Hiển thị chi tiết phòng ",
                navController = navController
            )
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
                        Text(
                            text = "Tên tòa nhà*", color = Color(0xFF7c7b7b), fontSize = 13.sp
                        )
                        Text(
                            text = "${roomDetail?.room_name}",
                            color = Color(0xFF7c7b7b),
                            fontSize = 13.sp
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                    ) {
                        Text(
                            text = "Mô tả phòng*", color = Color(0xFF7c7b7b), fontSize = 13.sp
                        )
                        Text(
                            text = "${roomDetail?.description}",
                            color = Color(0xFF7c7b7b),
                            fontSize = 13.sp
                        )
                    }
                    Column {
                        RoomTypeLabel()

                        // Gọi RoomTypeOptions với danh sách từ roomDetail
                        RoomTypeOptionschitiet(
                            apiSelectedRoomTypes = listOfNotNull(roomDetail?.room_type), // Chuyển loại phòng từ roomDetail thành danh sách
                            onRoomTypeSelected = { selectedRoomType ->
                                println("Selected Room Type: $selectedRoomType")
                            }
                        )
                    }


                    Text(text = "Hình ảnh:", fontSize = 16.sp, color = Color.Black)
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(room.photos_room ?: emptyList()) { photoUrl ->
                            val urianh : String="http://10.0.2.2:3000/${photoUrl}"
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
                    Text(text = "Videos:", fontSize = 16.sp, color = Color.Black)
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(room.video_room ?: emptyList()) { videoUrl ->
                            val urivideo : String="http://10.0.2.2:3000/${videoUrl}"
                            ExoPlayerView(context = context, videoUri = Uri.parse(urivideo))
                        }
                    }

                    Log.d("TAG", "RoomDetailScreen:${room.video_room} ")
                    // giới hạn người
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                    ) {
                        Text(
                            text = "Giới hạn người *",
                            //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                            color = Color(0xFF7c7b7b),
                            //  fontWeight = FontWeight(700),
                            fontSize = 13.sp,

                            )
                        Text(
                            text = " ${roomDetail?.limitPerson}",
                            color = Color(0xFF7c7b7b),
                            fontSize = 13.sp
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                    ) {
                        Text(
                            text = "Diện tích (m2)*",
                            //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                            color = Color(0xFF7c7b7b),
                            //  fontWeight = FontWeight(700),
                            fontSize = 13.sp,

                            )
                        Text(
                            text = " ${roomDetail?.size}",
                            color = Color(0xFF7c7b7b),
                            fontSize = 13.sp
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                    ) {
                        Text(
                            text = "Giá phòng*",
                            //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                            color = Color(0xFF7c7b7b),
                            //  fontWeight = FontWeight(700),
                            fontSize = 13.sp,

                            )
                        Text(
                            text = " ${roomDetail?.price}",
                            color = Color(0xFF7c7b7b),
                            fontSize = 13.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(3.dp))
                    Column {
                        ComfortableLabel()
                        roomDetail?.amenities?.let { amenities ->
                            ComfortableOptionsFromApi(
                                apiSelectedComfortable = amenities
                            )
                        } ?: run {
                            Text(
                                text = "Không có tiện nghi nào được cung cấp.",
                                color = Color(0xFF7c7b7b),
                                fontSize = 13.sp
                            )
                        }
                    }

                    Log.d("amenities", "tiện nghi :${roomDetail?.amenities} ")
                    Spacer(modifier = Modifier.height(10.dp))
                    Column {
                        ServiceLabel()

                        roomDetail?.service?.let { serviceList ->
                            ServiceOptionschitiet(
                                selectedServices = serviceList )
                        } ?: run {
                            Text(
                                text = "Không có dịch vụ nào được cung cấp.",
                                color = Color(0xFF7c7b7b),
                                fontSize = 13.sp
                            )
                        }
                    }
                    Log.d("service", "dịch vụ :${roomDetail?.service}")
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
                                    navController.navigate("UpdateRoomScreen")
                                },
                                indication = null, // Tắt hiệu ứng gợn sóng
                                interactionSource = remember { MutableInteractionSource() }
                            ),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically // Căn giữa theo chiều dọc
                    ) {
                        Text(
                            text = "Update Room",
                            modifier = Modifier.padding(5.dp),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold, // Đậm chữ
                            color = Color.White, // Màu chữ trắng
                            lineHeight = 24.sp // Khoảng cách giữa các dòng
                        )
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
                                    viewModel.deleteRoomById(id)
                                    navController.popBackStack()
                                },
                                indication = null, // Tắt hiệu ứng gợn sóng
                                interactionSource = remember { MutableInteractionSource() }
                            ),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically // Căn giữa theo chiều dọc
                    ) {
                        Text(
                            text = "Delete Room",
                            modifier = Modifier.padding(5.dp),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold, // Đậm chữ
                            color = Color.White, // Màu chữ trắng
                            lineHeight = 24.sp // Khoảng cách giữa các dòng
                        )
                    }
                } else {
                    // Hiển thị trạng thái loading hoặc lỗi nếu không có thông tin
                    Text(text = "Loading room details...")
                }

                // Hiển thị thông báo lỗi nếu có
                if (error != null) {
                    Toast.makeText(context,"thất bại: $error ",Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context,"thành công  ",Toast.LENGTH_SHORT).show()
                }

            }
        }
    }
}


@Composable
fun ExoPlayerView(context: Context, videoUri: Uri) {
    AndroidView(
        factory = {
            val exoPlayer = ExoPlayer.Builder(context).build().apply {
                setMediaItem(MediaItem.fromUri(videoUri))
                prepare()
                playWhenReady = false // Start playing when the user interacts
            }
            PlayerView(context).apply {
                player = exoPlayer
            }
        },
        modifier = Modifier
            .size(200.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.Black)
    )
}