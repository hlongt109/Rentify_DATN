package com.rentify.user.app.view.userScreens.personalScreen.components
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.R
import com.rentify.user.app.view.userScreens.homeScreen.components.TypeProduct


data class TypeProduct(val type: String, val icon: Int)


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun MenuComponent() {
    LayoutMenu(navController = rememberNavController())
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LayoutMenu(navController: NavHostController) {
    var searchText by remember { mutableStateOf(TextFieldValue("")) }
    val listTypeProduct = listOf(
        TypeProduct("Săn phòng giảm giá", R.drawable.sanphong),
        TypeProduct("Tìm phòng xung quanh", R.drawable.map),
        TypeProduct("Tin đăng cho thuê", R.drawable.tindang),
        TypeProduct("Tin đăng tìm phòng", R.drawable.iconhomefindroom),
        TypeProduct("Tìm người ở ghép", R.drawable.timnguoi),
        TypeProduct("Vận chuyển", R.drawable.vanchuyen)
    )

    var statusType by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(top = 10.dp)
            .padding(horizontal = 10.dp)
            .background(color = Color.White, shape = RoundedCornerShape(10.dp))
            .padding(10.dp)
    ) {
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