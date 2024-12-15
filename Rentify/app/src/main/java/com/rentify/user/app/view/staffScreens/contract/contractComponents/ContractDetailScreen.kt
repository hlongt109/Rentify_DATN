package com.rentify.user.app.view.staffScreens.contract.contractComponents

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.gson.Gson
import com.rentify.user.app.R
import com.rentify.user.app.model.Model.NotificationRequest
import com.rentify.user.app.model.User
import com.rentify.user.app.view.staffScreens.UpdatePostScreen.Components.CustomTextField
import com.rentify.user.app.view.staffScreens.UpdatePostScreen.isFieldEmpty
import com.rentify.user.app.view.staffScreens.UpdatePostScreen.prepareMultipartBody
import com.rentify.user.app.view.staffScreens.addContractScreen.UserItem
import com.rentify.user.app.view.staffScreens.addContractScreen.validateUserIds
import com.rentify.user.app.view.userScreens.cancelContract.components.ContractInfoRow
import com.rentify.user.app.view.userScreens.cancelContract.components.CustomButton
import com.rentify.user.app.view.userScreens.cancelContract.components.HeaderSection
import com.rentify.user.app.view.userScreens.contract.components.ViewContractButton
import com.rentify.user.app.viewModel.NotificationViewModel
import com.rentify.user.app.viewModel.StaffViewModel.ContractViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ContractDetailScreen(
    navController: NavController,
    contractId: String,
    notificationViewModel: NotificationViewModel = viewModel()
) {
    var context = LocalContext.current
    val contractViewModel: ContractViewModel = viewModel()
    val contractDetail by contractViewModel.contractDetail.observeAsState()
    var showDialog by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val onDismissDialog: () -> Unit = {
        showDialog = false
    }
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
                AppointmentAppBarc(
                    onBackClick = {
                        // Logic quay lại, ví dụ: điều hướng về màn hình trước
                        navController.navigate("CONTRACT_STAFF")
                        {
                            //    popUpTo("ADDCONTRAC_STAFF") { inclusive = true }

                        }
                    })
             //   HeaderSection(backgroundColor = Color.White, title = "Xem hợp đồng", navController = navController)
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
                            ContractInfoRow("Tiền cọc", "${ contract.room_id?.price}",isImportant = true) // Màu sắc khác cho tiền cọc
                            ContractInfoRow("Tiền thuê", "${ contract.room_id?.price} VND / tháng", isImportant = true) // Màu sắc khác cho tiền cọc
                            ContractInfoRow("Kỳ thanh toán", "${contract.paymentCycle} hàng tháng" )
                        }}
                    Spacer(modifier = Modifier
                        .background(color = Color(0xFFeeeeee))
                        .fillMaxWidth()
                        .height(10.dp))
                    Box(
                        modifier = Modifier
                            .padding(15.dp)
                            .fillMaxWidth()){
                        Column {
                            CustomButton(
                                onClick = {
                                    navController.navigate("contract_image_detail/${contract._id}")

                                },
                                backgroundColor = Color(0xFFFFFFFF), // Nền trắng
                                imageRes = R.drawable.clipboard,
                                buttonText = "Xem văn bản hợp đồng",
                                textColor = Color(0xFF0066CC), // Chữ xanh dương
                                borderWidth = 1.dp, // Độ rộng viền
                                borderColor = Color(0xFF0066CC), // Màu viền xanh dương
                                modifier = Modifier
                                    .height(50.dp)
                                    .shadow(2.dp, RoundedCornerShape(10.dp))
                            )
                            Spacer(modifier = Modifier
                                .background(color = Color(0xFFeeeeee))
                                .fillMaxWidth()
                                .height(10.dp))
                           CustomButton(
                                        onClick = {  showDialog = true },
                                        backgroundColor = Color(0xFF4CAF50),
                                        buttonText = "Sửa hợp đồng",
                                        textColor = Color.White,
                                        borderWidth = 0.dp, // Độ rộng viền
                                        borderColor = Color(0xFF0066CC),
                                        modifier = Modifier
                                            .height(50.dp)
                                            .shadow(2.dp, RoundedCornerShape(10.dp))
                                    )
                        }
                        if (showDialog) {
                            EditContractDialog(
                                contractId="${contract._id}",
                                onDismiss = onDismissDialog,
                                snackbarHostState = snackbarHostState // Truyền snackbarHostState vào dialog
                            )
                        }

                    }
                }
            }

        }
    }?: run{
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White), // Màu nền nếu cần
            contentAlignment = Alignment.Center // Căn giữa nội dung
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(50.dp), // Kích thước loading
                color = Color(0xFF5DADFF), // Màu sắc hiệu ứng
                strokeWidth = 4.dp // Độ dày của đường loading
            )
        }
    }}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditContractDialog(
    contractId: String?,
    onDismiss: () -> Unit,
    snackbarHostState: SnackbarHostState,
    notificationViewModel: NotificationViewModel = viewModel()
) {
    val context = LocalContext.current
    var userId by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var selectedPhotos by remember { mutableStateOf(emptyList<Uri>()) }
    val contractViewModel: ContractViewModel = viewModel()
    val contractDetail by contractViewModel.contractDetail.observeAsState()
    val updateStatus by contractViewModel.error.observeAsState() // Theo dõi trạng thái lỗi từ ViewModel
    var isEdited by remember { mutableStateOf(false) }
    var userList by remember { mutableStateOf<List<User>>(emptyList()) }
    val errorMessage by contractViewModel.error.observeAsState("")
    var isFetching by remember { mutableStateOf(false) }
    val userDetail by contractViewModel.userDetail.collectAsState()
    val userIds = userList.joinToString(",") { it._id }
    val scrollState = rememberScrollState()
    // Lấy chi tiết hợp đồng khi hợp đồng ID thay đổi
    LaunchedEffect(contractId) {
        contractId?.let {
            contractViewModel.fetchContractDetail(it)
        }
    }

    // Fetch user details when contractDetail is updated
    LaunchedEffect(contractDetail) {
        contractDetail?.user_id?.forEach { user ->
            contractViewModel.fetchUserById(user._id)
            Log.d("LaunchedEffect", "Fetching user with ID: ${user._id}")
        }
    }
    LaunchedEffect(userDetail) {
        userDetail?.let { user ->
            isFetching = false
            // Chỉ thêm người dùng nếu chưa có trong danh sách
            if (!userList.any { it._id == user._id }) {
                userList = userList + user
            }
        }
    }
    LaunchedEffect(contractDetail) {
        contractDetail?.user_id?.let { userIds ->
            // Khởi tạo danh sách người dùng từ user_id trong contractDetail
            userList = userIds.map { user ->
                User(
                    _id = user._id,
                    name = user.name ,// Thêm các thuộc tính khác nếu cần
                            username = "",
                    password = "",
                    email = "",
                    phoneNumber = "",
                    role = "",
                    dob = "",
                    gender = "",
                    address = "",
                    profile_picture_url = "",
                    verified = true,
                    landlord_id = "",
                    createdAt = "",
                    updatedAt = ""
                )
            }
        }
    }


    contractDetail?.let { contract ->
        if (!isEdited) {
         //   userId = contract.user_id?.joinToString { it._id } ?: "" // Ghép ID người dùng
            content = contract.content
        }
    }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = Color(0xfff7f7f7)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(scrollState)
            ) {
                Text(
                    text = "Sửa hợp đồng",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Spacer(modifier = Modifier.padding(5.dp))
                Row(horizontalArrangement = Arrangement.Center) {
                    CustomTextField(
                        label = "Nhập userId",
                        value = userId,
                        onValueChange = { userId = it  },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(8f) // Chiếm 8 phần
                            .padding(horizontal = 10.dp),
                        placeholder = "Nhập userId người dùng",
                        isReadOnly = false
                    )
                    Button(
                        onClick = {
                            // Kiểm tra nếu userId đã có trong danh sách
                            if (userList.any { it._id == userId }) {
                                // Hiển thị thông báo nếu userId đã có
                                Toast.makeText(context, "Người dùng đã có trong danh sách", Toast.LENGTH_SHORT).show()
                                return@Button
                            }
                            if (userId.length != 24) {
                                Toast.makeText(context, "UserId phải có đúng 24 ký tự", Toast.LENGTH_SHORT).show()
                                return@Button
                            }
                            // Show error message if any
                            CoroutineScope(Dispatchers.Main).launch {
                                val result = contractViewModel.fetchUserByIdSuspend(userId)
                                result.onSuccess { user ->
                                    // Xử lý thành công: Thêm người dùng vào danh sách
                                    if (user != null) {
                                        userList = userList + user
                                        Toast.makeText(context, "Người dùng đã được thêm thành công!", Toast.LENGTH_SHORT).show()
                                        return@launch
                                    }
                                }.onFailure { error ->
                                    // Hiển thị lỗi qua Toast
                                    Toast.makeText(context,"Người dùng không tồn tại, hãy kiểm tra lại ", Toast.LENGTH_SHORT).show()
                                    isFetching = false
                                    return@launch
                                }}
                            // Nếu chưa có, thực hiện gọi API
                            isFetching = true
                            contractViewModel.fetchUserById(userId)
                            userId = ""
                        },
                        colors = ButtonDefaults.buttonColors(Color(0xff209FA8)),
                        enabled = userId.isNotEmpty() && !isFetching, // Enable button only when userId is not empty and fetching is not in progress
                        modifier = Modifier .padding(top = 28.dp).height(53.dp),// Chiếm 2 phần
                        shape = RoundedCornerShape(8.dp),
                    ) {
                        androidx.compose.material3.Text("Kiểm tra")
                    }
                }
                Spacer(modifier = Modifier.padding(5.dp))
                if (userList.isNotEmpty()) {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(userList) { user ->
                            UserItem(user, onDelete = {
                                userList = userList.filterNot { it._id == user._id }
                            })
                        }
                    }
                }

                // Sửa Content
                CustomTextField(
                    label = "Nội dung",
                    value = content,
                    onValueChange = { content = it  },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    placeholder = "Nhập nội dung",
                    isReadOnly = false
                )

                // Thêm hình ảnh
                SelectMedia { images ->
                    selectedPhotos = images
                }

                // Phản hồi trạng thái lỗi/thành công
                updateStatus?.let { errorMessage ->
                    LaunchedEffect(errorMessage) {
                        if (errorMessage.isNotEmpty()) {
                            snackbarHostState.showSnackbar(errorMessage)
                        }
                    }
                }
                Row {
                    Button(
                        onClick = onDismiss,
                        modifier = Modifier
                            .weight(1f)
                            .padding(
                                end = 10
                                    .dp
                            ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFf7f7f7)
                        ),
                        shape = RoundedCornerShape(10.dp)

                    ) {
                        androidx.compose.material3.Text(
                            text = "Quay lại",
                            color = Color(0xff2e90fa),
                            fontFamily = FontFamily(Font(R.font.cairo_regular)),
                            fontWeight = FontWeight(600),
                            fontSize = 17.sp,
                        )
                    }
                    Button(
                        onClick = {
                            if (isFieldEmpty(userIds)) {
                                // Hiển thị thông báo lỗi nếu title trống
                                Toast.makeText(context, "Userid không thể trống", Toast.LENGTH_SHORT).show()
                                return@Button        }
                            val userIdsList = userIds.split(",").map { it.trim() }
                            val isValid = validateUserIds(userIdsList)
                            if (!isValid) {
                                Toast.makeText(context, "Một hoặc nhiều userId không hợp lệ, mỗi userId phải có đủ 24 ký tự!", Toast.LENGTH_SHORT).show()
                                return@Button
                            }
                            if (isFieldEmpty(content)) {
                                // Hiển thị thông báo lỗi nếu content trống
                                Toast.makeText(context, "Nội dung không thể trống", Toast.LENGTH_SHORT).show()
                                return@Button
                            }
                            val maxPhotos = 10

                            if (selectedPhotos.size > maxPhotos) {
                                Toast.makeText(
                                    context,
                                    "Chỉ cho phép tối đa $maxPhotos ảnh!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                return@Button
                            }

                            val photoParts = selectedPhotos.mapNotNull { uri ->
                                val mimeType = context.contentResolver.getType(uri) ?: "image/jpeg"
                                prepareMultipartBody(
                                    context,
                                    uri,
                                    "photos_contract",
                                    ".jpg",
                                    mimeType
                                )
                            }

                            contractId?.let {
                                contractViewModel.updateContract_STAFF(
                                    contractId = contractId,
                                    userId = userIds,
                                    content = content,
                                    photos = photoParts
                                )
                            }

                            val formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy")
                            val currentTime = LocalDateTime.now().format(formatter)

                            val notificationRequest = NotificationRequest(
                                user_id = userId,
                                title = "Chỉnh sửa phòng thành công",
                                content = "Chỉnh sửa hợp đồng thành công lúc: $currentTime",
                            )

                            notificationViewModel.createNotification(notificationRequest)


                            Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show()

                            onDismiss() // Đóng Dialog
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFf7f7f7)
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        androidx.compose.material3.Text(
                            text = "Xác nhận",
                            color = Color(0xfff04438),
                            fontFamily = FontFamily(Font(R.font.cairo_regular)),
                            fontWeight = FontWeight(600),
                            fontSize = 17.sp
                        )
                    }

                }
            }
        }
    }
}