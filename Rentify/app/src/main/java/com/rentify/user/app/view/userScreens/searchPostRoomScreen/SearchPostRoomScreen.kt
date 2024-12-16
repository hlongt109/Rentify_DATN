package com.rentify.user.app.view.userScreens.searchPostRoomScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.rentify.user.app.R
import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.LoginRepository.LoginRepository
import com.rentify.user.app.view.userScreens.searchPostRoomScreen.Component.CustomTabBar
import com.rentify.user.app.view.userScreens.searchPostRoomScreen.Component.HeaderComponent
import com.rentify.user.app.view.userScreens.searchPostRoomateScreen.ActivePostsScreen
import com.rentify.user.app.view.userScreens.searchPostRoomateScreen.HiddenPostsScreen
import com.rentify.user.app.view.userScreens.searchPostRoomateScreen.PendingPostsScreen
import com.rentify.user.app.viewModel.LoginViewModel
import com.rentify.user.app.viewModel.PostViewModel.PostViewModel

@Composable
fun SearchPostRoonmScreen
            (navController: NavController) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val apiService = RetrofitService()
    val userRepository = LoginRepository(apiService)
    val context = LocalContext.current
    val factory = remember(context) {
        LoginViewModel.LoginViewModelFactory(userRepository, context.applicationContext)
    }
    val loginViewModel: LoginViewModel = viewModel(factory = factory)
    val userId = loginViewModel.getUserData().userId

    // Tạo và lấy instance của PostViewModel
    val postViewModel: PostViewModel = viewModel()

    // Gọi API để lấy danh sách bài đăng
    LaunchedEffect(userId) {
        postViewModel.getPostingList_user(userId, postType = "seek")
    }
    val tabs = listOf("Đang hoạt động", "Đã bị ẩn")

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {       navController.navigate("ADDPOST?postType=seek")  },
                containerColor = Color(0xFF2196F3),
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier.padding(bottom = 30.dp).padding(end = 20.dp)
            ) {
                // Icon bên trong FAB
                androidx.compose.material3.Icon(
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
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color(0xfff7f7f7))

                ) {
                    SearchPostRoomTopBar(navController)
                    CustomTabBar(
                        items = tabs,
                        selectedIndex = selectedTabIndex,
                        onTabSelected = { index ->
                            selectedTabIndex = index
                        }
                    )

                    when (selectedTabIndex) {
                        0 -> ActivePostsScreen(
                            navController = navController,
                            posts = postViewModel.activePosts.value,
                            onDeletePost = { postId -> postViewModel.deletePostWithFeedback_user(postId) }
                        )
                        1 -> HiddenPostsScreen(
                            navController = navController,
                            posts = postViewModel.hiddenPosts.value,
                            onDeletePost = { postId -> postViewModel.deletePostWithFeedback_user(postId) }
                        )
                    }

                }
            }
        }
    )
}

@Composable
fun SearchPostRoomTopBar(
    navController: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        androidx.compose.material.IconButton(
            onClick = { navController.popBackStack() }
        ) {
            androidx.compose.material.Icon(
                imageVector = Icons.Filled.ArrowBackIosNew,
                contentDescription = "Back"
            )
        }

        Spacer(modifier = Modifier.width(8.dp)) // Khoảng cách giữa icon và text

        androidx.compose.material.Text(
            text = "Bài đăng tìm phòng ",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 18.sp,
            style = MaterialTheme.typography.h6
        )
    }
}