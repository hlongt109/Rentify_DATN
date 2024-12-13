package com.rentify.user.app.view.staffScreens.scheduleScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rentify.user.app.MainActivity.ROUTER
import com.rentify.user.app.model.Model.BookingStaff
import com.rentify.user.app.network.ApiStaff.RetrofitStaffService
import com.rentify.user.app.repository.StaffRepository.BookingRepository.BookingRespository
import com.rentify.user.app.utils.Component.getLoginViewModel
import com.rentify.user.app.view.components.TopBar
import com.rentify.user.app.view.staffScreens.scheduleScreen.components.ScheduleItem
import com.rentify.user.app.viewModel.BookingStaffViewModel.BookingStaffViewModel
import com.rentify.user.app.viewModel.BookingStaffViewModel.BookingViewModelFactory

@Composable
fun ScheduleScreen(nav: NavController) {

    val tabItems = listOf(
        TabItems(
            title = "Chờ xác nhận",
        ),
        TabItems(
            title = "Đã xác nhận",
        ),
        TabItems(
            title = "Đã xem",
        ),
        TabItems(
            title = "Đã hủy",
        ),
    )
    val context = LocalContext.current
    val loginViewModel = getLoginViewModel(context)
    val userData = loginViewModel.getUserData()
    val staffId = userData.userId
    val repository = BookingRespository(RetrofitStaffService)
    val viewModel: BookingStaffViewModel = viewModel (factory = BookingViewModelFactory(staffId, repository))
    LaunchedEffect(staffId) {
        viewModel.loadBookingList(staffId)
    }
    val bookingList by viewModel.listBooking.observeAsState(emptyList())
    Log.d("BookingList", "ScheduleScreen: "+bookingList)

    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val pagerState = rememberPagerState { tabItems.size }
    LaunchedEffect(selectedTabIndex) {
        pagerState.animateScrollToPage(selectedTabIndex)
    }
    LaunchedEffect(pagerState.currentPage) {
        if (!pagerState.isScrollInProgress) {
            selectedTabIndex = pagerState.currentPage
        }
    }

    Scaffold(
        containerColor = Color(0xffffffff),
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            TopBar(
                title = "Lịch hẹn xem phòng",
                onBackClick = { nav.popBackStack() }
            )
        }

    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            ScrollableTabRow(
                selectedTabIndex = selectedTabIndex,
                modifier = Modifier.background(Color(0xffffffff)),
                edgePadding = 0.dp,
                indicator = { tabPositions ->
                    Box(
                        Modifier
                            .tabIndicatorOffset(tabPositions[selectedTabIndex])
                            .height(3.dp)
                            .background(Color("#c0392b".toColorInt()))
                    )
                }
            ) {
                tabItems.forEachIndexed { index, item ->
                    Tab(
                        selected = index == selectedTabIndex,
                        onClick = {
                            selectedTabIndex = index
                        },
                        text = {
                            Text(
                                text = item.title,
                                color = if (index == selectedTabIndex) Color("#c0392b".toColorInt()) else Color("#99a3a4".toColorInt())
                            )
                        }

                    )
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(Color(0xFFF5F5F5)),

            ) { index ->
                val filteredSchedules = when (index) {
                    0 -> bookingList.filter { it.status == 0 }
                    1 -> bookingList.filter { it.status == 1 }
                    2 -> bookingList.filter { it.status == 2 }
                    3 -> bookingList.filter { it.status == 3 }
                    else -> bookingList
                }

                ScheduleList(filteredSchedules, nav)
            }
        }

    }
}

@Composable
fun ScheduleList(schedules: List<BookingStaff>, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)

    ) {
        if (schedules.isEmpty()) {
            Text(
                text = "Không có lịch hẹn nào",
                modifier = Modifier.fillMaxSize(),
                color = Color.Gray
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(schedules.size) { index ->
                    ScheduleItem(
                        schedules[index],
                        onClick = { id -> navController.navigate("${ROUTER.ScheduleDetails.name}/${id}") }
                    )
                }
            }
        }
    }
}

data class TabItems(
    val title: String,
)
