package com.rentify.user.app.view.userScreens.paymentscreen.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.R

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PaymentContentPreview() {
    PaymentContent(navController = rememberNavController())
}

@Composable
fun PaymentContent(navController: NavHostController) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
    }
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.qr),
                contentDescription = "",
                modifier = Modifier.size(200.dp)
            )
        }

        Row {
            Image(
                painter = painterResource(id = R.drawable.mb),
                contentDescription = "",
                modifier = Modifier
                    .size(65.dp)
                    .padding(start = 20.dp, top = 20.dp)
            )
            Column(
                modifier = Modifier.padding(top = 20.dp, start = 5.dp)
            ) {
                Text(
                    text = "Ngân hàng",
                    fontSize = 15.sp,
                    color = Color(0xFF4d4d4d)
                )
                Text(
                    text = "Ngân hàng TMCP Quân đội",
                    modifier = Modifier.padding(top = 7.dp),
                    fontSize = 15.sp,
                    color = Color(0xFF4d4d4d),
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Column(
            modifier = Modifier.padding(top = 20.dp, start = 20.dp)
        ) {
            Text(
                text = "Chủ tài khoản: ",
                fontSize = 15.sp,
                color = Color(0xFF4d4d4d)
            )
            Text(
                text = "NGUYEN DUY PHONG",
                modifier = Modifier.padding(top = 7.dp),
                fontSize = 15.sp,
                color = Color(0xFF4d4d4d),
                fontWeight = FontWeight.Bold
            )
        }
        Column(
            modifier = Modifier.padding(top = 20.dp, start = 20.dp)
        ) {
            Text(
                text = "Số tài khoản:",
                fontSize = 15.sp,
                color = Color(0xFF4d4d4d)
            )
            Text(
                text = "0888667400",
                modifier = Modifier.padding(top = 7.dp),
                fontSize = 15.sp,
                color = Color(0xFF4d4d4d),
                fontWeight = FontWeight.Bold
            )
        }
        Column(
            modifier = Modifier.padding(top = 20.dp, start = 20.dp)
        ) {
            Text(
                text = "Số tiền: ",
                fontSize = 15.sp,
                color = Color(0xFF4d4d4d)
            )
            Text(
                text = "3.500.000 VND",
                modifier = Modifier.padding(top = 7.dp),
                fontSize = 15.sp,
                color = Color(0xFF4d4d4d),
                fontWeight = FontWeight.Bold
            )
        }
        Column(
            modifier = Modifier.padding(top = 20.dp, start = 20.dp)
        ) {
            Text(
                text = buildAnnotatedString {
                    append("Lưu ý: Nhập chính xác số tiền ")
                    pushStyle(SpanStyle(color = Color(0xFF4d4d4d), fontWeight = FontWeight.Bold))
                    append("3.500.000")
                    pop()
                    append(" Khi chuyển khoản")
                },
                fontSize = 17.sp
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .height(50.dp)
                    .width(100.dp)
                    .border(
                        width = 1.dp,
                        color = Color.Red,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .clickable {
                        navController.popBackStack()
                        Toast
                            .makeText(context, "Hủy thành công", Toast.LENGTH_LONG)
                            .show()
                    }, // Đặt clickable ở đây
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Hủy",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 1.dp)
                )
            }
        }
        Column(
            modifier = Modifier.padding(top = 20.dp, start = 20.dp)
        ) {
            Text(
                text = buildAnnotatedString {
                    append("Lưu ý: Chuyển khoản thành công gửi")
                    pushStyle(SpanStyle(color = Color(0xFF4d4d4d), fontWeight = FontWeight.Bold))
                    append(" Hóa đơn tại đây")
                    pop()
                    append(" để xác nhận rằng bạn đã thanh toán tiền phòng thành công")
                },
                fontSize = 17.sp
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)
                    .border(
                        width = 1.dp,
                        color = Color(0xFF84d8ff),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .clickable {
                        Toast
                            .makeText(context, "Thêm Bill chuyển khoản tại đây", Toast.LENGTH_LONG)
                            .show()
                    }, // Đặt clickable ở đây
                contentAlignment = Alignment.Center
            ) {
                Image(painter = painterResource(id = R.drawable.add), contentDescription = "")
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
        }
    }
}