@file:OptIn(ExperimentalMaterial3Api::class)

package com.rentify.user.app.view.userScreens.searchPostRoomateScreen.Component

import android.util.Log
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RotateRight
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.rentify.user.app.R
import com.rentify.user.app.model.Post
import com.rentify.user.app.view.staffScreens.postingList.PostingListComponents.PostingList
import com.rentify.user.app.view.userScreens.contract.components.DialogCompose
import com.rentify.user.app.viewModel.PostViewModel.PostViewModel
import kotlin.math.roundToInt

//@OptIn(ExperimentalMaterialApi::class)
//@Composable
//fun PostListScreen(navController: NavController, userId: String,postType:String) {
//    val viewModel: PostViewModel = viewModel()
//    val post by viewModel.posts
//    val context = LocalContext.current
//    var isShowDialog by remember { mutableStateOf(false) }
//    var postIdToDelete by remember { mutableStateOf<String?>(null) }
//    var isExpanded by remember { mutableStateOf(false) }
//
//    // Gọi API lấy danh sách bài đăng
//    LaunchedEffect(userId) {
//        viewModel.getPostingList_user(userId,postType)
//    }
//
//    // Hiển thị dialog xác nhận xóa
//    if (isShowDialog) {
//        DialogCompose(
//            onConfirmation = {
//                isShowDialog = false
//                postIdToDelete?.let { id ->
//                    viewModel.deletePostWithFeedback(id)
//                    postIdToDelete = null
//                }
//            },
//            onCloseDialog = {
//                isShowDialog = false
//                postIdToDelete = null
//                // Khôi phục trạng thái vuốt về vị trí ban đầu
//
//            },
//            titleDialog = "Xác nhận xóa",
//            mess = "Bạn có chắc chắn muốn xóa bài đăng này?"
//        )
//    }
//
//    LazyColumn(
//        modifier = Modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.spacedBy(8.dp),
//        contentPadding = PaddingValues(16.dp)
//    ) {
//        items(post, key = { it._id }) { post ->
//            // Quản lý trạng thái vuốt cho từng item
//            val swipeableState = rememberSwipeableState(0)
//
//            // Giới hạn 30% chiều rộng item
//            val width = LocalDensity.current.run { 200.dp.toPx() }
//            val anchors = mapOf(0f to 0, -width * 0.3f to 1) // 30% chiều rộng item sẽ là ngưỡng vuốt
//
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .swipeable(
//                        state = swipeableState,
//                        anchors = anchors,
//                        orientation = Orientation.Horizontal
//                    )
//            ) {
//                // Nền khi vuốt sang trái (hiển thị trước)
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(if (!isExpanded) 157.dp else 300.dp)
//                        .padding(horizontal = 15.dp)
//                        .shadow(elevation = 4.dp, shape = RoundedCornerShape(10.dp))
//                        .clip(RoundedCornerShape(8.dp))
//                        .background(Color.Red),
//                    contentAlignment = Alignment.CenterEnd
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.Delete,
//                        contentDescription = "Delete",
//                        tint = Color.White,
//                        modifier = Modifier
//                            .size(40.dp)
//                            .padding(end = 10.dp)
//                    )
//                }
//
//                // Card hiển thị nội dung chính (luôn nằm trên)
//                PostingListCard(
//                    postlist = post,
//                    modifier = Modifier.graphicsLayer {
//                        translationX = swipeableState.offset.value
//                    },
//                    onHeightChanged = { newHeight -> cardHeight = newHeight }
//
//
//                )
//
//                // Khi vuốt qua ngưỡng xóa
//                LaunchedEffect(swipeableState.offset.value) {
//                    if (swipeableState.offset.value <= -width * 0.3f) {
//                        postIdToDelete = post._id
//                        isShowDialog = true
//
//                        swipeableState.snapTo(0)
//                    }
//                }
//            }
//        }
//    }
//}

@Composable
fun PostingListCard(
    postlist: PostingList,
    modifier: Modifier = Modifier,
    onHeightChanged: (Dp) -> Unit // Callback để truyền chiều cao mới
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val maxWidth = screenWidth * 0.7f // Giới hạn 70% chiều rộng màn hình
    var isExpanded by remember { mutableStateOf(false) }

    var cardHeightPx by remember { mutableStateOf(0) }

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
                modifier = Modifier.fillMaxWidth().padding(0.dp),
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
                    Spacer(modifier = Modifier.width(10.dp))
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
                modifier = Modifier.fillMaxWidth().padding(0.dp),
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
                        .padding(15.dp)
                        .height(270.dp),
                    verticalArrangement = Arrangement.Center,
                ) {
                    com.rentify.user.app.view.userScreens.SearchRoomateScreen.SearchRoomateComponent.PostMediaSection1(
                        mediaList = postlist.photos + postlist.videos
                    )
                    Spacer(modifier = Modifier.height(25.dp))
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
                        .height(cardHeights.value[post._id] ?: 157.dp) // Sử dụng chiều cao của card từ map
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




@OptIn(ExperimentalPagerApi::class)
@Composable
fun PostMediaSection1(mediaList: List<String>) {
    if (mediaList.isNotEmpty()) {
        var showDialog by remember { mutableStateOf(false) }
        var selectedIndex by remember { mutableStateOf(0) }

        val pagerState = rememberPagerState() // Quản lý trạng thái pager
        Column(modifier = Modifier.fillMaxWidth()) {
            androidx.compose.material3.Text(
                text = "Media",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
            )
            HorizontalPager(
                count = mediaList.size,
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) { pageIndex ->
                val media = mediaList[pageIndex]
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.Gray)
                        .clickable {
                            selectedIndex = pageIndex
                            showDialog = true // Mở dialog
                        },
                    contentAlignment = Alignment.Center
                ) {

                    if (media.endsWith(".mp4")) {
                        // Play button overlay
                        androidx.compose.material3.Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Play Video",
                            tint = Color.White,
                            modifier = Modifier
                                .size(40.dp)
                                .align(Alignment.Center)
                        )

                    } else {
                        // Hiển thị ảnh
                        Image(
                            painter = rememberImagePainter("http://192.168.2.104:3000/$media"),
                            contentDescription = "Post Image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .padding(8.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color.LightGray),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }

            HorizontalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(8.dp),
                activeColor = Color.Black,
                inactiveColor = Color.Gray
            )
        }

        // Hiển thị dialog nếu người dùng nhấn vào media
        if (showDialog) {
            PostMediaDialog1(
                mediaList = mediaList,
                currentIndex = selectedIndex,
                onDismiss = { showDialog = false } // Đóng dialog
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PostMediaDialog1(
    mediaList: List<String>,
    currentIndex: Int,
    onDismiss: () -> Unit
) {
    val pagerState = rememberPagerState(initialPage = currentIndex)
    var isLandscape by remember { mutableStateOf(false) } // Quản lý trạng thái xoay ảnh/video
    val currentPlayingIndex = remember { mutableStateOf(-1) } // Theo dõi video đang phát

    Dialog(onDismissRequest = onDismiss) {
        androidx.compose.material3.Surface(
            modifier = Modifier.fillMaxSize(), // Hiển thị toàn màn hình
            color = Color.Black // Nền đen để tạo cảm giác fullscreen
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // Nút đóng (X) ở góc trên bên phải
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        androidx.compose.material3.IconButton(onClick = onDismiss) {
                            androidx.compose.material3.Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                tint = Color.White
                            )
                        }
                    }

                    // Nội dung chính: ảnh/video với trạng thái xoay
                    HorizontalPager(
                        count = mediaList.size,
                        state = pagerState,
                        modifier = Modifier.weight(1f) // Chiếm phần lớn chiều cao màn hình
                    ) { pageIndex ->
                        val media = mediaList[pageIndex]
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            if (media.endsWith(".mp4")) {
                                // Render video
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .rotate(if (isLandscape) 90f else 0f)
                                ) {
                                    VideoPlayer1(
                                        videoUrl = "http://192.168.2.104:3000/$media",
                                        isPlaying = currentPlayingIndex.value == pageIndex
                                    )
                                }
                            } else {
                                // Render ảnh
                                Image(
                                    painter = rememberImagePainter("http://192.168.2.104:3000/$media"),
                                    contentDescription = "Post Image",
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .rotate(if (isLandscape) 90f else 0f), // Xoay ảnh
                                    contentScale = ContentScale.Fit
                                )
                            }
                        }
                    }

                    // Cập nhật video đang phát khi trang thay đổi
                    LaunchedEffect(pagerState.currentPage) {
                        currentPlayingIndex.value = pagerState.currentPage
                    }

                    // Nút điều khiển xoay
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Spacer(modifier = Modifier.weight(1f)) // Đẩy nút xoay về giữa
                        androidx.compose.material3.IconButton(
                            onClick = { isLandscape = !isLandscape } // Đổi trạng thái xoay
                        ) {
                            androidx.compose.material3.Icon(
                                imageVector = Icons.Default.RotateRight,
                                contentDescription = "Rotate",
                                tint = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}



@Composable
fun VideoPlayer1(
    videoUrl: String,
    isPlaying: Boolean
) {
    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(videoUrl))
            prepare()
        }
    }

    // Cập nhật trạng thái phát khi `isPlaying` thay đổi
    LaunchedEffect(isPlaying) {
        exoPlayer.playWhenReady = isPlaying
        if (!isPlaying) {
            exoPlayer.pause()
        }
    }

    // Giải phóng ExoPlayer khi không còn được sử dụng
    DisposableEffect(key1 = exoPlayer) {
        onDispose {
            exoPlayer.release()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
    ) {
        AndroidView(
            factory = { context ->
                PlayerView(context).apply {
                    player = exoPlayer
                    useController = true
                    layoutParams = FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        Gravity.CENTER
                    )
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}






//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .background(color = Color.White)
//            .clip(RoundedCornerShape(10.dp))
//            .padding(10.dp),
//    ) {
//        androidx.compose.material3.Card(
//            modifier = Modifier
//                .fillMaxSize()
//                .shadow(
//                    elevation = 3.dp,
//                    shape = RoundedCornerShape(10.dp),
//                    spotColor = Color.Black
//                )
//                .align(Alignment.Center),
//            shape = RoundedCornerShape(10.dp),
//            colors = CardDefaults.cardColors(
//                containerColor = Color.White
//            )
//        ) {
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(15.dp),
//            ) {
//                Row(
//                    modifier = Modifier.fillMaxWidth()
//                        .padding(0.dp), // Căn đầy chiều rộng và padding cho đẹp
//                    horizontalArrangement = Arrangement.SpaceBetween, // Căn giữa đầu và cuối
//                    verticalAlignment = Alignment.CenterVertically // Căn giữa theo chiều dọc
//                ) {
//                    androidx.compose.material3.Text(
//                        text = "${postlist.user?.name}",
//                        fontSize = 15.sp,
//                        fontWeight = FontWeight.Normal,
//                        color = Color(0xffB95533),
//                        maxLines = 1,
//                        overflow = TextOverflow.Ellipsis,
//                    )
//                    Row {
//                        Image(
//                            painter = painterResource(id = R.drawable.phone),
//                            contentDescription = null,
//                            modifier = Modifier.size(25.dp, 25.dp)
//                        )
//                        Spacer(modifier = Modifier.width(10.dp))
//                        Image(
//                            painter = painterResource(id = R.drawable.mess),
//                            contentDescription = null,
//                            modifier = Modifier.size(25.dp, 25.dp)
//                        )
//                    }
//
//                }
//                Spacer(modifier = Modifier.height(10.dp))
//                androidx.compose.material3.Text(
//                    text = "Mô tả: ${postlist.content}",
//                    fontSize = 13.sp,
//                    fontWeight = FontWeight.Normal,
//                    color = Color(0xff7f7f7f),
//                    maxLines = if (isExpanded) Int.MAX_VALUE else 3, // Thay đổi số dòng hiển thị
//                    overflow = if (isExpanded) TextOverflow.Clip else TextOverflow.Ellipsis, // Hiển thị đầy đủ hoặc rút gọn
//                )
//                Row(
//                    modifier = Modifier.fillMaxWidth()
//                        .padding(0.dp), // Căn đầy chiều rộng và padding cho đẹp
//                    horizontalArrangement = Arrangement.SpaceBetween, // Căn giữa đầu và cuối
//                    verticalAlignment = Alignment.CenterVertically // Căn giữa theo chiều dọc
//                ) {
//                    androidx.compose.material3.Text(
//                        text = "Khu vực : ${postlist.addresss?.takeIf { it.isNotBlank() } ?: "Người đăng không ghi địa chỉ, bạn có thể liên hệ"}",
//                        fontSize = 13.sp,
//                        fontWeight = FontWeight.Normal,
//                        color = Color(0xff7f7f7f),
//                        maxLines = if (isExpanded) Int.MAX_VALUE else 2, // Thay đổi số dòng hiển thị
//                        overflow = if (isExpanded) TextOverflow.Clip else TextOverflow.Ellipsis,
//                        modifier = Modifier.widthIn(max = maxWidth)
//                    )
//                    androidx.compose.material3.IconButton(
//                        onClick = { isExpanded = !isExpanded }
//                    ) {
//                        androidx.compose.material3.Icon(
//                            imageVector = if (isExpanded)
//                                Icons.Default.KeyboardArrowUp
//                            else
//                                Icons.Default.KeyboardArrowDown,
//                            contentDescription = if (isExpanded) "Collapse" else "Expand",
//                            tint = Color.Black,
//                            modifier = Modifier.size(25.dp)
//                        )
//                    }
//                }
//
//                AnimatedVisibility(
//                    visible = isExpanded,
//                    enter = fadeIn() + expandVertically(),
//                    exit = fadeOut() + shrinkVertically()
//                ) {
//                    Column(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .background(color = Color.White)
//                            .padding(15.dp)
//                            .height(270.dp),
//                        verticalArrangement = Arrangement.Center,
//                    ) {
//                        PostMediaSection1(mediaList = postlist.photos + postlist.videos)
//                        Spacer(modifier = Modifier.height(25.dp))
//                    }
//                }
//            }
//        }
//    }
