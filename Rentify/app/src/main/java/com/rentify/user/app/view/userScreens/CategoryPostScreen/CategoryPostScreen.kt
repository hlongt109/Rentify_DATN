package com.rentify.user.app.view.userScreens.CategoryPostScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.view.userScreens.CategoryPostScreen.components.SpacerHeightCompose
import com.rentify.user.app.R
import com.rentify.user.app.utils.Component.HeaderBar

@Composable
fun CategoryPostScreen(navController: NavController) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xfff7f7f7))
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            HeaderBar(navController, title = "Quản lý bài đăng")
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
                                navController.navigate("SEARCHPOSTROOM")
                            }
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.room),
                        contentDescription = null,
                        modifier = Modifier.size(45.dp, 35.dp)
                    )
                    Text(
                        text = "Tìm phòng",
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
                                navController.navigate("SEARCHPOSTROOMATE")

                            }
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.home),
                        contentDescription = null,
                        modifier = Modifier.size(45.dp, 35.dp)
                    )
                    Text(
                        text = "Tìm người ở ghép",
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