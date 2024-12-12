package com.rentify.user.app.view.userScreens.serviceScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.rentify.user.app.R
import com.rentify.user.app.viewModel.ServiceViewmodel.ServiceViewModel

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun ItemComponentPreview(){
    ItemComponent(navController= rememberNavController())
}
@Composable
fun ItemComponent(navController: NavHostController) {
    val serviceViewmodel: ServiceViewModel = viewModel()
    val serviceDeltai by serviceViewmodel.service.observeAsState()

    LaunchedEffect(Unit) {
        serviceViewmodel.fetchServiceByAdminId("675310e93fb4e39abb74c772")
    }

    // Check if service data is available
    serviceDeltai?.data?.let { services ->
        LazyColumn(
            modifier = Modifier
               .fillMaxSize()
               .background(Color(0xfff7f7f7))
        ) {
            items(services.chunked(2)) { chunk ->  // Chia mảng thành các phần tử 2 dịch vụ
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp) // Tạo khoảng cách giữa các Box
                ) {
                    // Duyệt qua các phần tử trong chunk (tối đa 2 dịch vụ)
                    chunk.forEach { service ->
                        Box(
                            modifier = Modifier
                                .weight(1f)  // Chia không gian đều cho các Box
                                .padding(8.dp)
                                .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
                                .clip(RoundedCornerShape(12.dp))
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .background(color = Color(0xFFfafafa))
                                    .border(
                                        width = 1.dp,
                                        color = Color(0xFFfafafa),
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .clickable { /* Handle item click if needed */ },
                                verticalArrangement = Arrangement.Center, // Center items vertically
                                horizontalAlignment = Alignment.CenterHorizontally // Center items horizontally
                            ) {
                                service.photos.firstOrNull()?.let { photoUrl ->
                                    val imageUrl = "http://10.0.2.2:3000/$photoUrl"
                                    AsyncImage(
                                        model = imageUrl,
                                        contentDescription = "Service Image",
                                        placeholder = painterResource(R.drawable.error_image),
                                        error = painterResource(R.drawable.error_image),
                                        modifier = Modifier.size(100.dp),
                                        contentScale = ContentScale.Crop
                                    )
                                }

                                Text(
                                    text = service.name,
                                    fontSize = 15.sp,
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(8.dp),
                                    textAlign = TextAlign.Center
                                )

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .padding(end = 16.dp)
                                            .clickable {
                                                navController.navigate("TINNHAN")
                                            },
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = "Tin Nhắn",
                                            fontSize = 12.sp,
                                            color = Color(0xFF44acfe),
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(4.dp),
                                            textAlign = TextAlign.Center
                                        )
                                    }

                                    Column(
                                        modifier = Modifier
                                            .clickable {
                                                navController.navigate("MESSENGER")
                                            },
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = "Liên Hệ",
                                            fontSize = 12.sp,
                                            color = Color(0xFF44acfe),
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(4.dp),
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Kiểm tra nếu chunk có 1 phần tử, thêm Box trống vào
                    if (chunk.size == 1) {
                        Box(
                            modifier = Modifier
                                .weight(1f) // Thẻ Box trống chiếm không gian tương đương thẻ Box còn lại
                                .padding(8.dp)
                        ) {}
                    }
                }
            }
        }
    } ?: run {
        Text(
            text = "No services available",
            modifier = Modifier.fillMaxSize(),
            textAlign = TextAlign.Center
        )
    }
}

