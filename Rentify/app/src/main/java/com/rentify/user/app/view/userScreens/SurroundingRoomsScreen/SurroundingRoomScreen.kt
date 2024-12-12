package com.rentify.user.app.view.userScreens.SurroundingRoomsScreen

import android.util.Log
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mapbox.maps.extension.compose.MapboxMap
import com.rentify.user.app.utils.Component.HeaderBar
import com.rentify.user.app.view.userScreens.SurroundingRoomsScreen.Component.MapboxMapView
import com.rentify.user.app.viewModel.UserViewmodel.AutofillViewModel
import com.rentify.user.app.viewModel.UserViewmodel.MapViewModel

@Composable
fun SurroundingRoomScreen(
    navController: NavController,
    latitude: Double = 0.0,
    longitude: Double = 0.0,
    viewModel: AutofillViewModel = viewModel(),
    listViewModel: MapViewModel = viewModel(),
) {
    Log.d("TestToaDo", "SurroundingRoomScreen: $longitude,$latitude")
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            HeaderBar(navController, title = "Phòng trọ xung quanh")
            MapboxMapView(
                navController,
                viewModel,
                listViewModel,
                longitude,
                latitude,
                )
        }
    }
}

