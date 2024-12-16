package com.rentify.user.app.view.userScreens.SearchRoomateScreen.SearchRoomateComponent

import android.util.Log
import android.view.Gravity
import android.widget.FrameLayout
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RotateRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.rentify.user.app.R
import com.rentify.user.app.model.Post
import com.rentify.user.app.model.PostResponse
import com.rentify.user.app.ui.theme.ColorBlack
import com.rentify.user.app.viewModel.UserViewmodel.PostUserViewModel

@Composable
fun PostListRoomateScreen(navController: NavController, postType: String) {
    val viewModel: PostUserViewModel = viewModel()
    val posts = viewModel.posts.value // Truy cập giá trị từ State

    // Gọi API khi màn hình được khởi chạy
    LaunchedEffect(postType) {
        viewModel.fetchPostsByType(postType)
        Log.d("Debug", "LaunchedEffect triggered with postType: $postType")
    }
    if (posts.isEmpty()) {
        Text(
            text = "Không có bài đăng nào",
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    } else {
        LazyColumn {
            items(posts) { post ->
                // Sử dụng PostResponse để truyền vào ItemPost
                Log.d("UI posts", posts.toString())
                ItemPost(post = post, navController)
            }

        }
    }
}


@Composable
fun ItemPost(post: PostResponse, navController: NavController) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val maxWidth = screenWidth * 0.7f // Giới hạn 80% chiều rộng màn hình
    var isExpanded by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .clip(RoundedCornerShape(10.dp))
            .padding(10.dp),
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .shadow(
                    elevation = 3.dp,
                    shape = RoundedCornerShape(10.dp),
                    spotColor = Color.Black
                )
                .align(Alignment.Center),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp), // Căn đầy chiều rộng và padding cho đẹp
                    horizontalArrangement = Arrangement.SpaceBetween, // Căn giữa đầu và cuối
                    verticalAlignment = Alignment.CenterVertically // Căn giữa theo chiều dọc
                ) {
                        Text(
                            text = "${post.user?.name}",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xffB95533),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    Row {
                        Image(
                            painter = painterResource(id = R.drawable.phone),
                            contentDescription = null,
                            modifier = Modifier.size(25.dp, 25.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Image(
                            painter = painterResource(id = R.drawable.mess),
                            contentDescription = null,
                            modifier = Modifier
                                .size(25.dp, 25.dp)
                                .clickable {
                                    navController.navigate("TINNHAN/${post.user?._id}/${post.user?.name}")
                                }
                        )
                    }

            }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Mô tả: ${post.content}",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xff7f7f7f),
                    maxLines = if (isExpanded) Int.MAX_VALUE else 3, // Thay đổi số dòng hiển thị
                    overflow = if (isExpanded) TextOverflow.Clip else TextOverflow.Ellipsis, // Hiển thị đầy đủ hoặc rút gọn
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp), // Căn đầy chiều rộng và padding cho đẹp
                    horizontalArrangement = Arrangement.SpaceBetween, // Căn giữa đầu và cuối
                    verticalAlignment = Alignment.CenterVertically // Căn giữa theo chiều dọc
                ) {
                    Text(
                        text = "Khu vực : ${post.address?.takeIf {it.isNotBlank() } ?: "Người đăng không ghi địa chỉ, bạn có thể liên hệ"}",
                                fontSize = 13.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xff7f7f7f),
                        maxLines = if (isExpanded) Int.MAX_VALUE else 2, // Thay đổi số dòng hiển thị
                        overflow = if (isExpanded) TextOverflow.Clip else TextOverflow.Ellipsis,
                        modifier =  Modifier.widthIn(max = maxWidth)
                    )
                    IconButton(
                        onClick = { isExpanded = !isExpanded }
                    ) {
                        Icon(
                            imageVector = if (isExpanded)
                                Icons.Default.KeyboardArrowUp
                            else
                                Icons.Default.KeyboardArrowDown,
                            contentDescription = if (isExpanded) "Collapse" else "Expand",
                            tint = Color.Black,
                            modifier = Modifier.size(25.dp)
                        )
                    }
                }

                AnimatedVisibility(
                    visible = isExpanded,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.White)
                            .padding(15.dp)
                            .height(270.dp),
                        verticalArrangement = Arrangement.Center,
                    ) {
                        PostMediaSection1(mediaList = (post.photos + (post.videos ?: emptyList())).filterIsInstance<String>())
                        Log.d("PostVideoSection", "Videos: ${post.videos ?: "Empty or Null"}")
                        Spacer(modifier = Modifier.height(25.dp))
                    }
                }
            }
        }
    }
}
@OptIn(ExperimentalPagerApi::class)
@Composable
fun PostMediaSection1(mediaList: List<String>) {
    if (mediaList.isNotEmpty()) {
        var showDialog by remember { mutableStateOf(false) }
        var selectedIndex by remember { mutableStateOf(0) }

        val pagerState = rememberPagerState() // Quản lý trạng thái pager
        Column(modifier = Modifier.fillMaxWidth()) {
            HorizontalPager(
                count = mediaList.size,
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) { pageIndex ->
                val media = mediaList[pageIndex]
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.Gray)
                        .clickable {
                            selectedIndex = pageIndex
                            showDialog = true // Mở dialog
                        },
                    contentAlignment = Alignment.Center
                ) {

                    if (media.endsWith(".mp4")) {
                            // Play button overlay
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Play Video",
                                tint = Color.White,
                                modifier = Modifier
                                    .size(40.dp)
                                    .align(Alignment.Center)
                            )

                    } else {
                        // Hiển thị ảnh
                        Log.d("PostMediaSection1", "PostMediaSection1: http://10.0.2.2:3000/${media}")
                        Image(
                            painter = rememberImagePainter("http://10.0.2.2:3000/${media}"),

                            contentDescription = "Post Image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .padding(8.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color.LightGray),
                            contentScale = ContentScale.Crop
                        )
                    }
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
        // Hiển thị dialog nếu người dùng nhấn vào media
        if (showDialog) {
            PostMediaDialog1(
                mediaList = mediaList,
                currentIndex = selectedIndex,
                onDismiss = { showDialog = false } // Đóng dialog
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PostMediaDialog1(
    mediaList: List<String>,
    currentIndex: Int,
    onDismiss: () -> Unit
) {
    val pagerState = rememberPagerState(initialPage = currentIndex)
    var isLandscape by remember { mutableStateOf(false) } // Quản lý trạng thái xoay ảnh/video
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
//                    HorizontalPager(
//                        count = mediaList.size,
//                        state = pagerState,
//                        modifier = Modifier.weight(1f) // Chiếm phần lớn chiều cao màn hình
//                    ) { pageIndex ->
//                        val media = mediaList[pageIndex]
//                        Box(
//                            modifier = Modifier.fillMaxSize(),
//                            contentAlignment = Alignment.Center
//                        ) {
//                            if (media.endsWith(".mp4")) {
//                                // Render video
//                                Box(
//                                    modifier = Modifier
//                                        .fillMaxSize()
//                                        .rotate(if (isLandscape) 90f else 0f)
//                                ) {
//                                    VideoPlayer1(
//                                        videoUrl = "http://10.0.2.2:3000/$media",
//                                        isPlaying = currentPlayingIndex.value == pageIndex
//                                    )
//                                }
//                            } else {
//                                // Render ảnh
//                                Image(
//                                    painter = rememberImagePainter("http://10.0.2.2:3000/$media"),
//                                    contentDescription = "Post Image",
//                                    modifier = Modifier
//                                        .fillMaxSize()
//                                        .rotate(if (isLandscape) 90f else 0f), // Xoay ảnh
//                                    contentScale = ContentScale.Fit
//                                )
//                            }
//                        }
//                    }

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
fun VideoPlayer1(
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


