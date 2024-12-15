package com.rentify.user.app.view.userScreens.SearchRoomateScreen.SearchRoomateComponent

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
//import com.google.android.exoplayer2.ExoPlayer
//import com.google.android.exoplayer2.MediaItem
//import com.google.android.exoplayer2.ui.PlayerView
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RotateRight
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.rentify.user.app.R


@OptIn(ExperimentalPagerApi::class)
@Composable
fun SeachRoomateDetailScreen(navController: NavController, postId: String, viewModel: PostViewModel = PostViewModel()) {
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
        Log.d("detail", "RequestBody check:${detail} ")
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
                        .background(color = Color(0xfff7f7f7)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = { navController.navigate("Search_roommate")}) {
                        Image(
                            painter = painterResource(id = R.drawable.back),
                            contentDescription = null,
                            modifier = Modifier.size(30.dp, 30.dp)
                        )
                    }
                    Text(
                        text = "Chi tiết bài đăng",
                        color = Color.Black,
                        fontWeight = FontWeight(700),
                        fontSize = 17.sp,
                    )
                    IconButton(onClick = { /*TODO*/ }) {
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .height(screenHeight.dp / 0.6f)
                        .verticalScroll(scrollState)
                ) {

                    // Hiển thị ảnh/video đầu tiên
                    PostImageSection(images = detail.photos ?: emptyList())
                    Spacer(modifier = Modifier.height(16.dp))
// loại phòng
                    Text(
                        text = "Loại phòng: ${detail.room?.room_type}",
                        fontSize = 14.sp,
                        color=Color(0xfffeb501),
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    // tên phòng
                    Text(
                        text = "Phòng: ${detail.room?.room_name}",
                        fontSize = 16.sp,
                        color=Color.Black,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    // địa chỉ
                    Text(
                        text = "Địa chỉ: ${detail.building?.address}",
                        fontSize = 16.sp,
                        color=Color.Black,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    // giá
                    Text(
                        text = "Giá: ${detail.room?.price?.let { "$it VND" }}",
                        fontSize = 15.sp,
                        color=Color(0xffde5135),
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    // Tiêu đề bài đăng
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                    ) {
                        Row {
                            Text(
                                text = "Tiêu đề",
                                color = Color(0xff7f7f7f),
                                fontSize = 14.sp,
                            )

                            Text(

                                text = " *",
                                color = Color(0xffff1a1a),
                                fontSize = 16.sp,

                                )
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = detail.title,
                            fontSize = 15.sp,
                            color = Color(0xff898888),
                            modifier = Modifier.padding(horizontal = 10.dp),

                            )
                        Spacer(
                            modifier = Modifier
                                .padding(vertical = 5.dp)
                                .fillMaxWidth() // Gạch dưới rộng bằng chiều rộng nội dung
                                .height(1.dp)  // Độ dày của gạch
                                .background(Color(0xff898888)) // Màu của gạch dưới
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
/// nôi dung
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                    ) {
                        Row {
                            Text(
                                text = "Nội dung",
                                color = Color(0xff7f7f7f),
                                fontSize = 14.sp,
                            )
                            Text(

                                text = " *",
                                color = Color(0xffff1a1a),
                                fontSize = 16.sp,

                                )
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = "${detail.content}",
                            fontSize = 15.sp,
                            color = Color(0xff898888),
                            modifier = Modifier.padding(horizontal = 10.dp),

                            )
                        Spacer(
                            modifier = Modifier
                                .padding(vertical = 5.dp)
                                .fillMaxWidth() // Gạch dưới rộng bằng chiều rộng nội dung
                                .height(1.dp)  // Độ dày của gạch
                                .background(Color(0xff898888)) // Màu của gạch dưới
                        )
                    }
                    PostVideoSection(videos = detail.videos ?: emptyList())
                    Spacer(modifier = Modifier.height(10.dp))
// tên tòa và số bài dăng
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(start = 15.dp),

                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.building_2),
                            contentDescription = "",
                            modifier = Modifier.size(50.dp)
                        )

                        Column {
                            Text(
                                text = "Tòa nhà: ${detail.building?.nameBuilding}",
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(start = 5.dp)
                                    .padding(top = 10.dp),
                                fontSize = 16.sp
                            )
                            Text(
                                text = "${detail.building?.post_count} bài đăng",
                                modifier = Modifier
                                    .padding(start = 5.dp)
                                    .padding(bottom = 10.dp),
                                fontSize = 10.sp,
                                color = Color(0xFF44ACFE)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(start = 15.dp),
                                verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.error),
                                contentDescription = null,
                                modifier = Modifier.size(25.dp, 25.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Báo cáo",
                                fontSize = 16.sp
                            )

                        }
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.mess),
                                contentDescription = null,
                                modifier = Modifier.size(25.dp, 25.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Nhắn tin",
                                fontSize = 16.sp
                            )

                        }
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
                                text = "Điện thoai",
                                fontSize = 16.sp
                            )

                        }
                    }
                    // Hiển thị thông tin người đăng
