package com.rentify.user.app.view.staffScreens.homeScreen.Components

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.rentify.user.app.MainActivity
import com.rentify.user.app.R

@Composable
fun HeaderSection(navController: NavHostController) {
    Row(
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth()
            .background(color = Color(0xffffffff)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .width(100.dp)
                .padding(5.dp)

                .clickable(onClick = { /**/ }, indication = null, interactionSource = remember { MutableInteractionSource() }),
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.staff),
                contentDescription = null,
                modifier = Modifier  .clip(CircleShape) // Bo tròn hoàn toàn
                    .background(Color.White) .size(30.dp, 30.dp)
                    .clickable { navController.navigate(MainActivity.ROUTER.PersonalStaff.name) }
            )
        }
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
            modifier = Modifier.size(40.dp, 40.dp)
        )
        Row(
            modifier = Modifier
                .width(100.dp)
                .padding(5.dp)
                .clickable(onClick = { /**/ }, indication = null, interactionSource = remember { MutableInteractionSource() }),
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.noti),
                contentDescription = null,
                modifier = Modifier.size(30.dp, 30.dp)
                    .clickable {  }
            )
        }

    }
}
