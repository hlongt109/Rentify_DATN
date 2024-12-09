package com.rentify.user.app.view.userScreens.profileScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.view.userScreens.profileScreen.components.FeetPersonalProfileUSER
import com.rentify.user.app.view.userScreens.profileScreen.components.FeetPersonalProfileuser
import com.rentify.user.app.view.userScreens.profileScreen.components.ProfileComponent

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun ProfilePreview(){
    ProfileScreen(navController=rememberNavController())
}
@Composable
fun ProfileScreen(navController: NavHostController){
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(scrollState)
    ) {
        ProfileComponent(navController)

        Column {
            Text(
                text = "Thông tin định danh",
                modifier = Modifier
                    .padding(20.dp),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
        FeetPersonalProfileuser(navController)
        Column {
            Text(
                text = "Thông tin tài khoản",
                modifier = Modifier
                    .padding(20.dp),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
        FeetPersonalProfileUSER(navController)
    }
}