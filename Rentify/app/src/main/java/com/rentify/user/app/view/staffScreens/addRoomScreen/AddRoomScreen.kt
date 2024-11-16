package com.rentify.user.app.view.staffScreens.addRoomScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border

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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.rentify.user.app.R
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.view.auth.components.HeaderComponent

import com.rentify.user.app.view.staffScreens.addRoomScreen.Components.ComfortableLabel
import com.rentify.user.app.view.staffScreens.addRoomScreen.Components.ComfortableOptions
import com.rentify.user.app.view.staffScreens.addRoomScreen.Components.RoomTypeLabel
import com.rentify.user.app.view.staffScreens.addRoomScreen.Components.RoomTypeOptions
import com.rentify.user.app.view.staffScreens.addRoomScreen.Components.ServiceLabel
import com.rentify.user.app.view.staffScreens.addRoomScreen.Components.ServiceOptions


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRoomScreen(navController: NavHostController) {

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    var selectedRoomTypes by remember { mutableStateOf(listOf<String>()) }
    var selectedComfortable by remember { mutableStateOf(listOf<String>()) }
    var selectedService by remember { mutableStateOf(listOf<String>()) }
    var expanded by remember { mutableStateOf(false) }
    var selectedGender by remember { mutableStateOf("") }
    val genderOptions = listOf("Nam", "Nữ", "Giới tính thứ 3")
    val scrollState = rememberScrollState()
    var postTitle by remember { mutableStateOf("") }
    var numberOfRoommates by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var currentPeopleCount by remember { mutableStateOf("") }
    var area by remember { mutableStateOf("") }
    var floor by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var roomPrice by remember { mutableStateOf("") }
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


            HeaderComponent(backgroundColor = Color(0xffffffff) , title ="Thêm phòng", navController =navController )
        Spacer(modifier = Modifier.height(10.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .background(color = Color(0xfff7f7f7))
                .padding(15.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                Text(
                    text = "Tên tòa nhà*", color = Color(0xFF7c7b7b), fontSize = 13.sp
                )
                TextField(
                    value = postTitle,
                    onValueChange = { newText -> postTitle = newText },
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
                            text = "Nhập tên tòa nhà ",
                            fontSize = 14.sp,
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

// TextField cho số người tìm ghép
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                Text(
                    text = "Số người tìm ghép *", color = Color(0xFF7c7b7b), fontSize = 13.sp
                )
                TextField(
                    value = numberOfRoommates,
                    onValueChange = { newText -> numberOfRoommates = newText },
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
                            text = "Nhập số người ở ghép",
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
            // loai phòng
            Column {
                RoomTypeLabel()

                RoomTypeOptions(
                    selectedRoomTypes = selectedRoomTypes,
                    onRoomTypeSelected = { roomType ->
                        selectedRoomTypes = if (selectedRoomTypes.contains(roomType)) {
                            selectedRoomTypes - roomType
                        } else {
                            selectedRoomTypes + roomType
                        }
                    }
                )
            }
            //ảnh thêm ảnh từ máy
            Row(
                modifier = Modifier.padding(5.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    modifier = Modifier

                        .shadow(3.dp, shape = RoundedCornerShape(10.dp))
                        .background(color = Color(0xFFffffff))
                        .border(
                            width = 0.dp,
                            color = Color(0xFFEEEEEE),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(25.dp),

                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.image),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp, 30.dp)
                    )
                }
                Spacer(modifier = Modifier.width(15.dp))
                Column {
                    Text(
                        text = "Ảnh Phòng trọ",
                        //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                        color = Color.Black,
                        // fontWeight = FontWeight(700),
                        fontSize = 14.sp,

                        )

                    Text(
                        text = "Tối đa 10 ảnh",

                        //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                        color = Color(0xFFBFBFBF),
                        // fontWeight = FontWeight(700),
                        fontSize = 13.sp,

                        )
                }


            }
//video
            Spacer(modifier = Modifier.height(17.dp))
            Column(
                modifier = Modifier
                    .fillMaxHeight(0.6f)
                    .fillMaxWidth()
                    .shadow(3.dp, shape = RoundedCornerShape(10.dp))
                    .background(color = Color(0xFFffffff))
                    .border(
                        width = 0.dp, color = Color(0xFFEEEEEE), shape = RoundedCornerShape(10.dp)
                    )
                    .padding(25.dp),

                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.video),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp, 30.dp)
                )
                Spacer(modifier = Modifier.height(7.dp))
                Text(

                    text = "Video",
                    //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                    color = Color.Black,
                    // fontWeight = FontWeight(700),
                    fontSize = 13.sp,

                    )
            }
            // dịa chỉ
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                Text(
                    text = "Địa chỉ *",
                    //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                    color = Color(0xFF7c7b7b),
                    //  fontWeight = FontWeight(700),
                    fontSize = 13.sp,

                    )
                TextField(
                    value = address,
                    onValueChange = { address = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(53.dp),
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
                            text = "Nhập địa chỉ *",
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
// số người hiện tại
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                Text(
                    text = "số người hiện tại *",
                    //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                    color = Color(0xFF7c7b7b),
                    //  fontWeight = FontWeight(700),
                    fontSize = 13.sp,

                    )
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
                            text = "Nhập số người hiện tại",
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
//  diền tích
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                Text(
                    text = "Diện tích(m2) *",
                    //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                    color = Color(0xFF7c7b7b),
                    //  fontWeight = FontWeight(700),
                    fontSize = 13.sp,

                    )
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
                        color = Color.Black, fontFamily = FontFamily(Font(R.font.cairo_regular))
                    )
                )
            }

            // giá phòng
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                Text(
                    text = "Giá phòng *",
                    //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                    color = Color(0xFF7c7b7b),
                    //  fontWeight = FontWeight(700),
                    fontSize = 13.sp,
                )
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
                            color = Color(0xFF898888),
                            //   fontFamily = FontFamily(Font(R.font.cairo_regular))
                        )
                    },
                    shape = RoundedCornerShape(size = 8.dp),
                    textStyle = TextStyle(
                        color = Color.Black, fontFamily = FontFamily(Font(R.font.cairo_regular))
                    )
                )
            }
            //  Tiện nghi
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
                    }
                )
            }
            // dịch vụ
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
                }
            )
        }

        }
    }
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
                        navController.navigate("RoomDetailScreen")
                    }, modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth(),
                    //  .background(Color(0xffFE724C), RoundedCornerShape(25.dp)), // Bo tròn 12.dp
                    shape = RoundedCornerShape(10.dp), colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xff5dadff)
                    )
                ) {
                    Text(
                        text = "Thêm bài đăng",
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.cairo_regular)),
                        color = Color(0xffffffff)
                    )
                }

            }
        }

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingLayoutAddRoomScreen() {
    AddRoomScreen(navController = rememberNavController())
}