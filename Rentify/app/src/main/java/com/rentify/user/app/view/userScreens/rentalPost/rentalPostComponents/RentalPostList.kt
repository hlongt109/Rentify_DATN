package com.rentify.user.app.view.userScreens.rentalPost.rentalPostComponents


import com.rentify.user.app.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLocation
import androidx.compose.material.icons.filled.CropSquare
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.rentify.user.app.model.Model.RoomResponse
import com.rentify.user.app.viewModel.HomeScreenViewModel
import java.text.DecimalFormat


@Composable
fun RentalPostList(
    getRentalPostList: List<RoomResponse>,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        items(getRentalPostList) { room ->
            RentalPostCard(room)
        }
    }
}


@Composable
fun RentalPostCard(room: RoomResponse) {
    val imageLoading = remember { mutableStateOf(true) }
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        val imageUrl = "http://10.0.2.2:3000/" + room.photos_room[0]
        val formattedPrice = DecimalFormat("#,###,###").format(room.price)
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
                Text(
                    maxLines = 1,
                    text = "Từ ${formattedPrice}đ /Tháng" ,
                    color = Color.Red,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )

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