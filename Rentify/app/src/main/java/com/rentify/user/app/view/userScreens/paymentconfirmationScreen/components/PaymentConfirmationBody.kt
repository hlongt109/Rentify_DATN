package com.rentify.user.app.view.userScreens.paymentconfirmationScreen.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.R
import java.time.format.TextStyle

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PaymentConfirmationBodyPreview() {
    PaymentConfirmationBody(navController = rememberNavController())
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PaymentConfirmationBody(navController: NavHostController) {
    var isChecked by remember { mutableStateOf(false) }
    Column {
        Row {
            Text(text = "Phương thức thanh toán",
                modifier = Modifier.padding(start = 20.dp),
                fontSize = 19.sp,
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(color = Color.White)
                    .border(
                        width = 1.dp,
                        color = Color(0xFFF0F0F0),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable {
                        navController.navigate("TINNHAN")
                    }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.bank),
                    contentDescription = null,
                    modifier = Modifier
                        .size(70.dp) // Kích thước nhỏ hơn so với ảnh ban đầu
                        .padding(start = 20.dp)
                )
                Column(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = "Chuyển khoản",
                        fontSize = 16.sp, // Font size giảm một chút
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Chuyển tiền qua ngân hàng nội địa",
                        fontSize = 13.sp,
                        color = Color(0xFF4d4d4d),
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.tich),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp) // Kích thước nhỏ hơn so với ảnh ban đầu
                        .padding(end = 20.dp)
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(color = Color.White)
                    .border(
                        width = 1.dp,
                        color = Color(0xFFF0F0F0),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable {
                        navController.navigate("TINNHAN")
                    }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.tienmat),
                    contentDescription = null,
                    modifier = Modifier
                        .size(70.dp) // Kích thước nhỏ hơn so với ảnh ban đầu
                        .padding(start = 20.dp)
                )
                Column(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = "Tiền mặt",
                        fontSize = 16.sp, // Font size giảm một chút
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Thanh toán bằng tiền mặt ",
                        fontSize = 13.sp,
                        color = Color(0xFF4d4d4d),
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.tich),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp) // Kích thước nhỏ hơn so với ảnh ban đầu
                        .padding(end = 20.dp)
                )
            }
        }
//        yêu cầu bạn sửa phần này :
        Row(
            modifier = Modifier.padding(start = 20.dp),
            verticalAlignment = Alignment.CenterVertically // Căn giữa theo chiều dọc
        ) {
            CustomCheckbox(
                isChecked = isChecked,
                onCheckedChange = { isChecked = it }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Row(
                modifier = Modifier.weight(1f).
                height(60.dp)
            ) {
                Text(
                    text = buildAnnotatedString {
                        append("Tôi đã hiểu và đồng ý với ")
                        pushStyle(SpanStyle(color = Color(0xFFc24b2a)))
                        append(" Điều khoản")
                        pop()
                        append(" và ")
                        pushStyle(SpanStyle(color = Color(0xFFc24b2a)))
                        append("Điều kiện")
                        pop()
                        append(" và ")
                        pushStyle(SpanStyle(color = Color(0xFFc24b2a)))
                        append("Chính sách bảo mật")
                        pop()
                        append(" thanh toán của BQL, bao gồm cả hạn mức và phí theo quy định.")
                    }
                )

            }
        }

    }
}
@Composable
fun CustomCheckbox(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(bottom = 32.dp)
            .size(24.dp)
            .clickable { onCheckedChange(!isChecked) }
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = { onCheckedChange(it) },
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0xFF84d8ff),
                uncheckedColor = Color.Gray
            )
        )
    }
}