//                    Text(
//                        text = "Người đăng: ${detail.user?.name}",
//                        fontSize = 16.sp,
//                        modifier = Modifier.padding(horizontal = 10.dp)
//                    )
//                    Text(
//                        text = "Email: ${detail.user?.email}",
//                        fontSize = 14.sp,
//                        modifier = Modifier.padding(horizontal = 10.dp)
//                    )
//                    Spacer(modifier = Modifier.height(16.dp))
                    //                   Spacer(modifier = Modifier.height(16.dp))
//                    // Hiển thị ngày tạo và cập nhật
//                    Text(
//                        text = "Ngày tạo: ${detail.created_at}",
//                        fontSize = 14.sp,
//                        modifier = Modifier.padding(horizontal = 10.dp)
//                    )
//                    Text(
//                        text = "Ngày cập nhật: ${detail.updated_at}",
//                        fontSize = 14.sp,
//                        modifier = Modifier.padding(horizontal = 10.dp)
//                    )

                }
            }

//            Box(
//                modifier = Modifier
//                    .align(Alignment.BottomCenter)
//                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp)
//                    .height(screenHeight.dp / 9f)
//                    .background(color = Color(0xfff7f7f7))
//            ) {
//                Button(
//                    onClick = {
//                        navController.navigate("update_post_screen/${postId}") {
//                            popUpTo("post_detail/$postId") { inclusive = true }
//                        }
//                    },
//                    modifier = Modifier.height(50.dp).fillMaxWidth(),
//                    shape = RoundedCornerShape(10.dp),
//                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xff5dadff))
//                ) {
//                    Text(
//                        text = "Sửa bài đăng",
//                        fontSize = 16.sp,
//                        fontFamily = FontFamily(Font(R.font.cairo_regular)),
//                        color = Color(0xffffffff)
//                    )
//                }
//            }
        }
    } ?: run {
        Text("Đang tải chi tiết bài đăng...")
    }
}
@Preview(showBackground = true)
@Composable
fun PostDetailScreenPreview() {
    val mockNavController = rememberNavController() // Tạo NavController giả lập cho preview
    SeachRoomateDetailScreen(
        navController = mockNavController,
        postId = "mockPostId", // Truyền postId giả lập
        viewModel = PostViewModel()
    )
}
@OptIn(ExperimentalPagerApi::class)

