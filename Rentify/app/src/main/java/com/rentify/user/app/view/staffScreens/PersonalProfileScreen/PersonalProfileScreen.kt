package com.rentify.user.app.view.staffScreens.PersonalProfileScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.view.staffScreens.PersonalProfileScreen.components.BodyPersonalProfile
import com.rentify.user.app.view.staffScreens.PersonalProfileScreen.components.BodyPersonalProfile1
import com.rentify.user.app.view.staffScreens.PersonalProfileScreen.components.FeetPersonalProfile
import com.rentify.user.app.view.staffScreens.PersonalProfileScreen.components.FeetPersonalProfile1
import com.rentify.user.app.view.staffScreens.PersonalProfileScreen.components.FeetPersonalProfile2
import com.rentify.user.app.view.staffScreens.PersonalProfileScreen.components.HeadPersonalProfile

// _vanphuc : màn hình thông tin cá nhân
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Preview(){
    PersonalProfileScreen(navController= rememberNavController())
}
@Composable
fun PersonalProfileScreen(navController: NavHostController){
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
            .background(color = Color.White)
    ){
        HeadPersonalProfile(navController)
        BodyPersonalProfile(navController)
        FeetPersonalProfile(navController)
        BodyPersonalProfile1(navController)
        FeetPersonalProfile1(navController)
        FeetPersonalProfile2(navController)
    }
}

