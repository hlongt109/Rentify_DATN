package com.rentify.user.app.view.userScreens.homeScreen.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.rentify.user.app.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BannerComponent() {
    LayoutBanner()
}

@Composable
fun LayoutBanner() {
    val images = listOf(
        R.drawable.banner1,
        R.drawable.banner2,
        R.drawable.banner3,
        R.drawable.banner4
    )

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
                nextIndex = (nextIndex + 1) % images.size
            }
            delay(6000)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .shadow(8.dp)
            .background(Brush.horizontalGradient(listOf(Color.Gray, Color.LightGray)))
    ) {
        // Ảnh hiện tại (dịch chuyển và fade-out)
        Image(
            painter = rememberImagePainter(data = images[currentIndex]),
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
            painter = rememberImagePainter(data = images[nextIndex]),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(
                    translationX = nextOffsetX.value,
                    alpha = nextAlpha.value
                ),
            contentScale = ContentScale.Crop
        )
    }
}


@Composable
fun LayoutBanner2() {
    val images = listOf(
        R.drawable.banner1,
        R.drawable.banner2,
        R.drawable.banner3,
        R.drawable.banner4
    )

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
                nextIndex = (nextIndex + 1) % images.size
            }
            delay(6000)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .shadow(8.dp)
            .background(Brush.horizontalGradient(listOf(Color.Gray, Color.LightGray)))
    ) {
        // Ảnh hiện tại (dịch chuyển và fade-out)
        Image(
            painter = rememberImagePainter(data = images[currentIndex]),
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
            painter = rememberImagePainter(data = images[nextIndex]),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(
                    translationX = nextOffsetX.value,
                    alpha = nextAlpha.value
                ),
            contentScale = ContentScale.Crop
        )
    }
}
