package com.rentify.user.app.view.staffScreens.BillScreenStaff

import android.app.DatePickerDialog
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.MainActivity.ROUTER
import com.rentify.user.app.model.Model.NotificationRequest
import com.rentify.user.app.network.ApiStaff.ApiServiceStaff
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
import com.rentify.user.app.utils.Component.getLoginViewModel
import com.rentify.user.app.utils.ShowReport
import com.rentify.user.app.view.auth.components.HeaderComponent
import com.rentify.user.app.view.staffScreens.BillScreenStaff.Componenet.ShowService
import com.rentify.user.app.view.staffScreens.BillScreenStaff.Componenet.TextFieldSmall
import com.rentify.user.app.view.staffScreens.BillScreenStaff.Componenet.TextFiledComponent
import com.rentify.user.app.viewModel.LoginViewModel
import com.rentify.user.app.viewModel.NotificationViewModel
import com.rentify.user.app.viewModel.StaffViewModel.BuildingStaffViewModel
import com.rentify.user.app.viewModel.StaffViewModel.InvoiceStaffViewModel
import com.rentify.user.app.viewModel.StaffViewModel.InvoiceUiState
import com.rentify.user.app.viewModel.StaffViewModel.RoomStaffViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBillStaff(
    navController: NavController,
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
    val addBillResult by invoiceViewModel.addBillResult.collectAsState()
    Log.d("ListBuilding", "AddBillStaff: $listBuilding")

    //khai bao 1 ty thu
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
    var dateBillText by remember { mutableStateOf("")}
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
        AddBillManagerTopBar(navController = navController)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(top = 0.dp, bottom = 15.dp, start = 15.dp, end = 15.dp)
        ) {
            //toa nha
            TextFiledComponent(
                value = building,
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
                onExpandedChange = {
                    isExpanded = it
                },
                onBuildingSelected = { buildingId ->
                    selectedBuildingId = buildingId
                    buildingStaffViewModel.selectBuilding(buildingId)
                    room = ""
                    priceRoom = 0.0
                    formattedPriceRoom = ""
                },
                onExpandedBuildingNull = {
                    isExpandedBuildingNull = it
                }
            )
            errorBuilding?.let { ShowReport.ShowError(message = it) }
            //phong
            TextFiledComponent(
                value = room,
                onValueChange = { newRoom ->
                    room = newRoom
                },
                onClick = {
                },
                placeHolder = "Phòng",
                isFocused = remember { mutableStateOf(isFocusRoom) },
                isShowIcon = true,
                isIcon = Icons.Filled.KeyboardArrowDown,
                listRoom = listRoom,
                enable = false,
                onExpandedRoom = {
                    isExpandedRoom = it
                },
                onRoomSelected = { selectedRoom ->
                    val originalPrice = selectedRoom.price.toDouble()
                    val saleDiscount = selectedRoom.sale.toDouble() ?: 0.0 // Nếu `sale` null thì mặc định là 0
                    priceRoom = originalPrice - saleDiscount
                    formattedPriceRoom = CheckUnit.formattedPrice(priceRoom.toFloat())
                },
                onExpandedRoomNull = {
                    isExpandedRoomNull = it
                }
            )
            errorRoom?.let { ShowReport.ShowError(message = it) }
            //tien phong
            TextFiledComponent(
                value = formattedPriceRoom.toString(),
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
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Điện",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = ColorBlack,
                    modifier = Modifier.padding(bottom = 15.dp)
                )

                //hang 1
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column() {
                        TextFieldSmall(
                            value = if (oldElecNumber == null) "" else oldElecText,
                            onValueChange = { newText ->
                                oldElecText = newText
                                oldElecNumber = newText.toIntOrNull() ?: 0
                                invoiceViewModel.clearOldElecError()
                            },
                            placeHolder = "Số điện cũ",
                            isFocused = remember { mutableStateOf(isFocusOldElecNumber) }
                        )
                        oldElec?.let { ShowReport.ShowError(message = it) }
                    }
                    //
                    Column {
                        TextFieldSmall(
                            value = if (electricNumber == 0) "" else elecText,
                            onValueChange = { newText ->
                                elecText = newText
                                electricNumber = newText.toIntOrNull() ?: 0
                                invoiceViewModel.clearElecError()
                            },
                            placeHolder = "Số điện mới",
                            isFocused = remember { mutableStateOf(isFocusElectricNumber) }
                        )
                        errorElec?.let { ShowReport.ShowError(message = it) }
                    }
                    //
                }
                //hang 2
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextFieldSmall(
                        value = if (consumeElec == 0) "" else consumeElec.toString(),
                        onValueChange = { newText ->
                            consumeElecText = newText
                            consumeElec = newText.toIntOrNull() ?: 0
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

            //nuoc
            Spacer(modifier = Modifier.padding(top = 20.dp))
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Nước",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = ColorBlack,
                    modifier = Modifier.padding(bottom = 15.dp)
                )

                //hang 1
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        TextFieldSmall(
                            value = if (oldWaterNumber == null) "" else oldWaterText,
                            onValueChange = { newText ->
                                oldWaterText = newText
                                oldWaterNumber = newText.toIntOrNull() ?: 0
                                invoiceViewModel.clearOldWaterError()
                            },
                            placeHolder = "Số nước cũ",
                            isFocused = remember { mutableStateOf(isFocusOldWaterNumber) }
                        )
                        oldWater?.let { ShowReport.ShowError(message = it) }
                    }
                    //
                    Column {
                        TextFieldSmall(
                            value = if (waterNumber == 0) "" else waterText,
                            onValueChange = { newText ->
                                waterText = newText
                                waterNumber = newText.toIntOrNull() ?: 0
                                invoiceViewModel.clearWaterError()
                            },
                            placeHolder = "Số nước mới",
                            isFocused = remember { mutableStateOf(isFocusWaterNumber) }
                        )
                        errorWater?.let { ShowReport.ShowError(message = it) }
                    }
                }
                //hang 2
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextFieldSmall(
                        value = if (consumeWater == 0) "" else consumeWater.toString(),
                        onValueChange = { newText ->
                            consumeWaterText = newText
                            consumeWater = newText.toIntOrNull() ?: 0
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
                    if (!isLoading.value) { // Chỉ cho phép gọi khi không ở trạng thái loading
                        val selectedRoom = listRoom.find { it.room_name == room }
                        if (selectedRoom != null) {
                            invoiceViewModel.addBill(
                                userId = staffId,
                                roomId = selectedRoom._id,
                                buildingId = selectedBuildingId,
                                consumeElec = consumeElec,
                                totalElec = totalElec,
                                consumeWater = consumeWater,
                                totalWater = totalWater,
                                roomPrice = priceRoom,
                                amount = totalBill,
                                serviceFees = serviceFees,
                                pricePreUnitElec = pricePreElec,
                                pricePreUnitWater = pricePreWater,
                                dueDate = dateBill,
                                water = waterNumber,
                                elec = electricNumber,
                                buildingName = building,
                                roomName = room,
                                oldElec = oldElecNumber,
                                oldWater = oldWaterNumber,
                                describe = describe
                            )
                            successMessage?.let {
                                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                            }

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
                                title = "Thêm hoá đơn thành công",
                                content = "Phòng ${room} đã được thêm hoá đơn thành công lúc: $currentTime",
                            )

                            notificationViewModel.createNotification(notificationRequest)

                            navController.navigate(ROUTER.BILL_STAFF.name)
                            invoiceViewModel.clearAll()
                        }

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
                        text = "Thêm hóa đơn",
                        color = Color.White,
                        modifier = Modifier.padding(vertical = 4.dp),
                        fontWeight = FontWeight.Medium
                    )
                }
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
fun AddBillManagerTopBar(
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
            text = "Thêm hoá đơn",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 18.sp,
            style = MaterialTheme.typography.h6
        )
    }
}