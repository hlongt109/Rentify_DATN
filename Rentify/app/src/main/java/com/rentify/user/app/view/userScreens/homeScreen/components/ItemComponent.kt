package com.rentify.user.app.view.userScreens.homeScreen.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.rentify.user.app.R
import com.rentify.user.app.model.Model.Room
import com.rentify.user.app.model.Model.RoomResponse
import com.rentify.user.app.model.Model.RoomSaleResponse
import java.text.DecimalFormat


@Composable
fun LayoutItemHome(
    navController: NavHostController,
    room: RoomSaleResponse
) {
    val (ward, district) = extractAddressDetails(room.building_id.address)
    val imageUrl = "http://10.0.2.2:3000/" + room.photos_room[0]
    val formattedPrice = DecimalFormat("#,###,###").format(room.price)
    val discountedPrice = room.sale.let { room.price - it } // Tính giá sau giảm (nếu có sale)

    // State để theo dõi khi hình ảnh được tải
    val imageLoading = remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 9.dp, end = 9.dp, top = 9.dp)
            .shadow(3.dp, RoundedCornerShape(15.dp))
            .clickable { navController.navigate("ROOMDETAILS/${room._id}") }
            .background(Color(0xFFFFFFFF), RoundedCornerShape(12.dp))
            .border(1.dp, Color(0xFFfafafa), RoundedCornerShape(12.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(15.dp))
            ) {
                // Hiển thị biểu tượng loading trong khi ảnh chưa tải
                if (imageLoading.value) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(30.dp)
                                .padding(20.dp),
                            color = Color.Gray
                        )
                    }
                }

                // AsyncImage hiển thị ảnh và thay đổi trạng thái khi ảnh đã được tải
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Room Image",
                    modifier = Modifier.fillMaxSize(),
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
            }

            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "${room.room_type} - ${room.room_name}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    if (discountedPrice != null && discountedPrice < room.price) {
                        // Hiển thị giá sau giảm và giá gốc
                        Text(
                            text = "${DecimalFormat("#,###,###").format(discountedPrice)}đ / tháng",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier,
                            color = Color.Red,
                            fontSize = 12.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "(${formattedPrice}đ gốc)",
                            fontSize = 10.sp,
                            color = Color.Gray,
                            textDecoration = TextDecoration.LineThrough
                        )
                    } else {
                        // Hiển thị giá bình thường
                        Text(
                            text = "${formattedPrice}đ / tháng",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier,
                            color = Color.Red,
                            fontSize = 12.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                IconTextRow(
                    iconId = R.drawable.iconhomelocation,
                    text = room.building_id.address
                )
                IconTextRow(
                    iconId = R.drawable.iconhonehouse,
                    text = ward
                )
                IconTextRow(
                    iconId = R.drawable.iconhomem,
                    text = "${room.size}m2"
                )
                IconTextRow(
                    iconId = R.drawable.iconhomeperson,
                    text = if (room.limit_person == 0 || room.limit_person == null) {
                        "Không giới hạn"
                    } else {
                        "${room.limit_person}"
                    }
                )

            }
        }
    }
}


@Composable
fun IconTextRow(iconId: Int, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 2.dp)
    ) {
        Image(
            painter = painterResource(id = iconId),
            contentDescription = null,
            modifier = Modifier
                .size(16.dp)
                .padding(end = 4.dp)
        )
        Text(
            text = text,
            fontSize = 12.sp,
            color = Color(0xFF909191),
            maxLines = 1,  // Chỉ hiển thị 1 dòng
            overflow = TextOverflow.Ellipsis, // Hiển thị '...' khi văn bản dài
            modifier = Modifier.weight(1f) // Cho phép Text chiếm hết không gian còn lại
        )
    }
}


fun extractAddressDetails(address: String): Pair<String, String> {
    val parts = address.split(",").map { it.trim() }
    val district = parts.getOrNull(1) ?: "Không rõ quận"
    val ward = parts.getOrNull(0) ?: "Không rõ phường"
    return Pair(ward, district)
}

