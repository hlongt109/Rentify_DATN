package com.rentify.user.app.view.staffScreens.BillScreenStaff

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.rentify.user.app.network.ApiStaff.RetrofitStaffService
import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.LoginRepository.LoginRepository
import com.rentify.user.app.repository.StaffRepository.InvoiceRepository.InvoiceRepository
import com.rentify.user.app.utils.Component.getLoginViewModel
import com.rentify.user.app.view.auth.components.HeaderComponent
import com.rentify.user.app.view.staffScreens.BillScreenStaff.Componenet.PaidStaffScreen
import com.rentify.user.app.view.staffScreens.BillScreenStaff.Componenet.UnPaidStaffScreen
import com.rentify.user.app.view.staffScreens.BillScreenStaff.Componenet.WaitStaffScreen
import com.rentify.user.app.view.userScreens.BillScreen.Component.CustomTabBar
import com.rentify.user.app.view.userScreens.BillScreen.PaidScreen
import com.rentify.user.app.view.userScreens.BillScreen.UnPaidScreen
import com.rentify.user.app.viewModel.LoginViewModel
import com.rentify.user.app.viewModel.StaffViewModel.InvoiceStaffViewModel

@Composable
fun BillScreenStaff(navController: NavController) {

    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Chưa thanh toán", "Đang chờ duyệt", "Đã thanh toán")

    val context = LocalContext.current
    val loginViewModel = getLoginViewModel(context)
    val invoiceService = RetrofitStaffService
    val invoiceRepository = InvoiceRepository(invoiceService.ApiService)
    val invoiceStaffViewModel: InvoiceStaffViewModel = viewModel(
        factory = InvoiceStaffViewModel.InvoiceStaffViewModelFactory(invoiceRepository)
    )

    val userData = loginViewModel.getUserData()
    val staffId = userData.userId

    LaunchedEffect(Unit) {
        invoiceStaffViewModel.getInvoiceList(staffId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            BillManagerTopBar(navController = navController)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White)
            ) {
                //tabbar
                CustomTabBar(
                    items = tabs,
                    selectedIndex = selectedTabIndex,
                    onTabSelected = { index ->
                        selectedTabIndex = index
                    }
                )
                // Nội dung của từng tab

                when (selectedTabIndex) {
                    0 -> UnPaidStaffScreen(
                        navController,
                        invoiceStaffViewModel,
                        staffId
                    )

                    1 -> WaitStaffScreen(
                        navController,
                        invoiceStaffViewModel,
                        staffId
                    )

                    2 -> PaidStaffScreen(navController, invoiceStaffViewModel, staffId)
                }

            }
        }
    }
}

@Composable
fun BillManagerTopBar(
    navController: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        androidx.compose.material.IconButton(onClick = { navController.popBackStack() }) {
            androidx.compose.material.Icon(
                imageVector = Icons.Filled.ArrowBackIosNew, contentDescription = "Back"
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        androidx.compose.material.Text(
            text = "Quản lý hoá đơn",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 18.sp,
            style = MaterialTheme.typography.h6
        )
    }
}

