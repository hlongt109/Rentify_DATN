package com.rentify.user.app.view.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rentify.user.app.MainActivity.ROUTER
import com.rentify.user.app.R

import com.rentify.user.app.network.RetrofitService
import com.rentify.user.app.repository.LoginRepository.ApiResponse
import com.rentify.user.app.repository.LoginRepository.RegisterRequest
import com.rentify.user.app.repository.RegisterRepository.RegisterRepository
import com.rentify.user.app.utils.Component.HeaderBar
import com.rentify.user.app.utils.ShowReport
import com.rentify.user.app.viewModel.RegisterViewModel

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun RegisterScreen(navController: NavHostController) {
    val apiService = RetrofitService()
    val userRepository = RegisterRepository(apiService)
    val registerViewModel: RegisterViewModel = viewModel(
        factory = RegisterViewModel.RegisterViewModelFactory(userRepository)
    )
    val context = LocalContext.current
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repassword by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    // Biến trạng thái cho từng TextField
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isRepasswordVisible by remember { mutableStateOf(false) }
    // Biến để theo dõi trạng thái focus cho từng TextField
    var isNameFocused by remember { mutableStateOf(false) }
    var isEmailFocused by remember { mutableStateOf(false) }
    var isPasswordFocused by remember { mutableStateOf(false) }
    var isRePasswordFocused by remember { mutableStateOf(false) }
    var isPhoneNumber by remember { mutableStateOf(false) }
    //khai bao cac loi
    val errorEmail by registerViewModel.errorEmail.observeAsState()
    val errorPass by registerViewModel.errorPass.observeAsState()
    val errorPhone by registerViewModel.errorPhone.observeAsState()
    val errorName by registerViewModel.errorName.observeAsState()
    //
    val successMessage by registerViewModel.successMessage.observeAsState()
    val isLoading by registerViewModel.isLoading.observeAsState()

    LaunchedEffect(successMessage) {
        successMessage?.let {
            navController.navigate(ROUTER.LOGIN.name)
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color(0xffffffff)
            )
            .navigationBarsPadding()
            .statusBarsPadding()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        //icone
       HeaderBar(navController)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp),
        ) {
            Text(
                text = "Chào mừng bạn đến với Rentify",
                fontFamily = FontFamily(Font(R.font.cairo_regular)),
                color = Color.Black,
                fontWeight = FontWeight(700),
                fontSize = 24.sp,
            )
            Spacer(modifier = Modifier.height(8.dp)) // Thêm khoảng cách giữa hai Text

            Text(
                text = "Hãy đăng ký tài khoản của bạn để bắt đầu nào ! ",
                fontFamily = FontFamily(Font(R.font.cairo_regular)),
                color = Color.Black,
                fontWeight = FontWeight(500),
                fontSize = 14.sp,
            )
            Spacer(modifier = Modifier.fillMaxHeight(0.07f)) // Thêm khoảng cách giữa hai Text
            // Thêm khoảng cách giữa hai Text
            Text(
                text = "Đăng ký",
                fontFamily = FontFamily(Font(R.font.cairo_regular)),
                color = Color.Black,
                fontWeight = FontWeight(700),
                fontSize = 24.sp,
            )
            errorName?.let {
                ShowReport.ShowError(message = it)
            }
            Spacer(modifier = Modifier.fillMaxHeight(0.02f)) // Thêm khoảng cách giữa hai Text
        }

        Column(
            modifier = Modifier.fillMaxWidth()

        ) {
            TextField(
                value = username,
                onValueChange = { username = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 2.dp,
                        color = if (isNameFocused) Color(0xFF56b5bc) else Color.Transparent,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .onFocusChanged { focusState -> isNameFocused = focusState.isFocused },


                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedPlaceholderColor = Color.Black,
                    unfocusedPlaceholderColor = Color.Gray,
                    unfocusedContainerColor = Color(0xFFfafafa),
                    focusedContainerColor = Color.White
                ),
                placeholder = {
                    Text(
                        text = "Name",
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.cairo_regular))
                    )
                },
                shape = RoundedCornerShape(size = 8.dp),
                textStyle = TextStyle(
                    color = Color.Black, fontFamily = FontFamily(Font(R.font.cairo_regular))
                )
            )

        }
        Spacer(modifier = Modifier.fillMaxHeight(0.02f)) // Thêm khoảng cách giữa hai Text
        //emai
        Column(
            modifier = Modifier.fillMaxWidth()

        ) {

            TextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 2.dp,
                        color = if (isEmailFocused) Color(0xFF56b5bc) else Color.Transparent,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .onFocusChanged { focusState -> isEmailFocused = focusState.isFocused },


                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedPlaceholderColor = Color.Black,
                    unfocusedPlaceholderColor = Color.Gray,
                    unfocusedContainerColor = Color(0xFFfafafa),
                    focusedContainerColor = Color.White
                ),
                placeholder = {
                    Text(
                        text = "Email",
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.cairo_regular))
                    )
                },
                shape = RoundedCornerShape(size = 8.dp),
                textStyle = TextStyle(
                    color = Color.Black, fontFamily = FontFamily(Font(R.font.cairo_regular))
                )
            )
            errorEmail?.let {
                ShowReport.ShowError(message = it)
            }
        }
        Spacer(modifier = Modifier.fillMaxHeight(0.02f))
        //phoneNumber
        Column(
            modifier = Modifier.fillMaxWidth()

        ) {

            TextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 2.dp,
                        color = if (isPhoneNumber) Color(0xFF56b5bc) else Color.Transparent,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .onFocusChanged { focusState -> isPhoneNumber = focusState.isFocused },


                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedPlaceholderColor = Color.Black,
                    unfocusedPlaceholderColor = Color.Gray,
                    unfocusedContainerColor = Color(0xFFfafafa),
                    focusedContainerColor = Color.White
                ),
                placeholder = {
                    Text(
                        text = "Số điện thoại",
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.cairo_regular))
                    )
                },
                shape = RoundedCornerShape(size = 8.dp),
                textStyle = TextStyle(
                    color = Color.Black, fontFamily = FontFamily(Font(R.font.cairo_regular))
                )
            )
            errorPass?.let {
                ShowReport.ShowError(message = it)
            }
        }

        //pass
        Spacer(modifier = Modifier.fillMaxHeight(0.02f)) // Thêm khoảng cách giữa hai Text

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(value = password, onValueChange = {
                password = it
            }, modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 2.dp,
                    color = if (isPasswordFocused) Color(0xFF56b5bc) else Color.Transparent,
                    shape = RoundedCornerShape(12.dp)
                )
                .onFocusChanged { focusState -> isPasswordFocused = focusState.isFocused },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedPlaceholderColor = Color.Black,
                    unfocusedPlaceholderColor = Color.Gray,
                    unfocusedContainerColor = Color(0xFFfafafa),
                    focusedContainerColor = Color.White
                ), placeholder = {
                    Text(
                        text = "Password",
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.cairo_regular)),

                        )
                }, shape = RoundedCornerShape(size = 8.dp), trailingIcon = {
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(
                            painter = painterResource(
                                id = if (isPasswordVisible) R.drawable.show else R.drawable.an
                            ), contentDescription = null, modifier = Modifier.size(20.dp, 20.dp)
                        )
                    }
                }, visualTransformation = if (isPasswordVisible) VisualTransformation.None
                else PasswordVisualTransformation(), textStyle = TextStyle(
                    color = Color.Black, fontFamily = FontFamily(Font(R.font.cairo_regular))
                )
            )
            errorPass?.let {
                ShowReport.ShowError(message = it)
            }
        }
        //nhập lại pass
        Spacer(modifier = Modifier.fillMaxHeight(0.02f)) // Thêm khoảng cách giữa hai Text


        Column(
            modifier = Modifier.fillMaxWidth()
        ) {

            TextField(
                value = repassword, onValueChange = {
                    repassword = it
                }, modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 2.dp,
                        color = if (isRePasswordFocused) Color(0xFF56b5bc) else Color.Transparent,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .onFocusChanged { focusState -> isRePasswordFocused = focusState.isFocused },

                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedPlaceholderColor = Color.Black,
                    unfocusedPlaceholderColor = Color.Gray,
                    unfocusedContainerColor = Color(0xFFfafafa),
                    focusedContainerColor = Color.White
                ), placeholder = {
                    Text(
                        text = "Re-enter password",
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.cairo_regular)),
                        style = TextStyle(

                            fontFamily = FontFamily(Font(R.font.cairo_regular))
                        )
                    )
                },

                shape = RoundedCornerShape(size = 8.dp), trailingIcon = {
                    IconButton(onClick = { isRepasswordVisible = !isRepasswordVisible }

                    ) {
                        Icon(
                            painter = painterResource(
                                id = if (isRepasswordVisible) R.drawable.show else R.drawable.an
                            ), contentDescription = null, modifier = Modifier.size(20.dp, 20.dp)
                        )
                    }
                }, visualTransformation = if (isRepasswordVisible) VisualTransformation.None
                else PasswordVisualTransformation(), textStyle = TextStyle(
                    color = Color.Black, fontFamily = FontFamily(Font(R.font.cairo_regular))
                )

            )
        }
        Spacer(modifier = Modifier.fillMaxHeight(0.1f)) // Thêm khoảng cách giữa hai Text
