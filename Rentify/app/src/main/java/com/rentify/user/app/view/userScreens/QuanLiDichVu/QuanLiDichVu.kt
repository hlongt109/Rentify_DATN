package com.rentify.user.app.view.userScreens.QuanLiDichVu
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.rentify.user.app.R
import com.rentify.user.app.model.Building
import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.LoginRepository.LoginRepository
import com.rentify.user.app.view.userScreens.QuanLiDichVu.Components.PhanDauQuanLiDichVu
import com.rentify.user.app.viewModel.LoginViewModel
import com.rentify.user.app.viewModel.RoomViewModel.RoomViewModel


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
    val roomViewModel: RoomViewModel = viewModel(
        factory = RoomViewModel.RoomViewModeFactory(context)
    )
    val buildingWithRooms by roomViewModel.buildingWithRooms.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        roomViewModel.fetchBuildingsWithRooms(userId)
    }

    // Map tên dịch vụ với tài nguyên ảnh
    val serviceIconMap = mapOf(
        "Điện" to R.drawable.service,
        "Nước" to R.drawable.khepkin,
        "Wifi" to R.drawable.ad,
        "Dịch vụ chung" to R.drawable.dien,
        "sadsda" to R.drawable.add
    )

    Column {
        PhanDauQuanLiDichVu(navController)

        // Lặp qua từng BuildingWithRooms
        buildingWithRooms.forEach { building ->
            var expanded by remember { mutableStateOf(false) }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clickable { expanded = !expanded },
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Color.LightGray)
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Ảnh bên trái
                        Image(
                            painter = painterResource(id = R.drawable.property),
                            contentDescription = "Icon Building",
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                        )
                        Spacer(modifier = Modifier.width(12.dp))

                        // Văn bản
                        Text(
                            text = "Dịch vụ tòa nhà ${building.nameBuilding} của bạn",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Black,
                            modifier = Modifier.weight(1f)
                        )

                        // Biểu tượng mũi tên (nếu mở rộng thì dùng biểu tượng 'down', nếu không thì 'baseline_navigate_next_24')
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

                    // Khi mở rộng, hiển thị phần dịch vụ
                    AnimatedVisibility(visible = expanded) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                                .background(color = Color.White)
                        ) {
                            building.serviceFees.forEach { serviceFee ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp)
                                        .background(Color(0xFFF5F5F5)) // Nền nhẹ cho mỗi phần dịch vụ
                                ) {
                                    val serviceIcon = serviceIconMap[serviceFee.name] ?: R.drawable.error
                                    Image(
                                        painter = rememberImagePainter(data = serviceIcon),
                                        contentDescription = "Icon for ${serviceFee.name}",
                                        modifier = Modifier.size(30.dp)
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))

                                    Text(
                                        text = serviceFee.name,
                                        style = MaterialTheme.typography.bodySmall,
                                        modifier = Modifier
                                            .align(Alignment.CenterVertically)
                                            .weight(1f) // Nâng dịch vụ lên chiếm không gian còn lại
                                    )

                                    Text(
                                        text = "Giá: ${serviceFee.price} VND",
                                        style = MaterialTheme.typography.bodySmall,
                                        modifier = Modifier.align(Alignment.CenterVertically)
                                    )
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}





