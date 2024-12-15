package com.rentify.user.app.view.userScreens.cancelContract.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentify.user.app.R

@Composable
fun CustomButton(
    onClick: () -> Unit,
    backgroundColor: Color,
    imageRes: Int? = null,
    buttonText: String,
    textColor: Color,
    modifier: Modifier = Modifier,
    borderWidth: Dp = 0.dp, // Mặc định không có viền
    borderColor: Color = Color.Transparent // Mặc định là trong suốt
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (borderWidth > 0.dp) Modifier.border(
                    width = borderWidth,
                    color = borderColor,
                    shape = RoundedCornerShape(10.dp)
                ) else Modifier
            ),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            imageRes?.let {
                Image(
                    painter = painterResource(id = it),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp, 30.dp)
                )
                Spacer(modifier = Modifier.width(8.dp)) // Khoảng cách giữa hình và text
            }
            Text(
                modifier = Modifier,
                text = buttonText,
                fontSize = 16.sp,
                color = textColor
            )
        }
    }
}


