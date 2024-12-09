package com.rentify.user.app.view.userScreens.SurroundingRoomsScreen.Component

import android.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DrawerDefaults.backgroundColor
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.LatLng
import com.rentify.user.app.ui.theme.ColorBlack

@Composable
fun CustomMarker(
    title: String,
    position: LatLng,
){
    Box(
        modifier = Modifier
            .background(
                color = backgroundColor.copy(alpha = 0.8f),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp)
    ) {
        Text(
            text = title,
            color = ColorBlack,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
    }
}