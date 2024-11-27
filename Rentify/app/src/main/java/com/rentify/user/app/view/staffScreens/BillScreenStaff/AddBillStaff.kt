package com.rentify.user.app.view.staffScreens.BillScreenStaff

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.MainActivity.ROUTER
import com.rentify.user.app.ui.theme.ColorBlack
import com.rentify.user.app.ui.theme.calender
import com.rentify.user.app.ui.theme.colorHeaderSearch
import com.rentify.user.app.utils.CheckUnit
import com.rentify.user.app.view.auth.components.HeaderComponent
import com.rentify.user.app.view.staffScreens.BillScreenStaff.Componenet.ShowService
import com.rentify.user.app.view.staffScreens.BillScreenStaff.Componenet.TextFieldSmall
import com.rentify.user.app.view.staffScreens.BillScreenStaff.Componenet.TextFiledComponent

@Composable
fun AddBillStaff(navController: NavController) {

    val fakeList = fakeList
    val totalAmount = fakeList.sumOf { it.price }
    val formatPrice = CheckUnit.formattedPrice(totalAmount.toFloat())
    var building by remember { mutableStateOf("") }
    var room by remember { mutableStateOf("") }
    var priceRoom by remember { mutableStateOf("") }
    var dateBill by remember { mutableStateOf("") }
    var electricNumber by remember { mutableStateOf("") }
    var waterNumber by remember { mutableStateOf("") }
    var isFocusBuilding by remember { mutableStateOf(false) }
    var isFocusRoom by remember { mutableStateOf(false) }
    var isFocusPriceRoom by remember { mutableStateOf(false) }
    var isFocusDateBill by remember { mutableStateOf(false) }
    var isFocusElectricNumber by remember { mutableStateOf(false) }
    var isFocusWaterNumber by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .background(color = Color.White)
            .verticalScroll(state = rememberScrollState())
            .padding(bottom = 20.dp)
    ) {
        HeaderComponent(
            title = "Thêm hóa đơn",
            backgroundColor = Color.White,
            navController = navController
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            //toa nha
            TextFiledComponent(
                value = building,
                onValueChange = {},
                onClick = {},
                placeHolder = "Tòa nhà",
                isFocused = remember { mutableStateOf(isFocusBuilding) },
                isShowIcon = true,
                isIcon = Icons.Filled.KeyboardArrowDown
            )

            //phong
            TextFiledComponent(
                value = room,
                onValueChange = {},
                onClick = {},
                placeHolder = "Phòng",
                isFocused = remember { mutableStateOf(isFocusRoom) },
                isShowIcon = true,
                isIcon = Icons.Filled.KeyboardArrowDown
            )

            //tien phong
            TextFiledComponent(
                value = priceRoom,
                onValueChange = { newText ->
                    priceRoom = newText
                },
                placeHolder = "Tiền phòng",
                isFocused = remember { mutableStateOf(isFocusPriceRoom) },
                isShowIcon = false,
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
                    TextFieldSmall(
                        value = electricNumber,
                        onValueChange = { newText ->
                            electricNumber = newText
                        },
                        placeHolder = "Số điện cũ",
                        isFocused = remember { mutableStateOf(isFocusElectricNumber) }
                    )

                    TextFieldSmall(
                        value = electricNumber,
                        onValueChange = { newText ->
                            electricNumber = newText
                        },
                        placeHolder = "Số điện mới",
                        isFocused = remember { mutableStateOf(isFocusElectricNumber) }
                    )

                }
                //hang 2
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextFieldSmall(
                        value = electricNumber,
                        onValueChange = { newText ->
                            electricNumber = newText
                        },
                        placeHolder = "Tiêu thụ",
                        isFocused = remember { mutableStateOf(isFocusElectricNumber) }
                    )

                    TextFieldSmall(
                        value = electricNumber,
                        onValueChange = { newText ->
                            electricNumber = newText
                        },
                        placeHolder = "Tổng tiền",
                        isFocused = remember { mutableStateOf(isFocusElectricNumber) }
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
                    TextFieldSmall(
                        value = waterNumber,
                        onValueChange = { newText ->
                            waterNumber = newText
                        },
                        placeHolder = "Số nước cũ",
                        isFocused = remember { mutableStateOf(isFocusWaterNumber) }
                    )

                    TextFieldSmall(
                        value = waterNumber,
                        onValueChange = { newText ->
                            waterNumber = newText
                        },
                        placeHolder = "Số nước mới",
                        isFocused = remember { mutableStateOf(isFocusWaterNumber) }
                    )

                }
                //hang 2
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextFieldSmall(
                        value = electricNumber,
                        onValueChange = { newText ->
                            electricNumber = newText
                        },
                        placeHolder = "Tiêu thụ",
                        isFocused = remember { mutableStateOf(isFocusWaterNumber) }
                    )

                    TextFieldSmall(
                        value = electricNumber,
                        onValueChange = { newText ->
                            electricNumber = newText
                        },
                        placeHolder = "Tổng tiền",
                        isFocused = remember { mutableStateOf(isFocusWaterNumber) }
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
                ){
                    fakeList.forEach {
                            item ->
                        ShowService(item)
                    }
                    Spacer(modifier = Modifier.padding(top = 10.dp))
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
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

            // ngay chot
            Spacer(modifier = Modifier.padding(top = 20.dp))
            TextFiledComponent(
                value = dateBill,
                onValueChange = { newText ->
                    dateBill = newText
                },
                placeHolder = "Ngày chốt",
                isFocused = remember { mutableStateOf(isFocusDateBill)},
                isShowIcon = true,
                isIcon = calender
            )

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
                   modifier = Modifier.fillMaxSize().padding(10.dp),
                   verticalAlignment = Alignment.CenterVertically,
                   horizontalArrangement = Arrangement.SpaceBetween
               ){
                   Text(
                       text = "Tổng hóa đơn: ",
                       fontSize = 16.sp,
                       fontWeight = FontWeight.Bold,
                       color = ColorBlack,
                   )

                   Text(
                       text = "3000000 VND ",
                       fontSize = 16.sp,
                       fontWeight = FontWeight.Bold,
                       color = Color.Red,
                   )
               }
            }

            //button
            Spacer(modifier = Modifier.padding(top = 20.dp))
            Button(
                onClick = {navController.navigate(ROUTER.ADDBILL_STAFF.name)},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorHeaderSearch
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Xác nhận",
                    color = Color.White,
                    modifier = Modifier.padding(vertical = 4.dp),
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAddBill() {
    AddBillStaff(navController = rememberNavController())
}