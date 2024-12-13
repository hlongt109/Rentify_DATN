package com.rentify.user.app.view.userScreens.contract

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.request.ImageRequest
import com.google.gson.Gson
import com.rentify.user.app.R
import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.LoginRepository.LoginRepository
import com.rentify.user.app.view.userScreens.cancelContract.components.ContractInfoRow
import com.rentify.user.app.view.userScreens.cancelContract.components.CustomButton
import com.rentify.user.app.view.userScreens.contract.components.ContractTopBar
import com.rentify.user.app.viewModel.ContractViewModel
import com.rentify.user.app.viewModel.LoginViewModel
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ContractScreen(
    navController: NavController,
    contractViewModel: ContractViewModel = viewModel()
) {
    val context = LocalContext.current
    val apiService = RetrofitService()
    val userRepository = LoginRepository(apiService)
    val factory = remember(context) {
        LoginViewModel.LoginViewModelFactory(userRepository, context.applicationContext)
    }
    val loginViewModel: LoginViewModel = viewModel(factory = factory)
    val userId = loginViewModel.getUserData().userId
    var searchText by remember { mutableStateOf("") } // Lưu trữ trạng thái tìm kiếm

    val contractDetails by contractViewModel.contract.observeAsState()

    val isLoading by contractViewModel.isLoading.observeAsState(false)

    val imageUrls = contractDetails?.firstOrNull()?.let { details ->
        details.photos_contract.map { photo ->
            "http://10.0.2.2:3000/$photo"
        }
    } ?: emptyList()

    LaunchedEffect(userId) {
        contractViewModel.getContractDetails(userId)
    }
    val scrollState = rememberScrollState()

    when {
        isLoading -> {
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
        contractDetails?.firstOrNull() == null && isLoading -> {
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
                    modifier = Modifier.size(250.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                androidx.compose.material.Text(
                    text = "Không có dữ liệu",
                    color = Color.Gray,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
        else -> {
            Column(
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
                    ContractTopBar(navController)
                    Spacer(modifier = Modifier.height(10.dp))
                    Column(
                        modifier = Modifier
                            .verticalScroll(scrollState)
                            .fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(10.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(color = Color(0xFFffffff))
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .background(color = Color(0xFFffffff))
                            ) {
                                Spacer(modifier = Modifier.padding(5.dp))
                                Text(
                                    text = "Thông tin hợp đồng",
                                    color = Color.Black,
                                    fontWeight = FontWeight(700),
                                    fontSize = 17.sp
                                )
                                contractDetails?.let { detailsList ->
                                    detailsList.firstOrNull()?.let { details ->
                                        // Định dạng giá trị tiền
                                        val formattedPrice = DecimalFormat("#,###,###").format(details.room_id.price)

                                        // Định dạng ngày tháng
                                        val startDate = details.start_date.let {
                                            try {
                                                val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                                                val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                                                val date = inputFormat.parse(it)
                                                outputFormat.format(date ?: Date())
                                            } catch (e: Exception) {
                                                it
                                            }
                                        }

                                        val endDate = details.end_date.let {
                                            try {
                                                val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                                                val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                                                val date = inputFormat.parse(it)
                                                outputFormat.format(date ?: Date())
                                            } catch (e: Exception) {
                                                it
                                            }
                                        }

                                        ContractInfoRow("Toà nhà", details.building_id.nameBuilding ?: "N/A")
                                        ContractInfoRow("Tên phòng", "${details.room_id.room_name} - ${details.room_id.room_type}" ?: "N/A")
                                        ContractInfoRow("Thời hạn ký kết", startDate ?: "N/A")
                                        ContractInfoRow("Thời hạn kết thúc", endDate ?: "N/A")
                                        ContractInfoRow("Tiền cọc", "$formattedPrice VNĐ", isImportant = true) // Màu sắc khác cho tiền cọc
                                        ContractInfoRow("Tiền thuê", "$formattedPrice VNĐ", isImportant = true) // Màu sắc khác cho tiền thuê
                                        ContractInfoRow("Kỳ thanh toán", "01 - 05 hằng tháng")
                                    }
                                }
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    Spacer(modifier = Modifier.padding(10.dp))
                                    CustomButton(
                                        onClick = {
                                            val gson = Gson()
                                            val encodedImageUrls = Uri.encode(gson.toJson(imageUrls))
                                            navController.navigate("CONTRACT/$encodedImageUrls")
                                        },
                                        backgroundColor = Color(0xFFFFFFFF), // Nền trắng
                                        imageRes = R.drawable.clipboard,
                                        buttonText = "Xem hình ảnh hợp đồng",
                                        textColor = Color(0xFF0066CC), // Chữ xanh dương
                                        borderWidth = 1.dp, // Độ rộng viền
                                        borderColor = Color(0xFF0066CC), // Màu viền xanh dương
                                        modifier = Modifier
                                            .height(50.dp)
                                            .shadow(2.dp, RoundedCornerShape(10.dp))
                                    )
                                    Spacer(modifier = Modifier.padding(6.dp))
                                    CustomButton(
                                        onClick = { /* Handle click */ },
                                        backgroundColor = Color(0xFF4CAF50),
                                        buttonText = "Gia hạn hợp đồng",
                                        textColor = Color.White,
                                        borderWidth = 0.dp, // Độ rộng viền
                                        borderColor = Color(0xFF0066CC),
                                        modifier = Modifier
                                            .height(50.dp)
                                            .shadow(2.dp, RoundedCornerShape(10.dp))
                                    )
                                    Spacer(modifier = Modifier.padding(6.dp))
                                    CustomButton(
                                        onClick = { /* Handle click */ },
                                        backgroundColor = Color(0xFFFF5252),
                                        buttonText = "Yêu cầu hợp đồng",
                                        textColor = Color.White,
                                        borderWidth = 0.dp, // Độ rộng viền
                                        borderColor = Color(0xFF0066CC),
                                        modifier = Modifier
                                            .height(50.dp)
                                            .shadow(2.dp, RoundedCornerShape(10.dp))
                                    )
                                    Spacer(modifier = Modifier.padding(6.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }


}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingLayoutContractScreen() {
    ContractScreen(navController = rememberNavController())
}