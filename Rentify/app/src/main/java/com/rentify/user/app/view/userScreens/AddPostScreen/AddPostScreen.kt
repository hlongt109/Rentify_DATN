//package com.rentify.user.app.view.userScreens.AddPostScreen
//
//import android.content.Context
//import android.net.Uri
//import android.util.Log
//import android.widget.Toast
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxHeight
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.navigationBarsPadding
//
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.statusBarsPadding
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
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
//import androidx.compose.ui.draw.shadow
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
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowDropDown
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.DropdownMenu
//import androidx.compose.material3.DropdownMenuItem
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.ExposedDropdownMenuBox
//import androidx.compose.material3.Icon
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.livedata.observeAsState
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.platform.LocalConfiguration
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.compose.rememberNavController
//import com.rentify.user.app.network.APIService
//import com.rentify.user.app.network.ApiClient
//import com.rentify.user.app.network.RetrofitClient
//
//import com.rentify.user.app.ui.theme.colorHeaderSearch
//import com.rentify.user.app.view.staffScreens.addPostScreen.Components.BuildingLabel
//import com.rentify.user.app.view.userScreens.AddPostScreen.Components.BuildingOptions
//
//import com.rentify.user.app.view.userScreens.AddPostScreen.Components.RoomLabel
//import com.rentify.user.app.view.userScreens.AddPostScreen.Components.RoomOption
//import com.rentify.user.app.view.userScreens.AddPostScreen.Components.RoomOptions
//
//import com.rentify.user.app.view.userScreens.AddPostScreen.Components.SelectMedia
//import com.rentify.user.app.viewModel.PostViewModel.PostViewModel
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
//fun isFieldEmpty(field: String): Boolean {
//    return field.isBlank() // Kiểm tra trường có trống không
//}
//
//fun isValidPrice(price: String): Boolean {
//    return price.toDoubleOrNull() != null // Kiểm tra giá có phải là số hợp lệ
//}
//
//fun isValidPhoneNumber(phone: String): Boolean {
//    // Kiểm tra số điện thoại có đúng 10 chữ số và bắt đầu bằng "0"
//    return phone.startsWith("0") && phone.length == 10 && phone.all { it.isDigit() }
//}
//
//
//fun logRequestBody(requestBody: RequestBody) {
//    val buffer = Buffer()
//    try {
//        requestBody.writeTo(buffer)
//        val content = buffer.readUtf8()
//        Log.d("click", "RequestBody content: $content")
//    } catch (e: Exception) {
//        Log.e("click", "Error reading RequestBody: ${e.message}")
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun AddPostScreen(navController: NavHostController) {
//    val viewModel: PostViewModel = viewModel()
//    var selectedImages by remember { mutableStateOf(emptyList<Uri>()) }
//    var selectedVideos by remember { mutableStateOf(emptyList<Uri>()) }
//    val configuration = LocalConfiguration.current
//    val screenHeight = configuration.screenHeightDp
//    val buildings by viewModel.buildings
//    val selectedBuilding by viewModel.selectedBuilding
//
//    val postTypee = navController.currentBackStackEntry?.arguments?.getString("postType") ?: "default"
//
//    val context = LocalContext.current
//    val scrollState = rememberScrollState()
//    var title by remember { mutableStateOf("") }
//    var userId by remember { mutableStateOf("671a29b84e350b2df4aee4ed") }
//    var content by remember { mutableStateOf("") }
//
//    var selectedRoom by remember { mutableStateOf<String?>(null) }
//    val rooms by viewModel.roomsFromContracts.collectAsState() // Lấy danh sách phòng từ ViewModel
//    var selectedRoomId by remember { mutableStateOf<String?>(null) }
//
//
//
//    suspend fun addPost(
//        context: Context,
//        apiService: APIService,
//        selectedImages: List<Uri>,
//        selectedVideos: List<Uri>
//    ): Boolean {
//        // Chuẩn bị dữ liệu `RequestBody`
//        val userId = "671a29b84e350b2df4aee4ed".toRequestBody("text/plain".toMediaTypeOrNull())
//        val buildingId = selectedBuilding?.toRequestBody("text/plain".toMediaTypeOrNull())
//            ?: "".toRequestBody("text/plain".toMediaTypeOrNull())
//        val roomId  = selectedRoom?.toRequestBody("text/plain".toMediaTypeOrNull())
//        val title = title.toRequestBody("multipart/form-data".toMediaTypeOrNull())
//        val content = content.toRequestBody("multipart/form-data".toMediaTypeOrNull())
//        val status = "0".toRequestBody("multipart/form-data".toMediaTypeOrNull())
//        val postType = postTypee.toRequestBody("multipart/form-data".toMediaTypeOrNull())
//        Log.d("postTypee", "Dư liệu vừa thêm xong: ${postTypee}")
//        logRequestBody(postType)
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
//            val response = apiService.addPost_user(
//                userId = userId,
//                buildingId = buildingId,
//                roomId = roomId,
//                title = title,
//                content = content,
//                postType = postType,
//                status = status,
//                videos = photoPart,
//                photos = videoPart
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
//                        text = "Thêm bài đăng tìm ở ghép",
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
//
////video
//                SelectMedia { images, videos ->
//                    selectedImages = images
//                    selectedVideos = videos
//                    Log.d("AddPost", "Received Images: $selectedImages")
//                    Log.d("AddPost", "Received Videos: $selectedVideos")
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
//                Column {
//                    RoomLabel()
//                    RoomOptions(
//                        userId = userId,
//                        selectedRoom = selectedRoom,
//                        onRoomSelected = { roomId ->
//                            selectedRoom = roomId
//                            Log.d("MainScreen", "Selected room ID: $roomId")
//                        }
//                    )
//
//
////                    viewModel.selectedBuilding.value?.let {
////                        RoomOptions (
////                            buildingId = it,
////                            selectedRoom = selectedRoom,
////                            onRoomSelected = { roomId ->
////                                selectedRoom = roomId
////                            }
////                        )
////                    }
//                    //673b57f7d24f9f5e94603b17
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
//                }
//
//
//                // dịch vụ
//                Spacer(modifier = Modifier.height(10.dp))
//                Column {
//                    BuildingLabel()
//
//                    selectedRoom?.let {
//                        BuildingOptions(
//                            roomId= it,
//                            selectedBuilding = selectedBuilding,
//                            )
//                        Log.d("MainScreen", "Selected buiding ID: $selectedBuilding")
//                    }
//                }
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
//                        if (isFieldEmpty(title)) {
//                            // Hiển thị thông báo lỗi nếu title trống
//                            Toast.makeText(context, "Tiêu đề không thể trống", Toast.LENGTH_SHORT).show()
//                            return@Button        }
//
//                        if (selectedImages.isEmpty()) {
//                            // Hiển thị thông báo nếu không có ảnh nào được chọn
//                            Toast.makeText(context, "Bạn phải chọn ít nhất một ảnh!", Toast.LENGTH_SHORT).show()
//                            return@Button
//                        }
//                        if (selectedVideos.isEmpty()) {
//                            // Hiển thị thông báo nếu không có ảnh nào được chọn
//                            Toast.makeText(context, "Bạn phải chọn ít nhất một video!", Toast.LENGTH_SHORT).show()
//                            return@Button
//                        }
//
//                        if (isFieldEmpty(content)) {
//                            // Hiển thị thông báo lỗi nếu content trống
//                            Toast.makeText(context, "Nội dung không thể trống", Toast.LENGTH_SHORT).show()
//                            return@Button
//                        }
//                        CoroutineScope(Dispatchers.Main).launch {
//                            val apiService = RetrofitClient.apiService
//                            val isSuccessful = withContext(Dispatchers.IO) {
//                                addPost(context, apiService, selectedImages, selectedVideos)
//                            }
//
//                            if (isSuccessful) {
//                                // Chuyển màn khi bài đăng được tạo thành công
//                                navController.navigate("SEARCHPOSTROOMATE")
//                            } else {
//                                // Hiển thị thông báo lỗi nếu tạo bài thất bại
//                                Toast.makeText(context, "Failed to create post", Toast.LENGTH_SHORT).show()
//                            }
//                        }
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
//    AddPostScreen(navController = rememberNavController())
//}