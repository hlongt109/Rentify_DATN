package com.rentify.user.app.view.userScreens.SearchRoomScreen.SearchRoomComponent

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentify.user.app.ui.theme.ColorBlack
import com.rentify.user.app.ui.theme.colorInput
import com.rentify.user.app.ui.theme.colorLocation

@Composable
fun ItemLocation(
    name: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val colorText = if (isSelected) colorLocation else ColorBlack
    Box(
        modifier =
        Modifier
            .fillMaxWidth()
            .height(50.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.padding(top = 10.dp))
            Text(
                text = name,
                modifier = Modifier.clickable { onClick() },
                color = colorText,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.padding(top = 15.dp))
            Divider(color = colorInput, thickness = 1.dp)
        }
    }
}