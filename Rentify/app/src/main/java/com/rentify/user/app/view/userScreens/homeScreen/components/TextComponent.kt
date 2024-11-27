package com.rentify.user.app.view.userScreens.homeScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentify.user.app.R


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun KhamPhaComponent() {
    LayotKhamPha()
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DoitacComponent() {
    LayoutDoitac()
}

@Composable
fun LayotKhamPha() {
    Row (
        modifier = Modifier.fillMaxWidth().padding(top = 20.dp, end = 20.dp, start = 10.dp)
    ){
        Image(painter = painterResource(id = R.drawable.iconkhampha),
            contentDescription ="" ,
            modifier = Modifier.size(20.dp)
        )
        Text(text = "Khám Phá ",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 5.dp),
            fontSize = 17.sp
            )
    }
}
@Composable
fun LayoutDoitac() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, end = 20.dp, start = 10.dp),
        verticalAlignment = Alignment.CenterVertically // Căn chỉnh dọc cho các thành phần trong Row
    ) {
        // "Đối tác Rentify" bên trái
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.icondoitac),
                contentDescription = "",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "Đối tác Rentify",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 5.dp),
                fontSize = 17.sp
            )
        }

        // "Xem thêm" bên phải
        Text(
            text = "Xem thêm",
            fontSize = 14.sp,
            color = Color(0xFF6eafff), // Màu tím hoặc chỉnh theo chủ đề ứng dụng
            modifier = Modifier
                .clickable{} // Hành động khi nhấn
                .padding(4.dp),
            fontWeight = FontWeight.Medium
        )
    }
}
