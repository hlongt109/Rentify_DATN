package com.rentify.user.app.view.userScreens.contractScreen

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.rentify.user.app.R
import com.rentify.user.app.view.userScreens.contractScreen.components.ContractBody
import com.rentify.user.app.view.userScreens.contractScreen.components.ContractImageTopBar

@Composable
fun ContractImageScreen(navController: NavController, imageUrlsJson: String) {
    val gson = Gson()
    val imageUrls: List<String> = gson.fromJson(Uri.decode(imageUrlsJson), object : TypeToken<List<String>>() {}.type)

    var isDialogOpen by remember { mutableStateOf(false) }
    var selectedImageUrl by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        ContractImageTopBar(navController)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            // Hiển thị các ảnh với HorizontalPager để lướt qua ảnh
            imageUrls.forEach { imageUrl ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            selectedImageUrl = imageUrl
                            isDialogOpen = true
                        }
                ) {
                    // Hiển thị ảnh thông thường
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = "Contract Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(R.drawable.g), // Thay bằng ảnh placeholder của bạn
                        error = painterResource(R.drawable.g)        // Thay bằng ảnh lỗi nếu URL không hợp lệ
                    )
                }
            }
        }

        // Hiển thị dialog khi người dùng nhấn vào ảnh
        ZoomableImageDialog(
            isOpen = isDialogOpen,
            imageUrl = selectedImageUrl,
            onDismiss = { isDialogOpen = false }
        )
    }
}

@Composable
fun ZoomableImageDialog(
    isOpen: Boolean,
    imageUrl: String?,
    onDismiss: () -> Unit
) {
    if (isOpen && imageUrl != null) {
        Dialog(onDismissRequest = onDismiss) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent) // Nền trong suốt
                    .clickable(
                        indication = null, // Tắt hiệu ứng đổ bóng
                        interactionSource = remember { MutableInteractionSource() }
                    ) { onDismiss() } // Nhấn vào toàn bộ vùng ngoài ảnh để đóng dialog
            ) {
                var scale by remember { mutableStateOf(1f) }
                var offset by remember { mutableStateOf(Offset(0f, 0f)) }

                // Zoomable and draggable container
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(Unit) {
                            detectTransformGestures { _, pan, zoom, _ ->
                                scale *= zoom // Phóng to ảnh
                                offset = Offset(
                                    offset.x + pan.x,
                                    offset.y + pan.y
                                ) // Di chuyển ảnh
                            }
                        }
                ) {
                    // Hiển thị ảnh với AsyncImage và scale + drag
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = "Contract Image",
                        modifier = Modifier
                            .fillMaxSize()
                            .scale(scale) // Áp dụng tỷ lệ zoom
                            .offset { IntOffset(offset.x.toInt(), offset.y.toInt()) } // Chuyển đổi từ Offset sang IntOffset
                            .align(Alignment.Center),
                        contentScale = ContentScale.Fit,
                        placeholder = painterResource(R.drawable.g), // Thay bằng ảnh placeholder của bạn
                        error = painterResource(R.drawable.g)        // Thay bằng ảnh lỗi nếu URL không hợp lệ
                    )
                }
            }
        }
    }
}

