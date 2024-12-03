package com.rentify.user.app.view.staffScreens.addRoomScreen


import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rentify.user.app.view.auth.components.HeaderComponent
import com.rentify.user.app.view.staffScreens.addRoomScreen.Components.ComfortableLabel
import com.rentify.user.app.view.staffScreens.addRoomScreen.Components.ComfortableOptions
import com.rentify.user.app.view.staffScreens.addRoomScreen.Components.RoomTypeLabel
import com.rentify.user.app.view.staffScreens.addRoomScreen.Components.RoomTypeOptions
import com.rentify.user.app.view.staffScreens.addRoomScreen.Components.SelectMedia
import com.rentify.user.app.view.staffScreens.addRoomScreen.Components.ServiceLabel
import com.rentify.user.app.view.staffScreens.addRoomScreen.Components.ServiceOptions
import com.rentify.user.app.viewModel.RoomViewModel.RoomViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRoomScreen(
    navController: NavHostController,
    buildingId: String?
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
    var Status by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var selectedImages by remember { mutableStateOf(listOf<Uri>())}
    var selectedVideos by remember {mutableStateOf(listOf<Uri>())}
    Log.d("TAG", " tiện nghi select : $selectedComfortable")
    Log.d("TAG", "dịch vụ select: $selectedService")
    Log.d("TAG", " tiện nghi select : $selectedComfortable")
    Log.d("TAG", "Người select: $currentPeopleCount")
    // Observe states
    val isLoading by viewModel.isLoading.observeAsState(false)
    val addRoomResponse by viewModel.addRoomResponse.observeAsState()
    val error by viewModel.error.observeAsState()
    LaunchedEffect(addRoomResponse) {
        addRoomResponse?.let { response ->
            if (response.isSuccessful) {
                Toast.makeText(context, "Thêm phòng thành công", Toast.LENGTH_SHORT).show()
                // Navigate back
                navController.popBackStack()
            }
        }
    }
    LaunchedEffect(error) {
        error?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xfff7f7f7))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .background(color = Color(0xfff7f7f7))
                .padding(bottom = screenHeight.dp / 7f)
        ) {
            HeaderComponent(
                backgroundColor = Color(0xffffffff),
                title = "Thêm phòng",
                navController = navController
            )
            Spacer(modifier = Modifier.height(10.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
                    .background(color = Color(0xfff7f7f7))
                    .padding(15.dp)
            ) {
                // Tên phòng
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                ) {
                    Text(text = "Tên phòng*", color = Color(0xFF7c7b7b), fontSize = 13.sp)
                    TextField(
                        value = postTitle,
                        onValueChange = { postTitle = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(53.dp),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color(0xFFcecece),
                            unfocusedIndicatorColor = Color(0xFFcecece),
                            focusedPlaceholderColor = Color.Black,
                            unfocusedPlaceholderColor = Color.Gray,
                            unfocusedContainerColor = Color(0xFFf7f7f7),
                            focusedContainerColor = Color.White
                        ),
                        placeholder = {
                            Text(
                                text = "Nhập tên phòng",
                                fontSize = 14.sp,
                                color = Color(0xFF898888),
                                fontFamily = FontFamily(Font(R.font.cairo_regular))
                            )
                        },
                        shape = RoundedCornerShape(size = 8.dp),
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontFamily = FontFamily(Font(R.font.cairo_regular))
                        )
                    )
                }


                // Mô tả phòng
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                ) {
                    Text(text = "Mô tả phòng*", color = Color(0xFF7c7b7b), fontSize = 13.sp)
                    TextField(
                        value = numberOfRoommates,
                        onValueChange = { numberOfRoommates = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(53.dp),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color(0xFFcecece),
                            unfocusedIndicatorColor = Color(0xFFcecece),
                            focusedPlaceholderColor = Color.Black,
                            unfocusedPlaceholderColor = Color.Gray,
                            unfocusedContainerColor = Color(0xFFf7f7f7),
                            focusedContainerColor = Color.White
                        ),
                        placeholder = {
                            Text(
                                text = "Nhập mô tả",
                                fontSize = 13.sp,
                                color = Color(0xFF898888),
                                fontFamily = FontFamily(Font(R.font.cairo_regular))
                            )
                        },
                        shape = RoundedCornerShape(size = 8.dp),
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontFamily = FontFamily(Font(R.font.cairo_regular))
                        )
                    )
                }


                // Loại phòng
                Column {
                    RoomTypeLabel()
                    RoomTypeOptions(
                        selectedRoomTypes = selectedRoomTypes,
                        onRoomTypeSelected = { roomType ->
                            selectedRoomTypes =
                                listOf(roomType)  // Ensure only one room type is selected at a time
                        }
                    )
                }
                SelectMedia { images, videos ->
                    selectedImages = images
                    selectedVideos = videos
                }


                // Giới hạn người
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                ) {
                    Text(text = "Giới hạn người *", color = Color(0xFF7c7b7b), fontSize = 13.sp)
                    TextField(
                        value = currentPeopleCount,
                        onValueChange = { currentPeopleCount = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(53.dp),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color(0xFFcecece),
                            unfocusedIndicatorColor = Color(0xFFcecece),
                            focusedPlaceholderColor = Color.Black,
                            unfocusedPlaceholderColor = Color.Gray,
                            unfocusedContainerColor = Color(0xFFf7f7f7),
                            focusedContainerColor = Color.White
                        ),
                        placeholder = {
                            Text(
                                text = "Nhập số người",
                                fontSize = 13.sp,
                                color = Color(0xFF898888),
                                fontFamily = FontFamily(Font(R.font.cairo_regular))
                            )
                        },
                        shape = RoundedCornerShape(size = 8.dp),
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontFamily = FontFamily(Font(R.font.cairo_regular))
                        )
                    )
                }


                // Diện tích
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                ) {
                    Text(text = "Diện tích(m2) *", color = Color(0xFF7c7b7b), fontSize = 13.sp)
                    TextField(
                        value = area,
                        onValueChange = { area = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(53.dp),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color(0xFFcecece),
                            unfocusedIndicatorColor = Color(0xFFcecece),
                            focusedPlaceholderColor = Color.Black,
                            unfocusedPlaceholderColor = Color.Gray,
                            unfocusedContainerColor = Color(0xFFf7f7f7),
                            focusedContainerColor = Color.White
                        ),
                        placeholder = {
                            Text(
                                text = "Nhập diện tích",
                                fontSize = 13.sp,
                                color = Color(0xFF898888),
                                fontFamily = FontFamily(Font(R.font.cairo_regular))
                            )
                        },
                        shape = RoundedCornerShape(size = 8.dp),
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontFamily = FontFamily(Font(R.font.cairo_regular))
                        )
                    )
                }


                // Giá phòng
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                ) {
                    Text(text = "Giá phòng *", color = Color(0xFF7c7b7b), fontSize = 13.sp)
                    TextField(
                        value = roomPrice,
                        onValueChange = { roomPrice = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(53.dp),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color(0xFFcecece),
                            unfocusedIndicatorColor = Color(0xFFcecece),
                            focusedPlaceholderColor = Color.Black,
                            unfocusedPlaceholderColor = Color.Gray,
                            unfocusedContainerColor = Color(0xFFf7f7f7),
                            focusedContainerColor = Color.White
                        ),
                        placeholder = {
                            Text(
                                text = "Nhập giá phòng",
                                fontSize = 13.sp,
                                color = Color(0xFF898888)
                            )
                        },
                        shape = RoundedCornerShape(size = 8.dp),
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontFamily = FontFamily(Font(R.font.cairo_regular))
                        )
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                ) {
                    Text(text = "Trạng thái *", color = Color(0xFF7c7b7b), fontSize = 13.sp)
                    TextField(
                        value = Status,
                        onValueChange = { Status = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(53.dp),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color(0xFFcecece),
                            unfocusedIndicatorColor = Color(0xFFcecece),
                            focusedPlaceholderColor = Color.Black,
                            unfocusedPlaceholderColor = Color.Gray,
                            unfocusedContainerColor = Color(0xFFf7f7f7),
                            focusedContainerColor = Color.White
                        ),
                        placeholder = {
                            Text(
                                text = "Nhập trạng thái",
                                fontSize = 13.sp,
                                color = Color(0xFF898888)
                            )
                        },
                        shape = RoundedCornerShape(size = 8.dp),
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontFamily = FontFamily(Font(R.font.cairo_regular))
                        )
                    )
                }


                // Tiện nghi
                Spacer(modifier = Modifier.height(3.dp))
                Column {
                    ComfortableLabel()
                    ComfortableOptions(
                        selectedComfortable = selectedComfortable,
                        onComfortableSelected = { comfortable ->
                            selectedComfortable = if (selectedComfortable.contains(comfortable)) {
                                selectedComfortable - comfortable
                            } else {
                                selectedComfortable + comfortable
                            }
                        })
                }


                // Dịch vụ
                Spacer(modifier = Modifier.height(10.dp))
                Column {
                    ServiceLabel()
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


        // Nút thêm phòng
        // Nút thêm phòng
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(screenHeight.dp / 7f)
                .background(color = Color.White)
        ) {
            Box(modifier = Modifier.padding(20.dp)) {
                Button(
                    onClick = {
                        if (!isLoading) {  // Prevent multiple clicks while loading
                            // Validate input fields
                            if (postTitle.isBlank() || numberOfRoommates.isBlank() ||
                                currentPeopleCount.isBlank() || area.isBlank() ||
                                roomPrice.isBlank() || Status.isBlank()
                            ) {
                                errorMessage = "Vui lòng điền đầy đủ thông tin."
                                return@Button
                            }

                            // Kiểm tra giới hạn người
                            val limitPerson = currentPeopleCount.toIntOrNull()
                            if (limitPerson == null) {
                                errorMessage = "Giới hạn người phải là số nguyên."
                                return@Button
                            }

                            // Kiểm tra diện tích
                            val roomArea = area.toDoubleOrNull()
                            if (roomArea == null) {
                                errorMessage = "Diện tích phải là số."
                                return@Button
                            }

                            // Kiểm tra giá phòng
                            val roomPriceValue = roomPrice.toDoubleOrNull()
                            if (roomPriceValue == null) {
                                errorMessage = "Giá phòng phải là số."
                                return@Button
                            }

                            // Kiểm tra trạng thái
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
                                    limit_person = limitPerson
                                )
                                Log.d("TAG", "dịch vụ : ${selectedService}")
                                Log.d("TAG", "tiện nghi  : ${selectedComfortable}")
                                Log.d("TAG", "Người  : ${currentPeopleCount}")
                            }
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

            // Loading overlay (optional)
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
