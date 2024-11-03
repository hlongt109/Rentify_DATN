package com.rentify.user.app.view.userScreens.serviceScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.view.userScreens.homeScreen.components.BannerComponent
import com.rentify.user.app.view.userScreens.serviceScreen.components.ItemNameComponent
import com.rentify.user.app.view.userScreens.serviceScreen.components.ItemSComponent
import com.rentify.user.app.view.userScreens.serviceScreen.components.MenuComponent


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ServiceScreen() {
    LayoutService(navController= rememberNavController())
}

@Composable
fun LayoutService(navController: NavHostController) {
val scrollState= rememberScrollState()
    Column (
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        BannerComponent()
        ItemNameComponent(navController)
        MenuComponent()
        ItemSComponent()
    }
}