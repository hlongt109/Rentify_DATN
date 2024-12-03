package com.rentify.user.app.view.staffScreens.contract.contractComponents

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import coil.compose.rememberAsyncImagePainter
import com.rentify.user.app.R
import com.rentify.user.app.view.staffScreens.UpdatePostScreen.isFieldEmpty
import com.rentify.user.app.view.staffScreens.UpdatePostScreen.prepareMultipartBody


import com.rentify.user.app.view.userScreens.cancelContract.components.ContractInfoRow
import com.rentify.user.app.view.userScreens.cancelContract.components.HeaderSection
import com.rentify.user.app.view.userScreens.cancelContract.components.ViewContractButton
import com.rentify.user.app.view.userScreens.contract.components.DialogCompose
import com.rentify.user.app.viewModel.PostViewModel.PostViewModel
import com.rentify.user.app.viewModel.StaffViewModel.ContractViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import okio.Buffer
import java.io.IOException

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
    var showDialog by remember { mutableStateOf(false) }

    // State để hiển thị thông báo
    val snackbarHostState = remember { SnackbarHostState() }

    // Khi dialog đóng, cần reset trạng thái của thông báo
    val onDismissDialog: () -> Unit = {
        showDialog = false
    }
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
                        Column {
                            ViewContractButton(onClick = { navController.navigate("contract_image_detail/${contract._id}") })
                            Spacer(modifier = Modifier
                                .background(color = Color(0xFFeeeeee))
                                .fillMaxWidth()
                                .height(25.dp))
                            Button(
                                onClick = { showDialog = true },
                                modifier = Modifier
                                    .fillMaxWidth()
                                ,shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xffffffff))
                            ) {
                                Text("Sửa hợp đồng")
                            }
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
    }}


