package com.rentify.user.app.view.staffScreens.addPostScreen.Components

//import android.net.Uri
//import android.widget.Toast
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.result.PickVisualMediaRequest
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.background
//
//import androidx.compose.foundation.layout.Box
//
//import androidx.compose.foundation.layout.padding
//
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalConfiguration
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.Dp
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.interaction.MutableInteractionSource
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxHeight
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.offset
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.lazy.LazyRow
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.Button
//import androidx.compose.material.ButtonDefaults
//import androidx.compose.material3.IconButton
//
//
//import androidx.compose.ui.Alignment
//
//import androidx.compose.ui.draw.shadow
//
//import androidx.compose.ui.res.painterResource
//
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavHostController
//import com.rentify.user.app.R
//
//import androidx.compose.material3.Icon
//
//import androidx.compose.ui.graphics.Path
//import androidx.compose.ui.graphics.Shape
//import androidx.compose.ui.graphics.graphicsLayer
//import androidx.compose.ui.unit.Density
//import androidx.compose.ui.unit.LayoutDirection
//import androidx.compose.ui.geometry.Size
//import androidx.compose.ui.graphics.Outline
//
//import androidx.compose.material3.Text
//import androidx.compose.runtime.mutableStateListOf
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.platform.LocalContext
//import coil.compose.rememberAsyncImagePainter
//import coil.compose.rememberImagePainter
//
//import com.google.accompanist.flowlayout.FlowRow
//import com.rentify.user.app.network.addPost
//import kotlinx.coroutines.launch
//
//@Composable
//fun AddPostButton(
//    navController: NavHostController,
//    userId: String, // Lấy từ session hoặc ViewModel
//    title: String,
//    content: String,
//    roomPrice: String,
//    address: String,
//    phoneNumber: String,
//    selectedRoomTypes: List<String>,
//    selectedComfortable: List<String>,
//    selectedService: List<String>,
//    selectedImages: List<Uri>,
//    selectedVideos: List<Uri>,
//    onPostAdded: () -> Unit // Callback sau khi thêm thành công
//) {
//    val context = LocalContext.current
//    val coroutineScope = rememberCoroutineScope()
//
//    Button(
//        onClick = {
//            coroutineScope.launch {
//                try {
//                    val postCreated = addPost(
//                        userId = userId,
//                        title = title,
//                        content = content,
//                        roomPrice = roomPrice,
//                        address = address,
//                        phoneNumber = phoneNumber,
//                        roomTypes = selectedRoomTypes,
//                        comfortable = selectedComfortable,
//                        services = selectedService,
//                        images = selectedImages,
//                        videos = selectedVideos
//                    )
//                    if (postCreated) {
//                        Toast.makeText(context, "Thêm bài đăng thành công!", Toast.LENGTH_LONG).show()
//                        onPostAdded()
//                    } else {
//                        Toast.makeText(context, "Thêm bài đăng thất bại! Vui lòng thử lại.", Toast.LENGTH_LONG).show()
//
//                    }
//                } catch (e: Exception) {
//                    // Catch any error such as network issues, file upload issues, etc.
//                    Toast.makeText(context, "Lỗi: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
//             println("lỗi:${e.localizedMessage}")
//                }
//            }
//        },
//        modifier = Modifier.fillMaxWidth().height(50.dp),
//        shape = RoundedCornerShape(10.dp),
//        // colors = ButtonDefaults.buttonColors(containerColor = Color(0xff5dadff))
//    ) {
//        Text(text = "Thêm bài đăng", fontSize = 16.sp, color = Color.White)
//    }
//}
