package com.rentify.user.app.view.staffScreens.ListRommScreen


import android.util.Log
import androidx.compose.runtime.livedata.observeAsState


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.R
import com.rentify.user.app.model.Room
import com.rentify.user.app.view.userScreens.cancelContract.components.HeaderSection
import com.rentify.user.app.viewModel.LoginViewModel
import com.rentify.user.app.viewModel.RoomViewModel.RoomViewModel


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ListRoomScreenPreview() {
    val context = LocalContext.current
    val roomViewModel: RoomViewModel = viewModel(
        factory = RoomViewModel.RoomViewModeFactory(context = context)
    )
    ListRoomScreen(
        navController = rememberNavController(),
        buildingId = "sample_building_id"
    )
}


@Composable
fun ListRoomScreen(
    navController: NavHostController,
    buildingId: String?
) {
    val context = LocalContext.current
    val viewModel: RoomViewModel = viewModel(
        factory = RoomViewModel.RoomViewModeFactory(context)
    )
    // Gọi hàm fetchRoomsForBuilding khi buildingId không null
    LaunchedEffect(buildingId) {
        buildingId?.let {
            viewModel.fetchRoomsForBuilding(it)
        }
    }


    // Sử dụng observeAsState để lấy danh sách phòng
    val rooms by viewModel.rooms.observeAsState(initial = emptyList())


    Column(
        modifier = Modifier.padding(top = 20.dp)
    ) {
        HeaderSection(
            backgroundColor = Color.White,
            title = "Danh sách phòng",
            navController = navController
        )
        rooms.forEach { room ->
            RoomItem(room = room,navController)
        }


        Row(
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp, top = 10.dp)
                .background(color = Color(0xFF84d8ff))
                .border(width = 1.dp, color = Color(0xFF84d8ff), shape = RoundedCornerShape(20.dp))
                .clickable(
                    onClick = {
                        navController.navigate("ADDROOM/${buildingId}")
                    },
                    indication = null, // Tắt hiệu ứng gợn sóng
                    interactionSource = remember { MutableInteractionSource() }
                ),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically // Căn giữa theo chiều dọc
        ) {
            androidx.compose.material3.Text(
                text = "Thêm phòng",
                modifier = Modifier.padding(5.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold, // Đậm chữ
                color = Color.White, // Màu chữ trắng
                lineHeight = 24.sp // Khoảng cách giữa các dòng
            )
        }
    }
}


@Composable
fun RoomItem(room: Room,navController: NavHostController) {
    Log.d("UpdatePostScreen", "Post Detail: ${room.room_name}")
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(9.dp)
            .border(width = 1.dp, color = Color(0xffdddddd), shape = RoundedCornerShape(20.dp))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(color = Color.White)
                .border(
                    width = 1.dp,
                    color = Color(0xFFfafafa),
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(start = 20.dp)
                .clickable {
                    val id=room.id
                    navController.navigate("RoomDetailScreen/${id}")
                }
        ) {
            Image(
                painter = painterResource(id = R.drawable.roomm),
                contentDescription = null,
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
                    .padding(top = 5.dp)
                    .clip(CircleShape)
            )
            Column(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .weight(1f) // Cho phép cột chiếm không gian còn lại
            ) {
                Text(
                    text = "${room.room_name}",
                    fontSize = 18.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 5.dp),
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = painterResource(id = R.drawable.baseline_navigate_next_24),
                contentDescription = null,
                modifier = Modifier
                    .width(30.dp)
                    .height(30.dp)
                    .padding(end = 5.dp)
                    .clip(CircleShape)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}
