package com.rentify.user.app.view.userScreens.SearchRoomScreen.SearchRoomComponent


import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource

import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size

import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.runtime.remember

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentify.user.app.ui.theme.ColorBlack
import com.rentify.user.app.ui.theme.colorLocation
import com.rentify.user.app.ui.theme.colorTextSX
import com.rentify.user.app.ui.theme.down
import com.rentify.user.app.ui.theme.location


@Composable
fun LocationComponent(
    enabled: Boolean,
    onShowBottomSheet: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            // Sử dụng clickable đơn giản hơn
            .clickable(
                enabled = enabled,
                onClick = onShowBottomSheet
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(location),
            contentDescription = "location",
            modifier = Modifier
                .size(35.dp)
                .padding(end = 10.dp),
            tint = colorLocation,
        )
        Text(
            text = "Khu vực: ",
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = ColorBlack
        )
        Text(
            text = "Toàn quốc",
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = ColorBlack
        )
        Icon(
            painter = painterResource(down),
            contentDescription = "Down",
            tint = colorTextSX,
            modifier = Modifier.size(30.dp),
        )
    }
}