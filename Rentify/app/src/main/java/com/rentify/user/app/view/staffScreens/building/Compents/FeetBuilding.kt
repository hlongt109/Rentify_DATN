package com.rentify.user.app.view.staffScreens.ReportScreen.Components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.request.ImageRequest
import com.rentify.user.app.R
import com.rentify.user.app.model.BuildingWithRooms
import com.rentify.user.app.model.Room
import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.LoginRepository.LoginRepository
import com.rentify.user.app.view.staffScreens.ListRommScreen.ListRoomScreen
import com.rentify.user.app.view.staffScreens.ListRommScreen.RoomItem
import com.rentify.user.app.viewModel.LoginViewModel
import com.rentify.user.app.viewModel.RoomViewModel.RoomViewModel

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewFeetBuilding() {
    FeetBuilding(navController = rememberNavController())
}

@Composable
fun FeetBuilding(navController: NavController) {
    val context = LocalContext.current
    val viewModel: RoomViewModel = viewModel(
        factory = RoomViewModel.RoomViewModeFactory(context)
    )
    val apiService = RetrofitService()
    val userRepository = LoginRepository(apiService)
    val factory = remember(context) {
        LoginViewModel.LoginViewModelFactory(userRepository, context.applicationContext)
    }
    val loginViewModel: LoginViewModel = viewModel(factory = factory)
    val userId = loginViewModel.getUserData().userId

    val buildingWithRooms by viewModel.buildingWithRooms.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(false)

    LaunchedEffect(Unit) {
        try {
            viewModel.fetchBuildingsWithRooms(userId)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xfff7f7f7))
    ) {
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(R.drawable.loading)
                        .decoderFactory(GifDecoder.Factory())
                        .build(),
                    contentDescription = "Loading GIF",
                    modifier = Modifier
                        .size(150.dp)
                        .align(Alignment.Center)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                item {
                    Spacer(modifier = Modifier.padding(5.dp))
                }
                items(buildingWithRooms) { building ->
                    BuildingCard(
                        building = building,
                        navController = navController
                    )
                }
            }
        }
    }
}


@Composable
fun BuildingCard(
    building: BuildingWithRooms,
    navController: NavController
) {
    var isExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 7.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
    ) {
        Column {
            // Header hiển thị thông tin tòa nhà
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.building),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .padding(start = 15.dp)
                )
                Spacer(modifier = Modifier.padding(5.dp))
                Column(
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = building.nameBuilding ?: "Tên tòa nhà không xác định",
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                }
                Row {
                    Text(
                        text = "Còn trống: ",
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                    Text(
                        text = "${building.rooms.size} phòng",
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                }
                IconButton(
                    onClick = { isExpanded = !isExpanded } // Toggle danh sách phòng
                ) {
                    Icon(
                        imageVector = if (isExpanded)
                            Icons.Default.KeyboardArrowUp
                        else
                            Icons.Default.KeyboardArrowDown,
                        contentDescription = if (isExpanded) "Collapse" else "Expand",
                        tint = Color.Black,
                        modifier = Modifier.size(25.dp)
                    )
                }
            }

            // Danh sách phòng trong AnimatedVisibility
            AnimatedVisibility(
                visible = isExpanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White)
                        .padding(5.dp)
                ) {
                    building.rooms.forEach { room ->
                        RoomItem(room = room, navController)
                    }
                    Box(modifier = Modifier.padding(top = 8.dp)) {
                        Button(
                            onClick = {
                                navController.navigate("ADDROOM/${building._id}")
                            }, modifier = Modifier.height(50.dp).fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp), colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xffFB6B53)
                            )
                        ) {
                            androidx.compose.material3.Text(
                                text = "Thêm phòng",
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.cairo_regular)),
                                color = Color(0xffffffff)
                            )
                        }

                    }
                }
            }
        }
    }
}

