package com.rentify.user.app.view.staffScreens.UpdatePostScreen


import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.FrameLayout
import android.widget.ListPopupWindow.MATCH_PARENT
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.rentify.user.app.R
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel


import coil.compose.rememberImagePainter
import com.google.accompanist.flowlayout.FlowRow
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerView
import com.rentify.user.app.model.PostingDetail
import com.rentify.user.app.network.APIService
import com.rentify.user.app.network.RetrofitClient


import com.rentify.user.app.view.staffScreens.UpdatePostScreen.Components.ComfortableLabel

import com.rentify.user.app.view.staffScreens.UpdatePostScreen.Components.RoomTypeLabel
import com.rentify.user.app.view.staffScreens.UpdatePostScreen.Components.RoomTypeOptions
import com.rentify.user.app.view.staffScreens.UpdatePostScreen.Components.ServiceLabel

import com.rentify.user.app.view.staffScreens.UpdatePostScreen.Components.TriangleShape

import com.rentify.user.app.view.userScreens.searchPostRoomateScreen.Component.HeaderComponent
import com.rentify.user.app.viewModel.PostViewModel.PostViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okio.Buffer
import java.io.File

fun prepareMultipartBody(
    context: Context,
    uri: Uri,
    partName: String,
    defaultExtension: String,
    mimeType: String
): MultipartBody.Part? {
    return try {
        // Lấy input stream từ Uri
        val inputStream = context.contentResolver.openInputStream(uri) ?: return null

        // Tạo file tạm
        val tempFile = File.createTempFile("upload", defaultExtension, context.cacheDir)
        tempFile.outputStream().use { outputStream ->
            inputStream.copyTo(outputStream)
        }

        // Tạo MultipartBody.Part
        val requestFile = tempFile.asRequestBody(mimeType.toMediaTypeOrNull())
        MultipartBody.Part.createFormData(partName, tempFile.name, requestFile)
    } catch (e: Exception) {
        Log.e("prepareMultipartBody", "Error: ${e.message}")
        null
    }
}
fun isFieldEmpty(field: String): Boolean {
    return field.isBlank() // Kiểm tra trường có trống không
}

fun isValidPrice(price: String): Boolean {
    return price.toDoubleOrNull() != null // Kiểm tra giá có phải là số hợp lệ
}

