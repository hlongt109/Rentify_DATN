package com.rentify.user.app.view.userScreens.SearchRoomScreen.SearchRoomComponent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.rentify.user.app.ui.theme.ColorBlack
import com.rentify.user.app.ui.theme.colorTextSX
import com.rentify.user.app.ui.theme.down
import com.rentify.user.app.ui.theme.pinkMan
import com.rentify.user.app.ui.theme.x


@Composable
fun HeaderFilter() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(color = Color.White),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = {}, modifier = Modifier.size(15.dp)) {
                Icon(
                    painter = painterResource(x),
                    contentDescription = "",
                    modifier = Modifier.size(15.dp),
                    tint = ColorBlack
                )
            }

            Text(
                text = "Lọc kết quả",
                color = ColorBlack,
                fontWeight = FontWeight.SemiBold,
                fontSize = 17.sp,
                modifier = Modifier.padding(start = 20.dp)
            )

            Text(
                text = "Bỏ lọc",
                color = pinkMan,
                fontWeight = FontWeight.SemiBold,
                fontSize = 17.sp
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHeaderFilter(){
    HeaderFilter()
}