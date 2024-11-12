package com.rentify.user.app.view.userScreens.personalScreen.components

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
fun ItemNameComponent() {
    LayoutItemName(navController= rememberNavController())
}

@Composable
fun LayoutItemName(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(9.dp)
            .border(width = 1.dp, color = Color(0xffdddddd), shape = RoundedCornerShape(20.dp))
    ) {
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
                .padding(start = 20.dp)
                .clickable {
                    navController.navigate("PROFILE")
                }
        ) {
            Image(
                painter = painterResource(id = R.drawable.user),
                contentDescription = null,
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
                    .padding(top = 5.dp)
                    .clip(CircleShape)
            )
            Column(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .weight(1f) // Cho phép cột chiếm không gian còn lại
            ) {
                Text(
                    text = "Vũ Văn Phúc",
                    fontSize = 18.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(start = 5.dp),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "0981139895",
                    fontSize = 15.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(start = 5.dp),
                    fontWeight = FontWeight.Bold
                )
            }
            // Sử dụng Spacer để đẩy hình ảnh "next" sang bên phải
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = painterResource(id = R.drawable.baseline_navigate_next_24),
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
