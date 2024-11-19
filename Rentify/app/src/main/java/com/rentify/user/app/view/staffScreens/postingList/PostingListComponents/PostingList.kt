@file:OptIn(ExperimentalMaterial3Api::class)

package com.rentify.user.app.view.staffScreens.postingList.PostingListComponents

import android.content.Context
import android.system.Os.remove
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.rentify.user.app.network.RetrofitClient
import com.rentify.user.app.view.userScreens.contract.components.DialogCompose
import com.rentify.user.app.viewModel.PostViewModel
@OptIn(ExperimentalMaterialApi::class)
//@Composable
//fun PostListScreen(userId: String) {
//    val viewModel: PostViewModel = viewModel()
//    val rooms by viewModel.rooms
//    val context = LocalContext.current
//    val deleteStatus by viewModel.deleteStatus.observeAsState()
//    var isShowDialog by remember { mutableStateOf(false) }
//    var postIdToDelete by remember { mutableStateOf<String?>(null) } // Lưu trữ ID bài đăng cần xóa
//
//    // Gọi API để lấy dữ liệu
//    LaunchedEffect(userId) {
//        viewModel.getPostingList(userId)
//    }
//
//    // Hiển thị phản hồi khi trạng thái xóa thay đổi
//    deleteStatus?.let { success ->
//        if (success) {
//            Toast.makeText(context, "Đã xóa bài đăng", Toast.LENGTH_SHORT).show()
//        } else {
//            Toast.makeText(context, "Không thể xóa bài đăng", Toast.LENGTH_SHORT).show()
//        }
//        viewModel.resetDeleteStatus() // Đặt lại trạng thái xóa sau khi hiển thị thông báo
//    }
//
//    // Hiển thị dialog xác nhận xóa
//    if (isShowDialog) {
//        DialogCompose(
//            onConfirmation = {
//                isShowDialog = false
//                postIdToDelete?.let { id ->
//                    viewModel.deletePostWithFeedback(id) // Gọi API xóa bài đăng
//                    postIdToDelete = null // Đặt lại giá trị sau khi xóa
//                }
//            },
//            onCloseDialog = {
//                isShowDialog = false
//                postIdToDelete = null // Đặt lại nếu người dùng hủy
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
//        items(rooms, key = { it._id }) { room ->
//            val dismissState = rememberSwipeToDismissBoxState(
//                confirmValueChange = { direction ->
//                    if (direction == SwipeToDismissBoxValue.EndToStart) {
//                        postIdToDelete = room._id // Lưu ID bài đăng cần xóa
//                        isShowDialog = true     // Hiển thị hộp thoại xác nhận
//                        false                   // Không tự động xóa ngay
//                    } else {
//                        false
//                    }
//                }
//            )
//
//            SwipeToDismissBox(
//                state = dismissState,
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
//                content = {
//                    PostingListCard(room = room)
//                },
//                modifier = Modifier.swipeable(
//                    state = rememberSwipeableState(initialValue = 0),
//                    anchors = mapOf(
//                        0f to 0, // Trạng thái mặc định
//                        with(LocalDensity.current) { -0.3f * LocalConfiguration.current.screenWidthDp.dp.toPx() } to 1 // Chỉ vuốt sang trái
//                    ),
//                    thresholds = { _, _ -> FractionalThreshold(0.3f) }, // Ngưỡng 30% để chuyển trạng thái
//                    orientation = Orientation.Horizontal, // Vuốt theo chiều ngang
//                    enabled = true // Cho phép vuốt
//                )
//
//            )
//        }
//    }
//}
//
//
//@Composable
//fun PostingListCard(room: PostingList) {
//    Card(
//        elevation = 4.dp,
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 8.dp)
//            .shadow(elevation = 4.dp, shape = RoundedCornerShape(10.dp))
//            .clip(RoundedCornerShape(8.dp))
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
//                    text = room.title,  // Hiển thị title thay vì name
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 16.sp,
//                    maxLines = 1,
//                    overflow = TextOverflow.Ellipsis
//                )
//                Spacer(modifier = Modifier.height(3.dp))
//                Text(
//                    text = "Từ ${room.price}",
//                    color = Color.Red,
//                    fontSize = 14.sp
//                )
//
//                Spacer(modifier = Modifier.height(4.dp))
//
//                // Address
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Icon(
//                        imageVector = Icons.Default.LocationOn,
//                        contentDescription = "Location",
//                        tint = Color(0xFF03A9F4),
//                        modifier = Modifier.size(16.dp)
//                    )
//                    Text(
//                        text = " ${room.address}",
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


data class PostingList(
    val _id: String,
    val title: String,  // Tương ứng với trường title trong API
    val price: String,  // Tương ứng với trường price trong API
    val address: String // Tương ứng với trường address trong API
)
@Composable
fun PostListScreen(navController: NavController, userId: String) {
    val viewModel: PostViewModel = viewModel()
    val rooms by viewModel.rooms
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
        items(rooms, key = { it._id }) { room ->
            val dismissState = rememberSwipeToDismissBoxState(
                confirmValueChange = { direction ->
                    if (direction == SwipeToDismissBoxValue.EndToStart) {
                        postIdToDelete = room._id
                        isShowDialog = true
                        false // Không tự động xóa ngay, đợi người dùng xác nhận
                    } else {
                        false
                    }
                }
            )

            SwipeToDismissBox(
                state = dismissState,
                backgroundContent = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 8.dp)
                            .clip(RoundedCornerShape(10.dp))
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
                },
                content = {
                    PostingListCard(
                        room = room,
                        onClick = {
                            navController.navigate("post_detail/${room._id}")
                        }
                    )
                }
            )
        }
    }
}

@Composable
fun PostingListCard(room: PostingList, onClick: () -> Unit) {
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() } // Thêm sự kiện click để xem chi tiết
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
                    text = room.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = "Từ ${room.price}",
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
                        text = " ${room.address}",
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






