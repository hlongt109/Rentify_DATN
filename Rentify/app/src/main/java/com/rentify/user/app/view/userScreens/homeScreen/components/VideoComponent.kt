package com.rentify.user.app.view.userScreens.homeScreen.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun VideoComponent() {
    LayoutVideo()
}

@Composable
fun LayoutVideo() {
    val videoUrls = listOf(
        "https://youtube.com/shorts/qzLHzvGoijw?si=8WP37YkKSL7UT0Ei",
        "https://youtube.com/shorts/bGpxL7XVDx0?si=8OuGWgRW2hcm9BIW",
        "https://youtube.com/shorts/DakeacEADq4?si=xk4MUMHjfcFQBJf7"
    )
    val textUrls = listOf(
        "Đống Đa",
        "Thanh Xuân",
        "Cầu Giấy"
    )

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        items(videoUrls.size) { index ->
            VideoThumbnail(videoUrl = videoUrls[index], title = textUrls[index])
        }
    }
}

@Composable
fun VideoThumbnail(videoUrl: String, title: String) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(150.dp)
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))
                context.startActivity(intent)
            }
    ) {
        Column (
            modifier = Modifier.fillMaxWidth()
        ){
            // Use Coil to load a thumbnail for the video
            Image(
                painter = rememberImagePainter(data = "https://img.youtube.com/vi/${extractVideoId(videoUrl)}/0.jpg"),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Text(
                text = title,
                modifier = Modifier.padding(8.dp).fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif
            )
        }
    }
}

// Function to extract the video ID from the YouTube URL
fun extractVideoId(url: String): String {
    // Extract the video ID from the YouTube short link
    return Uri.parse(url).lastPathSegment ?: ""
}
