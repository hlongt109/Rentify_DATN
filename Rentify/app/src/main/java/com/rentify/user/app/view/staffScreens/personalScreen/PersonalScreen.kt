package com.rentify.user.app.view.staffScreens.personalScreen

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
import com.rentify.user.app.view.staffScreens.personalScreen.components.BodyPersonal
import com.rentify.user.app.view.staffScreens.personalScreen.components.FeetPersonal
import com.rentify.user.app.view.staffScreens.personalScreen.components.HeadPersonal


// _vanphuc : màn hình hồ sơ cá nhân
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PersonalScreenPreview(){
    PersonalScreen(navController = rememberNavController())
}
@Composable
fun PersonalScreen(navController: NavHostController){
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
            .background(color = Color.White)
    ){
        HeadPersonal(navController)
        BodyPersonal(navController)
        FeetPersonal(navController)
    }
}
