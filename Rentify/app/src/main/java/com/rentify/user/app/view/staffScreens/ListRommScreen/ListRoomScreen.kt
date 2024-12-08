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
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.request.ImageRequest
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

    LaunchedEffect(buildingId) {
        buildingId?.let {
            viewModel.fetchRoomsForBuilding(it)
        }
    }

    val rooms by viewModel.rooms.observeAsState(initial = emptyList())
    val isLoading by viewModel.isLoading.observeAsState(false)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .statusBarsPadding()
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
            Column(
                modifier = Modifier
            ) {
                ListRoomTopBar(navController = navController)
                rooms.forEach { room ->
                    RoomItem(room = room, navController)
                }
            }
        }

        FloatingActionButton(
            onClick = {
                navController.navigate("ADDROOM/${buildingId}")
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            backgroundColor = Color(0xFFFB6B53),
            contentColor = Color.White
        ) {
            Icon(
                painter = painterResource(id = R.drawable.add),
                contentDescription = "Thêm phòng",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun RoomItem(room: Room, navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .shadow(3.dp, RoundedCornerShape(10.dp))
            .border(
                width = if (room.status == 1) 1.dp else 0.dp, // Kiểm tra trạng thái phòng
                color = if (room.status == 1) Color(0xFFFF0000) else Color.Transparent, // Màu đỏ nếu status = 1
                shape = RoundedCornerShape(10.dp)
            )
            .clickable {
                val id = room.id
                navController.navigate("RoomDetailScreen/${id}")
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(color = Color.White)
                .padding(start = 20.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.listroom),
                contentDescription = null,
                modifier = Modifier
                    .width(30.dp)
                    .height(30.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier
                    .weight(1f) // Cho phép cột chiếm không gian còn lại
            ) {
                Text(
                    text = "${room.room_name}",
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 5.dp),
                    fontWeight = FontWeight.Medium
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

@Composable
fun ListRoomTopBar(
    navController: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        androidx.compose.material.IconButton(
            onClick = { navController.popBackStack() }
        ) {
            androidx.compose.material.Icon(
                imageVector = Icons.Filled.ArrowBackIosNew,
                contentDescription = "Back"
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        androidx.compose.material.Text(
            text = "Danh sách phòng",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 18.sp,
            style = MaterialTheme.typography.h6
        )
    }
}
