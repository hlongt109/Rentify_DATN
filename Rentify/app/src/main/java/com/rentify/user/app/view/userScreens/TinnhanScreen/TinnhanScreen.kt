package com.rentify.user.app.view.userScreens.TinnhanScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun TinnhanScreenPreview(){
    TinnhanScreen(navController= rememberNavController())
}
@Composable
fun TinnhanScreen(navController: NavHostController){
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ){
        Text(text = "tin nhắn ")
    }
}