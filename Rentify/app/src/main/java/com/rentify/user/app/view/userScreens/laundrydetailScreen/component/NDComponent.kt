package com.rentify.user.app.view.userScreens.laundrydetailScreen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun NDPreview(){
    NDComponent(navController= rememberNavController())
}
@Composable
fun NDComponent(navController: NavHostController){
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(color = Color(0xfff2f2f2))
        ) {
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 15.dp)
        ){
            Text(text = "Chăn ga ",
                modifier = Modifier.padding(5.dp),
                fontSize = 15.sp,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 15.dp)
        ){
            Text(text = "14.000 VNĐ / 1kg",
                modifier = Modifier.padding(5.dp),
                color = Color.Red,
                fontSize = 15.sp,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 15.dp)
        ){
            Text(text = "Người đã sử dụng: 260",
                modifier = Modifier.padding( 5.dp),
                fontSize = 15.sp,
            )
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
                        // Xử lý sự kiện nhấn nút
                    },
                    indication = null, // Tắt hiệu ứng gợn sóng
                    interactionSource = remember { MutableInteractionSource() }
                ),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically // Căn giữa theo chiều dọc
        ) {
            Text(
                text = "Liên Hệ Ngay",
                modifier = Modifier.padding(5.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold, // Đậm chữ
                color = Color.White, // Màu chữ trắng
                lineHeight = 24.sp // Khoảng cách giữa các dòng
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(15.dp)
        ){
            Text(text = "Mô tả sản phẩm",
                modifier = Modifier.padding(start = 5.dp),
                color = Color.Red,
                fontSize = 15.sp,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 15.dp)
        ){
            Text(text = "Vắt  cực khô khô đét",
                modifier = Modifier.padding(5.dp),
                fontSize = 15.sp,
            )
        }
    }
}