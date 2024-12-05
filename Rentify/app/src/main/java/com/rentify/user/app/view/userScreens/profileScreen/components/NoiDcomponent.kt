package com.rentify.user.app.view.userScreens.profileScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.R
import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.LoginRepository.LoginRepository
import com.rentify.user.app.viewModel.LoginViewModel
import com.rentify.user.app.viewModel.UserViewmodel.UserViewModel

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewFeetPersonalProfile(){
    FeetPersonalProfileuser(navController= rememberNavController())
    FeetPersonalProfileUSER(navController= rememberNavController())
}
@Composable
fun FeetPersonalProfileuser(navController: NavHostController) {
    val viewModel: UserViewModel = viewModel()
    val userDetail by viewModel.user.observeAsState()  // Quan sát LiveData người dùng
    val context = LocalContext.current
    val apiService = RetrofitService()
    val userRepository = LoginRepository(apiService)
    val factory = remember(context) {
        LoginViewModel.LoginViewModelFactory(userRepository, context.applicationContext)
    }
    val loginViewModel: LoginViewModel = viewModel(factory = factory)
    val userId = loginViewModel.getUserData().userId

    // Trạng thái cho các Dialog
    val showGenderDialog = remember { mutableStateOf(false) }
    val showDobDialog = remember { mutableStateOf(false) }
    val showAddressDialog = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getUserDetailById(userId)  // Lấy dữ liệu người dùng khi composable được gọi
    }

    Column {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(bottom = 20.dp, top = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            androidx.compose.material.Text(
                text = "Họ và tên ",
                modifier = Modifier
                    .padding(start = 20.dp, top = 5.dp),
                color = Color(0xFF989898)
            )
            androidx.compose.material.Text(
                text = "${userDetail?.name}",
                modifier = Modifier
                    .padding(end = 20.dp),
                fontSize = 15.sp
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(bottom = 20.dp, top = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            androidx.compose.material.Text(
                text = "Giới tính ",
                modifier = Modifier
                    .padding(start = 20.dp, top = 5.dp),
                color = Color(0xFF989898)
            )
            androidx.compose.material.Text(
                text = userDetail?.gender ?: "Thêm giới tính bắt buộc",
                modifier = Modifier
                    .padding(end = 20.dp)
                    .clickable { showGenderDialog.value = true },  // Mở dialog khi nhấn
                fontSize = 15.sp,
                color = Color.Black
            )
        }
        Divider() // Separator

        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(bottom = 20.dp, top = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            androidx.compose.material.Text(
                text = "Ngày sinh",
                modifier = Modifier
                    .padding(start = 20.dp, top = 5.dp),
                color = Color(0xFF989898)
            )
            androidx.compose.material.Text(
                text = userDetail?.dob ?: "Thêm ngày sinh bắt buộc",
                modifier = Modifier
                    .padding(end = 20.dp)
                    .clickable { showDobDialog.value = true },  // Mở dialog khi nhấn
                fontSize = 15.sp,
                color = Color.Black
            )
        }
        Divider() // Separator

        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(bottom = 20.dp, top = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            androidx.compose.material.Text(
                text = "Địa chỉ",
                modifier = Modifier
                    .padding(start = 20.dp, top = 5.dp),
                color = Color(0xFF989898)
            )
            androidx.compose.material.Text(
                text = userDetail?.address ?: "Thêm địa chỉ bắt buộc",
                modifier = Modifier
                    .padding(end = 20.dp)
                    .clickable { showAddressDialog.value = true },  // Mở dialog khi nhấn
                fontSize = 15.sp,
                color = Color.Black
            )
        }

        // Hiển thị Dialog cho giới tính
        if (showGenderDialog.value) {
            UpdateDialog(
                title = "Cập nhật giới tính",
                initialValue = userDetail?.gender ?: "",
                onDismiss = { showGenderDialog.value = false },
                onConfirm = { updatedValue ->
                    viewModel.updateUserDetails(userId, gender = updatedValue, dob = userDetail?.dob ?: "", address = userDetail?.address ?: "")
                    showGenderDialog.value = false
                }
            )
        }

        // Hiển thị Dialog cho ngày sinh
        if (showDobDialog.value) {
            UpdateDialog(
                title = "Cập nhật ngày sinh",
                initialValue = userDetail?.dob ?: "",
                onDismiss = { showDobDialog.value = false },
                onConfirm = { updatedValue ->
                    viewModel.updateUserDetails(userId, gender = userDetail?.gender ?: "", dob = updatedValue, address = userDetail?.address ?: "")
                    showDobDialog.value = false
                }
            )
        }

        // Hiển thị Dialog cho địa chỉ
        if (showAddressDialog.value) {
            UpdateDialog(
                title = "Cập nhật địa chỉ",
                initialValue = userDetail?.address ?: "",
                onDismiss = { showAddressDialog.value = false },
                onConfirm = { updatedValue ->
                    viewModel.updateUserDetails(userId, gender = userDetail?.gender ?: "", dob = userDetail?.dob ?: "", address = updatedValue)
                    showAddressDialog.value = false
                }
            )
        }
    }
}

@Composable
fun UpdateDialog(
    title: String,
    initialValue: String,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var text by remember { mutableStateOf(initialValue) }

    androidx.compose.material.AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title, fontWeight = FontWeight.Bold) },
        text = {
            androidx.compose.material.OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Nhập giá trị mới") },
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            androidx.compose.material.TextButton(onClick = { onConfirm(text) }) {
                Text("Cập nhật")
            }
        },
        dismissButton = {
            androidx.compose.material.TextButton(onClick = onDismiss) {
                Text("Hủy")
            }
        }
    )
}

