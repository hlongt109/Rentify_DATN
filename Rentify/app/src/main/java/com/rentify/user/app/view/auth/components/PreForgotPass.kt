package com.rentify.user.app.view.auth.components

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
import com.rentify.user.app.ui.theme.greenInput
import com.rentify.user.app.utils.Component.HeaderBar
import com.rentify.user.app.utils.ShowReport
import com.rentify.user.app.view.auth.ForgotPasswordScreen
import com.rentify.user.app.viewModel.LoginViewModel
import com.rentify.user.app.viewModel.UserViewmodel.ForgotPasswordViewModel

@Composable
fun PreForgotPass(
    navController: NavController,
    navigationType: String? = null,
    email: String? = null
) {

    val context = LocalContext.current
    val repository = ForgotRepository(RetrofitService().ApiService)
    val forgotViewModel: ForgotPasswordViewModel = viewModel(
        factory = ForgotPasswordViewModel.ForgotPasswordViewModelFactory(repository)
    )

    val errorMessage by forgotViewModel.errorMessage.observeAsState()
    val successMessage by forgotViewModel.successMessage.observeAsState()
    val successMessageMail by forgotViewModel.successMessageMail.observeAsState()
    val successMessageConfirm by forgotViewModel.successMessageConfirm.observeAsState()
    val errorCode by forgotViewModel.errorCode.observeAsState()
    val errorEmail by forgotViewModel.errorEmail.observeAsState()
    val isLoading by forgotViewModel.isLoading.observeAsState()

    var email by remember { mutableStateOf(email ?: "") }
    var confirmNumber by remember { mutableStateOf("") }
    var isFocusedEmail by remember { mutableStateOf(false) }
    var isConfirmNumber by remember { mutableStateOf(false) }

    var showConfirmCodeField by remember { mutableStateOf(false) }

    val screenTitle = when (navigationType) {
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
            Spacer(modifier = Modifier.padding(top = 70.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                Text(
                    text = screenTitle,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.padding(top = 30.dp))
                //email
                TextFieldComponent(
                    value = email,
                    onValueChange = { newText ->
                        email = newText
                        forgotViewModel.clearEmail()
                    },
                    placeholder = "Email",
                    isFocused = remember { mutableStateOf(isFocusedEmail) },
                )
                errorEmail?.let {
                    ShowReport.ShowError(message = it)
                }
                Spacer(modifier = Modifier.padding(top = 15.dp))
                if (showConfirmCodeField) {
                    TextFieldComponent(
                        value = confirmNumber,
                        onValueChange = { newText ->
                            confirmNumber = newText
                            forgotViewModel.confirmCode()
                        },
                        placeholder = "Mã xác nhận",
                        isFocused = remember { mutableStateOf(isConfirmNumber) },
                    )
                    errorCode?.let {
                        ShowReport.ShowError(message = it)
                    }
                }
                errorMessage?.let {
                    ShowReport.ShowError(message = it)
                }
                //button dang nhap
                Spacer(modifier = Modifier.padding(top = 50.dp))
                Button(
                    onClick = {
                        if (!showConfirmCodeField) {
                            forgotViewModel.postEmail(email) {
                                showConfirmCodeField = true
                                Log.d("LogSuccess", "PreForgotPass: $successMessageMail")
                                successMessageMail?.let {
                                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                                }
                            }
                        } else {
                            forgotViewModel.confirmCode(email, confirmNumber) {
                                successMessageConfirm?.let {
                                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                                    navController.navigate(MainActivity.ROUTER.FORGOTPASS.name + "/$email/$navigationType")
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
                        text = if (!showConfirmCodeField) "Gửi mã xác nhận" else "Xác nhận",
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

@Preview(showBackground = true)
@Composable
fun PreViewPreForgot() {
    PreForgotPass(navController = rememberNavController())
}