package com.rentify.user.app.view.userScreens.paymentscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.view.userScreens.paymentscreen.components.PaymentContent
import com.rentify.user.app.view.userScreens.paymentscreen.components.PaymentHeading
import com.rentify.user.app.viewModel.QRViewModel

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PaymentScreenPreview(){
    PaymentScreen(
        "",
        3000,
        "",
        navController= rememberNavController()
    )
}
@Composable
fun PaymentScreen(
    invoiceId: String,
    amount: Int,
    buildingId: String,
    navController: NavHostController,
    qrViewModel: QRViewModel = viewModel()
) {
    val bankAccountData by qrViewModel.bankAccount.observeAsState()

    LaunchedEffect(buildingId) {
        qrViewModel.fetchBankAccount(buildingId)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
    ) {
        PaymentTopBar(navController)

        // Kiểm tra dữ liệu và lấy thông tin từ manager hoặc landlord
        if (bankAccountData != null) {
            val bankAccountToShow = bankAccountData?.manager?.bankAccount
                ?: bankAccountData?.landlord?.bankAccount

            if (bankAccountToShow != null) {
                val imageUrls = bankAccountToShow.qr_bank.map { photoPath ->
                    "http://10.0.2.2:3000/$photoPath"
                } ?: emptyList()

                PaymentContent(
                    invoiceId = invoiceId,
                    accountName = bankAccountToShow.username ?: "",
                    accountNumber = bankAccountToShow.bank_number.toString() ?: "",
                    amount = amount ?: 0,
                    qrImageUrl = (imageUrls ?: "").toString(),
                    nameBank = bankAccountToShow.bank_name ?: "",
                    navController = navController
                )
            } else {
                Text(
                    text = "Không có thông tin tài khoản ngân hàng.",
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            }
        } else {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    }
}


@Composable
fun PaymentTopBar(
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

        Text(
            text = "Thanh toán",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 18.sp,
            style = MaterialTheme.typography.h6
        )
    }
}