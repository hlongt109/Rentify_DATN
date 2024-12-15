package com.rentify.user.app.view.staffScreens.addRoomScreen

import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.rentify.user.app.R
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.rentify.user.app.model.Model.NotificationRequest
import com.rentify.user.app.utils.Component.getLoginViewModel
import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.LoginRepository.LoginRepository
import com.rentify.user.app.view.auth.components.HeaderComponent
import com.rentify.user.app.view.staffScreens.addRoomScreen.Components.ComfortableLabel
import com.rentify.user.app.view.staffScreens.addRoomScreen.Components.ComfortableLabelAdd
import com.rentify.user.app.view.staffScreens.addRoomScreen.Components.ComfortableOptionsAdd
import com.rentify.user.app.view.staffScreens.addRoomScreen.Components.RoomTypeLabel
import com.rentify.user.app.view.staffScreens.addRoomScreen.Components.RoomTypeOptions
import com.rentify.user.app.view.staffScreens.addRoomScreen.Components.SelectMedia
import com.rentify.user.app.view.staffScreens.addRoomScreen.Components.ServiceLabel
import com.rentify.user.app.view.staffScreens.addRoomScreen.Components.ServiceOptions
import com.rentify.user.app.viewModel.NotificationViewModel
import com.rentify.user.app.viewModel.LoginViewModel
import com.rentify.user.app.viewModel.RoomViewModel.RoomViewModel
import com.stevdzasan.messagebar.rememberMessageBarState
import java.text.DecimalFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRoomScreen(
    navController: NavHostController,
    buildingId: String?,
    notificationViewModel: NotificationViewModel = viewModel()
) {
    val context = LocalContext.current
    val viewModel: RoomViewModel = viewModel(
        factory = RoomViewModel.RoomViewModeFactory(context = context)
    )
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    var selectedRoomTypes by remember { mutableStateOf(listOf<String>()) }
    var selectedComfortable by remember { mutableStateOf(listOf<String>()) }
    var selectedService by remember { mutableStateOf(listOf<String>()) }

    val scrollState = rememberScrollState()

    var postTitle by remember { mutableStateOf("") }
    var numberOfRoommates by remember { mutableStateOf("") }
    var currentPeopleCount by remember { mutableStateOf("") }
    var area by remember { mutableStateOf("") }
    var roomPrice by remember { mutableStateOf("") }
    var roomSale by remember { mutableStateOf("") }
    var Status by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf("") }
    var selectedImages by remember { mutableStateOf(listOf<Uri>()) }
    var selectedVideos by remember { mutableStateOf(listOf<Uri>()) }

    val loginViewModel = getLoginViewModel(context)
    val userData = loginViewModel.getUserData()
    val staffId = userData.userId

    val state = rememberMessageBarState()

    var allComfortable by remember {
        mutableStateOf(
            listOf(
                "Vệ sinh khép kín",
                "Gác xép",
                "Ra vào vân tay",
                "Nuôi pet",
                "Không chung chủ"
            )
        )
    }

    // Observe states
    val isLoading by viewModel.isLoading.observeAsState(false)
    val addRoomResponse by viewModel.addRoomResponse.observeAsState()
    val error by viewModel.error.observeAsState()
    LaunchedEffect(addRoomResponse) {
        addRoomResponse?.let { response ->
            if (response.isSuccessful) {
                Toast.makeText(context, "Thêm phòng thành công", Toast.LENGTH_SHORT).show()
                state.addSuccess("Thêm phòng thành công")
                navController.popBackStack()
            }
        }
    }
    LaunchedEffect(error) {
        error?.let {
            state.addError(exception = Exception("Thêm phòng không thành công"))
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .background(color = Color(0xfff7f7f7))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xfff7f7f7))
                .padding(bottom = screenHeight.dp / 9.5f)
        ) {
            AddRoomTopBar(navController = navController)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
                    .background(color = Color(0xfff7f7f7))
                    .padding(15.dp)
            ) {

                SelectMedia (
                    onMediaSelected = { images, videos ->
                        selectedImages = images
                        selectedVideos = videos
                    }
                )
                Spacer(modifier = Modifier.padding(5.dp))
                // Tên phòng
                CustomTextField(
                    label = "Tên phòng",
                    value = postTitle,
                    onValueChange = { postTitle = it.uppercase() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    placeholder = "Tên phòng... ( P201 )",
                    isReadOnly = false
                )

                CustomTextField(
                    label = "Mô tả",
                    value = numberOfRoommates,
                    onValueChange = { numberOfRoommates = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    placeholder = "Mô tả...",
                    isReadOnly = false
                )

                CustomTextField(
                    label = "Giới hạn người ở",
                    value = currentPeopleCount,
                    onValueChange = { currentPeopleCount = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    placeholder = "Giới hạn người ở...",
                    isReadOnly = false
                )

                CustomTextField(
                    label = "Diện tích(m2)",
                    value = area,
                    onValueChange = { area = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    placeholder = "Diện tích...",
                    isReadOnly = false
                )

                val decimalFormat = remember { DecimalFormat("#,###,###") }
                val formattedRoomPrice = roomPrice.replace(",", "").toDoubleOrNull()?.let {
                    decimalFormat.format(it)
                } ?: roomPrice
                CustomTextField(
                    label = "Giá phòng",
                    value = formattedRoomPrice,
                    onValueChange = { input ->
                        // Remove commas before storing the raw value
                        val rawInput = input.replace(",", "")
                        roomPrice = rawInput
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    placeholder = "Giá phòng...",
                    isReadOnly = false
                )

                val formattedRoomSale = roomSale.replace(",", "").toDoubleOrNull()?.let {
                    decimalFormat.format(it)
                } ?: roomSale
                CustomTextField(
                    label = "Giảm giá",
                    value = formattedRoomSale,
                    onValueChange = { input ->
                        // Remove commas before storing the raw value
                        val rawInput = input.replace(",", "")
                        roomSale = rawInput
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    placeholder = "Giảm giá...",
                    isReadOnly = false
                )

                StatusDropdown(
                    label = "Trạng thái",
                    currentStatus = when (Status) {
                        "0" -> "Chưa cho thuê"
                        "1" -> "Đã cho thuê"
                        else -> ""
                    },
                    onStatusChange = { newStatus -> Status = newStatus },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp)
                )


                // Loại phòng
                Spacer(modifier = Modifier.height(10.dp))
                Column(modifier = Modifier.padding(horizontal = 5.dp)) {
                    RoomTypeLabel()
                    Spacer(modifier = Modifier.height(5.dp))
                    RoomTypeOptions(
                        selectedRoomTypes = selectedRoomTypes,
                        onRoomTypeSelected = { roomType ->
                            selectedRoomTypes =
                                listOf(roomType)
                        }
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))
                Column(modifier = Modifier.padding(horizontal = 5.dp)) {
                    ComfortableLabelAdd { newComfortable ->
                        if (newComfortable !in allComfortable) {
                            allComfortable = allComfortable + newComfortable
                        }
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    ComfortableOptionsAdd(
                        selectedComfortable = selectedComfortable,
                        allComfortable = allComfortable,
                        onComfortableSelected = { comfortable ->
                            selectedComfortable = if (selectedComfortable.contains(comfortable)) {
                                selectedComfortable - comfortable
                            } else {
                                selectedComfortable + comfortable
                            }
                        }
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))
                Column(modifier = Modifier.padding(horizontal = 5.dp)) {
                    ServiceLabel()
                    Spacer(modifier = Modifier.height(5.dp))
                    ServiceOptions(
                        selectedService = selectedService,
                        onServiceSelected = { service ->
                            selectedService = if (selectedService.contains(service)) {
                                selectedService - service
                            } else {
                                selectedService + service
                            }
                        },
                        buildingId = buildingId!!
                    )
                }

                // Hiển thị thông báo lỗi
                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(5.dp)
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(screenHeight.dp / 9.5f)
                .background(color = Color.White)
        ) {
            Box(modifier = Modifier.padding(20.dp)) {
                Button(
                    onClick = {
                        if (!isLoading) {

                            if (postTitle.isBlank() || numberOfRoommates.isBlank() ||
                                currentPeopleCount.isBlank() || area.isBlank() ||
                                roomPrice.isBlank() || Status.isBlank()
                            ) {
                                errorMessage = "Vui lòng điền đầy đủ thông tin."
                                return@Button
                            }

                            val limitPerson = currentPeopleCount.toIntOrNull()
                            if (limitPerson == null) {
                                errorMessage = "Giới hạn người phải là số nguyên."
                                return@Button
                            }

                            val roomArea = area.toDoubleOrNull()
                            if (roomArea == null) {
                                errorMessage = "Diện tích phải là số."
                                return@Button
                            }

                            val roomPriceValue = roomPrice.toDoubleOrNull()
                            if (roomPriceValue == null) {
                                errorMessage = "Giá phòng phải là số."
                                return@Button
                            }

                            val roomSaleValue = roomSale.takeIf { it.isNotBlank() }?.toDoubleOrNull()
                            if (roomSale.isNotBlank() && roomSaleValue == null) {
                                errorMessage = "Giảm giá phải là số."
                                return@Button
                            }

                            val roomStatusValue = Status.toIntOrNull()
                            if (roomStatusValue != 0 && roomStatusValue != 1) {
                                errorMessage = "Trạng thái chỉ được nhập 0 hoặc 1."
                                return@Button
                            }

                            errorMessage = ""
                            buildingId?.let { id ->
                                viewModel.addRoom(
                                    buildingId = id,
                                    roomName = postTitle,
                                    roomType = selectedRoomTypes.joinToString(","),
                                    description = numberOfRoommates,
                                    price = roomPriceValue,
                                    size = area,
                                    status = Status,
                                    videoUris = selectedVideos,
                                    photoUris = selectedImages,
                                    service = selectedService,
                                    amenities = selectedComfortable,
                                    limit_person = limitPerson,
                                    sale = roomSaleValue ?: 0.0
                                )
                            }

                            val formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy")
                            val currentTime = LocalDateTime.now().format(formatter)

                            val notificationRequest = NotificationRequest(
                                user_id = staffId,
                                title = "Thêm phòng thành công",
                                content = "Phòng ${postTitle} đã được thêm thành công lúc: $currentTime",
                            )

                            notificationViewModel.createNotification(notificationRequest)

                        }
                    },
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xff5dadff),
                        disabledContainerColor = Color(0xff5dadff).copy(alpha = 0.7f)
                    ),
                    enabled = !isLoading
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = Color.White
                        )
                    } else {
                        Text(
                            text = "Thêm Phòng",
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.cairo_regular)),
                            color = Color.White
                        )
                    }
                }
            }

            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.3f))
                        .clickable(enabled = false) { /* prevent clicks while loading */ },
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
            }
        }

    }
}

