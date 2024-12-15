package com.rentify.user.app.view.staffScreens.NotificationScreen


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.livedata.observeAsState
import java.time.ZonedDateTime
import java.time.Duration
import java.util.*

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.request.ImageRequest
import com.rentify.user.app.R
import com.rentify.user.app.model.Model.Notification
import com.rentify.user.app.model.Room
import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.LoginRepository.LoginRepository
import com.rentify.user.app.view.staffScreens.NotificationScreen.Component.AppointmentAppBarc
import com.rentify.user.app.view.contract.contractComponents.ContractRoomListScreen
import com.rentify.user.app.viewModel.LoginViewModel
import com.rentify.user.app.viewModel.RoomViewModel.RoomViewModel
import com.rentify.user.app.viewModel.StaffViewModel.NotificationViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Notification_staffScreen(navController: NavController) {
    val context = LocalContext.current
    val viewModel: NotificationViewModel = viewModel()
    val notifications by viewModel.notifications.observeAsState()
    val errorMessage by viewModel.errorMessage.observeAsState()
    val scrollState = rememberScrollState()
    val apiService = RetrofitService()
    val userRepository = LoginRepository(apiService)
    val factory = remember(context) {
        LoginViewModel.LoginViewModelFactory(userRepository, context.applicationContext)
    }
    val loginViewModel: LoginViewModel = viewModel(factory = factory)
    val userId = loginViewModel.getUserData().userId
    LaunchedEffect(Unit) {
        viewModel.getNotificationsByUser(userId)
    }
    Scaffold(

        content = { paddingValues ->
            // Nội dung màn hình chính
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AppointmentAppBarc( onBackClick = {
                        // Logic quay lại, ví dụ: điều hướng về màn hình trước
                        navController.navigate("HOME_STAFF")
                    })
                    Column {
                        // Hiển thị danh sách thông báo
                        notifications?.let {
                            LazyColumn {
                                items(it) { notification ->
                                    NotificationItem(notification)
                                }
                            }
                        } ?: Text("Không có thông báo nào")

                    }
                }
            }
        }
    )

}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NotificationItem(notification: Notification) {
    val formattedTime = remember(notification.created_at) {
        formatTimeAgo(notification.created_at)
    }

    Card(
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .shadow(elevation = 0.dp, shape = RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(8.dp))
            .clickable { } // Thêm clickable vào đây
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.noti),
                contentDescription = null,

                modifier = Modifier
                    .size(50.dp, 50.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            // Nội dung thông báo
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = " ${notification.title}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${notification.content}",
                    color = Color.Black,
                    fontSize = 15.sp
                )
                // Hiển thị thời gian đã định dạng
                Text(
                    text = " $formattedTime",
                    color = Color.Black,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatTimeAgo(isoString: String): String {
    return try {
        // Chuyển chuỗi ISO thành đối tượng ZonedDateTime
        val dateTime = ZonedDateTime.parse(isoString)
        val currentTime = ZonedDateTime.now()

        // Tính toán sự chênh lệch thời gian
        val duration = Duration.between(dateTime, currentTime)

        when {
            duration.toDays() > 0 -> "${duration.toDays()} ngày trước"
            duration.toHours() > 0 -> "${duration.toHours()} giờ trước"
            duration.toMinutes() > 0 -> "${duration.toMinutes()} phút trước"
            else -> "Vừa xong"
        }
    } catch (e: Exception) {
        "Không rõ thời gian"
    }
}

//    Column {
//        Text("Tiêu đề: ")
//        Text("Tiêu đề: ${notification.title}")
//        Text("Nội dung: ${notification.content}")
//        Text("Trạng thái: ${notification.read_status}")
//        Text("Thời gian: ${notification.created_at}")
//    }
// Hàm định dạng ngày giờ