@Composable
fun FeetPersonalProfileUSER(navController: NavHostController){
    val viewModel: UserViewModel = viewModel()
    val userDetail by viewModel.user.observeAsState()  // Quan sát LiveData người dùng
    val context = LocalContext.current
    val apiService = RetrofitService()
    val userRepository = LoginRepository(apiService)
    val factory = remember(context) {
        LoginViewModel.LoginViewModelFactory(userRepository, context.applicationContext)
    }
    val loginViewModel: LoginViewModel = viewModel(factory = factory)
    val userId = loginViewModel.getUserData().userId
    LaunchedEffect(Unit) {
        viewModel.getUserDetailById(userId)  // Lấy dữ liệu người dùng khi composable được gọi
    }
    Column {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(bottom = 20.dp, top = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            androidx.compose.material.Text(
                text = "Số điện thoại",
                modifier = Modifier
                    .padding(start = 20.dp, top = 5.dp),
                color = Color(0xFF989898)
            )
            androidx.compose.material.Text(
                text = userDetail?.phoneNumber?:"Thêm thông tin bắt buộc",
                modifier = Modifier
                    .padding(end = 20.dp),
                fontSize = 15.sp
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(start = 20.dp, end = 20.dp)
                .background(color = Color(0xFFe4e4e4))
        ) {
        }
//        _vanphuc : giới tính
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(bottom = 20.dp, top = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            androidx.compose.material.Text(
                text = "Tên đăng nhập",
                modifier = Modifier
                    .padding(start = 20.dp, top = 5.dp),
                color = Color(0xFF989898)
            )
            androidx.compose.material.Text(
                text = userDetail?.username?:"Thêm thông tin bắt buộc",
                modifier = Modifier
                    .padding(end = 20.dp),
                fontSize = 15.sp
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(start = 20.dp, end = 20.dp)
                .background(color = Color(0xFFe4e4e4))
        ) {
        }
//        _vanphuc : ngay sinh
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(bottom = 20.dp, top = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            androidx.compose.material.Text(
                text = "Email",
                modifier = Modifier
                    .padding(start = 20.dp, top = 5.dp),
                color = Color(0xFF989898)
            )
            androidx.compose.material.Text(
                text = userDetail?.email?:"Thêm thông tin bắt buộc",
                modifier = Modifier
                    .padding(end = 20.dp),
                fontSize = 15.sp
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(start = 20.dp, end = 20.dp)
                .background(color = Color(0xFFe4e4e4))
        ) {
        }
//        _vanphuc : dia chi
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(bottom = 20.dp, top = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            androidx.compose.material.Text(
                text = "Mật khẩu",
                modifier = Modifier
                    .padding(start = 20.dp, top = 5.dp),
                color = Color(0xFF989898)
            )
            androidx.compose.material.Text(
                text = "********",
                modifier = Modifier
                    .padding(end = 20.dp),
                fontSize = 15.sp
            )
        }
    }
}