package com.rentify.user.app.view.userScreens.paymentconfirmationScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.view.userScreens.paymentconfirmationScreen.components.PaymentConfirmationBody
import com.rentify.user.app.view.userScreens.paymentconfirmationScreen.components.PaymentConfirmationHeading
import com.rentify.user.app.view.userScreens.roomdetailScreen.components.LayoutComfort
import com.rentify.user.app.view.userScreens.roomdetailScreen.components.LayoutInterior

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PaymentConfirmationScreenPreview() {
    PaymentConfirmationScreen(navController = rememberNavController())
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PaymentConfirmationScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight() // Ensure the Column takes up the full height of the parent
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            // Header content can go here
        }

        PaymentConfirmationHeading(navController)
        // Content Column
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // This allows the Column to take up the remaining space
                .background(color = Color(0xFFf5f5f5))
                .verticalScroll(rememberScrollState())
        ) {
            PaymentConfirmationBody(navController)
        }

        // Footer Row for total amount
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
            ) {
                // Header content can go here
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Tổng tiền: ",
                    modifier = Modifier.padding(15.dp)
                )
                Text(text = "3.500.000 VND",
                    modifier = Modifier.padding(15.dp),
                    fontWeight = FontWeight.Bold
                   )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
            ) {
                // Header content can go here
            }
            Row(
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp)
                    .background(color = Color(0xFF84d8ff), shape = RoundedCornerShape(20.dp))
                    .clickable(
                        onClick = {
                            navController.navigate("Payments")
                        },
                        indication = null, // Disable ripple effect
                        interactionSource = remember { MutableInteractionSource() }
                    ),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically // Center vertically
            ) {
                androidx.compose.material3.Text(
                    text = "Tiến hành thanh toán ",
                    modifier = Modifier.padding(5.dp), // Adjust padding for better appearance
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold, // Bold text
                    color = Color.White, // White text color
                    lineHeight = 24.sp // Line height
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            // Header content can go here
        }
    }
}

