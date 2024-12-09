package com.rentify.user.app.view.userScreens.SurroundingRoomsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mapbox.maps.extension.compose.MapboxMap
import com.rentify.user.app.utils.Component.HeaderBar
import com.rentify.user.app.view.userScreens.SurroundingRoomsScreen.Component.MapboxMapView

@Composable
fun SurroundingRoomScreen(navController: NavController){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
            .navigationBarsPadding()
    ){
        Column(
            modifier = Modifier.fillMaxSize()
        ){
            HeaderBar(navController, title = "Phòng trọ xung quanh")
            MapboxMapView()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SurroundingRoomScreenPreview(){
    SurroundingRoomScreen(navController = rememberNavController())
}

