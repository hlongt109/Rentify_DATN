package com.rentify.user.app.view.staffScreens.BillScreenStaff.Componenet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.rentify.user.app.utils.CheckUnit
import com.rentify.user.app.view.userScreens.BillScreen.Component.groupItemsByMonthYear
import com.rentify.user.app.viewModel.StaffViewModel.InvoiceStaffViewModel
import com.rentify.user.app.viewModel.StaffViewModel.InvoiceUiState
import java.util.Calendar

@Composable
fun PaidStaffScreen(
    navController: NavController,
    viewModel: InvoiceStaffViewModel = viewModel(),
    staffId: String
) {

    val paidInvoices by viewModel.paidInvoices.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.getInvoiceList(staffId)
    }
    val groupedByYear = paidInvoices.groupBy {
        val calendar =
            Calendar.getInstance().apply { time = CheckUnit.parseDate(it.created_at ?: "") }
        calendar.get(Calendar.YEAR) // Lấy năm từ ngày
    }
    val sortedGroupedByYear = groupedByYear.toSortedMap(compareByDescending { it.toInt() })
    Box(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxSize()
            .padding(15.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            when (uiState) {
                is InvoiceUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is InvoiceUiState.Success -> {
                    if (paidInvoices.isEmpty()) {
                       Text(
                           text = "Không có hóa đơn đã thanh toán",
                           modifier = Modifier.align(Alignment.Center),
                           style = MaterialTheme.typography.bodyLarge
                       )
                    }else{
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            contentPadding = PaddingValues(7.dp)
                        ) {
                            sortedGroupedByYear.forEach { (year, paymentsByYear) ->
                                // Nhóm các mục trong năm hiện tại theo tháng
                                val groupedByMonth = groupItemsByMonthYear(paymentsByYear)

                                // Thêm ItemPaid cho mỗi năm
                                item {
                                    ItemPaidStaff(
                                        year = year.toString(),
                                        itemsByMonth = groupedByMonth
                                    )
                                }
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
        }
    }
}
