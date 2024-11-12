package com.rentify.user.app.view.staffScreens.ServiceScreen.Component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentify.user.app.ui.theme.colorLocation

@Composable
fun DialogComponent(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    title: String,
    textConfirm: String,
    textDismiss: String
) {
    if (showDialog) {
        AlertDialog(
            modifier = Modifier
                .height(150.dp),
            onDismissRequest = onDismiss,
            title = {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            buttons = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp)
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(
                            text = textDismiss,
                            color = colorLocation,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    TextButton(onClick = onConfirm) {
                        Text(
                            text = textConfirm,
                            color = Color.Red,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            },
            shape = RoundedCornerShape(15.dp),
            backgroundColor = Color.White
        )
    }
}
