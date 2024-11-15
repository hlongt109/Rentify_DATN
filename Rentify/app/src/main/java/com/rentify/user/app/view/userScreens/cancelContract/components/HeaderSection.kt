package com.rentify.user.app.view.userScreens.cancelContract.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rentify.user.app.R

@Composable
fun HeaderSection(
    backgroundColor: Color,
    title: String,
    navController: NavController
) {
    Row(
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth()
            .background(backgroundColor),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .width(100.dp)
                .padding(5.dp),
             horizontalArrangement = Arrangement.Center
        ) {
            IconButton(onClick = {
                navController.navigateUp()
            }){
            Image(
                painter = painterResource(id = R.drawable.left),
                contentDescription = null,
                modifier = Modifier.size(30.dp, 30.dp)
            )}
        }

        Text(
            text = title,
            color = Color.Black,
            fontWeight = FontWeight(700),
            fontSize = 17.sp
        )

        Spacer(modifier = Modifier.width(100.dp))
    }
}
