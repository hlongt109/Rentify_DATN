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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import com.rentify.user.app.R

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ItemComponent() {
    LayoutItem()
}

@Composable
fun LayoutItem() {
    Spacer(modifier = Modifier.height(20.dp))
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
                .clickable { }
        ) {
            Image(
                painter = painterResource(id = R.drawable.map),
                contentDescription = null,
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
                    .padding(5.dp)
                    .clip(CircleShape)
            )
            Text(
                text = "Cơ bản về ảnh / video",
                fontSize = 20.sp,
                color = Color(0xFF909191),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}
