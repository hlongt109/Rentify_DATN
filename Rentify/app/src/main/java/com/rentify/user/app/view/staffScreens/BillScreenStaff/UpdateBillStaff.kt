package com.rentify.user.app.view.staffScreens.BillScreenStaff

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.rememberDatePickerState
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.request.ImageRequest
import com.rentify.user.app.MainActivity.ROUTER
import com.rentify.user.app.R
import com.rentify.user.app.model.Model.DescriptionItem
import com.rentify.user.app.model.Model.DescriptionOfUpdateInvoice
import com.rentify.user.app.model.Model.InvoiceUpdateRequest
import com.rentify.user.app.model.Model.NotificationRequest
import com.rentify.user.app.network.ApiStaff.RetrofitStaffService
import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.LoginRepository.LoginRepository
import com.rentify.user.app.repository.StaffRepository.BuildingRepository.BuildingStaffRepository
import com.rentify.user.app.repository.StaffRepository.InvoiceRepository.InvoiceRepository
import com.rentify.user.app.repository.StaffRepository.RoomRepository.RoomStaffRepository
import com.rentify.user.app.ui.theme.ColorBlack
import com.rentify.user.app.ui.theme.calender
import com.rentify.user.app.ui.theme.colorHeaderSearch
import com.rentify.user.app.utils.CheckUnit
import com.rentify.user.app.utils.ShowReport
import com.rentify.user.app.view.auth.components.HeaderComponent
import com.rentify.user.app.view.staffScreens.BillScreenStaff.Componenet.ShowService
import com.rentify.user.app.view.staffScreens.BillScreenStaff.Componenet.TextFieldSmall
import com.rentify.user.app.view.staffScreens.BillScreenStaff.Componenet.TextFiledComponent
import com.rentify.user.app.viewModel.LoginViewModel
import com.rentify.user.app.viewModel.NotificationViewModel
import com.rentify.user.app.viewModel.StaffViewModel.BuildingStaffViewModel
import com.rentify.user.app.viewModel.StaffViewModel.InvoiceStaffViewModel
import com.rentify.user.app.viewModel.StaffViewModel.RoomStaffViewModel
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateBillStaff(
    navController: NavController,
    invoiceId: String ?= "",
    notificationViewModel: NotificationViewModel = viewModel()
) {
    //lay thong tin user
    val context = LocalContext.current
    val apiService = RetrofitService()
    val userRepository = LoginRepository(apiService)
    val factory = remember(context) {
        LoginViewModel.LoginViewModelFactory(userRepository, context.applicationContext)
    }
    val loginViewModel: LoginViewModel = viewModel(factory = factory)
    val staffId = loginViewModel.getUserData().userId
    //view model building
    val buildingService = RetrofitStaffService
    val buildingRepository = BuildingStaffRepository(buildingService.ApiService)
    val buildingStaffViewModel: BuildingStaffViewModel = viewModel(
        factory = BuildingStaffViewModel.BuildingStaffViewModelFactory(buildingRepository)
    )
    //room viewmodel
    val roomService = RetrofitStaffService
    val roomRepository = RoomStaffRepository(roomService.ApiService)
    val roomStaffViewModel: RoomStaffViewModel = viewModel(
        factory = RoomStaffViewModel.RoomStaffViewModelFactory(roomRepository)
    )
    //bill viewmodel
    val invoiceRepository = InvoiceRepository(RetrofitStaffService.ApiService)
    val invoiceViewModel: InvoiceStaffViewModel = viewModel(
        factory = InvoiceStaffViewModel.InvoiceStaffViewModelFactory(invoiceRepository)
    )
    var isExpanded by remember { mutableStateOf(false) }
    var isExpandedRoom by remember { mutableStateOf(false) }
    var isExpandedRoomNull by remember { mutableStateOf(false) }
    var isExpandedBuildingNull by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        buildingStaffViewModel.getBuildingList(staffId)
    }
    var selectedBuildingId by remember { mutableStateOf("") }
    var pricePreElec by remember { mutableStateOf(0.0) }
    var pricePreWater by remember { mutableStateOf(0.0) }
    LaunchedEffect(selectedBuildingId) {
        if (selectedBuildingId.isNotEmpty()) {
            roomStaffViewModel.getRoomList(selectedBuildingId)
            // Tìm giá dịch vụ nước từ serviceFees
            val waterService = buildingStaffViewModel.serviceFees.value.find { it.name == "Nước" }
            pricePreWater = waterService?.price ?: 0.0
            val elecService = buildingStaffViewModel.serviceFees.value.find { it.name == "Điện" }
            pricePreElec = elecService?.price ?: 0.0
        }
    }
    val listBuilding by buildingStaffViewModel.listBuilding.collectAsState()
    val listRoom by roomStaffViewModel.listRoom.collectAsState()
    val serviceFees by buildingStaffViewModel.serviceFees.collectAsState()

    var building by remember { mutableStateOf("") }
    var room by remember { mutableStateOf("") }
    var priceRoom by remember { mutableStateOf(0.0) }
    var formattedPriceRoom by remember { mutableStateOf("") }
    val hasElecOrWaterService = serviceFees.filter { service ->
        service.name.lowercase() !in listOf("điện", "nước")
    }
    val totalAmount = hasElecOrWaterService.sumOf { it.price }
    val formatPrice = CheckUnit.formattedPrice(totalAmount.toFloat())
    //dien
    var oldElecNumber by remember { mutableStateOf(0) }
    var oldElecText by remember { mutableStateOf("") }
    var electricNumber by remember { mutableStateOf(0) }
    var elecText by remember { mutableStateOf("") }
    var consumeElecText by remember { mutableStateOf("") }
    var consumeElec = electricNumber - oldElecNumber
    var totalElec = consumeElec * pricePreElec
    var amountElec = CheckUnit.formattedPrice(totalElec.toFloat())
    var isFocusElectricNumber by remember { mutableStateOf(false) }
    var isFocusOldElecNumber by remember { mutableStateOf(false) }
    var isFocusConsumeElec by remember { mutableStateOf(false) }
    var isFocusAmountElec by remember { mutableStateOf(false) }
    //nuoc
    var oldWaterNumber by remember { mutableStateOf(0) }
    var oldWaterText by remember { mutableStateOf("") }
    var waterNumber by remember { mutableStateOf(0) }
    var waterText by remember { mutableStateOf("") }
    var consumeWaterText by remember { mutableStateOf("") }
    var consumeWater = waterNumber - oldWaterNumber
    var totalWater = consumeWater * pricePreWater
    var amountWater = CheckUnit.formattedPrice(totalWater.toFloat())
    var isFocusWaterNumber by remember { mutableStateOf(false) }
    var isFocusOldWaterNumber by remember { mutableStateOf(false) }
    var isFocusConsumeWater by remember { mutableStateOf(false) }
    var isFocusAmountWater by remember { mutableStateOf(false) }

    //ngay
    var showDatePicker by remember { mutableStateOf(false) }
    var dateBill by remember { mutableStateOf("") }
    var dateBillText by remember { mutableStateOf("") }
    val calendar = Calendar.getInstance()
    //
    var isFocusBuilding by remember { mutableStateOf(false) }
    var isFocusRoom by remember { mutableStateOf(false) }
    var isFocusPriceRoom by remember { mutableStateOf(false) }
    var isFocusDateBill by remember { mutableStateOf(false) }
    //
    var totalBill = totalAmount + priceRoom + totalElec + totalWater
    var amountBill = CheckUnit.formattedPrice(totalBill.toFloat())

    //
    var describe by remember { mutableStateOf("") }
    var isFocusDescribe by remember { mutableStateOf(false) }

    val uiState = invoiceViewModel.uiState.collectAsState()
    val isLoading = invoiceViewModel.isLoading.observeAsState(false)

    val invoiceDetails by invoiceViewModel.invoiceDetail.observeAsState()

    LaunchedEffect(invoiceId) {
        invoiceViewModel.getDetailInvoice(invoiceId!!)
    }

    val buildingName = listBuilding.find { it._id.toString() == selectedBuildingId }?.nameBuilding ?: "Tòa nhà chưa chọn"

    LaunchedEffect(invoiceDetails) {
        invoiceDetails?.let { detail ->
            Log.d("dcmm", "Invoice details: $detail")
            building = detail.buildingId.toString()
            selectedBuildingId = detail.buildingId.toString()
            room = detail.roomId?.room_name.toString()
            priceRoom = detail.roomId?.price?.toDouble() ?: 0.0
            formattedPriceRoom = CheckUnit.formattedPrice(priceRoom.toFloat())
            dateBill = detail.dueDate.toString()

            // Định dạng lại ngày theo định dạng dd/MM/yyyy
            val originalDate = detail.dueDate.toString()  // Giả sử dueDate là String
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

            try {
                val date = inputFormat.parse(originalDate)
                dateBillText = outputFormat.format(date ?: Date())
            } catch (e: Exception) {
                e.printStackTrace()
                dateBillText = originalDate // Trường hợp không parse được, giữ nguyên
            }

            describe = detail.describe.toString()

            detail.description?.forEach { description ->
                when (description.service_name) {
                    "Điện" -> {
                        oldElecText = (0 ?: "").toString()
                        electricNumber = description.quantity ?: 0
                        consumeElecText = (electricNumber - oldElecNumber).toString()
                    }
                    "Nước" -> {
                        oldWaterText = (0 ?: "").toString()
                        waterNumber = description.quantity ?: 0
                        consumeWaterText = (waterNumber - oldWaterNumber).toString()
                        amountWater = description.total?.toString() ?: ""
                    }
                }
            }

            detail.buildingId?.let { buildingStaffViewModel.selectBuilding(it) }
            detail.buildingId?.let { roomStaffViewModel.getRoomList(it) }
        } ?: run {
            Log.d("dcmm", "Invoice details is null")
        }
    }

    //cacloi
    val errorBuilding by invoiceViewModel.buildingErrorMessage.observeAsState()
    val errorRoom by invoiceViewModel.roomErorMessage.observeAsState()
    val errorDate by invoiceViewModel.dateErrorMessage.observeAsState()
    val errorWater by invoiceViewModel.waterErrorMessage.observeAsState()
    val errorElec by invoiceViewModel.elecErrorMessage.observeAsState()
    val oldWater by invoiceViewModel.oldWaterErrorMessage.observeAsState()
    val oldElec by invoiceViewModel.oldElecErrorMessage.observeAsState()
    val describeError by invoiceViewModel.describeErrorMessage.observeAsState()

    val successMessage by invoiceViewModel.successMessage.observeAsState()
    var showToast by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .background(color = Color.White)
            .verticalScroll(state = rememberScrollState())
            .padding(bottom = 20.dp)
    ) {
        UpdateBillManagerTopBar(navController = navController)

        if (!isLoading.value) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(top = 0.dp, bottom = 15.dp, start = 15.dp, end = 15.dp)
            ) {

                //toa nha
                TextFiledComponent(
                    value = buildingName,
                    onValueChange = { newBuilding ->
                        building = newBuilding
                    },
                    onClick = {},
                    placeHolder = "Tòa nhà",
                    isFocused = remember { mutableStateOf(isFocusBuilding) },
                    isShowIcon = true,
                    isIcon = Icons.Filled.KeyboardArrowDown,
                    listBuilding = listBuilding,
                    enable = false,
                    check = true,
                    onExpandedChange = { isExpanded = it },
                    onBuildingSelected = { buildingId ->
                        selectedBuildingId = buildingId
                        buildingStaffViewModel.selectBuilding(buildingId)
                        room = ""
                        priceRoom = 0.0
                        formattedPriceRoom = ""
                    },
                    onExpandedBuildingNull = { isExpandedBuildingNull = it }
                )

                errorBuilding?.let { ShowReport.ShowError(message = it) }
                //phong
                TextFiledComponent(
                    value = room,
                    onValueChange = { newRoom ->
                        room = newRoom
                    },
                    onClick = {},
                    placeHolder = "Phòng",
                    isFocused = remember { mutableStateOf(isFocusRoom) },
                    isShowIcon = true,
                    isIcon = Icons.Filled.KeyboardArrowDown,
                    listRoom = listRoom,
                    enable = false,
                    check = true,
                    onExpandedRoom = { isExpandedRoom = it },
                    onRoomSelected = { selectedRoom ->
                        priceRoom = selectedRoom.price.toDouble()
                        formattedPriceRoom = CheckUnit.formattedPrice(selectedRoom.price.toFloat())
                    },
                    onExpandedRoomNull = { isExpandedRoomNull = it }
                )

                errorRoom?.let { ShowReport.ShowError(message = it) }
                //tien phong
                TextFiledComponent(
                    value = formattedPriceRoom,
                    onValueChange = { newText ->
                        priceRoom = newText.toDouble()
                    },
                    placeHolder = "Tiền phòng",
                    isFocused = remember { mutableStateOf(isFocusPriceRoom) },
                    isShowIcon = false,
                    enable = false
                )


                //dien
                Spacer(modifier = Modifier.padding(top = 20.dp))
                // Điện
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Điện",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 15.dp)
                    )
                    // Hàng 1
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            TextFieldSmall(
                                value = oldElecText,
                                onValueChange = { newText ->
                                    oldElecText = newText
                                    electricNumber = newText.toIntOrNull() ?: 0
                                    invoiceViewModel.clearOldElecError()
                                },
                                placeHolder = "Số điện cũ",
                                isFocused = remember { mutableStateOf(isFocusOldElecNumber) }
                            )
                        }
                        Column {
                            TextFieldSmall(
                                value = electricNumber.toString(),
                                onValueChange = { newText ->
                                    electricNumber = newText.toIntOrNull() ?: 0
                                    invoiceViewModel.clearElecError()
                                },
                                placeHolder = "Số điện mới",
                                isFocused = remember { mutableStateOf(isFocusElectricNumber) }
                            )
                        }
                    }

                    // Hàng 2
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TextFieldSmall(
                            value = consumeElecText,
                            onValueChange = { newText ->
                                consumeElecText = newText
                            },
                            placeHolder = "Tiêu thụ",
                            isFocused = remember { mutableStateOf(isFocusConsumeElec) }
                        )

                        TextFieldSmall(
                            value = amountElec,
                            onValueChange = { newText ->
                                amountElec = newText
                            },
                            placeHolder = "Tổng tiền",
                            isFocused = remember { mutableStateOf(isFocusAmountElec) }
                        )
                    }
                }

