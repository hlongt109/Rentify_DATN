package com.rentify.user.app.view.fav

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.CropDin
import androidx.compose.material.icons.filled.CropSquare
import androidx.compose.material.icons.filled.People
import com.rentify.user.app.R
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.view.userScreens.appointment.AppointmentScreen

@Preview(showBackground = true)
@Composable
fun FavScreenPreview() {
    FavScreen(navController = rememberNavController())
}

@Composable
fun FavScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FavTopAppBarWithActions(
            onBackClick = { /* Handle back icon click */ },
            onCalendarClick = { /* Handle calendar icon click */ },
        )
        FavRoomListScreen()
    }
}


@Composable
fun FavTopAppBarWithActions(
    onBackClick: () -> Unit,
    onCalendarClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "Bài đăng yêu thích",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        backgroundColor = Color(0xFFFFFFFF), // Customize background color
        contentColor = Color.Black, // Customize text/icon color
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(imageVector = Icons.Default.ArrowBackIosNew, contentDescription = "Back")
            }
        },
        actions = {
            IconButton(onClick = onCalendarClick) {
                Icon(imageVector = Icons.Default.CalendarMonth, contentDescription = "Calendar")
            }
        },
        elevation = 4.dp // Customize elevation if needed
    )
}

@Composable
fun FavRoomListScreen() {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(getFavRoomList()) { room ->
            FavRoomCard(room = room)
        }
    }
}

@Composable
fun FavRoomCard(room: FavRoom) {
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(8.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Room Image

            Image(
                painter = painterResource(id = R.drawable.a),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 16.dp)
            )

            // Room Info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = room.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = "Từ ${room.price}",
                    color = Color.Red,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(4.dp))

                // Room Details Row
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CropSquare,
                        contentDescription = "Size",
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = " ${room.size}",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))

                // Room Details Row
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.People,
                        contentDescription = "People",
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = " ${room.capacity} người",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Address
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location",
                        tint = Color(0xFF03A9F4),
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = " ${room.address}",
                        color = Color.Gray,
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

// Data model for room
data class FavRoom(
    val name: String,
    val price: String,
    val size: String,
    val capacity: Int,
    val address: String,
    val imageRes: Int
)

// Sample data for room list
fun getFavRoomList(): List<FavRoom> {
    return listOf(
        FavRoom(
            "Phòng Đẹp - Rẻ - Hiện đại",
            "5.000.000đ/tháng",
            "18 - 25m2",
            3,
            "27/143 Xuân Phương, Hà Nội",
            1
        ),
        FavRoom(
            "Phòng Đẹp - Rẻ - Hiện đại",
            "5.000.000đ/tháng",
            "18 - 25m2",
            3,
            "27/143 Xuân Phương, Hà Nội",
            1
        ),
        FavRoom(
            "Phòng Đẹp - Rẻ - Hiện đại",
            "5.000.000đ/tháng",
            "18 - 25m2",
            3,
            "27/143 Xuân Phương, Hà Nội",
            1
        )
    )
}
