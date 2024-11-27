package com.rentify.user.app.view.userScreens.SearchRoomScreen.SearchRoomComponent

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentify.user.app.model.Room
import com.rentify.user.app.ui.theme.colorBgItem
import com.rentify.user.app.ui.theme.colorLocation
import com.rentify.user.app.ui.theme.colorTextSX

@Composable
fun ItemTypeRoom(
    itemType: Room,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) colorLocation else colorBgItem
    val colorText = if (isSelected) Color.White else colorTextSX
    var isPressedItem by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 0.95f else 1f,
        label = ""
    )
    Box(
        modifier = Modifier
            .scale(scale)
            .background(color = backgroundColor, shape = RoundedCornerShape(15.dp))
            .width(110.dp)
            .height(30.dp)
            .clip(RoundedCornerShape(15.dp))
            .clickable(
                enabled = true,
                indication = ripple(bounded = true),
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onClick()
                isPressedItem = !isPressedItem
            }
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        when {
                            event.type == PointerEventType.Press -> isPressedItem = true
                            event.type == PointerEventType.Release -> isPressedItem = false
                        }
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = itemType.room_type,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = colorText,
        )
    }
}