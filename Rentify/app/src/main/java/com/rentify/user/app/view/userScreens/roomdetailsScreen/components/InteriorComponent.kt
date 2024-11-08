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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentify.user.app.R

// nội thất
@Composable
@Preview(showBackground = true, showSystemUi = true)
fun InteriorComponent(){
    LayoutInterior()
}
@Composable
fun LayoutInterior(){
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
            painter = painterResource(id = R.drawable.phint),
            contentDescription = "",
            modifier = Modifier.size(40.dp)
        )
        Text(
            text = "Dịch Vụ",
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
//

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .horizontalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .width(160.dp)
                .padding(16.dp)
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
                verticalArrangement = Arrangement.Center, // Center items vertically
                horizontalAlignment = Alignment.CenterHorizontally // Center items horizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.dieuhoa),
                    contentDescription = null,
                    modifier = Modifier
                        .width(40.dp)
                        .height(40.dp)

                )
                Text(
                    text = "Điều hòa ",
                    fontSize = 15.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(start = 8.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
//
        Box(
            modifier = Modifier
                .width(160.dp)
                .padding(16.dp)
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
                verticalArrangement = Arrangement.Center, // Center items vertically
                horizontalAlignment = Alignment.CenterHorizontally // Center items horizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.nonglanh),
                    contentDescription = null,
                    modifier = Modifier
                        .width(40.dp)
                        .height(40.dp)

                )

                Text(
                    text = "Nóng lạnh",
                    fontSize = 15.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(start = 8.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
        Box(
            modifier = Modifier
                .width(160.dp)
                .padding(16.dp)
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
                verticalArrangement = Arrangement.Center, // Center items vertically
                horizontalAlignment = Alignment.CenterHorizontally // Center items horizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.giuong),
                    contentDescription = null,
                    modifier = Modifier
                        .width(40.dp)
                        .height(40.dp)

                )
                Text(
                    text = "Giường ngủ",
                    fontSize = 15.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(start = 8.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .horizontalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .width(160.dp)
                .padding(16.dp)
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
                verticalArrangement = Arrangement.Center, // Center items vertically
                horizontalAlignment = Alignment.CenterHorizontally // Center items horizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.maygiat),
                    contentDescription = null,
                    modifier = Modifier
                        .width(40.dp)
                        .height(40.dp)

                )
                Text(
                    text = "máy giặt",
                    fontSize = 15.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(start = 8.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
//
        Box(
            modifier = Modifier
                .width(160.dp)
                .padding(16.dp)
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
                verticalArrangement = Arrangement.Center, // Center items vertically
                horizontalAlignment = Alignment.CenterHorizontally // Center items horizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.maygiat),
                    contentDescription = null,
                    modifier = Modifier
                        .width(40.dp)
                        .height(40.dp)

                )

                Text(
                    text = "Máy Giặt",
                    fontSize = 15.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(start = 8.dp),
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