package com.rentify.user.app.view.userScreens.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.view.userScreens.homeScreen.components.BannerComponent
import com.rentify.user.app.view.userScreens.homeScreen.components.DoitacComponent
import com.rentify.user.app.view.userScreens.homeScreen.components.KhamPhaComponent
import com.rentify.user.app.view.userScreens.homeScreen.components.SearchComponent
import com.rentify.user.app.view.userScreens.homeScreen.components.VideoComponent

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreen() {
    LayoutHome(navController = rememberNavController())
}

@Composable
fun LayoutHome(navController: NavHostController) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        item {
            // #1. Gọi component banner
            BannerComponent()
        }
        item {
            // #2. Gọi component search
            SearchComponent()
        }
        item {
            KhamPhaComponent()
        }
        item {
            VideoComponent()
        }
        item {
            DoitacComponent()
        }
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(900.dp)
                    .background(color = Color.White)
            ) {

            }
        }
    }
}

