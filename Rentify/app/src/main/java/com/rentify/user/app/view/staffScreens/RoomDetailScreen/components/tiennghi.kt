package com.rentify.user.app.view.staffScreens.RoomDetailScreen.components

import androidx.compose.foundation.background

import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.padding

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import com.rentify.user.app.R
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.google.accompanist.flowlayout.FlowRow
import com.rentify.user.app.view.staffScreens.addRoomScreen.Components.TriangleShape


@Composable
fun ComfortableOptionsFromApi(
    selectedComfortables: List<String>,
    onComfortableChange: (List<String>) -> Unit,
    databaseComfortables: List<String>
) {
    // Danh sách cố định
    val fixedComfortables = listOf(
        "Vệ sinh khép kín",
        "Gác xép",
        "Ra vào vân tay",
        "Nuôi pet",
        "Không chung chủ"
    )

    // Gộp tiện nghi cố định với tiện nghi từ database và loại bỏ trùng lặp
    val combinedComfortables = (fixedComfortables + databaseComfortables).distinct()

    FlowRow(
        modifier = Modifier.padding(5.dp),
        mainAxisSpacing = 10.dp,
        crossAxisSpacing = 10.dp
    ) {
        combinedComfortables.forEach { option ->
            ComfortableOptionFromApi(
                text = option,
                isSelected = selectedComfortables.contains(option),
                onClick = {
                    val updatedList = selectedComfortables.toMutableList()
                    if (updatedList.contains(option)) {
                        updatedList.remove(option)
                    } else {
                        updatedList.add(option)
                    }
                    onComfortableChange(updatedList)
                }
            )
        }
    }
}


@Composable
fun ComfortableOptionFromApi(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .shadow(2.dp, shape = RoundedCornerShape(9.dp))
            .border(
                width = 1.dp,
                color = if (isSelected) Color(0xFF44acfe) else Color(0xFFeeeeee),
                shape = RoundedCornerShape(9.dp)
            )
            .background(color = Color.White, shape = RoundedCornerShape(9.dp))
            .padding(0.dp)
            .clickable { onClick() } // Xử lý sự kiện click
    ) {
        Text(
            text = text,
            color = if (isSelected) Color(0xFF44acfe) else Color(0xFF000000),
            fontSize = 13.sp,
            modifier = Modifier
                .background(color = if (isSelected) Color(0xFFffffff) else Color(0xFFeeeeee))
                .padding(14.dp)
                .align(Alignment.Center)
        )

        // Dấu tích ở góc khi được chọn
        if (isSelected) {
            Box(
                modifier = Modifier
                    .size(23.dp)
                    .align(Alignment.TopStart)
                    .background(
                        color = Color(0xFF44acfe), // Màu của dấu tích
                        shape = TriangleShape()
                    ),
                contentAlignment = Alignment.TopStart
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.tick), // ID của icon dấu tích
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

