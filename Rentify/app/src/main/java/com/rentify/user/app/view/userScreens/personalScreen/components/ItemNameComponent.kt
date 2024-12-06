package com.rentify.user.app.view.userScreens.personalScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.R
import com.rentify.user.app.utils.Component.getLoginViewModel

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ItemNameComponent() {
    LayoutItemName(navController = rememberNavController())
}

@Composable
fun LayoutItemName(navController: NavHostController) {
    val context = LocalContext.current
    val loginViewModel = getLoginViewModel(context)
    val userData = loginViewModel.getUserData()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .height(70.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color = Color.White)
            .clickable {
                navController.navigate("PROFILE")
            }
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.user),
            contentDescription = null,
            modifier = Modifier
                .width(50.dp)
                .height(50.dp)
                .clip(CircleShape)
        )
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = userData.name,
                fontSize = 14.sp,
                color = Color(0xff2d293a),
                modifier = Modifier
                    .padding(start = 5.dp),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.padding(3.dp))
            Text(
                text = userData.phoneNumber ?: "",
                fontSize = 12.sp,
                color = Color(0xff2d293a),
                modifier = Modifier
                    .padding(start = 5.dp),
                fontWeight = FontWeight.Bold
            )
        }
        // Sử dụng Spacer để đẩy hình ảnh "next" sang bên phải
        Spacer(modifier = Modifier.width(8.dp))
        Image(
            painter = painterResource(id = R.drawable.baseline_navigate_next_24),
            contentDescription = null,
            modifier = Modifier
                .width(30.dp)
                .height(30.dp)
                .padding(end = 5.dp)
                .clip(CircleShape)
        )
    }
}
