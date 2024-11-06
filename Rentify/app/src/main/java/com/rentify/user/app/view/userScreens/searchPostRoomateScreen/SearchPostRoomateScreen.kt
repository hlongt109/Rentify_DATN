package com.rentify.user.app.view.userScreens.searchPostRoomateScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.R

@Composable
fun SearchPostRoomateScreen(navController: NavHostController) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xfff7f7f7))

        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color(0xffffffff))
                    .padding(10.dp)
            ) {
                Row (
                    modifier = Modifier
                        .fillMaxWidth()

                        .background(color = Color(0xffffffff)), // Để IconButton nằm bên trái
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    IconButton(onClick = { /*TODO*/ }) {
                        Image(
                            painter = painterResource(id = R.drawable.back),
                            contentDescription = null,
                            modifier = Modifier.size(30.dp, 30.dp)
                        )

                    }

                    Text(
                        text = "Quản lý bài đăng",
                        //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                        color = Color.Black,
                        fontWeight = FontWeight(700),
                        fontSize = 17.sp,

                        )
                    IconButton(onClick = { /*TODO*/ }) {
                        Image(
                            painter = painterResource(id = R.drawable.addr),
                            contentDescription = null,
                            modifier = Modifier.size(25.dp, 25.dp)
                        )

                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Đang chờ duyệt",
                     //   fontFamily = FontFamily(Font(R.font.cairo_regular)),
                        color = Color(0xff44ACFE),
                        modifier = Modifier
                            .clickable {
                                // Hành động khi người dùng nhấp vào nút
                                // Ví dụ: chuyển đến trang đăng ký
                                //  navController.navigate("register")
                            }
                    )
                    Text(
                        text = "Đang hoạt động",
                        //   fontFamily = FontFamily(Font(R.font.cairo_regular)),
                        color = Color(0xff13CA5C),
                        modifier = Modifier
                            .clickable {
                                // Hành động khi người dùng nhấp vào nút
                                // Ví dụ: chuyển đến trang đăng ký
                                //  navController.navigate("register")
                            }
                    )
                    Text(
                        text = "Đã bị ẩn",
                        //   fontFamily = FontFamily(Font(R.font.cairo_regular)),
                        color = Color(0xffFF1B1B),
                        modifier = Modifier
                            .clickable {
                                // Hành động khi người dùng nhấp vào nút
                                // Ví dụ: chuyển đến trang đăng ký
                                //  navController.navigate("register")
                            }
                    )
                }
            }




        }
    }


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingLayoutQuanLyScreen() {
    SearchPostRoomateScreen(navController = rememberNavController())
}