package com.rentify.user.app.view.staffScreens.UpdatePostScreen.Components

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.FrameLayout
import android.widget.ListPopupWindow.MATCH_PARENT
import android.widget.VideoView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background

import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.padding

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.IconButton


import androidx.compose.ui.Alignment

import androidx.compose.ui.draw.shadow

import androidx.compose.ui.res.painterResource

import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.rentify.user.app.R

import androidx.compose.material3.Icon

import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline

import androidx.compose.material3.Text
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter

import com.google.accompanist.flowlayout.FlowRow
//import com.google.android.exoplayer2.ExoPlayer
//import com.google.android.exoplayer2.MediaItem
//import com.google.android.exoplayer2.ui.PlayerView
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
//
//@Composable
//fun SelectMedia(
//    onMediaSelected: (List<Uri>, List<Uri>) -> Unit
//) {
//    val selectedImages = remember { mutableStateListOf<Uri>() }
//    val selectedVideos = remember { mutableStateListOf<Uri>() }
//
//    // Launcher cho việc chọn nhiều ảnh từ album
//    val launcherImage = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.OpenMultipleDocuments(),
//        onResult = { uris ->
//            Log.d("LauncherImage", "Uris: $uris")
//            uris?.let {
//                selectedImages.addAll(it)
//                Log.d("SelectedImagesUpdated", selectedImages.toString())
//            }
//        }
//    )
//
//    val launcherVideo = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.OpenMultipleDocuments(),
//        onResult = { uris ->
//            Log.d("LauncherVideo", "Uris: $uris")
//            uris?.let {
//                selectedVideos.addAll(it)
//                Log.d("SelectedVideosUpdated", selectedVideos.toString())
//            }
//        }
//    )
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//
//    ) {
//        // Nút chọn nhiều ảnh từ album
//        Row(
//            modifier = Modifier.padding(5.dp),
//            verticalAlignment = Alignment.CenterVertically,
//        ) {
//            Row(
//                modifier = Modifier
//                    .clickable { launcherImage.launch(arrayOf("image/*")) } // "image/*" để chọn ảnh từ album
//                    .shadow(3.dp, shape = RoundedCornerShape(10.dp))
//                    .background(Color.White)
//                    .border(0.dp, Color(0xFFEEEEEE), RoundedCornerShape(10.dp))
//                    .padding(25.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.image),
//                    contentDescription = null,
//                    modifier = Modifier.size(30.dp)
//                )
//            }
//            Spacer(modifier = Modifier.width(15.dp))
//            Column {
//                Text(
//                    text = "Ảnh Phòng trọ",
//                    color = Color.Black,
//                    fontSize = 14.sp
//                )
//                Text(
//                    text = "Tối đa 10 ảnh",
//                    color = Color(0xFFBFBFBF),
//                    fontSize = 13.sp
//                )
//            }
//        }
//        // Hiển thị ảnh đã chọn
//        Spacer(modifier = Modifier.height(20.dp))
//        Text(
//            text = "Ảnh đã chọn:",
//            color = Color.Black,
//            fontSize = 16.sp
//        )
//        LazyRow(
//            modifier = Modifier.fillMaxWidth(),
//            contentPadding = PaddingValues(8.dp)
//        ) {
//            items(selectedImages) { imageUri ->
//                Image(
//                    painter = rememberImagePainter(imageUri),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .size(100.dp)
//                        .padding(4.dp)
//                )
//            }
//        }
//        Spacer(modifier = Modifier.height(17.dp))
//
//        // Nút chọn nhiều video từ album
//        Column(
//            modifier = Modifier
//                .clickable { launcherVideo.launch(arrayOf("video/*")) } // "video/*" để chọn video từ album
//                .fillMaxWidth()
//                .shadow(3.dp, shape = RoundedCornerShape(10.dp))
//                .background(Color.White)
//                .border(0.dp, Color(0xFFEEEEEE), RoundedCornerShape(10.dp))
//                .padding(25.dp),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.video),
//                contentDescription = null,
//                modifier = Modifier.size(30.dp)
//            )
//            Spacer(modifier = Modifier.height(7.dp))
//            Text(
//                text = "Video",
//                color = Color.Black,
//                fontSize = 13.sp
//            )
//        }
//
//
//
//        // Hiển thị video đã chọn
//        Spacer(modifier = Modifier.height(20.dp))
//        Text(
//            text = "Video đã chọn:",
//            color = Color.Black,
//            fontSize = 16.sp
//        )
//        LazyRow(
//            modifier = Modifier.fillMaxWidth(),
//            contentPadding = PaddingValues(8.dp)
//        ) {
//            items(selectedVideos) { videoUri ->
//                // Sử dụng ExoPlayer hoặc một thư viện khác để hiển thị video
//                // Ví dụ dưới đây chỉ là placeholder
//                Box(
//                    modifier = Modifier
//                        .size(100.dp)
//                        .padding(4.dp)
//                        .background(Color.Gray, shape = RoundedCornerShape(8.dp)),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(
//                        text = "Video",
//                        color = Color.White,
//                        modifier = Modifier.align(Alignment.Center)
//                    )
//                }
//            }
//        }
//    }
//}
@Composable
fun SelectMedia(
    onMediaSelected: (List<Uri>, List<Uri>) -> Unit
) {
    val selectedImages = remember { mutableStateListOf<Uri>() }
    val selectedVideos = remember { mutableStateListOf<Uri>() }

    // Launcher chọn ảnh
    val launcherImage = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenMultipleDocuments()
    ) { uris ->
        uris?.let {
            selectedImages.addAll(it)
            onMediaSelected(selectedImages, selectedVideos)
        }
    }

    // Launcher chọn video
    val launcherVideo = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenMultipleDocuments()
    ) { uris ->
        uris?.let {
            selectedVideos.addAll(it)
            onMediaSelected(selectedImages, selectedVideos)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Button chọn ảnh
        Row(
            modifier = Modifier.padding(5.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                modifier = Modifier
                    .clickable { launcherImage.launch(arrayOf("image/*")) }
                    .shadow(3.dp, shape = RoundedCornerShape(10.dp))
                    .background(Color.White)
                    .border(0.dp, Color(0xFFEEEEEE), RoundedCornerShape(10.dp))
                    .padding(25.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.image),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
            }
            Spacer(modifier = Modifier.width(15.dp))
            Column {
                Text(
                    text = "Ảnh Phòng trọ",
                    color = Color.Black,
                    fontSize = 14.sp
                )
                Text(
                    text = "Tối đa 10 ảnh",
                    color = Color(0xFFBFBFBF),
                    fontSize = 13.sp
                )
            }
        }

        LazyRow {
            items(selectedImages) { uri ->
                Box(modifier = Modifier.padding(4.dp)) {
                    Image(
                        painter = rememberImagePainter(uri),
                        contentDescription = null,
                        modifier = Modifier.size(80.dp)
                    )
                    // Nút xóa
                    Box(
                        modifier = Modifier
                            .size(16.dp) // Kích thước nút nhỏ hơn
                            .background(Color.Red, shape = CircleShape)
                            .align(Alignment.TopEnd)
                            .clickable { selectedImages.remove(uri) }, // Xóa ảnh khi nhấn
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Xóa",
                            tint = Color.White,
                            modifier = Modifier.size(12.dp) // Kích thước biểu tượng nhỏ hơn
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Button chọn video
        Column(
            modifier = Modifier
                .clickable { launcherVideo.launch(arrayOf("video/*")) }
                .fillMaxWidth()
                .shadow(3.dp, shape = RoundedCornerShape(10.dp))
                .background(Color.White)
                .border(0.dp, Color(0xFFEEEEEE), RoundedCornerShape(10.dp))
                .padding(25.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.video),
                contentDescription = null,
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.height(7.dp))
            Text(
                text = "Video",
                color = Color.Black,
                fontSize = 13.sp
            )
        }

        LazyRow {
            items(selectedVideos) { uri ->
                Box(modifier = Modifier.padding(4.dp)) {
                    // Hiển thị thumbnail video
                    VideoThumbnail(uri)
                    // Nút xóa
                    Box(
                        modifier = Modifier
                            .size(16.dp) // Kích thước nút nhỏ hơn
                            .background(Color.Red, shape = CircleShape)
                            .align(Alignment.TopEnd)
                            .clickable { selectedVideos.remove(uri) }, // Xóa ảnh khi nhấn
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Xóa",
                            tint = Color.White,
                            modifier = Modifier.size(12.dp) // Kích thước biểu tượng nhỏ hơn
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun VideoThumbnail(uri: Uri) {
    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(uri))
            prepare()
            playWhenReady = false // Chỉ hiển thị thumbnail, không tự động phát
        }
    }

    DisposableEffect(exoPlayer) {
        onDispose {
            exoPlayer.release() // Giải phóng tài nguyên khi component bị hủy
        }
    }

    Box(modifier = Modifier.size(80.dp)) {
        AndroidView(
            factory = { PlayerView(it).apply {
                player = exoPlayer
                useController = false // Tắt điều khiển
                layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
            } },
            modifier = Modifier.fillMaxSize()
        )
    }
}
