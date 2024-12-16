@file:OptIn(ExperimentalMaterial3Api::class)

package com.rentify.user.app.view.userScreens.searchPostRoomateScreen.Component

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.rentify.user.app.R
import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.LoginRepository.LoginRepository
import com.rentify.user.app.view.staffScreens.UpdatePostScreen.prepareMultipartBody
import com.rentify.user.app.view.staffScreens.postingList.PostingListComponents.PostingList
import com.rentify.user.app.view.userScreens.AddPostScreen.isFieldEmpty
import com.rentify.user.app.view.userScreens.contract.components.DialogCompose
import com.rentify.user.app.viewModel.LoginViewModel
import com.rentify.user.app.viewModel.PostViewModel.PostViewModel


@Composable
fun PostingListCard(
    postlist: PostingList,
    modifier: Modifier = Modifier,
    onHeightChanged: (Dp) -> Unit // Callback để truyền chiều cao mới
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val maxWidth = screenWidth * 0.7f // Giới hạn 70% chiều rộng màn hình
    var isExpanded by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var cardHeightPx by remember { mutableStateOf(0) }
    val scrollState = rememberScrollState()
    // Khi dialog đóng, cần reset trạng thái của thông báo
    val onDismissDialog: () -> Unit = {
        showDialog = false
    }
    val apiService = RetrofitService()
    val userRepository = LoginRepository(apiService)
    val context = LocalContext.current
    val factory = remember(context) {
        LoginViewModel.LoginViewModelFactory(userRepository, context.applicationContext)
    }
    val loginViewModel: LoginViewModel = viewModel(factory = factory)
    val userId = loginViewModel.getUserData().userId

    val postViewModel: PostViewModel = viewModel()

    // State để hiển thị thông báo
    val snackbarHostState = remember { SnackbarHostState() }
    // Sử dụng onGloballyPositioned để lấy chiều cao của Card
    Card(
        elevation = 4.dp,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(8.dp))
            .onGloballyPositioned { layoutCoordinates ->
                // Tính chiều cao của Card trong px
                cardHeightPx = layoutCoordinates.size.height
            }
    ) {
        // Sau khi tính toán chiều cao của Card, sử dụng LaunchedEffect để cập nhật chiều cao
        val density = LocalDensity.current.density
        val cardHeightDp = (cardHeightPx / density).dp // Chuyển từ px sang dp

        // Gọi callback khi chiều cao thay đổi
        LaunchedEffect(cardHeightPx) {
            onHeightChanged(cardHeightDp)
        }

        // Nội dung của Card
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                androidx.compose.material3.Text(
                    text = "${postlist.user?.name}",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xffB95533),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Row {
                    Image(
                        painter = painterResource(id = R.drawable.phone),
                        contentDescription = null,
                        modifier = Modifier.size(25.dp, 25.dp)
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                    Image(
                        painter = painterResource(id = R.drawable.mess),
                        contentDescription = null,
                        modifier = Modifier.size(25.dp, 25.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            androidx.compose.material3.Text(
                text = "Mô tả: ${postlist.content}",
                fontSize = 13.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xff7f7f7f),
                maxLines = if (isExpanded) Int.MAX_VALUE else 3,
                overflow = if (isExpanded) TextOverflow.Clip else TextOverflow.Ellipsis,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                androidx.compose.material3.Text(
                    text = "Khu vực : ${postlist.addresss?.takeIf { it.isNotBlank() } ?: "Người đăng không ghi địa chỉ, bạn có thể liên hệ"}",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xff7f7f7f),
                    maxLines = if (isExpanded) Int.MAX_VALUE else 2,
                    overflow = if (isExpanded) TextOverflow.Clip else TextOverflow.Ellipsis,
                    modifier = Modifier.widthIn(max = maxWidth)
                )
                androidx.compose.material3.IconButton(
                    onClick = { isExpanded = !isExpanded }
                ) {
                    androidx.compose.material3.Icon(
                        imageVector = if (isExpanded)
                            Icons.Default.KeyboardArrowUp
                        else
                            Icons.Default.KeyboardArrowDown,
                        contentDescription = if (isExpanded) "Collapse" else "Expand",
                        tint = Color.Black,
                        modifier = Modifier.size(25.dp)
                    )
                }
            }

            AnimatedVisibility(
                visible = isExpanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White)
                        .padding(5.dp)
                        .height(320.dp),
                    // .verticalScroll(scrollState),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Log.d("PostingListCard", "PostingListCard: ${postlist.photos + postlist.videos}")
                    if (postlist.photos.isNotEmpty() || postlist.videos.isNotEmpty()) {
                        com.rentify.user.app.view.userScreens.SearchRoomateScreen.SearchRoomateComponent.PostMediaSection1(
                            mediaList = postlist.photos + postlist.videos
                        )

                    }

                    Spacer(modifier = Modifier.height(5.dp))
                    Box(modifier = Modifier.padding(horizontal = 10.dp)) {
                        androidx.compose.material3.Button(
                            onClick = {
                                showDialog = true

                            }, modifier = Modifier
                                .height(50.dp)
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp), colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xff5dadff)
                            )
                        ) {
                            androidx.compose.material3.Text(
                                text = "Sửa bài đăng",
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.cairo_regular)),
                                color = Color(0xffffffff)
                            )
                        }

                    }
                    if (showDialog) {
                        EditPostDialog(
                            postId="${postlist._id}",
                            onDismiss = onDismissDialog, // Truyền snackbarHostState vào dialog,
                            snackbarHostState = snackbarHostState
                        )
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PostListWithSwipe(
    navController: NavController,
    posts: List<PostingList>,
    onDeletePost: (String) -> Unit
) {
    var isShowDialog by remember { mutableStateOf(false) }
    var postIdToDelete by remember { mutableStateOf<String?>(null) }

    // Tạo một map để lưu chiều cao của từng card
    val cardHeights = remember { mutableStateOf<Map<String, Dp>>(emptyMap()) }

    // Hiển thị dialog xác nhận xóa
    if (isShowDialog) {
        DialogCompose(
            onConfirmation = {
                isShowDialog = false
                postIdToDelete?.let { id -> onDeletePost(id) }
                postIdToDelete = null
            },
            onCloseDialog = {
                isShowDialog = false
                postIdToDelete = null
            },
            titleDialog = "Xác nhận xóa",
            mess = "Bạn có chắc chắn muốn xóa bài đăng này?"
        )
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(posts, key = { it._id }) { post ->
            val swipeableState = rememberSwipeableState(0)
            val width = LocalDensity.current.run { 200.dp.toPx() }
            val anchors = mapOf(0f to 0, -width * 0.3f to 1)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .swipeable(
                        state = swipeableState,
                        anchors = anchors,
                        orientation = Orientation.Horizontal
                    )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(
                            cardHeights.value[post._id] ?: 157.dp
                        ) // Sử dụng chiều cao của card từ map
                        .padding(horizontal = 15.dp)
                        .shadow(elevation = 4.dp, shape = RoundedCornerShape(10.dp))
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Red),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color.White,
                        modifier = Modifier
                            .size(40.dp)
                            .padding(end = 10.dp)
                    )
                }

                PostingListCard(
                    postlist = post,
                    modifier = Modifier.graphicsLayer {
                        translationX = swipeableState.offset.value
                    },
                    onHeightChanged = { newHeight ->
                        // Cập nhật chiều cao riêng cho mỗi card
                        cardHeights.value = cardHeights.value + (post._id to newHeight)
                    }
                )

                LaunchedEffect(swipeableState.offset.value) {
                    if (swipeableState.offset.value <= -width * 0.3f) {
                        postIdToDelete = post._id
                        isShowDialog = true
                        swipeableState.snapTo(0)
                    }
                }
            }
        }
    }
}