// Nước
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Nước",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 15.dp)
                    )
                    // Hàng 1
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            TextFieldSmall(
                                value = oldWaterText,
                                onValueChange = { newText ->
                                    oldWaterText = newText
                                    waterNumber = newText.toIntOrNull() ?: 0
                                    invoiceViewModel.clearOldWaterError()
                                },
                                placeHolder = "Số nước cũ",
                                isFocused = remember { mutableStateOf(isFocusOldWaterNumber) }
                            )
                        }
                        Column {
                            TextFieldSmall(
                                value = waterNumber.toString(),
                                onValueChange = { newText ->
                                    waterNumber = newText.toIntOrNull() ?: 0
                                    invoiceViewModel.clearWaterError()
                                },
                                placeHolder = "Số nước mới",
                                isFocused = remember { mutableStateOf(isFocusWaterNumber) }
                            )
                        }
                    }

                    // Hàng 2
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TextFieldSmall(
                            value = consumeWaterText,
                            onValueChange = { newText ->
                                consumeWaterText = newText
                            },
                            placeHolder = "Tiêu thụ",
                            isFocused = remember { mutableStateOf(isFocusConsumeWater) }
                        )

                        TextFieldSmall(
                            value = amountWater,
                            onValueChange = { newText ->
                                amountWater = newText
                            },
                            placeHolder = "Tổng tiền",
                            isFocused = remember { mutableStateOf(isFocusAmountWater) }
                        )
                    }
                }

                //dich vu
                Spacer(modifier = Modifier.padding(top = 20.dp))
                Text(
                    text = "Dịch vụ",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = ColorBlack,
                    modifier = Modifier.padding(bottom = 15.dp)
                )
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(
                            elevation = 3.dp,
                            shape = RoundedCornerShape(8.dp),
                            spotColor = Color.Black
                        )
                        .clip(shape = RoundedCornerShape(8.dp)),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        hasElecOrWaterService.forEach { item ->
                            ShowService(item)
                        }
                        Spacer(modifier = Modifier.padding(top = 10.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Tổng",
                                fontSize = 16.sp,
                                color = ColorBlack,
                                fontWeight = FontWeight.Medium
                            )

                            Text(
                                text = formatPrice,
                                fontSize = 13.sp,
                                color = ColorBlack,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                //mo ta
                TextFiledComponent(
                    value = describe,
                    onValueChange = { newText ->
                        describe = newText
                        invoiceViewModel.clearDescribeError()
                    },
                    placeHolder = "Mô tả hóa đơn",
                    isFocused = remember { mutableStateOf(isFocusDescribe) },
                    isShowIcon = false,
                    enable = true
                )
                describeError?.let { ShowReport.ShowError(message = it) }

                // ngay chot
                TextFiledComponent(
                    value = dateBillText,
                    onValueChange = { newText ->
                        dateBillText = newText
                        dateBill = newText
                        invoiceViewModel.clearDateError()
                    },
                    placeHolder = "Ngày chốt",
                    isFocused = remember { mutableStateOf(isFocusDateBill) },
                    isShowIcon = true,
                    isIcon = calender,
                    onClick = { showDatePicker = true },
                    enable = false
                )
                errorDate?.let { ShowReport.ShowError(message = it) }
                //tong tien
                Spacer(modifier = Modifier.padding(top = 10.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .shadow(
                            elevation = 3.dp,
                            shape = RoundedCornerShape(8.dp),
                            spotColor = Color.Black
                        )
                        .clip(shape = RoundedCornerShape(8.dp)),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Tổng hóa đơn: ",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = ColorBlack,
                        )

                        Text(
                            text = amountBill.toString(),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Red,
                        )
                    }
                }

                //button
                Spacer(modifier = Modifier.padding(top = 20.dp))
                Button(
                    onClick = {
                        if (!isLoading.value) {
                            if (describe.isEmpty()) {
                                Toast.makeText(context, "Vui lòng nhập mô tả hóa đơn", Toast.LENGTH_SHORT).show()
                                return@Button
                            }
                            val existingDescriptions = (invoiceDetails?.description as? List<DescriptionOfUpdateInvoice>)
                                ?.map {
                                    DescriptionItem(
                                        service_name = it.service_name!!,
                                        quantity = it.quantity!!,
                                        price_per_unit = it.pricePerUnit!!,
                                        total = it.total!!
                                    )
                                } ?: emptyList()

                            val otherServices = existingDescriptions.filter { it.service_name !in listOf("Điện", "Nước") }

                            val updatedDescriptions = listOf(
                                DescriptionItem(
                                    service_name = "Điện",
                                    quantity = consumeElec,
                                    price_per_unit = pricePreElec,
                                    total = totalElec
                                ),
                                DescriptionItem(
                                    service_name = "Nước",
                                    quantity = consumeWater,
                                    price_per_unit = pricePreWater,
                                    total = totalWater
                                )
                            ) + otherServices

                            val invoiceUpdateRequest = InvoiceUpdateRequest(
                                description = updatedDescriptions,
                                describe = describe,
                                amount = totalBill,
                                due_date = dateBillText
                            )

                            invoiceViewModel.updateInvoice(invoiceId!!, invoiceUpdateRequest)

                            val formatter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy")
                            } else {
                                TODO("VERSION.SDK_INT < O")
                            }
                            val currentTime = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                LocalDateTime.now().format(formatter)
                            } else {
                                TODO("VERSION.SDK_INT < O")
                            }

                            val notificationRequest = NotificationRequest(
                                user_id = staffId,
                                title = "Chỉnh sửa hoá đơn thành công",
                                content = "Phòng ${room} đã được chỉnh sửa hoá đơn thành công lúc: $currentTime",
                            )

                            notificationViewModel.createNotification(notificationRequest)

                            successMessage?.let {
                                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                            }
                            navController.navigate(ROUTER.BILL_STAFF.name)
                            invoiceViewModel.clearAll()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorHeaderSearch
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    if (isLoading.value) {
                        CircularProgressIndicator(
                            color = Color.White
                        )
                    } else {
                        Text(
                            text = "Chỉnh sửa hóa đơn",
                            color = Color.White,
                            modifier = Modifier.padding(vertical = 4.dp),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

            }
        }else{
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(R.drawable.loading)
                        .decoderFactory(GifDecoder.Factory())
                        .build(),
                    contentDescription = "Loading GIF",
                    modifier = Modifier
                        .size(150.dp)
                        .align(Alignment.Center)
                )
            }
        }
    }

    if (showDatePicker) {
        val today = Calendar.getInstance()
        val maxDate = Calendar.getInstance().apply {
            add(Calendar.MONTH, 1)
        }
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = today.timeInMillis,
            selectableDates = object : SelectableDates {
                override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                    return utcTimeMillis >= today.timeInMillis && utcTimeMillis <= maxDate.timeInMillis
                }
            }
        )

        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            calendar.timeInMillis = millis
                            dateBillText = CheckUnit.formatDay(calendar.time)
                            dateBill = CheckUnit.formatDay_2(calendar.time)
                        }
                        showDatePicker = false
                    }
                ) {
                    Text("Ok")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Hủy")
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
                showModeToggle = false
            )
        }
    }
}

@Composable
fun UpdateBillManagerTopBar(
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

        Text(
            text = "Chỉnh sửa hoá đơn",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 18.sp,
            style = MaterialTheme.typography.h6
        )
    }
}