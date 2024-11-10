package com.rentify.user.app.view.userScreens.paymentscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.view.userScreens.paymentscreen.components.PaymentContent
import com.rentify.user.app.view.userScreens.paymentscreen.components.PaymentHeading

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PaymentScreenPreview(){
    PaymentScreen(navController= rememberNavController())
}
@Composable
fun PaymentScreen(navController: NavHostController){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
        }
        PaymentHeading(navController)
        PaymentContent(navController)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
        }
    }
}