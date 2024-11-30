package com.rentify.user.app.view.staffScreens.contract.contractComponents

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.rentify.user.app.view.userScreens.cancelContract.components.ContractInfoRow
import com.rentify.user.app.view.userScreens.cancelContract.components.HeaderSection
import com.rentify.user.app.view.userScreens.cancelContract.components.ViewContractButton
import com.rentify.user.app.view.userScreens.contract.components.DialogCompose
import com.rentify.user.app.viewModel.StaffViewModel.ContractViewModel

@Composable
fun ContractDetailScreen(navController: NavController,contractId: String) {
//    val contractViewModel: ContractViewModel = viewModel()
//    val contractDetail by contractViewModel.contractDetail.observeAsState()
//
//    // Lấy chi tiết hợp đồng
//    LaunchedEffect(contractId) {
//        contractViewModel.fetchContractDetail(contractId)
//    }
//
//    contractDetail?.let { contract ->
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text(text = "Contract ID: ${contract.building_id?.nameBuilding}", fontWeight = FontWeight.Bold)
//            Text(text = "Loại hợp đồng: ${contract.duration}")
//            Text(text = "Thời hạn ký kết: ${contract.startDate}")
//            Text(text = "Thời hạn hết thúc: ${contract.endDate}")
//            Text(text = "Thời gian ký kết: ${contract.startDate}")
//            Text(text = "Tiền cọc: ${ contract.room_id?.price}")
//            Text(text = "Tiền thuê: ${contract.room_id?.price}")
//            Text(text = "Kỳ thanh toán: ${ contract.room_id?.price}")
//            // Hiển thị danh sách người thuê
//            contract.user_id?.forEach { user ->
//                Text(text = "Tenant: ${user.name}, Email: ${user.email}")
//            }
//        }
//    } ?: run {
//        // Hiển thị thông báo khi không có dữ liệu hợp đồng
//        Text(text = "Loading contract details...", modifier = Modifier.padding(16.dp))
//    }


        var context = LocalContext.current
    val contractViewModel: ContractViewModel = viewModel()
    val contractDetail by contractViewModel.contractDetail.observeAsState()

    // Lấy chi tiết hợp đồng
    LaunchedEffect(contractId) {
        contractViewModel.fetchContractDetail(contractId)
    }

        val configuration = LocalConfiguration.current
        val screenHeight = configuration.screenHeightDp
        val scrollState = rememberScrollState()
    contractDetail?.let { contract ->
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
            ) {
                HeaderSection(backgroundColor = Color.White, title = "Xem hợp đồng", navController = navController)
                Spacer(modifier = Modifier.height(30.dp))
                Column( modifier = Modifier
                    .verticalScroll(scrollState)
                    .fillMaxWidth()
                ){
                    Column(
                        modifier = Modifier

                            .background(color = Color(0xFFffffff))
                    ){
                        Column(
                            modifier = Modifier
                                .padding(15.dp)
                                .background(color = Color(0xFFffffff))
                        ) {
                            androidx.compose.material3.Text(
                                text = "Thông tin hợp đồng",
                                color = Color.Black,
                                fontWeight = FontWeight(700),
                                fontSize = 17.sp
                            )
                            ContractInfoRow("Số hợp đồng", "${contract.building_id?.nameBuilding}")
                            ContractInfoRow("Loại hợp đồng", "${contract.duration} tháng")
                            ContractInfoRow("Thời hạn ký kết", "${contract.start_date}")
                            ContractInfoRow("Thời hạn kết thúc", "${contract.end_date}")
                            ContractInfoRow("Tiền cọc", "${ contract.room_id?.price}")
                            ContractInfoRow("Tiền thuê", "${ contract.room_id?.price} VND / tháng")
                            ContractInfoRow("Kỳ thanh toán", "${contract.paymentCycle} hàng tháng")
                        }}
                    Spacer(modifier = Modifier
                        .background(color = Color(0xFFeeeeee))
                        .fillMaxWidth()
                        .height(25.dp))
                    Box(
                        modifier = Modifier
                            .padding(15.dp)
                            .fillMaxWidth()){
                        ViewContractButton(onClick = { navController.navigate("contract_image_detail/${contract._id}") })
                    }
                }
            }

        }
    }}