@Composable
fun EditContractDialog(
    contractId: String?,
    onDismiss: () -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    val context = LocalContext.current
    var userId by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var selectedPhotos by remember { mutableStateOf(emptyList<Uri>()) }
    val contractViewModel: ContractViewModel = viewModel()
    val contractDetail by contractViewModel.contractDetail.observeAsState()
    val updateStatus by contractViewModel.error.observeAsState() // Theo dõi trạng thái lỗi từ ViewModel
    var isEdited by remember { mutableStateOf(false) }

    // Lấy chi tiết hợp đồng khi hợp đồng ID thay đổi
    LaunchedEffect(contractId) {
        contractId?.let { contractViewModel.fetchContractDetail(it) }
    }

    val scrollState = rememberScrollState()

    contractDetail?.let { contract ->
        if (!isEdited) {
            userId = contract.user_id?.joinToString { it._id } ?: "" // Ghép ID người dùng
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
                // Sửa User ID
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                ) {
                    Row {
                      Text(
                            text = "UserId",
                            //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                            color = Color(0xff7f7f7f),
                            // fontWeight = FontWeight(700),
                            fontSize = 13.sp,
                        )
                       Text(
                            text = " *",
                            //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                            color = Color(0xffff1a1a),
                            // fontWeight = FontWeight(700),
                            fontSize = 16.sp,

                            )
                    }
                    TextField(
                        value = userId,
                        onValueChange = { newValue ->
                            userId = newValue // Cập nhật giá trị title khi người dùng thay đổi
                            isEdited = true  // Đánh dấu là đã chỉnh sửa
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color(0xFFcecece),
                            unfocusedIndicatorColor = Color(0xFFcecece),
                            focusedPlaceholderColor = Color.Black,
                            unfocusedPlaceholderColor = Color.Gray,
                            unfocusedContainerColor = Color(0xFFf7f7f7),
                            focusedContainerColor = Color(0xFFf7f7f7),
                        ),
                        placeholder = {
                            Text(
                                text = "Nhập userId",
                                fontSize = 13.sp,
                                color = Color(0xFF898888),
                                fontFamily = FontFamily(Font(R.font.cairo_regular))
                            )
                        },
                        shape = RoundedCornerShape(size = 8.dp),
                        textStyle = TextStyle(
                            color = Color.Black, fontFamily = FontFamily(Font(R.font.cairo_regular))
                        )
                    )
                }
                // Sửa Content
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                ) {
                    Row {
                  Text(
                            text = "Nội dung",
                            //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                            color = Color(0xff7f7f7f),
                            // fontWeight = FontWeight(700),
                            fontSize = 13.sp,
                        )
                     Text(
                            text = " *",
                            //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                            color = Color(0xffff1a1a),
                            // fontWeight = FontWeight(700),
                            fontSize = 16.sp,

                            )
                    }
                    TextField(
                        value = content,
                        onValueChange = { newValue ->
                            content = newValue // Cập nhật giá trị title khi người dùng thay đổi
                            isEdited = true  // Đánh dấu là đã chỉnh sửa
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color(0xFFcecece),
                            unfocusedIndicatorColor = Color(0xFFcecece),
                            focusedPlaceholderColor = Color.Black,
                            unfocusedPlaceholderColor = Color.Gray,
                            unfocusedContainerColor = Color(0xFFf7f7f7),
                            focusedContainerColor = Color(0xFFf7f7f7),
                        ),
                        placeholder = {
                            Text(
                                text = "Nhập nội dung",
                                fontSize = 13.sp,
                                color = Color(0xFF898888),
                                fontFamily = FontFamily(Font(R.font.cairo_regular))
                            )
                        },
                        shape = RoundedCornerShape(size = 8.dp),
                        textStyle = TextStyle(
                            color = Color.Black, fontFamily = FontFamily(Font(R.font.cairo_regular))
                        )
                    )
                }

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
                            if (isFieldEmpty(userId)) {
                            // Hiển thị thông báo lỗi nếu title trống
                            Toast.makeText(context, "Userid không thể trống", Toast.LENGTH_SHORT).show()
                            return@Button        }
                        if (selectedPhotos.isEmpty()) {
                            // Hiển thị thông báo nếu không có ảnh nào được chọn
                            Toast.makeText(context, "Bạn phải chọn ít nhất một ảnh!", Toast.LENGTH_SHORT).show()
                            return@Button
                        }


                        if (isFieldEmpty(content)) {
                            // Hiển thị thông báo lỗi nếu content trống
                            Toast.makeText(context, "Nội dung không thể trống", Toast.LENGTH_SHORT).show()
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
                                userId = userId,
                                content = content,
                                photos = photoParts
                            )

                        }
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
//                Row(
//
//                    horizontalArrangement = Arrangement.End,
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    TextButton(onClick = onDismiss) {
//                        Text("Hủy")
//                    }
//                    Spacer(modifier = Modifier.width(8.dp))
//                    Button(onClick = {
//                        if (isFieldEmpty(userId)) {
//                            // Hiển thị thông báo lỗi nếu title trống
//                            Toast.makeText(context, "Userid không thể trống", Toast.LENGTH_SHORT).show()
//                            return@Button        }
//                        if (selectedPhotos.isEmpty()) {
//                            // Hiển thị thông báo nếu không có ảnh nào được chọn
//                            Toast.makeText(context, "Bạn phải chọn ít nhất một ảnh!", Toast.LENGTH_SHORT).show()
//                            return@Button
//                        }
//
//
//                        if (isFieldEmpty(content)) {
//                            // Hiển thị thông báo lỗi nếu content trống
//                            Toast.makeText(context, "Nội dung không thể trống", Toast.LENGTH_SHORT).show()
//                            return@Button
//                        }
//                        val photoParts = selectedPhotos.mapNotNull { uri ->
//                            val mimeType = context.contentResolver.getType(uri) ?: "image/jpeg"
//                            prepareMultipartBody(
//                                context,
//                                uri,
//                                "photos_contract",
//                                ".jpg",
//                                mimeType
//                            )
//                        }
//
//                        contractId?.let {
//                            contractViewModel.updateContract_STAFF(
//                                contractId = contractId,
//                                userId = userId,
//                                content = content,
//                                photos = photoParts
//                            )
//
//                        }
//                        onDismiss() // Đóng Dialog
//                    }) {
//                        Text("Lưu")
//                    }
//                }
            }
        }
    }
}

fun getRequestBodyString(requestBody: RequestBody): String {
    return try {
        val buffer = Buffer()
        requestBody.writeTo(buffer)
        buffer.readUtf8()  // Trả về chuỗi UTF-8 từ RequestBody
    } catch (e: IOException) {
        "Error reading RequestBody: ${e.localizedMessage}"
    }
}
