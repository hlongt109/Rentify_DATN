package com.rentify.user.app.view.userScreens.paymentconfirmationScreen

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.LoginRepository.LoginRepository
import com.rentify.user.app.utils.CheckUnit
import com.rentify.user.app.view.userScreens.paymentconfirmationScreen.components.PaymentConfirmationBody
import com.rentify.user.app.view.userScreens.paymentconfirmationScreen.components.PaymentConfirmationHeading
import com.rentify.user.app.view.userScreens.roomdetailScreen.components.LayoutComfort
import com.rentify.user.app.view.userScreens.roomdetailScreen.components.LayoutInterior
import com.rentify.user.app.viewModel.LoginViewModel
import com.rentify.user.app.viewModel.QRViewModel
import java.io.File

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PaymentConfirmationScreenPreview() {
    PaymentConfirmationScreen(
        "",
        amount = 5000000,
        "",
        "",
        navController = rememberNavController()
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PaymentConfirmationScreen(
    invoiceId: String,
    amount: Int,
    buildingId : String,
    roomId : String,
    navController: NavHostController,
    qrViewModel: QRViewModel = viewModel()
) {

    val context = LocalContext.current
    val apiService = RetrofitService()
    val userRepository = LoginRepository(apiService)
    val factory = remember(context) {
        LoginViewModel.LoginViewModelFactory(userRepository, context.applicationContext)
    }
    val loginViewModel: LoginViewModel = viewModel(factory = factory)
    val userId = loginViewModel.getUserData().userId

    val formatPrice = CheckUnit.formattedPrice(amount.toFloat())
    var isChecked by remember { mutableStateOf(false) }
    var isPaymentMethodSelected by remember { mutableStateOf(false) }
    var isBankTransferSelected by remember { mutableStateOf(false) }
    var isCashSelected by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        PaymentChooseTopBar(navController)
        Spacer(modifier = Modifier.height(15.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(color = Color(0xFFf7f7f7))
                .verticalScroll(rememberScrollState())
        ) {
            PaymentConfirmationBody(
                isChecked = isChecked,
                onCheckedChange = { isChecked = it },
                isBankTransferSelected = isBankTransferSelected,
                isCashSelected = isCashSelected,
                onPaymentMethodSelected = { bankTransfer, cash ->
                    isBankTransferSelected = bankTransfer
                    isCashSelected = cash
                    isPaymentMethodSelected = true
                }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Tổng tiền: ",
                    modifier = Modifier.padding(15.dp),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = formatPrice,
                    modifier = Modifier.padding(15.dp),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )
            }

            Button(
                onClick = {
                    if (isChecked && isPaymentMethodSelected) {
                        if (isBankTransferSelected) {
                            // Điều hướng tới màn hình chuyển khoản
                            navController.navigate("Payments/${amount}/${buildingId}/${invoiceId}")
                        } else if (isCashSelected) {
                            // Gọi addSupport() khi thanh toán bằng tiền mặt
                            qrViewModel.addSupport(
                                userId = userId,
                                roomId = roomId,
                                buildingId = buildingId,
                                titleSupport = "Hỗ trợ thanh toán",
                                contentSupport = "Hoá đơn này phòng của tôi muốn được thanh toán bằng tiền mặt",
                                status = "1",
                                imageFiles = listOf()
                            )
                            qrViewModel.updateStatusInvoice(invoiceId)
                            Toast.makeText(
                                context,
                                "Đã ghi nhận thanh toán bằng tiền mặt. Cảm ơn bạn!",
                                Toast.LENGTH_SHORT
                            ).show()
                            navController.navigate("Invoice_screen") {
                                popUpTo("Invoice_screen") { inclusive = true }
                            }

                        }
                    } else {
                        Toast.makeText(
                            navController.context,
                            "Vui lòng chọn phương thức thanh toán và đồng ý với các điều khoản.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp)
                    .background(color = Color(0xFF84d8ff), shape = RoundedCornerShape(20.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF84d8ff)), // Tùy chỉnh màu nền
                shape = RoundedCornerShape(20.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "Tiến hành thanh toán",
                    modifier = Modifier.padding(5.dp),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    lineHeight = 24.sp
                )
            }
        }
    }
}


@Composable
fun PaymentChooseTopBar(
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
            text = "Xác nhận thanh toán",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 18.sp,
            style = MaterialTheme.typography.h6
        )
    }
}

