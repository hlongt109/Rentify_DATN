package com.rentify.user.app.view.staffScreens.PersonalProfileScreen.components

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.rentify.user.app.R
import com.rentify.user.app.model.Model.Bank
import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.LoginRepository.LoginRepository
import com.rentify.user.app.viewModel.LoginViewModel
import com.rentify.user.app.viewModel.UserViewmodel.UserViewModel
import java.util.Calendar

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
    var selectedImageUer by remember { mutableStateOf<Uri?>(null) }
    val ImageUserLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        selectedImageUer = uri
    }
    // Trạng thái Dialog
    var showEditDialog by remember { mutableStateOf(false) }
    var currentField by remember { mutableStateOf("") }
    var newValue by remember { mutableStateOf("") }
    var genderValue by remember { mutableStateOf(userDetail?.gender) }
    var dobValue by remember { mutableStateOf(userDetail?.dob) }

    // Lấy thông tin người dùng khi composable được gọi
    LaunchedEffect(Unit) {
        viewModel.getUserDetailById(userId)
    }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Kiểm tra ảnh người dùng đã chọn và ảnh từ API
            val imageUrl = selectedImageUer ?: userDetail?.profile_picture_url?.let {
                "http://10.0.2.2:3000/$it"
            } ?: R.drawable.anhdaidien

            // Hiển thị ảnh đại diện
            if (selectedImageUer != null || userDetail?.profile_picture_url != null) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .height(50.dp)
                        .width(60.dp)
                        .padding(start = 10.dp)
                        .clip(CircleShape)  // Thay đổi thành CircleShape để hiển thị ảnh tròn
                        .clickable {
                            ImageUserLauncher.launch("image/*") // Mở bộ chọn ảnh
                        },
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    painter = rememberImagePainter(imageUrl),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .height(50.dp)
                        .width(60.dp)
                        .padding(start = 10.dp)
                        .clip(CircleShape)  // Thay đổi thành CircleShape để hiển thị ảnh tròn
                        .clickable {
                            ImageUserLauncher.launch("image/*")
                        }
                )
            }

            // Nút lưu ảnh
            androidx.compose.material3.Text(
                text = "Lưu ảnh",
                modifier = Modifier
                    .padding(start = 20.dp, top = 20.dp, end = 20.dp)
                    .clickable {
                        selectedImageUer?.let { imageUri ->
                            viewModel.updateProfilePicture(userId, imageUri, context)
                        }
                        Toast.makeText(
                            context,
                            "Cập nhật ảnh đại diện thành công",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                color = Color.Black
            )
        }

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
                genderValue = userDetail?.gender ?: ""
                showEditDialog = true
            }
        )

        // Ngày sinh
        EditableRow(
            label = "Ngày sinh",
            value = userDetail?.dob ?: "",
            onClick = {
                currentField = "dob"
                dobValue = userDetail?.dob ?: ""
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
            when (currentField) {
                "gender" -> GenderDialog(
                    gender = genderValue ?: "",
                    onGenderChange = { genderValue = it },
                    onSave = {
                        viewModel.updateTaiKhoan(userId,
                            genderValue?.let { userDetail?.copy(gender = it) })
                        showEditDialog = false
                    },
                    onCancel = { showEditDialog = false }
                )
                "dob" -> DatePickerDialog(
                    dob = dobValue ?: "",
                    onDateChange = { dobValue = it },
                    onSave = {
                        viewModel.updateTaiKhoan(userId,
                            dobValue?.let { userDetail?.copy(dob = it) })
                        showEditDialog = false
                    },
                    onCancel = { showEditDialog = false }
                )
                else -> EditDialog(
                    title = "Chỉnh sửa $currentField",
                    value = newValue,
                    onValueChange = { newValue = it },
                    onSave = {
                        showEditDialog = false
                        when (currentField) {
                            "name" -> viewModel.updateTaiKhoan(userId, userDetail?.copy(name = newValue))
                            "address" -> viewModel.updateTaiKhoan(userId, userDetail?.copy(address = newValue))
                        }
                    },
                    onCancel = { showEditDialog = false }
                )
            }
        }
    }
}