//đăng ký

        Button(
            onClick = {
                if (username.isBlank() || email.isBlank() || password.isBlank() || repassword.isBlank()) {
                    Toast.makeText(
                        context,
                        "Vui lòng điền đầy đủ thông tin đăng ký.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (password != repassword) {
                    Toast.makeText(
                        context,
                        "Mật khẩu và mật khẩu nhập lại không khớp.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    registerViewModel.register(username, email, password, repassword, phoneNumber){
                        Toast.makeText(context, successMessage, Toast.LENGTH_SHORT).show()
                        navController.navigate(ROUTER.LOGIN.name)
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth(),
            //  .background(Color(0xffFE724C), RoundedCornerShape(25.dp)), // Bo tròn 12.dp

            shape = RoundedCornerShape(10.dp), colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xff209FA8)
            )
        ) {
            Text(
                text = "Đăng ký",
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.cairo_regular)),
                color = Color(0xffffffff)
            )
        }
        Spacer(modifier = Modifier.fillMaxHeight(0.2f)) // Thêm khoảng cách giữa hai Text

//text đăng nhập
        Row {
            Text(
                text = "Bạn có tài khoản rồi!",
                fontFamily = FontFamily(Font(R.font.cairo_regular)),
                color = Color.Black
            )

            Text(
                text = " Đăng nhập",
                fontFamily = FontFamily(Font(R.font.cairo_regular)),
                color = Color(0xff209FA8),
                modifier = Modifier
                    .clickable {
                        navController.navigate(ROUTER.LOGIN.name)
                    }
            )

        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingGetLayoutRegisterScreen() {
    RegisterScreen(navController = rememberNavController())
}
//            BasicTextField(
//                value = email,
//                onValueChange = { email = it },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(47.dp) // Chiều cao nhỏ hơn
//                    .background(Color.White, RoundedCornerShape(8.dp))
//                    .padding(horizontal = 8.dp, vertical = 8.dp), // Thêm padding để đảm bảo văn bản không bị cắt
//                singleLine = true,
//                textStyle = TextStyle(
//                    color = Color.Black,
//                    fontSize = 15.sp,
//                    fontFamily = FontFamily(Font(R.font.cairo_regular))
//                ),
//                decorationBox = { innerTextField ->
//                    if (email.isEmpty()) {
//                        Text(
//                            text = "Enter your email",
//                            color = Color.Gray,
//                            fontSize = 15.sp,
//                            fontFamily = FontFamily(Font(R.font.cairo_regular))
//                        )
//                    }
//                    innerTextField() // Hiển thị nội dung nhập của người dùng
//                }
//            )



