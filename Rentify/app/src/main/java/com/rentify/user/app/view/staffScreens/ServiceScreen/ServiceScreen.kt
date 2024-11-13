package com.rentify.user.app.view.staffScreens.ServiceScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.ui.theme.colorLocation
import com.rentify.user.app.view.auth.components.HeaderComponent
import com.rentify.user.app.view.staffScreens.ServiceScreen.Component.HeaderServiceComponent
import com.rentify.user.app.view.staffScreens.ServiceScreen.Component.ItemService
import com.rentify.user.app.view.userScreens.BillScreen.Component.ItemUnPaid

@Composable
fun ServiceScreen(navController: NavController){
    val listService = FakeData().fakeServices
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(bottom = 50.dp)
    ){
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            HeaderServiceComponent(backgroundColor = Color.White, title = "Dịch vụ", navController = navController)
            Column(
                modifier = Modifier.fillMaxSize().padding(15.dp)
            ){
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = PaddingValues(7.dp)
                ) {
                    items(listService) { item ->
                        ItemService(item, navController)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewScreen(){
    ServiceScreen(navController = rememberNavController())
}