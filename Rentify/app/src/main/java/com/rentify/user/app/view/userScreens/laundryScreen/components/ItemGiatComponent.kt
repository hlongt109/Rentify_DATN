package com.rentify.user.app.view.userScreens.laundryScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.R

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun ItemgiatComponentPreview(){
    ItemgiatComponent(navController = rememberNavController())
}
@Composable
fun ItemgiatComponent(navController: NavHostController){
    Row {
        Box(
            modifier = Modifier
                .width(200.dp)
                .padding(16.dp, top = 20.dp)
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
                .clickable {
                    navController.navigate("LAUDRYDETAIL")
                }

        ) {
            Column(
                modifier = Modifier
                    .width(200.dp)
                    .height(250.dp)
                    .background(color = Color(0xFFfafafa))
                    .border(
                        width = 1.dp,
                        color = Color(0xFFfafafa),
                        shape = RoundedCornerShape(12.dp)
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.giattay),
                    contentDescription = null,
                    modifier = Modifier
                        .width(130.dp)
                        .height(130.dp)
                        .padding(top = 6.dp),
                )
                Column (
                    modifier = Modifier.padding(top = 12.dp)
                ){
                    Text(
                        text = "Chăn ga ",
                        fontSize = 12.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp),
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "14.000 VNĐ / 1kg",
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    )
                    Text(
                        text = "Người đã sử dụng: 260",
                        fontSize = 12.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp),
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Mô tả sản phẩm",
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    )
                    Text(
                        text = "Vắt  cực khô khô đét",
                        fontSize = 12.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp),
                        fontWeight = FontWeight.Bold
                    )
                }

            }
            Spacer(modifier = Modifier.height(20.dp))
        }
        Box(
            modifier = Modifier
                .width(200.dp)
                .padding(16.dp, top = 20.dp)
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
                .clickable {
                    navController.navigate("LAUDRYDETAIL")
                }

        ) {
            Column(
                modifier = Modifier
                    .width(200.dp)
                    .height(250.dp)
                    .background(color = Color(0xFFfafafa))
                    .border(
                        width = 1.dp,
                        color = Color(0xFFfafafa),
                        shape = RoundedCornerShape(12.dp)
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.giattay),
                    contentDescription = null,
                    modifier = Modifier
                        .width(130.dp)
                        .height(130.dp)
                        .padding(top = 6.dp),
                )
                Column (
                    modifier = Modifier.padding(top = 12.dp)
                ){
                    Text(
                        text = "Chăn ga ",
                        fontSize = 12.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp),
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "14.000 VNĐ / 1kg",
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    )
                    Text(
                        text = "Người đã sử dụng: 260",
                        fontSize = 12.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp),
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Mô tả sản phẩm",
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    )
                    Text(
                        text = "Vắt  cực khô khô đét",
                        fontSize = 12.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp),
                        fontWeight = FontWeight.Bold
                    )
                }

            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
