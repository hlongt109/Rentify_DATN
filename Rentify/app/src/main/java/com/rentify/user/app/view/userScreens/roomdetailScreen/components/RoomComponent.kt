package com.rentify.user.app.view.userScreens.roomdetailScreen.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.rentify.user.app.model.Model.EmptyRoomResponse

// Define the TypeProduct data class
data class TypeProduct(val type: String, val id: String)

@Composable
fun LayoutRoom(
    landlordId: String,
    landlordName: String,
    totalRooms: Int,
    listEmptyRoom: List<EmptyRoomResponse>,
    roomId: String,
    onRoomSelected: (String) -> Unit
) {
    val listTypeProduct = listEmptyRoom.map { TypeProduct(it.room_name, it._id) }
    var selectedType by remember {
        mutableStateOf(
            listTypeProduct.find { it.id == roomId }?.type.orEmpty().also {
                if (it.isEmpty()) {
                    Log.d("Debug", "Room ID ($roomId) does not exist. Defaulting to ${listTypeProduct.firstOrNull()?.type.orEmpty()}")
                } else {
                    Log.d("Debug", "Room ID ($roomId) matches room name: $it")
                }
            }
        )
    }


    Column(
        modifier = Modifier
            .background(color = Color.White)
    ) {
        // Horizontal scrollable row for product types
        ProductTypeRow(listTypeProduct, selectedType) { newType ->
            selectedType = newType
            // Gửi sự kiện chọn phòng
            onRoomSelected(newType)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .background(color = Color(0xfff7f7f7))
        ) {
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp)
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.user),
                contentDescription = "",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape) // Bo tròn ảnh
            )

            Column {
                Text(
                    text = landlordName,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .padding(top = 10.dp),
                    fontSize = 20.sp
                )
                Text(
                    text = "$totalRooms bài đăng ",
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .padding(bottom = 10.dp),
                    fontSize = 10.sp,
                    color = Color(0xFF109bff)
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProductTypeRow(
    listTypeProduct: List<TypeProduct>,
    selectedType: String,
    onTypeSelected: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .height(1.dp)
            .background(color = Color(0xffCECECE))
    )

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(listTypeProduct) { room ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .width(100.dp) // Đặt chiều rộng cố định cho từng mục
                    .height(40.dp)
                    .shadow(3.dp, RoundedCornerShape(10.dp))
                    .background(
                        color = if (selectedType == room.type) Color(0xff84d8ff) else Color(0xFFE0E0E0),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(6.dp)
                    .clickable { onTypeSelected(room.type) },
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = room.type,
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    softWrap = true
                )
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .height(1.dp)
            .background(color = Color(0xffCECECE))
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp)
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
                .padding(start = 34.dp)
                .padding(top = 10.dp),
            fontSize = 16.sp
        )
    }


    Row(
        modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp).padding(horizontal = 10.dp)
    ) {
        Text(
            text = "Các tin đăng của chủ nhà đối tác hợp tác với Rentify được hiển thị nổi bật, uy tín hơn các chủ nhà thường để giúp bạn an tâm hơn khi thue phòng và trải nhiệm trên App Rentify",
            modifier = Modifier.padding(start = 5.dp),
            fontSize = 15.sp,
            color = Color(0xff777777)
        )
    }
}
