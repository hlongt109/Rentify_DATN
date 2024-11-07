package com.rentify.user.app.view.userScreens.roomdetailsScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun NameComponent() {
    LayoutNameComponent(navController= rememberNavController())
}
@Composable
fun LayoutNameComponent (navController: NavHostController){
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ){
        Image(
            painter = painterResource(id = R.drawable.back),
            contentDescription = "back",
            modifier = Modifier
                .width(40.dp)
                .height(40.dp)
                .padding(5.dp)
                .clip(CircleShape)
                .clickable {
                    navController.popBackStack()
                }
        )
        Column(
            modifier = Modifier
                .padding(top = 10.dp)
                .weight(1f) // Cho phép cột chiếm không gian còn lại
        ) {
            Text(
                text = "Chi tiết phòng",
                fontSize = 20.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }
        // Sử dụng Spacer để đẩy hình ảnh "next" sang bên phải
        Spacer(modifier = Modifier.width(8.dp))
        Image(
            painter = painterResource(id = R.drawable.yeuthich),
            contentDescription = null,
            modifier = Modifier
                .width(30.dp)
                .height(30.dp)
                .padding(top = 10.dp, end = 10.dp)
        )
    }
}