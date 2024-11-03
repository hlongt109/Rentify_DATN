package com.rentify.user.app.view.userScreens.roomdetailsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.view.userScreens.roomdetailsScreen.components.ComfortComponent
import com.rentify.user.app.view.userScreens.roomdetailsScreen.components.ImageComponent
import com.rentify.user.app.view.userScreens.roomdetailsScreen.components.InteriorComponent
import com.rentify.user.app.view.userScreens.roomdetailsScreen.components.NameComponent
import com.rentify.user.app.view.userScreens.roomdetailsScreen.components.NoidungComponent
import com.rentify.user.app.view.userScreens.roomdetailsScreen.components.RoomComponen
import com.rentify.user.app.view.userScreens.roomdetailsScreen.components.ServiceComponent

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
        NameComponent(navController)
        ImageComponent()
        NoidungComponent()
        RoomComponen()
        ServiceComponent()
        ComfortComponent()
        InteriorComponent()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        ) {
        }
    }
}
