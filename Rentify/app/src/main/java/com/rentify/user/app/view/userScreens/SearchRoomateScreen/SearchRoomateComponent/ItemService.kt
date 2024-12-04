package com.rentify.user.app.view.userScreens.SearchRoomateScreen.SearchRoomateComponent

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.ripple
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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentify.user.app.model.FakeModel.FakeService
import com.rentify.user.app.ui.theme.ColorBlack
import com.rentify.user.app.ui.theme.colorBgItem
import com.rentify.user.app.ui.theme.colorHeaderSearch
import com.rentify.user.app.ui.theme.colorInput_2
//import com.rentify.user.app.view.userScreens.SearchRoomScreen.FakeData
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import com.rentify.user.app.ui.theme.borderColor
import com.rentify.user.app.ui.theme.colorText
import com.rentify.user.app.ui.theme.tick

@Composable
fun ItemService(
    item: FakeService,
    isSelected: Boolean,
    onItemClick: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp
    val borderColor = if (isSelected) colorHeaderSearch else borderColor

    // Tạo custom shape cho góc dấu tích
    class TriangleShape : Shape {
        override fun createOutline(
            size: Size,
            layoutDirection: LayoutDirection,
            density: Density
        ): Outline {
            val path = Path().apply {
                moveTo(0f, 0f)  // Điểm bắt đầu
                lineTo(size.width, 1f)  // Đường ngang
                lineTo(1f, size.height)  // Đường dọc
                close()  // Đóng path để tạo tam giác
            }
            return Outline.Generic(path)
        }
    }

    //animation
    var isPressedItem by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .height(40.dp)
            .border(1.dp, color = borderColor, shape = RoundedCornerShape(5.dp))
            .clip(RoundedCornerShape(5.dp))
            .background(color = Color.White, shape = RoundedCornerShape(5.dp))
            .clickable(
                enabled = true,
                indication = ripple(bounded = true),
                interactionSource = remember { MutableInteractionSource() }

            ) {
                onItemClick()
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

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = item.title,
                fontSize = 15.sp,
                color = ColorBlack,
                textAlign = TextAlign.Center,
            )
        }


        // Dấu tích ở góc
        if (isSelected) {
            Box(
                modifier = Modifier
                    .size(25.dp)
                    .align(Alignment.TopStart)
                    .background(
                        color = colorHeaderSearch,
                        shape = TriangleShape()
                    ),
            ) {
                Icon(
                    painter = painterResource(tick),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(10.dp)
                        .offset(x = 3.dp, y = 2.dp)
                )
            }
        }
    }
}
