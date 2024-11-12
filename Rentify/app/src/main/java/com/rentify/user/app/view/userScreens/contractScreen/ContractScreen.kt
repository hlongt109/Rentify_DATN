package com.rentify.user.app.view.userScreens.contractScreen

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
import com.rentify.user.app.view.userScreens.contractScreen.components.ContractBody
import com.rentify.user.app.view.userScreens.contractScreen.components.ContractHeading

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ContractScreenPreview(){
    ContractScreen(navController= rememberNavController())
}
@Composable
fun ContractScreen(navController: NavHostController){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
        }
        ContractHeading(navController)
        ContractBody(navController)
        ContractBody(navController)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
        }
    }
}