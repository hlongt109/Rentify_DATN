package com.rentify.user.app.view.staffScreens.ReportScreen.Components
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.R
import com.rentify.user.app.model.BuildingWithRooms
import com.rentify.user.app.viewModel.RoomViewModel.RoomViewModel

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewFeetBuilding() {
    FeetBuilding(navController = rememberNavController())
}

@Composable
fun FeetBuilding(navController: NavController, viewModel: RoomViewModel = viewModel()) {
    // Quan sát LiveData buildingWithRooms
    val buildingWithRooms by viewModel.buildingWithRooms.observeAsState(emptyList())

    // Lấy dữ liệu khi composable được tạo lần đầu
    LaunchedEffect(Unit) {
        try {
            viewModel.fetchBuildingsWithRooms("6727bee93361c4e22f074cd5")
        } catch (e: Exception) {
            e.printStackTrace() // Ghi log lỗi nếu có
        }
    }

    // Hiển thị danh sách hoặc thông báo nếu không có dữ liệu
    if (buildingWithRooms.isNotEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            buildingWithRooms.forEach { building ->
                BuildingCard(building = building, navController = navController, viewModel = viewModel)
            }
        }
    } else {
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
@Composable
fun BuildingCard(building: BuildingWithRooms, navController: NavController, viewModel: RoomViewModel) {
    var isExpanded by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    // Quan sát danh sách phòng
    val rooms by viewModel.roomNames.observeAsState(emptyList())

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
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
                        text = "${building.rooms?.size ?: 0} phòng",
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                }

                // Nút mở rộng/thu gọn
                IconButton(
                    onClick = {
                        isExpanded = !isExpanded
                        // Khi mở rộng, lấy danh sách phòng cho tòa nhà này
                        if (isExpanded && building._id != null) {
                            viewModel.fetchRoomNamesByBuildingId(building._id)
                        }
                    }
                ) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = if (isExpanded) "Thu gọn" else "Mở rộng",
                        tint = Color.Black
                    )
                }
            }

            // Hiển thị phòng khi mở rộng
            AnimatedVisibility(
                visible = isExpanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White)
                        .padding(15.dp)
                        .verticalScroll(scrollState),
                    verticalArrangement = Arrangement.Center
                ) {
                    if (rooms.isNotEmpty()) {
                        rooms.forEach { roomName ->
                            RoomCard(roomName)
                        }
                    } else {
                        Text(
                            text = "Không có phòng nào.",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    }

                    Button(
                        onClick = {
                            navController.navigate("ADDROOM/${building._id}")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xfffb6b53))
                    ) {
                        Text(
                            modifier = Modifier.padding(6.dp),
                            text = "Thêm phòng",
                            fontSize = 16.sp,
                            color = Color(0xffffffff)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RoomCard(roomName: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .background(Color(0xffe0e0e0))
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.roomm),
            contentDescription = null,
            modifier = Modifier.size(40.dp)
        )
        Text(
            text = roomName ?: "Phòng không xác định",
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.padding(start = 10.dp)
        )
    }
}

