package com.rentify.user.app.view.userScreens.appointment


import android.R.attr.contentDescription
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.*
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.runtime.*
import androidx.compose.material.icons.filled.Clear
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.LoginRepository.LoginRepository

import com.rentify.user.app.view.userScreens.serviceScreen.LayoutService
import com.rentify.user.app.viewModel.BookingViewModel
import com.rentify.user.app.viewModel.LoginViewModel


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppointmentScreenPreview() {
    AppointmentScreen(navController = rememberNavController())
}


@Composable
fun AppointmentScreen(navController: NavHostController) {
    val context = LocalContext.current
    val apiService = RetrofitService()
    val userRepository = LoginRepository(apiService)
    val factory = remember(context) {
        LoginViewModel.LoginViewModelFactory(userRepository, context.applicationContext)
    }
    val loginViewModel: LoginViewModel = viewModel(factory = factory)
    val userId = loginViewModel.getUserData().userId
    var searchText by remember { mutableStateOf("") } // Lưu trữ trạng thái tìm kiếm

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .statusBarsPadding()
            .background(Color.White),
    ) {
        AppointmentAppBar(
            onBackClick = { navController.popBackStack() },
            canlendarClick = {}
        )
        SearchBar(searchText = searchText, onSearchTextChange = { searchText = it }) // Truyền vào searchText và callback
        AppointmentTabRow(userId, searchText, navController) // Truyền vào searchText
    }
}



@Composable
fun AppointmentAppBar(
    onBackClick: () -> Unit,
    canlendarClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp)
            .height(50.dp)
            .background(Color(0xFFFFFFFF)) // Background color
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(imageVector = Icons.Default.ArrowBackIosNew, contentDescription = "Back")
                }
                Text(
                    text = "Quản lý lịch hẹn",
                    style = MaterialTheme.typography.h6,
                    textAlign = TextAlign.Start
                )
            }
            IconButton(onClick = canlendarClick) {
                Icon(imageVector = Icons.Default.CalendarMonth, contentDescription = "Calendar", tint = Color(0xff5d5d5d))
            }
        }
    }
}

@Composable
fun SearchBar(searchText: String, onSearchTextChange: (String) -> Unit) {
    var isFocused by remember { mutableStateOf(false) } // Trạng thái focus
    val focusRequester = remember { FocusRequester() }

    BasicTextField(
        value = searchText,
        onValueChange = { onSearchTextChange(it) }, // Cập nhật text khi thay đổi
        textStyle = TextStyle(color = Color.Gray, fontSize = 16.sp),
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 5.dp)
            .focusRequester(focusRequester) // Đăng ký focus requester
            .onFocusChanged { focusState -> isFocused = focusState.isFocused }, // Theo dõi focus
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color(0xFFf7f7f7), RoundedCornerShape(15.dp)),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = Color.Black,
                        modifier = Modifier.size(30.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    if (searchText.isEmpty() && !isFocused) { // Hiển thị placeholder khi ô trống và không được focus
                        Text(
                            text = "Nhập thông tin tìm kiếm",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    }
                    innerTextField() // Hiển thị nội dung của TextField
                }
            }
        },
        singleLine = true
    )
}

@Composable
fun AppointmentTabRow(
    userId: String,
    searchText: String, // Nhận searchText từ AppointmentScreen
    navController: NavController
) {
    var selectedTabIndex by remember { mutableStateOf(0) }

    Column {
        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex,
            backgroundColor = Color.White,
            contentColor = Color.Transparent,
            edgePadding = 0.dp, // Loại bỏ padding mặc định
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    color = Color(0xFF5DADFF), // Màu gạch chân
                    height = 2.dp // Độ dày của gạch chân
                )
            }
        ) {
            val tabTitles = listOf("Chờ xác nhận", "Phòng chưa xem", "Phòng đã xem", "Huỷ xem phòng")

            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    modifier = Modifier,
                    text = {
                        Text(
                            text = title,
                            color = if (selectedTabIndex == index) Color(0xFF5DADFF) else Color(0xFFA7A7A7),
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold
                        )
                    }
                )
            }
        }

        // Truyền searchText xuống RoomListScreen
        RoomListScreen(userId, selectedTabIndex, searchText, navController = navController)
    }
}
