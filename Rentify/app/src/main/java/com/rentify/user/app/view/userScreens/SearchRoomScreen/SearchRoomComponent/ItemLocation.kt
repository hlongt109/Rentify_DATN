package com.rentify.user.app.view.userScreens.SearchRoomScreen.SearchRoomComponent

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentify.user.app.model.Location
import com.rentify.user.app.ui.theme.ColorBlack
import com.rentify.user.app.ui.theme.colorInput

@Composable
fun ItemLocation(
    item: Location,
    onClick: () -> Unit
) {
    Box(
        modifier =
        Modifier.fillMaxWidth()
    ) {
        Column (modifier = Modifier.fillMaxWidth()){
            Spacer(modifier = Modifier.padding(top = 10.dp))
            Text(
                text = item.city,
                modifier = Modifier,
                color = Color.Black,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.padding(top = 10.dp))
            Divider(color = colorInput, thickness = 1.dp)
        }
    }
}