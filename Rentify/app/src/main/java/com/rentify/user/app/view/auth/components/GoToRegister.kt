package com.rentify.user.app.view.auth.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rentify.user.app.MainActivity.ROUTER
import com.rentify.user.app.ui.theme.ColorBlack
import com.rentify.user.app.ui.theme.greenInput


@Composable
fun GoToRegister(navController: NavController) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Chưa có tài khoản ?",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = ColorBlack
        )
        Text(
            text = "Đăng ký",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = greenInput,
            modifier = Modifier
                .padding(start = 10.dp)
                .clickable {
                    navController.navigate(ROUTER.RESGITER.name)
                }
        )
    }
}