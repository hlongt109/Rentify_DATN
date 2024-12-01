package com.rentify.user.app.view.userScreens.messengerScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.rentify.user.app.R
import com.rentify.user.app.viewModel.UserViewmodel.chatUser

@Composable
fun UserItem(
    user: chatUser,
    navController: NavHostController
){
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
                .height(80.dp)
                .background(color = Color.White)
                .border(
                    width = 1.dp,
                    color = Color(0xFFF0F0F0),
                    shape = RoundedCornerShape(12.dp)
                )
                .clickable {
                    navController.navigate("TINNHAN/${user.id}/${user.name}")
                }
        ) {
            Image(
                painter = painterResource(id = R.drawable.ad),
                contentDescription = null,
                modifier = Modifier
                    .size(70.dp) // Kích thước nhỏ hơn so với ảnh ban đầu
                    .clip(CircleShape)
                    .padding(start = 20.dp)
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .weight(1f)
            ) {
                Text(
                    text = user.name,
                    fontSize = 16.sp, // Font size giảm một chút
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
//            Text(
//                text = "Người cho thuê",
//                fontSize = 14.sp,
//                color = Color.White,
//                modifier = Modifier
//                    .padding(end = 10.dp)
//                    .background(
//                        color = Color(0xff44acfe), // Màu nền xanh nhạt
//                        shape = RoundedCornerShape(8.dp)
//                    )
//                    .padding(horizontal = 8.dp, vertical = 4.dp) // Padding cho nhãn "Admin"
//            )
        }
    }
}