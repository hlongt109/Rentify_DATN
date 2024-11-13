package com.rentify.user.app.view.userScreens.IncidentReport

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding

import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import androidx.navigation.NavController

import androidx.navigation.compose.rememberNavController

import com.rentify.user.app.R
import com.rentify.user.app.view.userScreens.IncidentReport.Components.CustomTabBar
import com.rentify.user.app.view.userScreens.IncidentReport.Components.HeaderComponent


@Composable
fun IncidentReportScreen(navController: NavController) {
    var selectedTabIndex by remember { mutableStateOf(0) }
   val tabs = listOf("Đang yêu cầu", "Đã hoàn thành")
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xfff7f7f7))
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
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
                    0 -> Requesting(navController)
                    1 -> Completed(navController)
                }
            }

            // Nút Add cố định ở góc dưới bên phải

            Box(
                modifier = Modifier
                    //  .background(Color(0xff70cbff))
                    .fillMaxSize()
                    .padding(25.dp),

                contentAlignment = Alignment.BottomEnd // Cố định Row ở góc dưới bên phải
            ){    Row(
                    modifier = Modifier
                        .shadow(3.dp, shape = RoundedCornerShape(30.dp))
                        .background(color = Color(0xFF84d8ff))
                        .border(
                            width = 0.dp,
                            color = Color(0xFFEEEEEE),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clickable(onClick = { navController.navigate("ADDINCIDENTREPORT") },
                            indication = null, // Bỏ hiệu ứng tối khi nhấn
                            interactionSource = remember { MutableInteractionSource() })

                        .padding(15.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.addr),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp, 30.dp)
                    )
                }

            }

        }
    }

    @Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingLayoutIncidentReportScreenScreen() {
    IncidentReportScreen(navController = rememberNavController())
}
@Composable
fun Requesting(navController: NavController) {
    // Nội dung cho các bài đăng đang chờ duyệt
}

@Composable
fun Completed(navController: NavController) {
    // Nội dung cho các bài đăng đang hoạt động
}


