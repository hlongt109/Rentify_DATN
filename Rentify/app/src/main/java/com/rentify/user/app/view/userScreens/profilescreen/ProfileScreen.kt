package com.rentify.user.app.view.userScreens.profilescreen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun ProfilePreview(){
    ProfileScreen(navController=rememberNavController())
}
@Composable
fun ProfileScreen(navController: NavHostController){
    Column {
        Text(text = "Profile")
    }
}