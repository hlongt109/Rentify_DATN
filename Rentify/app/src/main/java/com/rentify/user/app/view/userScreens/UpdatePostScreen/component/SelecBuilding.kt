package com.rentify.user.app.view.userScreens.UpdatePostScreen.component

import androidx.compose.foundation.background

import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.padding

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape


import androidx.compose.ui.Alignment

import androidx.compose.ui.draw.shadow

import androidx.compose.ui.res.painterResource

import com.rentify.user.app.R

import androidx.compose.material3.Icon

import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel

import com.google.accompanist.flowlayout.FlowRow
import com.rentify.user.app.model.Building
import com.rentify.user.app.viewModel.PostViewModel.PostViewModel


@Composable
fun BuildingLabel() {
    Row(
        modifier = Modifier
            .border(
                width = 2.dp, color = Color(0xFFeeeeee), shape = RoundedCornerShape(9.dp)
            )
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = R.drawable.comfortable),
            contentDescription = null,
            modifier = Modifier.size(30.dp, 30.dp)
        )
        Spacer(modifier = Modifier.width(3.dp))
        Text(
            text = "Tòa",
            color = Color.Black,
            fontSize = 13.sp,
        )
    }
}
@Composable
fun BuildingOptions(
    roomId: String,
    selectedBuilding: String?
) {
    val buildingViewModel: PostViewModel = viewModel()
    val buildings by buildingViewModel.buildingss.collectAsState(initial = emptyList())

    LaunchedEffect(roomId) {
        buildingViewModel.fetchBuildingForRoom(roomId)
    }

    FlowRow(
        modifier = Modifier.padding(5.dp),
        mainAxisSpacing = 10.dp,
        crossAxisSpacing = 10.dp
    ) {
        buildings.forEach { building ->
            BuildingOption(
                text = building.nameBuilding,
                isSelected = building._id == selectedBuilding,
                onClick = {
                    buildingViewModel.updateSelectedBuilding(building._id) // Cập nhật khi chọn tòa nhà
                }
            )
        }
    }
}






@Composable
fun BuildingOption(
    text: String, isSelected: Boolean, onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clickable(
                onClick = onClick,
                indication = null,
                interactionSource = remember { MutableInteractionSource() })
            .shadow(3.dp, shape = RoundedCornerShape(9.dp))
            .border(
                width = 1.dp,
                color = if (isSelected) Color(0xFF44acfe) else Color(0xFFeeeeee),
                shape = RoundedCornerShape(9.dp)
            )
            .background(color = Color.White, shape = RoundedCornerShape(9.dp))
            .padding(0.dp)
    ) {
        androidx.compose.material.Text(
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
                        color = Color(0xFF44acfe),  // Màu của dấu tích
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



