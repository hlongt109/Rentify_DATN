package com.rentify.user.app.view.userScreens.personalScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.R

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ItemSComponent() {
    LayoutItems(navController = rememberNavController())
}

@Composable
fun LayoutItems(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xfff7f7f7))
            .padding(10.dp)
            .clip(RoundedCornerShape(10.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White)
        ) {
            Column(

            ) {
                CustomRow(
                    imageId = R.drawable.qlbaidang,
                    text = "Quản lý bài đăng",
                    onClick = { navController.navigate("CATEGORYPOST") }
                )
                CustomRow(
                    imageId = R.drawable.qldonhang,
                    text = "Quản lý dịch vụ",
                    onClick = { navController.navigate("CATEGORYPOST") }
                )
                CustomRow(
                    imageId = R.drawable.lich,
                    text = "Lịch hẹn xem phòng",
                    onClick = { navController.navigate("AppointmentScreen") }
                )
                CustomRow(
                    imageId = R.drawable.bill,
                    text = "Hóa đơn",
                    onClick = { navController.navigate("Invoice_screen") }
                )
                CustomRow(
                    imageId = R.drawable.hdong,
                    text = "Hợp đồng",
                    onClick = { navController.navigate("ConTract") }
                )
                CustomRow(
                    imageId = R.drawable.postyt,
                    text = "Bài đăng yêu thích",
                    onClick = { /* Thực hiện hành động khi click */ }
                )
                CustomRow(
                    imageId = R.drawable.dkhoan,
                    text = "Điều khoản và chính sách",
                    onClick = { /* Thực hiện hành động khi click */ }
                )
                CustomRow(
                    imageId = R.drawable.baocao,
                    text = "Báo cáo sự cố",
                    onClick = { navController.navigate("INCIDENTREPORT") }
                )
                CustomRow(
                    imageId = R.drawable.out,
                    text = "Đăng xuất",
                    onClick = { /* Thực hiện hành động khi click */ }
                )

                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    shape = RectangleShape

                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center, // Căn giữa theo chiều ngang
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .background(color = Color(0xFFffffff))
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.delete),
                            contentDescription = null,
                            modifier = Modifier
                                .width(50.dp)
                                .height(50.dp)
                                .padding(10.dp)
                        )
                        Text(
                            text = "Yêu cầu xóa tài khoản",
                            fontSize = 20.sp,
                            color = Color(0xFF84d8ff),
                            textAlign = TextAlign.Center
                        )
                    }
                }


            }

        }
    }
}

@Composable
fun CustomRow(
    modifier: Modifier = Modifier,
    imageId: Int,
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color.Transparent),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        shape = RectangleShape

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Image(
                painter = painterResource(id = imageId),
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
            )
            Text(
                text = text,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.baseline_navigate_next_24),
                contentDescription = "",
                modifier = Modifier.size(25.dp)
            )
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .padding(horizontal = 15.dp)
            .background(color = Color(0xffcdccd1))
    ) {}
}

