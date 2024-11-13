package com.rentify.user.app.view.staffScreens.ReportScreen.Components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.R
import com.rentify.user.app.ui.theme.ColorBlack

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewFeetReport() {
    FeetReportyeucau(navController = rememberNavController())
}
@Composable
fun FeetReportyeucau(navController: NavHostController) {
    var isExpanded by remember { mutableStateOf(false) }
    var incidentdescription by remember { mutableStateOf("") }
    var incident by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .clickable { isExpanded = !isExpanded } // Làm cho toàn bộ Box có thể nhấp
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(color = Color.White) // Đã xóa đường viền
            ) {
                Image(
                    painter = painterResource(id = R.drawable.phong),
                    contentDescription = null,
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .padding(start = 20.dp)
                )
                Column(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = "Phòng 2001",
                        fontSize = 16.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
                Box(
                    modifier = Modifier
                        .height(40.dp)
                        .width(80.dp)
                        .padding(end = 10.dp)
                        .background(color = Color.Red, shape = RoundedCornerShape(10.dp))
                        .border(width = 1.dp, color = Color.Red, shape = RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Cao",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                IconButton(
                    onClick = { isExpanded = !isExpanded }
                ) {
                    Icon(
                        imageVector = if (isExpanded)
                            Icons.Default.KeyboardArrowUp
                        else
                            Icons.Default.KeyboardArrowDown,
                        contentDescription = if (isExpanded) "Collapse" else "Expand",
                        tint = Color.Black
                    )
                }
            }

            // Sử dụng AnimatedVisibility để xử lý việc mở rộng
            AnimatedVisibility(
                visible = isExpanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White)
                        .padding(15.dp),
                    verticalArrangement = Arrangement.Center,
                ) {
                    // Nhập mô tả sự cố
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                    ) {
                        Row {
                            Text(
                                text = "Sự cố",
                                color = Color(0xff000000),
                                fontSize = 13.sp,
                            )
                            Text(
                                text = " *",
                                color = Color(0xffff1a1a),
                                fontSize = 16.sp,
                            )
                        }
                        TextField(
                            value = incidentdescription,
                            onValueChange = { incidentdescription = it },
                            modifier = Modifier.fillMaxWidth(),
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
                    // Mô tả sự cố
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                    ) {
                        Row {
                            Text(
                                text = "Mô tả sự cố",
                                color = Color(0xff000000),
                                fontSize = 13.sp,
                            )
                            Text(
                                text = " *",
                                color = Color(0xffff1a1a),
                                fontSize = 16.sp,
                            )
                        }
                        TextField(
                            value = incident,
                            onValueChange = { incident = it },
                            modifier = Modifier.fillMaxWidth(),
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
                    // Các phần tử UI bổ sung...
                    Row(
                        modifier = Modifier.padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Row(
                            modifier = Modifier
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
                                                floatArrayOf(dashWidth, gapWidth)
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
                                color = Color.Black,
                                fontSize = 14.sp,
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(17.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
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
                            color = Color.Black,
                            fontSize = 13.sp,
                        )
                    }
                    Spacer(modifier = Modifier.height(25.dp))
                    Button(
                        onClick = { /* Handle report action */ },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xfffb6b53)
                        )
                    ) {
                        Text(
                            modifier = Modifier.padding(6.dp),
                            text = "Báo cáo sự cố",
                            fontSize = 16.sp,
                            color = Color(0xffffffff)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FeetReporthoanthanh() {
    Column (
        modifier = Modifier.fillMaxSize()
    ){
        Text(text = "Ok,hiểu rồi",
            textAlign = TextAlign.Center)
    }
}
