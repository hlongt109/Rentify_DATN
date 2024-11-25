package com.rentify.user.app.view.userScreens.BillScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.MainActivity.ROUTER
import com.rentify.user.app.view.auth.components.HeaderComponent
import com.rentify.user.app.view.userScreens.BillScreen.Component.CustomTabBar

@Composable
fun BillScreen(navController: NavController) {

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
                    0 -> UnPaidScreen( navController )
                    1 -> PaidScreen()
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBillScreen() {
    BillScreen(navController = rememberNavController())
}