@Composable
fun AddRoomTopBar(
    navController: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        androidx.compose.material.IconButton(onClick = { navController.popBackStack() }) {
            androidx.compose.material.Icon(
                imageVector = Icons.Filled.ArrowBackIosNew, contentDescription = "Back"
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        androidx.compose.material.Text(
            text = "Thêm phòng",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 18.sp,
            style = MaterialTheme.typography.h6
        )
    }
}

@Composable
fun CustomTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isReadOnly: Boolean = false,
    placeholder: String = ""
) {
    Column(modifier = modifier) {
        // Label
        Text(
            text = label,
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        // TextField
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(
                    color = Color.Transparent, shape = RoundedCornerShape(8.dp)
                )
                .border(
                    width = 1.dp, color = Color(0xFF908b8b), shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 12.dp, vertical = 10.dp),
            textStyle = TextStyle(
                fontSize = 14.sp,
                color = Color(0xFF989898),
            ),
            enabled = !isReadOnly,
            decorationBox = { innerTextField ->
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp)
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            fontSize = 14.sp,
                            color = Color(0xFF989898)
                        )
                    }
                    innerTextField()
                }
            }
        )
    }
}

@Composable
fun StatusDropdown(
    label: String,
    currentStatus: String,
    onStatusChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val statusOptions =
        listOf("Chưa cho thuê" to 0, "Đã cho thuê" to 1) // Lưu trạng thái là số (0, 1)
    val icon = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown

    Column(modifier = modifier) {
        // Label
        Text(
            text = label,
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        // Dropdown trigger
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(
                    color = Color.Transparent, shape = RoundedCornerShape(8.dp)
                )
                .border(
                    width = 1.dp, color = Color(0xFF908b8b), shape = RoundedCornerShape(8.dp)
                )
                .clickable { expanded = !expanded }, // Mở menu khi click
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (currentStatus.isEmpty()) "Chọn trạng thái" else currentStatus,
                    fontSize = 14.sp,
                    color = if (currentStatus.isEmpty()) Color(0xFF989898) else Color.Black
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(imageVector = icon, contentDescription = null)
            }
        }

        // Dropdown Menu
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            statusOptions.forEach { (statusText, statusValue) ->
                DropdownMenuItem(
                    onClick = {
                        onStatusChange(statusValue.toString()) // Cập nhật trạng thái là số (0 hoặc 1)
                        expanded = false
                    },
                    text = { Text(text = statusText) }
                )
            }
        }
    }
}