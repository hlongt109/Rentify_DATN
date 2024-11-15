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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun RentalPostList(getRentalPostList: List<RentalPostRoom>) {

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
fun RentalPostCard(room: RentalPostRoom) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .height(230.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            // Hình ảnh phòng
            Image(
                painter = painterResource(id = R.drawable.a),
                contentDescription = "Room Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )

            Column (modifier = Modifier.padding(8.dp)) {
                // Thời gian đăng
                Text(
                    maxLines = 1,
                    text = room.timePosted,
                    color = Color(0xFF2196F3),
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )

                // Tiêu đề phòng
                Text(

                    text = room.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    maxLines = 1
                )

                // Giá phòng
                Text(
                    maxLines = 1,
                    text = "Từ ${room.price}  /Tháng" ,
                    color = Color.Red,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Diện tích và số người
                Row(
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
                        text = " ${room.area} m2  ",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Size",
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        maxLines = 1,
                        text = "${room.maxOccupants} người",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }

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
                        maxLines = 1,
                        text = room.address,
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))


        }
    }
}

data class RentalPostRoom(
    val imageResId: Int,        // ID của hình ảnh
    val timePosted: String,      // Thời gian đăng
    val title: String,           // Tiêu đề phòng
    val price: String,           // Giá phòng
    val area: String,            // Diện tích
    val maxOccupants: Int,       // Số người tối đa
    val address: String          // Địa chỉ
)


@Composable
fun getRentalRoomList(): List<RentalPostRoom> {
    return listOf(
        RentalPostRoom(
            imageResId = 1,
            timePosted = "3 giờ trước",
            title = "Phòng 1",
            price = "500.000 VND",
            area = "30 m2",
            maxOccupants = 2,
            address = "123 Đường 1, Quan 1, Tp HCM"
        ),
        RentalPostRoom(
            imageResId = 1,
            timePosted = "3 giờ trước",
            title = "Phòng 2",
            price = "500.000 VND",
            area = "30 m2",
            maxOccupants = 2,
            address = "123 Đường 1, Quan 1, Tp HCM"
        ),
        RentalPostRoom(
            imageResId = 1,
            timePosted = "3 giờ trước",
            title = "Phòng 3",
            price = "500.000 VND",
            area = "30 m2",
            maxOccupants = 2,
            address = "123 Đường 1, Quan 1, Tp HCM"
        )
    )
}

@Preview(showBackground = true)
@Composable
fun RentalPostListPreview() {
    RentalPostList(getRentalRoomList())
}