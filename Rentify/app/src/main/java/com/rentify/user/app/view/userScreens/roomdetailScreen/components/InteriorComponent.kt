package com.rentify.user.app.view.userScreens.roomdetailScreen.components

import androidx.compose.animation.core.animate
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentify.user.app.R
import com.rentify.user.app.model.Model.Service

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LayoutInterior(
    listAmenities: List<Service>
){
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
                painter = painterResource(id = R.drawable.phint),
                contentDescription = "",
                modifier = Modifier.size(30.dp)
            )
            Text(
                text = "Nội thất",
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
            listAmenities.forEach { service ->
                val imageResId = when (service.name) {
                    "Điều hoà" -> R.drawable.dieuhoa
                    "Nóng lạnh" -> R.drawable.nonglanh
                    "Máy giặt" -> R.drawable.maygiat
                    "Dịch vụ chung" -> R.drawable.home
                    "Thang máy" -> R.drawable.elevator
                    "Tủ lạnh" -> R.drawable.tulanh
                    else -> R.drawable.home
                }
                IconWithTextButton(
                    imageResId = imageResId, // Truyền ảnh theo tiện nghi
                    text = service.name // Truyền tên tiện nghi
                )
            }
        }
    }
}