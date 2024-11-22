package com.rentify.user.app.view.staffScreens.RoomDetailScreen


import android.os.Looper.prepare
import android.util.Log
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.VideoView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.rentify.user.app.R
import com.rentify.user.app.view.auth.components.HeaderComponent
import com.rentify.user.app.view.staffScreens.addRoomScreen.Components.ComfortableLabel
import com.rentify.user.app.view.staffScreens.addRoomScreen.Components.RoomTypeLabel
import com.rentify.user.app.view.staffScreens.addRoomScreen.Components.ServiceLabel
import com.rentify.user.app.viewModel.RoomViewModel.RoomViewModel
import androidx.compose.material3.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RoomDetailScreenPreview() {
    RoomDetailScreen(
        navController = rememberNavController(),
        id = "",
        viewModel = RoomViewModel()
    )
}

@Composable
fun RoomDetailScreen(
    navController: NavHostController,
    id: String,
    viewModel: RoomViewModel = viewModel()
) {
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

                        Text(
                            text = "${roomDetail?.room_type}",
                            color = Color(0xFF7c7b7b),
                            fontSize = 13.sp
                        )
                    }
                    room.photos_room.forEach { photoUrl ->
                        Image(
                            painter = rememberImagePainter(photoUrl),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .padding(5.dp)
                                .clip(RoundedCornerShape(10.dp))
                        )
                    }
                    Log.d("TAG", "RoomDetailScreen:${room.photos_room} ")
                    // Hiển thị video

                    room.video_room.forEach { videoUrl ->
                        VideoPlayer(videoUrl)
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
                            text = " ${roomDetail?.limit_person}",
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

                        Text(
                            text = "${roomDetail?.amenities}",
                            color = Color(0xFF7c7b7b),
                            fontSize = 13.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Column {
                        ServiceLabel()

                        Text(
                            text = " ${roomDetail?.service}",
                            color = Color(0xFF7c7b7b),
                            fontSize = 13.sp
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
                                    // Xử lý sự kiện nhấn nút
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
                                    // Xử lý sự kiện nhấn nút
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
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Error: $error", color = androidx.compose.ui.graphics.Color.Red)
                }

            }
        }
    }
}


@Composable
fun VideoPlayer(videoUrl: String) {
    AndroidView(
        factory = { context ->
            VideoView(context).apply {
                setVideoPath(videoUrl)
                setOnPreparedListener { start() }
            }
        },
        update = { videoView ->
            videoView.setVideoPath(videoUrl)
        },
    )
}
