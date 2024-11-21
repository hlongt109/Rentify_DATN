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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.R
import com.rentify.user.app.model.BuildingWithRooms
import com.rentify.user.app.model.Room
import com.rentify.user.app.viewModel.RoomViewModel.RoomViewModel

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewFeetBuilding() {
    FeetBuilding(navController = rememberNavController())
}

@Composable
fun FeetBuilding(navController: NavController, viewModel: RoomViewModel = viewModel()) {
    val buildingWithRooms by viewModel.buildingWithRooms.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        try {
            viewModel.fetchBuildingsWithRooms("6727bee93361c4e22f074cd5")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        item {
            Text(
                text = "Danh sách tòa nhà",
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)
            )
        }

        if (buildingWithRooms.isNotEmpty()) {
            items(buildingWithRooms) { building ->
                BuildingCard(building = building, navController = navController, viewModel = viewModel)
            }
        } else {
            item {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Không có dữ liệu tòa nhà.",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun BuildingCard(building: BuildingWithRooms, navController: NavController, viewModel: RoomViewModel) {
    var isExpanded by remember { mutableStateOf(false) }

    // Quan sát danh sách phòng
    val rooms by viewModel.rooms.observeAsState(emptyList())

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                navController.navigate("ListRoom/${building._id}")
            }
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
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
                Column {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_navigate_next_24),
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .padding(start = 15.dp, end = 10.dp)
                    )
                }
            }
        }
    }
}





