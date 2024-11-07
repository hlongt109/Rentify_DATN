package com.rentify.user.app.view.userScreens.CategoryPostScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
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
import com.rentify.user.app.view.userScreens.CategoryPostScreen.components.SpacerHeightCompose
import com.rentify.user.app.R

@Composable
fun CategoryPostScreen(navController: NavHostController) {

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
                ){
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = null,
                            tint = Color.Black
                        )

                    }
                    Text(
                        text = "Quản lý bài đăng",
                        //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                        color = Color.Black,
                        fontWeight = FontWeight(700),
                        fontSize = 17.sp,

                        )
                }
            }


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color(0xffffffff))
                        .padding(15.dp)
                        .selectable(
                            selected = true,
                            onClick = {
//                                navController.navigate(
//                                    ROUTE.detail_ql_loai_mon.name
//                                )
                            }
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.room),
                        contentDescription = null,
                        modifier = Modifier.size(60.dp, 50.dp)
                    )
                    Text(
                        text = "Quản lý loại món ăn",
                   //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                        fontSize = 17.sp,
                        fontWeight = FontWeight(700),
                        color = Color.Black,
                        modifier = Modifier.padding(start = 5.dp)
                    )
                }
                SpacerHeightCompose(height = 15)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color(0xffffffff))
                        .padding(15.dp)
                        .selectable(
                            selected = true,
                            onClick = {

//                                navController.navigate(
//                                    ROUTE.detail_ql_mon.name
//                                )
                            }
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.home),
                        contentDescription = null,
                        modifier = Modifier.size(60.dp, 50.dp)
                    )
                    Text(
                        text = "Quản lý món ăn",
                      //  fontFamily = FontFamily(Font(R.font.cairo_regular)),
                        fontSize = 17.sp,
                        fontWeight = FontWeight(700),
                        color = Color.Black,
                        modifier = Modifier.padding(start = 5.dp)
                    )
                }


            }

        }
    }


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingLayoutCategoryPostScreen() {
    CategoryPostScreen(navController = rememberNavController())
}