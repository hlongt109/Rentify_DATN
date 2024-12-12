package com.rentify.user.app.view.userScreens.SurroundingRoomsScreen.Component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.rentify.user.app.R
import com.rentify.user.app.model.FakeModel.MarkerInfo
import com.rentify.user.app.model.Model.RoomResponse
import com.rentify.user.app.model.RoomsResponse
import com.rentify.user.app.repository.ListRoomMap.RepresentativeRoom
import com.rentify.user.app.repository.ListRoomMap.RoomData
import com.rentify.user.app.ui.theme.homeMarker
import com.rentify.user.app.utils.CheckUnit
import com.rentify.user.app.view.userScreens.homeScreen.components.IconTextRow
import com.rentify.user.app.view.userScreens.homeScreen.components.extractAddressDetails

@Composable
fun ItemClickedMarker(
    onClose: () -> Unit,
    room: RepresentativeRoom,
    navController: NavController,
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp
    val imageUrl = "http://10.0.2.2:3000/" + room.photosRoom[0]
    val (ward, district) = extractAddressDetails(room.building.address)

    val imageLoading = remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 9.dp, end = 9.dp, top = 9.dp)
            .shadow(3.dp, RoundedCornerShape(15.dp))
            .clickable { navController.navigate("ROOMDETAILS/${room.id}") }
            .background(Color(0xFFFFFFFF), RoundedCornerShape(12.dp))
            .border(1.dp, Color(0xFFfafafa), RoundedCornerShape(12.dp))
    ) {
        IconButton(
            onClick = onClose,
            modifier = Modifier
                .size(25.dp)
                .offset(x = screenWidth.dp / 1.15f, y = 5.dp)
                .zIndex(100000f),
            colors = IconButtonColors(
                containerColor = Color.Red,
                contentColor = Color.Red,
                disabledContainerColor = Color.Red,
                disabledContentColor = Color.Red
            )
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close",
                tint = Color.White
            )
        }
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
                        imageLoading.value =
                            true // Khi ảnh đang tải, sẽ hiển thị CircularProgressIndicator
                    },
                    onSuccess = {
                        imageLoading.value =
                            false // Khi ảnh tải thành công, ẩn CircularProgressIndicator
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
                    text = "${room.roomType} - ${room.roomName}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Giá phòng: ${CheckUnit.formattedPrice(room.price.toFloat())}/tháng",
                    fontWeight = FontWeight(600),
                    color = Color.Red,
                    fontSize = 12.sp,
                )
                Spacer(modifier = Modifier.height(4.dp))
                IconTextRow(
                    iconId = R.drawable.iconhomelocation,
                    text = "Địa chỉ: " + room.building.address
                )
                IconTextRow(
                    iconId = R.drawable.iconhonehouse,
                    text = ward
                )
                IconTextRow(
                    iconId = R.drawable.iconhomem,
                    text = "Kích thước: ${room.size}m2"
                )
                IconTextRow(
                    iconId = R.drawable.iconhomeperson,
                    text = if (room.limitPerson == 0 || room.limitPerson == null) {
                        "Không giới hạn"
                    } else {
                        "Số lượng người: ${room.limitPerson}"
                    }
                )

            }
        }
    }
}