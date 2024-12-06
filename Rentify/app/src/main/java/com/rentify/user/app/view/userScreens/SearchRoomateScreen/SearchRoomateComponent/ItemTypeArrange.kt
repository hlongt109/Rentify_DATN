package com.rentify.user.app.view.userScreens.SearchRoomateScreen.SearchRoomateComponent

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ripple
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentify.user.app.model.FakeModel.FakeTypeArrange
import com.rentify.user.app.ui.theme.ColorBlack
import com.rentify.user.app.ui.theme.colorHeaderSearch
import com.rentify.user.app.ui.theme.colorInput_2

@Composable
fun ItemTypeArrange(
    item: FakeTypeArrange,
    isSelected: Boolean,
    onItemClicked: (FakeTypeArrange) -> Unit
) {
    var isPressedItem by remember { mutableStateOf(false) }
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(50.dp)
        .drawBehind {
            drawLine(
                color = colorInput_2,
                start = Offset(0f,0f),
                end = Offset(size.width, 0f),
                strokeWidth = 1.dp.toPx()
            )
        }
        .clickable(
            enabled = true,
            indication = ripple(bounded = true),
            interactionSource = remember { MutableInteractionSource() }
        )
        {
            onItemClicked(item)
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
        }){
        Row(modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            Row(verticalAlignment = Alignment.CenterVertically){
                Icon(
                    painter = painterResource(item.icon),
                    contentDescription = "",
                    modifier = Modifier.size(17.dp)
                )

                Text(
                    text = item.title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = ColorBlack,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }

            Checkbox(
                checked = isSelected,
                onCheckedChange = {onItemClicked(item)},
                colors = CheckboxDefaults.colors(
                    checkedColor = colorHeaderSearch,
                )
            )

        }
    }
}


