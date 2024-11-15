package com.rentify.user.app.view.staffScreens.homeScreen.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.flowlayout.FlowRow
import com.rentify.user.app.R
import com.rentify.user.app.view.staffScreens.addRoomScreen.Components.ComfortableOption
@Composable
fun ComfortableOption(
    text: String,
    imageRes: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clickable(onClick = onClick, indication = null, interactionSource = remember { MutableInteractionSource() })

            .background(color = Color.White)
            .padding(0.dp)
            .height(90.dp)
            .width(67.dp) // Cố định chiều rộng chiếm 40% chiều rộng của container (điều chỉnh giá trị tùy ý)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Ảnh có viền
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier
                    .size(55.dp) // Điều chỉnh kích thước ảnh
                    .border(
                        width = 3.dp,
                        color = Color(0xFF209fa8) ,
                        shape = RoundedCornerShape(20) // Bo tròn viền ảnh
                    )
                    .padding(10.dp) // Khoảng cách cho viền
            )
            Spacer(modifier = Modifier.height(5.dp))

            // Text hiển thị
            Text(
                text = text,
                fontSize = 13.sp, // Điều chỉnh kích thước font
                modifier = Modifier
                    .fillMaxWidth() // Đảm bảo text chiếm hết chiều rộng của Column
                    .padding(horizontal = 5.dp), // Khoảng cách từ 2 bên
                textAlign = TextAlign.Center, // Căn giữa văn bản
                maxLines = 2, // Giới hạn tối đa là 2 dòng
                overflow = TextOverflow.Ellipsis, // Cắt bớt nếu quá dài
                softWrap = true // Cho phép xuống dòng tự động
            )
        }
    }
}
