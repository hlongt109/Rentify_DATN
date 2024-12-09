package com.rentify.user.app.view.staffScreens.PersonalProfileScreen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewBodyPersonalProfile() {
    BodyPersonalProfile(navController = rememberNavController())
    BodyPersonalProfile1(navController = rememberNavController())
}

@Composable
fun BodyPersonalProfile(navController: NavHostController) {
    Column {
        Text(
            text = "Thông tin định danh",
            modifier = Modifier
                .padding(20.dp),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
@Composable
fun BodyPersonalProfile1(navController: NavHostController) {
    Column {
        Text(
            text = "Thông tin tài khoản",
            modifier = Modifier
                .padding(20.dp),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}