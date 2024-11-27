package com.rentify.user.app.view.staffScreens.BillScreenStaff.Componenet

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentify.user.app.ui.theme.ColorBlack
import com.rentify.user.app.ui.theme.camera
import com.rentify.user.app.utils.CheckUnit
import com.rentify.user.app.view.staffScreens.BillScreenStaff.ServiceStaff

@Composable
fun ShowService(
    item: ServiceStaff
) {
    val formatMoney = CheckUnit.formattedPrice(item.price.toFloat())
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(item.image),
                    contentDescription = "image",
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .size(25.dp)
                )

                Text(
                    text = item.title,
                    fontSize = 13.sp,
                    color = ColorBlack,
                    fontWeight = FontWeight.Medium
                )
            }
            Text(
                text = formatMoney,
                fontSize = 13.sp,
                color = ColorBlack,
                fontWeight = FontWeight.Medium
            )
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp),
            color = Color.LightGray
        )
    }
}
