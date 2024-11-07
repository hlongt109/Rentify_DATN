package com.rentify.user.app.view.userScreens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Preview(){
    Screen(navController= rememberNavController())
}
@Composable
fun Screen(navController: NavHostController){

}