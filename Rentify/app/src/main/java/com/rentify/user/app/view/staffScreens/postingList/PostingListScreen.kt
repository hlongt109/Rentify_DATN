package com.rentify.user.app.view.staffScreens.postingList

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rentify.user.app.MainActivity
import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.LoginRepository.LoginRepository
import com.rentify.user.app.view.staffScreens.postingList.PostingListComponents.AppointmentAppBar
import com.rentify.user.app.view.staffScreens.postingList.PostingListComponents.PostListScreen

import com.rentify.user.app.viewModel.LoginViewModel

@Preview(showBackground = true)
@Composable
fun PostingListScreenPreview() {
    PostingListScreen(navController = rememberNavController())
}

@Composable
fun PostingListScreen(navController: NavHostController) {
    val context = LocalContext.current
    val apiService = RetrofitService()
    val userRepository = LoginRepository(apiService)
    val factory = remember(context) {
        LoginViewModel.LoginViewModelFactory(userRepository, context.applicationContext)
    }
    val loginViewModel: LoginViewModel = viewModel(factory = factory)
    var userId = loginViewModel.getUserData().userId
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {     navController.navigate(MainActivity.ROUTER.ADDPOST_staff.name) },
                containerColor = Color(0xFF2196F3),
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier.padding(bottom = 30.dp).padding(end = 20.dp)
            ) {
                // Icon bên trong FAB
                Icon(
                    imageVector = Icons.Default.Add, // Thay thế bằng icon của bạn
                    contentDescription = "Add",
                    tint = Color.White
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End, // Vị trí của FAB (có thể là Center)
        content = { paddingValues ->
            // Nội dung màn hình chính
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                // Gọi nội dung chính của màn hình
                Column {
                    AppointmentAppBar( onBackClick = {
                        // Logic quay lại, ví dụ: điều hướng về màn hình trước
                        navController.navigate("HOME_STAFF")
                    })
                    PostListScreen(navController,userId = userId)
                }
            }
        }
    )
}
