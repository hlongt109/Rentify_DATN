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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.rentify.user.app.R
import com.rentify.user.app.model.FakeModel.MarkerInfo
import com.rentify.user.app.ui.theme.homeMarker
import com.rentify.user.app.view.userScreens.homeScreen.components.IconTextRow

@Composable
fun ItemClickedMarker(
    item: MarkerInfo
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 9.dp, end = 9.dp, top = 9.dp)
            .shadow(3.dp, RoundedCornerShape(15.dp))
//            .clickable { navController.navigate("ROOMDETAILS/${room._id}") }
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
//                if (imageLoading.value) {
//                    Box(
//                        modifier = Modifier.fillMaxSize(),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        CircularProgressIndicator(
//                            modifier = Modifier
//                                .size(30.dp)
//                                .padding(20.dp),
//                            color = Color.Gray
//                        )
//                    }
//                }

                // AsyncImage hiển thị ảnh và thay đổi trạng thái khi ảnh đã được tải
                Image(
                    painter = painterResource(homeMarker),
                    contentDescription = "Room Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                )
            }

            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = item.title,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "10000đ/tháng",
                    fontWeight = FontWeight(600),
                    color = Color.Red,
                    fontSize = 12.sp,
                )
                Spacer(modifier = Modifier.height(4.dp))
                item.type?.let {
                    IconTextRow(
                        iconId = R.drawable.iconhomelocation,
                        text = it
                    )
                }
                item.address?.let {
                    IconTextRow(
                        iconId = R.drawable.iconhonehouse,
                        text = it
                    )
                }
                IconTextRow(
                    iconId = R.drawable.iconhomem,
                    text = item.description
                )
                IconTextRow(
                    iconId = R.drawable.iconhomeperson,
                    text = "1"
                )

            }
        }
    }
}