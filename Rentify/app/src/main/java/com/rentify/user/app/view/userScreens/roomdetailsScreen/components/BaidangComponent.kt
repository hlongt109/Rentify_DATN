package com.rentify.user.app.view.userScreens.roomdetailsScreen.components

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
fun baidangPreview(){
    Layoutbaidang()
}
@Composable
fun Layoutbaidang(){
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
                .width(200.dp)
                .padding(16.dp, top = 20.dp)
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
        ) {
            Column(
                modifier = Modifier
                    .width(200.dp)
                    .height(350.dp)
                    .background(color = Color(0xFFfafafa))
                    .border(
                        width = 1.dp,
                        color = Color(0xFFfafafa),
                        shape = RoundedCornerShape(12.dp)
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.iconproduct),
                    contentDescription = null,
                    modifier = Modifier
                        .width(130.dp)
                        .height(130.dp)
                        .padding(top = 6.dp),
                )
                Column (
                    modifier = Modifier.padding(top = 12.dp)
                ){
                    Text(
                        text = "PHONG TRỌ RẺ ĐẸP HÀ NỘI ",
                        fontSize = 12.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    )
                    Text(
                        text = "Từ 3.000.000đ/tháng",
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    )
                    Text(
                        text = "27/143 xuân phương",
                        fontSize = 12.sp,
                        color = Color(0xFF909191),
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    )
                    Text(
                        text = "Quận Nam Từ Liêm, Hà Nội",
                        fontSize = 12.sp,
                        color = Color(0xFF909191),
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    )
                    Text(
                        text = "Diện tích : 25 - 25m2",
                        fontSize = 12.sp,
                        color = Color(0xFF909191),
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    )
                    Text(
                        text = "Người:2",
                        fontSize = 12.sp,
                        color = Color(0xFF909191),
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    )
                }

            }
            Spacer(modifier = Modifier.height(20.dp))
        }
        Box(
            modifier = Modifier
                .width(200.dp)
                .padding(16.dp, top = 20.dp)
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
        ) {
            Column(
                modifier = Modifier
                    .width(200.dp)
                    .height(350.dp)
                    .background(color = Color(0xFFfafafa))
                    .border(
                        width = 1.dp,
                        color = Color(0xFFfafafa),
                        shape = RoundedCornerShape(12.dp)
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.iconproduct),
                    contentDescription = null,
                    modifier = Modifier
                        .width(130.dp)
                        .height(130.dp)
                        .padding(top = 6.dp),
                )
                Column (
                    modifier = Modifier.padding(top = 12.dp)
                ){
                    Text(
                        text = "PHONG TRỌ RẺ ĐẸP HÀ NỘI ",
                        fontSize = 12.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    )
                    Text(
                        text = "Từ 3.000.000đ/tháng",
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    )
                    Text(
                        text = "27/143 xuân phương",
                        fontSize = 12.sp,
                        color = Color(0xFF909191),
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    )
                    Text(
                        text = "Quận Nam Từ Liêm, Hà Nội",
                        fontSize = 12.sp,
                        color = Color(0xFF909191),
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    )
                    Text(
                        text = "Diện tích : 25 - 25m2",
                        fontSize = 12.sp,
                        color = Color(0xFF909191),
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    )
                    Text(
                        text = "Người:2",
                        fontSize = 12.sp,
                        color = Color(0xFF909191),
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    )
                }

            }
            Spacer(modifier = Modifier.height(20.dp))
        }
        Box(
            modifier = Modifier
                .width(200.dp)
                .padding(16.dp, top = 20.dp)
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
        ) {
            Column(
                modifier = Modifier
                    .width(200.dp)
                    .height(350.dp)
                    .background(color = Color(0xFFfafafa))
                    .border(
                        width = 1.dp,
                        color = Color(0xFFfafafa),
                        shape = RoundedCornerShape(12.dp)
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.iconproduct),
                    contentDescription = null,
                    modifier = Modifier
                        .width(130.dp)
                        .height(130.dp)
                        .padding(top = 6.dp),
                )
                Column (
                    modifier = Modifier.padding(top = 12.dp)
                ){
                    Text(
                        text = "PHONG TRỌ RẺ ĐẸP HÀ NỘI ",
                        fontSize = 12.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    )
                    Text(
                        text = "Từ 3.000.000đ/tháng",
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    )
                    Text(
                        text = "27/143 xuân phương",
                        fontSize = 12.sp,
                        color = Color(0xFF909191),
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    )
                    Text(
                        text = "Quận Nam Từ Liêm, Hà Nội",
                        fontSize = 12.sp,
                        color = Color(0xFF909191),
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    )
                    Text(
                        text = "Diện tích : 25 - 25m2",
                        fontSize = 12.sp,
                        color = Color(0xFF909191),
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    )
                    Text(
                        text = "Người:2",
                        fontSize = 12.sp,
                        color = Color(0xFF909191),
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    )
                }

            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        // First Row Item
        Row(
            modifier = Modifier
                .height(50.dp)
                .width(100.dp)
                .background(color = Color(0xffd9d9d9))
                .padding(horizontal = 8.dp), // Add horizontal padding
            verticalAlignment = Alignment.CenterVertically, // Center content vertically
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.error),
                contentDescription = "",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(4.dp)) // Add space between image and text
            Text(text = "Báo cáo")
        }

        Spacer(modifier = Modifier.width(16.dp)) // Space between items

        // Second Row Item
        Row(
            modifier = Modifier
                .height(50.dp)
                .width(100.dp)
                .background(color = Color(0xffd9d9d9))
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.tinnhan),
                contentDescription = "",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "Tin nhắn")
        }

        Spacer(modifier = Modifier.width(16.dp)) // Space between items

        // Third Row Item
        Row(
            modifier = Modifier
                .height(50.dp)
                .width(100.dp)
                .background(color = Color(0xffd9d9d9))
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.phone),
                contentDescription = "",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "Gọi điện")
        }
    }


}