package com.rentify.user.app.view.auth

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.MainActivity
import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.ForgotRepository.ForgotRepository
import com.rentify.user.app.repository.LoginRepository.LoginRepository
import com.rentify.user.app.ui.theme.colorLocation
import com.rentify.user.app.ui.theme.colorText
import com.rentify.user.app.ui.theme.greenInput
import com.rentify.user.app.utils.Component.HeaderBar
import com.rentify.user.app.utils.ShowReport
import com.rentify.user.app.view.auth.components.GoToRegister
import com.rentify.user.app.view.auth.components.HeaderComponent
import com.rentify.user.app.view.auth.components.TextFieldComponent
import com.rentify.user.app.view.auth.components.TextLoginContent
import com.rentify.user.app.viewModel.LoginViewModel
import com.rentify.user.app.viewModel.UserViewmodel.ForgotPasswordViewModel

@Composable
fun ForgotPasswordScreen(
    navController: NavController,
    email: String,
    typeTitle: String
) {

    val context = LocalContext.current
    val repository = ForgotRepository(RetrofitService().ApiService)
    val forgotViewModel: ForgotPasswordViewModel = viewModel(
        factory = ForgotPasswordViewModel.ForgotPasswordViewModelFactory(repository)
    )
    val apiService = RetrofitService()
    val userRepository = LoginRepository(apiService)
    val loginViewModel: LoginViewModel = viewModel(
        factory = LoginViewModel.LoginViewModelFactory(userRepository, context)
    )

    val errorMessage by forgotViewModel.errorMessage.observeAsState()
    val successMessage by forgotViewModel.successMessage.observeAsState()
    val errorEmail by forgotViewModel.errorEmail.observeAsState()
    val errorPassword by forgotViewModel.errorPassword.observeAsState()
    val errorConfirmPass by forgotViewModel.errorConfirmPassword.observeAsState()
    val errorCode by forgotViewModel.errorCode.observeAsState()
    val isLoading by forgotViewModel.isLoading.observeAsState()

    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isNewPassword by remember { mutableStateOf(false) }
    var isConfirmPassword by remember { mutableStateOf(false) }
    Log.d("CheckEmail", "ForgotPasswordScreen: $email")

    val title = when(typeTitle){
        "changePassword" -> "Đổi mật khẩu"
        else -> "Quên mật khẩu"

    }

    Box(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        HeaderBar(navController)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
        ) {
            Spacer(modifier = Modifier.padding(top = 50.dp))
            //text chao mung
//            TextLoginContent()
            //
            Spacer(modifier = Modifier.padding(top = 70.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                Text(
                    text = title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.padding(top = 30.dp))
                //email

                TextFieldComponent(
                    value = newPassword,
                    onValueChange = { newText ->
                        newPassword = newText
                        forgotViewModel.clearPassword()
                    },
                    placeholder = "Mật khẩu mới",
                    isFocused = remember { mutableStateOf(isNewPassword) },
                    isPassword = true
                    )
                //loi cho email
                errorPassword?.let { ShowReport.ShowError(message = it) }
                Spacer(modifier = Modifier.padding(top = 30.dp))
                //password
                TextFieldComponent(
                    value = confirmPassword,
                    onValueChange = { newText ->
                        confirmPassword = newText
                        forgotViewModel.clearConfirmPassword()
                    },
                    placeholder = "Xác nhận mật khẩu",
                    isFocused = remember { mutableStateOf(isConfirmPassword) },
                    isPassword = true
                )
                errorConfirmPass?.let {
                    ShowReport.ShowError(message = it)
                }
                errorMessage?.let {
                    ShowReport.ShowError(message = it)
                }
                //button dang nhap
                Spacer(modifier = Modifier.padding(top = 50.dp))
                Button(
                    onClick = {
                        forgotViewModel.resetPassword(email, newPassword, confirmPassword){
                            successMessage?.let {
                                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                                when(typeTitle){
                                    "changePassword" -> {
                                        navController.navigate(MainActivity.ROUTER.LOGIN.name)
                                        loginViewModel.logout()
                                    }

                                    "resetPass" -> {
                                        navController.navigate(MainActivity.ROUTER.LOGIN.name)
                                    }
                                }
                            }
                        }
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
                        text = "Xác nhận",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        )
                }
            }
        }
    }
    if (isLoading == true) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .background(Color.White, shape = RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = colorLocation)
            }
        }
    }
}