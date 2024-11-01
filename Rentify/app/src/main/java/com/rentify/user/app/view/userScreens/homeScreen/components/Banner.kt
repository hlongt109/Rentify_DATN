package com.rentify.user.app.view.userScreens.homeScreen.components
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import kotlinx.coroutines.delay

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Banner() {
    LayoutBanner()
}

@Composable
fun LayoutBanner() {
    val images = listOf(
        "https://9to5mac.com/wp-content/uploads/sites/6/2023/11/iphone-16-rumors.jpg?quality=82&strip=all&w=1600",
        "https://mir-s3-cdn-cf.behance.net/project_modules/max_1200/ca6cd7150863355.63021c4cc4737.jpg",
        "https://thumbs.dreamstime.com/b/vector-banner-iphone-vinnytsia-ukraine-september-illustration-app-web-presentation-design-229970813.jpg"
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