@Composable
fun GenderDialog(gender: String, onGenderChange: (String) -> Unit, onSave: () -> Unit, onCancel: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onCancel() },
        title = { Text("Chỉnh sửa Giới tính", fontWeight = FontWeight.Bold) },
        text = {
            Column {
                Row {
                    RadioButton(
                        selected = gender == "Male",
                        onClick = { onGenderChange("Male") }
                    )
                    Text("Nam", modifier = Modifier.padding(start = 8.dp, top = 13.dp))
                }
                Row {
                    RadioButton(
                        selected = gender == "Female",
                        onClick = { onGenderChange("Female") }
                    )
                    Text("Nữ", modifier = Modifier.padding(start = 8.dp,top = 13.dp))
                }
                Row {
                    RadioButton(
                        selected = gender == "Other",
                        onClick = { onGenderChange("Other") }
                    )
                    Text("Khác", modifier = Modifier.padding(start = 8.dp,top = 13.dp))
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { onSave() }) {
                Text("Lưu")
            }
        },
        dismissButton = {
            TextButton(onClick = { onCancel() }) {
                Text("Hủy")
            }
        }
    )
}

@Composable
fun DatePickerDialog(dob: String, onDateChange: (String) -> Unit, onSave: () -> Unit, onCancel: () -> Unit) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    // Trạng thái lưu ngày được chọn
    var selectedDate by remember { mutableStateOf(dob) }

    val datePickerDialog = android.app.DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            selectedDate = "$dayOfMonth/${month + 1}/$year" // Cập nhật ngày đã chọn
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    AlertDialog(
        onDismissRequest = { onCancel() },
        title = { Text("Chỉnh sửa Ngày sinh", fontWeight = FontWeight.Bold) },
        text = {
            Column {
                Text(text = "Ngày đã chọn: $selectedDate", fontSize = 16.sp)
            }
        },
        confirmButton = {
            Row {
                TextButton(onClick = { datePickerDialog.show() }) {
                    Text("Chọn ngày")
                }
                Spacer(modifier = Modifier.width(8.dp)) // Khoảng cách giữa 2 nút
                TextButton(onClick = {
                    onDateChange(selectedDate) // Gửi ngày đã chọn
                    onSave() // Lưu thay đổi
                }) {
                    Text("OK")
                }
            }
        },
        dismissButton = {
            TextButton(onClick = { onCancel() }) {
                Text("Hủy")
            }
        }
    )
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
        title = { Text(title, fontWeight = FontWeight.Bold) },
        text = {
            Column {
                OutlinedTextField(
                    value = value,
                    onValueChange = onValueChange,
                    label = { Text("Nhập giá trị mới") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
        },
        confirmButton = {
            TextButton(onClick = { onSave() }) {
                Text("Lưu")
            }
        },
        dismissButton = {
            TextButton(onClick = { onCancel() }) {
                Text("Hủy")
            }
        }
    )
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
        }
    }
}



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

