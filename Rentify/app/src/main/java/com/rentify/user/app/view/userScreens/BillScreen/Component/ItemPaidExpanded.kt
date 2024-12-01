package com.rentify.user.app.view.userScreens.BillScreen.Component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentify.user.app.model.FakeModel.RoomPaymentInfo
import com.rentify.user.app.repository.StaffRepository.InvoiceRepository.Invoice
import com.rentify.user.app.ui.theme.ColorBlack
import com.rentify.user.app.ui.theme.colorHeaderSearch
import com.rentify.user.app.ui.theme.colorInput_2
import com.rentify.user.app.ui.theme.file
import com.rentify.user.app.ui.theme.iconColor
import com.rentify.user.app.utils.CheckUnit

@Composable
fun ItemPaidExpand(
    item: Invoice
) {
    var isExpanded by remember { mutableStateOf(false) }
    val monthYear = CheckUnit.formatMonth(CheckUnit.parseDate(item.created_at ?: ""))
//    val amount = item.description.calculateTotal()
    val formatMoney = CheckUnit.formattedPrice(item.amount.toFloat())
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessHigh
                )
            )
            .clickable {
                isExpanded = !isExpanded
            }
            .clip(shape = RoundedCornerShape(8.dp))
            .padding(start = 10.dp, end = 15.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        painter = painterResource(file),
                        contentDescription = "",
                        tint = iconColor,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Text(
                    text = monthYear,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            //tien
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = formatMoney,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
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
        }

        //animation
        AnimatedVisibility(
            visible = isExpanded,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                Divider(
                    color = Color.LightGray,
                    thickness = 1.dp,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
//                    PaymentDetailRow(
//                        "Tiền phòng",
//                        CheckUnit.formattedPrice(item.description.roomCharge.toFloat())
//                    )
//                    PaymentDetailRow(
//                        "Tiền điện",
//                        CheckUnit.formattedPrice(item.paymentDetails.electricityCharge.toFloat())
//                    )
//                    PaymentDetailRow(
//                        "Tiền nước",
//                        CheckUnit.formattedPrice(item.paymentDetails.waterCharge.toFloat())
//                    )
//                    PaymentDetailRow(
//                        "Tiền dịch vụ",
//                        CheckUnit.formattedPrice(item.paymentDetails.serviceCharge.toFloat())
//                    )

                }
            }
        }


    }
}

@Composable
fun PaymentDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                drawLine(
                    color = colorInput_2,
                    start = Offset(0f,size.height),
                    end = Offset(size.width,size.height),
                    strokeWidth = 1.dp.toPx()
                )
            }
            .padding(top = 10.dp, bottom = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            color = ColorBlack,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            fontSize = 13.sp,
            color = ColorBlack,
            fontWeight = FontWeight.Medium
        )
    }
}
