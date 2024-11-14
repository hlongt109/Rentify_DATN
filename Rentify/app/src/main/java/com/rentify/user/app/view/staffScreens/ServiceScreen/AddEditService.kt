package com.rentify.user.app.view.staffScreens.ServiceScreen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.model.Service
import com.rentify.user.app.ui.theme.colorHeaderSearch
import com.rentify.user.app.view.auth.components.HeaderComponent
import com.rentify.user.app.view.staffScreens.ServiceScreen.Component.FieldTextComponent
import com.rentify.user.app.view.staffScreens.ServiceScreen.Component.HeaderServiceComponent
import com.rentify.user.app.view.staffScreens.ServiceScreen.Component.PickImage

@Composable
fun AddEditService(
    navController: NavController,
    isEditing: Boolean,
    item: Service? = null
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp
    val title = if (isEditing) "Chỉnh sửa dịch vụ" else "Thêm mới dịch vụ"
    // Khởi tạo giá trị mặc định cho các TextField từ item nếu là chế độ chỉnh sửa
    var nameService by remember { mutableStateOf(item?.name ?: "") }
    var descriptionService by remember { mutableStateOf(item?.description ?: "") }
    var priceService by remember { mutableStateOf(item?.price?.toString() ?: "") }
    var photos by remember { mutableStateOf(item?.photos ?: listOf<String>()) }
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            // Xử lý uri thành String URL và thêm vào list photos
            val newPhotoUrl = uri.toString()
            photos = photos + newPhotoUrl
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(bottom = 50.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize() // Thay đổi từ fillMaxWidth thành fillMaxSize
        ) {
            // Header
            HeaderComponent(
                backgroundColor = Color.White,
                title = title,
                navController = navController
            )

            // Content
            Column(
                modifier = Modifier
                    .weight(1f) // Thêm weight để content có thể co giãn
                    .fillMaxWidth()
                    .padding(15.dp)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(screenHeight.dp / 1.7f)
                        .background(color = Color.White, shape = RoundedCornerShape(10.dp))
                        .shadow(
                            elevation = 5.dp,
                            shape = RoundedCornerShape(10.dp),
                            spotColor = Color.Black
                        )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(15.dp)
                    ) {
                        FieldTextComponent(
                            title = "Tên dịch vụ",
                            value = nameService,
                            onValueChange = { nameService = it },
                            placeholder = "Nhập tên của dịch vụ "
                        )

                        FieldTextComponent(
                            title = "Mô tả dịch vụ",
                            value = descriptionService, // Sửa lại biến
                            onValueChange = { descriptionService = it },
                            placeholder = "Nhập mô tả của dịch vụ "
                        )

                        FieldTextComponent(
                            title = "Giá dịch vụ",
                            value = priceService, // Sửa lại biến
                            onValueChange = { priceService = it },
                            placeholder = "Nhập giá của dịch vụ "
                        )

                        Spacer(modifier = Modifier.padding(top = 20.dp))
                        PickImage(
                            photos = photos,
                            onAddImage = {
                                //chon anh
                                launcher.launch("image/*")
                            },
                            onRemoveImage = { index ->
                                //xoa anh
                                photos = photos.toMutableList().apply {
                                    removeAt(index)
                                }
                            }
                        )
                    }
                }
            }

            // Button ở dưới cùng
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 20.dp) // Thêm padding cho button
            ) {
                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = colorHeaderSearch
                    ),
                    shape = RoundedCornerShape(15.dp)
                ) {
                    Text(
                        text = title,
                        color = Color.White, // Sửa màu text thành trắng để dễ đọc
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAddEditService() {
    AddEditService(navController = rememberNavController(), false)
}