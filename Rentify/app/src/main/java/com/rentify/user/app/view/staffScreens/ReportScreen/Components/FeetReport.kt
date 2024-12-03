package com.rentify.user.app.view.staffScreens.ReportScreen.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.R
import com.rentify.user.app.viewModel.RoomViewModel.RoomViewModel

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewFeetReport() {
    FeetReportyeucau(navController = rememberNavController())
}

@Composable
fun FeetReportyeucau(
    navController: NavHostController,
) {
    val context = LocalContext.current
    val viewModel: RoomViewModel = viewModel(
        factory = RoomViewModel.RoomViewModeFactory(context)
    )
    val buildingWithRooms by viewModel.buildingWithRooms.observeAsState(emptyList())

    // Biến trạng thái để theo dõi tòa nhà được chọn
    var selectedBuildingId by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            viewModel.fetchBuildingsWithRooms("674f1c2975eb705d0ff112b6")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Điều kiện hiển thị
    if (selectedBuildingId == null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            if (buildingWithRooms.isNotEmpty()) {
                buildingWithRooms.forEach { building ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
                            .clip(RoundedCornerShape(12.dp))
                            .clickable {
                                // Cập nhật trạng thái khi nhấn vào tòa nhà
                                selectedBuildingId = building._id
                            }
                    ) {
                        Column {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(70.dp)
                                    .background(color = Color.White)
                                    .padding(10.dp)
                            ) {
                                // Icon hoặc hình ảnh của tòa nhà
                                Image(
                                    painter = painterResource(id = R.drawable.building), // Hình ảnh đại diện tòa nhà
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFFF5F5F5))
                                        .padding(5.dp)
                                )
                                Text(
                                    text = building.nameBuilding, // Tên tòa nhà
                                )
                                Text(
                                    text = "Cảnh báo sự cố !!! ⚠️  ",
                                    color = Color.Red,
                                    modifier = Modifier.padding(start = 20.dp)// Tên tòa nhà
                                )
                                Column(
                                    modifier = Modifier
                                        .padding(start = 10.dp)
                                        .weight(1f)
                                ) {
                                }
                                // Mũi tên điều hướng
                                Image(
                                    painter = painterResource(id = R.drawable.baseline_navigate_next_24),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(24.dp)
                                )
                            }
                        }
                    }
                }
            } else {
                // Khi không có tòa nhà nào
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No buildings available",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Gray
                        )
                    )
                }
            }
        }
    } else {
        // Hiển thị ListSupportByRoom
        ListSupportByRoom(navController, buildingId = selectedBuildingId!!, 1)
    }
}



