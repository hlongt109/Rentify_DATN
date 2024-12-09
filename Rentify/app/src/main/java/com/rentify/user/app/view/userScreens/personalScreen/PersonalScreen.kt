package com.rentify.user.app.view.userScreens.personalScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.view.userScreens.homeScreen.components.BannerComponent
import com.rentify.user.app.view.userScreens.homeScreen.components.LayoutBanner2
import com.rentify.user.app.view.userScreens.homeScreen.components.LayoutSearch
import com.rentify.user.app.view.userScreens.personalScreen.components.LayoutItemName
import com.rentify.user.app.view.userScreens.personalScreen.components.LayoutItems
import com.rentify.user.app.view.userScreens.personalScreen.components.LayoutMenu
import com.rentify.user.app.view.userScreens.personalScreen.components.MenuComponent


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PersonalScreen() {
    LayoutPersonal(navController= rememberNavController())
}

@Composable
fun LayoutPersonal(
    navController: NavHostController
) {

    val scrollState= rememberScrollState()
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xfff7f7f7))
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(224.dp)
        ) {
            LayoutBanner2()
            Box(
                modifier = Modifier.offset(y = 190.dp)
            ) {
                LayoutItemName(navController)
            }
        }
        Spacer(modifier = Modifier.padding(20.dp))
        LayoutMenu(navController)
        Spacer(modifier = Modifier.padding(3.dp))
        LayoutItems(navController)
        Spacer(modifier = Modifier.padding(35.dp))
    }
}