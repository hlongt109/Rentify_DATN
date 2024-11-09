package com.rentify.user.app.view.userScreens.roomdetailScreen.components

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentify.user.app.R


@Preview(showSystemUi = true, showBackground =true)
@Composable
fun NoidungComponent (){
    LayoutNoidung()
}
@Composable
fun LayoutNoidung(){
    Column {
        Row (
            modifier = Modifier.fillMaxWidth().padding(start = 15.dp)
        ){
            Text(text = "Phòng Trọ",
                modifier = Modifier.padding(start = 5.dp)
                    .padding(end = 10.dp),
                fontSize = 15.sp,
                color = Color(0xfffeb051)
            )
            Image(painter = painterResource(id = R.drawable.n),
                contentDescription ="" ,
                modifier = Modifier.size(20.dp)
            )
            Text(text = "Nam / Nữ  ",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 10.dp),
                fontSize = 15.sp
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 15.dp, top = 5.dp)
        ){
            Text(text = "250 Kim Giang - Phòng rộng giá rẻ ở 4 người ",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 5.dp),
                fontSize = 15.sp,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 15.dp,5.dp)
        ){
            Text(text = "3.500.000 - 5.100.00đ/tháng",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 5.dp),
                color = Color.Red,
                fontSize = 15.sp,
            )
        }
        Row (
            modifier = Modifier.fillMaxWidth().padding(start = 17.dp,5.dp)
        ){

            Image(painter = painterResource(id = R.drawable.nha),
                contentDescription ="" ,
                modifier = Modifier.size(20.dp)
            )
            Text(text = "Tên toà nhà : GHM26",
                modifier = Modifier.padding(start = 5.dp),
                fontSize = 15.sp,
                color = Color(0xff777777)
            )
        }
        Row (
            modifier = Modifier.fillMaxWidth().padding(start = 15.dp, top = 5.dp)
        ){

            Image(painter = painterResource(id = R.drawable.dc),
                contentDescription ="" ,
                modifier = Modifier.size(20.dp)
            )
            Text(text = "27/143 xuân phương, nam từ liêm, hà nội",
                modifier = Modifier.padding(start = 5.dp),
                fontSize = 15.sp,
                color = Color(0xff777777)
            )
        }

        Row(
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp, top = 10.dp)
                .background(color = Color(0xFF84d8ff))
                .border(width = 1.dp, color =Color(0xFF84d8ff), shape = RoundedCornerShape(20.dp))
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
                text = "Đặt trước phòng",
                modifier = Modifier.padding(start = 5.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold, // Đậm chữ
                color = Color.White, // Màu chữ trắng
                lineHeight = 24.sp // Khoảng cách giữa các dòng
            )
        }

    }
}