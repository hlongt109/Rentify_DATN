package com.rentify.user.app.view.userScreens.roomdetailScreen.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.rentify.user.app.R
import java.text.DecimalFormat
import java.util.Calendar

@Composable
fun LayoutNoidung(
    roomName: String,
    roomType: String,
    priceRange: Int,
    buildingName: String,
    fullAddress: String,
    sale: Int?, // Thêm trường sale
    onClick: () -> Unit
) {
    val formattedPrice = DecimalFormat("#,###,###").format(priceRange)
    val discountedPrice = sale?.let { priceRange - it } // Tính giá sau giảm (nếu có sale)

    Column(
        modifier = Modifier
            .padding(top = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp)
        ) {
            Text(
                text = roomType.replaceFirstChar { it.uppercaseChar() },
                modifier = Modifier.padding(start = 5.dp, end = 10.dp),
                fontSize = 18.sp,
                color = Color(0xfffeb051),
                fontWeight = FontWeight.Bold,
            )
            Image(
                painter = painterResource(id = R.drawable.n),
                contentDescription = "",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "Nam / Nữ",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 10.dp),
                fontSize = 18.sp
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, top = 5.dp)
        ) {
            Text(
                text = " $roomName - Phòng rộng giá rẻ",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 5.dp),
                fontSize = 16.sp
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, 5.dp)
        ) {
            if (discountedPrice != null && discountedPrice < priceRange) {
                // Hiển thị giá sau giảm và giá gốc
                Text(
                    text = "${DecimalFormat("#,###,###").format(discountedPrice)}đ / tháng",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 5.dp),
                    color = Color.Red,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "(${formattedPrice}đ gốc)",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    textDecoration = TextDecoration.LineThrough
                )
            } else {
                // Hiển thị giá bình thường
                Text(
                    text = "${formattedPrice}đ / tháng",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 5.dp),
                    color = Color.Red,
                    fontSize = 16.sp
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 17.dp, 5.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.nha),
                contentDescription = "",
                modifier = Modifier.size(17.dp)
            )
            Text(
                text = "Tên tòa nhà: $buildingName",
                modifier = Modifier.padding(start = 5.dp),
                fontSize = 15.sp,
                color = Color(0xff777777)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, top = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.dc),
                contentDescription = "",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = fullAddress,
                modifier = Modifier.padding(start = 5.dp),
                fontSize = 15.sp,
                color = Color(0xff777777)
            )
        }
        GradientButton(
            text = "Đặt lịch xem phòng",
            onClick = {
                onClick()
            }
        )
    }
}

