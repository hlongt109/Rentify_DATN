package com.rentify.user.app.view.userScreens.personalScreen.components
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentify.user.app.R
import com.rentify.user.app.view.userScreens.homeScreen.components.TypeProduct


data class TypeProduct(val type: String, val icon: Int)


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun MenuComponent() {
    LayoutMenu()
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LayoutMenu() {
    val listTypeProduct = listOf(
        TypeProduct("Tư vấn thiết   kế phòng", R.drawable.tuvan),
        TypeProduct("Giặt là ", R.drawable.gatla),
        TypeProduct("Tin đăng cho thuê", R.drawable.tindang),
        TypeProduct("Tìm người ở ghép", R.drawable.timnguoi),
        TypeProduct("Vận chuyển", R.drawable.vanchuyen)
    )
    var statusType by remember { mutableStateOf(listTypeProduct.first().type) }
    Column(
        modifier = Modifier
            .padding(start = 9.dp, end = 9.dp, bottom = 9.dp)
            .shadow(
                elevation = 9.dp,
                shape = RoundedCornerShape(20.dp)
            )
            .background(color = Color.White, shape = RoundedCornerShape(20.dp))
    ) {
        // Thanh tìm kiếm với icon và chữ "Hà Nội"
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF5F5F5), RoundedCornerShape(8.dp))
                .padding(horizontal = 12.dp, vertical = 4.dp)
        ) {
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