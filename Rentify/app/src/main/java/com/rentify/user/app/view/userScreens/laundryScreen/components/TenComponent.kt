package com.rentify.user.app.view.userScreens.laundryScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
fun TenPreview() {
    TenComponent(navController = rememberNavController())
}

@Composable
fun TenComponent(navController: NavHostController) {
    Column (
        modifier = Modifier.fillMaxWidth()
            .height(100.dp)
            .background(color = Color(0xff84d8ff))
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp)
                .background(color = Color(0xff84d8ff))
        ) {
            Image(
                painter = painterResource(id = R.drawable.back),
                contentDescription = "Back",
                modifier = Modifier
                    .padding(start = 12.dp, top = 12.dp)
                    .size(30.dp)
                    .clickable {
                        navController.popBackStack()
                    },
            )
            Text(
                text = "Giặt là ",
                modifier = Modifier.padding(15.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
    }
}
