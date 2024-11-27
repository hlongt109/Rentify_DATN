package com.rentify.user.app.view.userScreens.searchPostRoomScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.R
import com.rentify.user.app.view.userScreens.searchPostRoomScreen.Component.CustomTabBar
import com.rentify.user.app.view.userScreens.searchPostRoomScreen.Component.HeaderComponent
@Composable
fun SearchPostRoonmScreen
            (navController: NavController) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Đang chờ duyệt", "Đang hoạt động", "Đã bị ẩn")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xfff7f7f7))
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        HeaderComponent(navController)
        CustomTabBar(
            items = tabs,
            selectedIndex = selectedTabIndex,
            onTabSelected = { index ->
                selectedTabIndex = index
            }
        )

        when (selectedTabIndex) {
            0 -> PendingPostsScreen(navController)
            1 -> ActivePostsScreen(navController)
            2 -> HiddenPostsScreen(navController)
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingLayoutSearchPostRoonmScreen() {
    SearchPostRoonmScreen(navController = rememberNavController())
}
@Composable
fun PendingPostsScreen(navController: NavController) {
    // Nội dung cho các bài đăng đang chờ duyệt
}

@Composable
fun ActivePostsScreen(navController: NavController) {
    // Nội dung cho các bài đăng đang hoạt động
}

@Composable
fun HiddenPostsScreen(navController: NavController) {
    // Nội dung cho các bài đăng đã bị ẩn
}
