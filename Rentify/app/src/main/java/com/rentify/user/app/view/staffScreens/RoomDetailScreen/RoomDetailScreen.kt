package com.rentify.user.app.view.staffScreens.RoomDetailScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.R
import com.rentify.user.app.view.auth.components.HeaderComponent
import com.rentify.user.app.view.staffScreens.addRoomScreen.Components.ComfortableLabel
import com.rentify.user.app.view.staffScreens.addRoomScreen.Components.RoomTypeLabel
import com.rentify.user.app.view.staffScreens.addRoomScreen.Components.ServiceLabel

@Composable
fun RoomDetailScreen(navController: NavHostController) {

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    val scrollState = rememberScrollState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xfff7f7f7))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .background(color = Color(0xfff7f7f7))
                .padding(bottom = screenHeight.dp / 7f)

        ) {


            HeaderComponent(
                backgroundColor = Color(0xffffffff),
                title = "Hiển thị Thêm phòng",
                navController = navController
            )
            Spacer(modifier = Modifier.height(10.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
                    .background(color = Color(0xfff7f7f7))
                    .padding(15.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                ) {
                    Text(
                        text = "Tên tòa nhà*", color = Color(0xFF7c7b7b), fontSize = 13.sp
                    )
                    Text(
                        text = "Hiển thị từ api", color = Color(0xFF7c7b7b), fontSize = 13.sp
                    )
                }

// TextField cho số người tìm ghép
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                ) {
                    Text(
                        text = "Số người tìm ghép *", color = Color(0xFF7c7b7b), fontSize = 13.sp
                    )
                    Text(
                        text = "Hiển thị từ api", color = Color(0xFF7c7b7b), fontSize = 13.sp
                    )
                }
                // loai phòng
                Column {
                    RoomTypeLabel()

                    Text(
                        text = "Hiển thị từ api", color = Color(0xFF7c7b7b), fontSize = 13.sp
                    )
                }
                //ảnh
                Row(
                    modifier = Modifier.padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Row(
                        modifier = Modifier

                            .shadow(3.dp, shape = RoundedCornerShape(10.dp))
                            .background(color = Color(0xFFffffff))
                            .border(
                                width = 0.dp,
                                color = Color(0xFFEEEEEE),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(25.dp),

                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.image),
                            contentDescription = null,
                            modifier = Modifier.size(30.dp, 30.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(15.dp))
                    Column {
                        Text(
                            text = "Ảnh Phòng trọ",
                            //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                            color = Color.Black,
                            // fontWeight = FontWeight(700),
                            fontSize = 14.sp,

                            )

                        Text(
                            text = "Tối đa 10 ảnh",

                            //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                            color = Color(0xFFBFBFBF),
                            // fontWeight = FontWeight(700),
                            fontSize = 13.sp,

                            )
                    }


                }
//video
                Spacer(modifier = Modifier.height(17.dp))
                Column(
                    modifier = Modifier
                        .fillMaxHeight(0.6f)
                        .fillMaxWidth()
                        .shadow(3.dp, shape = RoundedCornerShape(10.dp))
                        .background(color = Color(0xFFffffff))
                        .border(
                            width = 0.dp,
                            color = Color(0xFFEEEEEE),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(25.dp),

                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.video),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp, 30.dp)
                    )
                    Spacer(modifier = Modifier.height(7.dp))
                    Text(

                        text = "Video",
                        //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                        color = Color.Black,
                        // fontWeight = FontWeight(700),
                        fontSize = 13.sp,

                        )
                }
                // dịa chỉ
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                ) {
                    Text(
                        text = "Địa chỉ *",
                        //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                        color = Color(0xFF7c7b7b),
                        //  fontWeight = FontWeight(700),
                        fontSize = 13.sp,

                        )
                    Text(
                        text = "Hiển thị từ api", color = Color(0xFF7c7b7b), fontSize = 13.sp
                    )
                }
// số người hiện tại
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                ) {
                    Text(
                        text = "số người hiện tại *",
                        //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                        color = Color(0xFF7c7b7b),
                        //  fontWeight = FontWeight(700),
                        fontSize = 13.sp,

                        )
                    Text(
                        text = "Hiển thị từ api", color = Color(0xFF7c7b7b), fontSize = 13.sp
                    )
                }
//  diền tích
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                ) {
                    Text(
                        text = "Diện tích(m2) *",
                        //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                        color = Color(0xFF7c7b7b),
                        //  fontWeight = FontWeight(700),
                        fontSize = 13.sp,

                        )
                    Text(
                        text = "Hiển thị từ api", color = Color(0xFF7c7b7b), fontSize = 13.sp
                    )
                }

                // giá phòng
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                ) {
                    Text(
                        text = "Giá phòng *",
                        //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                        color = Color(0xFF7c7b7b),
                        //  fontWeight = FontWeight(700),
                        fontSize = 13.sp,
                    )
                    Text(
                        text = "Hiển thị từ api", color = Color(0xFF7c7b7b), fontSize = 13.sp
                    )
                }
                //  Tiện nghi
                Spacer(modifier = Modifier.height(3.dp))

                Column {
                    ComfortableLabel()

                    Text(
                        text = "Hiển thị từ api", color = Color(0xFF7c7b7b), fontSize = 13.sp
                    )
                }
                // dịch vụ
                Spacer(modifier = Modifier.height(10.dp))
                Column {
                    ServiceLabel()

                    Text(
                        text = "Hiển thị từ api", color = Color(0xFF7c7b7b), fontSize = 13.sp
                    )
                }

            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RoomDetailScreenPreview() {
    RoomDetailScreen(navController = rememberNavController())
}