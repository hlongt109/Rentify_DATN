package com.rentify.user.app.view.userScreens.SurroundingRoomsScreen.Component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GpsFixed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.rentify.user.app.ui.theme.colorLocation
import com.rentify.user.app.ui.theme.map_layer

@Composable
fun PositionButton(
    onClick: () -> Unit
){
    Box(
        modifier = Modifier
            .size(50.dp)
            .background(colorLocation, shape = RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .zIndex(100000f),
        contentAlignment = Alignment.Center
    ){
        IconButton(
            onClick = onClick
        ) {
            Icon(
                imageVector = Icons.Default.GpsFixed,
                contentDescription = "Position",
                tint = Color.White
            )
        }
    }
}

@Composable
fun ChangeStyleMap(
    currentStyle: String,
    onStyleChange: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .background(colorLocation, shape = RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .zIndex(100000f),
        contentAlignment = Alignment.Center
    ){
        IconButton(
            onClick = onStyleChange
        ) {
            Image(
                painter = painterResource(map_layer),
                modifier = Modifier.size(25.dp),
                contentDescription = "Thay đổi kiểu bản đồ",
            )
        }
    }
}
