package com.rentify.user.app.view.userScreens.QuanLiDichVu.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.rentify.user.app.R

@Composable
fun PhanDauQuanLiDichVu(navController: NavHostController){
    Column (
        modifier = Modifier.fillMaxWidth()
            .height(100.dp)
            .background(color = Color(0xFF84d8ff))
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.back),
                contentDescription = "back",
                modifier = Modifier
                    .width(40.dp)
                    .height(40.dp)
                    .padding(top = 10.dp)
                    .clip(CircleShape)
                    .clickable {
                        navController.popBackStack()
                    }
            )
            Column(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally, // Center horizontally
                verticalArrangement = Arrangement.Center // Center vertically
            ) {
                Text(
                    text = "Quản Lí dịch vụ ",
                    modifier = Modifier.padding(end = 20.dp),
                    fontSize = 25.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily.Serif
                )
            }
        }
    }
}