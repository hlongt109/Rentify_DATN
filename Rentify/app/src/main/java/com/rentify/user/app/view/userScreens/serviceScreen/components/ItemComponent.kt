package com.rentify.user.app.view.userScreens.serviceScreen.components

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.R

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun ItemComponentPreview(){
    ItemComponent(navController= rememberNavController())
}
@Composable
fun ItemComponent(navController: NavHostController){
    Row {
        Box(
            modifier = Modifier
                .width(200.dp)
                .padding(16.dp)
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(color = Color(0xFFfafafa))
                    .border(
                        width = 1.dp,
                        color = Color(0xFFfafafa),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable { },
                verticalArrangement = Arrangement.Center, // Center items vertically
                horizontalAlignment = Alignment.CenterHorizontally // Center items horizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.tuvan),
                    contentDescription = null,
                    modifier = Modifier
                        .width(110.dp)
                        .height(100.dp)
                        .padding(5.dp)
                )
                Text(
                    text = "Khép kín",
                    fontSize = 20.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(start = 8.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
        Box(
            modifier = Modifier
                .width(200.dp)
                .padding(16.dp)
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
                .clickable {

                }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(color = Color(0xFFfafafa))
                    .border(
                        width = 1.dp,
                        color = Color(0xFFfafafa),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable {
                        navController.navigate("LAUDRY")
                    },
                verticalArrangement = Arrangement.Center, // Center items vertically
                horizontalAlignment = Alignment.CenterHorizontally // Center items horizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.gatla),
                    contentDescription = null,
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp)
                        .padding(5.dp)
                )
                Text(
                    text = "Giặt là ",
                    fontSize = 20.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(start = 8.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
