//package com.rentify.user.app.view.staffScreens.UpdatePostScreen.Components
//
//import android.net.Uri
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.lazy.LazyRow
//import androidx.compose.material.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import coil.compose.rememberImagePainter
//
//
//@Composable
//fun UpdateScreenContent(
//    selectedRoomTypes: List<String>,
//    onRoomTypeSelected: (String) -> Unit,
//    selectedComfortable: List<String>,
//    onComfortableSelected: (String) -> Unit,
//    selectedService: List<String>,
//    onServiceSelected: (String) -> Unit,
//    selectedImages: List<Uri>,
//    selectedVideos: List<Uri>,
//    onMediaSelected: (List<Uri>, List<Uri>) -> Unit
//) {
//    Column(modifier = Modifier.padding(16.dp).fillMaxSize()) {
//        // Hiển thị loại phòng
//        Text(text = "Loại Phòng", fontSize = 18.sp, color = Color.Black)
//        RoomTypeOptions(
//            selectedRoomTypes = selectedRoomTypes,
//            onRoomTypeSelected = onRoomTypeSelected
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Hiển thị tiện ích
//        Text(text = "Tiện Ích", fontSize = 18.sp, color = Color.Black)
//        ComfortableOptions(
//            selectedComfortable = selectedComfortable,
//            onComfortableSelected = onComfortableSelected
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Hiển thị dịch vụ
//        Text(text = "Dịch Vụ", fontSize = 18.sp, color = Color.Black)
//        ServiceOptions(
//            selectedService = selectedService,
//            onServiceSelected = onServiceSelected
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Chọn hình ảnh và video
//        Text(text = "Phương Tiện Media", fontSize = 18.sp, color = Color.Black)
//        SelectMedia(onMediaSelected = onMediaSelected)
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Hiển thị các hình ảnh đã chọn
//        Text(text = "Hình Ảnh", fontSize = 18.sp, color = Color.Black)
//        LazyRow {
//            items(selectedImages) { imageUri ->
//                Image(
//                    painter = rememberImagePainter(imageUri),
//                    contentDescription = null,
//                    modifier = Modifier.size(80.dp).padding(8.dp)
//                )
//            }
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Hiển thị các video đã chọn
//        Text(text = "Video", fontSize = 18.sp, color = Color.Black)
//        LazyRow {
//            items(selectedVideos) { videoUri ->
//                VideoThumbnail(videoUri)
//            }
//        }
//    }
//}
