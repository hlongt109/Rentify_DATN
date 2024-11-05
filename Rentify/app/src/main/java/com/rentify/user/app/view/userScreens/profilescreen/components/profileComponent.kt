package com.rentify.user.app.view.userScreens.profilescreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
fun ProfileComponentPreview() {
    ProfileComponent(navController = rememberNavController())
}

@Composable
fun ProfileComponent(navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.back),
            contentDescription = "back",
            modifier = Modifier
                .width(50.dp)
                .height(50.dp)
                .padding(5.dp)
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
                text = "Hồ sơ cá nhân",
                fontSize = 25.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}