package com.rentify.user.app.view.userScreens.DieuKhoanChinhSach.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun DKCSTopBar(navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        androidx.compose.material.IconButton(
            onClick = { navController.popBackStack() }
        ) {
            androidx.compose.material.Icon(
                imageVector = Icons.Filled.ArrowBackIosNew,
                contentDescription = "Back"
            )
        }

        Spacer(modifier = Modifier.width(8.dp)) // Khoảng cách giữa icon và text

        androidx.compose.material.Text(
            text = "Điều Khoản và chính sách ",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 18.sp,
            style = MaterialTheme.typography.h6
        )
    }
}