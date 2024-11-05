package com.rentify.user.app.view.userScreens.serviceScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TeudePreview() {
    TeudeComponent()
}

@Composable
fun TeudeComponent() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(color = Color(0xffd2f1ff))
    ) {
        Text(text = "Dịch Vụ bán ",
            modifier = Modifier.padding(12.dp),
            fontWeight = FontWeight.Bold
            )
    }
}
