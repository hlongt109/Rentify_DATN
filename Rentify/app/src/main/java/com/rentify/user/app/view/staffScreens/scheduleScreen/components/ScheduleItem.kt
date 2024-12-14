package com.rentify.user.app.view.staffScreens.scheduleScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentify.user.app.model.Model.BookingStaff

@Composable
fun ScheduleItem(
    item: BookingStaff,
    onClick: (id: String) -> Unit,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFFFFF),
            contentColor = Color(0xFFFFFFFF)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable { onClick(item._id) }

    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "Phòng  : ${item.room.room_name}",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF888888),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Địa chỉ : ${item.building_id.address}",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xff363636),
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Giờ hẹn: ${
                        item.check_in_date.split(" ")
                            .let { "${it.getOrElse(1) { "" }} - ${it.getOrElse(0) { "" }}" }
                    }",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF888888),
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier.fillMaxHeight()
                    .width(100.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(30.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(
                            if (item.status == 0) {
                                Color(0xFFfde3c2)
                            } else if (item.status == 1) {
                                Color(0xFFe4f5e3)
                            } else if (item.status == 2) {
                                Color(0xCCA3C8E5)
                            } else {
                                Color(0xFFfbd0cd)
                            },
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = if (item.status == 0) {
                            "Chờ xác nhận"
                        } else if (item.status == 1) {
                            "Đã xác nhận"
                        } else if (item.status == 2) {
                            "Đã xem"
                        } else {
                            "Đã hủy"
                        },
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (item.status == 0) {
                            Color(0xFFf79009)
                        } else if (item.status == 1) {
                            Color(0xFF6fc876)
                        } else if (item.status == 2) {
                            Color(0xFF3498db)
                        } else {
                            Color(0xFFf04438)
                        },
                        modifier = Modifier.wrapContentWidth()
                    )
                }
            }
        }
    }
}