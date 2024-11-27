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
import com.rentify.user.app.model.PostingDetail
import com.rentify.user.app.view.staffScreens.UpdatePostScreen.Components.ComfortableLabel
import com.rentify.user.app.view.staffScreens.UpdatePostScreen.Components.ServiceLabel
import com.rentify.user.app.view.staffScreens.UpdatePostScreen.Components.TriangleShape
import com.rentify.user.app.viewModel.PostViewModel.PostViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
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
fun UpdatePostScreen(navController: NavHostController,postId: String) {
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

    val postDetail by viewModel.postDetail.observeAsState()

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
        Log.d("EditPostScreen", "Editing title: $title, content: $content")
        // Gán thông tin tòa nhà nếu có
        selectedBuilding = detail.building?._id
        Log.d("EditPostScreen", "Initial selected building: $selectedBuilding")
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

                            Log.d("SelectedImages", "Selected images: $selectedImages")
                            Log.d("SelectedVideos", "Selected videos: $selectedVideos")
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
                Spacer(modifier = Modifier.height(3.dp))
                Column {
                    ComfortableLabel()
                    BuildingOptions(
                        userId = "67362213c6d421d3027fb5a7",
                        selectedBuilding = selectedBuilding, // Truyền giá trị ban đầu
                        onBuildingSelected = { buildingId ->
                            viewModel.setSelectedBuilding(buildingId) // Cập nhật tòa nhà đã chọn
                            Log.d("toa nha looo", "Updated selected building: $selectedBuilding1")
                          //  onBuildingUpdated(buildingId) // Gửi callback ra ngoài
                        }
                    )
                }
                // dịch vụ
                Spacer(modifier = Modifier.height(10.dp))
                Column {
                    ServiceLabel()

                    viewModel.selectedBuilding.value?.let { buildingId ->
                        // Lấy phòng cho tòa nhà đã chọn
                        RoomOptions(
                            buildingId = buildingId, // Truyền tòa nhà đã chọn
                            selectedRoom = selectedRoom, // Truyền giá trị phòng đã chọn
                            onRoomSelected = { roomId ->
                                selectedRoom1 = roomId // Cập nhật phòng đã chọn
                                Log.d("room toa nha", "Updated selected room: $selectedRoom1")
                            }
                        )
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
                            viewModel.updatePost(
                                postId = postId,
                                userId = "67362213c6d421d3027fb5a7",
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
                        navController.navigate("POSTING_STAFF")
                        {
                            popUpTo("update_post_screen/$postId")
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
fun BuildingOptions(
    userId: String,
    selectedBuilding: String?, // Tòa nhà đã chọn ban đầu
    onBuildingSelected: (String) -> Unit // Hàm được gọi khi chọn tòa nhà
) {
    val buildingViewModel: PostViewModel = viewModel()
    val buildings by buildingViewModel.buildings // Danh sách tòa nhà từ ViewModel
    val context = LocalContext.current // Context để sử dụng khi cần

    // Tạo trạng thái tòa nhà đã chọn
    val selectedBuildingState = remember(selectedBuilding) { mutableStateOf(selectedBuilding) }

    Log.d("toa da chon", "tòa đã chọn $selectedBuildingState")


    // Gọi API lấy danh sách tòa nhà theo userId
    LaunchedEffect(userId) {
        buildingViewModel.getBuildings(userId)
    }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        // Hiển thị thông tin tòa nhà đã chọn
        selectedBuildingState.value?.let { buildingId ->
            val selectedBuildingName = buildings.find { it._id == buildingId }?.nameBuilding
            selectedBuildingName?.let {
                Text(
                    text = "Tòa nhà đã chọn: $it",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        } ?: run {
            Text(
                text = "Chưa chọn tòa nhà nào",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        // Danh sách các tòa nhà để chọn lại
        FlowRow(
            modifier = Modifier.padding(5.dp),
            mainAxisSpacing = 10.dp, // Khoảng cách giữa các phần tử trên cùng một hàng
            crossAxisSpacing = 10.dp // Khoảng cách giữa các hàng
        ) {
            // Hiển thị các tùy chọn tòa nhà
            buildings.forEach { building ->
                BuildingOption(
                    text = building.nameBuilding,
                    isSelected = selectedBuildingState.value == building._id,
                    onClick = {
                        // Khi chọn, cập nhật trạng thái và gọi callback
                        selectedBuildingState.value = building._id
                        onBuildingSelected(building._id)
                        Toast.makeText(context, "Bạn đã chọn tòa nhà: ${building.nameBuilding}", Toast.LENGTH_SHORT).show()
                    }
                )
            }
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
    val rooms by roomViewModel.rooms // Danh sách phòng từ ViewModel
    val context = LocalContext.current

    val selectedRoomState = remember { mutableStateOf(selectedRoom) }

    // Gọi API lấy danh sách phòng khi buildingId thay đổi
    LaunchedEffect(buildingId) {
        Log.d("RoomOptions", "Fetching rooms for buildingId: $buildingId")
        roomViewModel.getRooms(buildingId)
    }

    // Kiểm tra xem rooms có được lấy đúng không
    LaunchedEffect(rooms) {
        Log.d("RoomOptions", "Fetched rooms: ${rooms.size} rooms")
    }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        selectedRoomState.value?.let { roomId ->
            val selectedRoomName = rooms.find { it._id == roomId }?.room_name
            selectedRoomName?.let {
                Text(
                    text = "Phòng đã chọn: $it",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        } ?: run {
            Text(
                text = "Chưa chọn phòng nào",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        // Hiển thị danh sách các phòng
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
                        Toast.makeText(context, "Bạn đã chọn phòng: ${room.room_name}", Toast.LENGTH_SHORT).show()
                    }
                )
            }
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