@Composable
fun FeetPersonalProfile2(navController: NavHostController) {
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

    var showEditDialog by remember { mutableStateOf(false) }
    var currentField by remember { mutableStateOf("") }
    var newValue by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val pickImageLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        selectedImageUri = uri
    }

    LaunchedEffect(Unit) {
        viewModel.getBankAccountByUser(userId)
    }

    Column {
        EditableRow(
            label = "Tên ngân hàng",
            value = bankAccountDetail?.bank_name ?: "",
            onClick = {
                currentField = "bank_name"
                newValue = bankAccountDetail?.bank_name ?: ""
                showEditDialog = true
            }
        )

        EditableRow(
            label = "Số tài khoản",
            value = bankAccountDetail?.bank_number.toString(),
            onClick = {
                currentField = "bank_number"
                newValue = bankAccountDetail?.bank_number.toString()
                showEditDialog = true
            }
        )

        EditableRow(
            label = "Tên tài khoản ngân hàng",
            value = bankAccountDetail?.username ?: "",
            onClick = {
                currentField = "username"
                newValue = bankAccountDetail?.username ?: ""
                showEditDialog = true
            }
        )

        // Hiển thị ảnh QR Code
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            if (selectedImageUri != null) {
                item {
                    Image(
                        painter = rememberAsyncImagePainter(selectedImageUri),
                        contentDescription = "Ảnh mới chọn",
                        modifier = Modifier
                            .width(250.dp)
                            .height(250.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color.LightGray)
                            .clickable { pickImageLauncher.launch("image/*") },
                        contentScale = ContentScale.Crop
                    )
                }
            } else if (bankAccountDetail?.qr_bank.isNullOrEmpty()) {
                // Nếu danh sách qr_bank rỗng, hiển thị Box thêm ảnh
                item {
                    Box(
                        modifier = Modifier
                            .width(250.dp)
                            .height(250.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFFE0E0E0)) // Màu nền đẹp
                            .clickable { pickImageLauncher.launch("image/*") },
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.image1), // Icon thêm ảnh
                                contentDescription = "Thêm ảnh",
                                tint = Color.Gray,
                                modifier = Modifier.size(50.dp)
                            )
                            Text(
                                text = "Thêm ảnh",
                                color = Color.Gray,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    }
                }
            } else {
                // Nếu danh sách qr_bank không rỗng, hiển thị ảnh QR
                items(bankAccountDetail?.qr_bank!!) { qrUrl ->
                    val qrImageUri = "http://10.0.2.2:3000/$qrUrl"
                    AsyncImage(
                        model = qrImageUri,
                        contentDescription = "QR Bank Photo",
                        modifier = Modifier
                            .width(250.dp)
                            .height(250.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color.LightGray)
                            .clickable { pickImageLauncher.launch("image/*") },
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }


        // Nút lưu ảnh mới
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.save),
                contentDescription = "Cập nhật hình ảnh",
                modifier = Modifier
                    .size(30.dp)
                    .clickable {
                        selectedImageUri?.let { uri ->
                            val updatedBank = Bank(
                                bank_name = bankAccountDetail?.bank_name ?: "Chưa xác định",
                                bank_number = bankAccountDetail?.bank_number ?: 0L,
                                qr_bank = bankAccountDetail?.qr_bank ?: emptyList(),
                                username = bankAccountDetail?.username ?: "Chưa xác định"
                            )
                            viewModel.updateBankAccountWithImage(userId, updatedBank, uri, context)
                        }
                        Toast.makeText(context,"Đổi ảnh mới thành công ",Toast.LENGTH_SHORT).show()
                    }
            )
        }

        Spacer(modifier = Modifier.height(70.dp))

        // Dialog chỉnh sửa thông tin
        if (showEditDialog) {
            EditDialog(
                title = "Chỉnh sửa $currentField",
                value = newValue,
                onValueChange = { newValue = it },
                onSave = {
                    showEditDialog = false
                    val updatedBankAccount = when (currentField) {
                        "bank_name" -> bankAccountDetail?.copy(
                            bank_name = newValue.ifEmpty { "Chưa xác định" }
                        ) ?: Bank(
                            bank_name = newValue.ifEmpty { "Chưa xác định" },
                            bank_number = bankAccountDetail?.bank_number ?: 0L,
                            qr_bank = bankAccountDetail?.qr_bank ?: emptyList(),
                            username = bankAccountDetail?.username ?: "Chưa xác định"
                        )
                        "bank_number" -> bankAccountDetail?.copy(
                            bank_number = newValue.toLongOrNull() ?: 0L
                        ) ?: Bank(
                            bank_name = bankAccountDetail?.bank_name ?: "Chưa xác định",
                            bank_number = newValue.toLongOrNull() ?: 0L,
                            qr_bank = bankAccountDetail?.qr_bank ?: emptyList(),
                            username = bankAccountDetail?.username ?: "Chưa xác định"
                        )
                        "username" -> bankAccountDetail?.copy(
                            username = newValue.ifEmpty { "Chưa xác định" }
                        ) ?: Bank(
                            bank_name = bankAccountDetail?.bank_name ?: "Chưa xác định",
                            bank_number = bankAccountDetail?.bank_number ?: 0L,
                            qr_bank = bankAccountDetail?.qr_bank ?: emptyList(),
                            username = newValue.ifEmpty { "Chưa xác định" }
                        )
                        else -> bankAccountDetail
                    }
                    updatedBankAccount?.let {
                        viewModel.updateBankAccount(userId, it)
                    }
                },
                onCancel = { showEditDialog = false }
            )
        }
    }
}
