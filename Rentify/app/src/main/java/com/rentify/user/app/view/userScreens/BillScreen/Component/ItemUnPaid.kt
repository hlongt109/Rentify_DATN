package com.rentify.user.app.view.userScreens.BillScreen.Component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.ripple
import androidx.compose.material.ripple.rememberRipple
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentify.user.app.model.FakeModel.RoomPaymentInfo
import com.rentify.user.app.model.Invoice
import com.rentify.user.app.ui.theme.ColorBlack
import com.rentify.user.app.ui.theme.colorHeaderSearch
import com.rentify.user.app.ui.theme.colorInput_2
import com.rentify.user.app.utils.CheckUnit

@Composable
fun ItemUnPaid(
    item: RoomPaymentInfo,
    modifier: Modifier = Modifier
) {
    val amount = item.paymentDetails.calculateTotal()
    val formatPrice = CheckUnit.formattedPrice(amount.toFloat())
    var isExpanded by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 8.dp)

    ) {
        // Di chuyển animation vào Column bên trong Card thay vì Box
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 3.dp,
                    shape = RoundedCornerShape(8.dp),
                    spotColor = Color.Black
                )
                .clickable {
                    isExpanded = !isExpanded
                }
                .clip(shape = RoundedCornerShape(8.dp)),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp,
                pressedElevation = 2.dp,
                focusedElevation = 8.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
                    .animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessHigh
                        )
                    )
                    .background(color = Color.White, shape = RoundedCornerShape(10.dp))
            ) {
                Text(
                    text = "Thông tin phòng",
                    fontSize = 13.sp,
                    color = ColorBlack,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row {
                        Text(
                            text = "Số phòng: ",
                            fontSize = 13.sp,
                            color = ColorBlack,
                        )
                        Text(
                            text = item.roomInfo.roomNumber,
                            fontSize = 13.sp,
                            color = ColorBlack,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Row {
                        Text(
                            text = "Số lượng người: ",
                            fontSize = 13.sp,
                            color = ColorBlack,
                        )
                        Text(
                            text = item.roomInfo.numberOfPeople.toString(),
                            fontSize = 13.sp,
                            color = ColorBlack,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row {
                        Text(
                            text = "Tiền phòng: ",
                            fontSize = 13.sp,
                            color = ColorBlack,
                        )
                        Text(
                            text = formatPrice.toString(),
                            fontSize = 13.sp,
                            color = ColorBlack,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    IconButton(
                        onClick = { isExpanded = !isExpanded }
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
                            PaymentDetailRow(
                                "Tiền phòng",
                                CheckUnit.formattedPrice(item.paymentDetails.roomCharge.toFloat())
                            )
                            PaymentDetailRow(
                                "Tiền điện",
                                CheckUnit.formattedPrice(item.paymentDetails.electricityCharge.toFloat())
                            )
                            PaymentDetailRow(
                                "Tiền nước",
                                CheckUnit.formattedPrice(item.paymentDetails.waterCharge.toFloat())
                            )
                            PaymentDetailRow(
                                "Tiền dịch vụ",
                                CheckUnit.formattedPrice(item.paymentDetails.serviceCharge.toFloat())
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Button(
                                onClick = { /* TODO: Xử lý thanh toán */ },
                                modifier = Modifier.fillMaxWidth().height(50.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = colorHeaderSearch
                                ),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = "Tiến hành thanh toán",
                                    color = Color.White,
                                    modifier = Modifier.padding(vertical = 4.dp),
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PaymentDetailRow(label: String, value: String) {
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