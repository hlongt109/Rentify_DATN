package com.rentify.user.app.view.staffScreens.UpdateRoomScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.view.auth.components.HeaderComponent

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun UpdateRoomScreenPreview(){
    UpdateRoomScreen(navController= rememberNavController())
}
@Composable
fun UpdateRoomScreen(navController: NavHostController){
    HeaderComponent(
        backgroundColor = Color(0xffffffff),
        title = "Update Room",
        navController = navController
    )
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ){

    }
}
