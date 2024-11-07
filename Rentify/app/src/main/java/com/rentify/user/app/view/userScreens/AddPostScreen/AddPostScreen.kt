package com.rentify.user.app.view.userScreens.AddPostScreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.rentify.user.app.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.flowlayout.FlowRow



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPostScreen(navController: NavHostController) {
    var selectedRoomTypes by remember { mutableStateOf(setOf<String>()) }
    var selectedComfortable by remember { mutableStateOf(setOf<String>()) }
    var selectedService by remember { mutableStateOf(setOf<String>()) }
    var expanded by remember { mutableStateOf(false) }
    var selectedGender by remember { mutableStateOf("Nhập giới tính") }
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
    Column(
        modifier = Modifier
            .fillMaxSize()

            .background(color = Color(0xfff7f7f7))

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xffffffff))
                .padding(10.dp)

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()

                    .background(color = Color(0xffffffff)), // Để IconButton nằm bên trái
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { /*TODO*/ }) {
                    Image(
                        painter = painterResource(id = R.drawable.back),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp, 30.dp)
                    )

                }

                Text(
                    text = "Thêm bài đăng",
                    //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                    color = Color.Black,
                    fontWeight = FontWeight(700),
                    fontSize = 17.sp,

                    )
                IconButton(onClick = { /*TODO*/ }) {


                }
            }
        }
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
                    text = "Tiêu đề bài đăng *", color = Color(0xFF7c7b7b), fontSize = 13.sp
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
                            text = "Nhập tiêu đề bài đăng",
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
            Spacer(modifier = Modifier.height(3.dp))
            Row(
                modifier = Modifier
                    .border(
                        width = 2.dp, color = Color(0xFFeeeeee), shape = RoundedCornerShape(9.dp)
                    )
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {

                Image(
                    painter = painterResource(id = R.drawable.roomtype),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp, 30.dp)
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = "Loại phòng",
                    //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                    color = Color.Black,
                    // fontWeight = FontWeight(700),
                    fontSize = 13.sp,

                    )
            }
            Row(
                modifier = Modifier.padding(5.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                RoomTypeOption(text = "Phòng trọ",
                    isSelected = selectedRoomTypes.contains("Phòng trọ"),
                    onClick = {
                        selectedRoomTypes = if (selectedRoomTypes.contains("Phòng trọ")) {
                            selectedRoomTypes - "Phòng trọ" // Tạo bản sao mới khi loại bỏ phần tử
                        } else {
                            selectedRoomTypes + "Phòng trọ" // Tạo bản sao mới khi thêm phần tử
                        }
                    })

                Spacer(modifier = Modifier.width(10.dp))

                RoomTypeOption(text = "Nguyên căn",
                    isSelected = selectedRoomTypes.contains("Nguyên căn"),
                    onClick = {
                        selectedRoomTypes = if (selectedRoomTypes.contains("Nguyên căn")) {
                            selectedRoomTypes - "Nguyên căn"
                        } else {
                            selectedRoomTypes + "Nguyên căn"
                        }
                    })

                Spacer(modifier = Modifier.width(10.dp))

                RoomTypeOption(text = "Chung cư",
                    isSelected = selectedRoomTypes.contains("Chung cư"),
                    onClick = {
                        selectedRoomTypes = if (selectedRoomTypes.contains("Chung cư")) {
                            selectedRoomTypes - "Chung cư"
                        } else {
                            selectedRoomTypes + "Chung cư"
                        }
                    })
            }
            //ảnh
            Row(
                modifier = Modifier.padding(5.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    modifier = Modifier

                        .shadow(6.dp, shape = RoundedCornerShape(10.dp))
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
                    .shadow(6.dp, shape = RoundedCornerShape(10.dp))
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
            // tầnng
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                Text(
                    text = "Tầng *",
                    //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                    color = Color(0xFF7c7b7b),
                    //  fontWeight = FontWeight(700),
                    fontSize = 13.sp,

                    )
                TextField(
                    value = floor,
                    onValueChange = { floor = it },
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
                            text = "Nhập số tầng",
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
            //siis đ thoại
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                Text(
                    text = "Số điện thoại *",
                    //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                    color = Color(0xFF7c7b7b),
                    //  fontWeight = FontWeight(700),
                    fontSize = 13.sp,

                    )
                TextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
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
                            text = "Nhập số điện thoại",
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
            // giới tính
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                Text(
                    text = "Giới tính *", color = Color(0xFF7c7b7b), fontSize = 13.sp
                )
                ExposedDropdownMenuBox(expanded = expanded,
                    onExpandedChange = { expanded = !expanded }) {
                    TextField(
                        value = selectedGender,
                        onValueChange = { /* Không cần thay đổi giá trị ở đây */ },
                        readOnly = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(53.dp)
                            .menuAnchor(),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color(0xFFcecece),
                            focusedPlaceholderColor = Color.Black,
                            unfocusedPlaceholderColor = Color.Gray,
                            unfocusedContainerColor = Color(0xFFf7f7f7),
                            focusedContainerColor = Color.White
                        ),
                        placeholder = {
                            Text(
                                text = "Nhập giới tính",
                                fontSize = 13.sp,
                                color = Color(0xFFcecece),
                                fontFamily = FontFamily(Font(R.font.cairo_regular))
                            )
                        },
                        shape = RoundedCornerShape(size = 8.dp),
                        trailingIcon = {
                            Icon(imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = null,
                                Modifier.clickable { expanded = !expanded })
                        },
                        textStyle = TextStyle(
                            color = Color.Black, fontFamily = FontFamily(Font(R.font.cairo_regular))
                        )
                    )
                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        genderOptions.forEach { gender ->
                            DropdownMenuItem(text = { Text(gender) }, onClick = {
                                selectedGender = gender
                                expanded = false
                            })
                        }
                    }
                }
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
            Row(
                modifier = Modifier
                    .border(
                        width = 2.dp, color = Color(0xFFeeeeee), shape = RoundedCornerShape(9.dp)
                    )
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.comfortable),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp, 30.dp)
                )
                Text(
                    text = "Tiện nghi",
                    //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                    color = Color.Black,
                    // fontWeight = FontWeight(700),
                    fontSize = 13.sp,
                )
            }
            FlowRow(
                modifier = Modifier.padding(5.dp),
                mainAxisSpacing = 10.dp, // Khoảng cách giữa các phần tử trên cùng một hàng
                crossAxisSpacing = 10.dp // Khoảng cách giữa các hàng
            ) {
                ComfortableOption(text = "Vẹ sinh khép kín",
                    isSelected = selectedComfortable.contains("Vẹ sinh khép kín"),
                    onClick = {
                        selectedComfortable =
                            if (selectedComfortable.contains("Vẹ sinh khép kín")) {
                                selectedComfortable - "Vẹ sinh khép kín" // Tạo bản sao mới khi loại bỏ phần tử
                            } else {
                                selectedComfortable + "Vẹ sinh khép kín" // Tạo bản sao mới khi thêm phần tử
                            }
                    })
                ComfortableOption(text = "Gác xép",
                    isSelected = selectedComfortable.contains("Gác xép"),
                    onClick = {
                        selectedComfortable = if (selectedComfortable.contains("Gác xép")) {
                            selectedComfortable - "Gác xép"
                        } else {
                            selectedComfortable + "Gác xép"
                        }
                    })
                ComfortableOption(text = "Ra vào vân tay",
                    isSelected = selectedComfortable.contains("Ra vào vân tay"),
                    onClick = {
                        selectedComfortable = if (selectedComfortable.contains("Ra vào vân tay")) {
                            selectedComfortable - "Ra vào vân tay"
                        } else {
                            selectedComfortable + "Ra vào vân tay"
                        }
                    })
                ComfortableOption(text = "Ban công",
                    isSelected = selectedComfortable.contains("Ban công"),
                    onClick = {
                        selectedComfortable = if (selectedComfortable.contains("Ban công")) {
                            selectedComfortable - "Ban công"
                        } else {
                            selectedComfortable + "Ban công"
                        }
                    })
                ComfortableOption(text = "Nuôi pet",
                    isSelected = selectedComfortable.contains("Nuôi pet"),
                    onClick = {
                        selectedComfortable = if (selectedComfortable.contains("Nuôi pet")) {
                            selectedComfortable - "Nuôi pet"
                        } else {
                            selectedComfortable + "Nuôi pet"
                        }
                    })
                ComfortableOption(text = "Không chung chủ",
                    isSelected = selectedComfortable.contains("Không chung chủ"),
                    onClick = {
                        selectedComfortable = if (selectedComfortable.contains("Không chung chủ")) {
                            selectedComfortable - "Không chung chủ"
                        } else {
                            selectedComfortable + "Không chung chủ"
                        }
                    })
            }
            // dịch vụ
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .border(
                        width = 2.dp, color = Color(0xFFeeeeee), shape = RoundedCornerShape(9.dp)
                    )
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.service),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp, 30.dp)
                )
                Text(
                    text = "Dịch vụ",
                    //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                    color = Color.Black,
                    // fontWeight = FontWeight(700),
                    fontSize = 13.sp,
                )
            }
            FlowRow(
                modifier = Modifier.padding(5.dp),
                mainAxisSpacing = 10.dp, // Khoảng cách giữa các phần tử trên cùng một hàng
                crossAxisSpacing = 10.dp // Khoảng cách giữa các hàng
            ) {
                ServiceOption(text = "Điều hoà",
                    isSelected = selectedService.contains("Điều hoà"),
                    onClick = {
                        selectedService = if (selectedService.contains("Điều hoà")) {
                            selectedService - "Điều hoà" // Tạo bản sao mới khi loại bỏ phần tử
                        } else {
                            selectedService + "Điều hoà" // Tạo bản sao mới khi thêm phần tử
                        }
                    })
                ServiceOption(text = "Kệ bếp",
                    isSelected = selectedService.contains("Kệ bếp"),
                    onClick = {
                        selectedService = if (selectedService.contains("Kệ bếp")) {
                            selectedService - "Kệ bếp"
                        } else {
                            selectedService + "Kệ bếp"
                        }
                    })
                ServiceOption(text = "Tủ lạnh",
                    isSelected = selectedService.contains("Tủ lạnh"),
                    onClick = {
                        selectedService = if (selectedService.contains("Tủ lạnh")) {
                            selectedService - "Tủ lạnh"
                        } else {
                            selectedService + "Tủ lạnh"
                        }
                    })
                ServiceOption(text = "Bình nóng lạnh",
                    isSelected = selectedService.contains("Bình nóng lạnh"),
                    onClick = {
                        selectedService = if (selectedService.contains("Bình nóng lạnh")) {
                            selectedService - "Bình nóng lạnh"
                        } else {
                            selectedService + "Bình nóng lạnh"
                        }
                    })
                ServiceOption(text = "Máy giặt",
                    isSelected = selectedService.contains("Máy giặt"),
                    onClick = {
                        selectedService = if (selectedService.contains("Máy giặt")) {
                            selectedService - "Máy giặt"
                        } else {
                            selectedService + "Máy giặt"
                        }
                    })
                ServiceOption(text = "Bàn ghế",
                    isSelected = selectedService.contains("Bàn ghế"),
                    onClick = {
                        selectedService = if (selectedService.contains("Bàn ghế")) {
                            selectedService - "Bàn ghế"
                        } else {
                            selectedService + "Bàn ghế"
                        }
                    })
            }
            Button(
                onClick = {}, modifier = Modifier.fillMaxWidth(),
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

@Composable
fun RoomTypeOption(
    text: String, isSelected: Boolean, onClick: () -> Unit
) {
    Row(modifier = Modifier
        .clickable(onClick = onClick, indication = null, // Bỏ hiệu ứng tối khi nhấn
            interactionSource = remember { MutableInteractionSource() })
        .shadow(10.dp, shape = RoundedCornerShape(10.dp))
        .border(
            width = 2.dp,
            color = if (isSelected) Color(0xFF44acfe) else Color(0xFFeeeeee), // Change border color if selected
            shape = RoundedCornerShape(9.dp)
        ), verticalAlignment = Alignment.CenterVertically) {
        Text(
            modifier = Modifier
                .background(color = Color(0xFFeeeeee))
                .padding(7.dp),
            text = text,
            color = if (isSelected) Color(0xFF44acfe) else Color(0xFF000000),
            fontSize = 13.sp
        )
    }
}

@Composable
fun ComfortableOption(
    text: String, isSelected: Boolean, onClick: () -> Unit
) {
    Row(modifier = Modifier
        .clickable(onClick = onClick, indication = null, // Bỏ hiệu ứng tối khi nhấn
            interactionSource = remember { MutableInteractionSource() })
        .shadow(10.dp, shape = RoundedCornerShape(10.dp))
        .border(
            width = 2.dp,
            color = if (isSelected) Color(0xFF44acfe) else Color(0xFFeeeeee), // Change border color if selected
            shape = RoundedCornerShape(9.dp)
        ), verticalAlignment = Alignment.CenterVertically) {
        Text(
            modifier = Modifier
                .background(color = Color(0xFFeeeeee))
                .padding(7.dp),
            text = text,
            color = if (isSelected) Color(0xFF44acfe) else Color(0xFF000000),
            fontSize = 13.sp
        )
    }
}

@Composable
fun ServiceOption(
    text: String, isSelected: Boolean, onClick: () -> Unit
) {
    Row(modifier = Modifier
        .clickable(onClick = onClick, indication = null, // Bỏ hiệu ứng tối khi nhấn
            interactionSource = remember { MutableInteractionSource() })
        .shadow(10.dp, shape = RoundedCornerShape(10.dp))
        .border(
            width = 2.dp,
            color = if (isSelected) Color(0xFF44acfe) else Color(0xFFeeeeee), // Change border color if selected
            shape = RoundedCornerShape(9.dp)
        ), verticalAlignment = Alignment.CenterVertically) {
        Text(
            modifier = Modifier
                .background(color = Color(0xFFeeeeee))
                .padding(7.dp),
            text = text,
            color = if (isSelected) Color(0xFF44acfe) else Color(0xFF000000),
            fontSize = 13.sp
        )
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingLayoutAddPostScreen() {
    AddPostScreen(navController = rememberNavController())
}