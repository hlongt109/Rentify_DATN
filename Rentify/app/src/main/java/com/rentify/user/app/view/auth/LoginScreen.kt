package com.rentify.user.app.view.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.LoginRepository.LoginRepository
import com.rentify.user.app.ui.theme.greenInput
import com.rentify.user.app.utils.ShowReport
import com.rentify.user.app.view.auth.components.GoToRegister
import com.rentify.user.app.view.auth.components.HeaderComponent
import com.rentify.user.app.view.auth.components.TextFieldComponent
import com.rentify.user.app.view.auth.components.TextLoginContent
import com.rentify.user.app.viewModel.LoginViewModel
@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreenApp(navController = rememberNavController())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreenApp(navController: NavHostController) {
    val apiService = RetrofitService()
    val userRepository = LoginRepository(apiService)

    // Sử dụng LocalContext để lấy ViewModelStoreOwner
    val context = LocalContext.current
    val loginViewModel: LoginViewModel = ViewModelProvider(context as ViewModelStoreOwner, LoginViewModel.LoginViewModelFactory(userRepository)).get(LoginViewModel::class.java)

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val isUsernameFocused = remember { mutableStateOf(false) }
    val isPasswordFocused = remember { mutableStateOf(false) }

    val errorEmail by loginViewModel.errorEmail.observeAsState()
    val errorPass by loginViewModel.errorPass.observeAsState()
    val successMessage by loginViewModel.successMessage.observeAsState()

    LaunchedEffect(successMessage) {
        successMessage?.let {
            navController.navigate("HOME")
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
            HeaderComponent(backgroundColor = Color.White, title = "")
            Spacer(modifier = Modifier.padding(top = 50.dp))
            TextLoginContent()
            Spacer(modifier = Modifier.padding(top = 70.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Đăng nhập",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.padding(top = 30.dp))

                TextFieldComponent(
                    value = username,
                    onValueChange = { newText ->
                        username = newText
                        loginViewModel.clearEmailError()
                    },
                    placeholder = "Email",
                    isFocused = isUsernameFocused // Truyền MutableState vào đây
                )
                errorEmail?.let { ShowReport.ShowError(message = it) }
                Spacer(modifier = Modifier.padding(top = 30.dp))

                TextFieldComponent(
                    value = password,
                    onValueChange = { newText ->
                        password = newText
                        loginViewModel.clearPasswordError()
                    },
                    placeholder = "Password",
                    isFocused = isPasswordFocused // Truyền MutableState vào đây
                )
                errorPass?.let { ShowReport.ShowError(message = it) }

                Text(
                    text = "Quên mật khẩu ?",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.padding(top = 50.dp))
                Button(
                    onClick = {
                        loginViewModel.login(username, password)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = greenInput)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = greenInput)
                ) {
                    Text(
                        text = "Đăng nhập",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.padding(top = 50.dp))
                GoToRegister()
            }
        }
    }
}
