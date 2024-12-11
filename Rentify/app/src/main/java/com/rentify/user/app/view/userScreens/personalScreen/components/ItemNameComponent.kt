package com.rentify.user.app.view.userScreens.personalScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.R
import com.rentify.user.app.utils.Component.getLoginViewModel

import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.LoginRepository.LoginRepository
import com.rentify.user.app.viewModel.LoginViewModel
import com.rentify.user.app.viewModel.UserViewmodel.UserViewModel
import android.widget.Toast
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import coil.compose.AsyncImage

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ItemNameComponent() {
    LayoutItemName(navController = rememberNavController())
}

@Composable
fun LayoutItemName(navController: NavHostController) {
    val viewModel: UserViewModel = viewModel()
    val userDetail by viewModel.user.observeAsState()  // Quan sát LiveData người dùng
    val errorMessage by viewModel.error.observeAsState()  // Quan sát LiveData lỗi
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
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

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .height(70.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color = Color.White)
            .clickable {
                navController.navigate("PROFILE")
            }
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        userDetail?.profile_picture_url?.let { photoUrl ->
            val uriAnh = "http://10.0.2.2:3000/$photoUrl"
            AsyncImage(
                model = uriAnh,
                contentDescription = "Profile Picture",
                placeholder = painterResource(R.drawable.user), // Ảnh placeholder
                error = painterResource(R.drawable.user), // Ảnh lỗi
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(50.dp)),
                contentScale = ContentScale.Crop
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = "${userDetail?.name}",
                fontSize = 14.sp,
                color = Color(0xff2d293a),
                modifier = Modifier
                    .padding(start = 5.dp),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.padding(3.dp))
            Row {
                Text(
                    text = userDetail?._id?.let {
                        if (it.length > 10) "${it.take(10)}..." else it
                    } ?: "Unknown ID",
                    fontSize = 12.sp,
                    color = Color(0xff2d293a),
                    modifier = Modifier
                        .padding(start = 5.dp, top = 5.dp),
                    fontWeight = FontWeight.Bold
                )
                Image(
                    painter = painterResource(id = R.drawable.copy),
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp)
                        .padding(start = 10.dp)
                        .clip(CircleShape)
                        .clickable {
                            val idToCopy = userDetail?._id ?: "Unknown ID"
                            clipboardManager.setText(AnnotatedString(idToCopy))
                            Toast.makeText(context, "Sao chép thành công: $idToCopy", Toast.LENGTH_SHORT).show()
                        }
                )
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        Image(
            painter = painterResource(id = R.drawable.baseline_navigate_next_24),
            contentDescription = null,
            modifier = Modifier
                .width(30.dp)
                .height(30.dp)
                .padding(end = 5.dp)
                .clip(CircleShape)
        )
    }
}
