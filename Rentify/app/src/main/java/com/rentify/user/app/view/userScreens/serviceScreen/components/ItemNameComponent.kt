package com.rentify.user.app.view.userScreens.serviceScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentify.user.app.R

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ItemNameComponent(modifier: Modifier=Modifier) {
    LayoutItemName()
}

@Composable
fun LayoutItemName() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
    ) {
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
        ) {
            Image(
                painter = painterResource(id = R.drawable.user),
                contentDescription = null,
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
                    .padding(5.dp)
                    .clip(CircleShape)
            )
            Column(
                modifier = Modifier
                    .padding(15.dp)
                    .weight(1f) // Cho phép cột chiếm không gian còn lại
            ) {
                Text(
                    text = "Vũ Văn Phúc",
                    fontSize = 18.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(start = 5.dp)
                )
                Text(
                    text = "0981139895",
                    fontSize = 15.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(start = 5.dp)
                )
            }
            // Sử dụng Spacer để đẩy hình ảnh "next" sang bên phải
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = painterResource(id = R.drawable.next),
                contentDescription = null,
                modifier = Modifier
                    .width(30.dp)
                    .height(30.dp)
                    .padding(end = 5.dp)
                    .clip(CircleShape)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}
