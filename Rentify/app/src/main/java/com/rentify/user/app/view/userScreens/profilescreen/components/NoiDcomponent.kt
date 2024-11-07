package com.rentify.user.app.view.userScreens.profilescreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.R

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NoiDcomponentPreview() {
    NoiDcomponent(navController = rememberNavController())
}

@Composable
fun NoiDcomponent(navController: NavHostController) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp)
        ) {
            Text(
                text = "Thông tin cá nhân ",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 5.dp),
                fontSize = 20.sp,
                color = Color.Black
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp)
        ) {

            Image(
                painter = painterResource(id = R.drawable.lich),
                contentDescription = "",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "Ngày sinh: 14/02/2004",
                modifier = Modifier.padding(start = 5.dp),
                fontSize = 20.sp,
                color = Color(0xff777777)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp)
        ) {

            Image(
                painter = painterResource(id = R.drawable.n),
                contentDescription = "",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "Giới tính: Nam",
                modifier = Modifier.padding(start = 5.dp),
                fontSize = 20.sp,
                color = Color(0xff777777)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp)
        ) {

            Image(
                painter = painterResource(id = R.drawable.gmail),
                contentDescription = "",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "Email: hoanhlongtran109@gmail.com",
                modifier = Modifier.padding(start = 5.dp),
                fontSize = 20.sp,
                color = Color(0xff777777)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp)
        ) {

            Image(
                painter = painterResource(id = R.drawable.phone),
                contentDescription = "",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "Số điện thoại: 0339160077",
                modifier = Modifier.padding(start = 5.dp),
                fontSize = 20.sp,
                color = Color(0xff777777)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp)
        ) {

            Image(
                painter = painterResource(id = R.drawable.map),
                contentDescription = "",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "Địa chỉ thường trú: Khu 2 Hoàng Cương, Thanh Ba, Phú Thọ",
                modifier = Modifier.padding(start = 5.dp),
                fontSize = 20.sp,
                color = Color(0xff777777)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp)
        ) {

            Image(
                painter = painterResource(id = R.drawable.map),
                contentDescription = "",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "Địa chỉ hiện tại:  Khu 2 Hoàng Cương, Thanh Ba, Phú Thọ",
                modifier = Modifier.padding(start = 5.dp),
                fontSize = 20.sp,
                color = Color(0xff777777)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
        ) {
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .background(color = Color(0xffeeeeee))
        ) {
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
        ) {
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp)
        ) {
            Text(
                text = "Thông tin thuê trọ",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 5.dp),
                fontSize = 20.sp,
                color = Color.Black
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp)
        ) {

            Image(
                painter = painterResource(id = R.drawable.home),
                contentDescription = "",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "Phòng thuê hiện tại: P202 , nhà số 9, ngõ 103 đường Phương Canh",
                modifier = Modifier.padding(start = 5.dp),
                fontSize = 20.sp,
                color = Color(0xff777777)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp)
        ) {

            Image(
                painter = painterResource(id = R.drawable.oclock),
                contentDescription = "",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "Thời hạn hợp đồng: 10/10/2023 - 10/10/2024",
                modifier = Modifier.padding(start = 5.dp),
                fontSize = 20.sp,
                color = Color(0xff777777)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp)
        ) {

            Image(
                painter = painterResource(id = R.drawable.hdong),
                contentDescription = "",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "Trạng thái hợp đồng: Sắp hết hạn",
                modifier = Modifier.padding(start = 5.dp),
                fontSize = 20.sp,
                color = Color(0xff777777)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
        ) {
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .background(color = Color(0xffeeeeee))
        ) {
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
        ) {
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp)
        ) {
            Text(
                text = "Lịch sử thuê trọ",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 5.dp),
                fontSize = 20.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.width(150.dp))
            Row(
                modifier = Modifier.padding(10.dp)
            ) {
                Text(
                    text = "Xem thêm",
                    modifier = Modifier.padding(start = 5.dp),
                    fontSize = 10.sp,
                    color = Color(0xff777777)
                )
                Image(
                    painter = painterResource(id = R.drawable.next),
                    contentDescription = "",
                    modifier = Modifier.size(10.dp)
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp)
        ) {

            Image(
                painter = painterResource(id = R.drawable.iconproduct),
                contentDescription = "",
                modifier = Modifier.size(100.dp)
            )
            Column {
                Text(
                    text = "Thời gian: 10/10/2022 - 10/10/2023",
                    modifier = Modifier.padding(start = 5.dp),
                    fontSize = 15.sp,
                    color = Color(0xff777777)
                )
                Text(
                    text = "Phòng S1.12345, Tòa 3B, chung ...",
                    modifier = Modifier.padding(start = 5.dp),
                    fontSize = 15.sp,
                    color = Color(0xff777777)
                )
                Text(
                    text = "Căn hộ 70m2 với 2 phòng ngủ, 2 phòng...",
                    modifier = Modifier.padding(start = 5.dp),
                    fontSize = 15.sp,
                    color = Color(0xff777777)
                )
                Row(
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.dola),
                        contentDescription = "",
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "12tr/tháng",
                        modifier = Modifier.padding(start = 5.dp),
                        fontSize = 15.sp,
                        color = Color(0xffd69352)
                    )
                }
            }

        }
    }
}