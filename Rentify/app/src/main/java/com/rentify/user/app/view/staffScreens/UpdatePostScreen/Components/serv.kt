package com.rentify.user.app.view.staffScreens.UpdatePostScreen.Components

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

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.rentify.user.app.R

import androidx.compose.material3.Icon

import com.google.accompanist.flowlayout.FlowRow


@Composable
fun ServiceLabel() {
    Row(
        modifier = Modifier
            .border(
                width = 2.dp, color = Color(0xFFeeeeee), shape = RoundedCornerShape(9.dp)
            )
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = R.drawable.service),
            contentDescription = null,
            modifier = Modifier.size(30.dp, 30.dp)
        )
        Spacer(modifier = Modifier.width(3.dp))
        Text(
            text = "Phòng",
            color = Color.Black,
            fontSize = 13.sp,
        )
    }
}
//
//@Composable
//fun ServiceOptions(
//    selectedService: List<String>,
//    onServiceSelected: (String) -> Unit
//) {
//    FlowRow(
//        modifier = Modifier.padding(5.dp),
//        mainAxisSpacing = 10.dp, // Khoảng cách giữa các phần tử trên cùng một hàng
//        crossAxisSpacing = 10.dp // Khoảng cách giữa các hàng
//    ) {
//        listOf(
//            "Điều hoà",
//            "Kệ bếp",
//            "Tủ lạnh",
//            "Bình nóng lạnh",
//            "Máy giặt",
//            "Bàn ghế"
//        ).forEach { service ->
//            ServiceOption(
//                text = service,
//                isSelected = selectedService.contains(service),
//                onClick = {
//                    onServiceSelected(service)
//                }
//            )
//        }
//    }
//}
//
//@Composable
//fun ServiceOption(
//    text: String, isSelected: Boolean, onClick: () -> Unit
//) {
//    Box(
//        modifier = Modifier
//            .clickable(onClick = onClick, indication = null, interactionSource = remember { MutableInteractionSource() })
//            .shadow(3.dp, shape = RoundedCornerShape(6.dp))
//            .border(
//                width = 1.dp,
//                color = if (isSelected) Color(0xFF44acfe) else Color(0xFFeeeeee),
//                shape = RoundedCornerShape(9.dp)
//            )
//            .background(color = Color.White, shape = RoundedCornerShape(6.dp))
//            .padding(0.dp)
//    ) {
//        Text(
//            text = text,
//            color = if (isSelected) Color(0xFF44acfe) else Color(0xFF000000),
//            fontSize = 13.sp,
//            modifier = Modifier
//                .background(color = if (isSelected) Color(0xFFffffff) else Color(0xFFeeeeee))
//                .padding(14.dp)
//                .align(Alignment.Center)
//        )
//
//        // Dấu tích ở góc khi được chọn
//        if (isSelected) {
//            Box(
//                modifier = Modifier
//                    .size(23.dp)
//                    .align(Alignment.TopStart)
//                    .background(
//                        color = Color(0xFF44acfe),  // Màu của dấu tích
//                        shape = TriangleShape()
//                    ),
//                contentAlignment = Alignment.TopStart
//            ) {
//                Icon(
//                    painter = painterResource(id = R.drawable.tick), // ID của icon dấu tích
//                    contentDescription = null,
//                    tint = Color.White,
//                    modifier = Modifier
//                        .size(10.dp)
//                        .offset(x = 3.dp, y = 2.dp)
//                )
//            }
//        }
//    }
//}
//
