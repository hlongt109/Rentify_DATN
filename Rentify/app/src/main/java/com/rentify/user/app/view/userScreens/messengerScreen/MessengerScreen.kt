package com.rentify.user.app.view.userScreens.messengerScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.view.userScreens.messengerScreen.components.MessengerComponent
import com.rentify.user.app.view.userScreens.messengerScreen.components.headcomponent

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
        headcomponent(navController)
        MessengerComponent(navController)
    }

}