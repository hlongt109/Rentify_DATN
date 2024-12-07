package com.rentify.user.app.view.staffScreens.PersonalProfileScreen.components

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.LoginRepository.LoginRepository
import com.rentify.user.app.viewModel.LoginViewModel
import com.rentify.user.app.viewModel.UserViewmodel.UserViewModel

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewFeetPersonalProfile(){
    FeetPersonalProfile(navController= rememberNavController())
    FeetPersonalProfile1(navController= rememberNavController())
    FeetPersonalProfile2(navController= rememberNavController())
}
@Composable
fun FeetPersonalProfile(navController: NavHostController) {
    val viewModel: UserViewModel = viewModel()
    val userDetail by viewModel.user.observeAsState() // Quan sát LiveData người dùng
    val context = LocalContext.current
    val apiService = RetrofitService()
    val userRepository = LoginRepository(apiService)
    val factory = remember(context) {
        LoginViewModel.LoginViewModelFactory(userRepository, context.applicationContext)
    }
    val loginViewModel: LoginViewModel = viewModel(factory = factory)
    val userId = loginViewModel.getUserData().userId

    // Trạng thái Dialog
    var showEditDialog by remember { mutableStateOf(false) }
    var currentField by remember { mutableStateOf("") }
    var newValue by remember { mutableStateOf("") }

    // Lấy thông tin người dùng khi composable được gọi
    LaunchedEffect(Unit) {
        viewModel.getUserDetailById(userId)
    }

    Column {
        // Họ và tên
        EditableRow(
            label = "Họ và tên",
            value = userDetail?.name ?: "",
            onClick = {
                currentField = "name"
                newValue = userDetail?.name ?: ""
                showEditDialog = true
            }
        )

        // Giới tính
        EditableRow(
            label = "Giới tính",
            value = userDetail?.gender ?: "",
            onClick = {
                currentField = "gender"
                newValue = userDetail?.gender ?: ""
                showEditDialog = true
            }
        )

        // Ngày sinh
        EditableRow(
            label = "Ngày sinh",
            value = userDetail?.dob ?: "",
            onClick = {
                currentField = "dob"
                newValue = userDetail?.dob ?: ""
                showEditDialog = true
            }
        )
        EditableRow(
            label = "Địa chỉ",
            value = userDetail?.address ?: "",
            onClick = {
                currentField = "address"
                newValue = userDetail?.address ?: ""
                showEditDialog = true
            }
        )

        // Hiển thị Dialog chỉnh sửa
        if (showEditDialog) {
            EditDialog(
                title = "Chỉnh sửa $currentField",
                value = newValue,
                onValueChange = { newValue = it },
                onSave = {
                    showEditDialog = false
                    when (currentField) {
                        "name" -> viewModel.updateTaiKhoan(userId, userDetail?.copy(name = newValue))
                        "gender" -> viewModel.updateTaiKhoan(userId, userDetail?.copy(gender = newValue))
                        "dob" -> viewModel.updateTaiKhoan(userId, userDetail?.copy(dob = newValue))
                    }
                },
                onCancel = { showEditDialog = false }
            )
        }
    }
}

@Composable
fun EditableRow(label: String, value: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp, top = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            modifier = Modifier
                .padding(start = 20.dp, top = 5.dp),
            color = Color(0xFF989898)
        )
        Row(
            modifier = Modifier.clickable { onClick() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = value,
                modifier = Modifier.padding(end = 20.dp),
                fontSize = 15.sp
            )
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit $label",
                modifier = Modifier.size(16.dp),
                tint = Color.Gray
            )
        }
    }
}

@Composable
fun EditDialog(
    title: String,
    value: String,
    onValueChange: (String) -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onCancel() },
        title = { Text(title) },
        text = {
            Column {
                TextField(
                    value = value,
                    onValueChange = onValueChange,
                    label = { Text("Nhập giá trị mới") }
                )
            }
        },
        confirmButton = {
            Button(onClick = { onSave() }) {
                Text("Lưu")
            }
        },
        dismissButton = {
            Button(onClick = { onCancel() }) {
                Text("Hủy")
            }
        }
    )
}

