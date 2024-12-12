package com.rentify.user.app.view.userScreens.appointment

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material.icons.filled.Timelapse
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.rentify.user.app.R
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.rentify.user.app.model.Model.BookingResponse
import com.rentify.user.app.viewModel.BookingViewModel
import java.text.Normalizer
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun RoomListScreen(
    userId: String,
    status: Int,
    searchText: String,
    navController: NavController,
    bookingViewModel: BookingViewModel = viewModel()
) {
    val listBooking by bookingViewModel.bookingList.observeAsState(emptyList())
    val context = LocalContext.current
    val updateBookingStatusResult by bookingViewModel.updateBookingStatusResult.observeAsState()
    val isLoading by bookingViewModel.isLoading.observeAsState(false)

    LaunchedEffect(userId, status) {
        bookingViewModel.fetchListBooking(userId, status)
    }

    LaunchedEffect(updateBookingStatusResult) {
        updateBookingStatusResult?.let { result ->
            result.fold(
                onSuccess = {
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show()
                    bookingViewModel.fetchListBooking(userId, status)
                    bookingViewModel.resetUpdateBookingStatusResult()
                },
                onFailure = { error ->
                    Toast.makeText(context, "Cập nhật thất bại: ${error.message}", Toast.LENGTH_SHORT).show()
                    bookingViewModel.resetUpdateBookingStatusResult()
                }
            )
        }
    }

    val filteredBookings = listBooking.filter {
        it.status == status && (
                it.manager_id.name.removeDiacritics().contains(searchText.removeDiacritics(), ignoreCase = true) ||
                        it.check_in_date.contains(searchText, ignoreCase = true)
                )
    }

    Spacer(modifier = Modifier.padding(5.dp))
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .background(Color(0xfff7f7f7))
                .fillMaxHeight()
        ) {
            items(filteredBookings) { booking ->
                BookingCard(
                    booking,
                    onClickHuyXemPhong = { bookingViewModel.updateBookingStatus(booking._id, 3) },
                    onClickDaXemPhong = { bookingViewModel.updateBookingStatus(booking._id, 2) },
                    navController = navController
                )
            }
        }

        // Hiển thị Loading GIF khi isLoading = true
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(50.dp)
            )
        }
    }
}

@Composable
fun BookingCard(
    bookingResponse: BookingResponse,
    onClickHuyXemPhong: () -> Unit,
    onClickDaXemPhong: () -> Unit,
    navController: NavController
) {
    val (formattedDate, formattedTime) = splitDateTime(bookingResponse.check_in_date)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
        ) {
            // Calendar Icon
            Image(
                painter = painterResource(id = R.drawable.user),
                contentDescription = "User Avatar",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )
            Column(modifier = Modifier
                .weight(1f)
                .padding(start = 5.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Chủ nhà: ${bookingResponse.manager_id.name}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Row {
                       IconButton(
                           onClick = {}
                       ) {
                           Icon(
                               imageVector = Icons.Default.Phone,
                               contentDescription = "Location",
                               tint = Color(0xFF37a6ee),
                               modifier = Modifier.size(25.dp)
                           )
                       }
                        Spacer(modifier = Modifier.padding(5.dp))
                        IconButton(
                            onClick = {
                                navController.navigate("TINNHAN/${bookingResponse.manager_id._id}/${bookingResponse.manager_id.name}")
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Message,
                                contentDescription = "Location",
                                tint = Color(0xFFfd9900),
                                modifier = Modifier.size(25.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.padding(5.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = "Location",
                        tint = Color(0xFFcdccd1),
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = bookingResponse.manager_id.phoneNumber ?: "",
                        color = Color(0xffcdccd1),
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(start = 8.dp),
                        fontWeight = FontWeight(500)
                    )
                }
                Spacer(modifier = Modifier.padding(3.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.PostAdd,
                        contentDescription = "Location",
                        tint = Color(0xFFcdccd1),
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Bài đăng: ",
                        color = Color(0xffcdccd1),
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(start = 8.dp),
                        fontWeight = FontWeight(500)
                    )
                    Text(
                        text = "${bookingResponse.room_id.room_type} - ${bookingResponse.room_id.room_name}",
                        color = Color(0xfffd9900), // Màu chữ
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight(600),
                        style = TextStyle(textDecoration = TextDecoration.Underline),
                        modifier = Modifier.clickable {
                            navController.navigate("ROOMDETAILS/${bookingResponse.room_id._id}")
                        }
                    )
                }
                Spacer(modifier = Modifier.padding(3.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Timelapse,
                        contentDescription = "Location",
                        tint = Color(0xFFcdccd1),
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Thời gian: ",
                        color = Color(0xffcdccd1),
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(start = 8.dp),
                        fontWeight = FontWeight(500)
                    )
                    Text(
                        text = formattedTime,
                        color = Color(0xfffd9900),
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight(600)
                    )
                    Text(
                        text = "ngày ",
                        color = Color(0xff6c6b74),
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(start = 3.dp),
                        fontWeight = FontWeight(600)
                    )
                    Text(
                        text = formattedDate,
                        color = Color(0xfffd9900),
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight(600)
                    )
                }
            }
        }
        when (bookingResponse.status) {
            0 -> {
                // Hiển thị nút "Hủy"
                androidx.compose.material3.Button(
                    onClick = {
                        onClickHuyXemPhong()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .padding(horizontal = 10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xfff44336), // Màu đỏ cho nút Hủy
                        contentColor = Color.White
                    ),
                ) {
                    Text(
                        text = "Hủy xem phòng",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.button,
                        color = Color.White
                    )
                }
            }

            1 -> {
                // Hiển thị nút "Đã xem phòng"
                androidx.compose.material3.Button(
                    onClick = {
                        onClickDaXemPhong()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .padding(horizontal = 10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xff84d8ff), // Màu xanh nhạt
                        contentColor = Color.White
                    ),
                ) {
                    Text(
                        text = "Đã xem phòng",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.button,
                        color = Color.White
                    )
                }
            }

            2, 3 -> {
                // Không hiển thị nút, không cần thêm gì
            }
        }
        Spacer(modifier = Modifier.padding(5.dp))
    }
}

fun splitDateTime(dateTime: String): Pair<String, String> {
    return try {
        val inputFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        val date = inputFormat.parse(dateTime)

        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        val formattedDate = dateFormat.format(date)
        val formattedTime = timeFormat.format(date)

        formattedDate to formattedTime
    } catch (e: Exception) {
        "N/A" to "N/A" // Trả về giá trị mặc định nếu lỗi
    }
}

fun String.removeDiacritics(): String {
    return Normalizer.normalize(this, Normalizer.Form.NFD)
        .replace(Regex("[^\\p{ASCII}]"), "") // Loại bỏ dấu
        .lowercase() // Chuyển thành chữ thường
}
