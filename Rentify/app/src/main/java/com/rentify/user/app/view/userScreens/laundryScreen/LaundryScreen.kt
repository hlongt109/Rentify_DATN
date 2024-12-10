package com.rentify.user.app.view.userScreens.laundryScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.view.userScreens.laundryScreen.components.ItemgiatComponent
import com.rentify.user.app.view.userScreens.laundryScreen.components.TenComponent

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LaundryPreview(){
    LaundryScreen(navController= rememberNavController())
}
@Composable
fun LaundryScreen(navController: NavController){
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ){

//        TenComponent(navController)
//        ItemgiatComponent(navController)
    }
}