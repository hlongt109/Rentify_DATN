package com.rentify.user.app.view.userScreens.roomdetailsScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.R
import com.rentify.user.app.view.userScreens.roomdetailsScreen.components.ComfortComponent
import com.rentify.user.app.view.userScreens.roomdetailsScreen.components.ImageComponent
import com.rentify.user.app.view.userScreens.roomdetailsScreen.components.InteriorComponent
import com.rentify.user.app.view.userScreens.roomdetailsScreen.components.Layoutbaidang
import com.rentify.user.app.view.userScreens.roomdetailsScreen.components.NameComponent
import com.rentify.user.app.view.userScreens.roomdetailsScreen.components.NoidungComponent
import com.rentify.user.app.view.userScreens.roomdetailsScreen.components.RoomComponen
import com.rentify.user.app.view.userScreens.roomdetailsScreen.components.ServiceComponent
import com.rentify.user.app.view.userScreens.roomdetailsScreen.components.baidangPreview

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
        NameComponent(navController)
        ImageComponent()
        NoidungComponent()
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
