package com.rentify.user.app.view.userScreens.paymentscreen.components

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.rentify.user.app.R
import com.rentify.user.app.viewModel.QRViewModel
import java.io.File
import java.text.DecimalFormat

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PaymentContentPreview() {
    PaymentContent(
        "",
        "NGUYEN DUY PHONG",
        "0888667400",
        3000000,
        "https://example.com/qr-code.png",
        "viettinback",
        navController= rememberNavController()
    )
}

@Composable
fun PaymentContent(
    invoiceId: String,
    accountName: String,
    accountNumber: String,
    amount: Int,
    qrImageUrl: String,
    nameBank: String,
    navController: NavHostController,
    qrViewModel: QRViewModel = viewModel()
) {
    val context = LocalContext.current
    val formattedPrice = DecimalFormat("#,###,###").format(amount)
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var isUploading by remember { mutableStateOf(false) }
    // Activity Result Launcher để chọn ảnh
    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        selectedImageUri = uri // Lưu URI sau khi chọn ảnh
    }

    val updateStatus by qrViewModel.updateStatus.observeAsState()
    val errorMessage by qrViewModel.errorMessage.observeAsState()

    LaunchedEffect(updateStatus, errorMessage) {
        if (updateStatus != null) {
            Toast.makeText(context, "Cập nhật hóa đơn thành công!", Toast.LENGTH_SHORT).show()
            navController.navigate("Invoice_screen")
            isUploading = false
        }

        if (errorMessage != null) {
            Toast.makeText(context, "Lỗi: $errorMessage", Toast.LENGTH_SHORT).show()
            isUploading = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // QR Code Image
        AsyncImage(
            model = qrImageUrl,
            contentDescription = "",
            placeholder = painterResource(R.drawable.error), // Ảnh placeholder
            error = painterResource(R.drawable.error), // Ảnh lỗi
            modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Bank Details
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Ngân hàng $nameBank",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4d4d4d)
                )
                Text(
                    text = nameBank,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Account Details
        PaymentDetailItem(label = "Chủ tài khoản:", value = accountName)
        PaymentDetailItem(label = "Số tài khoản:", value = accountNumber)
        PaymentDetailItem(label = "Số tiền:", value = formattedPrice)

        Spacer(modifier = Modifier.height(16.dp))

        // Note
        Text(
            text = buildAnnotatedString {
                append("Lưu ý: Nhập chính xác số tiền ")
                pushStyle(SpanStyle(fontWeight = FontWeight.Bold, color = Color.Red))
                append(formattedPrice)
                pop()
                append(" khi chuyển khoản.")
            },
            fontSize = 15.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Cancel Button
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text(
                text = "Thêm hóa đơn chuyển khoản:",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4d4d4d)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray.copy(alpha = 0.2f), shape = RoundedCornerShape(10.dp))
                    .height(500.dp)
                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(10.dp))
                    .clickable {
                        // Kích hoạt launcher để chọn ảnh
                        pickImageLauncher.launch("image/*")
                    },
                contentAlignment = Alignment.Center
            ) {
                if (selectedImageUri != null) {
                    // Hiển thị ảnh đã chọn
                    AsyncImage(
                        model = selectedImageUri,
                        contentDescription = "Hóa đơn",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    // Hiển thị nút thêm ảnh khi chưa có ảnh
                    Text(
                        text = "Nhấn để thêm ảnh hóa đơn",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Upload Bill Button
        Button(
            onClick = {
                if (selectedImageUri != null) {
                    val uri = selectedImageUri!!
                    val file = uriToFile(context, uri)

                    if (file != null) {
                        isUploading = true
                        qrViewModel.updatePaymentImage(invoiceId = invoiceId, file)
                    } else {
                        Toast.makeText(context, "Không thể xử lý ảnh đã chọn.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Vui lòng chọn ảnh hóa đơn trước.", Toast.LENGTH_SHORT).show()
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF84d8ff)),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(
                text = if (isUploading) "Đang gửi..." else "Gửi hóa đơn",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

    }
}

fun uriToFile(context: Context, uri: Uri): File? {
    val contentResolver = context.contentResolver
    val inputStream = contentResolver.openInputStream(uri) ?: return null

    // Lấy phần mở rộng của tệp từ URI
    val mimeType = contentResolver.getType(uri)
    val fileExtension = when (mimeType) {
        "image/jpeg" -> ".jpg"
        "image/png" -> ".png"
        else -> ".jpg" // Đặt mặc định là jpg nếu không xác định được
    }

    // Tạo tệp tạm với phần mở rộng xác định
    val tempFile = File.createTempFile("temp_image", fileExtension, context.cacheDir)

    tempFile.outputStream().use { outputStream ->
        inputStream.copyTo(outputStream)
    }

    return tempFile
}


@Composable
fun PaymentDetailItem(label: String, value: String) {
    Column(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.Gray
        )
        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

@Composable
fun LoadingAsyncImage(
    imageUrl: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
    size: Dp = 200.dp
) {
    val painter = rememberAsyncImagePainter(
        model = imageUrl,
        error = painterResource(R.drawable.error) // Placeholder khi lỗi
    )
    val imageState = painter.state

    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        when (imageState) {
            is AsyncImagePainter.State.Loading -> {
                // Hiển thị vòng tròn xoay khi đang tải
                CircularProgressIndicator(
                    modifier = Modifier.size(50.dp),
                    color = MaterialTheme.colors.primary
                )
            }
            is AsyncImagePainter.State.Error -> {
                // Placeholder ảnh lỗi
                Icon(
                    imageVector = Icons.Default.BrokenImage,
                    contentDescription = "Error Image",
                    tint = Color.Gray,
                    modifier = Modifier.size(50.dp)
                )
            }
            else -> {
                // Hiển thị ảnh khi đã tải xong
                AsyncImage(
                    model = imageUrl,
                    contentDescription = contentDescription,
                    placeholder = painterResource(R.drawable.loading), // Ảnh placeholder
                    error = painterResource(R.drawable.error), // Ảnh lỗi
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