fun isValidPhoneNumber(phone: String): Boolean {
    // Kiểm tra số điện thoại có đúng 10 chữ số và bắt đầu bằng "0"
    return phone.startsWith("0") && phone.length == 10 && phone.all { it.isDigit() }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePostScreen(navController: NavHostController,postId: String) {
    var selectedImages by remember { mutableStateOf(emptyList<Uri>()) }
    var selectedVideos by remember { mutableStateOf(emptyList<Uri>()) }
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    var selectedRoomTypes by remember { mutableStateOf(listOf<String>()) }
    var selectedRoomTypes1 by remember { mutableStateOf(listOf<String>()) }

    var selectedComfortable_up by remember { mutableStateOf(listOf<String>()) }
    var selectedComfortable by remember { mutableStateOf(listOf<String>()) }
    var selectedService by remember { mutableStateOf(listOf<String>()) }
    var selectedService_up by remember { mutableStateOf(listOf<String>()) }

    val context = LocalContext.current
    val scrollState = rememberScrollState()
    var address by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var isEdited by remember { mutableStateOf(false) }
    var content by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var roomPrice by remember { mutableStateOf("") }
    val postId = navController.currentBackStackEntry?.arguments?.getString("postId")
    val viewModel: PostViewModel = viewModel()
    val postDetail by viewModel.postDetail.observeAsState()

    LaunchedEffect(postId) {
        postId?.let {
            viewModel.getPostDetail(it)
        }
    }

//    postDetail?.let { detail ->
//        if (!isEdited) {
//            title = detail.title ?: "" // Gán giá trị cũ từ postDetail nếu chưa chỉnh sửa
//            content = detail.content ?: ""
//            roomPrice = detail.price?.toString() ?: ""
//            address = detail.address ?: ""
//            phoneNumber = detail.phoneNumber ?: ""
//        }
//
//
//        selectedComfortable = detail.amenities?.let { amenities ->
//            amenities.toString() // Chuyển thành chuỗi
//                .removeSurrounding("[", "]") // Loại bỏ ký tự không mong muốn
//                .split(",") // Tách thành danh sách
//                .map { it.trim() } // Xóa khoảng trắng dư thừa
//                .filter { it.isNotEmpty() } // Lọc bỏ chuỗi rỗng
//        } ?: listOf()
//        selectedRoomTypes = detail.room_type?.let { room_type ->
//            room_type.toString() // Chuyển thành chuỗi
//                .removeSurrounding("[", "]") // Loại bỏ ký tự không mong muốn
//                .split(",") // Tách thành danh sách
//                .map { it.trim() } // Xóa khoảng trắng dư thừa
//                .filter { it.isNotEmpty() } // Lọc bỏ chuỗi rỗng
//        } ?: listOf()
//        selectedService = detail.services?.let { services ->
//            services.toString() // Chuyển thành chuỗi
//                .removeSurrounding("[", "]") // Loại bỏ ký tự không mong muốn
//                .split(",") // Tách thành danh sách
//                 .map { it.trim() } // Xóa khoảng trắng dư thừa
//                .filter { it.isNotEmpty() } // Lọc bỏ chuỗi rỗng
//        } ?: listOf()

//        val images = detail.photos ?: listOf()
//        val videos = detail.videos ?: listOf()
//        Log.d("image", "đtail: $images")
//        Log.d("video", "detail: $videos")
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
    }

//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(color = Color(0xfff7f7f7))
//    ) {
//
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .statusBarsPadding()
//                .navigationBarsPadding()
//                .background(color = Color(0xfff7f7f7))
//                .padding(bottom = screenHeight.dp/7f)
//
//        ) {
//
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
//                        text = "Sửa bài đăng",
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
//                        onValueChange = { newValue ->
//                            title = newValue // Cập nhật giá trị title khi người dùng thay đổi
//                            isEdited = true  // Đánh dấu là đã chỉnh sửa
//                        },
//                        modifier = Modifier
//                            .fillMaxWidth(),
//
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
//                    RoomTypesDisplay (
//                        roomTypes = selectedRoomTypes, // Danh sách tiện ích đã chọn
//                        onRoomTypeChange = { updatedRoomType ->
//                            selectedRoomTypes1 = updatedRoomType // Cập nhật danh sách tiện ích trong state ngoài
//                            Log.d("logUi", "updated rroom: $updatedRoomType") // Kiểm tra kết quả
//                        }
//                    )
//                }
////video
//                postDetail?.let {
//                    SelectMedia(
//                        onMediaSelected = { images, videos ->
//                            selectedImages = images
//                            selectedVideos = videos
//
//                            Log.d("SelectedImages", "Selected images: $selectedImages")
//                            Log.d("SelectedVideos", "Selected videos: $selectedVideos")
//                        },
//                        detail = it
//                    )
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
//
//                        onValueChange = { newValue ->
//                            roomPrice = newValue // Cập nhật giá trị title khi người dùng thay đổi
//                            isEdited = true  // Đánh dấu là đã chỉnh sửa
//                        },
//                        modifier = Modifier
//                            .fillMaxWidth(),
//
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
//                        onValueChange = { newValue ->
//                            content = newValue // Cập nhật giá trị title khi người dùng thay đổi
//                            isEdited = true  // Đánh dấu là đã chỉnh sửa
//                        },
//                        modifier = Modifier
//                            .fillMaxWidth(),
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
//
//                        onValueChange = { newValue ->
//                            address = newValue // Cập nhật giá trị title khi người dùng thay đổi
//                            isEdited = true  // Đánh dấu là đã chỉnh sửa
//                        },
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
//
//                        onValueChange = { newValue ->
//                            phoneNumber = newValue // Cập nhật giá trị title khi người dùng thay đổi
//                            isEdited = true  // Đánh dấu là đã chỉnh sửa
//                        },
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
//                    AmenitiesDisplay(
//                        amenities = selectedComfortable, // Danh sách tiện ích đã chọn
//                        onAmenitiesChange = { updatedAmenities ->
//                            selectedComfortable_up = updatedAmenities // Cập nhật danh sách tiện ích trong state ngoài
//                            Log.d("EditPostScreen", "Updated Amenities: $updatedAmenities") // Kiểm tra kết quả
//                        }
//                    )
//                }
//                // dịch vụ
//                Spacer(modifier = Modifier.height(10.dp))
//                Column {
//                    ServiceLabel()
//                    ServicesDisplay(
//                        services = selectedService, // Danh sách tiện ích đã chọn
//                        onServicesChange = { updatedServices ->
//                            selectedService_up = updatedServices // Cập nhật danh sách tiện ích trong state ngoài
//                            Log.d("logUi", "updatedService: $updatedServices") // Kiểm tra kết quả
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
//                .background(color = Color(0xfff7f7f7))
//        ) {
//            fun logRequestBody(requestBody: RequestBody) {
//                val buffer = Buffer()
//                try {
//                    requestBody.writeTo(buffer)
//                    val content = buffer.readUtf8()
//                    Log.d("click", "RequestBody content: $content")
//                } catch (e: Exception) {
//                    Log.e("click", "Error reading RequestBody: ${e.message}")
//                }
//            }
//
//            Box(modifier = Modifier.padding(20.dp)) {
//                Button(
//                    onClick = {
////                        if (isFieldEmpty(title)) {
////                            // Hiển thị thông báo lỗi nếu title trống
////                            Toast.makeText(context, "Tiêu đề không thể trống", Toast.LENGTH_SHORT).show()
////                            return@Button        }
////                        if (isFieldEmpty(roomPrice)) {
////                            // Hiển thị thông báo lỗi nếu roomPrice trống
////                            Toast.makeText(context, "Giá phòng không thể trống", Toast.LENGTH_SHORT).show()
////                            return@Button
////                        }
////// Kiểm tra giá phòng có phải là số hợp lệ không
////                        if (!isValidPrice(roomPrice)) {
////                            Toast.makeText(context, "Giá phòng phải là số hợp lệ", Toast.LENGTH_SHORT).show()
////                            return@Button
////                        }
////                        if (isFieldEmpty(content)) {
////                            // Hiển thị thông báo lỗi nếu content trống
////                            Toast.makeText(context, "Nội dung không thể trống", Toast.LENGTH_SHORT).show()
////                            return@Button
////                        }
////                        if (isFieldEmpty(address)) {
////                            // Hiển thị thông báo lỗi nếu address trống
////                            Toast.makeText(context, "Địa chỉ không thể trống", Toast.LENGTH_SHORT).show()
////                            return@Button
////                        }
////
////                        if (isFieldEmpty(phoneNumber)) {
////                            // Hiển thị thông báo lỗi nếu phoneNumber trống
////                            Toast.makeText(context, "Số điện thoại không thể trống", Toast.LENGTH_SHORT).show()
////                            return@Button
////                        }
////                        // Kiểm tra số điện thoại có đúng định dạng không (10 chữ số và bắt đầu bằng 0)
////                        if (!isValidPhoneNumber(phoneNumber)) {
////                            Toast.makeText(context, "Số điện thoại phải có 10 chữ số và bắt đầu bằng 0", Toast.LENGTH_SHORT).show()
////                            return@Button
////                        }
////
////
////
////                        val userId = "672490e5ce87343d0e701012".toRequestBody("text/plain".toMediaTypeOrNull())
////                        val title = title.toRequestBody("multipart/form-data".toMediaTypeOrNull())
////                        val content = content.toRequestBody("multipart/form-data".toMediaTypeOrNull())
////                        val status = "0".toRequestBody("multipart/form-data".toMediaTypeOrNull())
////                        val postType = "rent".toRequestBody("multipart/form-data".toMediaTypeOrNull())
////                        val price = roomPrice.toRequestBody("multipart/form-data".toMediaTypeOrNull())
////                        val address = address.toRequestBody("multipart/form-data".toMediaTypeOrNull())
////                        val phoneNumber = phoneNumber.toRequestBody("multipart/form-data".toMediaTypeOrNull())
////                        val roomType = selectedRoomTypes1.joinToString(",").toRequestBody("multipart/form-data".toMediaTypeOrNull())
////                        val amenities = selectedComfortable_up.joinToString(",").toRequestBody("multipart/form-data".toMediaTypeOrNull())
////                        val services = selectedService_up.joinToString(",").toRequestBody("multipart/form-data".toMediaTypeOrNull())
////                        // Kiểm tra trống các trường quan trọng
////
////                        Log.d("click", "${selectedService_up.joinToString(",")}")
////
////                        logRequestBody(services)   // Log nội dung services
////
////                        fun isRemoteUri(uri: Uri): Boolean {
////                            val scheme = uri.scheme ?: return false
////                            return scheme.startsWith("http")
////                        }
////                        val videoParts = selectedVideos.mapNotNull { uri ->
////                            val mimeType = context.contentResolver.getType(uri) ?: "video/mp4"
////                           prepareMultipartBody(
////                                context,
////                                uri,
////                                "video",
////                                ".mp4",
////                                mimeType
////                            )
////                        }
////                        val photoParts = selectedImages.mapNotNull { uri ->
////                            val mimeType = context.contentResolver.getType(uri) ?: "image/jpeg"
////                         prepareMultipartBody(
////                                context,
////                                uri,
////                                "photo",
////                                ".jpg",
////                                mimeType
////                            )
////                        }
////                        // Gửi yêu cầu cập nhật
////                        postId?.let {
////                            viewModel.updatePost(
////                                postId = it,
////                                userId = userId,
////                                room_id = userId,
////                                building_id = userId,
////                                title = title,
////                                content = content,
////                                status = status,
////                                postType = postType,
////                                videos = videoParts,
////                                photos = photoParts
////                            )
////                        }
////                        navController.navigate("post_detail/$postId")
////                        {
////                            popUpTo("update_post_screen/$postId") { inclusive = true }
////                        }
//
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






@Composable
fun StaticComfortableOptions(
    amenities: List<String>, // Danh sách tiện ích từ dữ liệu
    allOptions: List<String> = listOf(
        "Vệ sinh khép kín",
        "Gác xép",
        "Ra vào vân tay",
        "Ban công",
        "Nuôi pet",
        "Không chung chủ"
    ),
    onSelectAmenity: (String) -> Unit // Thêm tham số cho sự kiện chọn
) {

    FlowRow(
        modifier = Modifier.padding(8.dp),
        mainAxisSpacing = 10.dp, // Khoảng cách giữa các phần tử trên cùng một hàng
        crossAxisSpacing = 10.dp // Khoảng cách giữa các hàng
    ) {
        allOptions.forEach { option ->
            StaticComfortableOption(
                text = option,
                isSelected = amenities.contains(option), // Hiển thị dấu tích nếu có trong danh sách tiện ích
                onClick = { onSelectAmenity(option) } // Khi nhấp vào, gọi onSelectAmenity

            )
        }

    }
}

@Composable
fun StaticComfortableOption(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit // Thêm sự kiện nhấp vào
) {

    Box(
        modifier = Modifier
            .clickable(onClick = onClick) // Tạo sự kiện nhấp
            .shadow(3.dp, shape = RoundedCornerShape(6.dp))
            .border(
                width = 1.dp,
                color = if (isSelected) Color(0xFF44acfe) else Color(0xFFeeeeee),
                shape = RoundedCornerShape(9.dp)
            )
            .background(color = Color.White, shape = RoundedCornerShape(6.dp))
            .padding(0.dp)
    ) {
        Text(
            text = text,
            color = if (isSelected) Color(0xFF44acfe) else Color(0xFF000000),
            fontSize = 13.sp,
            modifier = Modifier
                .background(color = if (isSelected) Color(0xFFffffff) else Color(0xFFeeeeee))
                .padding(14.dp)
                .align(Alignment.Center)
        )

        // Hiển thị dấu tích nếu được chọn
        if (isSelected) {
            Box(
                modifier = Modifier
                    .size(23.dp)
                    .align(Alignment.TopStart)
                    .background(
                        color = Color(0xFF44acfe),
                        shape = TriangleShape()
                    ),
                contentAlignment = Alignment.TopStart
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.tick),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(10.dp)
                        .offset(x = 3.dp, y = 2.dp)
                )
            }
        }
    }
}
@Composable
fun AmenitiesDisplay(
    amenities: List<String>,
    onAmenitiesChange: (List<String>) -> Unit
) {
    val context = LocalContext.current
    val selectedAmenities = remember(amenities) { mutableStateOf(amenities) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        StaticComfortableOptions(
            amenities = selectedAmenities.value,
            onSelectAmenity = { amenity ->
                val updatedAmenities = if (selectedAmenities.value.contains(amenity)) {
                    selectedAmenities.value - amenity
                } else {
                    selectedAmenities.value + amenity
                }
                if (updatedAmenities.isEmpty()) {
                    Toast.makeText(context, "Không được để trống tiện nghi", Toast.LENGTH_SHORT).show()
                    return@StaticComfortableOptions
                }
                selectedAmenities.value = updatedAmenities
                onAmenitiesChange(updatedAmenities) // Gửi danh sách mới qua callback
                Log.d("hamm", "Updated Amenities: $updatedAmenities")
            }
        )
    // Trạng thái danh sách tiện ích

    }
    }

