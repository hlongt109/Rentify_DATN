package com.rentify.user.app.view.staffScreens.addPostScreen


import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.network.APIService
import com.rentify.user.app.network.RetrofitClient
import com.rentify.user.app.view.staffScreens.addPostScreen.Components.SelectMedia

import com.rentify.user.app.view.userScreens.AddPostScreen.Components.ComfortableLabel
import com.rentify.user.app.view.userScreens.AddPostScreen.Components.ComfortableOptions
import com.rentify.user.app.view.userScreens.AddPostScreen.Components.RoomTypeLabel
import com.rentify.user.app.view.userScreens.AddPostScreen.Components.RoomTypeOptions
import com.rentify.user.app.view.userScreens.AddPostScreen.Components.ServiceLabel
import com.rentify.user.app.view.userScreens.AddPostScreen.Components.ServiceOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
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




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPostScreens(navController: NavHostController) {
    var selectedImages by remember { mutableStateOf(emptyList<Uri>()) }
    var selectedVideos by remember { mutableStateOf(emptyList<Uri>()) }
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    var selectedRoomTypes by remember { mutableStateOf(listOf<String>()) }
    var selectedComfortable by remember { mutableStateOf(listOf<String>()) }
    var selectedService by remember { mutableStateOf(listOf<String>()) }
    val context = LocalContext.current
    val scrollState = rememberScrollState()
   var address by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
     var content by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var roomPrice by remember { mutableStateOf("") }

    suspend fun addPost(
        context: Context,
        apiService: APIService,
        selectedImages: List<Uri>,
        selectedVideos: List<Uri>
    ): Boolean {
        val userId = "672490e5ce87343d0e701012".toRequestBody("text/plain".toMediaTypeOrNull())
        val title = title.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val content = content.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val status = "0".toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val postType = "rent".toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val price = roomPrice.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val address = address.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val phoneNumber = phoneNumber.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val roomType = selectedRoomTypes.joinToString(",").toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val amenities = selectedComfortable.joinToString(",").toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val services = selectedService.joinToString(",").toRequestBody("multipart/form-data".toMediaTypeOrNull())

        val videoParts = selectedVideos.mapNotNull { uri ->
            val mimeType = context.contentResolver.getType(uri) ?: "video/mp4"
            prepareMultipartBody(context, uri, "video", ".mp4", mimeType)
        }
        val photoParts = selectedImages.mapNotNull { uri ->
            val mimeType = context.contentResolver.getType(uri) ?: "image/jpeg"
            prepareMultipartBody(context, uri, "photo", ".jpg", mimeType)
        }

        return try {
            val response = apiService.addPost(
                userId, title, content, status, postType, price, address, phoneNumber,
                roomType, amenities, services, videos = videoParts, photos = photoParts
            )
            if (response.isSuccessful) {
                Log.d("addPost", "Post created successfully: ${response.body()}")
                true // Thành công
            } else {
                Log.e("addPost", "Error: ${response.errorBody()?.string()}")
                false // Thất bại
            }
        } catch (e: Exception) {
            Log.e("addPost", "Exception: ${e.message}")
            false // Thất bại
        }
    }

        Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xfff7f7f7))
    ) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .background(color = Color(0xfff7f7f7))
            .padding(bottom = screenHeight.dp/7f)

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xffffffff))
                .padding(10.dp)

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()

                    .background(color = Color(0xffffffff)), // Để IconButton nằm bên trái
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = {   navController.navigate("POSTING_STAFF")}) {
                    Image(
                        painter = painterResource(id = R.drawable.back),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp, 30.dp)
                    )
                }
                Text(
                    text = "Thêm bài đăng",
                    //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                    color = Color.Black,
                    fontWeight = FontWeight(700),
                    fontSize = 17.sp,
                    )
                IconButton(onClick = { /*TODO*/ }) {
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .background(color = Color(0xfff7f7f7))
                .padding(15.dp)
        ) {
            // tiêu đề
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                Row {
                    Text(
                        text = "Tiêu đề bài đằng",
                        //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                        color = Color(0xff7f7f7f),
                        // fontWeight = FontWeight(700),
                        fontSize = 13.sp,
                    )
                    Text(

                        text = " *",
                        //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                        color = Color(0xffff1a1a),
                        // fontWeight = FontWeight(700),
                        fontSize = 16.sp,

                        )
                }
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(53.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color(0xFFcecece),
                        unfocusedIndicatorColor = Color(0xFFcecece),
                        focusedPlaceholderColor = Color.Black,
                        unfocusedPlaceholderColor = Color.Gray,
                        unfocusedContainerColor = Color(0xFFf7f7f7),
                        focusedContainerColor = Color(0xFFf7f7f7),
                    ),
                    placeholder = {
                        Text(
                            text = "Nhập tiêu đề bài đăng",
                            fontSize = 13.sp,
                            color = Color(0xFF898888),
                            fontFamily = FontFamily(Font(R.font.cairo_regular))
                        )
                    },
                    shape = RoundedCornerShape(size = 8.dp),
                    textStyle = TextStyle(
                        color = Color.Black, fontFamily = FontFamily(Font(R.font.cairo_regular))
                    )
                )
            }
            // loai phòng
            Column {
                RoomTypeLabel()

                RoomTypeOptions(
                    selectedRoomTypes = selectedRoomTypes,
                    onRoomTypeSelected = { roomType ->
                        selectedRoomTypes = if (selectedRoomTypes.contains(roomType)) {
                            selectedRoomTypes - roomType
                        } else {
                            selectedRoomTypes + roomType
                        }
                    }
                )
            }
