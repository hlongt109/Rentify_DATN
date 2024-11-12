package com.rentify.user.app.view.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.LoginRepository.LoginRepository
import com.rentify.user.app.ui.theme.colorText
import com.rentify.user.app.ui.theme.greenInput
import com.rentify.user.app.utils.ShowReport
import com.rentify.user.app.view.auth.components.GoToRegister
import com.rentify.user.app.view.auth.components.HeaderComponent
import com.rentify.user.app.view.auth.components.TextFieldComponent
import com.rentify.user.app.view.auth.components.TextLoginContent
import com.rentify.user.app.view.navigator.ROUTER
import com.rentify.user.app.viewModel.LoginViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreenApp(navController: NavController) {
    val apiService = RetrofitService()
    val userRepository = LoginRepository(apiService)
    val loginViewModel: LoginViewModel = viewModel(
        factory = LoginViewModel.LoginViewModelFactory(userRepository)
    )
    // Khai báo biến để lưu thông tin đăng nhập
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isFocusedEmail by remember { mutableStateOf(false) }
    var isFocusedPass by remember { mutableStateOf(false) }

    // Quan sát thông báo lỗi
    val errorEmail by loginViewModel.errorEmail.observeAsState()
    val errorPass by loginViewModel.errorPass.observeAsState()
    // Quan sát thông báo thành công
    val successMessage by loginViewModel.successMessage.observeAsState()

    // Hiển thị thông báo khi đăng nhập thành công
    LaunchedEffect(successMessage) {
        successMessage?.let {
            // Xử lý chuyển màn khi đăng nhập thành công
            // Định nghĩa hàm này để chuyển đến màn hình chính
                role ->
            if (role == "user") {
                navController.navigate(ROUTER.HOME.name)
            } else if (role == "staffs") {
                //chua co man nay
                //navigator.navigate(ROUTER.HOME.name)
            }
        }
    }
    Box(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
        ) {
            Spacer(modifier = Modifier.padding(top = 30.dp))
            HeaderComponent(backgroundColor = Color.White, title = "", navController = navController)
            Spacer(modifier = Modifier.padding(top = 50.dp))
            //text chao mung
            TextLoginContent()
            //
            Spacer(modifier = Modifier.padding(top = 70.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                Text(
                    text = "Đăng nhập",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.padding(top = 30.dp))
                //email

                TextFieldComponent(
                    value = username,
                    onValueChange = { newText ->
                        username = newText
                        loginViewModel.clearEmailError()
                    },
                    placeholder = "Email",
                    isFocused = remember { mutableStateOf(isFocusedEmail) },

                )
                //loi cho email
                errorEmail?.let { ShowReport.ShowError(message = it) }
                Spacer(modifier = Modifier.padding(top = 30.dp))
                //password
                TextFieldComponent(
                    value = password,
                    onValueChange = { newText ->
                        password = newText
                        loginViewModel.clearPasswordError()
                    },
                    placeholder = "Password",
                    isFocused = remember { mutableStateOf(isFocusedPass) },
                    isPassword = true
                )
                //loi cho password
                errorPass?.let { ShowReport.ShowError(message = it) }
                //quen mat khau

                Text(
                    text = "Quên mật khẩu ?",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = colorText,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
                //button dang nhap
                Spacer(modifier = Modifier.padding(top = 50.dp))
                Button(
                    onClick = {
                        loginViewModel.login(username, password)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = greenInput, shape = RoundedCornerShape(20.dp))
                        .height(50.dp),
                    colors = ButtonColors(
                        containerColor = greenInput,
                        contentColor = greenInput,
                        disabledContentColor = greenInput,
                        disabledContainerColor = greenInput
                    )
                ) {
                    Text(
                        text = "Đăng nhập",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,

                        )
                }

                //chua co tai khoan
                Spacer(modifier = Modifier.padding(top = 50.dp))
                GoToRegister(navController)
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    val navController = rememberNavController()
    LoginScreenApp(navController)
}