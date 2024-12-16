package com.rentify.user.app.view.staffScreens.addPostScreen


import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.MainActivity
import com.rentify.user.app.model.Model.NotificationRequest
import com.rentify.user.app.network.APIService
import com.rentify.user.app.network.RetrofitClient
import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.LoginRepository.LoginRepository
import com.rentify.user.app.view.staffScreens.UpdatePostScreen.Components.CustomTextField
import com.rentify.user.app.view.staffScreens.UpdatePostScreen.isFieldEmpty
import com.rentify.user.app.view.staffScreens.addPostScreen.Components.AppointmentAppBar

import com.rentify.user.app.view.staffScreens.addPostScreen.Components.BuildingLabel
import com.rentify.user.app.view.staffScreens.addPostScreen.Components.BuildingOptions
import com.rentify.user.app.view.staffScreens.addPostScreen.Components.RoomLabel
import com.rentify.user.app.view.staffScreens.addPostScreen.Components.RoomOptions
import com.rentify.user.app.view.userScreens.AddPostScreen.Components.SelectMedia
import com.rentify.user.app.viewModel.LoginViewModel
import com.rentify.user.app.viewModel.NotificationViewModel


import com.rentify.user.app.viewModel.PostViewModel.PostViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okio.Buffer
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPostScreens(
    navController: NavHostController,
    notificationViewModel: NotificationViewModel = viewModel()

) {
    var selectedImages by remember { mutableStateOf(emptyList<Uri>()) }
    var selectedVideos by remember { mutableStateOf(emptyList<Uri>()) }
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val apiService = RetrofitService()
    val userRepository = LoginRepository(apiService)
    val factory = remember(context) {
        LoginViewModel.LoginViewModelFactory(userRepository, context.applicationContext)
    }
    val loginViewModel: LoginViewModel = viewModel(factory = factory)
    val userId = loginViewModel.getUserData().userId

    val viewModel: PostViewModel = viewModel()
    val scrollState = rememberScrollState()
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    var selectedRoom by remember { mutableStateOf<String?>(null) }

    fun logRequestBody(requestBody: RequestBody) {
        val buffer = Buffer()
        try {
            requestBody.writeTo(buffer)
            val content = buffer.readUtf8()
            Log.d("click", "RequestBody content: $content")
        } catch (e: Exception) {
            Log.e("click", "Error reading RequestBody: ${e.message}")
        }
    }

    suspend fun addPost(
        context: Context,
        apiService: APIService,
        selectedImages: List<Uri>,
        selectedVideos: List<Uri>
    ): Boolean {
        // Chuẩn bị dữ liệu `RequestBody`
        val userId = userId.toRequestBody("text/plain".toMediaTypeOrNull())
        val buildingId = viewModel.selectedBuilding.value?.toRequestBody("text/plain".toMediaTypeOrNull())
            ?: "".toRequestBody("text/plain".toMediaTypeOrNull())

        val roomId  = selectedRoom?.toRequestBody("text/plain".toMediaTypeOrNull())
        val title = title.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val content = content.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val status = "0".toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val postType = "rent".toRequestBody("multipart/form-data".toMediaTypeOrNull())

        val videoPart = selectedVideos.mapNotNull { uri ->
            val mimeType = context.contentResolver.getType(uri) ?: "video/mp4"
            prepareMultipartBody(
                context,
                uri,
                "video",
                ".mp4",
                mimeType
            )
        }
        val photoPart = selectedImages.mapNotNull { uri ->
            val mimeType = context.contentResolver.getType(uri) ?: "image/jpeg"
            prepareMultipartBody(
                context,
                uri,
                "photo",
                ".jpg",
                mimeType
            )
        }


        // Gửi dữ liệu đến API
        return try {
            val response = apiService.addPost(
                userId = userId,
                buildingId = buildingId,
                roomId = roomId,
                title = title,
                content = content,
                postType = postType,
                status = status,
                videos = videoPart,
                photos =photoPart
            )
            if (response.isSuccessful) {
                Log.d("AddPost", "Dư liệu vừa thêm xong: ${response.body()}")
                true
            } else {
                Log.e("AddPost", "Error: ${response.errorBody()?.string()}")
                false
            }
        } catch (e: Exception) {
            Log.e("AddPost", "Exception: ${e.message}")
            false
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
                AppointmentAppBar( onBackClick = {
                    // Logic quay lại, ví dụ: điều hướng về màn hình trước
                    navController.navigate("POSTING_STAFF")
                    {
                        popUpTo("ADDPOST_staff") { inclusive = true }

                    }
                })

            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
                    .background(color = Color(0xfff7f7f7))
                    .padding(15.dp)
            ) {

//video
                SelectMedia { images, videos ->
                    selectedImages = images
                    selectedVideos = videos
                    Log.d("AddPost", "Received Images: $selectedImages")
                    Log.d("AddPost", "Received Videos: $selectedVideos")
                }
                // tiêu đề
                CustomTextField(
                    label = "Tiêu đề bài đằng",
                    value = title,
                    onValueChange = { title = it  },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    placeholder = "Nhập tiêu đề bài đăng",
                    isReadOnly = false
                )
                //  Nội dung
                CustomTextField(
                    label = "Nội dung",
                    value = content,
                    onValueChange = { content = it  },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    placeholder = "Nhập nội dung bài đăng",
                    isReadOnly = false
                )
                Spacer(modifier = Modifier.height(3.dp))
                Column {
                    BuildingLabel()

                    BuildingOptions(
                        userId = userId,
                        selectedBuilding = viewModel.selectedBuilding.value,
                        onBuildingSelected = { buildingId ->
                            viewModel.setSelectedBuilding(buildingId) // Cập nhật tòa nhà đã chọn
                        }
                    )
                }
                // dịch vụ
                Spacer(modifier = Modifier.height(10.dp))
                Column {
                    RoomLabel()
                    viewModel.selectedBuilding.value?.let { buildingId ->
                        RoomOptions(
                            buildingId = buildingId,
                            selectedRoom = selectedRoom,
                            onRoomSelected = { roomId ->
                                selectedRoom = roomId
                            }
                        )
                    } ?: run {
                        Text(
                            text = "Vui lòng chọn tòa nhà trước",
                            color = Color.Red,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
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
                        if (isFieldEmpty(title)) {
                            // Hiển thị thông báo lỗi nếu title trống
                            Toast.makeText(context, "Tiêu đề không thể trống", Toast.LENGTH_SHORT).show()
                            return@Button        }

                        if (selectedImages.isEmpty()) {
                            // Hiển thị thông báo nếu không có ảnh nào được chọn
                            Toast.makeText(context, "Ảnh không được để trống", Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        val maxPhotos = 10
                        val maxVideos = 3
                        if (selectedImages.size > maxPhotos) {
                            Toast.makeText(
                                context,
                                "Chỉ cho phép tối đa $maxPhotos ảnh!",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@Button
                        }

                        if (selectedVideos.size > maxVideos) {
                            Toast.makeText(
                                context,
                                "Chỉ cho phép tối đa $maxVideos video!",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@Button
                        }
                        if (viewModel.selectedBuilding.value.isNullOrBlank()) {
                            Toast.makeText(context, "Vui lòng chọn tòa", Toast.LENGTH_SHORT).show()
                            return@Button
                        }


                        if (selectedRoom.isNullOrEmpty()) {
                            Toast.makeText(context, "Vui lòng chọn phòng", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        if (isFieldEmpty(content)) {
                            // Hiển thị thông báo lỗi nếu content trống
                            Toast.makeText(context, "Nội dung không thể trống", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        CoroutineScope(Dispatchers.Main).launch {
                            val apiService = RetrofitClient.apiService
                            val isSuccessful = withContext(Dispatchers.IO) {
                                addPost(context, apiService, selectedImages, selectedVideos)
                            }

                            if (isSuccessful) {
                                val formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy")
                                val currentTime = LocalDateTime.now().format(formatter)

                                val notificationRequest = NotificationRequest(
                                    user_id = userId,
                                    title = "Thêm bài đăng thành công",
                                    content = "${title} đã được thêm thành công lúc: $currentTime",
                                )

                                notificationViewModel.createNotification(notificationRequest)

                                Toast.makeText(context, "Tạo bài đăng thành công!", Toast.LENGTH_SHORT).show()
                                navController.navigate(MainActivity.ROUTER.POSTING_STAFF.name)
                            } else {
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