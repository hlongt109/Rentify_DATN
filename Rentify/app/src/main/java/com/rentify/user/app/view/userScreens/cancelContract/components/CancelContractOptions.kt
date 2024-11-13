package com.rentify.user.app.view.userScreens.cancelContract.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CancelContractOptions() {
    Column(
        modifier = Modifier
            .background(color = Color(0xfff5f5f5))
            .padding(20.dp)
            .fillMaxWidth()
    ) {
        // Hàng đầu tiên: Thông báo yêu cầu hủy hợp đồng
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFFfbd0cd), shape = RoundedCornerShape(9.dp))
                .clickable(
                    onClick = { /** Handle cancellation request action */ },
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                )
                .padding(vertical = 8.dp), // Thêm padding vertical
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.padding(vertical = 10.dp), // Đặt padding ngang cho Text
                text = "Đã yêu cầu hủy hợp đồng",
                color = Color(0xffff303b),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp)) // Khoảng cách giữa các hàng

        // Hàng thứ hai: Nút hủy hợp đồng
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White, shape = RoundedCornerShape(9.dp))
                .clickable(
                    onClick = { /** Handle contract cancellation */ },
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                )
                .border(
                    width = 2.dp,
                    color = Color(0xFF84d8ff),
                    shape = RoundedCornerShape(9.dp)
                )
                .padding(vertical = 8.dp), // Thêm padding vertical
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.padding(vertical = 10.dp), // Đặt padding ngang cho Text
                text = "Hủy hợp đồng",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
    }
}