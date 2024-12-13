package com.rentify.user.app.view.staffScreens.ReportScreen.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewBodyReport() {
    BodyReport(navController = rememberNavController())
}

@Composable
fun BodyReport(navController: NavHostController) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Đang yêu cầu", "Đã hoàn thành ")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        // Tab bar
        CustomTab(
            items = tabs,
            selectedIndex = selectedTabIndex,
            onTabSelected = { index ->
                selectedTabIndex = index
            }
        )
        // Nội dung của mỗi tab
        when (selectedTabIndex) {
            0 -> DangYeuCau(navController)
            1 -> DaHoanThanh(navController)
        }
    }
}
