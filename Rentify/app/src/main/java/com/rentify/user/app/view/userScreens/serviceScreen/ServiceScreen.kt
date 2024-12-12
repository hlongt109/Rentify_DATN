package com.rentify.user.app.view.userScreens.serviceScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.view.userScreens.homeScreen.components.BannerComponent
import com.rentify.user.app.view.userScreens.personalScreen.components.ItemNameComponent
import com.rentify.user.app.view.userScreens.personalScreen.components.ItemSComponent
import com.rentify.user.app.view.userScreens.personalScreen.components.MenuComponent
import com.rentify.user.app.view.userScreens.serviceScreen.components.ItemComponent
import com.rentify.user.app.view.userScreens.serviceScreen.components.TeudeComponent


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ServiceScreen() {
    LayoutService(navController= rememberNavController())
}

@Composable
fun LayoutService(navController: NavHostController) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ){
        ItemComponent(navController)
    }
}