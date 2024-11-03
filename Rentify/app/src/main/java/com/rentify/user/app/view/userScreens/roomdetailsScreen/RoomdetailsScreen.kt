package com.rentify.user.app.view.userScreens.roomdetailsScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.view.userScreens.roomdetailsScreen.components.ImageComponent
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RoomdetailsScreen() {
    LayoutRoomdetails(navController = rememberNavController())
}

@Composable
fun LayoutRoomdetails(navController: NavHostController) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        ImageComponent()
    }
}
