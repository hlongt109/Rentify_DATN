package com.rentify.user.app.view.auth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.LoginRepository.LoginRepository
import com.rentify.user.app.ui.theme.ColorBlack
import com.rentify.user.app.ui.theme.colorInput
import com.rentify.user.app.ui.theme.colorText
import com.rentify.user.app.ui.theme.greenInput
import com.rentify.user.app.ui.theme.textFieldBackgroundColor
import com.rentify.user.app.utils.ShowReport
import com.rentify.user.app.view.auth.components.GoToRegister
import com.rentify.user.app.view.auth.components.HeaderComponent
import com.rentify.user.app.view.auth.components.TextFieldComponent
import com.rentify.user.app.view.auth.components.TextLoginContent
import com.rentify.user.app.viewModel.LoginViewModel


class LoginScreen : ComponentActivity() {
    private lateinit var loginViewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //view model
            // Khởi tạo LoginRepository

            LoginScreenApp()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun LoginScreenApp() {
        val apiService = RetrofitService()
        val userRepository = LoginRepository(apiService)
        loginViewModel =
            ViewModelProvider(this, LoginViewModel.LoginViewModelFactory(userRepository)).get(
                LoginViewModel::class.java
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
                HeaderComponent(backgroundColor = Color.White, title = "")
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
                    GoToRegister()
                }

            }
        }
    }
    // Hàm chuyển màn
    private fun navigateToHome(role: String) {
        // Thực hiện chuyển màn ở đây (ví dụ sử dụng NavController hoặc Intent)
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
//    LoginScreenApp()
}