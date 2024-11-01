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
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rentify.user.app.repository.LoginRepository
import com.rentify.user.app.ui.theme.ColorBlack
import com.rentify.user.app.ui.theme.colorInput
import com.rentify.user.app.ui.theme.colorText
import com.rentify.user.app.ui.theme.greenInput
import com.rentify.user.app.ui.theme.textFieldBackgroundColor
import com.rentify.user.app.utils.ShowReport
import com.rentify.user.app.view.auth.components.HeaderComponent
import com.rentify.user.app.viewModel.LoginViewModel


class LoginScreen : ComponentActivity() {
    private lateinit var loginViewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            var loginViewModel: LoginViewModel = ViewModelProvider(this, LoginViewModel::class.java)
            LoginScreenApp(loginViewModel)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun LoginScreenApp(loginViewModel: LoginViewModel) {
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
                navigateToHome(it) // Định nghĩa hàm này để chuyển đến màn hình chính
            }
        }
        // Hiển thị thông báo lỗi


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
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding()
                ) {
                    Text(
                        text = "Chào mừng quay trở lại !",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.padding(top = 15.dp))
                    Text(
                        text = "Hãy đăng nhập vào tài khoản của bạn để bắt đầu nào ! ",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )

                }

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
                    TextField(
                        value = username,
                        onValueChange = { newText ->
                            username = newText
                            loginViewModel.clearEmailError() // Reset thông báo lỗi khi người dùng nhập lại
                        },
                        placeholder = { Text(text = "Email") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp)
                            .border(
                                width = 1.dp,
                                color = if (isFocusedEmail) greenInput else colorInput,
                                shape = RoundedCornerShape(15.dp)
                            )
                            .focusable()
                            .onFocusChanged { focusState -> isFocusedEmail = focusState.isFocused },
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = textFieldBackgroundColor,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = greenInput
                        ),
                        shape = RoundedCornerShape(20.dp),
                    )
                    //loi cho email
                    errorEmail?.let { ShowReport.ShowError(message = it) }

                    Spacer(modifier = Modifier.padding(top = 30.dp))
                    //password
                    TextField(
                        value = password,
                        onValueChange = { newText ->
                            password = newText
                            loginViewModel.clearPasswordError()
                        },
                        placeholder = { Text(text = "Password") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp)
                            .border(
                                width = 1.dp,
                                color = if (isFocusedPass) greenInput else colorInput,
                                shape = RoundedCornerShape(15.dp)
                            )
                            .focusable()
                            .onFocusChanged { focusState -> isFocusedPass = focusState.isFocused },
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = textFieldBackgroundColor,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = greenInput
                        ),
                        shape = RoundedCornerShape(20.dp),
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
                        onClick = {},
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Chưa có tài khoản ?",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = ColorBlack
                        )
                        Text(
                            text = "Đăng ký",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = greenInput,
                            modifier = Modifier
                                .padding(start = 10.dp)
                                .clickable {
                                    //chuyen sang man hinh dang ky

                                }
                        )

                    }

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