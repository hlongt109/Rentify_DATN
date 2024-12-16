package com.rentify.user.app.view.staffScreens.UpdatePostScreen

import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.FrameLayout
import android.widget.ListPopupWindow.MATCH_PARENT
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import coil.compose.rememberImagePainter
import com.google.accompanist.flowlayout.FlowRow
//import com.google.android.exoplayer2.MediaItem
//import com.google.android.exoplayer2.ui.PlayerView
import androidx.compose.material.CircularProgressIndicator
import com.rentify.user.app.MainActivity
import com.rentify.user.app.model.Model.NotificationRequest

import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.LoginRepository.LoginRepository
import com.rentify.user.app.view.staffScreens.UpdatePostScreen.Components.AppointmentAppBar
import com.rentify.user.app.view.staffScreens.UpdatePostScreen.Components.ComfortableLabel
import com.rentify.user.app.view.staffScreens.UpdatePostScreen.Components.CustomTextField
import com.rentify.user.app.view.staffScreens.UpdatePostScreen.Components.ServiceLabel
import com.rentify.user.app.view.staffScreens.UpdatePostScreen.Components.TriangleShape

import com.rentify.user.app.view.staffScreens.postingList.PostingListComponents.PostingList
import com.rentify.user.app.view.userScreens.AddPostScreen.Components.VideoThumbnail
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
import okio.Buffer
import java.io.File
import java.io.IOException
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
fun isFieldEmpty(field: String): Boolean {
    return field.isBlank() // Kiểm tra trường có trống không
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePostScreen(
    navController: NavHostController,
    postId: String,
    notificationViewModel: NotificationViewModel = viewModel()
) {
    var selectedImages by remember { mutableStateOf(emptyList<Uri>()) }
    var selectedVideos by remember { mutableStateOf(emptyList<Uri>()) }
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    var selectedBuilding by remember { mutableStateOf<String?>(null) }
    var selectedBuilding1 by remember { mutableStateOf<String?>(null) }
    var selectedRoom by remember { mutableStateOf<String?>(null) }
    var selectedRoom1 by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    var title by remember { mutableStateOf("") }
    var isEdited by remember { mutableStateOf(false) }
    var content by remember { mutableStateOf("") }
    val postId = navController.currentBackStackEntry?.arguments?.getString("postId")
    val viewModel: PostViewModel = viewModel()
    val buildingId = viewModel.selectedBuilding.value
    val postDetail by viewModel.postDetail.observeAsState()
    val apiService = RetrofitService()
    val userRepository = LoginRepository(apiService)
    val factory = remember(context) {
        LoginViewModel.LoginViewModelFactory(userRepository, context.applicationContext)
    }
    val loginViewModel: LoginViewModel = viewModel(factory = factory)
    var userId = loginViewModel.getUserData().userId

    LaunchedEffect(postId) {
        postId?.let {
            viewModel.getPostDetail(it)
        }
    }

    postDetail?.let { detail ->
        // Gán giá trị cũ từ postDetail nếu chưa chỉnh sửa
        if (!isEdited) {
            title = detail.title ?: ""
            content = detail.content ?: ""
        }

        // Gán thông tin tòa nhà nếu có
        selectedBuilding = detail.building?._id

        // Gán thông tin phòng nếu có
        selectedRoom = detail.room?._id ?: ""
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
                .padding(bottom = screenHeight.dp / 7f)

        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color(0xffffffff))
            ) {
                AppointmentAppBar( onBackClick = {
                    // Logic quay lại, ví dụ: điều hướng về màn hình trước
                    navController.navigate("POSTING_STAFF")
                    {
                    //    popUpTo("ADDCONTRAC_STAFF") { inclusive = true }

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
                // tiêu đề


                postDetail?.let {
                    SelectMedia(
                        onMediaSelected = { images, videos ->
                            selectedImages = images
                            selectedVideos = videos


                        },
                        detail = it
                    )
                }
                ///
                // tieeu de
                CustomTextField(
                    label = "Tiêu đề bài đằng",
                    value = title,
                    onValueChange = { newValue ->
                        title = newValue // Cập nhật giá trị title khi người dùng thay đổi
                        isEdited = true  },
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
                    onValueChange = { newValue ->
                        content = newValue // Cập nhật giá trị title khi người dùng thay đổi
                        isEdited = true  },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    placeholder = "Nhập nội dung",
                    isReadOnly = false
                )
                Spacer(modifier = Modifier.height(3.dp))
                Column {
                    ComfortableLabel()
                    BuildingOptions(
                        userId = userId,
                        selectedBuilding = selectedBuilding, // Truyền giá trị ban đầu
                        onBuildingSelected = { selectedBuilding ->
                            viewModel.setSelectedBuilding(selectedBuilding) // Cập nhật tòa nhà đã chọn
                            Log.d("toa nha looo", "Updated selected building: $selectedBuilding1")
                            //  onBuildingUpdated(buildingId) // Gửi callback ra ngoài
                        }
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Column {
                    ServiceLabel()
                    if (!selectedBuilding.isNullOrEmpty()) {
                        RoomOptions(
                            buildingId = selectedBuilding!!, // Truyền tòa nhà đã chọn
                            selectedRoom = selectedRoom, // Truyền giá trị phòng đã chọn
                            onRoomSelected = { roomId ->
                                selectedRoom1 = roomId // Cập nhật phòng đã chọn
                                Log.d("room toa nha", "Updated selected room: $selectedRoom")
                            }
                        )
                    } else {
                        // Hiển thị thông báo nếu buildingId là null hoặc rỗng
                        Text(text = "Vui lòng chọn tòa nhà", color = Color.Gray)
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(screenHeight.dp / 7f)
                .background(color = Color(0xfff7f7f7))
        ) {
            Box(modifier = Modifier.padding(20.dp)) {
                Button(
                    onClick = {
                        if (isFieldEmpty(title)) {
                            // Hiển thị thông báo lỗi nếu title trống
                            Toast.makeText(context, "Tiêu đề không thể trống", Toast.LENGTH_SHORT).show()
                            return@Button        }
                        if (isFieldEmpty(content)) {
                            // Hiển thị thông báo lỗi nếu content trống
                            Toast.makeText(context, "Nội dung không thể trống", Toast.LENGTH_SHORT).show()
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
                        val videoParts = selectedVideos.mapNotNull { uri ->
                            val mimeType = context.contentResolver.getType(uri) ?: "video/mp4"
                            prepareMultipartBody(
                                context,
                                uri,
                                "video",
                                ".mp4",
                                mimeType
                            )
                        }
                        val photoParts = selectedImages.mapNotNull { uri ->
                            val mimeType = context.contentResolver.getType(uri) ?: "image/jpeg"
                            prepareMultipartBody(
                                context,
                                uri,
                                "photo",
                                ".jpg",
                                mimeType
                            )
                        }

                        postId?.let {
                            isLoading = true // Bắt đầu loading
                            CoroutineScope(Dispatchers.Main).launch {
                                try {
                                    // Cập nhật bài đăng
                                    viewModel.updatePost(
                                        postId = postId,
                                        userId = userId,
                                        buildingId = buildingId,
                                        roomId = selectedRoom1,
                                        title = title,
                                        address = "",
                                        content = content,
                                        status = "0",
                                        postType = "rent",
                                        videoFile = videoParts,
                                        photoFile = photoParts
                                    )
                                    Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show()

                                    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy")
                                    val currentTime = LocalDateTime.now().format(formatter)

                                    val notificationRequest = NotificationRequest(
                                        user_id = userId,
                                        title = "Chỉnh sửa bài đăng thành công",
                                        content = "${title} đã được chỉnh sửa thành công lúc: $currentTime",
                                    )

                                    notificationViewModel.createNotification(notificationRequest)

                                    viewModel.getPostDetail(postId)
                                    navController.navigate(MainActivity.ROUTER.POSTING_STAFF.name)
                                } catch (e: Exception) {
                                    isError = true
                                    Toast.makeText(
                                        context,
                                        "Lỗi khi cập nhật bài đăng: ${e.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } finally {
                                    isLoading = false // Kết thúc loading
                                }
                            }
                        }
                    }, modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp), colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xff5dadff)
                    )
                ) {
                    Text(
                        text = "Sửa bài đăng",
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.cairo_regular)),
                        color = Color(0xffffffff)
                    )
                }

            }
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
            }
            if (isError) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Lỗi khi cập nhật bài đăng!",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}


@Composable
fun BuildingOptions(
    userId: String,
    selectedBuilding: String?,
    onBuildingSelected: (String) -> Unit
) {
    val buildingViewModel: PostViewModel = viewModel()
    val buildings by buildingViewModel.buildings // Danh sách tòa nhà từ ViewModel
    val context = LocalContext.current

    // Tạo trạng thái tòa nhà đã chọn
    val selectedBuildingState = remember(selectedBuilding) { mutableStateOf(selectedBuilding) }

    LaunchedEffect(userId) {
        buildingViewModel.getBuildings(userId) // Gọi API lấy danh sách tòa nhà
    }

    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        // Chỉ hiển thị khi danh sách tòa nhà đã được tải
        if (buildings.isNotEmpty()) {
            selectedBuildingState.value?.let { buildingId ->
                val selectedBuildingName = buildings.find { it._id == buildingId }?.nameBuilding
                selectedBuildingName?.let {
                }
            } ?: run {
            }

            FlowRow(
                modifier = Modifier.padding(5.dp),
                mainAxisSpacing = 10.dp,
                crossAxisSpacing = 10.dp
            ) {
                buildings.forEach { building ->
                    BuildingOption(
                        text = building.nameBuilding,
                        isSelected = selectedBuildingState.value == building._id,
                        onClick = {
                            selectedBuildingState.value = building._id
                            onBuildingSelected(building._id)
                         //   Toast.makeText(context, "Bạn đã chọn tòa nhà: ${building.nameBuilding}", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        } else {
            // Hiển thị một thông báo khi danh sách tòa nhà chưa được tải
            Text(text = "Đang tải danh sách tòa nhà...", fontSize = 16.sp, color = Color.Gray)
        }
    }
}



@Composable
fun BuildingOption(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clickable(
                onClick = onClick,
                indication = null, // Tắt hiệu ứng nhấp
                interactionSource = remember { MutableInteractionSource() } // Tùy chỉnh tương tác
            )
            .shadow(3.dp, shape = RoundedCornerShape(9.dp))
            .border(
                width = 1.dp,
                color = if (isSelected) Color(0xFF44acfe) else Color(0xFFeeeeee),
                shape = RoundedCornerShape(9.dp)
            )
            .background(color = Color.White, shape = RoundedCornerShape(9.dp))
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
                        shape = TriangleShape() // Dấu tích dạng tam giác
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
//
//
@Composable
fun RoomOptions(
    buildingId: String,
    selectedRoom: String?,
    onRoomSelected: (String) -> Unit
) {
    val roomViewModel: PostViewModel = viewModel()
    val rooms by roomViewModel.rooms.collectAsState(initial = emptyList()) // Danh sách phòng từ ViewModel
    val context = LocalContext.current
    val selectedRoomState = remember { mutableStateOf(selectedRoom) }

    LaunchedEffect(buildingId) {
        if (buildingId.isNotEmpty()) {
            roomViewModel.getRooms(buildingId) // Gọi API để lấy phòng cho tòa nhà
        }
    }

    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        // Chỉ hiển thị khi danh sách phòng đã được tải
        if (rooms.isNotEmpty()) {
            selectedRoomState.value?.let { roomId ->
                val selectedRoomName = rooms.find { it._id == roomId }?.room_name
                selectedRoomName?.let {

                }
            } ?: run {

            }

            FlowRow(
                modifier = Modifier.padding(5.dp),
                mainAxisSpacing = 10.dp,
                crossAxisSpacing = 10.dp
            ) {
                rooms.forEach { room ->
                    RoomOption(
                        text = room.room_name,
                        isSelected = selectedRoomState.value == room._id,
                        onClick = {
                            selectedRoomState.value = room._id
                            onRoomSelected(room._id)
                          //  Toast.makeText(context, "Bạn đã chọn phòng: ${room.room_name}", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        } else {
            // Hiển thị một thông báo khi danh sách phòng chưa được tải
            Text(text = "Đang tải danh sách phòng...", fontSize = 16.sp, color = Color.Gray)
        }
    }
}
@Composable
fun RoomOption(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clickable(
                onClick = onClick,
                indication = null, // Tắt hiệu ứng nhấp
                interactionSource = remember { MutableInteractionSource() } // Tùy chỉnh tương tác
            )
            .shadow(3.dp, shape = RoundedCornerShape(9.dp))
            .border(
                width = 1.dp,
                color = if (isSelected) Color(0xFF44acfe) else Color(0xFFeeeeee),
                shape = RoundedCornerShape(9.dp)
            )
            .background(color = Color.White, shape = RoundedCornerShape(9.dp))
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
                        shape = TriangleShape() // Dấu tích dạng tam giác
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
    detail: PostingList // Truyền đối tượng detail chứa ảnh và video
) {
    val selectedImages = remember { mutableStateListOf<Uri>() }
    val selectedVideos = remember { mutableStateListOf<Uri>() }
    val baseUrl = "http://10.0.2.2:3000/"

// Chuyển đổi các đường dẫn ảnh và video từ detail thành Uri, thêm base URL vào trước mỗi đường dẫn
    val imagesFromDetail = detail.photos?.map { Uri.parse( baseUrl+it) } ?: listOf()
    val videosFromDetail = detail.videos?.map { Uri.parse(baseUrl+it) } ?: listOf()

    // Gán giá trị ảnh và video từ detail vào selectedImages và selectedVideos
    if (selectedImages.isEmpty()) {
        selectedImages.addAll(imagesFromDetail)
    }

    if (selectedVideos.isEmpty()) {
        selectedVideos.addAll(videosFromDetail)
    }
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
                // Chỉ hiển thị Text khi selectedImages rỗng
                if (selectedImages.isEmpty()) {
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

                // Hiển thị LazyRow nếu có ảnh
                if (selectedImages.isNotEmpty()) {
                    LazyRow(
                        modifier = Modifier.padding(top = 8.dp) // Khoảng cách giữa LazyRow và các thành phần khác
                    ) {
                        items(selectedImages) { uri ->
                            Box(modifier = Modifier.padding(4.dp)) {
                                Image(
                                    painter = rememberImagePainter(uri),
                                    contentDescription = null,
                                    modifier = Modifier.size(120.dp)
                                )
                                // Nút xóa
                                Box(
                                    modifier = Modifier
                                        .size(16.dp)
                                        .background(Color.Red, shape = CircleShape)
                                        .align(Alignment.TopEnd)
                                        .clickable { selectedImages.remove(uri) }, // Xóa ảnh khi nhấn
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Xóa",
                                        tint = Color.White,
                                        modifier = Modifier.size(12.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Button chọn video
            Column(
                modifier = Modifier
                    .clickable { launcherVideo.launch(arrayOf("video/*")) }
                    .shadow(3.dp, shape = RoundedCornerShape(10.dp))
                    .background(Color.White)
                    .border(0.dp, Color(0xFFEEEEEE), RoundedCornerShape(10.dp))
                    .padding(if (selectedVideos.isEmpty()) 25.dp else 22.dp) // Thu nhỏ khi có video
                    .weight(1f), // Chiếm khoảng không còn lại
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.video),
                    contentDescription = null,
                    modifier = Modifier.size(if (selectedVideos.isEmpty()) 30.dp else 35.dp) // Thu nhỏ khi có video
                )

                if (selectedVideos.isEmpty()) { // Hiển thị text khi chưa có video
                    Spacer(modifier = Modifier.height(7.dp))
                    Text(
                        text = "Video",
                        color = Color.Black,
                        fontSize = 13.sp
                    )
                }
            }

            // Hiển thị danh sách video đã chọn (LazyRow)
            if (selectedVideos.isNotEmpty()) {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(3f) // Chiếm không gian rộng hơn cho LazyRow
                ) {
                    items(selectedVideos) { uri ->
                        Box(modifier = Modifier.padding(4.dp)) {
                            // Hiển thị thumbnail video
                            VideoThumbnail(uri)
                            // Nút xóa
                            Box(
                                modifier = Modifier
                                    .size(16.dp)
                                    .background(Color.Red, shape = CircleShape)
                                    .align(Alignment.TopEnd)
                                    .clickable { selectedVideos.remove(uri) }, // Xóa video khi nhấn
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Xóa",
                                    tint = Color.White,
                                    modifier = Modifier.size(12.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

    }}
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

fun getRequestBodyString(requestBody: RequestBody): String {
    return try {
        val buffer = Buffer()
        requestBody.writeTo(buffer)
        buffer.readUtf8()  // Trả về chuỗi UTF-8 từ RequestBody
    } catch (e: IOException) {
        "Error reading RequestBody: ${e.localizedMessage}"
    }
}