////
///
@Composable
fun StaticServiceOptions(
    services: List<String>, // Danh sách tiện ích từ dữ liệu
    allOptions: List<String> = listOf( "Điều hoà","Kệ bếp","Tủ lạnh","Bình nóng lạnh","Máy giặt","Bàn ghế"),
    onSelectService: (String) -> Unit // Thêm tham số cho sự kiện chọn
) {

    FlowRow(
        modifier = Modifier.padding(8.dp),
        mainAxisSpacing = 10.dp, // Khoảng cách giữa các phần tử trên cùng một hàng
        crossAxisSpacing = 10.dp // Khoảng cách giữa các hàng
    ) {
        allOptions.forEach { option ->
            StaticComfortableOption(
                text = option,
                isSelected = services.contains(option), // Hiển thị dấu tích nếu có trong danh sách tiện ích
                onClick = { onSelectService(option) } // Khi nhấp vào, gọi onSelectAmenity

            )
        }

    }
}
@Composable
fun ServicesDisplay(
    services: List<String>,
    onServicesChange: (List<String>) -> Unit
) {
    val context = LocalContext.current
    val selectedServices = remember(services) { mutableStateOf(services) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        StaticServiceOptions(
            services = selectedServices.value,
            onSelectService = { amenity ->
                val updatedService = if (selectedServices.value.contains(amenity)) {
                    selectedServices.value - amenity
                } else {
                    selectedServices.value + amenity
                }
                if (updatedService.isEmpty()) {
                    Toast.makeText(context, "Không được để trống dịch vụ", Toast.LENGTH_SHORT)
                        .show()
                    return@StaticServiceOptions
                }
                selectedServices.value = updatedService
                onServicesChange(updatedService) // Gửi danh sách mới qua callback
                Log.d("hamm", "Updated Amenities: $updatedService")
            }
        )
        // Trạng thái danh sách tiện ích

    }

}
@Composable
fun StaticRoomTypeOptions(
    roomTypes: List<String>, // Danh sách tiện ích từ dữ liệu
    allOptions: List<String> = listOf(
        "Phòng trọ",
        "Nguyên căn",
        "Chung cư"
    ),
    onSelectRoomType: (String) -> Unit // Thêm tham số cho sự kiện chọn
) {

    FlowRow(
        modifier = Modifier.padding(8.dp),
        mainAxisSpacing = 10.dp, // Khoảng cách giữa các phần tử trên cùng một hàng
        crossAxisSpacing = 10.dp // Khoảng cách giữa các hàng
    ) {
        allOptions.forEach { option ->
            StaticRoomTypeOption(
                text = option,
                isSelected = roomTypes.contains(option), // Hiển thị dấu tích nếu có trong danh sách tiện ích
                onClick = { onSelectRoomType(option) } // Khi nhấp vào, gọi onSelectAmenity

            )
        }

    }
}
@Composable
fun StaticRoomTypeOption(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit // Thêm sự kiện nhấp vào
) {

    Box(
        modifier = Modifier
            .clickable(onClick = onClick) // Tạo sự kiện nhấp
            .shadow(3.dp, shape = RoundedCornerShape(6.dp))
            .border(
                width = 1.dp,
                color = if (isSelected) Color(0xFF44acfe) else Color(0xFFeeeeee),
                shape = RoundedCornerShape(9.dp)
            )
            .background(color = Color.White, shape = RoundedCornerShape(6.dp))
            .padding(0.dp)
    ) {
        Text(
            text = text,
            color = if (isSelected) Color(0xFF44acfe) else Color(0xFF000000),
            fontSize = 13.sp,
            modifier = Modifier
                .background(color = if (isSelected) Color(0xFFffffff) else Color(0xFFeeeeee))
                .padding(14.dp)
                .align(Alignment.Center)
        )

        // Hiển thị dấu tích nếu được chọn
        if (isSelected) {
            Box(
                modifier = Modifier
                    .size(23.dp)
                    .align(Alignment.TopStart)
                    .background(
                        color = Color(0xFF44acfe),
                        shape = TriangleShape()
                    ),
                contentAlignment = Alignment.TopStart
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.tick),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(10.dp)
                        .offset(x = 3.dp, y = 2.dp)
                )
            }
        }
    }
}
@Composable
fun RoomTypesDisplay(
    roomTypes: List<String>, // Danh sách các loại phòng (sẽ chỉ chứa tối đa 1 giá trị)
    onRoomTypeChange: (List<String>) -> Unit // Callback để cập nhật danh sách
) {
    val selectedRoomType = remember(roomTypes) { mutableStateOf(roomTypes.firstOrNull() ?: "") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        StaticRoomTypeOptions(
            roomTypes = listOf(selectedRoomType.value), // Chỉ truyền loại phòng đang được chọn
            onSelectRoomType = { roomType ->
                selectedRoomType.value = roomType // Cập nhật giá trị đã chọn
                onRoomTypeChange(listOf(roomType)) // Gửi danh sách mới chỉ chứa một giá trị
            }
        )
    }
}

