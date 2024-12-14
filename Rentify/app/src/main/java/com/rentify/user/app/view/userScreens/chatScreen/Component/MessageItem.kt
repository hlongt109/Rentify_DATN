package com.rentify.user.app.view.userScreens.chatScreen.Component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentify.user.app.ui.theme.colorLocation
import com.rentify.user.app.utils.CheckUnit
import com.rentify.user.app.viewModel.UserViewmodel.Message


@Composable
fun MessageItem(message: Message, isSentByCurrentUser: Boolean) {
    val backgroundColor = if (isSentByCurrentUser) {
        // Màu nền cho tin nhắn của người gửi (bạn)
        colorLocation // Xanh nhạt
    } else {
        // Màu nền cho tin nhắn của người nhận
        Color.LightGray  // Xám nhạt
    }
    val contentColor = if (isSentByCurrentUser) Color.White else Color.Black
    val alignment = if (isSentByCurrentUser) Alignment.CenterEnd else Alignment.CenterStart
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = if (isSentByCurrentUser) Arrangement.End else Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isSentByCurrentUser) {
            // Thời gian bên trái cho tin nhắn người gửi
            Text(
                text = CheckUnit.formatTimeMessage(message.timestamp),
                color = Color.Gray,
                fontSize = 12.sp,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.width(8.dp)) // Khoảng cách giữa thời gian và tin nhắn
        }
        Box(
            modifier = Modifier
                .background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(12.dp)
                .widthIn(max = (0.7f * LocalConfiguration.current.screenWidthDp).dp)
                .wrapContentWidth(),
        ) {
            Text(
                text = message.content,
                color = contentColor,
                modifier = Modifier
                    .background(
                        color = backgroundColor,
                        shape = RoundedCornerShape(12.dp)
                    ),
            )
        }
        if (!isSentByCurrentUser) {
            // Thời gian bên phải cho tin nhắn người nhận
            Spacer(modifier = Modifier.width(8.dp)) // Khoảng cách giữa tin nhắn và thời gian
            Text(
                text = CheckUnit.formatTimeMessage(message.timestamp),
                color = Color.Gray,
                fontSize = 12.sp,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}