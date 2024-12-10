@file:JvmName("ContractInfoRowKt")

package com.rentify.user.app.view.userScreens.cancelContract.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ContractInfoRow(label: String, value: String, isImportant: Boolean = false, ) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp)
            .padding(horizontal = 16.dp) // Thêm padding cho phần bên ngoài
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Tiêu đề
            Text(
                text = label,
                color = Color(0xFF3E3E3E),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            // Giá trị
            Text(
                text = value,
                color = if (isImportant) Color(0xFF1D8E64) else Color(0xFF2F2F2F), // Màu khác cho giá trị quan trọng
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        }
        Divider(
            color = Color(0xFFE0E0E0),
            thickness = 1.dp,
        )
    }
}


