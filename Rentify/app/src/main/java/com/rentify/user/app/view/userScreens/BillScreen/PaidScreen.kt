package com.rentify.user.app.view.userScreens.BillScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.request.ImageRequest
import com.rentify.user.app.R
import com.rentify.user.app.view.userScreens.BillScreen.Component.ItemPaid
import com.rentify.user.app.view.userScreens.BillScreen.Component.ItemUnPaid
import com.rentify.user.app.view.userScreens.BillScreen.Component.groupItemsByMonthYear
import com.rentify.user.app.viewModel.InvoiceViewModel
import java.util.Calendar

@Composable
fun PaidScreen(
    roomId: String,
    invoiceViewModel: InvoiceViewModel,
    status: String,
    navController: NavController
) {
    val listInvoices by invoiceViewModel.listInvoice.observeAsState(emptyList())
    val errorMessage by invoiceViewModel.errorMessage.observeAsState("")
    val isLoading by invoiceViewModel.isLoading.observeAsState(false)

    LaunchedEffect(roomId, status) {
        invoiceViewModel.fetchListInvoice(roomId, status)
    }

    Box(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxSize()
    ) {
        when {
            isLoading -> {
                // Hiển thị GIF loading
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(R.drawable.loading)
                            .decoderFactory(GifDecoder.Factory())
                            .build(),
                        contentDescription = "Loading GIF",
                        modifier = Modifier.size(150.dp)
                    )
                }
            }

            listInvoices.isEmpty() -> {
                // Hiển thị thông báo không có dữ liệu
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.chinhsach),
                        contentDescription = "No Data",
                        modifier = Modifier.size(100.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = errorMessage.ifEmpty { "Không có dữ liệu" },
                        color = Color.Gray,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }

            else -> {
                // Hiển thị danh sách hóa đơn
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
}
