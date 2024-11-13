package com.rentify.user.app.view.appointment

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rentify.user.app.R
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true)
@Composable
fun RoomListScreen() {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(getRoomList()) { room ->
            RoomCard(room = room)
        }
    }
}

@Composable
fun RoomCard(room: Room) {
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 3.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(8.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Calendar Icon
            Image(
                painter = painterResource(id = R.drawable.calendar),
                contentDescription = "Calendar",
                modifier = Modifier.size(35.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Room Info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = room.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Từ ${room.price}",
                    color = Color.Red,
                    fontSize = 14.sp
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location",
                        tint = Color(0xFF03A9F4),
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = room.address,
                        color = Color.Gray,
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

// Data model for room
data class Room(val name: String, val price: String, val address: String)

// Sample data for room list
fun getRoomList(): List<Room> {
    return listOf(
        Room("Phòng Đẹp - Rẻ - Hiện đại", "5.000.000đ/tháng", "27/143 Xuân Phương, Hà Nội"),
        Room("Phòng Đẹp - Rẻ - Hiện đại", "5.000.000đ/tháng", "27/143 Xuân Phương, Hà Nội"),
        Room("Phòng Đẹp - Rẻ - Hiện đại", "5.000.000đ/tháng", "27/143 Xuân Phương, Hà Nội"),
        Room("Phòng Đẹp - Rẻ - Hiện đại", "5.000.000đ/tháng", "27/143 Xuân Phương, Hà Nội"),
        Room("Phòng Đẹp - Rẻ - Hiện đại", "5.000.000đ/tháng", "27/143 Xuân Phương, Hà Nội"),
        Room("Phòng Đẹp - Rẻ - Hiện đại", "5.000.000đ/tháng", "27/143 Xuân Phương, Hà Nội"),
        Room("Phòng Đẹp - Rẻ - Hiện đại", "5.000.000đ/tháng", "27/143 Xuân Phương, Hà Nội"),
        Room("Phòng Đẹp - Rẻ - Hiện đại", "5.000.000đ/tháng", "27/143 Xuân Phương, Hà Nội"),
    )
}
