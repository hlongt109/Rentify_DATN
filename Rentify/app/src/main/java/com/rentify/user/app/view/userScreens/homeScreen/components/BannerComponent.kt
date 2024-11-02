package com.rentify.user.app.view.userScreens.homeScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.rentify.user.app.R
import kotlinx.coroutines.delay

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BannerComponent() {
    LayoutBanner()
}

@Composable
fun LayoutBanner() {
    val images = listOf(
        R.drawable.banner1,
        R.drawable.banner3,
        R.drawable.banner4
    )

    var currentIndex by remember { mutableStateOf(0) }
    var previousIndex by remember { mutableStateOf(currentIndex) }
    var offset by remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            previousIndex = currentIndex
            currentIndex = (currentIndex + 1) % images.size
            offset = 100f
            delay(300)
            offset = 0f
            delay(300)
        }
    }

    Box(modifier = Modifier.fillMaxWidth().height(200.dp)) {
        Image(
            painter = rememberImagePainter(data = images[previousIndex]),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .graphicsLayer(translationX = -offset)
        )
        Image(
            painter = rememberImagePainter(data = images[currentIndex]),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .graphicsLayer(translationX = offset)
        )
    }
}
// thêm hiệu ứng chuyển ảnh _vanphuc
//merge