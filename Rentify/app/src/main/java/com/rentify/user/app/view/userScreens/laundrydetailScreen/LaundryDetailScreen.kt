package com.rentify.user.app.view.userScreens.laundrydetailScreen

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
import com.rentify.user.app.view.userScreens.laundrydetailScreen.component.NDComponent
import com.rentify.user.app.view.userScreens.laundrydetailScreen.component.anhComponent
import com.rentify.user.app.view.userScreens.laundrydetailScreen.component.tenchitietComponent

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun LaundryDetailScreenPreview() {
    LaundryDetailScreenScreen(navController = rememberNavController())
}

@Composable
fun LaundryDetailScreenScreen(navController: NavHostController) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        tenchitietComponent(navController)
        anhComponent()
        NDComponent(navController)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        ) {
        }
    }
}