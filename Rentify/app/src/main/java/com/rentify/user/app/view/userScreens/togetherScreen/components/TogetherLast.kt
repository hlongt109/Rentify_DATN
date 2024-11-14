package com.rentify.user.app.view.userScreens.togetherScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.R


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TogetherLastPreview(){
    TogetherLast(navController= rememberNavController())
}
@Composable
fun TogetherLast(navController: NavHostController){
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