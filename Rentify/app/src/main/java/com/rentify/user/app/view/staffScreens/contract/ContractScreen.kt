package com.rentify.manager.app.view.contract

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.MainActivity
import com.rentify.user.app.view.contract.contractComponents.ContractRoomListScreen
import com.rentify.user.app.view.contract.contractComponents.ContractTopBar
import com.rentify.user.app.view.staffScreens.postingList.PostingListComponents.PostListScreen
import com.rentify.user.app.view.staffScreens.postingList.PostingListComponents.PostingListTopBar
import com.rentify.user.app.viewModel.StaffViewModel.ContractViewModel


import kotlin.contracts.contract

@Preview(showBackground = true)
@Composable
fun ContractScreenPreview() {
    ContractScreen(navController = rememberNavController())
}

@Composable
fun ContractScreen(navController: NavHostController) {
    val viewModel: ContractViewModel = viewModel()
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {     navController.navigate("ADDCONTRAC_STAFF")  },
                containerColor = Color(0xFF2196F3),
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier.padding(bottom = 30.dp).padding(end = 20.dp)
            ) {
                // Icon bên trong FAB
                Icon(
                    imageVector = Icons.Default.Add, // Thay thế bằng icon của bạn
                    contentDescription = "Add",
                    tint = Color.White
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End, // Vị trí của FAB (có thể là Center)
        content = { paddingValues ->
            // Nội dung màn hình chính
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ContractTopBar(
                        onClickGoBack = { navController.popBackStack() },
                        contractViewModel = viewModel
                    )
                    ContractRoomListScreen(navController,manageId="674f1c2975eb705d0ff112b6")
                }
            }
        }
    )

}