@Composable
fun GradientButton(
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 10.dp)
    ) {
        Button(
            onClick = onClick,
            shape = RoundedCornerShape(20.dp),
            contentPadding = PaddingValues(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            // Gradient background
            Box(
                modifier = Modifier
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF84d8ff), // Gradient start
                                Color(0xFF4facfe)  // Gradient end
                            )
                        ),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .fillMaxSize()
            ) {
                Text(
                    text = text,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun datLichXemPhong(
    staffId: String,
    userName: String = "",
    phoneNumber: String = "",
    staffName: String = "",
    staffPhoneNumber: String = "",
    date: String, // Truyền trạng thái date
    time: String, // Truyền trạng thái time
    onDateSelected: (String) -> Unit,
    onTimeSelected: (String) -> Unit,
    onClick: () -> Unit,
    isLoading: Boolean = false
) {

    // Trạng thái điều khiển hiển thị DatePicker hoặc TimePicker
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    val selectedDate = remember { mutableStateOf("") } // Make it mutable
    val selectedTime = remember { mutableStateOf("") } // Make it mutable

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(20.dp)
    ) {

        Text(
            text = "Đặt lịch xem phòng",
            fontWeight = FontWeight(500),
            fontSize = 16.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp)
        )

        Divider()

        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp), // Đảm bảo Row chiếm đầy chiều rộng
            verticalAlignment = Alignment.CenterVertically
        ) {
            // UserInfoCard bên trái
            UserInfoCard(
                staffId,
                userName = userName,
                phoneNumber = phoneNumber,
                role = "Khách thuê",
                imageResId = R.drawable.user
            )

            // Spacer giữa các phần tử
            Spacer(modifier = Modifier.width(16.dp))

            // Divider với dấu chấm tròn ở giữa
            Row(
                modifier = Modifier
                    .weight(1f), // Divider chiếm không gian còn lại
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Box đầu tiên (chấm tròn)
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(Color(0xff44ACFE), shape = CircleShape)
                )

                // Divider sẽ chiếm không gian còn lại
                Box(
                    modifier = Modifier
                        .weight(1f) // Chiếm không gian còn lại
                        .padding(horizontal = 2.dp)
                        .height(1.dp) // Đặt chiều cao của divider
                        .background(Color(0xff44ACFE)) // Màu nền của divider
                        .drawBehind {
                            // Vẽ vết nứt trên divider
                            val paint = Paint().apply {
                                color = Color.White // Màu của vết nứt
                                strokeWidth = 6f // Độ dày của vết nứt
                                pathEffect = PathEffect.dashPathEffect(
                                    floatArrayOf(10f, 10f),
                                    0f
                                ) // Định nghĩa vết nứt
                            }
                            // Vẽ đường chấm trên divider
                            drawLine(
                                color = Color.White, // Màu vết nứt
                                start = Offset(0f, size.height / 2), // Điểm bắt đầu
                                end = Offset(size.width, size.height / 2), // Điểm kết thúc
                                strokeWidth = 6f, // Độ dày của vết nứt
                                pathEffect = paint.pathEffect // Áp dụng hiệu ứng vết nứt
                            )
                        }
                )


            }
            // Box thứ hai (chấm tròn)
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .background(Color(0xff44ACFE), shape = CircleShape)
            )
            // Spacer giữa các phần tử
            Spacer(modifier = Modifier.width(16.dp))

            // UserInfoCard bên phải
            UserInfoCard(
                staffId,
                userName = staffName,
                phoneNumber = staffPhoneNumber,
                role = "Nhân viên",
                imageResId = R.drawable.user
            )
        }

        Spacer(modifier = Modifier.padding(5.dp))
        Divider()

        Text(
            text = "Thời gian xem phòng *",
            fontWeight = FontWeight(500),
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth().padding(top = 15.dp)
        )

        Row(
            modifier = Modifier
                .height(50.dp)
                .padding(10.dp)
                .fillMaxWidth()
                .clickable {
                    showDatePicker = true
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.lich),
                contentDescription = "",
                modifier = Modifier.size(25.dp)
            )
            Spacer(modifier = Modifier.padding(5.dp))
            Text(
                text = if (date.isNotEmpty() && time.isNotEmpty()) {
                    "$date $time"
                } else {
                    "Chọn thời gian xem phòng"
                },
                fontWeight = FontWeight(500),
                fontSize = 14.sp,
                color = if (date.isNotEmpty() && time.isNotEmpty()) Color(0xff84d8ff) else Color(0xffcfcfcf),
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        Divider()
        Spacer(modifier = Modifier.padding(10.dp))

        Text(
            text = "Khi chủ nhà xác nhận lịch hẹn của bạn, bạn và chủ nhà có thể chủ động liên lạc để hẹn lại giờ nếu thay đổi nhé!",
            fontWeight = FontWeight(500),
            fontSize = 13.sp,
            color = Color(0xff474747),
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.padding(10.dp))

        Button(
            onClick = {
                if (!isLoading) {
                    onClick() // Gọi hàm onClick khi chưa đang loading
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xff84d8ff),
                contentColor = Color.White
            ),
            shape = CutCornerShape(10)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(24.dp) // Kích thước progress indicator
                )
            } else {
                Text(
                    text = "Đặt lịch",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.button
                )
            }
        }

        Spacer(modifier = Modifier.padding(30.dp))

        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = {
                    showDatePicker = false
                    showTimePicker = false
                },
                onDateSelected = { selectedDateString ->
                    selectedDate.value = selectedDateString // Cập nhật ngày được chọn
                    onDateSelected(selectedDate.value)
                    showDatePicker = false // Đóng DatePicker
                    showTimePicker = true // Hiển thị TimePicker sau khi chọn ngày
                }
            )
        }
        if (showTimePicker) {
            TimePickerDialog(
                onDismissRequest = {
                    showTimePicker = false
                    showDatePicker = false
                },
                onTimeSelected = { selectedTimeString ->
                    selectedTime.value = selectedTimeString // Cập nhật thời gian được chọn
                    onTimeSelected(selectedTime.value)
                    showTimePicker = false // Đóng TimePicker
                }
            )
        }
    }
}

