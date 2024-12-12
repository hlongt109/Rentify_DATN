package com.rentify.user.app.view.userScreens.rentalPost.rentalPostComponents


import androidx.compose.foundation.clickable
import com.rentify.user.app.R
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLocation
import androidx.compose.material.icons.filled.CropSquare
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.rentify.user.app.model.Model.RoomResponse
import com.rentify.user.app.model.Model.RoomSaleResponse
import kotlinx.coroutines.delay
import java.text.DecimalFormat
import kotlin.math.ceil

@Composable
fun RentalPostList(
    getRentalPostList: List<RoomSaleResponse>,
    totalRooms: Int,
    currentPage: Int,
    loadMoreRooms: () -> Unit,
    isLoading: Boolean,
    pageSize: Int,
    navController: NavController

) {
    val gridState = rememberLazyGridState()

    val totalPages = ceil(totalRooms.toDouble() / pageSize.toDouble()).toInt() // Tính tổng số trang

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        state = gridState
    ) {
        items(getRentalPostList) { room ->
            RentalPostCard(room, navController)
        }

        if (!isLoading && currentPage < totalPages && getRentalPostList.size < totalRooms) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(35.dp)
                    )
                }
                LaunchedEffect(currentPage) {
                    delay(1000)
                    loadMoreRooms()
                }
            }
        }

    }
}


@Composable
fun RentalPostCard(
    room: RoomSaleResponse,
    navController: NavController
) {
    val imageLoading = remember { mutableStateOf(true) }
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .clickable { navController.navigate("ROOMDETAILS/${room._id}") }
        ,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        val imageUrl = "http://10.0.2.2:3000/" + room.photos_room[0]
        val discountedPrice = room.price - room.sale
        val formattedDiscountedPrice = DecimalFormat("#,###,###").format(discountedPrice)
        val formattedOriginalPrice = DecimalFormat("#,###,###").format(room.price)
        Column {
            // Hình ảnh phòng
            AsyncImage(
                model = imageUrl,
                contentDescription = "Room Image",
                modifier = Modifier.fillMaxWidth().height(120.dp),
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

            Column (modifier = Modifier.padding(8.dp)) {

                // Tiêu đề phòng
                Text(
                    text = "${room.room_type} - ${room.room_name}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis, // Hiển thị '...' khi văn bản dài
                )
                Spacer(modifier = Modifier.height(4.dp))
                // Giá phòng
                Column {
                    if (room.sale > 0) {
                        // Nếu có giảm giá
                        Text(
                            text = "Từ ${formattedDiscountedPrice}đ /Tháng",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Red,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(2.dp)) // Khoảng cách giữa giá giảm và giá gốc
                        Text(
                            text = "${formattedOriginalPrice}đ",
                            fontSize = 12.sp,
                            color = Color.Gray,
                            textDecoration = TextDecoration.LineThrough,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    } else {
                        // Nếu không có giảm giá
                        Text(
                            text = "Từ ${formattedOriginalPrice}đ /Tháng",
                            fontSize = 13.sp,
                            color = Color.Red,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Spacer(modifier = Modifier.height(3.dp))

                // Diện tích và số người
                Row(
                    modifier = Modifier.padding(end = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CropSquare,
                        contentDescription = "Size",
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        maxLines = 1,
                        text = " ${room.size}",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Size",
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        maxLines = 1,
                        text = if (room.limit_person == 0 || room.limit_person == null) {
                            "Không giới hạn"
                        } else {
                            "${room.limit_person}"
                        },
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }
                Spacer(modifier = Modifier.height(3.dp))
                // Địa chỉ
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.AddLocation,
                        contentDescription = "Size",
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = room.building_id.address ?: "Địa chỉ không có sẵn",  // Nếu building_id là null, hiển thị thông báo mặc định
                        color = Color.Gray,
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis, // Hiển thị '...' khi văn bản dài
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}