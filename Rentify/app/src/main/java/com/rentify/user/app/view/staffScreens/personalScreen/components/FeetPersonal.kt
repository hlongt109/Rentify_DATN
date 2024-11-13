package com.rentify.user.app.view.staffScreens.personalScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.R


// _vanphuc : phần chân

@Preview()
@Composable
fun FeetPersonalPreview(){
    FeetPersonal(navController = rememberNavController())
}
@Composable
fun FeetPersonal(navController: NavHostController){
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(9.dp)
        ) {
            Column {
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
                        .clickable {
                            navController.navigate("PersonalProfileScreen")
                        }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.you),
                        contentDescription = null,
                        modifier = Modifier
                            .width(40.dp)
                            .height(40.dp)
                            .padding(5.dp)
                    )
                    Text(
                        text = "Thông tin cá nhân ",
                        fontSize = 15.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp),
                        fontWeight = FontWeight.Bold
                    )
                    Image(painter = painterResource(id = R.drawable.baseline_navigate_next_24),
                        contentDescription = "",
                        modifier = Modifier.size(30.dp)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .padding(start = 10.dp, end = 10.dp)
                        .background(color = Color(0xFFe4e4e4))
                ) {
                }
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
                        .clickable { }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ngonngu),
                        contentDescription = null,
                        modifier = Modifier
                            .width(40.dp)
                            .height(40.dp)
                            .padding(5.dp)
                    )
                    Text(
                        text = "Ngôn ngữ ",
                        fontSize = 15.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp),fontWeight = FontWeight.Bold
                    )
                    Image(painter = painterResource(id = R.drawable.baseline_navigate_next_24),
                        contentDescription = "",
                        modifier = Modifier.size(30.dp)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .padding(start = 10.dp, end = 10.dp)
                        .background(color = Color(0xFFe4e4e4))
                ) {
                }
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
                        .clickable { }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.baomat),
                        contentDescription = null,
                        modifier = Modifier
                            .width(40.dp)
                            .height(40.dp)
                            .padding(5.dp)
                    )
                    Text(
                        text = "Bảo mật",
                        fontSize = 15.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp),
                        fontWeight = FontWeight.Bold
                    )
                    Image(painter = painterResource(id = R.drawable.baseline_navigate_next_24),
                        contentDescription = "",
                        modifier = Modifier.size(30.dp)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .padding(start = 10.dp, end = 10.dp)
                        .background(color = Color(0xFFe4e4e4))
                ) {
                }
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
                        .clickable {
                        }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.gioithieu),
                        contentDescription = null,
                        modifier = Modifier
                            .width(40.dp)
                            .height(40.dp)
                            .padding(5.dp)
                    )
                    Text(
                        text = "Giới thiệu",
                        fontSize = 15.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp),
                        fontWeight = FontWeight.Bold
                    )
                    Image(painter = painterResource(id = R.drawable.baseline_navigate_next_24),
                        contentDescription = "",
                        modifier = Modifier.size(30.dp)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .padding(start = 10.dp, end = 10.dp)
                        .background(color = Color(0xFFe4e4e4))
                ) {
                }
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
                        .clickable {
                            navController.navigate("LOGIN") //ConTract
                        }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.danxuat),
                        contentDescription = null,
                        modifier = Modifier
                            .width(40.dp)
                            .height(40.dp)
                            .padding(5.dp)
                    )
                    Text(
                        text = "Đăng xuất",
                        fontSize = 15.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp),
                        fontWeight = FontWeight.Bold
                    )
                    Image(painter = painterResource(id = R.drawable.baseline_navigate_next_24),
                        contentDescription = "",
                        modifier = Modifier.size(30.dp)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .padding(start = 10.dp, end = 10.dp)
                        .background(color = Color(0xFFe4e4e4))
                ) {
                }


            }

        }
    }
}