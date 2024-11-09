package com.rentify.user.app.view.userScreens.roomdetailScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentify.user.app.R

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun baidangPreview() {
    Layoutbaidang()
}

@Composable
fun Layoutbaidang() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
    ) {
    }
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Bài Đăng liên quan ",
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 5.dp)
                .padding(top = 10.dp),
            fontSize = 20.sp
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(vertical = 16.dp, horizontal = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .width(150.dp)
                .padding(16.dp, top = 20.dp)
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
        ) {
            Column(
                modifier = Modifier
                    .width(150.dp)
                    .height(200.dp)
                    .background(color = Color(0xFFfafafa))
                    .border(
                        width = 1.dp,
                        color = Color(0xFFfafafa),
                        shape = RoundedCornerShape(12.dp)
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.chitietp),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth() // This ensures the image takes the full width of the box
                        .height(100.dp)
                )
                Column(
                    modifier = Modifier.padding(top = 5.dp)
                ) {
                    Text(
                        text = "3 giờ trước",
                        color = Color(0XFF0d99ff),
                        fontSize = 12.sp,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    )
                    Text(
                        text = "Phòng Đẹp-Rẻ-Hiện...",
                        fontSize = 12.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    )
                    Text(
                        text = "Từ 5.000.000đ/tháng",
                        color = Color(0XFFb95533),
                        fontSize = 12.sp,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    )
                    Row(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .fillMaxWidth(), // Ensure the Row takes the full width available
                        verticalAlignment = Alignment.CenterVertically // Align items vertically centered
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.vg),
                            contentDescription = "",
                            modifier = Modifier.size(15.dp)
                        )
                        Text(
                            text = "18 - 25m2",
                            fontSize = 12.sp,
                            color = Color(0xFF909191),
                            modifier = Modifier
                                .padding(start = 4.dp) // Add some padding for spacing
                        )
                        Image(
                            painter = painterResource(id = R.drawable.person),
                            contentDescription = "",
                            modifier = Modifier.size(15.dp)
                        )
                        Text(
                            text = "2 ng",
                            fontSize = 12.sp,
                            color = Color(0xFF909191),
                            modifier = Modifier
                                .padding(start = 4.dp) // Add some padding for spacing
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(start = 8.dp, top = 5.dp, bottom = 5.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.mapp),
                            contentDescription = "",
                            modifier = Modifier.size(15.dp)
                        )
                        Text(
                            text = "27/143 Xuân ",
                            fontSize = 12.sp,
                            color = Color(0xFF909191),
                            modifier = Modifier
                                .padding(start = 4.dp) // Add some padding for spacing
                        )

                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }

        Box(
            modifier = Modifier
                .width(150.dp)
                .padding(16.dp, top = 20.dp)
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
        ) {
            Column(
                modifier = Modifier
                    .width(150.dp)
                    .height(200.dp)
                    .background(color = Color(0xFFfafafa))
                    .border(
                        width = 1.dp,
                        color = Color(0xFFfafafa),
                        shape = RoundedCornerShape(12.dp)
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.chitietp),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth() // This ensures the image takes the full width of the box
                        .height(100.dp)
                )
                Column(
                    modifier = Modifier.padding(top = 5.dp)
                ) {
                    Text(
                        text = "3 giờ trước",
                        color = Color(0XFF0d99ff),
                        fontSize = 12.sp,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    )
                    Text(
                        text = "Phòng Đẹp-Rẻ-Hiện...",
                        fontSize = 12.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    )
                    Text(
                        text = "Từ 5.000.000đ/tháng",
                        color = Color(0XFFb95533),
                        fontSize = 12.sp,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    )
                    Row(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .fillMaxWidth(), // Ensure the Row takes the full width available
                        verticalAlignment = Alignment.CenterVertically // Align items vertically centered
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.vg),
                            contentDescription = "",
                            modifier = Modifier.size(15.dp)
                        )
                        Text(
                            text = "18 - 25m2",
                            fontSize = 12.sp,
                            color = Color(0xFF909191),
                            modifier = Modifier
                                .padding(start = 4.dp) // Add some padding for spacing
                        )
                        Image(
                            painter = painterResource(id = R.drawable.person),
                            contentDescription = "",
                            modifier = Modifier.size(15.dp)
                        )
                        Text(
                            text = "2 ng",
                            fontSize = 12.sp,
                            color = Color(0xFF909191),
                            modifier = Modifier
                                .padding(start = 4.dp) // Add some padding for spacing
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(start = 8.dp, top = 5.dp, bottom = 5.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.mapp),
                            contentDescription = "",
                            modifier = Modifier.size(15.dp)
                        )
                        Text(
                            text = "27/143 Xuân ",
                            fontSize = 12.sp,
                            color = Color(0xFF909191),
                            modifier = Modifier
                                .padding(start = 4.dp) // Add some padding for spacing
                        )

                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }

        Box(
            modifier = Modifier
                .width(150.dp)
                .padding(16.dp, top = 20.dp)
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
        ) {
            Column(
                modifier = Modifier
                    .width(150.dp)
                    .height(200.dp)
                    .background(color = Color(0xFFfafafa))
                    .border(
                        width = 1.dp,
                        color = Color(0xFFfafafa),
                        shape = RoundedCornerShape(12.dp)
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.chitietp),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth() // This ensures the image takes the full width of the box
                        .height(100.dp)
                )
                Column(
                    modifier = Modifier.padding(top = 5.dp)
                ) {
                    Text(
                        text = "3 giờ trước",
                        color = Color(0XFF0d99ff),
                        fontSize = 12.sp,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    )
                    Text(
                        text = "Phòng Đẹp-Rẻ-Hiện...",
                        fontSize = 12.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    )
                    Text(
                        text = "Từ 5.000.000đ/tháng",
                        color = Color(0XFFb95533),
                        fontSize = 12.sp,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    )
                    Row(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .fillMaxWidth(), // Ensure the Row takes the full width available
                        verticalAlignment = Alignment.CenterVertically // Align items vertically centered
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.vg),
                            contentDescription = "",
                            modifier = Modifier.size(15.dp)
                        )
                        Text(
                            text = "18 - 25m2",
                            fontSize = 12.sp,
                            color = Color(0xFF909191),
                            modifier = Modifier
                                .padding(start = 4.dp) // Add some padding for spacing
                        )
                        Image(
                            painter = painterResource(id = R.drawable.person),
                            contentDescription = "",
                            modifier = Modifier.size(15.dp)
                        )
                        Text(
                            text = "2 ng",
                            fontSize = 12.sp,
                            color = Color(0xFF909191),
                            modifier = Modifier
                                .padding(start = 4.dp) // Add some padding for spacing
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(start = 8.dp, top = 5.dp, bottom = 5.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.mapp),
                            contentDescription = "",
                            modifier = Modifier.size(15.dp)
                        )
                        Text(
                            text = "27/143 Xuân ",
                            fontSize = 12.sp,
                            color = Color(0xFF909191),
                            modifier = Modifier
                                .padding(start = 4.dp) // Add some padding for spacing
                        )

                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        // Báo cáo
        Box(
            modifier = Modifier
                .width(130.dp)
                .height(80.dp)
                .padding(16.dp)
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(10.dp))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(color = Color(0xFFd9d9d9))
                    .border(
                        width = 1.dp,
                        color = Color(0xFFd9d9d9),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .clickable { },
                verticalAlignment = Alignment.CenterVertically // Căn giữa theo chiều dọc
            ) {
                Image(
                    painter = painterResource(id = R.drawable.error),
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                        .padding(5.dp)
                        .clip(CircleShape),
                )
                Text(
                    text = "Báo cáo",
                )
            }
        }
        // Tin nhắn
        Box(
            modifier = Modifier
                .width(130.dp)
                .height(80.dp)
                .padding(16.dp)
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(color = Color(0xFFfafafa))
                    .border(
                        width = 1.dp,
                        color = Color(0xFF93dcff),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable { },
                verticalAlignment = Alignment.CenterVertically // Căn giữa theo chiều dọc
            ) {
                Image(
                    painter = painterResource(id = R.drawable.tinnhan),
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                        .padding(5.dp)
                        .clip(CircleShape),
                )
                Text(
                    text = "Tin nhắn",
                )
            }
        }

        // Gọi điện
        Box(
            modifier = Modifier
                .width(130.dp)
                .height(80.dp)
                .padding(16.dp)
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)

                    .background(color = Color(0xFFfafafa))
                    .border(
                        width = 1.dp,
                        color = Color.Red,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable { },
                verticalAlignment = Alignment.CenterVertically // Căn giữa theo chiều dọc
            ) {
                Image(
                    painter = painterResource(id = R.drawable.phone),
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                        .padding(5.dp)
                        .clip(CircleShape),
                )
                Text(
                    text = "Gọi điện",
                )
            }
        }
    }


}