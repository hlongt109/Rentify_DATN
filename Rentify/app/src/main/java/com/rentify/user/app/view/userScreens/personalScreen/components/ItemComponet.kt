package com.rentify.user.app.view.userScreens.personalScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.MainActivity
import com.rentify.user.app.R
import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.LoginRepository.LoginRepository
import com.rentify.user.app.repository.SupportRepository.SupportRepository
import com.rentify.user.app.ui.theme.reset_password
import com.rentify.user.app.utils.Component.getLoginViewModel
import com.rentify.user.app.viewModel.LoginViewModel
import com.rentify.user.app.viewModel.NotificationViewmodel.NotificationViewmodel
import com.rentify.user.app.viewModel.UserViewmodel.CheckContractUiState
import com.rentify.user.app.viewModel.UserViewmodel.RoomSupportUiState
import com.rentify.user.app.viewModel.UserViewmodel.SupportViewModel

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ItemSComponent() {
    LayoutItems(navController = rememberNavController())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LayoutItems(
    navController: NavHostController
) {
    val apiService = RetrofitService()

    val notificationViewmodel: NotificationViewmodel = viewModel()

    // Quan sát danh sách thông báo
    val notificationsDetail by notificationViewmodel.notifications.observeAsState()
    val error by notificationViewmodel.error.observeAsState()
    //
    val supportService = RetrofitService()
    val supportRepository = SupportRepository(supportService.ApiService)
    val supportViewModel: SupportViewModel = viewModel(
        factory = SupportViewModel.SupportViewModelFactory(supportRepository)
    )
    val context = LocalContext.current
    val loginViewModel = getLoginViewModel(context)
    val userId = loginViewModel.getUserData().userId
    val showContractErrorDialog = remember { mutableStateOf(false) }
// Theo dõi trạng thái hợp đồng
    val contractState by supportViewModel.contractUiState.collectAsState()
    val email = loginViewModel.getUserData().email
    val navigationType = "changePassword"
    val userRepository = LoginRepository(apiService)
    val factory = remember(context) {
        LoginViewModel.LoginViewModelFactory(userRepository, context.applicationContext)
    }
    // Kiểm tra hợp đồng khi màn hình được tải
    LaunchedEffect(userId) {
        supportViewModel.checkUserContract(userId)
    }
    LaunchedEffect(Unit) {
        notificationViewmodel.getByUserNotification(userId)
    }
    // buttom sheet

    //
    if (showContractErrorDialog.value) {
        AlertDialog(
            onDismissRequest = { showContractErrorDialog.value = false },
            title = { Text("Thông báo") },

            text = { Text("Bạn cần có hợp đồng để sử dụng chức năng này. Hiện tại, bạn không có hợp đồng hoặc hợp đồng của bạn đã hết hạn.") },
            confirmButton = {
                TextButton(
                    onClick = { showContractErrorDialog.value = false }
                ) {
                    Text("OK")
                }
            }
        )
    }

    var showLogoutDialog by remember { mutableStateOf(false) }
    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Xác nhận đăng xuất") },
            text = { Text("Bạn có chắc chắn muốn đăng xuất không?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        // Xử lý logic đăng xuất ở đây
                        // Ví dụ: viewModel.logout() hoặc gọi hàm logout
                        loginViewModel.logout()
                        navController.navigate(MainActivity.ROUTER.SPLASH.name)
                        showLogoutDialog = false
                    }
                ) {
                    Text("Đăng xuất", color = Color.Red, fontSize = 14.sp)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showLogoutDialog = false }
                ) {
                    Text("Hủy", color = Color.Gray, fontSize = 14.sp)
                }
            }
        )
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xfff7f7f7))
            .padding(10.dp)
            .clip(RoundedCornerShape(10.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White)
        ) {
            Column(

            ) {
                CustomRow(
                    imageId = R.drawable.thongbao,
                    text = "Bạn có ${notificationsDetail?.data?.size?:0} thông báo ",
                    onClick = { navController.navigate("ThongBaoScreen") }
                )
                CustomRow(
                    imageId = R.drawable.qlbaidang,
                    text = "Quản lý bài đăng",
                    onClick = { navController.navigate("CATEGORYPOST") }
                )
                CustomRow(
                    imageId = R.drawable.qldonhang,
                    text = "Quản lý dịch vụ",
                    onClick = { navController.navigate("QuanLiDichVuUser") }
                )
                CustomRow(
                    imageId = R.drawable.lich,
                    text = "Lịch hẹn xem phòng",
                    onClick = { navController.navigate("AppointmentScreen") }
                )
                CustomRow(
                    imageId = R.drawable.bill,
                    text = "Hóa đơn",
                    onClick = { navController.navigate("Invoice_screen") }
                )
                CustomRow(
                    imageId = R.drawable.hdong,
                    text = "Hợp đồng",
                    onClick = { navController.navigate("ConTract") }
                )
                CustomRow(
                    imageId = R.drawable.postyt,
                    text = "Bài đăng yêu thích",
                    onClick = {
                        navController.navigate("BaiDangYeuThich")
                    }
                )
                CustomRow(
                    imageId = R.drawable.dkhoan,
                    text = "Điều khoản và chính sách",
                    onClick = {
                        navController.navigate("DieuKhoanChinhSach")
                    }
                )
                CustomRow(
                    imageId = R.drawable.baocao,
                    text = "Báo cáo sự cố",
                    onClick = {
                        if (contractState is CheckContractUiState.Success) {
                            val contract = (contractState as CheckContractUiState.Success).data
                            val isContractValid = contract.any { it.status == 0 } // Kiểm tra nếu có hợp đồng còn hiệu lực

                            if (isContractValid) {
                                // Nếu hợp đồng còn hiệu lực, điều hướng đến màn báo cáo sự cố
                                navController.navigate("INCIDENTREPORT")
                            } else if(isContractValid == null){
                                // Nếu không có hợp đồng hoặc hợp đồng đã hết hạn, hiển thị thông báo lỗi
                                showContractErrorDialog.value = true
                            }
                        } else {
                            // Nếu không thể lấy dữ liệu hợp đồng, hiển thị thông báo lỗi
                            showContractErrorDialog.value = true
                        }
                    }
                )

                CustomRow(
                    imageId = reset_password,
                    text = "Đổi mật khẩu",
                    onClick = {
                        navController.navigate(MainActivity.ROUTER.PREFORGOT.name+"/$email/$navigationType")
                    }
                )

                CustomRow(
                    imageId = R.drawable.out,
                    text = "Đăng xuất",
                    onClick = { showLogoutDialog = true }
                )

                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    shape = RectangleShape

                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center, // Căn giữa theo chiều ngang
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .background(color = Color(0xFFffffff))
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.delete),
                            contentDescription = null,
                            modifier = Modifier
                                .width(50.dp)
                                .height(50.dp)
                                .padding(10.dp)
                        )
                        Text(
                            text = "Yêu cầu xóa tài khoản",
                            fontSize = 20.sp,
                            color = Color(0xFF84d8ff),
                            textAlign = TextAlign.Center
                        )
                    }
                }


            }

        }
    }
}

@Composable
fun CustomRow(
    modifier: Modifier = Modifier,
    imageId: Int,
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color.Transparent),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        shape = RectangleShape

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Image(
                painter = painterResource(id = imageId),
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
            )
            Text(
                text = text,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.baseline_navigate_next_24),
                contentDescription = "",
                modifier = Modifier.size(25.dp)
            )
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .padding(horizontal = 15.dp)
            .background(color = Color(0xffcdccd1))
    ) {}
}
