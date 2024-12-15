package com.rentify.user.app.view.userScreens.homeScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.request.ImageRequest
import com.rentify.user.app.R
import com.rentify.user.app.view.userScreens.homeScreen.components.BannerComponent
import com.rentify.user.app.view.userScreens.homeScreen.components.DoitacComponent
import com.rentify.user.app.view.userScreens.homeScreen.components.KhamPhaComponent
import com.rentify.user.app.view.userScreens.homeScreen.components.LayoutItemHome
import com.rentify.user.app.view.userScreens.homeScreen.components.LayoutSearch
import com.rentify.user.app.view.userScreens.homeScreen.components.LayoutVideo
import com.rentify.user.app.view.userScreens.homeScreen.components.SearchComponent
import com.rentify.user.app.viewModel.HomeScreenViewModel

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreen() {
    LayoutHome(navController = rememberNavController())
}

@Composable
fun LayoutHome(
    navController: NavHostController,
    homeScreenViewModel: HomeScreenViewModel = viewModel()
) {
    val listRoom by homeScreenViewModel.roomList.observeAsState(emptyList())
    val isLoading by homeScreenViewModel.isLoading.observeAsState(true)
    var selectedCity by remember { mutableStateOf("Hà Nội") }
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xfff7f7f7))
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp)
            ) {
                BannerComponent()
                Box(
                    modifier = Modifier.offset(y = 143.dp)
                ) {
                    LayoutSearch(navController) { city ->
                        selectedCity = city
                    }
                }
            }
        }

        item {
            KhamPhaComponent()
        }
        item {
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(R.drawable.loading)
                            .decoderFactory(GifDecoder.Factory())
                            .build(),
                        contentDescription = "Loading GIF",
                        modifier = Modifier.size(150.dp)
                    )
                }
            } else {
                Spacer(modifier = Modifier.padding(2.dp))
                LayoutVideo(
                    districtViewModel = viewModel(),
                    navController = navController,
                    city = selectedCity
                )
            }
        }
        item {
            DoitacComponent(navController)
        }

        if (isLoading) {
            items(3) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(R.drawable.loading)
                            .decoderFactory(GifDecoder.Factory())
                            .build(),
                        contentDescription = "Loading GIF",
                        modifier = Modifier.size(150.dp)
                    )
                }
            }
        } else {
            items(listRoom.take(3)) { room ->
                LayoutItemHome(navController = navController, room = room)
            }
        }

        item {
            Spacer(modifier = Modifier.padding(40.dp))
        }

    }
}
