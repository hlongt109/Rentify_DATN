package com.rentify.user.app.view.staffScreens.ServiceScreen.Component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rentify.user.app.MainActivity.ROUTER
import com.rentify.user.app.ui.theme.iconBack

@Composable
fun HeaderServiceComponent(
    backgroundColor: Color,
    title: String,
    navController: NavController,
) {
    Box(
        modifier = Modifier
            .background(backgroundColor)
            .fillMaxWidth()
            .height(80.dp)
    )
    {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = {
                navController.navigateUp()
            }) {
                Icon(
                    painter = painterResource(id = iconBack),
                    contentDescription = "icon back",
                    tint = Color.Black,
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp)
                )
            }

            Text(
                text = title,
                color = Color.Black,
                textAlign = TextAlign.Center,
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
            )

            IconButton(onClick = {
                navController.navigate("${ROUTER.ADDINCIDENTREPORT.name}/false")
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "add",
                    tint = Color.Black,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}