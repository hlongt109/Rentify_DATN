package com.rentify.user.app.view.userScreens.SearchRoomateScreen.SearchRoomateComponent

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.R
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewFeetReport() {
    FeetReportyeucau(navController = rememberNavController())
}
@Composable
fun FeetReportyeucau(navController: NavController) {
    var isExpanded by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    var vacantRoom by remember { mutableStateOf("20 phòng") }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
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
                    painter = painterResource(id = R.drawable.building),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .padding(start = 15.dp)
                )
                Column(
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = "Phòng 2001",
                        fontSize = 14.sp,
                        color = Color.Black,
                    )
                }
                Row {
                    Text(
                        text ="Còn trống: ",
                        fontSize = 14.sp,
                        color = Color.Black,
                    )
                    Text(
                        text =vacantRoom,
                        fontSize = 14.sp,
                        color = Color.Black,
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
                        .padding(15.dp)
                        .verticalScroll(scrollState),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Spacer(modifier = Modifier.height(25.dp))
                    Button(
                        onClick = { navController.navigate("ADDROOM") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xfffb6b53)
                        )
                    ) {
                        Text(
                            modifier = Modifier.padding(6.dp),
                            text = "Thêm phòng",
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