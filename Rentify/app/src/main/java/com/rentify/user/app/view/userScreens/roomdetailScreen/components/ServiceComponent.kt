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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentify.user.app.R

// dịch vụ
@Composable
@Preview(showBackground = true, showSystemUi = true)
fun ServiceComponent() {
    LayoutService()
}

@Composable
fun LayoutService() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp)
    ) {
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.phidv),
            contentDescription = "",
            modifier = Modifier.size(40.dp)
        )
        Text(
            text = "Phí dịch vụ ",
            modifier = Modifier
                .padding(start = 5.dp)
                .padding(top = 10.dp),
            fontSize = 20.sp
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
            .height(1.dp)
            .background(color = Color(0xffd9d9d9))
            .padding(start = 20.dp, end = 20.dp)
    ) {
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .horizontalScroll(rememberScrollState())
    ) {
        // Thẻ 1
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(color = Color.White)
                    .border(
                        width = 1.dp,
                        color = Color(0xFFfafafa),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable { },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.dien),
                    contentDescription = null,
                    modifier = Modifier
                        .width(40.dp)
                        .height(40.dp)
                )
                Text(
                    text = "Điện",
                    fontSize = 15.sp,
                    color = Color(0xFF909191),
                    modifier = Modifier.padding(start = 8.dp),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "3.800đ/kW",
                    fontSize = 15.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 8.dp),
                    textAlign = TextAlign.Center
                )
            }
        }

        // Thẻ 2
        Box(
            modifier = Modifier
                .weight(1f) // Chia đều không gian
                .padding(8.dp)
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(color = Color.White)
                    .border(
                        width = 1.dp,
                        color = Color(0xFFfafafa),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable { },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.nuoc),
                    contentDescription = null,
                    modifier = Modifier
                        .width(40.dp)
                        .height(40.dp)
                )
                Text(
                    text = "Nước",
                    fontSize = 15.sp,
                    color = Color(0xFF909191),
                    modifier = Modifier.padding(start = 8.dp),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "28.000đ/khối",
                    fontSize = 15.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 8.dp),
                    textAlign = TextAlign.Center
                )
            }
        }

        // Thẻ 3
        Box(
            modifier = Modifier
                .weight(1f) // Chia đều không gian
                .padding(8.dp)
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(color = Color.White)
                    .border(
                        width = 1.dp,
                        color = Color(0xFFfafafa),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable { },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.wifi),
                    contentDescription = null,
                    modifier = Modifier
                        .width(40.dp)
                        .height(40.dp)
                )
                Text(
                    text = "Mạng",
                    fontSize = 15.sp,
                    color = Color(0xFF909191),
                    modifier = Modifier.padding(start = 8.dp),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "100.000đ/phòng",
                    fontSize = 15.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 8.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
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
            .background(color = Color(0xfff7f7f7))
    ) {
    }

}