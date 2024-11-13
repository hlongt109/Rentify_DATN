package com.rentify.user.app.view.userScreens.cancelContract.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.view.userScreens.CategoryPostScreen.components.SpacerHeightCompose
import com.rentify.user.app.R
@Composable
fun CancelContractOptions() {
    Column(
        modifier = Modifier.background(color = Color(0xfff5f5f5))
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
                .background(color = Color(0xFFfbd0cd), shape = RoundedCornerShape(9.dp))
                .clickable(onClick = { /**/ }, indication = null, interactionSource = remember { MutableInteractionSource() }),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.padding(20.dp),
                text = "Đã yêu cầu hủy hợp đồng",
                color = Color(0xffff303b),
                fontWeight = FontWeight(700),
                fontSize = 14.sp
            )
        }

        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
                .background(color = Color(0xffffffff))
                .clickable(onClick = { /**/ }, indication = null, interactionSource = remember { MutableInteractionSource() })
                .border(2.dp, Color(0xFF84d8ff), RoundedCornerShape(9.dp)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.padding(20.dp),
                text = "Hủy hợp đồng",
                color = Color(0xff000000),
                fontWeight = FontWeight(700),
                fontSize = 14.sp
            )
        }
    }
}
