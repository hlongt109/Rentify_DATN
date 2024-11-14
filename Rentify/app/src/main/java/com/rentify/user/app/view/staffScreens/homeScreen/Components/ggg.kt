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
            .shadow(3.dp, shape = RoundedCornerShape(6.dp))
            .background(color = Color.White, shape = RoundedCornerShape(6.dp))
            .padding(10.dp)
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
                    .size(30.dp)
                    .border(
                        width = 2.dp,
                        color = if (isSelected) Color(0xFF44acfe) else Color(0xFFeeeeee),
                        shape = RoundedCornerShape(50) // Bo tròn viền ảnh
                    )
                    .padding(4.dp) // Khoảng cách cho viền
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = text,
              //  modifier = Modifier.width(55.dp) .align(Alignment.CenterHorizontally),
                fontSize = 13.sp,
                maxLines = 2, // Giới hạn tối đa là 2 dòng
                overflow = TextOverflow.Ellipsis, // Cắt bớt nếu quá dài
                softWrap = true // Cho phép xuống dòng tự động   )
            )
        }
    }
}
