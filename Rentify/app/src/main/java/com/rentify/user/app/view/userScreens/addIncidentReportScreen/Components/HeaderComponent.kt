package com.rentify.user.app.view.userScreens.addIncidentReportScreen.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.rentify.user.app.ui.theme.colorInput
import com.rentify.user.app.ui.theme.colorInput_2
import com.rentify.user.app.ui.theme.colorTabBar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.IconButton

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rentify.user.app.R
import com.rentify.user.app.ui.theme.colorUnTabBar

@Composable
fun HeaderComponent(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(0xff84d8ff))
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xff84d8ff)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                modifier = Modifier.width(100.dp),
                onClick = { navController.popBackStack() }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp, 30.dp)
                )
            }

            Text(
                text = "Báo cáo sự cố",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp
            )

            IconButton(
                modifier = Modifier.width(100.dp),
                onClick = { }
            ) {
            }
        }
    }
}







