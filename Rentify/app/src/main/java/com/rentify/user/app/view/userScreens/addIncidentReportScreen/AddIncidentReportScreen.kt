package com.rentify.user.app.view.userScreens.addIncidentReportScreen

import android.widget.Toast
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.rentify.user.app.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.flowlayout.FlowRow
import com.rentify.user.app.view.userScreens.addIncidentReportScreen.Components.HeaderComponent


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddIncidentReportScreen(navController: NavHostController) {
    var isCheckedHigh by remember { mutableStateOf(false) }
    var isCheckedMedium by remember { mutableStateOf(false) }
    var isCheckedLow by remember { mutableStateOf(false) }
    var incidentdescription by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp

    var incident by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xfff7f7f7))
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {


        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            HeaderComponent(navController = navController)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(bottom = screenHeight.dp / 8f)
                    .padding(15.dp)
            ) {
// chọn phòg
                Row(
                    modifier = Modifier
                        .height(80.dp)
                        .fillMaxWidth()
                        .shadow(3.dp, shape = RoundedCornerShape(10.dp))
                        .background(color = Color(0xFFffffff))
                        .border(
                            width = 0.dp,
                            color = Color(0xFFEEEEEE),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(15.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Row {
                            Text(
                                text = "Chọn phòng",
                                //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                                color = Color(0xff7f7f7f),
                                // fontWeight = FontWeight(700),
                                fontSize = 16.sp,
                            )
                            Text(

                                text = " *",
                                //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                                color = Color(0xffff1a1a),
                                // fontWeight = FontWeight(700),
                                fontSize = 16.sp,

                                )
                        }

                        Spacer(modifier = Modifier.height(7.dp))
                        Text(

                            text = "Chọn phòng",
                            //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                            color = Color.Black,
                            // fontWeight = FontWeight(700),
                            fontSize = 14.sp,

                            )
                    }
                    Column {
                        Image(
                            painter = painterResource(id = R.drawable.next),
                            contentDescription = null,
                            modifier = Modifier.size(17.dp, 17.dp)
                        )


                    }
                }
///2
                Spacer(modifier = Modifier.height(20.dp))
                Column(
                    modifier = Modifier

                        .fillMaxWidth()
                        .shadow(3.dp, shape = RoundedCornerShape(10.dp))
                        .background(color = Color(0xFFffffff))
                        .border(
                            width = 0.dp,
                            color = Color(0xFFEEEEEE),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(15.dp),
                    verticalArrangement = Arrangement.Center,

                    ) {
// sự cố
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                    ) {
                        Row {
                            Text(

                                text = "Sự cố",
                                //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                                color = Color(0xff000000),
                                // fontWeight = FontWeight(700),
                                fontSize = 13.sp,

                                )
                            Text(

                                text = " *",
                                //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                                color = Color(0xffff1a1a),
                                // fontWeight = FontWeight(700),
                                fontSize = 16.sp,

                                )
                        }
                        TextField(
                            value = incidentdescription,
                            onValueChange = { incidentdescription = it },
                            modifier = Modifier
                                .fillMaxWidth(),
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color(0xFFcecece),
                                unfocusedIndicatorColor = Color(0xFFcecece),
                                focusedPlaceholderColor = Color.Black,
                                unfocusedPlaceholderColor = Color.Gray,
                                unfocusedContainerColor = Color(0xFFffffff),
                                focusedContainerColor = Color(0xFFffffff),
                            ),
                            placeholder = {
                                Text(
                                    text = "Nhập tóm tắt sự cố",
                                    fontSize = 13.sp,
                                    color = Color(0xFF7f7f7f),
                                    fontFamily = FontFamily(Font(R.font.cairo_regular))
                                )
                            },
                            shape = RoundedCornerShape(size = 8.dp),
                            textStyle = TextStyle(
                                color = Color.Black,
                                fontFamily = FontFamily(Font(R.font.cairo_regular))
                            )
                        )

                    }
                    // mô tả sự cố
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                    ) {
                        Row {
                            Text(

                                text = "Mô tả sự cố",
                                //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                                color = Color(0xff000000),
                                // fontWeight = FontWeight(700),
                                fontSize = 13.sp,

                                )
                            Text(

                                text = " *",
                                //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                                color = Color(0xffff1a1a),
                                // fontWeight = FontWeight(700),
                                fontSize = 16.sp,

                                )
                        }
                        TextField(
                            value = incident,
                            onValueChange = { incident = it },
                            modifier = Modifier
                                .fillMaxWidth(),

                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color(0xFFcecece),
                                unfocusedIndicatorColor = Color(0xFFcecece),
                                focusedPlaceholderColor = Color.Black,
                                unfocusedPlaceholderColor = Color.Gray,
                                unfocusedContainerColor = Color(0xFFffffff),
                                focusedContainerColor = Color(0xFFffffff),
                            ),
                            placeholder = {
                                Text(
                                    text = "Nhập mô tả ( ghi chú  ) khi giải quyết sự cố",
                                    fontSize = 13.sp,
                                    color = Color(0xFF7f7f7f),
                                    fontFamily = FontFamily(Font(R.font.cairo_regular))
                                )
                            },
                            shape = RoundedCornerShape(size = 8.dp),
                            textStyle = TextStyle(
                                color = Color.Black,
                                fontFamily = FontFamily(Font(R.font.cairo_regular))
                            )
                        )
                    }
