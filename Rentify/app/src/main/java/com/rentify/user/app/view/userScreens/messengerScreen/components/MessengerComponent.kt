package com.rentify.user.app.view.userScreens.messengerScreen.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.R
import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.LoginRepository.LoginRepository
import com.rentify.user.app.utils.Component.HeaderBar
import com.rentify.user.app.viewModel.LoginViewModel
import com.rentify.user.app.viewModel.UserViewmodel.ChatViewModel
import com.rentify.user.app.viewModel.UserViewmodel.chatUser

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MessengerComponentPreview() {
    MessengerComponent(navController = rememberNavController())
}

@Composable
fun MessengerComponent(
    navController: NavHostController,
    viewModel: ChatViewModel = viewModel(),
) {
    val context = LocalContext.current
    val apiService = RetrofitService()
    val userRepository = LoginRepository(apiService)
    val factory = remember(context) {
        LoginViewModel.LoginViewModelFactory(userRepository, context.applicationContext)
    }
    val loginViewModel: LoginViewModel = viewModel(factory = factory)
    val currentUserId = loginViewModel.getUserData().userId
    val chatList by viewModel.chatList.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(false)
    Log.d("TestChatList", "MessengerComponent: $chatList")
    LaunchedEffect(Unit) {
        viewModel.getChatList(
            userId = currentUserId,
        )
    }
    Column {
        HeaderBar(navController = navController, title = "Tin nhắn")
        Spacer(modifier = Modifier.padding(5.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .shadow(elevation = 3.dp, shape = RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(color = Color.White)
                    .clickable {
                        navController.navigate("TINNHAN")
                    }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ad),
                    contentDescription = null,
                    modifier = Modifier
                        .size(70.dp) // Kích thước nhỏ hơn so với ảnh ban đầu
                        .clip(CircleShape)
                        .padding(start = 20.dp)
                )
                Column(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = "Hỗ trợ tìm phòng",
                        fontSize = 16.sp, // Font size giảm một chút
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = "Admin",
                    fontSize = 14.sp,
                    color = Color.White,
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .background(
                            color = Color(0xff44acfe), // Màu nền xanh nhạt
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp) // Padding cho nhãn "Admin"
                )
            }
        }
        Spacer(modifier = Modifier.padding(top = 10.dp))
//        van phuc
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Log.d("KiemTraLai", "MessengerComponent: $chatList")
            LazyColumn {
                items(chatList, key = {user -> user}) { user ->
                    LaunchedEffect(user) {
                        loginViewModel.getInfoUser(user)
                    }
                    val userDataMap by loginViewModel.userDataMap.observeAsState(emptyMap())
                    val userData = userDataMap[user]
                    Log.d("UserId", "MessengerComponent: $user")
                    userData?.let { UserItem(it, navController) }
                }
            }
        }
    }
}
