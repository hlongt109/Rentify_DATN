package com.rentify.user.app.view.staffScreens.RoomDetailScreen.components
import androidx.compose.foundation.background

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.IconButton


import androidx.compose.ui.Alignment

import androidx.compose.ui.draw.shadow

import androidx.compose.ui.res.painterResource

import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.rentify.user.app.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.runtime.MutableState

import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
@Composable
fun RoomTypeOptionschitiet(
    apiSelectedRoomTypes: List<String>, // Dữ liệu từ API
    onRoomTypeSelected: (String) -> Unit
) {
    // Trạng thái lưu loại phòng được chọn (chỉ một phòng được chọn tại một thời điểm)
    val selectedRoomType = remember { mutableStateOf(apiSelectedRoomTypes.firstOrNull() ?: "") }

    Row(
        modifier = Modifier.padding(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Tùy chọn Phòng Đơn
        RoomTypeOption(
            text = "Phòng Đơn",
            isSelected = selectedRoomType.value == "Phòng Đơn",
            onClick = {
                selectedRoomType.value = "Phòng Đơn"
                onRoomTypeSelected("Phòng Đơn")
            }
        )

        Spacer(modifier = Modifier.width(10.dp))

        // Tùy chọn HomeStay
        RoomTypeOption(
            text = "HomeStay",
            isSelected = selectedRoomType.value == "HomeStay",
            onClick = {
                selectedRoomType.value = "HomeStay"
                onRoomTypeSelected("HomeStay")
            }
        )

        Spacer(modifier = Modifier.width(10.dp))

        // Tùy chọn Studio
        RoomTypeOption(
            text = "Studio",
            isSelected = selectedRoomType.value == "Studio",
            onClick = {
                selectedRoomType.value = "Studio"
                onRoomTypeSelected("Studio")
            }
        )
    }
}


// Hàm cập nhật danh sách các mục đã chọn
fun updateSelectedRoomTypes(
    roomType: String,
    selectedRoomTypes: MutableState<List<String>>
) {
    selectedRoomTypes.value = if (selectedRoomTypes.value.contains(roomType)) {
        selectedRoomTypes.value - roomType // Bỏ chọn
    } else {
        selectedRoomTypes.value + roomType // Thêm vào mục đã chọn
    }
}

@Composable
fun RoomTypeOption(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clickable(
                onClick = onClick,
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            )
            .shadow(2.dp, shape = RoundedCornerShape(9.dp))
            .border(
                width = 1.dp,
                color = if (isSelected) Color(0xFF44acfe) else Color(0xFFeeeeee),
                shape = RoundedCornerShape(9.dp)
            )
            .background(color = Color.White, shape = RoundedCornerShape(9.dp))
            .padding(0.dp)
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

        if (isSelected) {
            Box(
                modifier = Modifier
                    .size(23.dp)
                    .align(Alignment.TopStart)
                    .background(
                        color = Color(0xFF44acfe),
                        shape = TriangleShape()
                    ),
                contentAlignment = Alignment.TopStart
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.tick),
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

class TriangleShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            moveTo(0f, 0f)
            lineTo(size.width, 0f)
            lineTo(0f, size.height)
            close()
        }
        return Outline.Generic(path)
    }
}