//ảnh
                    Row(
                        modifier = Modifier.padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Row(
                            modifier = Modifier
                                //  .shadow(3.dp, shape = RoundedCornerShape(10.dp))
                                .background(color = Color(0xFFffffff))

                                .drawBehind {
                                    val borderWidth = 2.dp.toPx()  // Độ rộng của viền
                                    val dashWidth = 2.dp.toPx()   // Độ dài của nét đứt
                                    val gapWidth = 1.dp.toPx()     // Khoảng cách giữa các nét đứt
                                    val radius = 10.dp.toPx()      // Độ cong của góc

                                    // Vẽ viền nét đứt xung quanh
                                    drawRoundRect(
                                        color = Color(0xFF7ccaef),
                                        size = size.copy(
                                            width = size.width - borderWidth,
                                            height = size.height - borderWidth
                                        ),
                                        style = Stroke(
                                            width = borderWidth,
                                            pathEffect = PathEffect.dashPathEffect(
                                                floatArrayOf(
                                                    dashWidth,
                                                    gapWidth
                                                )
                                            )
                                        ),
                                        cornerRadius = CornerRadius(radius, radius)
                                    )
                                },

                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Row(
                                modifier = Modifier.padding(25.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.image1),
                                    contentDescription = null,
                                    modifier = Modifier.size(30.dp, 30.dp)
                                )
                            }
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
                        }


                    }
                    Spacer(modifier = Modifier.height(17.dp))
//video
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            //  .shadow(3.dp, shape = RoundedCornerShape(10.dp))
                            .background(color = Color(0xFFffffff))
                            .border(
                                width = 0.dp,
                                color = Color(0xFFEEEEEE),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .drawBehind {
                                val borderWidth = 2.dp.toPx()  // Độ rộng của viền
                                val dashWidth = 2.dp.toPx()   // Độ dài của nét đứt
                                val gapWidth = 1.dp.toPx()     // Khoảng cách giữa các nét đứt
                                val radius = 10.dp.toPx()      // Độ cong của góc

                                // Vẽ viền nét đứt xung quanh
                                drawRoundRect(
                                    color = Color(0xFF7ccaef),
                                    size = size.copy(
                                        width = size.width - borderWidth,
                                        height = size.height - borderWidth
                                    ),
                                    style = Stroke(
                                        width = borderWidth,
                                        pathEffect = PathEffect.dashPathEffect(
                                            floatArrayOf(
                                                dashWidth,
                                                gapWidth
                                            )
                                        )
                                    ),
                                    cornerRadius = CornerRadius(radius, radius)
                                )
                            }
                            .padding(25.dp),

                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.video2),
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
                    //checkbox
                    Spacer(modifier = Modifier.height(10.dp))
                    Column {
                        Text(
                            text = "Mức độ nghiêm trọng",
                            //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                            color = Color.Black,
                            // fontWeight = FontWeight(700),
                            fontSize = 14.sp,
                        )
                        Row {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = isCheckedHigh,
                                    onCheckedChange = { isCheckedHigh = it }
                                )
                                Text(
                                    text = "Cao",
                                    modifier = Modifier
                                        .background(
                                            color = Color(0xffff0000),
                                            shape = RoundedCornerShape(5.dp)
                                        ) // bo tròn nền
                                        .padding(5.dp),
                                    //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                                    color = Color.White,
                                    // fontWeight = FontWeight(700),
                                    fontSize = 14.sp,
                                )
                            }

                            // Checkbox "Trung bình"
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = isCheckedMedium,
                                    onCheckedChange = { isCheckedMedium = it }
                                )
                                Text(
                                    text = "Trung bình",
                                    modifier = Modifier
                                        .background(
                                            color = Color(0xffe40505),
                                            shape = RoundedCornerShape(5.dp)
                                        ) // bo tròn nền
                                        .padding(5.dp),
                                    //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                                    color = Color.White,
                                    // fontWeight = FontWeight(700),
                                    fontSize = 14.sp,
                                )
                            }

                            // Checkbox "Thấp"
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = isCheckedLow,
                                    onCheckedChange = { isCheckedLow = it }
                                )
                                Text(
                                    text = "Thấp",
                                    modifier = Modifier
                                        .background(
                                            color = Color(0xfff1d22d),
                                            shape = RoundedCornerShape(5.dp)
                                        ) // bo tròn nền
                                        .padding(5.dp),
                                    //     fontFamily = FontFamily(Font(R.font.cairo_regular)),
                                    color = Color.White,
                                    // fontWeight = FontWeight(700),
                                    fontSize = 14.sp,
                                )
                            }
                        }

                    }
                }
                Spacer(modifier = Modifier.height(25.dp))

            }

        }
        Box(
            modifier = Modifier.background(Color(0xfff7f7f7))
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
                .height(screenHeight.dp/8f)

        ) {
            Button(
                onClick = {/**/
                },
                modifier = Modifier
                    .fillMaxWidth(),
                //  , RoundedCornerShape(25.dp)), // Bo tròn 12.dp

                shape = RoundedCornerShape(10.dp), colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xfffb6b53)
                )
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 10.dp),
                    text = "Báo cáo sự cố",
                    fontSize = 16.sp,
                    // fontFamily = FontFamily(Font(R.font.cairo_regular)),
                    color = Color(0xffffffff)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingLayoutAddPostScreen() {
    AddIncidentReportScreen(navController = rememberNavController())
}