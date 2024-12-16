package com.rentify.user.app.view.staffScreens.addContractScreen


import DatePickerField
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.rentify.user.app.model.Model.NotificationRequest
import com.rentify.user.app.model.User
import com.rentify.user.app.network.APIService
import com.rentify.user.app.network.RetrofitClient
import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.LoginRepository.LoginRepository
import com.rentify.user.app.view.staffScreens.UpdatePostScreen.Components.CustomTextField
import com.rentify.user.app.view.staffScreens.UpdatePostScreen.isFieldEmpty
import com.rentify.user.app.view.staffScreens.addContractScreen.Components.AppointmentAppBar
import com.rentify.user.app.view.staffScreens.addContractScreen.Components.BuildingOptions
import com.rentify.user.app.view.staffScreens.addContractScreen.Components.RoomOptions
import com.rentify.user.app.view.staffScreens.addContractScreen.Components.SelectMedia

import com.rentify.user.app.view.staffScreens.addPostScreen.Components.BuildingLabel

import com.rentify.user.app.view.staffScreens.addPostScreen.Components.RoomLabel
import com.rentify.user.app.view.userScreens.messengerScreen.components.UserItem

import com.rentify.user.app.viewModel.LoginViewModel
import com.rentify.user.app.viewModel.NotificationViewModel