// Custom Shape cho góc dấu tích
class TriangleShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            moveTo(0f, 0f)
            lineTo(size.width, 0f)
            lineTo(0f, size.height)
            close()
        }
        return Outline.Generic(path)
    }
}


@Composable
fun SelectMedia(
    onMediaSelected: (List<Uri>, List<Uri>) -> Unit,
    detail: PostingDetail // Truyền đối tượng detail chứa ảnh và video
) {
    val selectedImages = remember { mutableStateListOf<Uri>() }
    val selectedVideos = remember { mutableStateListOf<Uri>() }
//    val baseUrl = "http://192.168.2.106:3000/"
//
//// Chuyển đổi các đường dẫn ảnh và video từ detail thành Uri, thêm base URL vào trước mỗi đường dẫn
//    val imagesFromDetail = detail.photos?.map { Uri.parse( baseUrl+it) } ?: listOf()
//    val videosFromDetail = detail.videos?.map { Uri.parse(baseUrl+it) } ?: listOf()
//
//    // Gán giá trị ảnh và video từ detail vào selectedImages và selectedVideos
//    if (selectedImages.isEmpty()) {
//        selectedImages.addAll(imagesFromDetail)
//    }
//
//    if (selectedVideos.isEmpty()) {
//        selectedVideos.addAll(videosFromDetail)
//    }
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

        // Hiển thị ảnh đã chọn từ detail
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

        // Hiển thị video đã chọn từ detail
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
                            .clickable { selectedVideos.remove(uri) }, // Xóa video khi nhấn
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
        com.google.android.exoplayer2.ExoPlayer.Builder(context).build().apply {
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


