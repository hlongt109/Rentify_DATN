package com.rentify.user.app.view.userScreens.homeScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.view.userScreens.homeScreen.components.BannerComponent
import com.rentify.user.app.view.userScreens.homeScreen.components.DoitacComponent
import com.rentify.user.app.view.userScreens.homeScreen.components.KhamPhaComponent
import com.rentify.user.app.view.userScreens.homeScreen.components.LayoutItemHome
import com.rentify.user.app.view.userScreens.homeScreen.components.LayoutSearch
import com.rentify.user.app.view.userScreens.homeScreen.components.SearchComponent
import com.rentify.user.app.view.userScreens.homeScreen.components.VideoComponent

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreen() {
    LayoutHome(navController = rememberNavController())
}

@Composable
fun LayoutHome(navController: NavHostController) {
    val scrollState = rememberScrollState()
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        // #1. Gọi component banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(screenHeight.dp / 2.5f)
        ){
            BannerComponent()
            // #2. Gọi component search
            Box (
                modifier = Modifier.offset(y = screenHeight.dp/7.5f)
            ){
                LayoutSearch(navController)
            }
        }
        KhamPhaComponent()
        VideoComponent()
        DoitacComponent()
        LayoutItemHome(navController)
        LayoutItemHome(navController)
        LayoutItemHome(navController)
    }
}