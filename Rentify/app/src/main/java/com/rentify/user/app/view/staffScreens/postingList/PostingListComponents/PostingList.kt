@file:OptIn(ExperimentalMaterial3Api::class)

package com.rentify.user.app.view.staffScreens.postingList.PostingListComponents

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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.rentify.user.app.model.Post
import com.rentify.user.app.view.userScreens.contract.components.DialogCompose
import com.rentify.user.app.viewModel.PostViewModel.PostViewModel
import kotlin.math.roundToInt
data class PostingList(
    val _id: String,
    val title: String,  // Tương ứng với trường title trong API
    val price: String,  // Tương ứng với trường price trong API
    val address: String // Tương ứng với trường address trong API
)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PostListScreen(navController: NavController, userId: String) {
    val viewModel: PostViewModel = viewModel()
    val post by viewModel.posts
    val context = LocalContext.current
    var isShowDialog by remember { mutableStateOf(false) }
    var postIdToDelete by remember { mutableStateOf<String?>(null) }


    // Gọi API lấy danh sách bài đăng
    LaunchedEffect(userId) {
        viewModel.getPostingList(userId)
    }

    // Hiển thị dialog xác nhận xóa
    if (isShowDialog) {
        DialogCompose(
            onConfirmation = {
                isShowDialog = false
                postIdToDelete?.let { id ->
                    viewModel.deletePostWithFeedback(id)
                    postIdToDelete = null
                }
            },
            onCloseDialog = {
                isShowDialog = false
                postIdToDelete = null
                // Khôi phục trạng thái vuốt về vị trí ban đầu

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
        items(post, key = { it._id }) { post ->
            // Quản lý trạng thái vuốt cho từng item
            val swipeableState = rememberSwipeableState(0)

            // Giới hạn 30% chiều rộng item
            val width = LocalDensity.current.run { 200.dp.toPx() }
            val anchors = mapOf(0f to 0, -width * 0.3f to 1) // 30% chiều rộng item sẽ là ngưỡng vuốt

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .swipeable(
                        state = swipeableState,
                        anchors = anchors,
                        orientation = Orientation.Horizontal
                    )
            ) {
                // Nền khi vuốt sang trái (hiển thị trước)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp)
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

                // Card hiển thị nội dung chính (luôn nằm trên)
                PostingListCard(
                    postlist = post,
                    onClick = {
                        navController.navigate("post_detail/${post._id}")
                    },
                    modifier = Modifier.graphicsLayer {
                        translationX = swipeableState.offset.value
                    }
                )

                // Khi vuốt qua ngưỡng xóa
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
fun PostingListCard(
    postlist: PostingList,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = 4.dp,
        modifier = modifier
            .fillMaxWidth()

            .padding(horizontal = 8.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 18.dp)
            ) {
                Text(
                    text = postlist.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = "Từ ${postlist.price}",
                    color = Color.Red,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location",
                        tint = Color(0xFF03A9F4),
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = " ${postlist.address}",
                        color = Color.Gray,
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

//@Composable
//fun PostListScreen(navController: NavController, userId: String) {
//    val viewModel: PostViewModel = viewModel()
//    val post by viewModel.posts
//    val context = LocalContext.current
//    var isShowDialog by remember { mutableStateOf(false) }
//    var postIdToDelete by remember { mutableStateOf<String?>(null) }
//
//    // Gọi API lấy danh sách bài đăng
//    LaunchedEffect(userId) {
//        viewModel.getPostingList(userId)
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
//            // Quản lý trạng thái vuốt
//            val dismissState = rememberSwipeToDismissBoxState(
//                confirmValueChange = { direction ->
//                    if (direction == SwipeToDismissBoxValue.EndToStart) {
//                        // Vuốt sang trái, kích hoạt logic xóa
//                        postIdToDelete = post._id
//                        isShowDialog = true
//                        false // Đợi người dùng xác nhận trước khi xóa
//                    } else {
//                        // Vuốt sang phải (StartToEnd), không làm gì cả
//                        false
//                    }
//                }
//            )
//
//
//            SwipeToDismissBox(
//                state = dismissState,
//                // Nội dung nền xuất hiện khi vuốt
//                backgroundContent = {
//                    Box(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(horizontal = 8.dp)
//                            .clip(RoundedCornerShape(10.dp))
//                            .background(Color.Red),
//                        contentAlignment = Alignment.CenterEnd
//                    ) {
//                        Icon(
//                            imageVector = Icons.Default.Delete,
//                            contentDescription = "Delete",
//                            tint = Color.White,
//                            modifier = Modifier
//                                .size(40.dp)
//                                .padding(end = 10.dp)
//                        )
//                    }
//                },
//                // Nội dung chính
//                content = {
//                    PostingListCard(
//                        postlist = post,
//                        onClick = {
//                            navController.navigate("post_detail/${post._id}")
//                        }
//                    )
//                }
//            )
//        }
//
//    }
//}
//
//@Composable
//fun PostingListCard(postlist: PostingList, onClick: () -> Unit) {
//    Card(
//        elevation = 4.dp,
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 8.dp)
//            .shadow(elevation = 4.dp, shape = RoundedCornerShape(10.dp))
//            .clip(RoundedCornerShape(8.dp))
//            .clickable { onClick() } // Thêm sự kiện click để xem chi tiết
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//        ) {
//            Column(
//                modifier = Modifier
//                    .weight(1f)
//                    .padding(horizontal = 18.dp)
//            ) {
//                Text(
//                    text = postlist.title,
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 16.sp,
//                    maxLines = 1,
//                    overflow = TextOverflow.Ellipsis
//                )
//                Spacer(modifier = Modifier.height(3.dp))
//                Text(
//                    text = "Từ ${postlist.price}",
//                    color = Color.Red,
//                    fontSize = 14.sp
//                )
//                Spacer(modifier = Modifier.height(4.dp))
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Icon(
//                        imageVector = Icons.Default.LocationOn,
//                        contentDescription = "Location",
//                        tint = Color(0xFF03A9F4),
//                        modifier = Modifier.size(16.dp)
//                    )
//                    Text(
//                        text = " ${postlist.address}",
//                        color = Color.Gray,
//                        fontSize = 12.sp,
//                        maxLines = 1,
//                        overflow = TextOverflow.Ellipsis
//                    )
//                }
//            }
//        }
//    }
//}

///





