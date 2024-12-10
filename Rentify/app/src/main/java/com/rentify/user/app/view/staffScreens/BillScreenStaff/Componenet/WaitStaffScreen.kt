package com.rentify.user.app.view.staffScreens.BillScreenStaff.Componenet

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.rentify.user.app.MainActivity.ROUTER
import com.rentify.user.app.ui.theme.colorHeaderSearch
import com.rentify.user.app.viewModel.StaffViewModel.InvoiceStaffViewModel
import com.rentify.user.app.viewModel.StaffViewModel.InvoiceUiState

@Composable
fun WaitStaffScreen(
    navController: NavController,
    viewModel: InvoiceStaffViewModel = viewModel(),
    staffId: String,
) {
    val unpaidInvoices by viewModel.waitInvoices.collectAsState()
    val error by viewModel.errorMessage.observeAsState("") // Nhận thông báo lỗi
    val uiState by viewModel.uiState.collectAsState()
    val isLoading by viewModel.isLoading.observeAsState()
    LaunchedEffect(Unit) {
        viewModel.getInvoiceList(staffId)
    }

    val expandedItems = remember { mutableStateListOf<String>() }
    Log.d("UnPaidList", "UnPaidStaffScreen: $unpaidInvoices")
    Box(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(bottom = 10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            when (uiState) {
                is InvoiceUiState.Loading -> {
                    //loading
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is InvoiceUiState.Success -> {
                    //danh sach hoa don chua thanh toan
                    if (unpaidInvoices.isEmpty()) {
                        Text(
                            text = "Không có hóa đơn chưa thanh toán",
                            modifier = Modifier.align(Alignment.Center),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(bottom = 80.dp)
                        ) {
                            items(unpaidInvoices) { invoice ->
                                ItemUnPaidStaff(
                                    invoice = invoice,
                                    navController = navController,
                                    isExpanded = expandedItems.contains(invoice._id),
                                    onToggleExpand = {
                                        if (expandedItems.contains(invoice._id)) {
                                            expandedItems.remove(invoice._id)
                                        } else {
                                            expandedItems.add(invoice._id)
                                        }
                                    },
                                    viewModel = viewModel,
                                    staffId = staffId
                                )
                            }
                        }
                    }
                }

                is InvoiceUiState.Error -> {
                    Text(
                        text = (uiState as InvoiceUiState.Error).message,
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.Red
                    )
                }
            }

//

            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(bottom = 20.dp, start = 16.dp, end = 16.dp)
            ) {
                Button(
                    onClick = { navController.navigate(ROUTER.ADDBILL_STAFF.name) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorHeaderSearch
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Thêm hóa đơn",
                        color = Color.White,
                        modifier = Modifier.padding(vertical = 4.dp),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}