package com.rentify.user.app.view.userScreens.homeScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.R

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ItemHomeComponent() {
    LayoutItemHome(navController= rememberNavController())
}

@Composable
fun LayoutItemHome(navController: NavHostController) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(9.dp)
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(color = Color(0xFFfafafa))
                .border(
                    width = 1.dp,
                    color = Color(0xFFfafafa),
                    shape = RoundedCornerShape(12.dp)
                )
                .clickable {
                    navController.navigate("ROOMDETAILS")
                }
        ) {
            Image(
                painter = painterResource(id = R.drawable.iconproduct),
                contentDescription = null,
                modifier = Modifier
                    .width(130.dp)
                    .height(130.dp)
            )
            Column (
                modifier = Modifier.padding(top = 12.dp)
            ){
                Text(
                    text = "PHONG TRỌ RẺ ĐẸP HÀ NỘI ",
                    fontSize = 12.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                )
                Text(
                    text = "Từ 3.000.000đ/tháng",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                )
                Text(
                    text = "27/143 xuân phương",
                    fontSize = 12.sp,
                    color = Color(0xFF909191),
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                )
                Text(
                    text = "Quận Nam Từ Liêm, Hà Nội",
                    fontSize = 12.sp,
                    color = Color(0xFF909191),
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                )
                Text(
                    text = "Diện tích : 25 - 25m2",
                    fontSize = 12.sp,
                    color = Color(0xFF909191),
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                )
                Text(
                    text = "Người:2",
                    fontSize = 12.sp,
                    color = Color(0xFF909191),
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                )
            }

        }
    }
}