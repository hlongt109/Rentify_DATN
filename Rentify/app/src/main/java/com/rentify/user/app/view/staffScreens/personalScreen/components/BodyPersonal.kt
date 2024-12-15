package com.rentify.user.app.view.staffScreens.personalScreen.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.rentify.user.app.R
import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.LoginRepository.LoginRepository
import com.rentify.user.app.viewModel.LoginViewModel
import com.rentify.user.app.viewModel.UserViewmodel.UserViewModel

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BodyPersonalPreview() {
    BodyPersonal(navController = rememberNavController())
}
@Composable
fun BodyPersonal(navController: NavHostController) {
    val viewModel: UserViewModel = viewModel()
    val userDetail by viewModel.user.observeAsState()  // Quan sát LiveData người dùng
    val errorMessage by viewModel.error.observeAsState()  // Quan sát LiveData lỗi
    val context = LocalContext.current
    val apiService = RetrofitService()
    val userRepository = LoginRepository(apiService)
    val factory = remember(context) {
        LoginViewModel.LoginViewModelFactory(userRepository, context.applicationContext)
    }
    val loginViewModel: LoginViewModel = viewModel(factory = factory)
    val userId = loginViewModel.getUserData().userId
    // Gọi API để lấy thông tin người dùng khi composable được gọi
    LaunchedEffect(Unit) {
        viewModel.getUserDetailById(userId)  // Gọi API với userId hợp lệ
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(color = Color(0xFFeef3f6))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ảnh từ API
            userDetail?.profile_picture_url?.let { photoUrl ->
                val uriAnh = "http://10.0.2.2:3000/$photoUrl"
                AsyncImage(
                    model = uriAnh,
                    contentDescription = "Profile Picture",
                    error = painterResource(R.drawable.anhdaidien), // Ảnh lỗi
                    modifier = Modifier
                        .width(100.dp)  // Giữ kích thước ảnh như cũ
                        .height(100.dp)
                        .padding(start = 40.dp, top = 20.dp, bottom = 20.dp)
                        .clip(CircleShape) // Bo tròn ảnh
                        .clickable {
                            Toast.makeText(context, "Đổi ảnh đại diện", Toast.LENGTH_LONG).show()
                        },
                    contentScale = ContentScale.Crop
                )
            } ?: run {
                // Hiển thị ảnh mặc định nếu không có ảnh từ API
                Image(
                    painter = painterResource(id = R.drawable.anhdaidien),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .width(100.dp)  // Giữ nguyên kích thước ảnh
                        .height(100.dp)
                        .padding(start = 30.dp)
                        .clip(CircleShape) // Bo tròn ảnh
                        .clickable {
                            Toast.makeText(context, "Đổi ảnh đại diện", Toast.LENGTH_LONG).show()
                        }
                )
            }

            Column(
                modifier = Modifier
                    .padding(start = 10.dp),
                verticalArrangement = Arrangement.Center
            ) {
                // Kiểm tra lỗi hoặc đang tải dữ liệu
                if (errorMessage != null) {
                    Text(
                        text = errorMessage ?: "Lỗi không xác định",
                        color = Color.Red,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                } else if (userDetail == null) {
                    Text("Đang tải thông tin người dùng ...")  // Hiển thị thông báo khi đang tải
                } else {
                    Text(
                        text = userDetail?.name ?: "Tên không có",  // Hiển thị tên người dùng
                        modifier = Modifier.padding(),
                        fontSize = 16.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.padding(2.dp))
                    Text(
                        text = userDetail?.email ?: "Email không có",  // Hiển thị email người dùng
                        modifier = Modifier,
                        fontSize = 14.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                    Log.d("TAG", "LỖI TÊN : ${userDetail?.name}")
                    Log.d("TAG", "LỖI MAIL : ${userDetail?.email}")
                }
            }
        }
    }
}




