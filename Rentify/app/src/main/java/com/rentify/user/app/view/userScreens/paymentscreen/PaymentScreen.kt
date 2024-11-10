package com.rentify.user.app.view.userScreens.paymentscreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PaymentScreenPreview(){
    PaymentScreen(navController= rememberNavController())
}
@Composable
fun PaymentScreen(navController: NavHostController){

}