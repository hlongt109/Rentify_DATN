package com.rentify.user.app.view.userScreens.personalScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import com.rentify.user.app.R

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ItemSComponent() {
    LayoutItems()
}

@Composable
fun LayoutItems() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(9.dp)
//                .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
//                .clip(RoundedCornerShape(12.dp))
                .border(width = 1.dp, color = Color(0xffdddddd), shape = RoundedCornerShape(20.dp))
        ) {
            Column {
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
                        .clickable { }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.qlbaidang),
                        contentDescription = null,
                        modifier = Modifier
                            .width(50.dp)
                            .height(50.dp)
                            .padding(5.dp)
                    )
                    Text(
                        text = "Quản lý bài đăng ",
                        fontSize = 20.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    )
                    Image(painter = painterResource(id = R.drawable.baseline_navigate_next_24),
                        contentDescription = "",
                        modifier = Modifier.size(30.dp)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .padding(start = 10.dp, end = 10.dp)
                        .background(color = Color(0xff8c8c8c))
                ) {
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .background(color = Color(0xFFfafafa))
                        .border(
                            width = 1.dp,
                            color = Color(0xFFfafafa),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable { }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.qldonhang),
                        contentDescription = null,
                        modifier = Modifier
                            .width(50.dp)
                            .height(50.dp)
                            .padding(5.dp)
                    )
                    Text(
                        text = "Quản lý dịch vụ ",
                        fontSize = 20.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    )
                    Image(painter = painterResource(id = R.drawable.baseline_navigate_next_24),
                        contentDescription = "",
                        modifier = Modifier.size(30.dp)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .padding(start = 10.dp, end = 10.dp)
                        .background(color = Color(0xff8c8c8c))
                ) {
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .background(color = Color(0xFFfafafa))
                        .border(
                            width = 1.dp,
                            color = Color(0xFFfafafa),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable { }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.lich),
                        contentDescription = null,
                        modifier = Modifier
                            .width(50.dp)
                            .height(50.dp)
                            .padding(5.dp)
                    )
                    Text(
                        text = "Lịch hẹn xem phòng ",
                        fontSize = 20.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    )
                    Image(painter = painterResource(id = R.drawable.baseline_navigate_next_24),
                        contentDescription = "",
                        modifier = Modifier.size(30.dp)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .padding(start = 10.dp, end = 10.dp)
                        .background(color = Color(0xff8c8c8c))
                ) {
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .background(color = Color(0xFFfafafa))
                        .border(
                            width = 1.dp,
                            color = Color(0xFFfafafa),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable { }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.bill),
                        contentDescription = null,
                        modifier = Modifier
                            .width(50.dp)
                            .height(50.dp)
                            .padding(5.dp)
                    )
                    Text(
                        text = "hóa đơn ",
                        fontSize = 20.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    )
                    Image(painter = painterResource(id = R.drawable.baseline_navigate_next_24),
                        contentDescription = "",
                        modifier = Modifier.size(30.dp)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .padding(start = 10.dp, end = 10.dp)
                        .background(color = Color(0xff8c8c8c))
                ) {
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .background(color = Color(0xFFfafafa))
                        .border(
                            width = 1.dp,
                            color = Color(0xFFfafafa),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable { }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.hdong),
                        contentDescription = null,
                        modifier = Modifier
                            .width(50.dp)
                            .height(50.dp)
                            .padding(5.dp)
                    )
                    Text(
                        text = "Hợp đồng",
                        fontSize = 20.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    )
                    Image(painter = painterResource(id = R.drawable.baseline_navigate_next_24),
                        contentDescription = "",
                        modifier = Modifier.size(30.dp)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .padding(start = 10.dp, end = 10.dp)
                        .background(color = Color(0xff8c8c8c))
                ) {
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .background(color = Color(0xFFfafafa))
                        .border(
                            width = 1.dp,
                            color = Color(0xFFfafafa),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable { }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.postyt),
                        contentDescription = null,
                        modifier = Modifier
                            .width(50.dp)
                            .height(50.dp)
                            .padding(5.dp)
                    )
                    Text(
                        text = "Bài đăng yêu thích ",
                        fontSize = 20.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    )
                    Image(painter = painterResource(id = R.drawable.baseline_navigate_next_24),
                        contentDescription = "",
                        modifier = Modifier.size(30.dp)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .padding(start = 10.dp, end = 10.dp)
                        .background(color = Color(0xff8c8c8c))
                ) {
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .background(color = Color(0xFFfafafa))
                        .border(
                            width = 1.dp,
                            color = Color(0xFFfafafa),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable { }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.dkhoan),
                        contentDescription = null,
                        modifier = Modifier
                            .width(50.dp)
                            .height(50.dp)
                            .padding(5.dp)
                    )
                    Text(
                        text = "Điều khoản và chính sách ",
                        fontSize = 20.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    )
                    Image(painter = painterResource(id = R.drawable.baseline_navigate_next_24),
                        contentDescription = "",
                        modifier = Modifier.size(30.dp)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .padding(start = 10.dp, end = 10.dp)
                        .background(color = Color(0xff8c8c8c))
                ) {
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .background(color = Color(0xFFfafafa))
                        .border(
                            width = 1.dp,
                            color = Color(0xFFfafafa),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable { }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.baocao),
                        contentDescription = null,
                        modifier = Modifier
                            .width(50.dp)
                            .height(50.dp)
                            .padding(5.dp)
                    )
                    Text(
                        text = "báo cáo sự cố ",
                        fontSize = 20.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    )
                    Image(painter = painterResource(id = R.drawable.baseline_navigate_next_24),
                        contentDescription = "",
                        modifier = Modifier.size(30.dp)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .padding(start = 10.dp, end = 10.dp)
                        .background(color = Color(0xff8c8c8c))
                ) {
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .background(color = Color(0xFFfafafa))
                        .border(
                            width = 1.dp,
                            color = Color(0xFFfafafa),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable { }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.out),
                        contentDescription = null,
                        modifier = Modifier
                            .width(50.dp)
                            .height(50.dp)
                            .padding(5.dp)
                    )
                    Text(
                        text = "Đăng xuất ",
                        fontSize = 20.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    )
                    Image(painter = painterResource(id = R.drawable.baseline_navigate_next_24),
                        contentDescription = "",
                        modifier = Modifier.size(30.dp)
                    )

                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .padding(start = 10.dp, end = 10.dp)
                        .background(color = Color(0xff8c8c8c))
                ) {
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center, // Căn giữa theo chiều ngang
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .background(color = Color(0xFFfafafa))
                        .border(
                            width = 1.dp,
                            color = Color(0xFFfafafa),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable { }
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