@Composable
fun EditPostDialog(
    postId: String?,
    onDismiss: () -> Unit,
    snackbarHostState: SnackbarHostState
) {
    val context = LocalContext.current
    var address by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var userId by remember { mutableStateOf("") }
    var post_type by remember { mutableStateOf("") }
    var selectedPhotos by remember { mutableStateOf(emptyList<Uri>()) }
    val PostViewModel: PostViewModel = viewModel()
    val postDetail by PostViewModel.postDetail.observeAsState()
    val updateStatus by PostViewModel.error.observeAsState()
    val updateStatus_status by PostViewModel.updateStatus.observeAsState()// Theo dõi trạng thái lỗi từ ViewModel
    var isEdited by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    val apiService = RetrofitService()
    val userRepository = LoginRepository(apiService)
    val factory = remember(context) {
        LoginViewModel.LoginViewModelFactory(userRepository, context.applicationContext)
    }
    val loginViewModel: LoginViewModel = viewModel(factory = factory)


    // Tạo và lấy instance của PostViewModel
    val postViewModel: PostViewModel = viewModel()
    var selectedImages by remember { mutableStateOf(emptyList<Uri>()) }
    var selectedVideos by remember { mutableStateOf(emptyList<Uri>()) }
    // Lấy chi tiết hợp đồng khi hợp đồng ID thay đổi
    LaunchedEffect(postId) {
        postId?.let { PostViewModel.getPostDetail(it) }
    }
    val scrollState = rememberScrollState()
    postDetail?.let { postDetail ->
        if (!isEdited) {
            address = postDetail.address ?: "" // Ghép ID người dùng
            content = postDetail.content?:""
            userId = postDetail.user?._id?:""
            post_type = postDetail.post_type?:""

        }
    }
    Dialog(onDismissRequest = onDismiss) {
        androidx.compose.material3.Surface(
            shape = MaterialTheme.shapes.medium,
            color = Color(0xfff7f7f7)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Sửa bài đăng",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                // Sửa User ID
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                ) {
                    Row {
                        androidx.compose.material3.Text(
                            text = "Địa chỉ",
                            //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                            color = Color(0xff363636),
                            fontWeight = FontWeight(700),
                            fontSize = 13.sp,
                        )
                        androidx.compose.material3.Text(

                            text = " *",
                            //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                            color = Color(0xffff1a1a),
                            // fontWeight = FontWeight(700),
                            fontSize = 16.sp,

                            )
                    }
                    androidx.compose.material3.TextField(
                        value = address,
                        onValueChange = { address = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                BorderStroke(2.dp, Color(0xFF908b8b)), // Độ dày và màu viền
                                shape = RoundedCornerShape(12.dp) // Bo góc
                            ),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedPlaceholderColor = Color.Black,
                            unfocusedPlaceholderColor = Color.Gray,
                            unfocusedContainerColor = Color(0xFFf7f7f7),
                            focusedContainerColor = Color(0xFFf7f7f7),
                        ),
                        placeholder = {
                            androidx.compose.material3.Text(
                                text = "Nhập địa chỉ",
                                fontSize = 13.sp,
                                color = Color(0xFF898888),
                                fontFamily = FontFamily(Font(R.font.cairo_regular))
                            )
                        },
                        shape = RoundedCornerShape(size = 8.dp),
                        textStyle = TextStyle(
                            color = Color.Black, fontFamily = FontFamily(Font(R.font.cairo_regular))
                        ),

                        )
                }
                // Sửa Content
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                ) {
                    Row {
                        androidx.compose.material3.Text(
                            text = "Nhập mô tả",
                            //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                            color = Color(0xff363636),
                            fontWeight = FontWeight(700),
                            fontSize = 13.sp,
                        )
                        androidx.compose.material3.Text(
                            text = " *",
                            //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                            color = Color(0xffff1a1a),
                            // fontWeight = FontWeight(700),
                            fontSize = 16.sp,
                        )
                    }
                    androidx.compose.material3.TextField(
                        value = content,
                        onValueChange = { content = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                BorderStroke(2.dp, Color(0xFF908b8b)), // Độ dày và màu viền
                                shape = RoundedCornerShape(12.dp) // Bo góc
                            ),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedPlaceholderColor = Color.Black,
                            unfocusedPlaceholderColor = Color.Gray,
                            unfocusedContainerColor = Color(0xFFf7f7f7),
                            focusedContainerColor = Color(0xFFf7f7f7),
                        ),
                        placeholder = {
                            androidx.compose.material3.Text(
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

                postDetail?.let {
                    com.rentify.user.app.view.staffScreens.UpdatePostScreen.SelectMedia(
                        onMediaSelected = { images, videos ->
                            selectedImages = images
                            selectedVideos = videos
                        },
                        detail = it
                    )

                    // Phản hồi trạng thái lỗi/thành công
                    updateStatus?.let { errorMessage ->
                        LaunchedEffect(errorMessage) {
                            if (errorMessage.isNotEmpty()) {
                                snackbarHostState.showSnackbar(errorMessage)
                            }
                        }
                    }
                    Row {
                        androidx.compose.material3.Button(
                            onClick = onDismiss,
                            modifier = Modifier
                                .weight(1f)
                                .padding(
                                    end = 10
                                        .dp
                                ),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFf7f7f7)
                            ),
                            shape = RoundedCornerShape(10.dp)

                        ) {
                            androidx.compose.material3.Text(
                                text = "Quay lại",
                                color = Color(0xff2e90fa),
                                fontFamily = FontFamily(Font(R.font.cairo_regular)),
                                fontWeight = FontWeight(600),
                                fontSize = 17.sp,
                            )
                        }
                        androidx.compose.material3.Button(
                            onClick = {
                                if (isFieldEmpty(content)) {
                                    // Hiển thị thông báo lỗi nếu content trống
                                    Toast.makeText(
                                        context,
                                        "Nội dung không thể trống",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    return@Button
                                }
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
                                val videoPart = selectedVideos.mapNotNull { uri ->
                                    val mimeType = context.contentResolver.getType(uri) ?: "video/mp4"
                                    com.rentify.user.app.view.userScreens.AddPostScreen.prepareMultipartBody(
                                        context,
                                        uri,
                                        "video",
                                        ".mp4",
                                        mimeType
                                    )
                                }
                                val photoPart = selectedImages.mapNotNull { uri ->
                                    val mimeType = context.contentResolver.getType(uri) ?: "image/jpeg"
                                    com.rentify.user.app.view.userScreens.AddPostScreen.prepareMultipartBody(
                                        context,
                                        uri,
                                        "photo",
                                        ".jpg",
                                        mimeType
                                    )
                                }
                                postId?.let {
                                    PostViewModel.updatePost(
                                        postId = postId,
                                        userId = userId,
                                        buildingId = "",
                                        roomId = "",
                                        title = "",
                                        address = address,
                                        content = content,
                                        status = "0",
                                        postType = post_type,
                                        videoFile =videoPart ,
                                        photoFile = photoPart
                                    )
                                    onDismiss()
                                }
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFf7f7f7)
                            ),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            androidx.compose.material3.Text(
                                text = "Xác nhận",
                                color = Color(0xfff04438),
                                fontFamily = FontFamily(Font(R.font.cairo_regular)),
                                fontWeight = FontWeight(600),
                                fontSize = 17.sp
                            )
                        }
                    }
                }
            }
        }
    }}
