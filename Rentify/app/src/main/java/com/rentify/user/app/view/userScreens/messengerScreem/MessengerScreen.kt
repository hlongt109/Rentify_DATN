package com.rentify.user.app.view.userScreens.messengerScreem

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
fun MessengerScreen() {
    LayoutMessenger(navController= rememberNavController())
}

@Composable
fun LayoutMessenger(navController: NavHostController) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ){
        Text(text = "LayoutMessenger")
    }

}