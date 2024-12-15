package com.rentify.user.app.view.staffScreens.scheduleScreen

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.rentify.user.app.R
import com.rentify.user.app.network.ApiStaff.RetrofitStaffService
import com.rentify.user.app.repository.StaffRepository.BookingRepository.BookingRespository
import com.rentify.user.app.utils.Component.getLoginViewModel
import com.rentify.user.app.view.components.TopBar
import com.rentify.user.app.view.staffScreens.scheduleScreen.components.HorizontalPagerIndicator
import com.rentify.user.app.viewModel.BookingStaffViewModel.BookingStaffViewModel
import com.rentify.user.app.viewModel.BookingStaffViewModel.BookingViewModelFactory
import kotlinx.coroutines.launch
import java.text.DecimalFormat

@SuppressLint("UnrememberedMutableInteractionSource")
@OptIn(ExperimentalPagerApi::class)
@Composable
fun ScheduleDetails(
    id: String,
    navController: NavController,
) {
    val context = LocalContext.current
    val loginViewModel = getLoginViewModel(context)
    val userData = loginViewModel.getUserData()
    val staffId = userData.userId
    val repository = BookingRespository(RetrofitStaffService)
    val viewModel: BookingStaffViewModel =
        viewModel(factory = BookingViewModelFactory(staffId, repository))

    val booking = viewModel.getBookingById(id).observeAsState(initial = null).value
    Log.d("imagess", "ScheduleDetails: " + booking?.room?.photos_room)

    val pagerState =
        rememberPagerState(initialPage = 0, pageCount = { booking?.room?.photos_room?.size ?: 0 })

    val price = booking?.room?.price ?: 0
    val formatter = DecimalFormat("#,###")
    val formattedPrice = formatter.format(price)

    var showPhoneCall by remember { mutableStateOf(false) }
    val phoneNumber = booking?.user?.phoneNumber ?: ""
    val localUrl = "http://10.0.2.2:3000/"
    val coroutineScope = rememberCoroutineScope()

    var showDialog by remember { mutableStateOf(false) }
    var resultMessage by remember { mutableStateOf("") }
    var dialogMessage by remember { mutableStateOf("") }
    var dialogTitle by remember { mutableStateOf("Xác nhận") }
    var updateAction: (() -> Unit)? by remember { mutableStateOf(null) }

    val updateBookingCancel = {
        dialogTitle = "Xác nhận hủy"
        dialogMessage = "Bạn có chắc chắn muốn hủy lịch hẹn này?"
        updateAction = {
            viewModel.updateStatusBooking(id, 3, staffId) { result ->
                coroutineScope.launch {
                    if (result) {
                        Toast.makeText(context, "Đã hủy lịch hẹn", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Có lỗi xảy ra :(", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        showDialog = true
    }

    val updateBookingStatus = {
        dialogTitle = "Xác nhận"
        dialogMessage = when (booking?.status) {
            0 -> "Bạn có chắc chắn muốn xác nhận lịch hẹn này?"
            1 -> "Bạn xác nhận đã xem phòng này?"
            else -> "Bạn muốn cập nhật trạng thái booking này?"
        }
        updateAction = {
            val status = when (booking?.status) {
                0 -> 1
                1 -> 2
                else -> 0
            }
            viewModel.updateStatusBooking(id, status, staffId) { result ->
                coroutineScope.launch {
                    if (result) {
                        Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Có lỗi xảy ra :(", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        showDialog = true
    }


    Scaffold(
        containerColor = Color(0xffffffff),
        modifier = Modifier
            .statusBarsPadding()
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                onClick = {
                    showPhoneCall = false
                }
            ),
        topBar = {
            TopBar(
                title = "Chi tiết lịch hẹn",
                onBackClick = { navController.popBackStack() }
            )
        },
        bottomBar = {
            if (booking?.status == 0 || booking?.status == 1) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xffffffff))
                        .padding(start = 12.dp, end = 12.dp, bottom = 25.dp, top = 8.dp)
                ) {
                    // Nút Hủy
                    Button(
                        onClick = updateBookingCancel,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFFfbd0cd)
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 4.dp)
                    ) {
                        Text(
                            text = when (booking.status) {
                                0, 1 -> "Hủy"
                                else -> "Quay lại"
                            },
                            color = when (booking.status) {
                                0, 1 -> Color(0xFFf04438)
                                else -> Color(0xFFf79009)
                            },
                            fontSize = 17.sp,
                            fontWeight = FontWeight.W600,
                            modifier = Modifier.padding(4.dp)
                        )
                    }

                    // Nút Xác nhận/Đã xem
                    Button(
                        onClick = updateBookingStatus,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = when (booking?.status) {
                                0 -> Color(0xFF6fc876)
                                1 -> Color(0xFF3498db)
                                else -> Color(0xFFfbd0cd)
                            }
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 4.dp)
                    ) {
                        Text(
                            text = when (booking.status) {
                                0 -> "Xác nhận"
                                1 -> "Đã xem"
                                else -> ""
                            },
                            color = Color(0xFFffffff),
                            fontSize = 17.sp,
                            fontWeight = FontWeight.W600,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }
            }
        }

    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xfff5f5f5))
                .padding(innerPadding)
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .background(Color(0xffffffff))
                    .padding(start = 12.dp, end = 12.dp, bottom = 12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16 / 9f)
                        .clip(RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center,
                ) {
                    val images = booking?.room?.photos_room
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xffa3a3a3)),
                        userScrollEnabled = true,
                    ) { page ->
                        val imageUrl = "$localUrl${images?.get(page)}"
                        Image(
                            painter = rememberImagePainter(data = imageUrl),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(16 / 9f),
                            contentScale = ContentScale.Crop,
                        )
                    }
                    HorizontalPagerIndicator(
                        pagerState = pagerState,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(10.dp),
                        activeColor = Color.White,
                        inactiveColor = Color.Gray
                    )

                }
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "Phòng ${booking?.room?.room_name}",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFFDE5135),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "${booking?.building_id?.address}",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF000000),
                    lineHeight = 22.sp,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "$formattedPrice đ/tháng",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFDE5135),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.toanha),
                        contentDescription = "tn",
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Tên tòa nhà: ${booking?.building_id?.nameBuilding}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.W500,
                        color = Color(0xFF777777),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = when (booking?.status) {
                        0 -> "Chờ xác nhận"
                        1 -> "Đã xác nhận"
                        2 -> "Đã xem"
                        else -> "Đã hủy"
                    },
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W500,
                    color = when (booking?.status) {
                        0 -> Color(0xFFf79009)
                        1 -> Color(0xFF6fc876)
                        2 -> Color(0xFF3498db)
                        else -> Color(0xFFf04438)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.End)
                )

            }
            Spacer(modifier = Modifier.height(15.dp))
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .background(Color(0xffffffff))
                    .padding(12.dp)
            ) {
                Text(
                    text = "Thông tin khách hàng",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF000000),
                    lineHeight = 22.sp,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Họ tên :",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.W500,
                        color = Color(0xFF777777),
                        modifier = Modifier.width(65.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "${booking?.user?.name}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W500,
                        color = Color(0xFF363636),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Điện thoại :",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.W500,
                        color = Color(0xFF777777),
                        modifier = Modifier.width(65.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "${booking?.user?.phoneNumber}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W500,
                        color = Color(0xFF363636),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Giờ hẹn: ",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.W500,
                        color = Color(0xFF777777),
                        modifier = Modifier.width(65.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = booking?.check_in_date?.split(" ")
                            .let { "${it?.getOrElse(1) { "" }} - ${it?.getOrElse(0) { "" }}" },
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W500,
                        color = Color(0xFF363636),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Nút nhắn tin
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .clickable { }
                            .padding(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Message,
                            contentDescription = "Nhắn tin",
                            modifier = Modifier.size(24.dp),
                            tint = Color(0xFF2a8bfe)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Nhắn tin",
                            fontSize = 14.sp,
                            color = Color(0xFF000000)
                        )
                    }
                    Divider(modifier = Modifier
                        .width(1.dp)
                        .height(20.dp))
                    // Nút gọi điện
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .clickable { showPhoneCall = true }
                            .padding(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = "Gọi điện",
                            modifier = Modifier.size(24.dp),
                            tint = Color(0xFF2a8bfe)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Gọi điện",
                            fontSize = 14.sp,
                            color = Color(0xFF000000)
                        )
                    }
                }
            }
            if (showPhoneCall) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(5.dp))
                            .background(Color(0xffffffff))
                            .border(0.5.dp, Color(0xCCA3C8E5), RoundedCornerShape(5.dp))
                            .padding(10.dp)
                            .clickable {
                                val dialIntent = Intent(Intent.ACTION_DIAL).apply {
                                    data = Uri.parse("tel:$phoneNumber")
                                }
                                context.startActivity(dialIntent)
                            }
                    ) {
                        Text(
                            text = "Gọi ",
                            fontSize = 15.sp,
                            color = Color(0xFF2a8bfe)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = phoneNumber,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.W500,
                            color = Color(0xFF000000)
                        )
                    }
                }
            }
        }

        //
        if (showDialog) {
            AlertDialog(
                onDismissRequest = {
                    showDialog = false
                },
                title = {
                    Text(text = dialogTitle)
                },
                text = {
                    Text(text = dialogMessage)
                },
                confirmButton = {
                    Button(
                        onClick = {
                            updateAction?.invoke()
                            showDialog = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF6fc876)
                        ),
                        modifier = Modifier
                            .padding(end = 4.dp)
                    ) {
                        Text("Đồng ý", color = Color(0xffffffff))
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            showDialog = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xff888888)
                        ),
                        modifier = Modifier
                            .padding(end = 4.dp)) {
                        Text("Thoát", color = Color(0xffffffff))
                    }
                }
            )
        }
    }
}