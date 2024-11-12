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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.R


data class TypeProduct(val type: String, val icon: Int)


@Composable
fun SearchComponent(navController: NavHostController) {
    LayoutSearch(navController = rememberNavController())
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LayoutSearch(navController: NavHostController) {
    var searchText by remember { mutableStateOf(TextFieldValue("")) }
    val listTypeProduct = listOf(
        TypeProduct("Săn phòng giảm giá", R.drawable.sanphong),
        TypeProduct("Tìm phòng xung quanh", R.drawable.map),
        TypeProduct("Tin đăng cho thuê", R.drawable.tindang),
        TypeProduct("Tìm người ở ghép", R.drawable.timnguoi),
        TypeProduct("Vận chuyển", R.drawable.vanchuyen)
    )


    var statusType by remember { mutableStateOf(listTypeProduct.first().type) }


    Column(
        modifier = Modifier
            .padding(9.dp)
            .shadow(
                elevation = 9.dp,
                shape = RoundedCornerShape(20.dp)
            )
            .background(color = Color.White, shape = RoundedCornerShape(20.dp))
            .padding(16.dp)
    ) {
        // Thanh tìm kiếm với icon và chữ "Hà Nội"
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF5F5F5), RoundedCornerShape(16.dp))
        ) {
            // Vùng chứa biểu tượng và chữ "Hà Nội"
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(Color(0xFFB3E5FC), RoundedCornerShape(16.dp))
                    .padding(horizontal = 25.dp, vertical = 18.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.dc), // Icon vị trí
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
                .padding(vertical = 2.dp, horizontal = 0.dp)
        ) {
            listTypeProduct.forEach { type ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(horizontal = 0.dp)
                        .width(80.dp)
                ) {
                    IconButton(
                        onClick = { statusType = type.type
                            if (type.type == "Tìm người ở ghép") {
                                navController.navigate("TogeTher") // Điều hướng đến TogetherScreen
                            } else {
                                statusType = type.type
                            }
                        },
                        modifier = Modifier
                            .background(
                                color = if (statusType == type.type) Color.White else Color.White,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .padding(2.dp)
                    ) {
                        Image(
                            painter = painterResource(id = type.icon),
                            contentDescription = "",
                            modifier = Modifier.size(24.dp)
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