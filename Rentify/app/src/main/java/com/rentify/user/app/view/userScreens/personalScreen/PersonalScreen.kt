package com.rentify.user.app.view.userScreens.personalScreen

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
import com.rentify.user.app.view.userScreens.personalScreen.components.ItemSComponent
import com.rentify.user.app.view.userScreens.personalScreen.components.LayoutItemName
import com.rentify.user.app.view.userScreens.personalScreen.components.MenuComponent


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PersonalScreen() {
    LayoutPersonal(navController= rememberNavController())
}

@Composable
fun LayoutPersonal(navController: NavHostController) {

    val scrollState= rememberScrollState()
    Column (
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        BannerComponent()
        LayoutItemName(navController)
        MenuComponent()
        ItemSComponent(navController)
    }
}