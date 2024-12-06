package com.rentify.user.app.view.userScreens.roomdetailScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.rentify.user.app.R
import com.rentify.user.app.viewModel.HomeScreenViewModel
import java.text.DecimalFormat

@Composable
fun baidangPreview(navController: NavController, staffId: String, name: String) {
    Layoutbaidang(navController = navController, userId = staffId, name = name)
}

@Composable
fun Layoutbaidang(
    navController: NavController,
    homeScreenViewModel: HomeScreenViewModel = viewModel(),
    userId: String,
    name: String
) {
    val listRoom by homeScreenViewModel.roomList.observeAsState(emptyList())

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Text(
            text = "Bài Đăng liên quan ",
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = 8.dp, start = 10.dp),
            fontSize = 16.sp
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
                .padding(start = 10.dp)
        ) {
            items(listRoom.take(4)){ room ->
                val imageUrl = "http://10.0.2.2:3000/" + room.photos_room[0]
                val formattedPrice = DecimalFormat("#,###,###").format(room.price)
                val person = if (room.limit_person == 0 || room.limit_person == null) {
                    "Không giới hạn"
                } else {
                    "${room.limit_person}"
                }
                RoomCard(
                    imageResId = imageUrl,
                    roomName = "${room.room_type} - ${room.room_name}",
                    price = "${formattedPrice}đ/tháng",
                    area = "${room.size}m2",
                    peopleCount = person,
                    address = room.building_id.address,
                    onClick = { navController.navigate("ROOMDETAILS2/${room._id}") }
                )
                Spacer(modifier = Modifier.padding(10.dp))
            }

            item {
                LoadMoreButton(
                    onClick = {}
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ActionButton(
                iconResId = R.drawable.error,
                text = "Báo cáo",
                backgroundColor = Color(0xFFD9D9D9),
                onClick = { /* Thực hiện hành động báo cáo */ }
            )

            ActionButton(
                iconResId = R.drawable.tinnhan,
                text = "Tin nhắn",
                backgroundColor = Color(0xFFffffff),
                borderColor = Color(0xff84D8FF),
                onClick = {
                    navController.navigate("TINNHAN/${userId}/${name}")
                }
            )

            ActionButton(
                iconResId = R.drawable.phone,
                text = "Gọi điện",
                backgroundColor = Color(0xFFffffff),
                borderColor = Color.Red,
                onClick = { /* Thực hiện hành động gọi điện */ }
            )
        }

        Spacer(modifier = Modifier.padding(15.dp))
    }
}

@Composable
fun RoomCard(
    imageResId: String,
    roomName: String,
    price: String,
    area: String,
    peopleCount: String,
    address: String,
    onClick: () -> Unit
) {
    val imageLoading = remember { mutableStateOf(true) }
    Column(
        modifier = Modifier
            .width(150.dp)
            .height(195.dp)
            .shadow(3.dp, RoundedCornerShape(10.dp))
            .clickable {
                onClick()
            }
            .background(color = Color(0xFFffffff)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Hình ảnh phòng
        AsyncImage(
            model = imageResId,
            contentDescription = "Room Image",
            modifier = Modifier.height(100.dp),
            contentScale = ContentScale.Crop,
            onLoading = {
                imageLoading.value = true // Khi ảnh đang tải, sẽ hiển thị CircularProgressIndicator
            },
            onSuccess = {
                imageLoading.value = false // Khi ảnh tải thành công, ẩn CircularProgressIndicator
            },
            onError = {
                imageLoading.value = false // Ẩn loading khi có lỗi
            },
            placeholder = painterResource(id = R.drawable.g), // Ảnh mặc định khi đang tải
            error = painterResource(id = R.drawable.g) // Ảnh thay thế khi tải thất bại
        )
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            // Tên phòng
            Text(
                text = roomName,
                fontSize = 12.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 3.dp)
            )
            // Giá phòng
            Text(
                text = price,
                color = Color(0XFFb95533),
                fontSize = 12.sp,
                modifier = Modifier.padding(bottom = 3.dp)
            )
            // Diện tích và số người
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 3.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    Image(
                        painter = painterResource(id = R.drawable.vg),
                        contentDescription = "",
                        modifier = Modifier.size(15.dp)
                    )
                    Text(
                        text = area,
                        fontSize = 12.sp,
                        color = Color(0xFF909191),
                        modifier = Modifier
                            .padding(start = 4.dp)
                    )
                }
                Row {
                    Image(
                        painter = painterResource(id = R.drawable.person),
                        contentDescription = "",
                        modifier = Modifier.size(15.dp)
                    )
                    Text(
                        text = peopleCount,
                        fontSize = 12.sp,
                        color = Color(0xFF909191),
                        modifier = Modifier
                            .padding(start = 4.dp)
                    )
                }
            }
            // Địa chỉ
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 3.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.mapp),
                    contentDescription = "",
                    modifier = Modifier.size(15.dp)
                )
                Text(
                    text = address,
                    fontSize = 12.sp,
                    color = Color(0xFF909191),
                    maxLines = 1,  // Chỉ hiển thị 1 dòng
                    overflow = TextOverflow.Ellipsis, // Hiển thị '...' khi văn bản dài
                    modifier = Modifier
                        .padding(start = 4.dp)
                )
            }
        }
    }
}

@Composable
fun LoadMoreButton(
    onClick: () -> Unit
) {
    // Thiết kế nút "Xem thêm"
    Box(
        modifier = Modifier
            .width(150.dp)
            .height(195.dp)
            .shadow(3.dp, RoundedCornerShape(10.dp))
            .clickable { onClick() }
            .background(color = Color(0xFFffffff)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Hình ảnh mặc định cho nút Xem thêm
            Image(
                painter = painterResource(id = R.drawable.rent),
                contentDescription = "More Icon",
                modifier = Modifier
                    .size(50.dp)
                    .padding(bottom = 10.dp),
                contentScale = ContentScale.Crop
            )
            // Nút Xem thêm
            Text(
                text = "Xem thêm",
                fontSize = 14.sp,
                color = Color(0xFF007BFF)
            )
        }
    }
}

@Composable
fun ActionButton(
    iconResId: Int,
    text: String,
    backgroundColor: Color,
    borderColor: Color = Color.Transparent, // Mặc định không có viền
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .width(100.dp)
            .height(40.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(color = backgroundColor)
            .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(15.dp))
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = null,
            modifier = Modifier
                .size(35.dp)
                .padding(5.dp)
                .clip(CircleShape)
        )
        Text(
            text = text
        )
    }
}