@Composable
fun PostImageSection(images: List<String>) {
    if (images.isNotEmpty()) {
        var showDialog by remember { mutableStateOf(false) }
        var selectedIndex by remember { mutableStateOf(0) }

        val pagerState = rememberPagerState() // Quản lý trạng thái pager
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Hình ảnh",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
            )
            HorizontalPager(
                count = images.size,
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) { pageIndex ->
                val image = images[pageIndex]
                Image(
                        painter = rememberImagePainter("http://10.0.2.2:3000/$image"),
                    contentDescription = "Post Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.LightGray)
                        .clickable {
                            selectedIndex = pageIndex
                            showDialog = true // Mở dialog
                        },
                    contentScale = ContentScale.Crop
                )
            }
            HorizontalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(8.dp),
                activeColor = Color.Black,
                inactiveColor = Color.Gray
            )
        }

        // Hiển thị dialog nếu người dùng nhấn vào ảnh
        if (showDialog) {
            PostMediaDialog(
                mediaList = images,
                currentIndex = selectedIndex,
                onDismiss = { showDialog = false } // Đóng dialog
            )
        }
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun PostVideoSection(videos: List<String>) {
    if (videos.isNotEmpty()) {
        var showDialog by remember { mutableStateOf(false) }
        var selectedIndex by remember { mutableStateOf(0) }

        val pagerState = rememberPagerState() // Quản lý trạng thái pager
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Video",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
            )
            HorizontalPager(
                count = videos.size,
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .height(200.dp)
            ) { pageIndex ->
                val videoUrl = videos[pageIndex]
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.Black)
                        .clickable {
                            selectedIndex = pageIndex
                            showDialog = true // Mở dialog
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Play Video",
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }

            HorizontalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(8.dp),
                activeColor = Color.Black,
                inactiveColor = Color.Gray
            )
        }

        // Hiển thị dialog nếu người dùng nhấn vào video
        if (showDialog) {
            PostMediaDialog(
                mediaList = videos,
                currentIndex = selectedIndex,
                onDismiss = { showDialog = false } // Đóng dialog
            )
        }
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun PostMediaDialog(
    mediaList: List<String>,
    currentIndex: Int,
    onDismiss: () -> Unit
) {
    val pagerState = rememberPagerState(initialPage = currentIndex)
    var isLandscape by remember { mutableStateOf(false) } // Quản lý trạng thái xoay ảnh
    val currentPlayingIndex = remember { mutableStateOf(-1) } // Theo dõi video đang phát

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier.fillMaxSize(), // Hiển thị toàn màn hình
            color = Color.Black // Nền đen để tạo cảm giác fullscreen
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // Nút đóng (X) ở góc trên bên phải
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(onClick = onDismiss) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                tint = Color.White
                            )
                        }
                    }

                    // Nội dung chính: ảnh/video với trạng thái xoay
                    HorizontalPager(
                        count = mediaList.size,
                        state = pagerState,
                        modifier = Modifier.weight(1f) // Chiếm phần lớn chiều cao màn hình
                    ) { pageIndex ->
                        val media = mediaList[pageIndex]
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            if (media.endsWith(".mp4")) {
                                // Video
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .rotate(if (isLandscape) 90f else 0f)
                                ) {
                                    VideoPlayer(
                                        videoUrl = "http://10.0.2.2:3000/$media",
                                        isPlaying = currentPlayingIndex.value == pageIndex
                                    )
                                }
                            } else {
                                // Ảnh
                                Image(
                                    painter = rememberImagePainter("http://10.0.2.2:3000/$media"),
                                    contentDescription = "Post Image",
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .rotate(if (isLandscape) 90f else 0f), // Xoay ảnh
                                    contentScale = ContentScale.Fit
                                )
                            }
                        }
                    }

                    // Cập nhật video đang phát khi trang thay đổi
                    LaunchedEffect(pagerState.currentPage) {
                        currentPlayingIndex.value = pagerState.currentPage
                    }

                    // Nút điều khiển xoay
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Spacer(modifier = Modifier.weight(1f)) // Đẩy nút xoay về giữa
                        IconButton(
                            onClick = { isLandscape = !isLandscape } // Đổi trạng thái xoay
                        ) {
                            Icon(
                                imageVector = Icons.Default.RotateRight,
                                contentDescription = "Rotate",
                                tint = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
fun VideoPlayer(
    videoUrl: String,
    isPlaying: Boolean
) {
    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(videoUrl))
            prepare()
        }
    }

    // Cập nhật trạng thái phát khi `isPlaying` thay đổi
    LaunchedEffect(isPlaying) {
        exoPlayer.playWhenReady = isPlaying
        if (!isPlaying) {
            exoPlayer.pause()
        }
    }

    // Giải phóng ExoPlayer khi không còn được sử dụng
    DisposableEffect(key1 = exoPlayer) {
        onDispose {
            exoPlayer.release()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
    ) {
        AndroidView(
            factory = { context ->
                PlayerView(context).apply {
                    player = exoPlayer
                    useController = true
                    layoutParams = FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        Gravity.CENTER
                    )
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}
