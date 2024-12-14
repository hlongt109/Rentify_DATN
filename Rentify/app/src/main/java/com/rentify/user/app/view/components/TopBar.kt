package com.rentify.user.app.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt

@Composable
fun TopBar(
    title: String,
    onBackClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xffffffff))
            .height(60.dp)
            .padding(start = 5.dp, end = 5.dp),
        contentAlignment = Alignment.Center
    ) {
        IconButton(
            onClick = { onBackClick() },
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                Icons.Outlined.ArrowBackIosNew, contentDescription = "back",
                modifier = Modifier.size(24.dp),
                tint = Color("#000000".toColorInt())
            )
        }
        Text(
            text = title,
            color = Color("#000000".toColorInt()),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}