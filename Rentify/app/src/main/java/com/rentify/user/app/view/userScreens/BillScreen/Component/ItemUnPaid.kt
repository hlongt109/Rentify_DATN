package com.rentify.user.app.view.userScreens.BillScreen.Component

import android.util.Log
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
import androidx.compose.foundation.border
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.rentify.user.app.R
import com.rentify.user.app.model.FakeModel.RoomPaymentInfo
import com.rentify.user.app.model.Model.InvoiceResponse
import com.rentify.user.app.ui.theme.ColorBlack
import com.rentify.user.app.ui.theme.colorHeaderSearch
import com.rentify.user.app.ui.theme.colorInput_2
import com.rentify.user.app.utils.CheckUnit
import com.rentify.user.app.view.staffScreens.BillScreenStaff.Componenet.PaymentSaleDetailRow
import com.rentify.user.app.view.userScreens.paymentscreen.components.LoadingAsyncImage

@Composable
fun ItemUnPaid(
    item: InvoiceResponse,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val amount = item.amount
    val formatPrice = CheckUnit.formattedPrice(amount.toFloat())
    var isExpanded by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 8.dp)

    ) {
        // Di chuyển animation vào Column bên trong Card thay vì Box
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 3.dp,
                    shape = RoundedCornerShape(12.dp),
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
                    .padding(10.dp)
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
                            text = "Tên phòng: ",
                            fontSize = 13.sp,
                            color = ColorBlack,
                        )
                        Text(
                            text = "${item.room_id.room_name} - ${item.room_id.room_type}",
                            fontSize = 13.sp,
                            color = ColorBlack,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row {
                        Text(
                            text = "Tổng tiền: ",
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
                        Spacer(modifier = Modifier.height(5.dp))
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

                            item.description.forEach { service ->
                                PaymentDetailRow(
                                    label = service.service_name,
                                    value = CheckUnit.formattedPrice(service.total.toFloat())
                                )
                            }

                            PaymentDetailRow(
                                label = "Tiền phòng",
                                value = CheckUnit.formattedPrice(item.room_id.price.toFloat())
                            )

                            if (item.room_id.sale != null && item.room_id.sale != 0){
                                PaymentSaleDetailRow(
                                    "Giảm giá",
                                    CheckUnit.formattedPrice(item.room_id.sale.toFloat())
                                )
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            if(item.image_paymentofuser != null && item.image_paymentofuser != ""){
                                val imageUrls = "http://10.0.2.2:3000/${item.image_paymentofuser.replace("\\", "/")}"
                                AsyncImage(
                                    model = imageUrls,
                                    contentDescription = "",
                                    placeholder = painterResource(R.drawable.error), // Ảnh placeholder
                                    error = painterResource(R.drawable.error), // Ảnh lỗi
                                    modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(10.dp)),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                            }

                            if (item.payment_status == "unpaid") {
                                Button(
                                    onClick = { navController.navigate("PaymentConfirmation/${item.amount}/${item.building_id}/${item._id}/${item.room_id._id}") },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(50.dp)
                                        .border(
                                            width = 1.dp,
                                            color = Color.Red,
                                            shape = RoundedCornerShape(8.dp)
                                        ),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.White
                                    ),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        text = "Chưa thanh toán",
                                        color = Color.Red,
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
}

@Composable
fun LabelValueRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            color = Color.Gray
        )
        Text(
            text = value,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )
    }
}