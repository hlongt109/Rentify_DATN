package com.rentify.user.app.view.userScreens.profileScreen.components

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.RadioButton
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import coil.compose.rememberImagePainter
import com.rentify.user.app.R
import com.rentify.user.app.model.Model.Bank
import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.LoginRepository.LoginRepository
import com.rentify.user.app.view.staffScreens.PersonalProfileScreen.components.EditDialog
import com.rentify.user.app.view.staffScreens.PersonalProfileScreen.components.EditableRow
import com.rentify.user.app.view.userScreens.roomdetailScreen.components.DatePickerDialog
import com.rentify.user.app.viewModel.LoginViewModel
import com.rentify.user.app.viewModel.UserViewmodel.UserViewModel

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewFeetPersonalProfile() {
    FeetPersonalProfileuser(navController = rememberNavController())
    FeetPersonalProfileUSER(navController = rememberNavController())
}

@Composable
fun FeetPersonalProfileuser(navController: NavHostController) {
    val viewModel: UserViewModel = viewModel()
    val userDetail by viewModel.userAcc.observeAsState()
    val context = LocalContext.current
    val apiService = RetrofitService()
    val userRepository = LoginRepository(apiService)
    val factory = remember(context) {
        LoginViewModel.LoginViewModelFactory(userRepository, context.applicationContext)
    }
    val loginViewModel: LoginViewModel = viewModel(factory = factory)
    val userId = loginViewModel.getUserData().userId

    val showGenderDialog = remember { mutableStateOf(false) }
    val showDobDialog = remember { mutableStateOf(false) }
    val showAddressDialog = remember { mutableStateOf(false) }
    var showNameDialog = remember { mutableStateOf(false) }
    var newValue by remember { mutableStateOf("") }
    var currentField by remember { mutableStateOf("") }
    var selectedImageUer by remember { mutableStateOf<Uri?>(null) }
    val ImageUserLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        selectedImageUer = uri
    }
    LaunchedEffect(Unit) {
        viewModel.getUserAccById(userId)
    }
    LaunchedEffect(Unit) {
        viewModel.getProfilePictureById(userId)
    }

    Column {
        // ảnh đại diện
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
            Text(
                text = "Lưu ảnh",
                modifier = Modifier
                    .padding(start = 20.dp, top = 20.dp, end = 20.dp)
                    .clickable {
                        selectedImageUer?.let { imageUri ->
                            viewModel.updateProfilePicture(userId, imageUri, context)
                        }
                        Toast.makeText(context, "Cập nhật ảnh đại diện thành công", Toast.LENGTH_SHORT).show()
                    },
                color = Color.Black
            )
        }

        // Họ và tên
        EditableRow(
            label = "Họ và tên",
            value = userDetail?.name ?: "Không có",
            onClick = {
                currentField = "name"
                newValue = userDetail?.name ?: ""
                showNameDialog.value = true
                Log.d("Debug", "showNameDialog.value: ${showNameDialog.value}")
            }
        )

        if (showNameDialog.value) {
            EditDialog(
                title = "Chỉnh sửa $currentField",
                value = newValue,
                onValueChange = { newValue = it },
                onSave = {
                    showNameDialog.value = false
                    viewModel.updateUserAccount(userId, userDetail?.copy(name = newValue))
                },
                onCancel = { showNameDialog.value = false }
            )
        }

        // Giới tính
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp, top = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Giới tính",
                modifier = Modifier.padding(start = 20.dp, top = 5.dp),
                color = Color(0xFF989898)
            )
            Row(
                modifier = Modifier
                    .padding(end = 20.dp)
                    .clickable { showGenderDialog.value = true }
            ) {
                Text(
                    text = userDetail?.gender ?: "",
                    modifier = Modifier.padding(end = 20.dp),
                    fontSize = 15.sp,
                    color = Color.Black
                )
            }
        }

        Divider()

        // Ngày sinh
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp, top = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Ngày sinh",
                modifier = Modifier.padding(start = 20.dp, top = 5.dp),
                color = Color(0xFF989898)
            )
            Text(
                text = userDetail?.dob ?: "",
                modifier = Modifier
                    .padding(end = 20.dp)
                    .clickable { showDobDialog.value = true },
                fontSize = 15.sp,
                color = Color.Black
            )
        }
        Divider()

        // Địa chỉ
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp, top = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Địa chỉ",
                modifier = Modifier.padding(start = 20.dp, top = 5.dp),
                color = Color(0xFF989898)
            )
            Text(
                text = userDetail?.address ?: "",
                modifier = Modifier
                    .padding(end = 20.dp)
                    .clickable { showAddressDialog.value = true },
                fontSize = 15.sp,
                color = Color.Black
            )
        }
        // Hiển thị dialog cập nhật địa chỉ
        if (showAddressDialog.value) {
            UpdateDialog(
                title = "Cập nhật địa chỉ",
                initialValue = userDetail?.address ?: "",
                onDismiss = { showAddressDialog.value = false },
                onConfirm = { updatedValue ->
                    viewModel.updateUserDetails(
                        userId,
                        gender = userDetail?.gender ?: "",
                        dob = userDetail?.dob ?: "",
                        address = updatedValue,
                    )
                    showAddressDialog.value = false
                }
            )
        }

        // Hiển thị lịch chọn ngày sinh
        if (showDobDialog.value) {
            DatePickerDialog(
                onDismissRequest = { showDobDialog.value = false },
                onDateSelected = { selectedDate ->
                    viewModel.updateUserDetails(
                        userId,
                        gender = userDetail?.gender ?: "",
                        dob = selectedDate,
                        address = userDetail?.address ?: "",
                    )
                    showDobDialog.value = false
                }
            )
        }

        // Hiển thị hộp thoại giới tính
        if (showGenderDialog.value) {
            GenderDialog(
                currentGender = userDetail?.gender ?: "",
                onDismiss = { showGenderDialog.value = false },
                onConfirm = { updatedGender ->
                    viewModel.updateUserDetails(
                        userId,
                        gender = updatedGender,
                        dob = userDetail?.dob ?: "",
                        address = userDetail?.address ?: "",
                    )
                    showGenderDialog.value = false
                }
            )
        }
    }
}

