package com.rentify.user.app.view.userScreens.profileScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.view.userScreens.profileScreen.components.NoiDcomponent
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
            .verticalScroll(scrollState)
    ) {
        ProfileComponent(navController)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
        ) {
        }
        NoiDcomponent(navController)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
        ) {
        }
    }
}