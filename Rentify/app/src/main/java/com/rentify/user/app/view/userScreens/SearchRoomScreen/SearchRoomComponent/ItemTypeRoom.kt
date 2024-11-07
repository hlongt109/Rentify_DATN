package com.rentify.user.app.view.userScreens.SearchRoomScreen.SearchRoomComponent

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    onClick: () -> Unit){
    val backgroundColor = if (isSelected) colorLocation else colorBgItem
    val colorText = if (isSelected) Color.White else colorTextSX
    Box (
        modifier = Modifier
            .background(color = backgroundColor, shape = RoundedCornerShape(15.dp))
            .width(110.dp)
            .height(30.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ){
        Text(
            text = itemType.roomType,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = colorText,
        )
    }
}