package com.rentify.user.app.view.userScreens.searchPostRoomateScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
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
import com.rentify.user.app.view.contract.contractComponents.ContractRoomListScreen

import com.rentify.user.app.view.staffScreens.postingList.PostingListComponents.PostingList
import com.rentify.user.app.view.userScreens.contract.components.DialogCompose
import com.rentify.user.app.view.userScreens.searchPostRoomScreen.SearchPostRoonmScreen

import com.rentify.user.app.view.userScreens.searchPostRoomateScreen.Component.CustomTabBar
import com.rentify.user.app.view.userScreens.searchPostRoomateScreen.Component.HeaderComponent

import com.rentify.user.app.view.userScreens.searchPostRoomateScreen.Component.PostListWithSwipe
import com.rentify.user.app.view.userScreens.searchPostRoomateScreen.Component.PostingListCard
import com.rentify.user.app.viewModel.LoginViewModel
import com.rentify.user.app.viewModel.PostViewModel.PostViewModel


@Composable
fun SearchPostRoomateScreen(navController: NavController) {
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
        postViewModel.getPostingList_user(userId, postType = "roomate")
    }

    val tabs = listOf("Đang chờ duyệt", "Đang hoạt động", "Đã bị ẩn")
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {       navController.navigate("ADDPOST?postType=roomate")  },
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
                    SearchPostRoommateTopBar(navController)
                    CustomTabBar(
                        items = tabs,
                        selectedIndex = selectedTabIndex,
                        onTabSelected = { index ->
                            selectedTabIndex = index
                        }
                    )

                    when (selectedTabIndex) {
                        0 -> PendingPostsScreen(
                            navController = navController,
                            posts = postViewModel.pendingPosts.value,
                            onDeletePost = { postId -> postViewModel.deletePostWithFeedback_user(postId) }
                        )
                        1 -> ActivePostsScreen(
                            navController = navController,
                            posts = postViewModel.activePosts.value,
                            onDeletePost = { postId -> postViewModel.deletePostWithFeedback_user(postId) }
                        )
                        2 -> HiddenPostsScreen(
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
fun PendingPostsScreen(
    navController: NavController,
    posts: List<PostingList>,
    onDeletePost: (String) -> Unit
) {
    PostListWithSwipe(navController = navController, posts = posts, onDeletePost = onDeletePost)
}

@Composable
fun ActivePostsScreen(
    navController: NavController,
    posts: List<PostingList>,
    onDeletePost: (String) -> Unit
) {
    PostListWithSwipe(navController = navController, posts = posts, onDeletePost = onDeletePost)
}

@Composable
fun HiddenPostsScreen(
    navController: NavController,
    posts: List<PostingList>,
    onDeletePost: (String) -> Unit
) {
    PostListWithSwipe(navController = navController, posts = posts, onDeletePost = onDeletePost)
}

@Composable
fun SearchPostRoommateTopBar(
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
            text = "Tìm người ở ghép",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 18.sp,
            style = MaterialTheme.typography.h6
        )
    }
}


