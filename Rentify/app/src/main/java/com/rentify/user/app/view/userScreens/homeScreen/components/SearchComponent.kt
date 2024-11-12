@file:Suppress("PackageDirectoryMismatch")


package com.rentify.user.app.view.userScreens.homeScreen.components
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.rentify.user.app.R


data class TypeProduct(val type: String, val icon: Int)


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun SearchComponent() {
    LayoutSearch()
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LayoutSearch() {
    var searchText by remember { mutableStateOf(TextFieldValue("")) }
    val listTypeProduct = listOf(
        TypeProduct("Săn phòng giảm giá", R.drawable.sanphong),
        TypeProduct("Tìm phòng xung quanh", R.drawable.map),
        TypeProduct("Tin đăng cho thuê", R.drawable.tindang),
        TypeProduct("Tìm người ở ghép", R.drawable.timnguoi),
        TypeProduct("Vận chuyển", R.drawable.vanchuyen)
    )
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp

    var statusType by remember { mutableStateOf(listTypeProduct.first().type) }
    Column(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
            .shadow(
                elevation = 9.dp,
                shape = RoundedCornerShape(20.dp)
            )
            .background(color = Color.White, shape = RoundedCornerShape(20.dp))
            .padding(16.dp)) {
        // Thanh tìm kiếm với icon và chữ "Hà Nội"
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF5F5F5), RoundedCornerShape(8.dp))
                .padding(horizontal = 12.dp, vertical = 4.dp)
        ) {
            // Vùng chứa biểu tượng và chữ "Hà Nội"
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(Color(0xFFB3E5FC), RoundedCornerShape(16.dp))
                    .padding(horizontal = 10.dp, vertical = 15.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.map), // Icon vị trí
                    contentDescription = "Location Icon",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Hà Nội",
                    fontSize = 14.sp,
                    color = Color(0xFF1E88E5)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))


            // TextField tìm kiếm
            TextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = { Text("Tìm kiếm tin đăng", color = Color.Gray) },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }


        // Thanh cuộn ngang với các loại sản phẩm
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(vertical = 16.dp, horizontal = 8.dp)
        ) {
            listTypeProduct.forEach { type ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .width(80.dp)
                ) {
                    IconButton(
                        onClick = { statusType = type.type },
                        modifier = Modifier
                            .background(
                                color = if (statusType == type.type) Color.White else Color.White,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp)
                    ) {
                        Image(
                            painter = painterResource(id = type.icon),
                            contentDescription = null,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                    Text(
                        text = type.type,
                        textAlign = TextAlign.Center,
                        fontSize = 12.sp,
                        color = if (statusType == type.type) Color(0xff84d8ff) else Color.Black,
                        maxLines = 2,
                        softWrap = true
                    )
                }
            }
        }
    }
}

