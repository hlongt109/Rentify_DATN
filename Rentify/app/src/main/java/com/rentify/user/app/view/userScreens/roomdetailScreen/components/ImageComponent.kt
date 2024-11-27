package com.rentify.user.app.view.userScreens.roomdetailScreen.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.rentify.user.app.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ImageComponent(
    imageUrls: List<String>,
    videoUrls: List<String>? = null
) {
    var showDialog by remember { mutableStateOf(false) }
    if (imageUrls.isEmpty()) {
        // Hiển thị hình ảnh mặc định nếu không có ảnh/video nào
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(horizontal = 10.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color.Gray),
            contentAlignment = Alignment.Center
        ) {
            Text("No Images or Videos Available", color = Color.White)
        }
        return
    }

    if (imageUrls.size == 1) {
        // Hiển thị duy nhất một ảnh nếu chỉ có một ảnh
        AsyncImage(
            model = imageUrls[0],
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(horizontal = 10.dp)
                .clip(RoundedCornerShape(20.dp))
                .clickable { showDialog = true },
            contentScale = ContentScale.Crop,
            error = painterResource(id = R.drawable.g),
            fallback = painterResource(id = R.drawable.g)
        )
        return
    }


    var currentIndex by remember { mutableStateOf(0) }
    var nextIndex by remember { mutableStateOf(1) }
    val currentOffsetX = remember { Animatable(0f) }
    val currentAlpha = remember { Animatable(1f) } // Alpha của ảnh hiện tại (fade-out)
    val nextAlpha = remember { Animatable(0f) } // Alpha của ảnh tiếp theo (fade-in)
    val coroutineScope = rememberCoroutineScope()
    val configuration = LocalConfiguration.current
    val screenWidthPx = with(LocalDensity.current) { configuration.screenWidthDp.dp.toPx() }
    val nextOffsetX = remember { Animatable(screenWidthPx) } // Ảnh tiếp theo bắt đầu ngoài màn hình phải

    LaunchedEffect(Unit) {
        while (true) {
            coroutineScope.launch {
                // Chạy tất cả các animation song song
                launch {
                    currentOffsetX.animateTo(
                        targetValue = -screenWidthPx,
                        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing)
                    )
                }
                launch {
                    nextOffsetX.animateTo(
                        targetValue = 0f,
                        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing)
                    )
                }
                launch {
                    currentAlpha.animateTo(
                        targetValue = 0f, // Fade-out ảnh hiện tại
                        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing)
                    )
                }
                launch {
                    nextAlpha.animateTo(
                        targetValue = 1f, // Fade-in ảnh tiếp theo
                        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing)
                    )
                }

                // Chờ animation hoàn tất
                currentOffsetX.snapTo(0f)
                nextOffsetX.snapTo(screenWidthPx)
                currentAlpha.snapTo(1f) // Reset alpha cho lần lặp tiếp theo
                nextAlpha.snapTo(0f)

                // Cập nhật index
                currentIndex = nextIndex
                nextIndex = (nextIndex + 1) % imageUrls.size
            }
            delay(6000)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(horizontal = 10.dp)
            .clip(RoundedCornerShape(20.dp))
            .shadow(8.dp)
            .background(Brush.horizontalGradient(listOf(Color.Gray, Color.LightGray)))
            .clickable { showDialog = true } // Thêm sự kiện click vào ảnh
    ) {
        // Ảnh hiện tại (dịch chuyển và fade-out)
        Image(
            painter = rememberImagePainter(data = imageUrls[currentIndex]),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(
                    translationX = currentOffsetX.value,
                    alpha = currentAlpha.value
                ),
            contentScale = ContentScale.Crop
        )

        // Ảnh tiếp theo (dịch chuyển và fade-in)
        Image(
            painter = rememberImagePainter(data = imageUrls[nextIndex]),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(
                    translationX = nextOffsetX.value,
                    alpha = nextAlpha.value
                ),
            contentScale = ContentScale.Crop
        )

        // Chỉ mục ở dưới cùng của Box
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter) // Căn chỉnh chỉ mục ở dưới giữa Box
                .padding(10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp) // Khoảng cách đều giữa các icon
        ) {
            imageUrls.forEachIndexed { index, _ ->
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(
                            if (index == currentIndex) Color.White
                            else Color.Gray.copy(alpha = 0.6f)
                        )
                )
            }
        }
    }

    // Cập nhật phần gọi dialog trong ImageComponent
    if (showDialog) {
        ImageSliderDialog(
            imageUrls = imageUrls,
            videoUrls = videoUrls,  // Truyền videoUrls vào dialog
            initialIndex = 0,
            onDismiss = { showDialog = false }
        )
    }
}


// Dialog hiển thị ảnh lướt ngang
@OptIn(ExperimentalPagerApi::class)
@Composable
fun ImageSliderDialog(
    imageUrls: List<String>,
    videoUrls: List<String>? = null,
    initialIndex: Int,
    onDismiss: () -> Unit
) {
    var currentIndex by remember { mutableStateOf(initialIndex) }

    // Tạo danh sách kết hợp ảnh và video
    val mediaUrls = mutableListOf<String>().apply {
        addAll(imageUrls)
        videoUrls?.let { addAll(it) }
    }

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .background(Color.Black.copy(alpha = 0.7f), RoundedCornerShape(20.dp))
        ) {
            val pagerState = rememberPagerState(initialPage = currentIndex)

            HorizontalPager(
                count = mediaUrls.size,
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                val mediaUrl = mediaUrls[page]
                if (mediaUrl.endsWith(".mp4")) {
                    VideoPlayer(mediaUrl)
                } else {
                    Image(
                        painter = rememberImagePainter(data = mediaUrl),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )
                }
            }
        }
    }
}

@androidx.annotation.OptIn(UnstableApi::class)
@SuppressLint("Range")
@Composable
fun VideoPlayer(videoUrl: String) {
    val context = LocalContext.current
    // Khởi tạo ExoPlayer
    val player = remember {
        ExoPlayer.Builder(context).build().apply {
            repeatMode = Player.REPEAT_MODE_OFF // Không lặp lại video
            volume = 1.0f // Đặt âm lượng ở mức tối đa
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(C.USAGE_MEDIA)
                    .setContentType(C.AUDIO_CONTENT_TYPE_MOVIE)
                    .build(),
                true
            )
        }
    }

    LaunchedEffect(videoUrl) {
        try {
            val mediaItem = MediaItem.fromUri(videoUrl)
            player.setMediaItem(mediaItem)
            player.prepare()
            player.play()
        } catch (e: Exception) {
            Log.e("VideoPlayer", "Error preparing video: ${e.message}")
        }
    }

    // Giải phóng ExoPlayer khi Composable bị hủy
    DisposableEffect(Unit) {
        onDispose {
            player.release()
        }
    }

    // Hiển thị video
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            PlayerView(context).apply {
                this.player = player
                useController = true // Hiển thị thanh điều khiển video
            }
        }
    )
}