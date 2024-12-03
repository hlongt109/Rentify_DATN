package com.rentify.user.app.view.staffScreens.PersonalProfileScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.viewModel.UserViewmodel.UserViewModel

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewFeetPersonalProfile(){
    FeetPersonalProfile(navController= rememberNavController())
    FeetPersonalProfile1(navController= rememberNavController())
}
@Composable
fun FeetPersonalProfile(navController: NavHostController){
    val viewModel: UserViewModel = viewModel()
    val userDetail by viewModel.user.observeAsState()  // Quan sát LiveData người dùng
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getUserDetailById("674efa9a06a2ca9e2b3ae2a4")  // Lấy dữ liệu người dùng khi composable được gọi
    }
    Column {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(bottom = 20.dp, top = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(text = "Họ và tên ",
                    modifier = Modifier
                        .padding(start = 20.dp, top = 5.dp),
                    color = Color(0xFF989898)
                )
                Text(text = "${userDetail?.name}",
                    modifier = Modifier
                        .padding(end = 20.dp),
                    fontSize = 15.sp
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .padding(start = 20.dp, end = 20.dp)
                    .background(color = Color(0xFFe4e4e4))
            ) {
            }
//        _vanphuc : giới tính
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(bottom = 20.dp, top = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Giới tính ",
                    modifier = Modifier
                        .padding(start = 20.dp, top = 5.dp),
                    color = Color(0xFF989898)
                )
                Text(text = "${userDetail?.gender}",
                    modifier = Modifier
                        .padding(end = 20.dp),
                    fontSize = 15.sp
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .padding(start = 20.dp, end = 20.dp)
                    .background(color = Color(0xFFe4e4e4))
            ) {
            }
//        _vanphuc : ngay sinh
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(bottom = 20.dp, top = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Ngày sinh",
                    modifier = Modifier
                        .padding(start = 20.dp, top = 5.dp),
                    color = Color(0xFF989898)
                )
                Text(text = "${userDetail?.dob}",
                    modifier = Modifier
                        .padding(end = 20.dp),
                    fontSize = 15.sp
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .padding(start = 20.dp, end = 20.dp)
                    .background(color = Color(0xFFe4e4e4))
            ) {
            }
//        _vanphuc : dia chi
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(bottom = 20.dp, top = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Địa chỉ",
                    modifier = Modifier
                        .padding(start = 20.dp, top = 5.dp),
                    color = Color(0xFF989898)
                )
                Text(text = "${userDetail?.address}",
                    modifier = Modifier
                        .padding(end = 20.dp),
                    fontSize = 15.sp
                )
            }
        }


}
@Composable
fun FeetPersonalProfile1(navController: NavHostController){
    val viewModel: UserViewModel = viewModel()
    val userDetail by viewModel.user.observeAsState()  // Quan sát LiveData người dùng
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getUserDetailById("674f1c2975eb705d0ff112b6")  // Lấy dữ liệu người dùng khi composable được gọi
    }
    Column {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(bottom = 20.dp, top = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Số điện thoại",
                modifier = Modifier
                    .padding(start = 20.dp, top = 5.dp),
                color = Color(0xFF989898)
            )
            Text(text = "${userDetail?.phoneNumber}",
                modifier = Modifier
                    .padding(end = 20.dp),
                fontSize = 15.sp
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(start = 20.dp, end = 20.dp)
                .background(color = Color(0xFFe4e4e4))
        ) {
        }
//        _vanphuc : giới tính
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(bottom = 20.dp, top = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Tên đăng nhập",
                modifier = Modifier
                    .padding(start = 20.dp, top = 5.dp),
                color = Color(0xFF989898)
            )
            Text(text = "${userDetail?.username}",
                modifier = Modifier
                    .padding(end = 20.dp),
                fontSize = 15.sp
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(start = 20.dp, end = 20.dp)
                .background(color = Color(0xFFe4e4e4))
        ) {
        }
//        _vanphuc : ngay sinh
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(bottom = 20.dp, top = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Email",
                modifier = Modifier
                    .padding(start = 20.dp, top = 5.dp),
                color = Color(0xFF989898)
            )
            Text(text = "${userDetail?.email}",
                modifier = Modifier
                    .padding(end = 20.dp),
                fontSize = 15.sp
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(start = 20.dp, end = 20.dp)
                .background(color = Color(0xFFe4e4e4))
        ) {
        }
//        _vanphuc : dia chi
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(bottom = 20.dp, top = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Mật khẩu",
                modifier = Modifier
                    .padding(start = 20.dp, top = 5.dp),
                color = Color(0xFF989898)
            )
            Text(text = "${userDetail?.password}",
                modifier = Modifier
                    .padding(end = 20.dp),
                fontSize = 15.sp
            )
        }
    }
}
