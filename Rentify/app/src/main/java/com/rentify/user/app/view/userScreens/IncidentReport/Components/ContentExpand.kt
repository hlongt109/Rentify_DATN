package com.rentify.user.app.view.userScreens.IncidentReport.Components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.rentify.user.app.repository.SupportRepository.Support
import com.rentify.user.app.ui.theme.ColorBlack
import com.rentify.user.app.ui.theme.building_icon
import com.rentify.user.app.ui.theme.colorInput_2
import com.rentify.user.app.utils.localUrl

@Composable
fun ContentExpand(
    item: Support,
    listImage: List<String>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(10.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Tiêu đề:",
                fontSize = 15.sp,
                color = ColorBlack,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = item.title_support,
                fontSize = 14.sp,
                color = ColorBlack,
                fontWeight = FontWeight.Light,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        Spacer(modifier = Modifier.padding(top = 10.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Nội dung:",
                fontSize = 15.sp,
                color = ColorBlack,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = item.content_support,
                fontSize = 13.sp,
                color = ColorBlack,
                fontWeight = FontWeight.Light,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        // Hiển thị các hình ảnh
        Spacer(modifier = Modifier.padding(top = 10.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Ảnh: ",
                fontSize = 15.sp,
                color = ColorBlack,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.padding(top = 15.dp))
            LazyRow {
                items(listImage){image ->
                    Log.d("UrlImage", "ContentExpand: ${localUrl+image}")
                    AsyncImage(
                        model = localUrl+image,
                        contentDescription = "Image support",
                        modifier = Modifier.size(200.dp).clip(RoundedCornerShape(8.dp))
                    )
                }
            }
        }
    }
}