//video
            SelectMedia { images, videos ->
                selectedImages = images
                selectedVideos = videos
                Log.d("AddPost", "Received Images: $selectedImages")
                Log.d("AddPost", "Received Videos: $selectedVideos")
            }
            // gía phòng
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                Row {
                    Text(
                        text = "Giá Phòng",
                        //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                        color = Color(0xff7f7f7f),
                        // fontWeight = FontWeight(700),
                        fontSize = 13.sp,
                    )
                    Text(

                        text = " *",
                        //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                        color = Color(0xffff1a1a),
                        // fontWeight = FontWeight(700),
                        fontSize = 16.sp,

                        )
                }
                TextField(
                    value = roomPrice,
                    onValueChange = { roomPrice = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(53.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color(0xFFcecece),
                        unfocusedIndicatorColor = Color(0xFFcecece),
                        focusedPlaceholderColor = Color.Black,
                        unfocusedPlaceholderColor = Color.Gray,
                        unfocusedContainerColor = Color(0xFFf7f7f7),
                        focusedContainerColor = Color(0xFFf7f7f7),
                    ),
                    placeholder = {
                        Text(
                            text = "Nhập giá phòng",
                            fontSize = 13.sp,
                            color = Color(0xFF898888),
                            fontFamily = FontFamily(Font(R.font.cairo_regular))
                        )
                    },
                    shape = RoundedCornerShape(size = 8.dp),
                    textStyle = TextStyle(
                        color = Color.Black, fontFamily = FontFamily(Font(R.font.cairo_regular))
                    )
                )
            }
            //  Nội dung
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                Row {
                    Text(
                        text = "Nội dung",
                        //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                        color = Color(0xff7f7f7f),
                        // fontWeight = FontWeight(700),
                        fontSize = 13.sp,
                    )
                    Text(
                        text = " *",
                        //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                        color = Color(0xffff1a1a),
                        // fontWeight = FontWeight(700),
                        fontSize = 16.sp,

                        )
                }
                TextField(
                    value = content,
                    onValueChange = { content = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(53.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color(0xFFcecece),
                        unfocusedIndicatorColor = Color(0xFFcecece),
                        focusedPlaceholderColor = Color.Black,
                        unfocusedPlaceholderColor = Color.Gray,
                        unfocusedContainerColor = Color(0xFFf7f7f7),
                        focusedContainerColor = Color(0xFFf7f7f7),
                    ),
                    placeholder = {
                        Text(
                            text = "Nhập nội dung",
                            fontSize = 13.sp,
                            color = Color(0xFF898888),
                            fontFamily = FontFamily(Font(R.font.cairo_regular))
                        )
                    },
                    shape = RoundedCornerShape(size = 8.dp),
                    textStyle = TextStyle(
                        color = Color.Black, fontFamily = FontFamily(Font(R.font.cairo_regular))
                    )
                )
            }
            // dịa chỉ
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                Row {
                    Text(
                        text = "Địa chỉ",
                        color = Color(0xff7f7f7f),
                        fontSize = 13.sp,
                    )
                    Text(
                        text = " *",
                        color = Color(0xffff1a1a),
                        fontSize = 16.sp,

                        )
                }
                TextField(
                    value = address,
                    onValueChange = { address = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(53.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color(0xFFcecece),
                        unfocusedIndicatorColor = Color(0xFFcecece),
                        focusedPlaceholderColor = Color.Black,
                        unfocusedPlaceholderColor = Color.Gray,
                        unfocusedContainerColor = Color(0xFFf7f7f7),
                        focusedContainerColor = Color(0xFFf7f7f7),
                    ),
                    placeholder = {
                        Text(
                            text = "Nhập địa chỉ *",
                            fontSize = 13.sp,
                            color = Color(0xFF898888),
                            fontFamily = FontFamily(Font(R.font.cairo_regular))
                        )
                    },
                    shape = RoundedCornerShape(size = 8.dp),
                    textStyle = TextStyle(
                        color = Color.Black, fontFamily = FontFamily(Font(R.font.cairo_regular))
                    )
                )
            }
            // sóo điện thoại
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                Row {
                    Text(
                        text = "Số điện thoại",
                        //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                        color = Color(0xff7f7f7f),
                        // fontWeight = FontWeight(700),
                        fontSize = 13.sp,
                    )
                    Text(

                        text = " *",
                        //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                        color = Color(0xffff1a1a),
                        // fontWeight = FontWeight(700),
                        fontSize = 16.sp,

                        )
                }
                TextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(53.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color(0xFFcecece),
                        unfocusedIndicatorColor = Color(0xFFcecece),
                        focusedPlaceholderColor = Color.Black,
                        unfocusedPlaceholderColor = Color.Gray,
                        unfocusedContainerColor = Color(0xFFf7f7f7),
                        focusedContainerColor = Color(0xFFf7f7f7),
                    ),
                    placeholder = {
                        Text(
                            text = "Nhập địa chỉ *",
                            fontSize = 13.sp,
                            color = Color(0xFF898888),
                            fontFamily = FontFamily(Font(R.font.cairo_regular))
                        )
                    },
                    shape = RoundedCornerShape(size = 8.dp),
                    textStyle = TextStyle(
                        color = Color.Black, fontFamily = FontFamily(Font(R.font.cairo_regular))
                    )
                )
            }
            Spacer(modifier = Modifier.height(3.dp))
            Column {
                ComfortableLabel()

                ComfortableOptions(
                    selectedComfortable = selectedComfortable,
                    onComfortableSelected = { comfortable ->
                        selectedComfortable = if (selectedComfortable.contains(comfortable)) {
                            selectedComfortable - comfortable
                        } else {
                            selectedComfortable + comfortable
                        }
                    }
                )
            }
            // dịch vụ
            Spacer(modifier = Modifier.height(10.dp))
            Column {
            ServiceLabel()
            ServiceOptions(
                selectedService = selectedService,
                onServiceSelected = { service ->
                    selectedService = if (selectedService.contains(service)) {
                        selectedService - service
                    } else {
                        selectedService + service
                    }
                }
            )
        }

        }
    }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(screenHeight.dp/7f)
                .background(color = Color.White)
        ) {
            Box(modifier = Modifier.padding(20.dp)) {
                Button(
                    onClick = {
                        CoroutineScope(Dispatchers.Main).launch {
                            val apiService = RetrofitClient.apiService
                            val isSuccessful = withContext(Dispatchers.IO) {
                                addPost(context, apiService, selectedImages, selectedVideos)
                            }

                            if (isSuccessful) {
                                // Chuyển màn khi bài đăng được tạo thành công
                                navController.navigate("POSTING_STAFF")
                            } else {
                                // Hiển thị thông báo lỗi nếu tạo bài thất bại
                                Toast.makeText(context, "Failed to create post", Toast.LENGTH_SHORT).show()
                            }
                        }

                    }, modifier = Modifier.height(50.dp).fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp), colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xff5dadff)
                    )
                ) {
                    Text(
                        text = "Thêm bài đăng",
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.cairo_regular)),
                        color = Color(0xffffffff)
                    )
                }

            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingLayoutAddPostScreen() {
    AddPostScreens(navController = rememberNavController())
}



