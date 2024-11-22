package com.rentify.user.app.view.staffScreens.BillScreenStaff

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rentify.user.app.view.auth.components.HeaderComponent
import com.rentify.user.app.view.staffScreens.BillScreenStaff.Componenet.PaidStaffScreen
import com.rentify.user.app.view.staffScreens.BillScreenStaff.Componenet.UnPaidStaffScreen
import com.rentify.user.app.view.userScreens.BillScreen.Component.CustomTabBar
import com.rentify.user.app.view.userScreens.BillScreen.PaidScreen
import com.rentify.user.app.view.userScreens.BillScreen.UnPaidScreen

@Composable
fun BillScreenStaff(navController: NavController) {

    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Chưa thanh toán", "Đã thanh toán")
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            HeaderComponent(backgroundColor = Color.White, title = "Hóa đơn", navController = navController)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White)
            ) {
                //tabbar
                CustomTabBar(
                    items = tabs,
                    selectedIndex = selectedTabIndex,
                    onTabSelected = { index ->
                        selectedTabIndex = index
                    }
                )
                // Nội dung của từng tab

                when (selectedTabIndex) {
                    0 -> UnPaidStaffScreen( navController )
                    1 -> PaidStaffScreen(navController)
                }

            }
        }
    }
}