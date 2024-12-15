package com.rentify.user.app.view.userScreens.UpdatePostScreen

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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import coil.compose.rememberImagePainter
//import com.google.android.exoplayer2.MediaItem
//import com.google.android.exoplayer2.ui.PlayerView
import com.rentify.user.app.view.staffScreens.UpdatePostScreen.Components.ComfortableLabel
import com.rentify.user.app.view.staffScreens.UpdatePostScreen.Components.ServiceLabel
import com.rentify.user.app.view.staffScreens.postingList.PostingListComponents.PostingList
import com.rentify.user.app.view.userScreens.UpdatePostScreen.component.BuildingOptions
import com.rentify.user.app.view.userScreens.UpdatePostScreen.component.RoomOptions
import com.rentify.user.app.viewModel.PostViewModel.PostViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okio.Buffer
import java.io.File
import java.io.IOException

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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePostUserScreen(navController: NavHostController,postId: String) {
    var selectedImages by remember { mutableStateOf(emptyList<Uri>()) }
    var selectedVideos by remember { mutableStateOf(emptyList<Uri>()) }
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    var selectedBuilding by remember { mutableStateOf<String?>(null) }
    var selectedBuilding1 by remember { mutableStateOf<String?>(null) }
    var selectedRoom by remember { mutableStateOf<String?>(null) }
    var selectedRoom1 by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current
    val scrollState = rememberScrollState()
    var title by remember { mutableStateOf("") }

    var isEdited by remember { mutableStateOf(false) }
    var content by remember { mutableStateOf("") }
    val postId = navController.currentBackStackEntry?.arguments?.getString("postId")

    val viewModel: PostViewModel = viewModel()

    val buildingId = viewModel.selectedBuilding.value

    val postDetail = viewModel.postDetail.value

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
                .padding(bottom = screenHeight.dp / 5f)

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
                        text = "Sửa bài đăng",
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
                    // tieeu de
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
                        onValueChange = { newValue ->
                            title = newValue // Cập nhật giá trị title khi người dùng thay đổi
                            isEdited = true  // Đánh dấu là đã chỉnh sửa
                        },
                        modifier = Modifier
                            .fillMaxWidth(),

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

                postDetail?.let {
                    SelectMedia(
                        onMediaSelected = { images, videos ->
                            selectedImages = images
                            selectedVideos = videos


                        },
                        detail = it
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
                        onValueChange = { newValue ->
                            content = newValue // Cập nhật giá trị title khi người dùng thay đổi
                            isEdited = true  // Đánh dấu là đã chỉnh sửa
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
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
                Spacer(modifier = Modifier.height(10.dp))
                Column {
                    ComfortableLabel()
                    if (!selectedRoom.isNullOrEmpty()) {
                        BuildingOptions(
                            roomId = selectedRoom!!,
                            selectedBuilding = selectedBuilding, // Truyền giá trị ban đầu

                        )}
                }
                // dịch vụ
                Spacer(modifier = Modifier.height(10.dp))
                Column {
                    ServiceLabel()
                    RoomOptions(
                        userId = "671a29b84e350b2df4aee4ed",
                        selectedRoom = selectedRoom,
                        onRoomSelected = { roomId ->
                            selectedRoom1 = roomId
                            Log.d("RoomOptions", "Selected Room: $roomId")
                        }
                    )
//                    if (!selectedBuilding.isNullOrEmpty()) {
//                        RoomOptions(
//                            buildingId = selectedBuilding!!, // Truyền tòa nhà đã chọn
//                            selectedRoom = selectedRoom, // Truyền giá trị phòng đã chọn
//                            onRoomSelected = { roomId ->
//                                selectedRoom1 = roomId // Cập nhật phòng đã chọn
//                                Log.d("room toa nha", "Updated selected room: $selectedRoom")
//                            }
//                        )
//                    } else {
//                        // Hiển thị thông báo nếu buildingId là null hoặc rỗng
//                        Text(text = "Vui lòng chọn tòa nhà", color = Color.Gray)
//                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(screenHeight.dp / 5f)
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
                            viewModel.updatePost_user(
                                postId = postId,
                                userId = "671a29b84e350b2df4aee4ed",
                                buildingId = buildingId,
                                roomId = selectedRoom1,
                                title = title,
                                content = content,
                                status = "0",
                                postType = "rent",
                                videoFile = videoParts,  // List<MultipartBody.Part>
                                photoFile = photoParts   // List<MultipartBody.Part>
                            )
                        }
//                        navController.navigate("post_detail/$postId")
                        navController.navigate("SEARCHPOSTROOMATE")
                        {
                            popUpTo("update_post_user_screen/$postId")
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
        }
    }
}

@Composable
fun SelectMedia(
    onMediaSelected: (List<Uri>, List<Uri>) -> Unit,
    detail: PostingList // Truyền đối tượng detail chứa ảnh và video
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


