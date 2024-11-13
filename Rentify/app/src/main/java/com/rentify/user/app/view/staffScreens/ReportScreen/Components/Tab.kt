package com.rentify.user.app.view.staffScreens.ReportScreen.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.rentify.user.app.ui.theme.backgroundGrayColor
import com.rentify.user.app.ui.theme.colorTabBar
import com.rentify.user.app.ui.theme.colorUnTabBar

@Composable
fun DangYeuCau(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        FeetReportyeucau(navController)

    }
}

@Composable
fun DaHoanThanh() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        FeetReporthoanthanh()
    }
}
@Composable
fun CustomTab(
    items: List<String>,
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(
            selectedTabIndex = selectedIndex,
            backgroundColor = Color.White,
            contentColor = Color.White,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedIndex]),
                    height = 4.dp,
                    color = colorTabBar
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            items.forEachIndexed { index, title ->
                androidx.compose.material.Tab(
                    selected = selectedIndex == index,
                    onClick = { onTabSelected(index) },
                    text = {
                        Text(
                            title,
                            color = when {
                                selectedIndex == index && index == 0 -> Color.Red // Đang yêu cầu
                                selectedIndex == index && index == 1 -> Color.Green // Đã hoàn thành
                                else -> Color(0xFFd8d8d8)
                            },
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    },
                    selectedContentColor = colorTabBar,
                    unselectedContentColor = colorUnTabBar
                )
            }
        }
    }
}