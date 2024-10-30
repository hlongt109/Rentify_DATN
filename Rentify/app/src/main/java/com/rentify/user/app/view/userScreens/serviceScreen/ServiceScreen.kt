package com.rentify.user.app.view.userScreens.serviceScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController



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
        Text(text = "LayoutService")
    }
}