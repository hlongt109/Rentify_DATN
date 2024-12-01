package com.rentify.user.app.view.userScreens.cancelContract

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.view.userScreens.cancelContract.components.ContractInfoRow
import com.rentify.user.app.view.userScreens.cancelContract.components.HeaderSection

@Composable
fun CancelContractScreen(navController: NavController) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    val scrollState = rememberScrollState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .background(color = Color(0xfff3f3f3))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()

                .padding(bottom = screenHeight.dp / 3.7f)
        ) {
            HeaderSection(
                backgroundColor = Color.White,
                title = "Xem hợp đồng",
                navController = navController
            )
            Spacer(modifier = Modifier.height(30.dp))
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier

                        .background(color = Color(0xFFffffff))
                ) {
                    Column(
                        modifier = Modifier
                            .padding(15.dp)
                            .background(color = Color(0xFFffffff))
                    ) {
                        Text(
                            text = "Thông tin hợp đồng",
                            color = Color.Black,
                            fontWeight = FontWeight(700),
                            fontSize = 17.sp
                        )
                        ContractInfoRow("Số hợp đồng", "ABC/1234")
                        ContractInfoRow("Loại hợp đồng", "6 tháng")
                        ContractInfoRow("Thời hạn ký kết", "01/09/2024")
                        ContractInfoRow("Thời hạn kết thúc", "01/04/2024")
                        ContractInfoRow("Tiền cọc", "3.500.000")
                        ContractInfoRow("Tiền thuê", "3.500.000 VND / tháng")
                        ContractInfoRow("Kỳ thanh toán", "01 - 05 hằng tháng")
                    }
                }
                Spacer(
                    modifier = Modifier
                        .background(color = Color(0xFFeeeeee))
                        .fillMaxWidth()
                        .height(25.dp)
                )

            }
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingLayoutCancelContractScreen() {
    CancelContractScreen(navController = rememberNavController())
}