package com.rentify.user.app.view.staffScreens.postingList.PostingListComponents

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController

import coil.compose.rememberImagePainter
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerView
import com.rentify.user.app.viewModel.PostViewModel.PostViewModel
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.window.Dialog
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.rentify.user.app.R
import com.rentify.user.app.network.RetrofitClient

import com.rentify.user.app.view.staffScreens.UpdatePostScreen.TriangleShape
import com.rentify.user.app.view.staffScreens.addPostScreen.Components.ComfortableLabel
import com.rentify.user.app.view.staffScreens.addPostScreen.Components.ServiceLabel
import com.rentify.user.app.view.staffScreens.addRoomScreen.Components.RoomTypeLabel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PostDetailScreen(navController: NavController, postId: String, viewModel: PostViewModel = PostViewModel()) {
    val postDetail by viewModel.postDetail.observeAsState()
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    val isPostUpdated = remember { mutableStateOf(false) } // Trạng thái để kiểm tra khi nào cập nhật

    // Chạy khi màn hình được hiển thị lại hoặc khi postId thay đổi
    LaunchedEffect(postId, isPostUpdated.value) {
        Log.d("detail", "RequestBody check: ")
        viewModel.getPostDetail(postId)

        isPostUpdated.value = false  // Reset lại trạng thái sau khi tải dữ liệu
    }


    postDetail?.let { detail ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xfff7f7f7))
        ) {
            Column(
                modifier = Modifier
                    .statusBarsPadding()
                    .navigationBarsPadding()
            ){
                Row(
                    modifier = Modifier

                        .fillMaxWidth()
                        .background(color = Color(0xfff7f7f7)), // Để IconButton nằm bên trái
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = {   navController.navigate("POSTING_STAFF")}) {
                        Image(
                            painter = painterResource(id = R.drawable.back),
                            contentDescription = null,
                            modifier = Modifier.size(30.dp, 30.dp)
                        )
                    }
                    Text(
                        text = "Chi tiết bài đăng",
                        //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                        color = Color.Black,
                        fontWeight = FontWeight(700),
                        fontSize = 17.sp,
                    )
                    IconButton(onClick = { /*TODO*/ }) {
                    }
                }
            Column(
                modifier = Modifier.padding(16.dp)
                    .height(screenHeight.dp / 1.3f)
                    .verticalScroll(scrollState)
            )
            {

                // Hiển thị ảnh/video đầu tiên
                PostImageSlider(
                    images = detail.photos ?: emptyList(),
                    videos = detail.videos ?: emptyList()
                )
                Spacer(modifier = Modifier.height(16.dp))
                //loai phong
                detail.room_type?.let { room_type ->
                    val selectedServices = room_type
                        .removeSurrounding("[", "]") // Loại bỏ dấu []
                        .split(",") // Tách thành danh sách
                        .map { it.trim() } // Loại bỏ khoảng trắng thừa
                    RoomTypeDisplay(it = selectedServices)
                }
                Spacer(modifier = Modifier.height(8.dp))
// Tiêu đề bài đăng

                Text(
                    text = detail.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    color = Color(0xFFF55151),
                    modifier = Modifier.padding(horizontal = 10.dp),
                    fontWeight = FontWeight.W700,
                    text = " ${detail.price?.let { "$it VND" } ?: "Chưa có giá"}",
                    fontSize = 16.sp)
                Spacer(modifier = Modifier.height(8.dp))
//địa chỉ
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.location),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp, 20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        color = Color(0xfffeb051),
                        fontSize = 16.sp,
                        text = " ${detail.address ?: "Chưa có địa chỉ"}"
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
// số điện thoại
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.phone),
                        contentDescription = null,
                        modifier = Modifier.size(25.dp, 25.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = " ${detail.phoneNumber ?: "Chưa có số điện thoại"}",
                        fontSize = 16.sp
                    )

                }
                Spacer(modifier = Modifier.height(8.dp))

                // Nội dung bài đăng
                Text(
                    text = "Nội dung: " + "${detail.content}",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                // Tiện ích
                detail.amenities?.let { amenities ->
                    val amenitiesString = amenities.toString() // Chuyển sang chuỗi nếu cần
                    val selectedAmenities = amenitiesString
                        .removeSurrounding("[", "]") // Loại bỏ dấu []
                        .split(",") // Tách thành danh sách
                        .map { it.trim() } // Loại bỏ khoảng trắng thừa
                    AmenitiesDisplay(it = selectedAmenities)
                }

                Spacer(modifier = Modifier.height(8.dp))


                // Dịch vụ

                detail.services?.let { services ->
                    val servicesString = services.toString() // Chuyển sang chuỗi nếu cần
                    val selectedServices = servicesString
                        .removeSurrounding("[", "]") // Loại bỏ dấu []
                        .split(",") // Tách thành danh sách
                        .map { it.trim() } // Loại bỏ khoảng trắng thừa
                    ServicesDisplay(it = selectedServices)
                }
                Spacer(modifier = Modifier.height(8.dp))


//            // Loại bài đăng
//            Text(text = "Loại bài đăng: ${detail.post_type ?: "Chưa có loại bài đăng"}")
//            Spacer(modifier = Modifier.height(8.dp))
//
//            // Trạng thái bài đăng
//            Text(text = "Trạng thái: ${if (detail.status == 1) "Phòng đã cho thuê" else "Phòng trống"}")
//            Spacer(modifier = Modifier.height(8.dp))
//
//            // Thời gian tạo và cập nhật bài đăng
//            Text(text = "Thời gian tạo: ${detail.created_at ?: "Chưa có thời gian tạo"}")
//            Text(text = "Thời gian cập nhật: ${detail.updated_at ?: "Chưa có thời gian cập nhật"}")

            }
        }
            Box(
                modifier = Modifier
                      .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(screenHeight.dp/9f)
                    .background(color = Color(0xfff7f7f7))
            ) {
                Button(
                    onClick = {
                        navController.navigate("update_post_screen/${postId}")
                        {
                            popUpTo("post_detail/$postId") { inclusive = true }
                        }
                    }, modifier = Modifier.height(50.dp).fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp), colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xff5dadff)
                    )
                ) {
                    Text(
                        text = "Sửa bài đăng",
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.cairo_regular)),
                        color = Color(0xffffffff)
                    )
                }}}
    } ?: run {
        Text("Đang tải chi tiết bài đăng...")
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PostImageSlider(images: List<String>, videos: List<String>) {
    val pagerState = rememberPagerState()
    var showDialog by remember { mutableStateOf(false) }
    var currentMediaIndex by remember { mutableStateOf(0) }

    // Lấy tất cả ảnh và video và ghép vào một list
    val mediaList = mutableListOf<String>().apply {
        addAll(images)
        addAll(videos)
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        HorizontalPager(
            count = mediaList.size,
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { pageIndex ->
            val media = mediaList[pageIndex]
            Box(
                modifier = Modifier
                    .clickable {
                        currentMediaIndex = pageIndex
                        showDialog = true
                    }
                    .fillMaxWidth()
                    .heightIn(min = 200.dp, max = 300.dp) // Giới hạn chiều cao của ảnh
                    .align(Alignment.CenterHorizontally) // Căn giữa ảnh/video
            ) {
                if (media.endsWith(".mp4")) {
                    // Hiển thị video nếu file có định dạng .mp4
                    VideoPlayer(videoUrl = "http://192.168.2.106:3000/$media")
                } else {
                    // Hiển thị ảnh nếu không phải video
                    Image(
                        painter = rememberImagePainter("http://192.168.2.106:3000/$media"),
                        contentDescription = "Post Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 250.dp, max = 400.dp)
                            .align(Alignment.Center)
                    )
                }
            }
        }

        // Thêm dấu chấm chỉ số trang (Pager Indicator)
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp),
            activeColor = Color.Black,
            inactiveColor = Color.Gray
        )
    }

    // Mở dialog phóng to ảnh/video
    if (showDialog) {
        PostMediaDialog(
            mediaList = mediaList,
            currentIndex = currentMediaIndex,
            onDismiss = { showDialog = false }
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PostMediaDialog(mediaList: List<String>, currentIndex: Int, onDismiss: () -> Unit) {
    val pagerState = rememberPagerState(initialPage = currentIndex)

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                HorizontalPager(
                    count = mediaList.size,
                    state = pagerState,
                    modifier = Modifier.fillMaxWidth()
                ) { pageIndex ->
                    val media = mediaList[pageIndex]
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (media.endsWith(".mp4")) {
                            VideoPlayer(videoUrl = "http://192.168.2.106:3000/$media")
                        } else {
                            Image(
                                painter = rememberImagePainter("http://192.168.2.106:3000/$media"),
                                contentDescription = "Post Image",
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun VideoPlayer(videoUrl: String) {
    // Quản lý ExoPlayer
    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(videoUrl))
            prepare()
            playWhenReady = false // Chỉ phát khi người dùng bấm
        }
    }

    // Đảm bảo ExoPlayer được giải phóng khi Composable bị hủy
    DisposableEffect(key1 = exoPlayer) {
        onDispose {
            exoPlayer.release() // Giải phóng tài nguyên ExoPlayer
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black) // Màu nền để đảm bảo không bị trống
    ) {
        AndroidView(
            factory = { context ->
                PlayerView(context).apply {
                    player = exoPlayer
                    useController = true // Hiển thị thanh điều khiển
                    layoutParams = FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        Gravity.CENTER // Căn giữa PlayerView trong FrameLayout
                    )
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}
@Composable
fun StaticamenitiesOptions(
    amenities: List<String>, // Danh sách tiện ích từ dữ liệu
    allOptions: List<String> = listOf(
        "Vệ sinh khép kín",
        "Gác xép",
        "Ra vào vân tay",
        "Ban công",
        "Nuôi pet",
        "Không chung chủ"
    )
) {
    FlowRow(
        modifier = Modifier.padding(0.dp),
        mainAxisSpacing = 10.dp, // Khoảng cách giữa các phần tử trên cùng một hàng
        crossAxisSpacing = 10.dp // Khoảng cách giữa các hàng
    ) {
        allOptions.forEach { option ->
            StaticamenitiesOption(
                text = option,
                isSelected = amenities.contains(option) // Hiển thị dấu tích nếu có trong danh sách tiện ích
            )
        }
    }
}

@Composable
fun StaticamenitiesOption(
    text: String,
    isSelected: Boolean
) {
    Box(
        modifier = Modifier
            .shadow(3.dp, shape = RoundedCornerShape(9.dp))
            .border(
                width = 1.dp,
                color = if (isSelected) Color(0xFF44acfe) else Color(0xFFeeeeee),
                shape = RoundedCornerShape(9.dp)
            )
            .background(color = Color.White, shape = RoundedCornerShape(9.dp))
            .padding(0.dp)
    ) {
        Text(
            text = text,
            color = if (isSelected) Color(0xFF44acfe) else Color(0xFF000000),
            fontSize = 13.sp,
            modifier = Modifier
                .background(color = if (isSelected) Color(0xFFffffff) else Color(0xFFeeeeee))
                .padding(14.dp)
                .align(Alignment.Center)
        )

        // Hiển thị dấu tích nếu được chọn
        if (isSelected) {
            Box(
                modifier = Modifier
                    .size(23.dp)
                    .align(Alignment.TopStart)
                    .background(
                        color = Color(0xFF44acfe),
                        shape = TriangleShape()
                    ),
                contentAlignment = Alignment.TopStart
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.tick),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(10.dp)
                        .offset(x = 3.dp, y = 2.dp)
                )
            }
        }
    }
}

@Composable
fun AmenitiesDisplay(it: List<String>?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        ComfortableLabel()
        Spacer(modifier = Modifier.height(8.dp))
        if (it != null && it.isNotEmpty()) {
            StaticamenitiesOptions(amenities = it)
        } else {
            Text(
                text = "Không có tiện ích nào.",
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
    }
}
////
@Composable
fun StaticServicesOptions(
    services: List<String>, // Danh sách tiện ích từ dữ liệu
    allservicesOptions: List<String> = listOf(
        "Điều hoà",
        "Kệ bếp",
        "Tủ lạnh",
        "Bình nóng lạnh",
        "Máy giặt",
        "Bàn ghế"
    )
) {
    FlowRow(
        modifier = Modifier.padding(0.dp),
        mainAxisSpacing = 10.dp, // Khoảng cách giữa các phần tử trên cùng một hàng
        crossAxisSpacing = 10.dp // Khoảng cách giữa các hàng
    ) {
        allservicesOptions.forEach { option ->
            StaticServicesOption(
                text = option,
                isSelected = services.contains(option) // Hiển thị dấu tích nếu có trong danh sách tiện ích
            )
        }
    }
}

@Composable
fun StaticServicesOption(
    text: String,
    isSelected: Boolean
) {
    Box(
        modifier = Modifier
            .shadow(3.dp, shape = RoundedCornerShape(9.dp))
            .border(
                width = 1.dp,
                color = if (isSelected) Color(0xFF44acfe) else Color(0xFFeeeeee),
                shape = RoundedCornerShape(9.dp)
            )
            .background(color = Color.White, shape = RoundedCornerShape(9.dp))
            .padding(0.dp)
    ) {
        Text(
            text = text,
            color = if (isSelected) Color(0xFF44acfe) else Color(0xFF000000),
            fontSize = 13.sp,
            modifier = Modifier
                .background(color = if (isSelected) Color(0xFFffffff) else Color(0xFFeeeeee))
                .padding(14.dp)
                .align(Alignment.Center)
        )

        // Hiển thị dấu tích nếu được chọn
        if (isSelected) {
            Box(
                modifier = Modifier
                    .size(23.dp)
                    .align(Alignment.TopStart)
                    .background(
                        color = Color(0xFF44acfe),
                        shape = TriangleShape()
                    ),
                contentAlignment = Alignment.TopStart
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.tick),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(10.dp)
                        .offset(x = 3.dp, y = 2.dp)
                )
            }
        }
    }
}

@Composable
fun ServicesDisplay(it: List<String>?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        ServiceLabel()
        Spacer(modifier = Modifier.height(8.dp))
        if (it != null && it.isNotEmpty()) {
            StaticServicesOptions(services = it)
        } else {
            Text(
                text = "Không có tiện ích nào.",
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
    }
}
////
@Composable
fun StaticRoomTypeOptions(
    services: List<String>, // Danh sách tiện ích từ dữ liệu
    allservicesOptions: List<String> = listOf(
        "Phòng trọ",
        "Nguyên căn",
        "Chung cư"

    )
) {
    FlowRow(
        modifier = Modifier.padding(0.dp),
        mainAxisSpacing = 10.dp, // Khoảng cách giữa các phần tử trên cùng một hàng
        crossAxisSpacing = 10.dp // Khoảng cách giữa các hàng
    ) {
        allservicesOptions.forEach { option ->
            StaticRoomTypeOption(
                text = option,
                isSelected = services.contains(option) // Hiển thị dấu tích nếu có trong danh sách tiện ích
            )
        }
    }
}

@Composable
fun StaticRoomTypeOption(
    text: String,
    isSelected: Boolean
) {
    Box(
        modifier = Modifier
            .shadow(3.dp, shape = RoundedCornerShape(9.dp))
            .border(
                width = 1.dp,
                color = if (isSelected) Color(0xFF44acfe) else Color(0xFFeeeeee),
                shape = RoundedCornerShape(9.dp)
            )
            .background(color = Color.White, shape = RoundedCornerShape(9.dp))
            .padding(0.dp)
    ) {
        Text(
            text = text,
            color = if (isSelected) Color(0xFF44acfe) else Color(0xFF000000),
            fontSize = 13.sp,
            modifier = Modifier
                .background(color = if (isSelected) Color(0xFFffffff) else Color(0xFFeeeeee))
                .padding(14.dp)
                .align(Alignment.Center)
        )

        // Hiển thị dấu tích nếu được chọn
        if (isSelected) {
            Box(
                modifier = Modifier
                    .size(23.dp)
                    .align(Alignment.TopStart)
                    .background(
                        color = Color(0xFF44acfe),
                        shape = TriangleShape()
                    ),
                contentAlignment = Alignment.TopStart
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.tick),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(10.dp)
                        .offset(x = 3.dp, y = 2.dp)
                )
            }
        }
    }
}

@Composable
fun RoomTypeDisplay(it: List<String>?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        RoomTypeLabel()
        Spacer(modifier = Modifier.height(8.dp))
        if (it != null && it.isNotEmpty()) {
            StaticRoomTypeOptions(services = it)
        } else {
            Text(
                text = "Không có tiện ích nào.",
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
    }
}