package com.rentify.user.app.view.userScreens.laundrydetailscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.view.userScreens.laundrydetailscreen.component.NDComponent
import com.rentify.user.app.view.userScreens.laundrydetailscreen.component.anhComponent
import com.rentify.user.app.view.userScreens.laundrydetailscreen.component.tenchitietComponent
import com.rentify.user.app.view.userScreens.roomdetailsScreen.components.ComfortComponent
import com.rentify.user.app.view.userScreens.roomdetailsScreen.components.ImageComponent
import com.rentify.user.app.view.userScreens.roomdetailsScreen.components.InteriorComponent
import com.rentify.user.app.view.userScreens.roomdetailsScreen.components.NameComponent
import com.rentify.user.app.view.userScreens.roomdetailsScreen.components.NoidungComponent
import com.rentify.user.app.view.userScreens.roomdetailsScreen.components.RoomComponen
import com.rentify.user.app.view.userScreens.roomdetailsScreen.components.ServiceComponent
import com.rentify.user.app.view.userScreens.roomdetailsScreen.components.baidangPreview

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun LaundryDetailScreenPreview() {
    LaundryDetailScreenScreen(navController = rememberNavController())
}

@Composable
fun LaundryDetailScreenScreen(navController: NavHostController) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        tenchitietComponent(navController)
        anhComponent()
        NDComponent(navController)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        ) {
        }
    }
}