package com.rentify.user.app.view.staffScreens.building

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.view.staffScreens.ReportScreen.Components.FeetReportyeucau
import com.rentify.user.app.view.userScreens.cancelContract.components.HeaderSection

@Composable
fun BuildingScreen(navController: NavController){

    val scrollState = rememberScrollState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .background(color = Color(0xfff3f3f3))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()

            //  .padding(bottom = screenHeight.dp / 6f)
        ) {
            HeaderSection(backgroundColor = Color.White, title = "Tòa nhà và căn hộ", navController = navController)
            Spacer(modifier = Modifier.height(20.dp))
            FeetReportyeucau(navController )
        }
    }
    }
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingLayoutBuildingScreen() {
    BuildingScreen(navController = rememberNavController())
}