package com.rentify.user.app.view.staffScreens.BillScreenStaff.Componenet

import android.app.Application
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.rentify.user.app.R
import com.rentify.user.app.model.FakeModel.RoomPaymentInfo
import com.rentify.user.app.model.Model.NotificationRequest
import com.rentify.user.app.repository.StaffRepository.InvoiceRepository.Invoice
import com.rentify.user.app.ui.theme.ColorBlack
import com.rentify.user.app.ui.theme.colorHeaderSearch
import com.rentify.user.app.ui.theme.colorInput_2
import com.rentify.user.app.ui.theme.colorLocation
import com.rentify.user.app.utils.CheckUnit
import com.rentify.user.app.utils.Component.getLoginViewModel
import com.rentify.user.app.viewModel.NotificationViewModel
import com.rentify.user.app.viewModel.StaffViewModel.InvoiceStaffViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ItemUnPaidStaff(
    modifier: Modifier = Modifier,
    navController: NavController,
    invoice: Invoice,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit,
    viewModel: InvoiceStaffViewModel = viewModel(),
    staffId: String,
    notificationViewModel: NotificationViewModel = viewModel()
) {
    val context = LocalContext.current
    var formatPrice = CheckUnit.formattedPrice(invoice.amount.toFloat())
    val isLoading = viewModel.isLoading.observeAsState(false)
    val successMessage by viewModel.successMessage.observeAsState()
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
                    onToggleExpand()
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
                            text = invoice.room_id.room_name,
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
                            text = "${invoice.room_id.limit_person}",
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
                        onClick = { onToggleExpand()  }
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
                                CheckUnit.formattedPrice(invoice.room_id.price.toFloat())
                            )
                            // Hiển thị các dịch vụ từ invoice.description
                            invoice.description?.forEach { service ->
                                val serviceName = service.service_name ?: "Dịch vụ không xác định"
                                val totalPrice = if (service.total != null) {
                                    CheckUnit.formattedPrice(service.total.toFloat())
                                } else {
                                    "0"
                                }
                                PaymentDetailRow(
                                    serviceName,
                                    totalPrice
                                )
                            }

                            if (invoice.room_id.sale != null && invoice.room_id.sale != 0){
                                PaymentSaleDetailRow(
                                    "Giảm giá",
                                    CheckUnit.formattedPrice(invoice.room_id.sale.toFloat())
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            if(invoice.image_paymentofuser != null && invoice.image_paymentofuser != ""){
                                val imageUrls = "http://10.0.2.2:3000/${invoice.image_paymentofuser.replace("\\", "/")}"
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

                            if(invoice.payment_status != "wait"){
                                Button(
                                    onClick = { navController.navigate("UpdateBillStaff/${invoice._id}") },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(50.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = colorLocation
                                    ),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        text = "Chỉnh sửa hóa đơn",
                                        color = Color.White,
                                        modifier = Modifier.padding(vertical = 4.dp),
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }

                            Button(
                                onClick =
                                {
                                    if(!isLoading.value){
                                        viewModel.confirmPaidInvoice(invoice._id, staffId)

                                        val formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy")
                                        val currentTime = LocalDateTime.now().format(formatter)

                                        val notificationRequest = NotificationRequest(
                                            user_id = staffId,
                                            title = "Xác nhân thanh toán thành công",
                                            content = "Phòng ${invoice.room_id.room_name} đã được Xác nhân thanh toán thành công lúc: $currentTime",
                                        )

                                        notificationViewModel.createNotification(notificationRequest)
                                        successMessage?.let {
                                            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                },
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
                                if(isLoading.value){
                                    CircularProgressIndicator(
                                        color = colorLocation
                                    )
                                }else{
                                    Text(
                                        text = "Xác nhận đã thanh toán",
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
private fun PaymentDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                drawLine(
                    color = colorInput_2,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
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

@Composable
fun PaymentSaleDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                drawLine(
                    color = colorInput_2,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
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
            color = Color.Red,
            fontWeight = FontWeight.Medium
        )
    }
}