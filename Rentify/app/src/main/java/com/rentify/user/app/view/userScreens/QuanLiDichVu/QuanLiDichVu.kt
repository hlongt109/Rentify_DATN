package com.rentify.user.app.view.userScreens.QuanLiDichVu
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.rentify.user.app.R
import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.LoginRepository.LoginRepository
import com.rentify.user.app.view.userScreens.QuanLiDichVu.Components.PhanDauQuanLiDichVu
import com.rentify.user.app.viewModel.LoginViewModel
import com.rentify.user.app.viewModel.UserViewmodel.UserViewModel
import java.text.DecimalFormat


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun QuanLiDichVuPreview() {
    QuanLiDichVu(navController = rememberNavController())
}
@Composable
fun QuanLiDichVu(navController: NavHostController) {
    val context = LocalContext.current
    val apiService = RetrofitService()
    val userRepository = LoginRepository(apiService)
    val factory = remember(context) {
        LoginViewModel.LoginViewModelFactory(userRepository, context.applicationContext)
    }
    val loginViewModel: LoginViewModel = viewModel(factory = factory)
    val userId = loginViewModel.getUserData().userId
    val viewModel: UserViewModel = viewModel()
    val decimalFormat = DecimalFormat("#,###,###")
    val serviceFees by viewModel.serviceFees.observeAsState(emptyList())
    LaunchedEffect(Unit) {
        viewModel.getServiceFeesByUser(userId)
    }

    val serviceIconMap = mapOf(
        "Điện" to R.drawable.electronic,
        "Nước" to R.drawable.water,
        "Wifi" to R.drawable.wifif,
        "Dịch vụ chung" to R.drawable.home,
        "Gửi xe" to R.drawable.motorbike,
        "Máy giặt" to R.drawable.maygiat,
        "Thang máy" to R.drawable.elevator,
        "Tủ lạnh" to R.drawable.tulanh,
        "Bảo trì" to R.drawable.baotri,
        "Bảo vệ" to R.drawable.baove
    )

    Column(
        modifier = Modifier
            .navigationBarsPadding()
            .statusBarsPadding()
    ) {
        PhanDauQuanLiDichVu(navController)

        // Lặp qua từng BuildingId
        serviceFees?.forEach { serviceFeeItem ->
            val building = serviceFeeItem.building_id
            building.serviceFees.forEach { serviceFee ->
                var expanded by remember { mutableStateOf(false) }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .clickable { expanded = !expanded },
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(3.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Biểu tượng dịch vụ
                            val serviceIcon = serviceIconMap[serviceFee.name] ?: R.drawable.error
                            Image(
                                painter = rememberImagePainter(data = serviceIcon),
                                contentDescription = "Icon for ${serviceFee.name}",
                                modifier = Modifier.size(35.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))

                            // Tên dịch vụ
                            Text(
                                text = serviceFee.name,
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.Black,
                                modifier = Modifier.weight(1f),
                                fontFamily = FontFamily.Serif
                            )

                            // Biểu tượng mũi tên
                            if (expanded) {
                                Image(
                                    painter = painterResource(id = R.drawable.down),
                                    contentDescription = "Navigate Down Icon",
                                    modifier = Modifier.size(24.dp)
                                )
                            } else {
                                Image(
                                    painter = painterResource(id = R.drawable.baseline_navigate_next_24),
                                    contentDescription = "Navigate Icon",
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }

                        // Hiển thị giá khi mở rộng
                        AnimatedVisibility(visible = expanded) {
                            Column {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp)
                                ) {
                                    Text(
                                        text = "Giá Tiền : ",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.Black,
                                        modifier = Modifier
                                            .padding(top = 8.dp)
                                            .weight(1f) // Đẩy về phía trái
                                    )
                                    Text(
                                        text = "${decimalFormat.format(serviceFee.price)} VND",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.Black,
                                        modifier = Modifier
                                            .padding(top = 8.dp)
                                            .weight(1f), // Đẩy về phía phải
                                        textAlign = TextAlign.End // Căn lề phải
                                    )
                                }

                                Spacer(modifier = Modifier.height(1.dp)
                                    .fillMaxWidth()
                                    .background(color = Color(0xFFd9d9d9))
                                    .padding(start = 10.dp, end = 10.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}







