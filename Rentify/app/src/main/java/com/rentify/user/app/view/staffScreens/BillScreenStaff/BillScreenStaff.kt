package com.rentify.user.app.view.staffScreens.BillScreenStaff

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
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
import com.rentify.user.app.view.userScreens.BillScreen.Component.CustomTabBar
import com.rentify.user.app.view.userScreens.BillScreen.PaidScreen
import com.rentify.user.app.view.userScreens.BillScreen.UnPaidScreen
import com.rentify.user.app.viewModel.LoginViewModel
import com.rentify.user.app.viewModel.StaffViewModel.InvoiceStaffViewModel

@Composable
fun BillScreenStaff(navController: NavController) {

    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Chưa thanh toán", "Đã thanh toán")

    val context = LocalContext.current
    val loginViewModel = getLoginViewModel(context)
    val invoiceService = RetrofitStaffService
    val invoiceRepository = InvoiceRepository(invoiceService.ApiService)
    val invoiceStaffViewModel: InvoiceStaffViewModel = viewModel(
        factory = InvoiceStaffViewModel.InvoiceStaffViewModelFactory(invoiceRepository)
    )

    // Load user data khi màn hình được tạo
//    LaunchedEffect(Unit) {
//        loginViewModel.getUserData()
//    }
    val userData = loginViewModel.getUserData()
    Log.d("UserData", "BillScreenStaffUserData: $userData")
    val staffId = userData.userId
//    Log.d("StaffId", "BillScreenStaff: $staffId")
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
            HeaderComponent(
                backgroundColor = Color.White,
                title = "Hóa đơn",
                navController = navController
            )
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

                    1 -> PaidStaffScreen(navController, invoiceStaffViewModel, staffId)
                }

            }
        }
    }
}