@Composable
fun GenderDialog(
    currentGender: String,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit,
) {
    val genders = listOf("Nam", "Nữ")
    var selectedGender by remember { mutableStateOf(currentGender) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Cập nhật giới tính", fontWeight = FontWeight.Bold) },
        text = {
            Column {
                genders.forEach { gender ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedGender = gender },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (gender == selectedGender),
                            onClick = { selectedGender = gender }
                        )
                        Text(text = gender, modifier = Modifier.padding(start = 8.dp))
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(selectedGender) }) {
                Text("Cập nhật")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Hủy")
            }
        }
    )
}

@Composable
fun UpdateDialog(
    title: String,
    initialValue: String,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit,
) {
    var text by remember { mutableStateOf(initialValue) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title, fontWeight = FontWeight.Bold) },
        text = {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Nhập địa chỉ mới") },
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(text) }) {
                Text("Cập nhật")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Hủy")
            }
        }
    )
}


@Composable
fun FeetPersonalProfileUSER(
    navController: NavHostController,

    ) {
    val viewModel: UserViewModel = viewModel()
    val userDetail by viewModel.userAcc.observeAsState() // Quan sát LiveData người dùng
    val context = LocalContext.current
    val apiService = RetrofitService()
    val userRepository = LoginRepository(apiService)
    val factory = remember(context) {
        LoginViewModel.LoginViewModelFactory(userRepository, context.applicationContext)
    }
    val loginViewModel: LoginViewModel = viewModel(factory = factory)
    val userId = loginViewModel.getUserData().userId
    val userData = loginViewModel.getUserData()
    // Trạng thái Dialog
    var showEditDialog by remember { mutableStateOf(false) }
    var currentField by remember { mutableStateOf("") }
    var newValue by remember { mutableStateOf("") }
    Log.d("Debug", "userDetail: $userDetail")

    // Lấy thông tin người dùng khi composable được gọi
    LaunchedEffect(Unit) {
        viewModel.getUserAccById(userId)
    }

    Column {
        // Số điện thoại
        EditableRow(
            label = "Số điện thoại",
            value = userDetail?.phoneNumber ?: "Không có",
            onClick = {
                currentField = "phoneNumber"
                newValue = userDetail?.phoneNumber ?: ""
                showEditDialog = true
            }
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
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
                text = userDetail?.username ?: "Không có",
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
            modifier = Modifier
                .fillMaxWidth()
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
                text = "${userDetail?.email}",
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
            modifier = Modifier
                .fillMaxWidth()
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
        // Hiển thị Dialog chỉnh sửa
        if (showEditDialog) {
            EditDialog(
                title = "Chỉnh sửa $currentField",
                value = newValue,
                onValueChange = { newValue = it },
                onSave = {
                    showEditDialog = false
                    when (currentField) {
                        "phoneNumber" -> viewModel.updateUserAccount(
                            userId,
                            userDetail?.copy(phoneNumber = newValue)
                        )

                        "username" -> viewModel.updateUserAccount(
                            userId,
                            userDetail?.copy(username = newValue)
                        )
//                        "email" -> viewModel.updateUserAccount(userId, userDetail?.copy(email = newValue))
//                        "password" -> viewModel.updateUserAccount(userId, userDetail?.copy(password = newValue)) // Đảm bảo mã hóa mật khẩu nếu cần
                    }
                },
                onCancel = { showEditDialog = false }
            )
        }
    }
}