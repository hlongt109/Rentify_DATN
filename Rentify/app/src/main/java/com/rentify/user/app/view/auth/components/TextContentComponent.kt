package com.rentify.user.app.view.auth.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextLoginContent(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding()
    ) {
        Text(
            text = "Chào mừng đến với Rentify !",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.padding(top = 15.dp))
        Text(
            text = "Hãy đăng nhập vào tài khoản của bạn để bắt đầu nào ! ",
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal
        )

    }
}