package com.rentify.user.app.view.navigator

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import com.rahad.riobottomnavigation.composables.RioBottomNavItemData
import com.rentify.user.app.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.rahad.riobottomnavigation.composables.RioBottomNavigation
import com.rentify.user.app.R.drawable.bottomiconmain
import com.rentify.user.app.view.userScreens.homeScreen.HomeScreen
import com.rentify.user.app.view.userScreens.homeScreen.LayoutHome
import com.rentify.user.app.view.userScreens.laundryScreen.LaundryScreen
import com.rentify.user.app.view.userScreens.messengerScreen.LayoutMessenger
import com.rentify.user.app.view.userScreens.messengerScreen.MessengerScreen
import com.rentify.user.app.view.userScreens.personalScreen.LayoutPersonal
import com.rentify.user.app.view.userScreens.personalScreen.PersonalScreen
import com.rentify.user.app.view.userScreens.serviceScreen.LayoutService

@Composable
fun BottomNavigation(
    navHostController: NavHostController
){
    val items = listOf(
        R.drawable.bottomhomeicon,
        R.drawable.bottomserviceicon,
        R.drawable.bottomchaticon,
        R.drawable.bottompersonicon
    )

    val labels = listOf(
        "Trang chính",
        "Dịch vụ",
        "Tin nhắn",
        "Cá nhân"
    )

    var selectedIndex = rememberSaveable { mutableIntStateOf(0) }

    val buttons = items.mapIndexed { index, iconData ->
        RioBottomNavItemData(
            imageVector = ImageVector.vectorResource(iconData),
            selected = index == selectedIndex.intValue,
            onClick = { selectedIndex.intValue = index },
            label = labels[index]
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize().statusBarsPadding().navigationBarsPadding(),
        bottomBar = {
            BottomNavigationBar(buttons = buttons, navHostController)
        },
    ) { innerPadding ->
        ScreenContent(selectedIndex.intValue, modifier = Modifier.padding(innerPadding), navHostController)
    }

}

@Composable
fun ScreenContent(selectedIndex: Int, modifier: Modifier = Modifier, navHostController: NavHostController) {
    when (selectedIndex) {
        0 -> LayoutHome(navHostController)
        1 -> LayoutService(navHostController)
        2 -> LayoutMessenger(navHostController)
        3 -> LayoutPersonal(navHostController)
    }
}
@Composable
fun BottomNavigationBar(buttons: List<RioBottomNavItemData>, navHostController: NavHostController) {
    RioBottomNavigation(
        fabIcon = ImageVector.vectorResource(id = bottomiconmain),
        buttons = buttons,
        fabSize = 70.dp,
        barHeight = 70.dp,
        selectedItemColor = Color(0xff44ACFE),
        fabBackgroundColor = Color(0xff44ACFE),
        onFabClick = {
            navHostController.navigate("CATEGORYPOST")
        }
    )
}