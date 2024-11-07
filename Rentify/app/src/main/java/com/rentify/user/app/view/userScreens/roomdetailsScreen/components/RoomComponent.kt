package com.rentify.user.app.view.userScreens.roomdetailsScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentify.user.app.R

// Define the TypeProduct data class
data class TypeProduct(val type: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LayoutRoom() {
    val listTypeProduct = listOf(
        TypeProduct("P501"),
        TypeProduct("P502"),
        TypeProduct("P503"),
        TypeProduct("P504"),
        TypeProduct("P505"),
    )

    // State to keep track of the selected product type
    var selectedType by remember { mutableStateOf(listTypeProduct.first().type) }

    Column(
        modifier = Modifier
            .padding(7.dp)
            .background(color = Color.White, shape = RoundedCornerShape(20.dp))
    ) {
        // Horizontal scrollable row for product types
        ProductTypeRow(listTypeProduct, selectedType) { newType ->
            selectedType = newType
        }
    }
}

@Composable
fun ProductTypeRow(
    listTypeProduct: List<TypeProduct>,
    selectedType: String,
    onTypeSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
            .background(color = Color(0xffeeeeee))
    ) {
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
    ) {
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(color = Color(0xff8c8c8c))
            .padding(bottom = 10.dp)
    ) {
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
    ) {
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
    ) {
        listTypeProduct.forEach { type ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(horizontal = 2.dp)
                    .width(80.dp)
                    .height(40.dp)
            ) {
                // Button with a rectangular shape
                IconButton(
                    onClick = { onTypeSelected(type.type) },
                    modifier = Modifier
                        .background(
                            color = if (selectedType == type.type) Color(0xff84d8ff) else Color(
                                0xFFE0E0E0
                            ), // Change color based on selection
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(6.dp) // Padding for better touch area
                ) {
                    Text(
                        text = type.type,
                        textAlign = TextAlign.Center,
                        fontSize = 12.sp,
                        color = Color.Black,
                        maxLines = 2,
                        softWrap = true
                    )
                }
            }
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
    ) {
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(color = Color(0xff8c8c8c))
    ) {
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
    ) {
    }
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "",
            modifier = Modifier.size(40.dp)
        )
        Text(
            text = "Tin đối tác ",
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 5.dp)
                .padding(top = 10.dp),
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.next),
            contentDescription = "",
            modifier = Modifier
                .size(30.dp)
                .padding(top = 10.dp)
        )
    }


    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Các tin đăng của chủ nhà đối tác hợp tác với Rentify được hiển thị nổi bật, uy tín hơn các chủ nhà thường để giúp bạn an tâm hơn khi thue phòng và trải nhiệm trên App Rentify",
            modifier = Modifier.padding(start = 5.dp),
            fontSize = 20.sp,
            color = Color(0xff777777)
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
            .background(color = Color(0xffeeeeee))
    ) {
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
    ) {
    }
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.user),
            contentDescription = "",
            modifier = Modifier.size(50.dp)
        )

        Column {
            Text(
                text = "GOHOMY",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 5.dp)
                    .padding(top = 10.dp),
                fontSize = 20.sp
            )
            Text(
                text = "42 bài đăng ",
                modifier = Modifier
                    .padding(start = 5.dp)
                    .padding(bottom = 10.dp),
                fontSize = 10.sp
            )
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
    ) {
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
            .background(color = Color(0xffeeeeee))
    ) {
    }

}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun RoomComponen() {
    LayoutRoom()
}
