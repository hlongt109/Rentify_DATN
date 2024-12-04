package com.rentify.user.app.view.userScreens.SearchRoomateScreen.SearchRoomateComponent


import LocationState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple

import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf

import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
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
    onShowBottomSheet: () -> Unit,
    locationState: LocationState
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        label = ""
    )
    Row(
        modifier = Modifier
            .scale(scale)
            .fillMaxSize()
            .clip(RoundedCornerShape(12.dp))
            .background(color = Color.Transparent, shape = RoundedCornerShape(12.dp))
            // Sử dụng clickable đơn giản hơn
            .clickable(
                enabled = enabled,
                indication = ripple(bounded = true),
                interactionSource = remember { MutableInteractionSource() }//
            ){
                onShowBottomSheet()
            }
            .pointerInput (Unit){
                awaitPointerEventScope {
                    while(true){
                        val event = awaitPointerEvent()
                        when{
                            event.type == PointerEventType.Press -> isPressed =true
                            event.type == PointerEventType.Release -> isPressed = false
                        }
                    }
                }
            }
           ,
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
            text = locationState.fullAddress,
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