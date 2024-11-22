package com.rentify.user.app.view.staffScreens.postingList.PostingListComponents

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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.window.Dialog
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.rentify.user.app.R
import com.rentify.user.app.network.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PostDetailScreen(navController: NavController, postId: String, viewModel: PostViewModel = PostViewModel()) {
    val postDetail by viewModel.postDetail.observeAsState()
    val scrollState = rememberScrollState()
    // Gọi API lấy chi tiết bài đăng
    LaunchedEffect(postId) {
        viewModel.getPostDetail(postId)
    }

    postDetail?.let { detail ->
        Column(modifier = Modifier.padding(16.dp)      .verticalScroll(scrollState)) {
            // Hiển thị ảnh/video đầu tiên
            PostImageSlider(
                images = detail.photos ?: emptyList(),
                videos = detail.videos ?: emptyList()
            )

            Spacer(modifier = Modifier.height(16.dp))
            Row(   verticalAlignment = Alignment.CenterVertically, ) {
                // Loại phòng
                Text(color = Color(0xfffeb051), fontSize = 16.sp, text = " ${detail.room_type ?: "Chưa có loại phòng"}")
                Spacer(modifier = Modifier.width(10.dp))
                Image(
                    painter = painterResource(id = R.drawable.namnu),
                    contentDescription = null,
                    modifier = Modifier.size(15.dp, 15.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            // Tiêu đề bài đăng
            Text(text = detail.title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(color = Color(0xFFF55151), fontWeight = FontWeight.W700, text = " ${detail.price?.let { "$it VND" } ?: "Chưa có giá"}", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(4.dp))


            // Thông tin bài đăng
            Row(   verticalAlignment = Alignment.CenterVertically, ) {
                // Loại phòng
                Image(
                    painter = painterResource(id = R.drawable.location),
                    contentDescription = null,
                    modifier = Modifier.size(15.dp, 15.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(color = Color(0xfffeb051), fontSize = 16.sp, text = " ${detail.address ?: "Chưa có địa chỉ"}")


            }
            Row(   verticalAlignment = Alignment.CenterVertically, ) {
                // Loại phòng

                Image(
                    painter = painterResource(id = R.drawable.phone),
                    contentDescription = null,
                    modifier = Modifier.size(15.dp, 15.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = " ${detail.phoneNumber ?: "Chưa có số điện thoại"}", fontSize = 16.sp)

            }
            Spacer(modifier = Modifier.height(4.dp))

            // Nội dung bài đăng
            Text(text = detail.content, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))

            // Tiện ích

            detail.amenities?.let {
                Text("Tiện ích: ${it.joinToString(", ")}")
            }
            Spacer(modifier = Modifier.height(8.dp))


            // Dịch vụ
            detail.services?.let {
                Text("Dịch vụ: ${it.joinToString(", ")}")
            }
            Spacer(modifier = Modifier.height(8.dp))



            // Loại bài đăng
            Text(text = "Loại bài đăng: ${detail.post_type ?: "Chưa có loại bài đăng"}")
            Spacer(modifier = Modifier.height(8.dp))

            // Trạng thái bài đăng
            Text(text = "Trạng thái: ${if (detail.status == 1) "Đang hoạt động" else "Không hoạt động"}")
            Spacer(modifier = Modifier.height(8.dp))

            // Thời gian tạo và cập nhật bài đăng
            Text(text = "Thời gian tạo: ${detail.created_at ?: "Chưa có thời gian tạo"}")
            Text(text = "Thời gian cập nhật: ${detail.updated_at ?: "Chưa có thời gian cập nhật"}")
            Button(
                onClick = {
                    navController.navigate("update_post_screen/${postId}")
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
            }
        }
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
                    .heightIn(min = 250.dp, max = 400.dp) // Giới hạn chiều cao của ảnh
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
    Box(
        modifier = Modifier
            .fillMaxWidth()
           // .aspectRatio(16f / 9f) // Đảm bảo tỷ lệ 16:9
            .background(Color.Black) // Màu nền để đảm bảo không bị trống
    ) {
        AndroidView(
            factory = { context ->
                PlayerView(context).apply {
                    player = ExoPlayer.Builder(context).build().apply {
                        setMediaItem(MediaItem.fromUri(videoUrl))
                        prepare()
                        playWhenReady = false // Chỉ phát khi người dùng bấm
                    }

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