//============1============
@Composable
fun FeetPersonalProfile1(navController: NavHostController) {
    val viewModel: UserViewModel = viewModel()
    val userDetail by viewModel.user.observeAsState() // Quan sát LiveData người dùng
    val context = LocalContext.current
    val apiService = RetrofitService()
    val userRepository = LoginRepository(apiService)
    val factory = remember(context) {
        LoginViewModel.LoginViewModelFactory(userRepository, context.applicationContext)
    }
    val loginViewModel: LoginViewModel = viewModel(factory = factory)
    val userId = loginViewModel.getUserData().userId

    // Trạng thái Dialog
    var showEditDialog by remember { mutableStateOf(false) }
    var currentField by remember { mutableStateOf("") }
    var newValue by remember { mutableStateOf("") }

    // Lấy thông tin người dùng khi composable được gọi
    LaunchedEffect(Unit) {
        viewModel.getUserDetailById(userId)
    }

    Column {
        // Số điện thoại
        EditableRow(
            label = "Số điện thoại",
            value = userDetail?.phoneNumber ?: "",
            onClick = {
                currentField = "phoneNumber"
                newValue = userDetail?.phoneNumber ?: ""
                showEditDialog = true
            }
        )
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(bottom = 20.dp, top = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Tên đăng nhập",
                modifier = Modifier
                    .padding(start = 20.dp, top = 5.dp),
                color = Color(0xFF989898)
            )
            Text(text = "${userDetail?.username}",
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
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(bottom = 20.dp, top = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Email",
                modifier = Modifier
                    .padding(start = 20.dp, top = 5.dp),
                color = Color(0xFF989898)
            )
            Text(text = "${userDetail?.email}",
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
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(bottom = 20.dp, top = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Mật khẩu",
                modifier = Modifier
                    .padding(start = 20.dp, top = 5.dp),
                color = Color(0xFF989898)
            )
            Text(text = "********",
                modifier = Modifier
                    .padding(end = 20.dp),
                fontSize = 15.sp
            )
        }
        // Hiển thị Dialog chỉnh sửa
        if (showEditDialog) {
            EditDialog(
                title = "Chỉnh sửa $currentField",
                value = newValue,
                onValueChange = { newValue = it },
                onSave = {
                    showEditDialog = false
                    when (currentField) {
                        "phoneNumber" -> viewModel.updateTaiKhoan(userId, userDetail?.copy(phoneNumber = newValue))
                        "username" -> viewModel.updateTaiKhoan(userId, userDetail?.copy(username = newValue))
                        "email" -> viewModel.updateTaiKhoan(userId, userDetail?.copy(email = newValue))
                        "password" -> viewModel.updateTaiKhoan(userId, userDetail?.copy(password = newValue)) // Đảm bảo mã hóa mật khẩu nếu cần
                    }
                },
                onCancel = { showEditDialog = false }
            )
        }
    }
}

//================================
@Composable
fun FeetPersonalProfile2(navController: NavHostController){
    val viewModel: UserViewModel = viewModel()
    val bankAccountDetail by viewModel.bankAccount.observeAsState()
    val context = LocalContext.current
    val apiService = RetrofitService()
    val userRepository = LoginRepository(apiService)
    val factory = remember(context) {
        LoginViewModel.LoginViewModelFactory(userRepository, context.applicationContext)
    }
    val loginViewModel: LoginViewModel = viewModel(factory = factory)
    val userId = loginViewModel.getUserData().userId
    LaunchedEffect(Unit) {
        viewModel.getBankAccountByUser(userId)  // Lấy dữ liệu người dùng khi composable được gọi
    }
    Column {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(bottom = 20.dp, top = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Tên ngân hàng",
                modifier = Modifier
                    .padding(start = 20.dp, top = 5.dp),
                color = Color(0xFF989898)
            )
            Text(text = "${bankAccountDetail?.bank_name}",
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
            Text(text = "Số tài khoản",
                modifier = Modifier
                    .padding(start = 20.dp, top = 5.dp),
                color = Color(0xFF989898)
            )
            Text(text = "${bankAccountDetail?.bank_number}",
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
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(bottom = 20.dp, top = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Tên tài khoản ngân hàng",
                modifier = Modifier
                    .padding(start = 20.dp, top = 5.dp),
                color = Color(0xFF989898)
            )
            Text(text = "${bankAccountDetail?.username}",
                modifier = Modifier
                    .padding(end = 20.dp),
                fontSize = 15.sp
            )
        }
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.Center // Căn giữa các item trong LazyRow
        ) {
            items(bankAccountDetail?.qr_bank ?: emptyList()) { qrUrl ->
                val urianhBank: String = "http://10.0.2.2:3000/${qrUrl}"
                AsyncImage(
                    model = urianhBank,
                    contentDescription = "QR Bank Photo",
                    modifier = Modifier
                        .width(250.dp)
                        .height(250.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.LightGray)
                        .align(Alignment.CenterHorizontally), // Căn giữa ảnh
                    contentScale = ContentScale.Crop
                )
            }
        }
        Spacer(modifier = Modifier.height(50.dp))
    }
}