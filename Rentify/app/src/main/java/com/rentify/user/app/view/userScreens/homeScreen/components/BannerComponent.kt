package com.rentify.user.app.view.userScreens.homeScreen.components
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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
        (R.drawable.banner),
        (R.drawable.banner2),
        (R.drawable.banner3),
        (R.drawable.banner4),
    )

    var currentIndex by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)  // Chuyển ảnh sau mỗi 3 giây
            currentIndex = (currentIndex + 1) % images.size
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        Image(
            painter = rememberImagePainter(data = images[currentIndex]),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )
    }
}