import com.rentify.user.app.viewModel.PostViewModel.PostViewModel
import com.rentify.user.app.viewModel.StaffViewModel.ContractViewModel
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
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

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
fun validateUserIds(userIds: List<String>): Boolean {
    for (userId in userIds) {
        if (userId.length != 24) {
            // Nếu có userId không đủ 24 ký tự
            return false // Trả về false khi không hợp lệ
        }
    }
    return true // Trả về true nếu tất cả userId đều hợp lệ
}
// Hiển thị một người dùng trong danh sách
@Composable
fun UserItem(user: User, onDelete: () -> Unit) {
    val imageUrl = "${user.profile_picture_url}"
    Log.d("UserItem", "Image URL: $imageUrl")
    val cardWidth = if (user.profile_picture_url.isNullOrEmpty()) 120.dp else 150.dp

    Card(
        modifier = Modifier
            ,shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier  .background(color =Color.White),   horizontalArrangement = Arrangement.Center,verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.width(5.dp))
            // Hiển thị hình ảnh người dùng nếu có
            user.profile_picture_url?.let {
                Image(
                    painter = rememberImagePainter(data = user.profile_picture_url),
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.width(5.dp))
            // Hiển thị tên người dùng
            Text(
                text = user.name ?: "Tên không có",

                textAlign = TextAlign.Center
            )
            // Nút xóa
            IconButton(
                onClick = onDelete,
            ) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
            }
            Spacer(modifier = Modifier.width(5.dp))
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddContractScreens(
    navController: NavHostController,
    notificationViewModel: NotificationViewModel = viewModel()

) {
    var selectedImages by remember { mutableStateOf(emptyList<Uri>()) }
    var isLoading by remember { mutableStateOf(false) }
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd") // Định dạng ngày tháng bạn sử dụng
    val viewModel: ContractViewModel = viewModel()
    val scrollState = rememberScrollState()
    var content by remember { mutableStateOf("") }
    var userId by remember { mutableStateOf("") }
    var selectedBuilding by remember { mutableStateOf<String?>(null) }
    var selectedRoom by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current
    val apiService = RetrofitService()
    val userRepository = LoginRepository(apiService)
    val factory = remember(context) {
        LoginViewModel.LoginViewModelFactory(userRepository, context.applicationContext)
    }

    val loginViewModel: LoginViewModel = viewModel(factory = factory)
    var manage_Id = loginViewModel.getUserData().userId
    var userList by remember { mutableStateOf<List<User>>(emptyList()) }
    val errorMessage by viewModel.error.observeAsState("")
    var isFetching by remember { mutableStateOf(false) }
    val userDetail by viewModel.userDetail.collectAsState()
    val userIds = userList.joinToString(",") { it._id }
    fun logRequestBody(requestBody: RequestBody) {
        val buffer = Buffer()
        try {
            requestBody.writeTo(buffer)
            val content = buffer.readUtf8()
            Log.d("click", "RequestBody content: $content")
        } catch (e: Exception) {
            Log.e("click", "Error reading ReuestBody: ${e.message}")
        }
    }

    suspend fun addContract(
        context: Context,
        apiService: APIService,
        selectedImages: List<Uri>,
    ): Boolean {
        // Chuẩn bị dữ liệu `RequestBody`
        val userId = userIds.toRequestBody("text/plain".toMediaTypeOrNull())
        val buildingId = selectedBuilding?.toRequestBody("text/plain".toMediaTypeOrNull())
            ?: "".toRequestBody("text/plain".toMediaTypeOrNull())
        val manageId = manage_Id.toRequestBody("text/plain".toMediaTypeOrNull())
        val roomId  = selectedRoom?.toRequestBody("text/plain".toMediaTypeOrNull())
        val content = content.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val status = "0".toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val startDate = startDate.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val endDate  = endDate.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        logRequestBody(userId)
        logRequestBody(buildingId)
        logRequestBody(manageId)
        if (roomId != null) {
            logRequestBody(roomId)
        }
        logRequestBody(content)
        logRequestBody(startDate)
        logRequestBody(endDate)
        val photoPart = selectedImages.mapNotNull { uri ->
            val mimeType = context.contentResolver.getType(uri) ?: "image/jpeg"
            prepareMultipartBody(
                context,
                uri,
                "photos_contract",
                ".jpg",
                mimeType
            )
        }


        // Gửi dữ liệu đến API
        return try {
            val response = apiService.addContract(
                userId = userId,
                buildingId = buildingId,
                roomId = roomId,
                manageId = manageId,
                content = content,
                startDate = startDate,
                status = status,
                endDate=endDate,
                photosContract  = photoPart
            )
            if (response.isSuccessful) {
                Log.d("AddContrac", "Dư liệu vừa thêm xong: ${response.body()}")
                true
            } else {
                Log.e("AddContrac", "Error: ${response.errorBody()?.string()}")
                false
            }
        } catch (e: Exception) {
            Log.e("AddContrac", "Exception: ${e.message}")
            false
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .background(color = Color(0xfff7f7f7))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xfff7f7f7))
                .padding(bottom = screenHeight.dp/7f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color(0xffffffff))
                    .padding(horizontal = 10.dp)

            ) {
                AppointmentAppBar( onBackClick = {
                    // Logic quay lại, ví dụ: điều hướng về màn hình trước
                    navController.navigate("CONTRACT_STAFF")
                    {
                        popUpTo("ADDCONTRAC_STAFF") { inclusive = true }

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
                SelectMedia { images ->
                    selectedImages = images
                    Log.d("AddPost", "Received Images: $selectedImages")

                }
                LaunchedEffect(userDetail) {
                    userDetail?.let {
                        userList = userList + it // Thêm người dùng vào danh sách
                        isFetching = false // Set lại trạng thái fetch
                    }
                }

                // Xử lý lỗi
                LaunchedEffect(errorMessage) {
                    errorMessage?.let {
                        isFetching = false
                        // Hiển thị lỗi nếu có
                    }
                }
                Spacer(modifier = Modifier.padding(5.dp))
                Row(horizontalArrangement = Arrangement.Center) {
                    CustomTextField(
                        label = "Nhập userId",
                        value = userId,
                        onValueChange = { userId = it  },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(8f) // Chiếm 8 phần
                            .padding(horizontal = 10.dp),
                        placeholder = "Nhập userId người dùng",
                        isReadOnly = false
                    )

                    Button(
                        onClick = {
                            // Kiểm tra nếu userId đã có trong danh sách
                            if (userList.any { it._id == userId }) {
                                // Hiển thị thông báo nếu userId đã có
                                Toast.makeText(context, "Người dùng đã có trong danh sách", Toast.LENGTH_SHORT).show()
                                return@Button
                            }
                            if (userId.length != 24) {
                                Toast.makeText(context, "UserId phải có đúng 24 ký tự", Toast.LENGTH_SHORT).show()
                                return@Button
                            }

                            isFetching = true
                            viewModel.fetchUserById(userId)
                            userId = ""
                        },
                        colors = ButtonDefaults.buttonColors(Color(0xff209FA8)),
                        enabled = userId.isNotEmpty() && !isFetching, // Enable button only when userId is not empty and fetching is not in progress
                        modifier = Modifier .padding(top = 28.dp).height(53.dp),// Chiếm 2 phần
                                shape = RoundedCornerShape(8.dp),
                    ) {
                        Text("Kiểm tra")
                    }
                }
                Spacer(modifier = Modifier.padding(5.dp))
                if (userList.isNotEmpty()) {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, start = 8.dp, bottom = 10.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(userList) { user ->
                            UserItem(user, onDelete = {
                                userList = userList.filterNot { it._id == user._id }
                            })
                        }
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
                //  Nội dung
                CustomTextField(
                    label = "Nội dung",
                    value = content,
                    onValueChange = { content = it  },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    placeholder = "Nhập nội dung",
                    isReadOnly = false
                )
                Spacer(modifier = Modifier.height(3.dp))
                Column(modifier = Modifier.fillMaxWidth().padding(10.dp)) {
                    Text(
                        text =  "Chọn ngày bắt đầu",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    Spacer(modifier = Modifier.height(5.dp))

                    // Trường chọn ngày bắt đầu
                    DatePickerField(
                        selectedDate = startDate,
                        onDateSelected = { startDate = it },
                        placeholder ="Chọn ngày bắt đầu"
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text =  "Chọn ngày kết thúc",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    Spacer(modifier = Modifier.height(5.dp))

                    // Trường chọn ngày kết thúc
                    DatePickerField(

                        selectedDate = endDate,
                        onDateSelected = { endDate = it },
                        placeholder ="Chọn ngày kết thúc"
                    )
                }
                Column {
                    BuildingLabel()

                    BuildingOptions(
                        manageId = manage_Id,
                        selectedBuilding = selectedBuilding,
                        onBuildingSelected = { buildingId ->
                            selectedBuilding =buildingId // Cập nhật tòa nhà đã chọn
                        }
                    )
                }
                // dịch vụ
                Spacer(modifier = Modifier.height(10.dp))
                Column {
                    RoomLabel()

                    if (selectedBuilding != null) {
                        RoomOptions(
                            buildingId = selectedBuilding!!, // Đảm bảo không null vì đã kiểm tra ở trên
                            selectedRoom = selectedRoom,
                            onRoomSelected = { roomId ->
                                selectedRoom = roomId
                            }
                        )
                    } else {
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
                        if (isFieldEmpty(userIds)) {
                            // Hiển thị thông báo lỗi nếu title trống
                            Toast.makeText(context, "Userid không thể trống", Toast.LENGTH_SHORT).show()
                            return@Button        }
                        val userIdsList = userIds.split(",").map { it.trim() }
                        val isValid = validateUserIds(userIdsList)
                        if (!isValid) {
                            Toast.makeText(context, "Một hoặc nhiều userId không hợp lệ, mỗi userId phải có đủ 24 ký tự!", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        if (selectedImages.isEmpty()) {
                            // Hiển thị thông báo nếu không có ảnh nào được chọn
                            Toast.makeText(context, "Ảnh không thể để trống", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        if (isFieldEmpty(startDate) || isFieldEmpty(endDate)) {
                            Toast.makeText(context, "Ngày bắt đầu và ngày kết thúc không thể trống", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        val startLocalDate = LocalDate.parse(startDate, dateFormatter)
                        val endLocalDate = LocalDate.parse(endDate, dateFormatter)

                        // Tính số tháng giữa 2 ngày
                        val monthsBetween = ChronoUnit.MONTHS.between(startLocalDate, endLocalDate)

                        // Kiểm tra nếu khoảng cách ít hơn 1 tháng
                        if (monthsBetween < 1) {
                            Toast.makeText(context, "Ngày kết thúc phải cách ngày bắt đầu ít nhất 1 tháng!", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        if (selectedBuilding.isNullOrEmpty()) {
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
                        val maxPhotos = 10

                        if (selectedImages.size > maxPhotos) {
                            Toast.makeText(
                                context,
                                "Chỉ cho phép tối đa $maxPhotos ảnh!",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@Button
                        }
                        isLoading = true
                        CoroutineScope(Dispatchers.Main).launch {
                            val apiService = RetrofitClient.apiService
                            val isSuccessful = withContext(Dispatchers.IO) {
                                isLoading = false // Ẩn loading khi hoàn tất
                                addContract(context, apiService, selectedImages)

                            }

                            if (isSuccessful) {
                                Toast.makeText(context, "Tạo hợp đồng thành công!", Toast.LENGTH_SHORT).show()
                                val formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy")
                                val currentTime = LocalDateTime.now().format(formatter)

                                val notificationRequest = NotificationRequest(
                                    user_id = userId,
                                    title = "Thêm hợp đồng thành công",
                                    content = "Hợp đồng đã được thêm thành công lúc: $currentTime",
                                )

                                val notificationRequest1 = NotificationRequest(
                                    user_id = manage_Id,
                                    title = "Thêm hợp đồng thành công",
                                    content = "Hợp đồng đã được thêm thành công lúc: $currentTime",
                                )
                                notificationViewModel.createNotification(notificationRequest)
                                notificationViewModel.createNotification(notificationRequest1)

                                // Chuyển màn khi bài đăng được tạo thành công
                                navController.navigate("CONTRACT_STAFF")
                            } else {
                                // Hiển thị thông báo lỗi nếu tạo bài thất bại
                                Toast.makeText(context, "Tạo hợp đồng thất bại. Vui lòng thử lại.", Toast.LENGTH_SHORT).show()
                            }
                        }

                    }, modifier = Modifier.height(50.dp).fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp), colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xff5dadff)
                    )
                ) {
                    Text(
                        text = "Thêm hợp đồng",
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.cairo_regular)),
                        color = Color(0xffffffff)
                    )
                }

            }
        }
    }
}




