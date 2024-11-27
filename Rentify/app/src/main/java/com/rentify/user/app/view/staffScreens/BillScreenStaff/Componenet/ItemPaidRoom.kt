package com.rentify.user.app.view.staffScreens.BillScreenStaff.Componenet

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentify.user.app.model.FakeModel.RoomPaymentInfo
import com.rentify.user.app.repository.StaffRepository.InvoiceRepository.Invoice
import com.rentify.user.app.repository.StaffRepository.InvoiceRepository.RoomDetail
import com.rentify.user.app.ui.theme.ColorBlack
import com.rentify.user.app.ui.theme.bill_staff
import com.rentify.user.app.ui.theme.colorInput_2
import com.rentify.user.app.utils.CheckUnit

@Composable
fun ItemPaidRoom(
    room: RoomDetail,
    invoice: Invoice
) {
    var isExpanded by remember { mutableStateOf(false) }
    val price = CheckUnit.formattedPrice(invoice.amount.toFloat())
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                drawLine(
                    color = Color.LightGray,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = 2.dp.toPx()
                )
            }
//            .animateContentSize(
//                animationSpec = spring(
//                    dampingRatio = Spring.DampingRatioMediumBouncy,
//                    stiffness = Spring.StiffnessHigh
//                )
//            )
            .clickable {
                isExpanded = !isExpanded
            }
            .padding(start = 15.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(bill_staff),
                    contentDescription = "",
                    modifier = Modifier.size(20.dp)
                )

                Text(
                    text = "Phòng",
                    fontSize = 13.sp,
                    color = ColorBlack,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(start = 10.dp)
                )
                Text(
                    text = room.room_name,
                    fontSize = 13.sp,
                    color = ColorBlack,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(start = 5.dp)
                )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = price,
                fontSize = 13.sp,
                color = ColorBlack,
                fontWeight = FontWeight.Medium
            )

            IconButton(
                onClick = {}
            ) {
                Icon(
                    imageVector = if (isExpanded)
                        Icons.Default.KeyboardArrowUp
                    else
                        Icons.Default.KeyboardArrowDown,
                    contentDescription = if (isExpanded) "Collapse" else "Expand",
                    tint = ColorBlack
                )
            }
        }

//        //animation click
//        AnimatedVisibility(
//            visible = isExpanded,
//            enter = fadeIn() + expandVertically(),
//            exit = fadeOut() + shrinkVertically()
//        ) {
//
//        }
    }
}
