package com.rentify.user.app.view.staffScreens.personalScreen.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.rentify.user.app.R
import com.rentify.user.app.viewModel.UserViewmodel.UserViewModel

// _vanphuc: phần thân
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BodyPersonalPreview() {
    BodyPersonal(navController = rememberNavController())
}

@Composable
fun BodyPersonal(navController: NavHostController) {
    val viewModel: UserViewModel = viewModel()
    val userDetail by viewModel.user.observeAsState()  // Quan sát LiveData người dùng
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getUserDetailByEmail("")  // Lấy dữ liệu người dùng khi composable được gọi
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(color = Color(0xFFeef3f6))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.anhdaidien),
                contentDescription = "back",
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .padding(start = 30.dp)
                    .clickable {
                        Toast.makeText(context, "Đổi ảnh đại diện", Toast.LENGTH_LONG).show()
                    }
            )
            Column(
                modifier = Modifier
                    .padding(top = 22.dp, start = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (userDetail == null) {
                    Text("Đang tải thông tin người dùng ...")  // Hiển thị thông báo khi đang tải
                } else {
                    Text(
                        text = userDetail?.name ?: "Tên không có",  // Hiển thị tên người dùng
                        modifier = Modifier.padding(),
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = userDetail?.email ?: "Email không có",  // Hiển thị email người dùng
                        modifier = Modifier.padding(start = 6.dp),
                        fontSize = 15.sp,
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

