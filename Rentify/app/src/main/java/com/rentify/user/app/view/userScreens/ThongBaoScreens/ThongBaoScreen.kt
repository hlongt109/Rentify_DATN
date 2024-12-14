package com.rentify.user.app.view.userScreens.ThongBaoScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.LoginRepository.LoginRepository
import com.rentify.user.app.view.userScreens.ThongBaoScreens.Components.Thongbaobuttombar
import com.rentify.user.app.viewModel.LoginViewModel
import com.rentify.user.app.viewModel.NotificationViewmodel.NotificationViewmodel


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ThongBaoScreenPreview(){
    ThongBaoScreen(navController= rememberNavController())
}

@Composable
fun ThongBaoScreen(navController: NavHostController) {
    val context = LocalContext.current
    val apiService = RetrofitService()
    val userRepository = LoginRepository(apiService)
    val factory = remember(context) {
        LoginViewModel.LoginViewModelFactory(userRepository, context.applicationContext)
    }
    val loginViewModel: LoginViewModel = viewModel(factory = factory)
    val userId = loginViewModel.getUserData().userId
    val notificationViewmodel: NotificationViewmodel = viewModel()

    // Quan sát danh sách thông báo
    val notificationsDetail by notificationViewmodel.notifications.observeAsState()
    val error by notificationViewmodel.error.observeAsState()

    LaunchedEffect(Unit) {
        notificationViewmodel.getByUserNotification(userId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xfff7f7f7))
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Thongbaobuttombar(navController)
        Spacer(modifier = Modifier.height(10.dp))
        if (notificationsDetail != null) {
            notificationsDetail?.data?.forEach { notification ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White)
                        .padding(5.dp)
                ) {
                    Text(
                        text = notification.title,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = notification.content,
                        color = Color.Black,
                        fontStyle = FontStyle.Italic
                    )
                    Spacer(
                        modifier = Modifier
                            .height(1.dp)
                            .fillMaxWidth()
                            .background(color = Color.Gray)
                            .padding(top = 8.dp)
                    )
                }
            }
        } else if (!error.isNullOrEmpty()) {
            Text(
                text = error ?: "Đang tải dữ liệu...",
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                color = Color.Red
            )
        }

    }
}

