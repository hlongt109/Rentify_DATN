package com.rentify.manager.app.view.contract

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.view.contract.contractComponents.ContractRoomListScreen
import com.rentify.user.app.view.contract.contractComponents.ContractTopBar


import kotlin.contracts.contract

@Preview(showBackground = true)
@Composable
fun ContractScreenPreview() {
    ContractScreen(navController = rememberNavController())
}

@Composable
fun ContractScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ContractTopBar()
        ContractRoomListScreen()
    }
}

