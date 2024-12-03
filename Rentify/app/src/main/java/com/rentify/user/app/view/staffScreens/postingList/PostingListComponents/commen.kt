package com.rentify.user.app.view.staffScreens.postingList.PostingListComponents

import android.util.Log
import android.view.Gravity
import android.widget.FrameLayout
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
//import com.google.android.exoplayer2.ExoPlayer
//import com.google.android.exoplayer2.MediaItem
//import com.google.android.exoplayer2.ui.PlayerView
import com.rentify.user.app.R
import com.rentify.user.app.view.staffScreens.UpdatePostScreen.TriangleShape

import com.rentify.user.app.viewModel.PostViewModel.PostViewModel

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun UpdatePostScreen(navController: NavHostController,postId: String) {
//    var selectedImages by remember { mutableStateOf(emptyList<Uri>()) }
//    var selectedVideos by remember { mutableStateOf(emptyList<Uri>()) }
//    val configuration = LocalConfiguration.current
//    val screenHeight = configuration.screenHeightDp
//    var selectedRoomTypes by remember { mutableStateOf(listOf<String>()) }
//    var selectedComfortable by remember { mutableStateOf(listOf<String>()) }
//    var selectedService by remember { mutableStateOf(listOf<String>()) }
//    val context = LocalContext.current
//    val scrollState = rememberScrollState()
//    var address by remember { mutableStateOf("") }
//    var title by remember { mutableStateOf("") }
//    var content by remember { mutableStateOf("") }
//    var phoneNumber by remember { mutableStateOf("") }
//    var roomPrice by remember { mutableStateOf("") }
//    val postId = navController.currentBackStackEntry?.arguments?.getString("postId")
//    val viewModel: PostViewModel = viewModel()
//    val postDetail by viewModel.postDetail.observeAsState()
//
//    LaunchedEffect(postId) {
//        postId?.let {
//            viewModel.getPostDetail(it)
//        }
//    }
//
//    postDetail?.let { detail ->
//        title = detail.title ?: ""
//        content = detail.content ?: ""
//        roomPrice = detail.price?.toString() ?: ""
//        address = detail.address ?: ""
//        phoneNumber = detail.phoneNumber ?: ""
//        selectedRoomTypes = detail.room_type?.split(",")?.toList() ?: listOf()
//        selectedComfortable = detail.amenities ?: listOf()
//        selectedService = detail.services ?: listOf()
//
//        val images = detail.photos ?: listOf()
//        val videos = detail.videos ?: listOf()
//
//        // Ghi log toàn bộ dữ liệu
//        Log.d("UpdatePostScreen", "Post Detail: ${detail}")
//        Log.d("UpdatePostScreen", "Title: $title")
//        Log.d("UpdatePostScreen", "Content: $content")
//        Log.d("UpdatePostScreen", "Room Price: $roomPrice")
//        Log.d("UpdatePostScreen", "Address: $address")
//        Log.d("UpdatePostScreen", "Phone Number: $phoneNumber")
//        Log.d("UpdatePostScreen", "Selected Room Types: $selectedRoomTypes")
//        Log.d("UpdatePostScreen", "Selected Comfortable: $selectedComfortable")
//        Log.d("UpdatePostScreen", "Selected Service: $selectedService")
//        Log.d("UpdatePostScreen", "Images: $images")
//        Log.d("UpdatePostScreen", "Videos: $videos")
//    }
//
////    suspend fun updatePost(
////        context: Context,
////        apiService: APIService,
////        selectedImages: List<Uri>,
////        selectedVideos: List<Uri>,
////        postId: String
////    ): Boolean {
////        val userId = "672490e5ce87343d0e701012".toRequestBody("text/plain".toMediaTypeOrNull())
////        val title = title.toRequestBody("multipart/form-data".toMediaTypeOrNull())
////        val content = content.toRequestBody("multipart/form-data".toMediaTypeOrNull())
////        val status = "0".toRequestBody("multipart/form-data".toMediaTypeOrNull())
////        val postType = "rent".toRequestBody("multipart/form-data".toMediaTypeOrNull())
////        val price = roomPrice.toRequestBody("multipart/form-data".toMediaTypeOrNull())
////        val address = address.toRequestBody("multipart/form-data".toMediaTypeOrNull())
////        val phoneNumber = phoneNumber.toRequestBody("multipart/form-data".toMediaTypeOrNull())
////        val roomType = selectedRoomTypes.joinToString(",").toRequestBody("multipart/form-data".toMediaTypeOrNull())
////        val amenities = selectedComfortable.joinToString(",").toRequestBody("multipart/form-data".toMediaTypeOrNull())
////        val services = selectedService.joinToString(",").toRequestBody("multipart/form-data".toMediaTypeOrNull())
////
////        //   val servicesString = selectedService.joinToString(",").toRequestBody("multipart/form-data".toMediaTypeOrNull())
////       // val amenitiesString = selectedComfortable.joinToString(",").toRequestBody("multipart/form-data".toMediaTypeOrNull())
////
////        val videoParts = selectedVideos.mapNotNull { uri ->
////            val mimeType = context.contentResolver.getType(uri) ?: "video/mp4"
////            prepareMultipartBody(context, uri, "video", ".mp4", mimeType)
////        }
////        val photoParts = selectedImages.mapNotNull { uri ->
////            val mimeType = context.contentResolver.getType(uri) ?: "image/jpeg"
////            prepareMultipartBody(context, uri, "photo", ".jpg", mimeType)
////        }
////
////        return try {
////            val response = apiService.updatePost(
////              postId,  userId, title, content, status, postType, price, address, phoneNumber,
////                roomType, amenities , services , videos = videoParts, photos = photoParts       )
////
////            if (response.isSuccessful) {
////                Log.d("updatePost", "Post updated successfully: ${response.body()}")
////                true // Thành công
////            } else {
////                Log.e("updatePost", "Error: ${response.errorBody()?.string()}")
////                false // Thất bại
////            }
////        } catch (e: Exception) {
////            Log.e("updatePost", "Exception: ${e.message}")
////            false // Thất bại
////        }
////    }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(color = Color(0xfff7f7f7))
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .statusBarsPadding()
//                .navigationBarsPadding()
//                .background(color = Color(0xfff7f7f7))
//                .padding(bottom = screenHeight.dp/7f)
//
//        ) {
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .background(color = Color(0xffffffff))
//                    .padding(10.dp)
//
//            ) {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//
//                        .background(color = Color(0xffffffff)), // Để IconButton nằm bên trái
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    IconButton(onClick = {   navController.navigate("POSTING_STAFF")}) {
//                        Image(
//                            painter = painterResource(id = R.drawable.back),
//                            contentDescription = null,
//                            modifier = Modifier.size(30.dp, 30.dp)
//                        )
//                    }
//                    Text(
//                        text = "Thêm bài đăng",
//                        //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
//                        color = Color.Black,
//                        fontWeight = FontWeight(700),
//                        fontSize = 17.sp,
//                    )
//                    IconButton(onClick = { /*TODO*/ }) {
//                    }
//                }
//            }
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .verticalScroll(scrollState)
//                    .background(color = Color(0xfff7f7f7))
//                    .padding(15.dp)
//            ) {
//                // tiêu đề
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(5.dp)
//                ) {
//                    Row {
//                        Text(
//                            text = "Tiêu đề bài đằng",
//                            //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
//                            color = Color(0xff7f7f7f),
//                            // fontWeight = FontWeight(700),
//                            fontSize = 13.sp,
//                        )
//                        Text(
//
//                            text = " *",
//                            //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
//                            color = Color(0xffff1a1a),
//                            // fontWeight = FontWeight(700),
//                            fontSize = 16.sp,
//
//                            )
//                    }
//                    TextField(
//                        value = title,
//                        onValueChange = { title = it },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(53.dp),
//                        colors = TextFieldDefaults.colors(
//                            focusedIndicatorColor = Color(0xFFcecece),
//                            unfocusedIndicatorColor = Color(0xFFcecece),
//                            focusedPlaceholderColor = Color.Black,
//                            unfocusedPlaceholderColor = Color.Gray,
//                            unfocusedContainerColor = Color(0xFFf7f7f7),
//                            focusedContainerColor = Color(0xFFf7f7f7),
//                        ),
//                        placeholder = {
//                            Text(
//                                text = "Nhập tiêu đề bài đăng",
//                                fontSize = 13.sp,
//                                color = Color(0xFF898888),
//                                fontFamily = FontFamily(Font(R.font.cairo_regular))
//                            )
//                        },
//                        shape = RoundedCornerShape(size = 8.dp),
//                        textStyle = TextStyle(
//                            color = Color.Black, fontFamily = FontFamily(Font(R.font.cairo_regular))
//                        )
//                    )
//                }
//                // loai phòng
//                Column {
//                    RoomTypeLabel()
//
//                    RoomTypeOptions(
//                        selectedRoomTypes = selectedRoomTypes,
//                        onRoomTypeSelected = { roomType ->
//                            // Nếu người dùng chọn lại loại đã chọn, bỏ chọn (trả lại danh sách rỗng)
//                            selectedRoomTypes = if (selectedRoomTypes.contains(roomType)) {
//                                listOf() // Xóa tất cả lựa chọn nếu chọn lại
//                            } else {
//                                listOf(roomType) // Chỉ chọn cái mới
//                            }
//                        }
//                    )
//                }
////video
//                SelectMedia { images, videos ->
//                    selectedImages = images
//                    selectedVideos = videos
//                    Log.d("AddPost", "Received Images: $selectedImages")
//                    Log.d("AddPost", "Received Videos: $selectedVideos")
//                }
//                // gía phòng
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(5.dp)
//                ) {
//                    Row {
//                        Text(
//                            text = "Giá Phòng",
//                            //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
//                            color = Color(0xff7f7f7f),
//                            // fontWeight = FontWeight(700),
//                            fontSize = 13.sp,
//                        )
//                        Text(
//
//                            text = " *",
//                            //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
//                            color = Color(0xffff1a1a),
//                            // fontWeight = FontWeight(700),
//                            fontSize = 16.sp,
//
//                            )
//                    }
//                    TextField(
//                        value = roomPrice,
//                        onValueChange = { roomPrice = it },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(53.dp),
//                        colors = TextFieldDefaults.colors(
//                            focusedIndicatorColor = Color(0xFFcecece),
//                            unfocusedIndicatorColor = Color(0xFFcecece),
//                            focusedPlaceholderColor = Color.Black,
//                            unfocusedPlaceholderColor = Color.Gray,
//                            unfocusedContainerColor = Color(0xFFf7f7f7),
//                            focusedContainerColor = Color(0xFFf7f7f7),
//                        ),
//                        placeholder = {
//                            Text(
//                                text = "Nhập giá phòng",
//                                fontSize = 13.sp,
//                                color = Color(0xFF898888),
//                                fontFamily = FontFamily(Font(R.font.cairo_regular))
//                            )
//                        },
//                        shape = RoundedCornerShape(size = 8.dp),
//                        textStyle = TextStyle(
//                            color = Color.Black, fontFamily = FontFamily(Font(R.font.cairo_regular))
//                        )
//                    )
//                }
//                //  Nội dung
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(5.dp)
//                ) {
//                    Row {
//                        Text(
//                            text = "Nội dung",
//                            //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
//                            color = Color(0xff7f7f7f),
//                            // fontWeight = FontWeight(700),
//                            fontSize = 13.sp,
//                        )
//                        Text(
//                            text = " *",
//                            //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
//                            color = Color(0xffff1a1a),
//                            // fontWeight = FontWeight(700),
//                            fontSize = 16.sp,
//
//                            )
//                    }
//                    TextField(
//                        value = content,
//                        onValueChange = { content = it },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(53.dp),
//                        colors = TextFieldDefaults.colors(
//                            focusedIndicatorColor = Color(0xFFcecece),
//                            unfocusedIndicatorColor = Color(0xFFcecece),
//                            focusedPlaceholderColor = Color.Black,
//                            unfocusedPlaceholderColor = Color.Gray,
//                            unfocusedContainerColor = Color(0xFFf7f7f7),
//                            focusedContainerColor = Color(0xFFf7f7f7),
//                        ),
//                        placeholder = {
//                            Text(
//                                text = "Nhập nội dung",
//                                fontSize = 13.sp,
//                                color = Color(0xFF898888),
//                                fontFamily = FontFamily(Font(R.font.cairo_regular))
//                            )
//                        },
//                        shape = RoundedCornerShape(size = 8.dp),
//                        textStyle = TextStyle(
//                            color = Color.Black, fontFamily = FontFamily(Font(R.font.cairo_regular))
//                        )
//                    )
//                }
//                // dịa chỉ
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(5.dp)
//                ) {
//                    Row {
//                        Text(
//                            text = "Địa chỉ",
//                            color = Color(0xff7f7f7f),
//                            fontSize = 13.sp,
//                        )
//                        Text(
//                            text = " *",
//                            color = Color(0xffff1a1a),
//                            fontSize = 16.sp,
//
//                            )
//                    }
//                    TextField(
//                        value = address,
//                        onValueChange = { address = it },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(53.dp),
//                        colors = TextFieldDefaults.colors(
//                            focusedIndicatorColor = Color(0xFFcecece),
//                            unfocusedIndicatorColor = Color(0xFFcecece),
//                            focusedPlaceholderColor = Color.Black,
//                            unfocusedPlaceholderColor = Color.Gray,
//                            unfocusedContainerColor = Color(0xFFf7f7f7),
//                            focusedContainerColor = Color(0xFFf7f7f7),
//                        ),
//                        placeholder = {
//                            Text(
//                                text = "Nhập địa chỉ *",
//                                fontSize = 13.sp,
//                                color = Color(0xFF898888),
//                                fontFamily = FontFamily(Font(R.font.cairo_regular))
//                            )
//                        },
//                        shape = RoundedCornerShape(size = 8.dp),
//                        textStyle = TextStyle(
//                            color = Color.Black, fontFamily = FontFamily(Font(R.font.cairo_regular))
//                        )
//                    )
//                }
//                // sóo điện thoại
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(5.dp)
//                ) {
//                    Row {
//                        Text(
//                            text = "Số điện thoại",
//                            //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
//                            color = Color(0xff7f7f7f),
//                            // fontWeight = FontWeight(700),
//                            fontSize = 13.sp,
//                        )
//                        Text(
//
//                            text = " *",
//                            //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
//                            color = Color(0xffff1a1a),
//                            // fontWeight = FontWeight(700),
//                            fontSize = 16.sp,
//
//                            )
//                    }
//                    TextField(
//                        value = phoneNumber,
//                        onValueChange = { phoneNumber = it },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(53.dp),
//                        colors = TextFieldDefaults.colors(
//                            focusedIndicatorColor = Color(0xFFcecece),
//                            unfocusedIndicatorColor = Color(0xFFcecece),
//                            focusedPlaceholderColor = Color.Black,
//                            unfocusedPlaceholderColor = Color.Gray,
//                            unfocusedContainerColor = Color(0xFFf7f7f7),
//                            focusedContainerColor = Color(0xFFf7f7f7),
//                        ),
//                        placeholder = {
//                            Text(
//                                text = "Nhập địa chỉ *",
//                                fontSize = 13.sp,
//                                color = Color(0xFF898888),
//                                fontFamily = FontFamily(Font(R.font.cairo_regular))
//                            )
//                        },
//                        shape = RoundedCornerShape(size = 8.dp),
//                        textStyle = TextStyle(
//                            color = Color.Black, fontFamily = FontFamily(Font(R.font.cairo_regular))
//                        )
//                    )
//                }
//                Spacer(modifier = Modifier.height(3.dp))
//                Column {
//                    ComfortableLabel()
//
//                    ComfortableOptions(
//                        selectedComfortable = selectedComfortable,
//                        onComfortableSelected = { comfortable ->
//                            selectedComfortable = if (selectedComfortable.contains(comfortable)) {
//                                selectedComfortable - comfortable
//                            } else {
//                                selectedComfortable + comfortable
//                            }
//                        }
//                    )
//                }
//                // dịch vụ
//                Spacer(modifier = Modifier.height(10.dp))
//                Column {
//                    ServiceLabel()
//                    ServiceOptions(
//                        selectedService = selectedService,
//                        onServiceSelected = { service ->
//                            selectedService = if (selectedService.contains(service)) {
//                                selectedService - service
//                            } else {
//                                selectedService + service
//                            }
//                        }
//                    )
//                }
//
//            }
//        }
//        Box(
//            modifier = Modifier
//                .align(Alignment.BottomCenter)
//                .fillMaxWidth()
//                .height(screenHeight.dp/7f)
//                .background(color = Color.White)
//        ) {
//            Box(modifier = Modifier.padding(20.dp)) {
//                Button(
//                    onClick = {
//
//                        val userId = "672490e5ce87343d0e701012".toRequestBody("text/plain".toMediaTypeOrNull())
//                        val title = title.toRequestBody("multipart/form-data".toMediaTypeOrNull())
//                        val content = content.toRequestBody("multipart/form-data".toMediaTypeOrNull())
//                        val status = "0".toRequestBody("multipart/form-data".toMediaTypeOrNull())
//                        val postType = "rent".toRequestBody("multipart/form-data".toMediaTypeOrNull())
//                        val price = roomPrice.toRequestBody("multipart/form-data".toMediaTypeOrNull())
//                        val address = address.toRequestBody("multipart/form-data".toMediaTypeOrNull())
//                        val phoneNumber = phoneNumber.toRequestBody("multipart/form-data".toMediaTypeOrNull())
//                        val roomType = selectedRoomTypes.joinToString(",").toRequestBody("multipart/form-data".toMediaTypeOrNull())
//                        val amenities = selectedComfortable.joinToString(",").toRequestBody("multipart/form-data".toMediaTypeOrNull())
//                        val services = selectedService.joinToString(",").toRequestBody("multipart/form-data".toMediaTypeOrNull())
//
//
//                        val videoParts = selectedVideos.mapNotNull { uri ->
//                            val mimeType = context.contentResolver.getType(uri) ?: "video/mp4"
//                            prepareMultipartBody(context, uri, "video", ".mp4", mimeType)
//                        }
//                        val photoParts = selectedImages.mapNotNull { uri ->
//                            val mimeType = context.contentResolver.getType(uri) ?: "image/jpeg"
//                            prepareMultipartBody(context, uri, "photo", ".jpg", mimeType)
//                        }
//                        // Gửi yêu cầu cập nhật
//                        postId?.let {
//                            viewModel.updatePost(
//                                postId = it,
//                                userId = userId,
//                                title = title,
//                                content = content,
//                                status = status,
//                                postType = postType,
//                                price = price,
//                                address = address,
//                                phoneNumber = phoneNumber,
//                                roomType = roomType,
//                                amenities = amenities,
//                                services = services,
//                                videos = videoParts,
//                                photos = photoParts
//                            )
//                        }
//                    }, modifier = Modifier.height(50.dp).fillMaxWidth(),
//                    shape = RoundedCornerShape(10.dp), colors = ButtonDefaults.buttonColors(
//                        containerColor = Color(0xff5dadff)
//                    )
//                ) {
//                    Text(
//                        text = "Sửa bài đăng",
//                        fontSize = 16.sp,
//                        fontFamily = FontFamily(Font(R.font.cairo_regular)),
//                        color = Color(0xffffffff)
//                    )
//                }
//
//            }
//        }
//    }
//}


//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun GreetingLayoutUpdatePostScreen() {
//    UpdatePostScreen(navController = rememberNavController())
//}
//

//                        CoroutineScope(Dispatchers.Main).launch {
//                            val apiService = RetrofitClient.apiService
//                            val isSuccessful = withContext(Dispatchers.IO) {
//                                updatePost(context, apiService, selectedImages, selectedVideos, postId = postId.toString())
//                            }
//
//                            if (isSuccessful) {
//                                // Chuyển màn khi bài đăng được chỉnh sửa thành công
//                                navController.navigate("POSTING_STAFF")
//                            } else {
//                                // Hiển thị thông báo lỗi nếu cập nhật bài thất bại
//                                Toast.makeText(context, "Failed to update post", Toast.LENGTH_SHORT).show()
//                            }
//                        }

///detail

//@Composable
//fun SelectMedia(
//    onMediaSelected: (List<Uri>, List<Uri>) -> Unit
//) {
//    val selectedImages = remember { mutableStateListOf<Uri>() }
//    val selectedVideos = remember { mutableStateListOf<Uri>() }
//
//    // Launcher chọn ảnh
//    val launcherImage = rememberLauncherForActivityResult(
//        ActivityResultContracts.OpenMultipleDocuments()
//    ) { uris ->
//        uris?.let {
//            selectedImages.addAll(it)
//            onMediaSelected(selectedImages, selectedVideos)
//        }
//    }
//
//    // Launcher chọn video
//    val launcherVideo = rememberLauncherForActivityResult(
//        ActivityResultContracts.OpenMultipleDocuments()
//    ) { uris ->
//        uris?.let {
//            selectedVideos.addAll(it)
//            onMediaSelected(selectedImages, selectedVideos)
//        }
//    }
//
//    Column(modifier = Modifier.fillMaxSize()) {
//        // Button chọn ảnh
//        Row(
//            modifier = Modifier.padding(5.dp),
//            verticalAlignment = Alignment.CenterVertically,
//        ) {
//            Row(
//                modifier = Modifier
//                    .clickable { launcherImage.launch(arrayOf("image/*")) }
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
//
//        LazyRow {
//            items(selectedImages) { uri ->
//                Box(modifier = Modifier.padding(4.dp)) {
//                    Image(
//                        painter = rememberImagePainter(uri),
//                        contentDescription = null,
//                        modifier = Modifier.size(80.dp)
//                    )
//                    // Nút xóa
//                    Box(
//                        modifier = Modifier
//                            .size(16.dp) // Kích thước nút nhỏ hơn
//                            .background(Color.Red, shape = CircleShape)
//                            .align(Alignment.TopEnd)
//                            .clickable { selectedImages.remove(uri) }, // Xóa ảnh khi nhấn
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Icon(
//                            imageVector = Icons.Default.Close,
//                            contentDescription = "Xóa",
//                            tint = Color.White,
//                            modifier = Modifier.size(12.dp) // Kích thước biểu tượng nhỏ hơn
//                        )
//                    }
//                }
//            }
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Button chọn video
//        Column(
//            modifier = Modifier
//                .clickable { launcherVideo.launch(arrayOf("video/*")) }
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
//        LazyRow {
//            items(selectedVideos) { uri ->
//                Box(modifier = Modifier.padding(4.dp)) {
//                    // Hiển thị thumbnail video
//                    VideoThumbnail(uri)
//                    // Nút xóa
//                    Box(
//                        modifier = Modifier
//                            .size(16.dp) // Kích thước nút nhỏ hơn
//                            .background(Color.Red, shape = CircleShape)
//                            .align(Alignment.TopEnd)
//                            .clickable { selectedVideos.remove(uri) }, // Xóa ảnh khi nhấn
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Icon(
//                            imageVector = Icons.Default.Close,
//                            contentDescription = "Xóa",
//                            tint = Color.White,
//                            modifier = Modifier.size(12.dp) // Kích thước biểu tượng nhỏ hơn
//                        )
//                    }
//                }
//            }
//        }
//    }
//}




////////detail

//@OptIn(ExperimentalPagerApi::class)
//@Composable
//fun PostDetailScreen(navController: NavController, postId: String, viewModel: PostViewModel = PostViewModel()) {
//    val postDetail by viewModel.postDetail.observeAsState()
//    val context = LocalContext.current
//    val scrollState = rememberScrollState()
//    val configuration = LocalConfiguration.current
//    val screenHeight = configuration.screenHeightDp
//    val isPostUpdated = remember { mutableStateOf(false) } // Trạng thái để kiểm tra khi nào cập nhật
//
//    // Chạy khi màn hình được hiển thị lại hoặc khi postId thay đổi
//    LaunchedEffect(postId, isPostUpdated.value) {
//        Log.d("detail", "RequestBody check: ")
//        viewModel.getPostDetail(postId)
//
//        isPostUpdated.value = false  // Reset lại trạng thái sau khi tải dữ liệu
//    }
//
//    postDetail?.let { detail ->
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(color = Color(0xfff7f7f7))
//        ) {
//            Column(
//                modifier = Modifier
//                    .statusBarsPadding()
//                    .navigationBarsPadding()
//            ){
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .background(color = Color(0xfff7f7f7)),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    IconButton(onClick = { navController.navigate("POSTING_STAFF")}) {
//                        Image(
//                            painter = painterResource(id = R.drawable.back),
//                            contentDescription = null,
//                            modifier = Modifier.size(30.dp, 30.dp)
//                        )
//                    }
//                    Text(
//                        text = "Chi tiết bài đăng",
//                        color = Color.Black,
//                        fontWeight = FontWeight(700),
//                        fontSize = 17.sp,
//                    )
//                    IconButton(onClick = { /*TODO*/ }) {
//                    }
//                }
//                Column(
//                    modifier = Modifier
//                        .padding(16.dp)
//                        .height(screenHeight.dp / 1.3f)
//                        .verticalScroll(scrollState)
//                ) {
//
//                    // Hiển thị ảnh/video đầu tiên
//                    PostImageSection(images = detail.photos ?: emptyList())
//                    Spacer(modifier = Modifier.height(16.dp))
//// loại phòng
//                    Text(
//                        text = "Loại phòng: ${detail.room?.room_type}",
//                        fontSize = 14.sp,
//                        color=Color(0xfffeb501),
//                        modifier = Modifier.padding(horizontal = 10.dp)
//                    )
//                    // tên phòng
//                    Text(
//                        text = "Phòng: ${detail.room?.room_name}",
//                        fontSize = 16.sp,
//                        color=Color.Black,
//                        fontWeight = FontWeight.Bold,
//                        modifier = Modifier.padding(horizontal = 10.dp)
//                    )
//                    // địa chỉ
//                    Text(
//                        text = "Địa chỉ: ${detail.building?.address}",
//                        fontSize = 16.sp,
//                        color=Color.Black,
//                        fontWeight = FontWeight.Bold,
//                        modifier = Modifier.padding(horizontal = 10.dp)
//                    )
//                    // giá
//                    Text(
//                        text = "Giá: ${detail.room?.price?.let { "$it VND" }}",
//                        fontSize = 15.sp,
//                        color=Color(0xffde5135),
//                        modifier = Modifier.padding(horizontal = 10.dp)
//                    )
//                    // Tiêu đề bài đăng
//                    Column(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(5.dp)
//                    ) {
//                        Row {
//                            Text(
//                                text = "Tiêu đề",
//                                color = Color(0xff7f7f7f),
//                                fontSize = 14.sp,
//                            )
//                            Text(
//
//                                text = " *",
//                                color = Color(0xffff1a1a),
//                                fontSize = 16.sp,
//
//                                )
//                        }
//                        Text(
//                            text = detail.title,
//                            fontSize = 15.sp,
//                            color = Color(0xff898888),
//                            modifier = Modifier.padding(horizontal = 10.dp),
//
//                            )
//                        Spacer(
//                            modifier = Modifier
//                                .padding(vertical = 5.dp)
//                                .fillMaxWidth() // Gạch dưới rộng bằng chiều rộng nội dung
//                                .height(1.dp)  // Độ dày của gạch
//                                .background(Color(0xff898888)) // Màu của gạch dưới
//                        )
//                    }
//                    Spacer(modifier = Modifier.height(8.dp))
///// nôi dung
//                    Column(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(5.dp)
//                    ) {
//                        Row {
//                            Text(
//                                text = "Nội dung",
//                                color = Color(0xff7f7f7f),
//                                fontSize = 14.sp,
//                            )
//                            Text(
//
//                                text = " *",
//                                color = Color(0xffff1a1a),
//                                fontSize = 16.sp,
//
//                                )
//                        }
//                        Text(
//                            text = "${detail.content}",
//                            fontSize = 15.sp,
//                            color = Color(0xff898888),
//                            modifier = Modifier.padding(horizontal = 10.dp),
//
//                            )
//
//
//
//                        Spacer(
//                            modifier = Modifier
//                                .padding(vertical = 5.dp)
//                                .fillMaxWidth() // Gạch dưới rộng bằng chiều rộng nội dung
//                                .height(1.dp)  // Độ dày của gạch
//                                .background(Color(0xff898888)) // Màu của gạch dưới
//                        )
//                    }
//                    PostVideoSection(videos = detail.videos ?: emptyList())
//// tên tòa và số bài dăng
//                    Row(
//                        modifier = Modifier.fillMaxWidth().padding(start = 15.dp)
//                    ) {
//                        Image(
//                            painter = painterResource(id = R.drawable.user),
//                            contentDescription = "",
//                            modifier = Modifier.size(50.dp)
//                        )
//
//                        Column {
//                            Text(
//                                text = "Tòa nhà: ${detail.building?.nameBuilding}",
//                                fontWeight = FontWeight.Bold,
//                                modifier = Modifier
//                                    .padding(start = 5.dp)
//                                    .padding(top = 10.dp),
//                                fontSize = 16.sp
//                            )
//                            Text(
//                                text = "${detail.building?.post_count} bài đăng",
//                                modifier = Modifier
//                                    .padding(start = 5.dp)
//                                    .padding(bottom = 10.dp),
//                                fontSize = 10.sp,
//                                color = Color(0xFF44ACFE)
//                            )
//                        }
//                    }
//
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    // Hiển thị thông tin người đăng
////                    Text(
////                        text = "Người đăng: ${detail.user?.name}",
////                        fontSize = 16.sp,
////                        modifier = Modifier.padding(horizontal = 10.dp)
////                    )
////                    Text(
////                        text = "Email: ${detail.user?.email}",
////                        fontSize = 14.sp,
////                        modifier = Modifier.padding(horizontal = 10.dp)
////                    )
////                    Spacer(modifier = Modifier.height(16.dp))
//                    //                   Spacer(modifier = Modifier.height(16.dp))
////                    // Hiển thị ngày tạo và cập nhật
////                    Text(
////                        text = "Ngày tạo: ${detail.created_at}",
////                        fontSize = 14.sp,
////                        modifier = Modifier.padding(horizontal = 10.dp)
////                    )
////                    Text(
////                        text = "Ngày cập nhật: ${detail.updated_at}",
////                        fontSize = 14.sp,
////                        modifier = Modifier.padding(horizontal = 10.dp)
////                    )
//
//                }
//            }
//
//            Box(
//                modifier = Modifier
//                    .align(Alignment.BottomCenter)
//                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp)
//                    .height(screenHeight.dp / 9f)
//                    .background(color = Color(0xfff7f7f7))
//            ) {
//                Button(
//                    onClick = {
//                        navController.navigate("update_post_screen/${postId}") {
//                            popUpTo("post_detail/$postId") { inclusive = true }
//                        }
//                    },
//                    modifier = Modifier.height(50.dp).fillMaxWidth(),
//                    shape = RoundedCornerShape(10.dp),
//                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xff5dadff))
//                ) {
//                    Text(
//                        text = "Sửa bài đăng",
//                        fontSize = 16.sp,
//                        fontFamily = FontFamily(Font(R.font.cairo_regular)),
//                        color = Color(0xffffffff)
//                    )
//                }
//            }
//        }
//    } ?: run {
//        Text("Đang tải chi tiết bài đăng...")
//    }
//}
//@Preview(showBackground = true)
//@Composable
//fun PostDetailScreenPreview() {
//    val mockNavController = rememberNavController() // Tạo NavController giả lập cho preview
//    PostDetailScreen(
//        navController = mockNavController,
//        postId = "mockPostId", // Truyền postId giả lập
//        viewModel = PostViewModel()
//    )
//}
//@OptIn(ExperimentalPagerApi::class)
//@Composable
//fun PostImageSection(images: List<String>) {
//    if (images.isNotEmpty()) {
//        val pagerState = rememberPagerState() // Quản lý trạng thái pager
//        Column(modifier = Modifier.fillMaxWidth()) {
//            Text(
//                text = "Hình ảnh",
//                fontSize = 18.sp,
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
//            )
//            HorizontalPager(
//                count = images.size,
//                state = pagerState,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(300.dp)
//            ) { pageIndex ->
//                val image = images[pageIndex]
//                Image(
//                    painter = rememberImagePainter("http://192.168.2.106:3000/$image"),
//                    contentDescription = "Post Image",
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(300.dp)
//                        .padding(8.dp)
//                        .clip(RoundedCornerShape(10.dp))
//                        .background(Color.LightGray),
//                    contentScale = ContentScale.Crop
//                )
//            }
//
//            // Dấu chấm chỉ số ảnh
//            HorizontalPagerIndicator(
//                pagerState = pagerState,
//                modifier = Modifier
//                    .align(Alignment.CenterHorizontally)
//                    .padding(8.dp),
//                activeColor = Color.Black,
//                inactiveColor = Color.Gray
//            )
//        }
//    }
//}
//@OptIn(ExperimentalPagerApi::class)
//@Composable
//fun PostVideoSection(videos: List<String>) {
//    if (videos.isNotEmpty()) {
//        val pagerState = rememberPagerState() // Quản lý trạng thái pager
//        Column(modifier = Modifier.fillMaxWidth()) {
//            Text(
//                text = "Video",
//                fontSize = 18.sp,
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
//            )
//            HorizontalPager(
//                count = videos.size,
//                state = pagerState,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(300.dp)
//            ) { pageIndex ->
//                val videoUrl = videos[pageIndex]
//                VideoPlayer(videoUrl = "http://192.168.2.106:3000/$videoUrl")
//            }
//
//            // Dấu chấm chỉ số video
//            HorizontalPagerIndicator(
//                pagerState = pagerState,
//                modifier = Modifier
//                    .align(Alignment.CenterHorizontally)
//                    .padding(8.dp),
//                activeColor = Color.Black,
//                inactiveColor = Color.Gray
//            )
//        }
//    }
//}
//
//
//@OptIn(ExperimentalPagerApi::class)
//@Composable
//fun PostMediaDialog(mediaList: List<String>, currentIndex: Int, onDismiss: () -> Unit) {
//    val pagerState = rememberPagerState(initialPage = currentIndex)
//
//    Dialog(onDismissRequest = onDismiss) {
//        Surface(
//            shape = MaterialTheme.shapes.medium,
//            color = Color.White,
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(500.dp)
//        ) {
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp)
//            ) {
//                HorizontalPager(
//                    count = mediaList.size,
//                    state = pagerState,
//                    modifier = Modifier.fillMaxWidth()
//                ) { pageIndex ->
//                    val media = mediaList[pageIndex]
//                    Box(
//                        modifier = Modifier.fillMaxWidth()
//                    ) {
//                        if (media.endsWith(".mp4")) {
//                            VideoPlayer(videoUrl = "http://192.168.2.106:3000/$media")
//                        } else {
//                            Image(
//                                painter = rememberImagePainter("http://192.168.2.106:3000/$media"),
//                                contentDescription = "Post Image",
//                                modifier = Modifier.fillMaxWidth()
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//@Composable
//fun VideoPlayer(videoUrl: String) {
//    // Quản lý ExoPlayer
//    val context = LocalContext.current
//    val exoPlayer = remember {
//        ExoPlayer.Builder(context).build().apply {
//            setMediaItem(MediaItem.fromUri(videoUrl))
//            prepare()
//            playWhenReady = false // Chỉ phát khi người dùng bấm
//        }
//    }
//
//    // Đảm bảo ExoPlayer được giải phóng khi Composable bị hủy
//    DisposableEffect(key1 = exoPlayer) {
//        onDispose {
//            exoPlayer.release() // Giải phóng tài nguyên ExoPlayer
//        }
//    }
//
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .background(Color.Black) // Màu nền để đảm bảo không bị trống
//    ) {
//        AndroidView(
//            factory = { context ->
//                PlayerView(context).apply {
//                    player = exoPlayer
//                    useController = true // Hiển thị thanh điều khiển
//                    layoutParams = FrameLayout.LayoutParams(
//                        FrameLayout.LayoutParams.MATCH_PARENT,
//                        FrameLayout.LayoutParams.MATCH_PARENT,
//                        Gravity.CENTER // Căn giữa PlayerView trong FrameLayout
//                    )
//                }
//            },
//            modifier = Modifier.fillMaxSize()
//        )
//    }
//}



//////add con6trc
//package com.rentify.user.app.view.staffScreens.addContractScreen
//
//
//import DatePickerField
//import android.content.Context
//import android.net.Uri
//import android.util.Log
//import android.widget.Toast
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.navigationBarsPadding
//
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.statusBarsPadding
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextField
//import androidx.compose.material3.TextFieldDefaults
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.Font
//import androidx.compose.ui.text.font.FontFamily
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavHostController
//import com.rentify.user.app.R
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.platform.LocalConfiguration
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.compose.rememberNavController
//import com.rentify.user.app.network.APIService
//import com.rentify.user.app.network.RetrofitClient
//import com.rentify.user.app.view.staffScreens.UpdatePostScreen.isFieldEmpty
//import com.rentify.user.app.view.staffScreens.addContractScreen.Components.BuildingOptions
//import com.rentify.user.app.view.staffScreens.addContractScreen.Components.RoomOptions
//import com.rentify.user.app.view.staffScreens.addContractScreen.Components.SelectMedia
//
//import com.rentify.user.app.view.staffScreens.addPostScreen.Components.BuildingLabel
//
//import com.rentify.user.app.view.staffScreens.addPostScreen.Components.RoomLabel
//
//
//
//
//import com.rentify.user.app.viewModel.PostViewModel.PostViewModel
//import com.rentify.user.app.viewModel.StaffViewModel.ContractViewModel
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//import okhttp3.MediaType.Companion.toMediaTypeOrNull
//import okhttp3.MultipartBody
//import okhttp3.RequestBody
//import okhttp3.RequestBody.Companion.asRequestBody
//import okhttp3.RequestBody.Companion.toRequestBody
//import okio.Buffer
//import java.io.File
//
//fun prepareMultipartBody(
//    context: Context,
//    uri: Uri,
//    partName: String,
//    defaultExtension: String,
//    mimeType: String
//): MultipartBody.Part? {
//    return try {
//        // Lấy input stream từ Uri
//        val inputStream = context.contentResolver.openInputStream(uri) ?: return null
//
//        // Tạo file tạm
//        val tempFile = File.createTempFile("upload", defaultExtension, context.cacheDir)
//        tempFile.outputStream().use { outputStream ->
//            inputStream.copyTo(outputStream)
//        }
//
//        // Tạo MultipartBody.Part
//        val requestFile = tempFile.asRequestBody(mimeType.toMediaTypeOrNull())
//        MultipartBody.Part.createFormData(partName, tempFile.name, requestFile)
//    } catch (e: Exception) {
//        Log.e("prepareMultipartBody", "Error: ${e.message}")
//        null
//    }
//}
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun AddContractScreens(navController: NavHostController) {
//    var selectedImages by remember { mutableStateOf(emptyList<Uri>()) }
//
//    val configuration = LocalConfiguration.current
//    val screenHeight = configuration.screenHeightDp
//    var startDate by remember { mutableStateOf("") }
//    var endDate by remember { mutableStateOf("") }
//
//    val viewModel: ContractViewModel = viewModel()
//    val context = LocalContext.current
//    val scrollState = rememberScrollState()
//    var userId by remember { mutableStateOf("") }
//    var content by remember { mutableStateOf("") }
//    var selectedBuilding by remember { mutableStateOf<String?>(null) }
//    var selectedRoom by remember { mutableStateOf<String?>(null) }
//
//    fun logRequestBody(requestBody: RequestBody) {
//        val buffer = Buffer()
//        try {
//            requestBody.writeTo(buffer)
//            val content = buffer.readUtf8()
//            Log.d("click", "RequestBody content: $content")
//        } catch (e: Exception) {
//            Log.e("click", "Error reading RequestBody: ${e.message}")
//        }
//    }
//
//    suspend fun addContrac(
//        context: Context,
//        apiService: APIService,
//        selectedImages: List<Uri>,
//        selectedVideos: List<Uri>
//    ): Boolean {
//        // Chuẩn bị dữ liệu `RequestBody`
//        val userId = userId.toRequestBody("text/plain".toMediaTypeOrNull())
//        val buildingId = selectedBuilding?.toRequestBody("text/plain".toMediaTypeOrNull())
//            ?: "".toRequestBody("text/plain".toMediaTypeOrNull())
//        val manageId = "673e064ef5b7bf786842bdbc".toRequestBody("text/plain".toMediaTypeOrNull())
//        val roomId  = selectedRoom?.toRequestBody("text/plain".toMediaTypeOrNull())
//        val content = content.toRequestBody("multipart/form-data".toMediaTypeOrNull())
//        val status = "0".toRequestBody("multipart/form-data".toMediaTypeOrNull())
//        val startDate = startDate.toRequestBody("multipart/form-data".toMediaTypeOrNull())
//        val endDate  = endDate.toRequestBody("multipart/form-data".toMediaTypeOrNull())
//        val videoPart = selectedVideos.mapNotNull { uri ->
//            val mimeType = context.contentResolver.getType(uri) ?: "video/mp4"
//            prepareMultipartBody(
//                context,
//                uri,
//                "video",
//                ".mp4",
//                mimeType
//            )
//        }
//        val photoPart = selectedImages.mapNotNull { uri ->
//            val mimeType = context.contentResolver.getType(uri) ?: "image/jpeg"
//            prepareMultipartBody(
//                context,
//                uri,
//                "photo",
//                ".jpg",
//                mimeType
//            )
//        }
//
//
//        // Gửi dữ liệu đến API
//        return try {
//            val response = apiService.addContract(
//                userId = userId,
//                buildingId = buildingId,
//                roomId = roomId,
//                manageId = manageId,
//                content = content,
//                startDate = startDate,
//                status = status,
//                endDate=endDate,
//                photosContract  = photoPart
//            )
//            if (response.isSuccessful) {
//                Log.d("AddPost", "Dư liệu vừa thêm xong: ${response.body()}")
//                true
//            } else {
//                Log.e("AddPost", "Error: ${response.errorBody()?.string()}")
//                false
//            }
//        } catch (e: Exception) {
//            Log.e("AddPost", "Exception: ${e.message}")
//            false
//        }
//    }
//
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(color = Color(0xfff7f7f7))
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .statusBarsPadding()
//                .navigationBarsPadding()
//                .background(color = Color(0xfff7f7f7))
//                .padding(bottom = screenHeight.dp/7f)
//
//        ) {
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .background(color = Color(0xffffffff))
//                    .padding(10.dp)
//
//            ) {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//
//                        .background(color = Color(0xffffffff)), // Để IconButton nằm bên trái
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    IconButton(onClick = {   navController.popBackStack()}) {
//                        Image(
//                            painter = painterResource(id = R.drawable.back),
//                            contentDescription = null,
//                            modifier = Modifier.size(30.dp, 30.dp)
//                        )
//                    }
//                    Text(
//                        text = "Thêm hợp đồng",
//                        //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
//                        color = Color.Black,
//                        fontWeight = FontWeight(700),
//                        fontSize = 17.sp,
//                    )
//                    IconButton(onClick = { /*TODO*/ }) {
//                    }
//                }
//            }
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .verticalScroll(scrollState)
//                    .background(color = Color(0xfff7f7f7))
//                    .padding(15.dp)
//            ) {
//                // tiêu đề
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(5.dp)
//                ) {
//                    Row {
//                        Text(
//                            text = "Tiêu đề bài đằng",
//                            //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
//                            color = Color(0xff7f7f7f),
//                            // fontWeight = FontWeight(700),
//                            fontSize = 13.sp,
//                        )
//                        Text(
//
//                            text = " *",
//                            //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
//                            color = Color(0xffff1a1a),
//                            // fontWeight = FontWeight(700),
//                            fontSize = 16.sp,
//
//                            )
//                    }
//                    TextField(
//                        value = userId,
//                        onValueChange = { userId = it },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(53.dp),
//                        colors = TextFieldDefaults.colors(
//                            focusedIndicatorColor = Color(0xFFcecece),
//                            unfocusedIndicatorColor = Color(0xFFcecece),
//                            focusedPlaceholderColor = Color.Black,
//                            unfocusedPlaceholderColor = Color.Gray,
//                            unfocusedContainerColor = Color(0xFFf7f7f7),
//                            focusedContainerColor = Color(0xFFf7f7f7),
//                        ),
//                        placeholder = {
//                            Text(
//                                text = "Nhập userId",
//                                fontSize = 13.sp,
//                                color = Color(0xFF898888),
//                                fontFamily = FontFamily(Font(R.font.cairo_regular))
//                            )
//                        },
//                        shape = RoundedCornerShape(size = 8.dp),
//                        textStyle = TextStyle(
//                            color = Color.Black, fontFamily = FontFamily(Font(R.font.cairo_regular))
//                        )
//                    )
//                }
////video
//                SelectMedia { images ->
//                    selectedImages = images
//                    Log.d("AddPost", "Received Images: $selectedImages")
//
//                }
//
//                //  Nội dung
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(5.dp)
//                ) {
//                    Row {
//                        Text(
//                            text = "Nội dung",
//                            //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
//                            color = Color(0xff7f7f7f),
//                            // fontWeight = FontWeight(700),
//                            fontSize = 13.sp,
//                        )
//                        Text(
//                            text = " *",
//                            //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
//                            color = Color(0xffff1a1a),
//                            // fontWeight = FontWeight(700),
//                            fontSize = 16.sp,
//                        )
//                    }
//                    TextField(
//                        value = content,
//                        onValueChange = { content = it },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(53.dp),
//                        colors = TextFieldDefaults.colors(
//                            focusedIndicatorColor = Color(0xFFcecece),
//                            unfocusedIndicatorColor = Color(0xFFcecece),
//                            focusedPlaceholderColor = Color.Black,
//                            unfocusedPlaceholderColor = Color.Gray,
//                            unfocusedContainerColor = Color(0xFFf7f7f7),
//                            focusedContainerColor = Color(0xFFf7f7f7),
//                        ),
//                        placeholder = {
//                            Text(
//                                text = "Nhập nội dung",
//                                fontSize = 13.sp,
//                                color = Color(0xFF898888),
//                                fontFamily = FontFamily(Font(R.font.cairo_regular))
//                            )
//                        },
//                        shape = RoundedCornerShape(size = 8.dp),
//                        textStyle = TextStyle(
//                            color = Color.Black, fontFamily = FontFamily(Font(R.font.cairo_regular))
//                        )
//                    )
//                }
//                Spacer(modifier = Modifier.height(3.dp))
//                Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
//                    Text(text = "Chọn ngày cho hợp đồng", fontSize = 18.sp, fontWeight = FontWeight.Bold)
//
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    // Trường chọn ngày bắt đầu
//                    DatePickerField(
//                        label = "Ngày bắt đầu",
//                        selectedDate = startDate,
//                        onDateSelected = { startDate = it }
//                    )
//
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    // Trường chọn ngày kết thúc
//                    DatePickerField(
//                        label = "Ngày kết thúc",
//                        selectedDate = endDate,
//                        onDateSelected = { endDate = it }
//                    )
//                }
//                Column {
//                    BuildingLabel()
//
//                    BuildingOptions(
//                        manageId = "673e064ef5b7bf786842bdbc",
//                        selectedBuilding = selectedBuilding,
//                        onBuildingSelected = { buildingId ->
//                            selectedBuilding =buildingId // Cập nhật tòa nhà đã chọn
//                        }
//                    )
//                }
//                // dịch vụ
//                Spacer(modifier = Modifier.height(10.dp))
//                Column {
//                    RoomLabel()
//
//                    RoomOptions (
//                        buildingId = selectedBuilding!!,
//                        selectedRoom = selectedRoom,
//                        onRoomSelected = { roomId ->
//                            selectedRoom = roomId
//                        }
//                    )
//                }
//                //673b57f7d24f9f5e94603b17
////            ServiceOptions(
////                selectedService = selectedService,
////                onServiceSelected = { service ->
////                    selectedService = if (selectedService.contains(service)) {
////                        selectedService - service
////                    } else {
////                        selectedService + service
////                    }
////                }
////            )
//            }
//
//        }
//
//        Box(
//            modifier = Modifier
//                .align(Alignment.BottomCenter)
//                .fillMaxWidth()
//                .height(screenHeight.dp/7f)
//                .background(color = Color.White)
//        ) {
//            Box(modifier = Modifier.padding(20.dp)) {
//                Button(
//                    onClick = {
//                        if (isFieldEmpty(userId)) {
//                            // Hiển thị thông báo lỗi nếu title trống
//                            Toast.makeText(context, "Tiêu đề không thể trống", Toast.LENGTH_SHORT).show()
//                            return@Button        }
//
//                        if (selectedImages.isEmpty()) {
//                            // Hiển thị thông báo nếu không có ảnh nào được chọn
//                            Toast.makeText(context, "Bạn phải chọn ít nhất một ảnh!", Toast.LENGTH_SHORT).show()
//                            return@Button
//                        }
//
//
//                        if (isFieldEmpty(content)) {
//                            // Hiển thị thông báo lỗi nếu content trống
//                            Toast.makeText(context, "Nội dung không thể trống", Toast.LENGTH_SHORT).show()
//                            return@Button
//                        }
////                        CoroutineScope(Dispatchers.Main).launch {
////                            val apiService = RetrofitClient.apiService
////                            val isSuccessful = withContext(Dispatchers.IO) {
////                                addPost(context, apiService, selectedImages, selectedVideos)
////                            }
////
////                            if (isSuccessful) {
////                                // Chuyển màn khi bài đăng được tạo thành công
////                                navController.navigate("POSTING_STAFF")
////                            } else {
////                                // Hiển thị thông báo lỗi nếu tạo bài thất bại
////                                Toast.makeText(context, "Failed to create post", Toast.LENGTH_SHORT).show()
////                            }
////                        }
//
//                    }, modifier = Modifier.height(50.dp).fillMaxWidth(),
//                    shape = RoundedCornerShape(10.dp), colors = ButtonDefaults.buttonColors(
//                        containerColor = Color(0xff5dadff)
//                    )
//                ) {
//                    Text(
//                        text = "Thêm bài đăng",
//                        fontSize = 16.sp,
//                        fontFamily = FontFamily(Font(R.font.cairo_regular)),
//                        color = Color(0xffffffff)
//                    )
//                }
//
//            }
//        }
//    }
//}
//
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun GreetingLayoutAddPostScreen() {
//    AddContractScreens(navController = rememberNavController())
//}
//


