package com.rentify.user.app.view.userScreens.BillScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.MainActivity.ROUTER
import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.LoginRepository.LoginRepository
import com.rentify.user.app.view.auth.components.HeaderComponent
import com.rentify.user.app.view.userScreens.BillScreen.Component.CustomTabBar
import com.rentify.user.app.view.userScreens.contract.components.ContractTopBar
import com.rentify.user.app.viewModel.ContractViewModel
import com.rentify.user.app.viewModel.InvoiceViewModel
import com.rentify.user.app.viewModel.LoginViewModel

@Composable
fun BillScreen(
    navController: NavController,
    invoiceViewModel: InvoiceViewModel = viewModel(),
    contractViewModel: ContractViewModel = viewModel()
) {
    val context = LocalContext.current
    val apiService = RetrofitService()
    val userRepository = LoginRepository(apiService)
    val factory = remember(context) {
        LoginViewModel.LoginViewModelFactory(userRepository, context.applicationContext)
    }
    val loginViewModel: LoginViewModel = viewModel(factory = factory)
    val userId = loginViewModel.getUserData().userId
    ////////////////////////////////////////////////////////////////////////////////////////
    val contractDetails by contractViewModel.contract.observeAsState()

    LaunchedEffect(userId) {
        contractViewModel.getContractDetails(userId)
    }

    val roomId = contractDetails?.let { detailsList ->
        detailsList.firstOrNull()?.let { details ->
            details.room_id._id
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////
    var searchText by remember { mutableStateOf("") }
    var selectedTabIndex by remember { mutableStateOf(0) }

    val tabs = listOf(
        "Chưa thanh toán" to "unpaid",
        "Đang đợi duyệt" to "wait",
        "Đã thanh toán" to "paid"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            InvoiceTopBar(navController)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White)
            ) {
                // TabBar
                CustomTabBar(
                    items = tabs.map { it.first }, // Chỉ lấy tiêu đề để hiển thị
                    selectedIndex = selectedTabIndex,
                    onTabSelected = { index ->
                        selectedTabIndex = index
                    }
                )

                // Truyền `status` vào các hàm
                when (tabs[selectedTabIndex].second) {
                    "unpaid" -> {
                        if (roomId != null) {
                            UnPaidScreen(
                                navController = navController,
                                roomId = roomId,
                                invoiceViewModel = invoiceViewModel,
                                status = "unpaid"
                            )
                        }
                    }
                    "wait" -> {
                        if (roomId != null) {
                            WantScreen(
                                navController = navController,
                                roomId = roomId,
                                invoiceViewModel = invoiceViewModel,
                                status = "wait"
                            )
                        }
                    }
                    "paid" -> {
                        if (roomId != null) {
                            PaidScreen(
                                roomId = roomId,
                                invoiceViewModel = invoiceViewModel,
                                status = "paid",
                                navController
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBillScreen() {
    BillScreen(navController = rememberNavController())
}

@Composable
fun InvoiceTopBar(
    navController: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        androidx.compose.material.IconButton(
            onClick = { navController.popBackStack() }
        ) {
            androidx.compose.material.Icon(
                imageVector = Icons.Filled.ArrowBackIosNew,
                contentDescription = "Back"
            )
        }

        Spacer(modifier = Modifier.width(8.dp)) // Khoảng cách giữa icon và text

        androidx.compose.material.Text(
            text = "Hoá đơn",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 18.sp,
            style = MaterialTheme.typography.h6
        )
    }
}