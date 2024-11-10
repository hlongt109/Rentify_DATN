package com.rentify.user.app.view.userScreens.IncidentReport

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
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

@Composable
fun IncidentReportScreen(navController: NavController) {
    var selectedTab by remember { mutableStateOf("pending") }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xfff7f7f7))

        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color(0xff84d8ff))
                    .padding(10.dp)
            ) {
                Row (
                    modifier = Modifier
                        .fillMaxWidth()

                        .background(color = Color(0xff84d8ff)), // Để IconButton nằm bên trái
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Row(   modifier = Modifier.width(100.dp).padding(5.dp)
                        .clickable(onClick = { /**/ },
                        indication = null, // Bỏ hiệu ứng tối khi nhấn
                        interactionSource = remember { MutableInteractionSource() }),
                        horizontalArrangement = Arrangement.Center
                        ) {
                        Image(
                            painter = painterResource(id = R.drawable.back),
                            contentDescription = null,
                            modifier = Modifier.size(30.dp, 30.dp)
                        )

                    }

                    Text(
                        text = "Báo cáo sự cố",
                        //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                        color = Color.Black,
                        fontWeight = FontWeight(700),
                        fontSize = 17.sp,

                        )
                    Row( modifier = Modifier.width(100.dp)) {

                    }
                }

            }
Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                // Đang chờ duyệt
                Row (
                    modifier = Modifier.fillMaxWidth(0.45f),

                ){
                    Text(
                        text = "Đang chờ duyệt",
                        color = Color(0xff44ACFE), // màu mặc định không đổi
                        modifier = Modifier
                            .clickable(onClick = { /**/ },
                                indication = null, // Bỏ hiệu ứng tối khi nhấn
                                interactionSource = remember { MutableInteractionSource() })
                    )
                }
                Row (
                    modifier = Modifier.fillMaxWidth(0.5f),
                ){
                // Đang hoạt động
                Text(
                    text = "Đang hoạt động",
                    color = Color(0xff13CA5C), // màu mặc định không đổi
                    modifier = Modifier

                        .clickable(onClick = { /**/ },
                            indication = null, // Bỏ hiệu ứng tối khi nhấn
                            interactionSource = remember { MutableInteractionSource() })
                )}
            }

            Spacer(modifier = Modifier.height(10.dp))



            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp)
            ) {

                Box(
                    modifier = Modifier
                      //  .background(Color(0xff70cbff))
                        .fillMaxSize()
                        .padding(25.dp),

                    contentAlignment = Alignment.BottomEnd // Cố định Row ở góc dưới bên phải
                ) {
                    Row(
                        modifier = Modifier
                            .shadow(3.dp, shape = RoundedCornerShape(30.dp))
                            .background(color = Color(0xFF84d8ff))
                            .border(
                                width = 0.dp,
                                color = Color(0xFFEEEEEE),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clickable(onClick = { navController.navigate("ADDINCIDENTREPORT") },
                                indication = null, // Bỏ hiệu ứng tối khi nhấn
                                interactionSource = remember { MutableInteractionSource() })

                            .padding(15.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.addr),
                            contentDescription = null,
                            modifier = Modifier.size(30.dp, 30.dp)
                        )
                    }

                }
            }

        }
        }



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingLayoutIncidentReportScreenScreen() {
    IncidentReportScreen(navController = rememberNavController())
}