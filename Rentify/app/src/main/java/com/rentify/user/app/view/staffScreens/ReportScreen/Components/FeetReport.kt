package com.rentify.user.app.view.staffScreens.ReportScreen.Components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateMapOf
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.R
import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.LoginRepository.LoginRepository
import com.rentify.user.app.view.staffScreens.ListRommScreen.RoomItem
import com.rentify.user.app.viewModel.LoginViewModel
import com.rentify.user.app.viewModel.RoomViewModel.RoomViewModel
import com.rentify.user.app.viewModel.SupportViewmodel.SupportViewModel

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
    val apiService = RetrofitService()
    val userRepository = LoginRepository(apiService)
    val factory = remember(context) {
        LoginViewModel.LoginViewModelFactory(userRepository, context.applicationContext)
    }
    val loginViewModel: LoginViewModel = viewModel(factory = factory)
    var userId = loginViewModel.getUserData().userId

    val viewModel: RoomViewModel = viewModel(
        factory = RoomViewModel.RoomViewModeFactory(context)
    )
    val buildingWithRooms by viewModel.buildingWithRooms.observeAsState(emptyList())
    val expandedStates = remember { mutableStateMapOf<String, Boolean>() }

    // Biến trạng thái để theo dõi tòa nhà được chọn
    var selectedBuildingId by remember { mutableStateOf<String?>(null) }
    val supportViewModel: SupportViewModel = viewModel()
    LaunchedEffect(Unit) {
        try {
            viewModel.fetchBuildingsWithRooms(userId)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Điều kiện hiển thị
    if (selectedBuildingId == null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.Start
        ) {
            if (buildingWithRooms.isNotEmpty()) {
                buildingWithRooms.forEach { building ->
                    val isExpanded = expandedStates[building._id] ?: false

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .shadow(elevation = 3.dp, shape = RoundedCornerShape(12.dp))
                            .clip(RoundedCornerShape(12.dp))
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
                                    text ="Tòa nhà: ${building.nameBuilding}" , // Tên tòa nhà
                                )
                                Column(
                                    modifier = Modifier
                                        .padding(start = 10.dp)
                                        .weight(1f)
                                ) {
                                }
                                IconButton(
                                    onClick = {   expandedStates[building._id] = !isExpanded
                                        if (!isExpanded) {
                                            supportViewModel.fetchSupport(building._id, 0)
                                        }
                                    } // Toggle danh sách phòng
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
                            // Danh sách sự cố
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
//selectedBuildingId =
                                    ListSupportByRoom(navController, buildingId = building._id, 0)
                                }
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
    }
    else {
        // Hiển thị ListSupportByRoom
     //   ListSupportByRoom(navController, buildingId = selectedBuildingId!!, 1)
    }
}



