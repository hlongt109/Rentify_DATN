package com.rentify.user.app.view.staffScreens.homeScreen.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.rentify.user.app.MainActivity
import com.rentify.user.app.R
import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.LoginRepository.LoginRepository
import com.rentify.user.app.viewModel.LoginViewModel
import com.rentify.user.app.viewModel.StaffViewModel.NotificationViewModel
import com.rentify.user.app.viewModel.UserViewmodel.UserViewModel

@Composable
fun HeaderSection(navController: NavHostController) {
    val viewModel: UserViewModel = viewModel()
    val viewModel_noti: NotificationViewModel = viewModel()
    val userDetail by viewModel.user.observeAsState()  // Quan sát LiveData người dùng
    val errorMessage by viewModel.error.observeAsState()  // Quan sát LiveData lỗi
    val context = LocalContext.current
    val apiService = RetrofitService()
    val userRepository = LoginRepository(apiService)
    val factory = remember(context) {
        LoginViewModel.LoginViewModelFactory(userRepository, context.applicationContext)
    }
    val unreadCount by viewModel_noti.unreadCount.observeAsState(0)
    val loginViewModel: LoginViewModel = viewModel(factory = factory)
    val userId = loginViewModel.getUserData().userId
    // Gọi API để lấy thông tin người dùng khi composable được gọi
    LaunchedEffect(Unit) {
        viewModel.getUserDetailById(userId)  // Gọi API với userId hợp lệ
    }
    LaunchedEffect(Unit) {
        viewModel_noti.getNotificationsByUser(userId)
    }
    Row(
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth()
            .background(color = Color(0xffffffff)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .width(100.dp)
                .padding(5.dp)
                .clickable(
                    onClick = { /**/ },
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }),
            horizontalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 25.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Ảnh từ API
                userDetail?.profile_picture_url?.let { photoUrl ->
                    val uriAnh = "http://10.0.2.2:3000/$photoUrl"
                    AsyncImage(
                        model = uriAnh,
                        contentDescription = "Profile Picture",
                        error = painterResource(R.drawable.anhdaidien), // Ảnh lỗi
                        modifier = Modifier
                            .size(30.dp)  // Giữ nguyên kích thước ảnh
                            .clip(CircleShape) // Bo tròn ảnh
                            .clickable { navController.navigate(MainActivity.ROUTER.PersonalStaff.name) },
                        contentScale = ContentScale.Crop
                    )
                } ?: run {
                    // Ảnh mặc định nếu không có ảnh từ API
                    Image(
                        painter = painterResource(id = R.drawable.staff),
                        contentDescription = null,
                        modifier = Modifier
                            .clip(CircleShape) // Bo tròn hoàn toàn
                            .background(Color.White)
                            .size(30.dp, 30.dp) // Đảm bảo kích thước giống như ảnh từ API
                            .clickable { navController.navigate(MainActivity.ROUTER.PersonalStaff.name) }
                    )
                }
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier.size(60.dp, 60.dp)
            )

            androidx.compose.material.Text(
                text = "Quản lý đơn giản, mọi việc dễ dàng",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        }
        NotificationIconWithBadge(userId,unreadCount,navController)

    }
}
@Composable
fun NotificationIconWithBadge(userId: String, unreadCount: Int, navController: NavController) {
    val viewModel_noti: NotificationViewModel = viewModel()
    Box(
        modifier = Modifier
            .width(100.dp)
            .padding(5.dp)
            .clickable(
                onClick = {
                    viewModel_noti.markAllAsRead(userId)
                    navController.navigate(MainActivity.ROUTER.Notification_staffScreen.name)
                },
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ),
        contentAlignment = Alignment.Center // Canh chỉnh Image ở giữa Box
    ) {
        // Image notification
        Image(
            painter = painterResource(id = R.drawable.noti),
            contentDescription = null,
            modifier = Modifier.size(30.dp, 30.dp)
        )

        // Badge hiển thị số lượng thông báo
        if (unreadCount > 0) {
            Box(
                modifier = Modifier
                    .padding(end =20.dp )
                    .size(18.dp) // Kích thước badge
                    .clip(CircleShape)
                    .background(Color.Red) // Màu nền đỏ
                    .align(Alignment.TopEnd), // Căn badge vào góc trên bên phải của Box
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = unreadCount.toString(),
                    color = Color.White, // Màu chữ
                    fontSize = 12.sp, // Kích thước chữ
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewHeaderSection() {
    // Sử dụng NavHostController giả lập (hoặc không dùng nếu không cần điều hướng)
    HeaderSection(navController = rememberNavController())
}