@file:Suppress("PackageDirectoryMismatch")


package com.rentify.user.app.view.userScreens.homeScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.MainActivity
import com.rentify.user.app.R


data class TypeProduct(val type: String, val icon: Int)


@Composable
fun SearchComponent(navController: NavHostController) {
    LayoutSearch(navController = rememberNavController(), onCitySelected = {})
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LayoutSearch(navController: NavHostController,onCitySelected: (String) -> Unit) {
    var searchText by remember { mutableStateOf(TextFieldValue("")) }
    val listTypeProduct = listOf(
        TypeProduct("Săn phòng giảm giá", R.drawable.sanphong),
        TypeProduct("Tìm phòng xung quanh", R.drawable.map),
        TypeProduct("Tin đăng cho thuê", R.drawable.tindang),
        TypeProduct("Tin đăng tìm phòng", R.drawable.iconhomefindroom),
        TypeProduct("Tìm người ở ghép", R.drawable.timnguoi),
        TypeProduct("Vận chuyển", R.drawable.vanchuyen)
    )

    val latitude = 0.0
    val longitude = 0.0

    var statusType by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }
    var isBottomSheetVisible by remember { mutableStateOf(false) }
    var selectedCity by remember { mutableStateOf("Hà Nội") }

    if (isBottomSheetVisible) {
        ModalBottomSheet(
            onDismissRequest = { isBottomSheetVisible = false },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            dragHandle = {}
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Chọn Thành Phố",
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Divider(color = Color.Gray.copy(alpha = 0.5f), thickness = 1.dp)
                listOf("Hà Nội", "Hồ Chí Minh", "Đà Nẵng").forEach { city ->
                    Text(
                        text = city,
                        style = TextStyle(fontSize = 16.sp, color = Color.Black),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedCity = city
                                onCitySelected(city)
                                isBottomSheetVisible = false
                            }
                            .padding(vertical = 12.dp, horizontal = 16.dp)
                    )
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .padding(9.dp)
            .shadow(
                elevation = 5.dp, shape = RoundedCornerShape(20.dp)
            )
            .background(color = Color.White, shape = RoundedCornerShape(20.dp))
            .padding(16.dp)
    ) {
        // Thanh tìm kiếm với icon và chữ "Hà Nội"
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color(0xFFF5F5F5), RoundedCornerShape(16.dp))
        ) {
            // Vùng chứa biểu tượng và chữ "Hà Nội"
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(Color(0xFFD2F1FF), RoundedCornerShape(16.dp))
                    .padding(horizontal = 25.dp)
                    .height(50.dp)
                    .clickable {
                        isBottomSheetVisible = true // Hiển thị Bottom Sheet
                    }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.iconhomeaddress), // Icon vị trí
                    contentDescription = "Location Icon", modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = selectedCity, fontSize = 14.sp, color = Color(0xFF1E88E5)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))

            // TextField tìm kiếm
            BasicTextField(value = searchText.text,
                onValueChange = { searchText = TextFieldValue(it) }, // Cập nhật giá trị text
                decorationBox = { innerTextField ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Transparent) // Màu nền
                            .padding(horizontal = 8.dp, vertical = 8.dp) // Khoảng cách xung quanh
                    ) {
                        if (searchText.text.isEmpty() && !isFocused) { // Hiển thị placeholder nếu chưa focus
                            Text(
                                text = "Tìm kiếm phòng", color = Color.Gray, // Màu chữ placeholder
                                style = TextStyle(fontSize = 14.sp) // Kiểu chữ placeholder
                            )
                        }
                        innerTextField() // Vùng nhập văn bản
                    }
                },
                textStyle = TextStyle(
                    fontSize = 14.sp, color = Color.Black
                ), // Kiểu chữ của văn bản
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focusState -> // Lắng nghe trạng thái focus
                        isFocused = focusState.isFocused
                    })
        }

        // Thanh cuộn ngang với các loại sản phẩm
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(vertical = 3.dp, horizontal = 0.dp)
        ) {
            listTypeProduct.forEach { type ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .width(80.dp)
                        .padding(top = 5.dp)
                        .clickable {
                            statusType = type.type
                            if (type.type == "Săn phòng giảm giá") {
                                navController.navigate("SaleRoomScreen") // Điều hướng đến TogetherScreen
                            } else {
                                statusType = type.type
                            }

                            if (type.type == "Tìm người ở ghép") {
                                navController.navigate("Search_roommate") // Điều hướng đến TogetherScreen
                            } else {
                                statusType = type.type
                            }

                            if (type.type == "Tin đăng cho thuê") {
                                navController.navigate("RENTAL_POST") // Điều hướng đến TogetherScreen
                            } else {
                                statusType = type.type
                            }

                            if (type.type == "Tin đăng tìm phòng") {
                                navController.navigate("Search_room") // Điều hướng đến TogetherScreen
                            } else {
                                statusType = type.type
                            }

                            if (type.type == "Tìm phòng xung quanh") {
                                navController.navigate(MainActivity.ROUTER.ROOMMAP.name+"/$latitude,$longitude") // Điều hướng đến TogetherScreen
                            } else {
                                statusType = type.type
                            }
                        },
                ) {
                    Image(
                        painter = painterResource(id = type.icon),
                        contentDescription = "",
                        modifier = Modifier.size(40.dp),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = type.type,
                        textAlign = TextAlign.Center,
                        fontSize = 12.sp,
                        color = if (statusType == type.type) Color(0xff84d8ff) else Color.Black,
                        maxLines = 2,
                        softWrap = true,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun PreviewSearchComponent() {
    SearchComponent(navController = rememberNavController())
}