@Composable
fun DatePickerDialog(
    onDismissRequest: () -> Unit,
    onDateSelected: (String) -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePicker = android.app.DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val selectedDate = "$dayOfMonth/${month + 1}/$year"
            onDateSelected(selectedDate)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    datePicker.setOnCancelListener {
        onDismissRequest()
    }

    DisposableEffect(Unit) {
        datePicker.show()
        onDispose {
            datePicker.dismiss()
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun TimePickerDialog(
    onDismissRequest: () -> Unit,
    onTimeSelected: (String) -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val timePicker = android.app.TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            val selectedTime = String.format("%02d:%02d", hourOfDay, minute)
            onTimeSelected(selectedTime)
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true // Sử dụng định dạng 24 giờ
    )

    timePicker.setOnCancelListener {
        onDismissRequest()
    }

    DisposableEffect(Unit) {
        timePicker.show()
        onDispose {
            timePicker.dismiss()
        }
    }
}

@Composable
fun UserInfoCard(
    staffId: String,
    userName: String? = null, // Cho phép null
    phoneNumber: String? = null, // Cho phép null
    role: String,
    imageResId: Int // Resource ID của hình ảnh
) {
    // Định dạng tên
    val formattedName = remember(userName) {
        if (!userName.isNullOrEmpty()) {
            val nameParts = userName.split(" ")
            if (nameParts.size > 1) {
                // Nếu tên có hơn 1 từ, lấy từ đầu tiên và từ cuối cùng
                "${nameParts[0]} ${nameParts.last()}"
            } else {
                // Nếu tên chỉ có 1 từ, hiển thị nguyên vẹn
                userName
            }
        } else {
            "" // Giá trị mặc định nếu userName null hoặc rỗng
        }
    }

    // Định dạng số điện thoại
    val formattedPhoneNumber = remember(phoneNumber) {
        if (!phoneNumber.isNullOrEmpty() && phoneNumber.length > 3) {
            if (role == "Nhân viên") {
                // Nếu role là "Nhân viên", che 3 số cuối
                "${phoneNumber.substring(0, phoneNumber.length - 3)}***"
            } else {
                phoneNumber // Hiển thị nguyên vẹn nếu không phải "Nhân viên"
            }
        } else {
            "" // Giá trị mặc định nếu phoneNumber null hoặc rỗng
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Hình ảnh
        Box(
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = null,
                modifier = Modifier
                    .size(70.dp)
                    .padding(bottom = 10.dp)
            )
            Box(
                modifier = Modifier
                    .width(90.dp)
                    .height(20.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .align(Alignment.BottomCenter)
                    .background(Color(0xff84d8ff)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = role,
                    fontWeight = FontWeight(500),
                    fontSize = 10.sp,
                    color = Color.White
                )
            }
        }
        Spacer(modifier = Modifier.padding(2.dp))
        // Hiển thị tên
        Text(
            text = formattedName,
            fontWeight = FontWeight(600),
            fontSize = 12.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.padding(1.dp))
        // Hiển thị số điện thoại
        Text(
            text = formattedPhoneNumber,
            fontWeight = FontWeight(600),
            fontSize = 12.sp,
            color = Color(0xff9f9f9f)
        )
    }
}



