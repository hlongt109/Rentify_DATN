package com.rentify.user.app.view.userScreens.roomdetailScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.view.userScreens.roomdetailScreen.components.ComfortComponent
import com.rentify.user.app.view.userScreens.roomdetailScreen.components.ImageComponent
import com.rentify.user.app.view.userScreens.roomdetailScreen.components.InteriorComponent
import com.rentify.user.app.view.userScreens.roomdetailScreen.components.LayoutNameComponent
import com.rentify.user.app.view.userScreens.roomdetailScreen.components.NoidungComponent
import com.rentify.user.app.view.userScreens.roomdetailScreen.components.RoomComponen
import com.rentify.user.app.view.userScreens.roomdetailScreen.components.ServiceComponent
import com.rentify.user.app.view.userScreens.roomdetailScreen.components.baidangPreview

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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
        }
        LayoutNameComponent(navController)
        ImageComponent()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
        ) {
        }
        NoidungComponent()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
        ) {
        }
        RoomComponen()
        ServiceComponent()
        ComfortComponent()
        InteriorComponent()
        baidangPreview()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        ) {
        }
    }
}