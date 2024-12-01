package com.rentify.user.app.view.userScreens.BillScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rentify.user.app.view.userScreens.BillScreen.Component.ItemPaid
import com.rentify.user.app.view.userScreens.BillScreen.Component.ItemUnPaid
import com.rentify.user.app.view.userScreens.BillScreen.Component.groupItemsByMonthYear
import com.rentify.user.app.viewModel.InvoiceViewModel
import java.util.Calendar

@Composable
fun PaidScreen(
    userId: String,
    invoiceViewModel: InvoiceViewModel,
    status: String,
    navController: NavController
){
    val listInvoices by invoiceViewModel.listInvoice.observeAsState(emptyList())
    LaunchedEffect(userId, status) {
        invoiceViewModel.fetchListInvoice(userId, status)
    }

    Box(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(7.dp)
            ) {
                items(listInvoices) { item ->
                    ItemUnPaid(item = item, navController = navController)
                }
            }
        }
    }
}