package com.rentify.user.app.view.userScreens.roomdetailScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentify.user.app.R
import com.rentify.user.app.model.Model.Service
import com.rentify.user.app.model.Model.ServiceFee
import java.text.DecimalFormat

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LayoutService(
    listAmenities: List<ServiceFee>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.phidv),
                contentDescription = "",
                modifier = Modifier.size(30.dp)
            )
            Text(
                text = "Phí dịch vụ",
                modifier = Modifier.padding(start = 5.dp),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(color = Color(0xffCECECE))
        )
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            listAmenities.forEach { serviceFees ->
                val formattedPrice = DecimalFormat("#,###,###").format(serviceFees.price)
                IconWithTextButtonDetail(
                    imageResId = getImageResource(serviceFees.name), // Truyền ảnh theo tiện nghi
                    text =  getFormattedText(serviceFees.name, formattedPrice) ,// Truyền tên tiện nghi
                    price = serviceFees.name
                )
            }
        }
    }
}

fun getFormattedText(name: String, price: String): String {
    return when (name) {
        "Điện" -> "${price}đ/kW"
        "Nước" -> "${price}đ/khối"
        "Wifi" -> "${price}đ/phòng"
        else -> "${price}đ" // Trường hợp khác
    }
}

fun getImageResource(name: String): Int {
    return when (name) {
        "Điện" -> R.drawable.electronic // Thay bằng ID ảnh tương ứng
        "Nước" -> R.drawable.water
        "Wifi" -> R.drawable.wifi
        "Dịch vụ chung" -> R.drawable.home
        "Máy giặt" -> R.drawable.maygiat
        "Thang máy" -> R.drawable.elevator
        "Tủ lạnh" -> R.drawable.tulanh
        "Bảo trì" -> R.drawable.baotri
        "Bảo vệ" -> R.drawable.baove
        else -> R.drawable.khac // Ảnh mặc định cho các tiện ích không xác định
    }
}


@Composable
fun IconWithTextButtonDetail(
    imageResId: Int,
    text: String,
    price: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(130.dp)
            .padding(5.dp)
            .shadow(4.dp, RoundedCornerShape(8.dp))
            .background(Color.White)
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Image với shadow
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = null,
            modifier = Modifier
                .size(35.dp)
        )
        Spacer(modifier = Modifier.padding(1.dp))
        Text(
            text = price,
            fontSize = 14.sp,
            color = Color(0xff777777),
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.padding(2.dp))
        // Text căn giữa
        Text(
            text = text,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}